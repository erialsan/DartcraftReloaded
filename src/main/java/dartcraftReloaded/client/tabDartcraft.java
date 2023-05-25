package dartcraftReloaded.client;

import dartcraftReloaded.Items.ModItems;
import dartcraftReloaded.Constants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * Created by BURN447 on 2/4/2018.
 */
public class tabDartcraft extends CreativeTabs {

    public tabDartcraft(){
        super(Constants.modId);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.gemForceGem);
    }
}
