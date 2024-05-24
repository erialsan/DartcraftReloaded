package dartcraftReloaded.items.tools;

import dartcraftReloaded.Constants;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.capablilities.Modifiable.IModifiableTool;
import dartcraftReloaded.capablilities.Modifiable.ModifiableProvider;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.handlers.InfusedActionHandler;
import dartcraftReloaded.util.DartUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemForceShears extends ItemShears implements IModifiableTool {

    public ItemForceShears(){
        this.setRegistryName(Constants.FORCE_SHEARS);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        this.setTranslationKey(Constants.FORCE_SHEARS);
        setMaxDamage(768);
    }


    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        return InfusedActionHandler.shearInteractionForEntity(itemstack, player, entity, hand);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null))
            return new ModifiableProvider();
        else
            return null;
    }


    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, Constants.FORCE_SHEARS);
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> lores, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, lores, flagIn);
        stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).addText(lores);
    }

    @Override
    public long getTool() {
        return Constants.SHEARS;
    }
}
