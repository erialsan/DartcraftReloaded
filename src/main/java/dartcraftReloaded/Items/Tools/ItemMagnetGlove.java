package dartcraftReloaded.Items.Tools;

import dartcraftReloaded.Items.ItemBase;
import dartcraftReloaded.capablilities.Magnet.MagnetProvider;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.util.References;
import dartcraftReloaded.Handlers.DCRCapabilityHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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

    public ItemMagnetGlove(String name) {
        super(name);
        this.setMaxStackSize(1);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(DCRCapabilityHandler.CAPABILITY_MAGNET, null))
            return new MagnetProvider(DCRCapabilityHandler.CAPABILITY_MAGNET, null);
        else
            return null;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(playerIn.isSneaking()) {
            if(playerIn.getHeldItem(handIn).hasCapability(DCRCapabilityHandler.CAPABILITY_MAGNET, null)) {
                if (this.getDamage(playerIn.getHeldItem(handIn)) == 1) {
                    playerIn.getHeldItem(handIn).getCapability(DCRCapabilityHandler.CAPABILITY_MAGNET, null).deactivate();
                    this.setDamage(playerIn.getHeldItem(handIn), 0);
                } else {
                    playerIn.getHeldItem(handIn).getCapability(DCRCapabilityHandler.CAPABILITY_MAGNET, null).activate();
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
    //@Override
    public ModelResourceLocation getModelLocation(ItemStack stack) {
        if(stack.hasCapability(DCRCapabilityHandler.CAPABILITY_MAGNET, null)) {
            if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_MAGNET, null).isActivated()) {
                return new ModelResourceLocation(References.modId + ":magnetglove", "active");
            }
        }
        return new ModelResourceLocation(References.modId + ":magnetglove", "deactivated");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 1, name + "active");
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, name + "deactivated");
    }
}
