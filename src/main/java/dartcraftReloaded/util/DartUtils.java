package dartcraftReloaded.util;

import com.google.common.collect.Lists;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.capablilities.Modifiable.IModifiableTool;
import dartcraftReloaded.capablilities.Modifiable.Modifier;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.handlers.PacketHandler;
import dartcraftReloaded.Constants;
import dartcraftReloaded.blocks.BlockForceLog;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.items.tools.ItemForceAxe;
import gnu.trove.set.hash.THashSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Method;
import java.util.*;

import static dartcraftReloaded.Constants.*;

public class DartUtils {

    public static Logger logger;
    public static final String RESOURCE = Constants.modId.toLowerCase(Locale.US);

    public static Logger getLogger(){
        if(logger == null){
            logger = LogManager.getFormatterLogger(Constants.modId);
        }
        return logger;
    }

    public static void removeEnchant(Enchantment enchantment, ItemStack stack){
        Map<Enchantment, Integer> enchantMap = EnchantmentHelper.getEnchantments(stack);
        if(enchantMap.containsKey(enchantment)){
            enchantMap.remove(enchantment);
        }

        EnchantmentHelper.setEnchantments(enchantMap, stack);
    }

    public static class TreeChopTask{
        public final World world;
        public final EntityPlayer player;
        public final ItemStack tool;
        public final int blocksPerTick;

        public Queue<BlockPos> blocks = Lists.newLinkedList();
        public Set<BlockPos> visited = new THashSet<>();

        public TreeChopTask(ItemStack tool, BlockPos start, EntityPlayer player, int blocksPerTick) {
            this.world = player.getEntityWorld();
            this.player = player;
            this.tool = tool;
            this.blocksPerTick = blocksPerTick;

            this.blocks.add(start);
        }

        @SubscribeEvent
        public void chop(TickEvent.WorldTickEvent event) {
            if(event.side.isClient()) {
                finish();
                return;
            }
            // only if same dimension
            if(event.world.provider.getDimension() != world.provider.getDimension()) {
                return;
            }

            // setup
            int left = blocksPerTick;

            // continue running
            BlockPos pos;
            while(left > 0) {
                // completely done or can't do our job anymore?!
                if(blocks.isEmpty()) {
                    finish();
                    return;
                }

                pos = blocks.remove();
                if(!visited.add(pos)) {
                    continue;
                }

                // can we harvest the block and is effective?
                if(!isLog(world, pos)) {
                    continue;
                }

                // save its neighbours
                for(EnumFacing facing : new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST }) {
                    BlockPos pos2 = pos.offset(facing);
                    if(!visited.contains(pos2)) {
                        blocks.add(pos2);
                    }
                }

                // also add the layer above.. stupid acacia trees
                for(int x = 0; x < 3; x++) {
                    for(int z = 0; z < 3; z++) {
                        BlockPos pos2 = pos.add(-1 + x, 1, -1 + z);
                        if(!visited.contains(pos2)) {
                            blocks.add(pos2);
                        }
                    }
                }

                // break it, wooo!
                DartUtils.breakExtraBlock(tool, world, player, pos, pos);
                left--;
            }
        }

        private void finish() {
            // goodbye cruel world
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }

    public static void fellTree(ItemStack stack, BlockPos pos, EntityPlayer player){
        if(player.getEntityWorld().isRemote)
            return;

        MinecraftForge.EVENT_BUS.register(new TreeChopTask(stack, pos, player, 10));
    }

    //Credit to Slimeknights for this code until I can logic through it on my own
    public static boolean isTree(World world, BlockPos origin){
        BlockPos pos = null;
        Stack<BlockPos> candidates = new Stack<>();
        candidates.add(origin);

        while(!candidates.isEmpty()) {
            BlockPos candidate = candidates.pop();
            if((pos == null || candidate.getY() > pos.getY()) && isLog(world, candidate)) {
                pos = candidate.up();
                // go up
                while(isLog(world, pos)) {
                    pos = pos.up();
                }
                // check if we still have a way diagonally up
                candidates.add(pos.north());
                candidates.add(pos.east());
                candidates.add(pos.south());
                candidates.add(pos.west());
            }
        }

        // not even one match, so there were no logs.
        if(pos == null) {
            return false;
        }

        // check if there were enough leaves around the last position
        // pos now contains the block above the topmost log
        // we want at least 5 leaves in the surrounding 26 blocks
        int d = 3;
        int o = -1; // -(d-1)/2
        int leaves = 0;
        for(int x = 0; x < d; x++) {
            for(int y = 0; y < d; y++) {
                for(int z = 0; z < d; z++) {
                    BlockPos leaf = pos.add(o + x, o + y, o + z);
                    IBlockState state = world.getBlockState(leaf);
                    if(state.getBlock().isLeaves(state, world, leaf)) {
                        if(++leaves >= 5) {
                            return true;
                        }
                    }
                }
            }
        }

        // not enough leaves. sorreh
        return false;
    }

    public static boolean isLog(World world, BlockPos pos){
        if(world.getBlockState(pos).getBlock() instanceof BlockLog || world.getBlockState(pos).getBlock() instanceof BlockForceLog)
            return true;
        else
            return false;
    }

    public static void breakExtraBlock(ItemStack stack, World world, EntityPlayer player, BlockPos pos, BlockPos refPos) {
        if(!canBreakExtraBlock(stack, world, player, pos, refPos)) {
            return;
        }

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        // callback to the tool the player uses. Called on both sides. This damages the tool n stuff.
        stack.onBlockDestroyed(world, state, pos, player);

        // server sided handling
        if(!world.isRemote) {
            // send the blockbreak event
            int xp = ForgeHooks.onBlockBreakEvent(world, ((EntityPlayerMP) player).interactionManager.getGameType(), (EntityPlayerMP) player, pos);
            if(xp == -1) {
                return;
            }

            // serverside we reproduce ItemInWorldManager.tryHarvestBlock

            TileEntity tileEntity = world.getTileEntity(pos);
            // ItemInWorldManager.removeBlock
            if(block.removedByPlayer(state, world, pos, player, true)) { // boolean is if block can be harvested, checked above
                block.onBlockHarvested(world, pos, state, player);
                block.harvestBlock(world, player, pos, state, tileEntity, stack);
                block.dropXpOnBlockBreak(world, pos, xp);
            }

            // always send block update to client
            PacketHandler.sendPacket(player, new SPacketBlockChange(world, pos));
        }
        // client sided handling
        else {
            // clientside we do a "this clock has been clicked on long enough to be broken" call. This should not send any new packets
            // the code above, executed on the server, sends a block-updates that give us the correct state of the block we destroy.

            // following code can be found in PlayerControllerMP.onPlayerDestroyBlock
            world.playBroadcastSound(2001, pos, Block.getStateId(state));
            if(block.removedByPlayer(state, world, pos, player, true)) {
                block.onBlockHarvested(world, pos, state, player);
            }
            // callback to the tool
            stack.onBlockDestroyed(world, state, pos, player);

            if(stack.getCount() == 0 && stack == player.getHeldItemMainhand()) {
                ForgeEventFactory.onPlayerDestroyItem(player, stack, EnumHand.MAIN_HAND);
                player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
            }

            // send an update to the server, so we get an update back
            //if(PHConstruct.extraBlockUpdates)
            NetHandlerPlayClient netHandlerPlayClient = Minecraft.getMinecraft().getConnection();
            assert netHandlerPlayClient != null;
            netHandlerPlayClient.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, Minecraft
                    .getMinecraft().objectMouseOver.sideHit));
        }
    }

    private static boolean canBreakExtraBlock(ItemStack stack, World world, EntityPlayer player, BlockPos pos, BlockPos refPos) {
        // prevent calling that stuff for air blocks, could lead to unexpected behaviour since it fires events
        if(world.isAirBlock(pos)) {
            return false;
        }

        // check if the block can be broken, since extra block breaks shouldn't instantly break stuff like obsidian
        // or precious ores you can't harvest while mining stone
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        // only effective materials
        //TODO: Check for Effective

        IBlockState refState = world.getBlockState(refPos);
        float refStrength = ForgeHooks.blockStrength(refState, player, world, refPos);
        float strength = ForgeHooks.blockStrength(state, player, world, pos);

        // only harvestable blocks that aren't impossibly slow to harvest
        if(!ForgeHooks.canHarvestBlock(block, player, world, pos) || refStrength / strength > 10f) {
            return false;
        }

        // From this point on it's clear that the player CAN break the block

        if(player.capabilities.isCreativeMode) {
            block.onBlockHarvested(world, pos, state, player);
            if(block.removedByPlayer(state, world, pos, player, false)) {
                block.onBlockHarvested(world, pos, state, player);
            }

            // send update to client
            if(!world.isRemote) {
                PacketHandler.sendPacket(player, new SPacketBlockChange(world, pos));
            }
            return false;
        }
        return true;
    }

    public static boolean isFakePlayer(Entity player){
        return (player instanceof FakePlayer);
    }

    public static String resource(String res) {
        return String.format("%s:%s", RESOURCE, res);
    }

    public static ResourceLocation getResource(String res) {
        return new ResourceLocation(RESOURCE, res);
    }

    // credit to dimdoors team
    public static RayTraceResult enderTrace(World worldIn, EntityPlayer player, double range) {
        Vec3d playerLookVector = player.getLookVec();
        Vec3d tempVector = player.getPositionEyes(1.0F); //this is what constantly gets shifted in the while-loop later, and it starts at the eyes
        Vec3d endVector = tempVector.add(playerLookVector.scale(range));
        if (!Double.isNaN(tempVector.x) && !Double.isNaN(tempVector.y) && !Double.isNaN(tempVector.z)) {
            if (!Double.isNaN(endVector.x) && !Double.isNaN(endVector.y) && !Double.isNaN(endVector.z)) {
                int endX = MathHelper.floor(endVector.x);
                int endY = MathHelper.floor(endVector.y);
                int endZ = MathHelper.floor(endVector.z);
                int playerX = MathHelper.floor(tempVector.x);
                int playerY = MathHelper.floor(tempVector.y);
                int playerZ = MathHelper.floor(tempVector.z);
                BlockPos blockpos = new BlockPos(playerX, playerY, playerZ);
                IBlockState state = worldIn.getBlockState(blockpos);
                Block block = state.getBlock();
                if (block.canCollideCheck(state, false)) {
                    RayTraceResult result = state.collisionRayTrace(worldIn, blockpos, tempVector, endVector);
                    //if (Objects.nonNull(result)) return result;
                }
                int counter = 200; //TODO: should this really be a hardcoded value?
                while (counter-- >= 0) {
                    if (Double.isNaN(tempVector.x) || Double.isNaN(tempVector.y) || Double.isNaN(tempVector.z) ||
                            (playerX == endX && playerY == endY && playerZ == endZ)) return null;
                    boolean xFlag = true;
                    boolean yFlag = true;
                    boolean zFlag = true;
                    double x1 = 999.0D;
                    double y1 = 999.0D;
                    double z1 = 999.0D;
                    if (endX > playerX) x1 = (double) playerX + 1.0D;
                    else if (endX < playerX) x1 = playerX;
                    else xFlag = false;
                    if (endY > playerY) y1 = (double) playerY + 1.0D;
                    else if (endY < playerY) y1 = playerY;
                    else yFlag = false;
                    if (endZ > playerZ) z1 = (double) playerZ + 1.0D;
                    else if (endZ < playerZ) z1 = playerZ;
                    else zFlag = false;
                    double x2 = endVector.x - tempVector.x;
                    double y2 = endVector.y - tempVector.y;
                    double z2 = endVector.z - tempVector.z;
                    double x3 = 999.0D;
                    double y3 = 999.0D;
                    double z3 = 999.0D;
                    if (xFlag) x3 = (x1 - tempVector.x) / x2;
                    if (yFlag) y3 = (y1 - tempVector.y) / y2;
                    if (zFlag) z3 = (z1 - tempVector.z) / z2;
                    if (x3 == -0.0D) x3 = -1.0E-4D;
                    if (y3 == -0.0D) y3 = -1.0E-4D;
                    if (z3 == -0.0D) z3 = -1.0E-4D;
                    EnumFacing enumfacing;
                    if (x3 < y3 && x3 < z3) {
                        enumfacing = endX > playerX ? EnumFacing.WEST : EnumFacing.EAST;
                        tempVector = new Vec3d(x1, tempVector.y + y2 * x3, tempVector.z + z2 * x3);
                    } else if (y3 < z3) {
                        enumfacing = endY > playerY ? EnumFacing.DOWN : EnumFacing.UP;
                        tempVector = new Vec3d(tempVector.x + x2 * y3, y1, tempVector.z + z2 * y3);
                    } else {
                        enumfacing = endZ > playerZ ? EnumFacing.NORTH : EnumFacing.SOUTH;
                        tempVector = new Vec3d(tempVector.x + x2 * z3, tempVector.y + y2 * z3, z1);
                    }
                    playerX = MathHelper.floor(tempVector.x) - (enumfacing == EnumFacing.EAST ? 1 : 0);
                    playerY = MathHelper.floor(tempVector.y) - (enumfacing == EnumFacing.UP ? 1 : 0);
                    playerZ = MathHelper.floor(tempVector.z) - (enumfacing == EnumFacing.SOUTH ? 1 : 0);
                    blockpos = new BlockPos(playerX, playerY, playerZ);
                    IBlockState state1 = worldIn.getBlockState(blockpos);
                    Block block1 = state1.getBlock();
                    if (block1.canCollideCheck(state1, false)) {
                        RayTraceResult result = state1.collisionRayTrace(worldIn, blockpos, tempVector, endVector);
                        if (Objects.nonNull(result)) return result;
                    }
                }
            }
        }
        return null;
    }

    private static MethodHandle getHandle(
            Lookup lookup, Class<?> clazz, String name, String srgName, Class<?>... args)
            throws ReflectiveOperationException {
        Method method;
        try {
            method = clazz.getDeclaredMethod(srgName, args);
        } catch(Exception ignored) {
            method = clazz.getDeclaredMethod(name, args);
        }
        method.setAccessible(true);
        return lookup.unreflect(method);
    }

    private static final MethodHandle captureCurrentPosition;
    private static void captureCurrentPosition(NetHandlerPlayServer connection) {
        try {
            captureCurrentPosition.invoke(connection);
        } catch(Throwable e) {
            throw new RuntimeException(e);
        }
    }
    static {
        try {
            Lookup lookup = MethodHandles.lookup();
            captureCurrentPosition = getHandle(lookup, NetHandlerPlayServer.class,"captureCurrentPosition","func_184342_d");
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void teleport(EntityPlayer playerIn, BlockPos tppos) {
        if (playerIn instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) playerIn;
            player.connection.setPlayerLocation(tppos.getX(), tppos.getY(), tppos.getZ(), playerIn.cameraYaw, playerIn.cameraPitch, EnumSet.noneOf(SPacketPlayerPosLook.EnumFlags.class));
            // Fix for https://bugs.mojang.com/browse/MC-98153. See this comment: https://bugs.mojang.com/browse/MC-98153#comment-411524
            captureCurrentPosition(player.connection);
        } else {
            playerIn.setLocationAndAngles(tppos.getX(), tppos.getY(), tppos.getZ(), playerIn.cameraYaw, playerIn.cameraPitch);
        }
    }

    public static List<ItemStack> getToolsForModifier(Modifier mod) {
        ArrayList<ItemStack> list = new ArrayList<>();
        if ((mod.getAllowedTools() & ARMOR) == ARMOR) {
            list.add(new ItemStack(ModItems.forceHelmet, 1));
            list.add(new ItemStack(ModItems.forceChest, 1));
            list.add(new ItemStack(ModItems.forceLegs, 1));
            list.add(new ItemStack(ModItems.forceBoots, 1));
        }
        if ((mod.getAllowedTools() & PICKAXE) == PICKAXE) {
            list.add(new ItemStack(ModItems.forcePickaxe, 1));
        }
        if ((mod.getAllowedTools() & AXE) == AXE) {
            list.add(new ItemStack(ModItems.forceAxe, 1));
        }
        if ((mod.getAllowedTools() & SHOVEL) == SHOVEL) {
            list.add(new ItemStack(ModItems.forceShovel, 1));
        }
        if ((mod.getAllowedTools() & ROD) == ROD) {
            list.add(new ItemStack(ModItems.forceRod, 1));
        }
        if ((mod.getAllowedTools() & SHEARS) == SHEARS) {
            list.add(new ItemStack(ModItems.forceShears, 1));
        }
        if ((mod.getAllowedTools() & SWORD) == SWORD) {
            list.add(new ItemStack(ModItems.forceSword, 1));
        }
        if ((mod.getAllowedTools() & BOW) == BOW) {
            list.add(new ItemStack(ModItems.forceBow, 1));
        }
        for (ItemStack stack : list) {
            if (stack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
                stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).setModifier(mod.getId(), mod.getMaxLevels());
            }
        }
        return list;
    }

    public static List<String> getToolNames(Modifier mod) {
        ArrayList<String> names =  new ArrayList<>();
        if ((mod.getAllowedTools() & ARMOR) == ARMOR) {
            names.add("Armor");
        }
        if ((mod.getAllowedTools() & PICKAXE) == PICKAXE) {
            names.add("Pickaxe");
        }
        if ((mod.getAllowedTools() & AXE) == AXE) {
            names.add("Axe");
        }
        if ((mod.getAllowedTools() & SHOVEL) == SHOVEL) {
            names.add("Shovel");
        }
        if ((mod.getAllowedTools() & ROD) == ROD) {
            names.add("Rod");
        }
        if ((mod.getAllowedTools() & SHEARS) == SHEARS) {
            names.add("Shears");
        }
        if ((mod.getAllowedTools() & SWORD) == SWORD) {
            names.add("Sword");
        }
        if ((mod.getAllowedTools() & BOW) == BOW) {
            names.add("Bow");
        }
        return names;
    }
}
