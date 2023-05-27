package dartcraftReloaded.container;

import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class BackpackItemStackHandler extends ItemStackHandler {
    public BackpackItemStackHandler(int size) {
        super(size);
    }
    @Override
    public ItemStack insertItem(int slot,  @Nonnull ItemStack stack, boolean simulate) {
        if (stack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN) || stack.getItem() instanceof ItemShulkerBox) {
            return stack;
        }
        return super.insertItem(slot, stack, simulate);
    }
}
