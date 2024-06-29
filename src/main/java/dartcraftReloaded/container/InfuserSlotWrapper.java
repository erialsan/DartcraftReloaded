package dartcraftReloaded.container;

import dartcraftReloaded.items.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class InfuserSlotWrapper extends ItemStackHandler {

    private InfuserItemStackHandler handler;

    public InfuserSlotWrapper(InfuserItemStackHandler handler)
    {
        this.handler = handler;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack)
    {
        handler.setStackInSlot(9, stack);
    }

    @Override
    public int getSlots()
    {
        return 1;
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot)
    {
        return handler.getStackInSlot(9);
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if (stack.getItem() != ModItems.gemForceGem) return stack;
        return handler.insertItem(9, stack, simulate);
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot)
    {
        return 64;
    }

    protected int getStackLimit(int slot, @Nonnull ItemStack stack)
    {
        return 64;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack)
    {
        return stack.getItem() == ModItems.gemForceGem;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        return handler.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        handler.deserializeNBT(nbt);
    }
}
