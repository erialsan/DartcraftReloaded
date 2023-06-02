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

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return handleShiftClick(this, playerIn, index);
    }

    public static ItemStack handleShiftClick(Container container, EntityPlayer player, int slotIndex) {
        List<Slot> slots = container.inventorySlots;
        Slot sourceSlot = slots.get(slotIndex);
        ItemStack inputStack = sourceSlot.getStack();

        boolean sourceIsPlayer = sourceSlot.inventory == player.inventory;

        ItemStack copy = inputStack.copy();

        if (sourceIsPlayer) {
            if (!mergeStack(player.inventory, false, sourceSlot, slots, false)) {
                return ItemStack.EMPTY;
            } else {
                return copy;
            }
        } else {
            boolean isMachineOutput = !sourceSlot.isItemValid(inputStack);
            if (!mergeStack(player.inventory, true, sourceSlot, slots, !isMachineOutput)) {
                return ItemStack.EMPTY;
            } else {
                return copy;
            }
        }
    }
    private static boolean mergeStack(InventoryPlayer playerInv, boolean mergeIntoPlayer, Slot sourceSlot, List<Slot> slots, boolean reverse) {
        ItemStack sourceStack = sourceSlot.getStack();

        int originalSize = sourceStack.getCount();

        int len = slots.size();
        int idx;

        if (sourceStack.isStackable()) {
            idx = reverse ? len - 1 : 0;

            while (sourceStack.getCount() > 0 && (reverse ? idx >= 0 : idx < len)) {
                Slot targetSlot = slots.get(idx);
                if ((targetSlot.inventory == playerInv) == mergeIntoPlayer) {
                    ItemStack target = targetSlot.getStack();
                    if (ItemStack.areItemStacksEqual(sourceStack, target)) {
                        int targetMax = Math.min(targetSlot.getSlotStackLimit(), target.getMaxStackSize());
                        int toTransfer = Math.min(sourceStack.getCount(), targetMax - target.getCount());
                        if (toTransfer > 0) {
                            target.setCount(target.getCount() + toTransfer);
                            sourceStack.setCount(sourceStack.getCount() - toTransfer);
                            targetSlot.onSlotChanged();
                        }
                    }
                }

                if (reverse) {
                    idx--;
                } else {
                    idx++;
                }
            }
            if (sourceStack.isEmpty()) {
                sourceSlot.putStack(ItemStack.EMPTY);
                return true;
            }
        }

        // 2nd pass: try to put anything remaining into a free slot
        idx = reverse ? len - 1 : 0;
        while (reverse ? idx >= 0 : idx < len) {
            Slot targetSlot = slots.get(idx);
            if ((targetSlot.inventory == playerInv) == mergeIntoPlayer
                    && !targetSlot.getHasStack() && targetSlot.isItemValid(sourceStack)) {
                if (mergeIntoPlayer) {
                    targetSlot.putStack(sourceStack);
                    sourceSlot.putStack(ItemStack.EMPTY);
                } else {
                    ItemStack stack = sourceStack.copy();
                    stack.setCount(1);
                    targetSlot.putStack(stack);
                    sourceStack.shrink(1);
                    sourceSlot.putStack(sourceStack);
                    sourceSlot.onSlotChanged();
                    targetSlot.onSlotChanged();
                }

                return true;
            }

            if (reverse) {
                idx--;
            } else {
                idx++;
            }
        }

        // we had success in merging only a partial stack
        if (sourceStack.getCount() != originalSize) {
            sourceSlot.onSlotChanged();
            return true;
        }
        return false;
    }

}