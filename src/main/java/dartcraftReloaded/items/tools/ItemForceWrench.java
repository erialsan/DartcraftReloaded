package dartcraftReloaded.items.tools;

import dartcraftReloaded.Constants;
import dartcraftReloaded.handlers.SoundHandler;
import dartcraftReloaded.items.ItemBase;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.capablilities.ItemTE.IItemTE;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by BURN447 on 3/6/2018.
 */
public class ItemForceWrench extends ItemBase {

    public ItemForceWrench(){
        super(Constants.FORCE_WRENCH);
        setTranslationKey(Constants.FORCE_WRENCH);
        setCreativeTab(DartcraftReloaded.creativeTab);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (player.getHeldItem(hand).getItem() instanceof ItemForceWrench) {
            if (world.getTileEntity(pos) instanceof TileEntity) {
                if (world.isRemote) {
                    world.playSound(player, pos, SoundHandler.SPARKLE, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    return EnumActionResult.SUCCESS;
                } else {
                    ItemStack te = new ItemStack(ModItems.itemTE);
                    IItemTE cap = te.getCapability(CapabilityHandler.CAPABILITY_TE, null);
                    te.setStackDisplayName("Packaged " + cap.getBlockName());
                    if (writeNBT(world, pos, cap)) {
                        EntityItem droppedItem = new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), te);
                        world.spawnEntity(droppedItem);
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        }
        return EnumActionResult.PASS;
    }

    private boolean writeNBT(World world, BlockPos pos, IItemTE cap){
        IBlockState state = world.getBlockState(pos);
        String blockName = state.getBlock().getLocalizedName();
        TileEntity tileEntity = world.getTileEntity(pos);
        NBTTagCompound nbt = new NBTTagCompound();

        if(tileEntity != null){
            tileEntity.writeToNBT(nbt);
            world.removeTileEntity(pos);
            world.setBlockToAir(pos);
            world.markBlockRangeForRenderUpdate(pos, pos);
            cap.setNBT(nbt);
            cap.setBlockName(blockName);
            cap.setBlockState(state);
            world.notifyBlockUpdate(pos, state, Blocks.AIR.getDefaultState(), 3);
            return true;
        }
        return false;
    }
}
