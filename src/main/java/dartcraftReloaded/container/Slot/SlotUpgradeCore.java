package dartcraftReloaded.container.Slot;

import dartcraftReloaded.Items.ItemUpgradeCore;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotUpgradeCore extends Slot {

    public SlotUpgradeCore(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof ItemUpgradeCore;
    }
}
