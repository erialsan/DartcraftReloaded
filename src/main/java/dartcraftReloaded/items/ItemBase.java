package dartcraftReloaded.items;

import dartcraftReloaded.DartcraftReloaded;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBase extends Item {

    protected String name;
    protected String oreName;
    protected String tooltip1 = null;
    public ItemBase(String name) {
        this.name = name;
        this.oreName = name;
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(DartcraftReloaded.creativeTab);
    }

    public ItemBase(String name, String tooltip) {
        this.name = name;
        this.oreName = name;
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(DartcraftReloaded.creativeTab);
        this.tooltip1 = tooltip;
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

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (tooltip1 != null) tooltip.add(tooltip1);
    }
}
