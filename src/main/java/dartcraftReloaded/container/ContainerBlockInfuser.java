package dartcraftReloaded.container;

import dartcraftReloaded.container.Slot.SlotForceBook;
import dartcraftReloaded.container.Slot.SlotForceGems;
import dartcraftReloaded.container.Slot.SlotForceTools;
import dartcraftReloaded.container.Slot.SlotModifier;
import dartcraftReloaded.tileEntity.TileEntityInfuser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ContainerBlockInfuser extends Container {

    TileEntityInfuser te;

    public ContainerBlockInfuser(IInventory playerInv, TileEntityInfuser te) {

        //Modifier Slots
        this.addSlotToContainer(new SlotModifier(te, te.handler, 0, 80, 20));
        this.addSlotToContainer(new SlotModifier(te, te.handler, 1, 104, 32));
        this.addSlotToContainer(new SlotModifier(te, te.handler, 2, 116, 57));
        this.addSlotToContainer(new SlotModifier(te, te.handler, 3, 104, 81));
        this.addSlotToContainer(new SlotModifier(te, te.handler, 4, 80, 93));
        this.addSlotToContainer(new SlotModifier(te, te.handler, 5, 56, 81));
        this.addSlotToContainer(new SlotModifier(te, te.handler, 6, 44, 57));
        this.addSlotToContainer(new SlotModifier(te, te.handler, 7, 56, 32));

        //Tools Slot
        this.addSlotToContainer(new SlotForceTools(te, te.handler, 8, 80, 57));

        //Force Gem Slot
        this.addSlotToContainer(new SlotForceGems(te.handler, 9, 10, 35));

        //Book slot
        this.addSlotToContainer(new SlotForceBook(te, te.handler, 10, 10, 10));

        int xPos = 8;
        int yPos = 127;

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
            }
        }

        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInv, x, xPos + x * 18, yPos + 58));
        }

        this.te = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return !playerIn.isSpectator();
    }

    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 11)
            {
                if (!this.mergeItemStack(itemstack1, 11, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 11, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection)
    {
        boolean flag = false;
        int i = startIndex;

        if (reverseDirection)
        {
            i = endIndex - 1;
        }

        if (stack.isStackable())
        {
            while (!stack.isEmpty())
            {
                if (reverseDirection)
                {
                    if (i < startIndex)
                    {
                        break;
                    }
                }
                else if (i >= endIndex)
                {
                    break;
                }

                Slot slot = this.inventorySlots.get(i);
                if (slot.isEnabled()) {
                    ItemStack itemstack = slot.getStack();

                    if (!itemstack.isEmpty() && itemstack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemstack)) {
                        int j = itemstack.getCount() + stack.getCount();
                        int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());

                        if (j <= maxSize) {
                            stack.setCount(0);
                            itemstack.setCount(j);
                            slot.onSlotChanged();
                            flag = true;
                        } else if (itemstack.getCount() < maxSize) {
                            stack.shrink(maxSize - itemstack.getCount());
                            itemstack.setCount(maxSize);
                            slot.onSlotChanged();
                            flag = true;
                        }
                    }
                }
                if (reverseDirection)
                {
                    --i;
                }
                else
                {
                    ++i;
                }
            }
        }

        if (!stack.isEmpty())
        {
            if (reverseDirection)
            {
                i = endIndex - 1;
            }
            else
            {
                i = startIndex;
            }

            while (true)
            {
                if (reverseDirection)
                {
                    if (i < startIndex)
                    {
                        break;
                    }
                }
                else if (i >= endIndex)
                {
                    break;
                }

                Slot slot1 = this.inventorySlots.get(i);
                if (slot1.isEnabled()) {
                    ItemStack itemstack1 = slot1.getStack();

                    if (itemstack1.isEmpty() && slot1.isItemValid(stack)) {
                        if (stack.getCount() > slot1.getSlotStackLimit()) {
                            slot1.putStack(stack.splitStack(slot1.getSlotStackLimit()));
                        } else {
                            slot1.putStack(stack.splitStack(stack.getCount()));
                        }

                        slot1.onSlotChanged();
                        flag = true;
                        break;
                    }
                }
                if (reverseDirection)
                {
                    --i;
                }
                else
                {
                    ++i;
                }
            }
        }

        return flag;
    }

}