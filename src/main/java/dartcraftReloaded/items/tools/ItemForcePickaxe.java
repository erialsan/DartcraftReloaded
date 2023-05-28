package dartcraftReloaded.items.tools;

import dartcraftReloaded.Constants;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.capablilities.Modifiable.IModifiableTool;
import dartcraftReloaded.capablilities.Modifiable.ModifiableProvider;
import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemForcePickaxe extends ItemPickaxe implements IModifiableTool {


    public ItemForcePickaxe() {
        super(DartcraftReloaded.forceToolMaterial);
        this.setRegistryName(Constants.FORCE_PICKAXE);
        this.setTranslationKey(Constants.FORCE_PICKAXE);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
    }

    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, Constants.FORCE_PICKAXE);
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
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> lores, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, lores, flagIn);
        stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).addText(lores);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        float speed = super.getDestroySpeed(stack, state);
        IModifiable cap = stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        if (cap.hasModifier(Constants.SPEED)) {
            speed *= (1 + 0.075 * cap.getLevel(Constants.SPEED));
        }
        return speed;
    }

    @Override
    public long getTool() {
        return Constants.PICKAXE;
    }
}