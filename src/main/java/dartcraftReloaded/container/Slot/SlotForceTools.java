package dartcraftReloaded.container.Slot;

import dartcraftReloaded.tileEntity.TileEntityInfuser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotForceTools extends SlotItemHandler {
    private final TileEntityInfuser te;
    public SlotForceTools(TileEntityInfuser te, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.te = te;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return te.isStackValid(stack) && !getHasStack();
    }

    @Override
    public int getItemStackLimit(@Nonnull ItemStack stack) {
        return 1;
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        return super.canTakeStack(playerIn) && te.processTime == -1;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
