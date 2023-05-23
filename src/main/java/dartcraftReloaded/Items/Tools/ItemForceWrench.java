package dartcraftReloaded.Items.Tools;

import dartcraftReloaded.Items.ItemBase;
import dartcraftReloaded.capablilities.ForceWrench.ForceWrenchProvider;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.Handlers.DCRCapabilityHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by BURN447 on 3/6/2018.
 */
public class ItemForceWrench extends ItemBase {

    public ItemForceWrench(String name){
        super(name);
        setTranslationKey(name);
        setCreativeTab(DartcraftReloaded.creativeTab);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (player.getHeldItem(hand).getItem() instanceof ItemForceWrench) {
            ItemStack heldWrench = player.getHeldItem(hand);

            if (world.getTileEntity(pos) instanceof TileEntity && !heldWrench.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, null).canStoreBlock()) {
                return serializeNBT(world, pos, player, hand);
            } else if(heldWrench.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, null).canStoreBlock())
                placeBlockFromWrench(world, pos, player, hand, side);
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, null))
            return new ForceWrenchProvider(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, null);
        else
            return null;
    }

    private EnumActionResult serializeNBT(World world, BlockPos pos, EntityPlayer player, EnumHand hand){
        ItemStack heldWrench = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);

        if(heldWrench.hasCapability(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, null)) {
            String blockName = state.getBlock().getLocalizedName();
            TileEntity tileEntity = world.getTileEntity(pos);
            NBTTagCompound nbt = new NBTTagCompound();

            if(tileEntity != null){
                tileEntity.writeToNBT(nbt);
                heldWrench.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, null).storeBlockNBT(nbt);
                heldWrench.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, null).storeBlockState(world.getBlockState(pos));
                heldWrench.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, null).setBlockName(blockName);
                world.removeTileEntity(pos);
                world.setBlockToAir(pos);
                world.markBlockRangeForRenderUpdate(pos, pos);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    private EnumActionResult placeBlockFromWrench(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side) {
        ItemStack heldWrench = player.getHeldItem(hand);
        if(heldWrench.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, null).getStoredBlockState() != null) {
            NBTTagCompound tileCmp = heldWrench.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, null).getStoredBlockNBT();
            IBlockState state = heldWrench.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, null).getStoredBlockState();
            TileEntity te = TileEntity.create(world, tileCmp);
            te.setPos(pos.offset(side));
            world.setBlockState(pos.offset(side), state);
            world.setTileEntity(pos.offset(side), te);
            heldWrench.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, null).clearBlockStorage();
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(stack.hasCapability(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, null)){
            if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, null).getStoredName() != null){
                tooltip.add("Stored: " + stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, null).getStoredName());
            }
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
