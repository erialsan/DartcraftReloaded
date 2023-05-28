package dartcraftReloaded.container.Slot;

import dartcraftReloaded.tileEntity.TileEntityInfuser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotModifier extends SlotItemHandler {
    private final TileEntityInfuser te;

    public SlotModifier(TileEntityInfuser te, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.te = te;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return te.isModifierValid(stack);
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
    @SideOnly(Side.CLIENT)
    public boolean isEnabled() {
        return getSlotIndex() < te.getBookLevel();
    }
}
