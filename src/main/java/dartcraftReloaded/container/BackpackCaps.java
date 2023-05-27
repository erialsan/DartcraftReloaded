package dartcraftReloaded.container;

import dartcraftReloaded.container.BackpackItemStackHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BackpackCaps implements ICapabilitySerializable<NBTBase> {
    private ItemStack stack;
    private int size;
    IItemHandler inventory;

    public BackpackCaps(int size, ItemStack stack) {
        this.stack = stack;
        this.size = size;
        inventory = new BackpackItemStackHandler(size);
    }

    @Override
    public boolean hasCapability(Capability<?> cap, EnumFacing facing) {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
        return null;
    }

    @Override
    public NBTBase serializeNBT() {
        NBTTagCompound sNBT = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        sNBT.setTag("inv", ((BackpackItemStackHandler) inventory).serializeNBT());
        stack.setTagCompound(sNBT);

        NBTTagCompound nbt = new NBTTagCompound();
        NBTBase i = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inventory, null);
        nbt.setTag("Inventory", i == null ? new NBTTagList() : i);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        if (nbt instanceof NBTTagCompound) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, null, ((NBTTagCompound) nbt).getTag("Inventory"));
        } else {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, null, nbt);
        }
    }
}