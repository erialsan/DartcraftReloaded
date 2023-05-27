package dartcraftReloaded.items;

import dartcraftReloaded.DartcraftReloaded;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by BURN447 on 2/4/2018.
 */
public class ItemBase extends Item {

    protected String name;
    protected String oreName;
    public ItemBase(String name) {
        this.name = name;
        this.oreName = name;
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(DartcraftReloaded.creativeTab);
    }

    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}
