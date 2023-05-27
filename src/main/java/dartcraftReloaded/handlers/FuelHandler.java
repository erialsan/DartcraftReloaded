package dartcraftReloaded.handlers;

import dartcraftReloaded.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler {
    @Override
    public int getBurnTime(ItemStack fuel) {

        //100 per each item
        //Smelts 10 items
        if(fuel.getItem() == ModItems.goldenPowerSource){
            return 2000;
        }
        else
            return 0;
    }
}
