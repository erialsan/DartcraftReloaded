package dartcraftReloaded.client;

import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.Constants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class tabDartcraft extends CreativeTabs {

    public tabDartcraft(){
        super(Constants.modId);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.gemForceGem);
    }
}
