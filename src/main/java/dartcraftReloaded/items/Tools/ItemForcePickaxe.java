package dartcraftReloaded.items.Tools;

import dartcraftReloaded.DartcraftReloaded;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

/**
 * Created by BURN447 on 5/13/2018.
 */
public class ItemForcePickaxe extends ItemPickaxe {

    private static String name;

    public ItemForcePickaxe(String name) {
        super(DartcraftReloaded.forceToolMaterial);
        this.setRegistryName(name);
        this.setTranslationKey(name);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        this.name = name;
    }

    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, name);
    }

/*
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(DCRCapabilityHandler.CAPABILITY_TOOLMOD, null))
            return new ToolModProvider(DCRCapabilityHandler.CAPABILITY_TOOLMOD, null);
        else
            return null;
    }
*/

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}