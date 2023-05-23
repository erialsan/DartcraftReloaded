package dartcraftReloaded.Items;

import dartcraftReloaded.capablilities.ToolModifier.ToolModProvider;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.Handlers.DCRCapabilityHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by BURN447 on 3/4/2018.
 */
public class ItemArmor extends net.minecraft.item.ItemArmor {

    private String name;

    public ItemArmor(ArmorMaterial material, EntityEquipmentSlot slot, String name) {
        super(material, 0, slot);
        setRegistryName(name);
        setTranslationKey(name);
        this.name = name;
        setCreativeTab(DartcraftReloaded.creativeTab);
    }

    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, name);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(DCRCapabilityHandler.CAPABILITY_TOOLMOD, null))
            return new ToolModProvider(DCRCapabilityHandler.CAPABILITY_TOOLMOD, null);
        else
            return null;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}
