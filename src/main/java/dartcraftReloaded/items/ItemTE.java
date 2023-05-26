package dartcraftReloaded.items;

import dartcraftReloaded.Constants;
import dartcraftReloaded.handlers.DCRCapabilityHandler;
import dartcraftReloaded.capablilities.ItemTE.IItemTE;
import dartcraftReloaded.capablilities.ItemTE.ItemTEProvider;
import dartcraftReloaded.handlers.DCRSoundHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
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

public class ItemTE extends ItemBase {
    public ItemTE() {
        super(Constants.ITEM_TE);
        setMaxStackSize(1);
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (worldIn.isRemote) {
            IItemTE cap = stack.getCapability(DCRCapabilityHandler.CAPABILITY_TE, null);
            if (cap != null) {
                stack.setStackDisplayName("Packaged "+cap.getBlockName());
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        IItemTE cap = stack.getCapability(DCRCapabilityHandler.CAPABILITY_TE, null);
        if (cap != null) {
            stack.setStackDisplayName("Packaged "+cap.getBlockName());
        }
        tooltip.add("Right-click to place");
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if (!stack.hasCapability(DCRCapabilityHandler.CAPABILITY_TE, null))
            return new ItemTEProvider(DCRCapabilityHandler.CAPABILITY_TE, null);
        else
            return null;
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (player.getHeldItem(hand).getItem() instanceof ItemTE) {
            if (placeBlockFromWrench(world, pos, player, hand, side)) {
                player.getHeldItem(hand).shrink(1);
                if (world.isRemote) {
                    Minecraft.getMinecraft().player.playSound(DCRSoundHandler.SPARKLE, 1.0f, 1.0f);
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }

    private boolean placeBlockFromWrench(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side) {
        ItemStack item = player.getHeldItem(hand);
        if (item.getCapability(DCRCapabilityHandler.CAPABILITY_TE, null) != null) {
            IItemTE cap = item.getCapability(DCRCapabilityHandler.CAPABILITY_TE, null);
            if (cap != null && cap.getBlockName() != null && cap.getBlockState() != null && cap.getNBT() != null) {
                IBlockState old = world.getBlockState(pos.offset(side));
                TileEntity te = TileEntity.create(world, cap.getNBT());
                te.setPos(pos.offset(side));
                world.setBlockState(pos.offset(side), cap.getBlockState());
                world.setTileEntity(pos.offset(side), te);
                world.notifyBlockUpdate(pos.offset(side), old, cap.getBlockState(), 3);
                return true;
            }
        }
        return false;
    }
}
