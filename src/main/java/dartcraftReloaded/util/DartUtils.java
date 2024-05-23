package dartcraftReloaded.util;

import dartcraftReloaded.handlers.PacketHandler;
import dartcraftReloaded.Constants;
import dartcraftReloaded.blocks.BlockForceLog;
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
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.ForgeEventFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Method;
import java.util.*;

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

}
