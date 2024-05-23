package dartcraftReloaded.items.tools;

import dartcraftReloaded.Constants;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.capablilities.Modifiable.IModifiableTool;
import dartcraftReloaded.capablilities.Modifiable.ModifiableProvider;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.util.DartUtils;
import com.google.common.collect.Lists;
import gnu.trove.set.hash.THashSet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import static dartcraftReloaded.util.DartUtils.isLog;


public class ItemForceAxe extends ItemAxe implements IModifiableTool {

    public ItemForceAxe() {
        super(DartcraftReloaded.forceToolMaterial, 8.0F, 8.0F);
        this.setRegistryName(Constants.FORCE_AXE);
        this.setTranslationKey(Constants.FORCE_AXE);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        this.attackSpeed = -2.0F;
        this.attackDamage = 8.0F;
    }

    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, Constants.FORCE_AXE);
    }




    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        if (stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).hasModifier(Constants.LUMBERJACK)) {
            if (DartUtils.isTree(player.getEntityWorld(), pos)) {
                return fellTree(stack, pos, player);
            }
        }
        return false;
    }


    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null))
            return new ModifiableProvider(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        else
            return null;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        damage(stack, attacker);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D) {
            damage(stack, entityLiving);
        }

        return true;
    }

    private void damage(ItemStack stack, EntityLivingBase entity) {
        IModifiable cap = stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        if (cap.hasModifier(Constants.IMPERVIOUS)) {
            stack.setItemDamage(0);
            return;
        }
        if (cap.hasModifier(Constants.REPAIR)) {
            if (Math.random() < .2*cap.getLevel(Constants.REPAIR)) {
                stack.setItemDamage(Math.max(stack.getItemDamage() - 1, 0));
                return;
            }
        }
        if (cap.hasModifier(Constants.STURDY)) {
            if (Math.random() > 1.0 / (1.0 + (double) cap.getLevel(Constants.STURDY))) {
                return;
            }
        }
        stack.damageItem(1, entity);
    }


    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        float speed = super.getDestroySpeed(stack, state);
        IModifiable cap = stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        if (cap.hasModifier(Constants.SPEED)) {
            //speed *= (1 + 0.075 * cap.getLevel(Constants.SPEED));
            speed *= cap.getLevel(Constants.SPEED);
        }
        return speed;
    }

    public static boolean fellTree(ItemStack stack, BlockPos pos, EntityPlayer player){
        if(player.getEntityWorld().isRemote)
            return true;

        MinecraftForge.EVENT_BUS.register(new TreeChopTask(stack, pos, player, 10));
        return true;
    }

    @Override
    public long getTool() {
        return Constants.AXE;
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

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> lores, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, lores, flagIn);
        stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).addText(lores);
    }

}
