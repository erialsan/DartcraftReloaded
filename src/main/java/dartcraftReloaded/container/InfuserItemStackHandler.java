package dartcraftReloaded.container;

import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.tileEntity.TileEntityInfuser;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class InfuserItemStackHandler extends ItemStackHandler {

    private TileEntityInfuser tile;

    public InfuserItemStackHandler(int size, TileEntityInfuser tile) {
        super(size);
        this.tile = tile;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot > 0 && slot < 8) {
            // modifier slot
            return tile.isModifierValid(stack) && getStackInSlot(slot).isEmpty() && tile.processTime == -1;
        } else if (slot == 8) {
            // tools
            return tile.isStackValid(stack) && getStackInSlot(slot).isEmpty();
        } else if (slot == 9) {
            // force gems
            return stack.getItem() == ModItems.gemForceGem;
        } else if (slot == 10) {
            // book
            return stack.getItem() == ModItems.upgradeTome && getStackInSlot(slot).isEmpty() && tile.processTime == -1;
        }
        return false;
    }
}
