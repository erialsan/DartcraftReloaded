package dartcraftReloaded.container.Slot;

import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.tileEntity.TileEntityInfuser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotForceBook extends SlotItemHandler {

    private TileEntityInfuser te;
    public SlotForceBook(TileEntityInfuser te, IItemHandler handler, int index, int posX, int posY){
        super(handler, index, posX, posY);
        this.te = te;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() == ModItems.upgradeTome && !getHasStack() && te.processTime == -1;
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
