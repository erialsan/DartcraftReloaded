package dartcraftReloaded.items.tools;

import dartcraftReloaded.items.ItemBase;
import dartcraftReloaded.capablilities.Magnet.MagnetProvider;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.Constants;
import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import java.util.List;

public class ItemMagnetGlove extends ItemBase {

    public ItemMagnetGlove() {
        super(Constants.MAGNET_GLOVE);
        this.setMaxStackSize(1);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(CapabilityHandler.CAPABILITY_MAGNET, null))
            return new MagnetProvider(CapabilityHandler.CAPABILITY_MAGNET, null);
        else
            return null;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(playerIn.isSneaking()) {
            if(playerIn.getHeldItem(handIn).hasCapability(CapabilityHandler.CAPABILITY_MAGNET, null)) {
                if (this.getDamage(playerIn.getHeldItem(handIn)) == 1) {
                    playerIn.getHeldItem(handIn).getCapability(CapabilityHandler.CAPABILITY_MAGNET, null).deactivate();
                    this.setDamage(playerIn.getHeldItem(handIn), 0);
                } else {
                    playerIn.getHeldItem(handIn).getCapability(CapabilityHandler.CAPABILITY_MAGNET, null).activate();
                    this.setDamage(playerIn.getHeldItem(handIn), 1);
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(this.getDamage(stack) == 1) {
            tooltip.add("Active");
        }
        else {
            tooltip.add("Deactivated");
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 1, name + "active");
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, name + "deactivated");
    }
}
