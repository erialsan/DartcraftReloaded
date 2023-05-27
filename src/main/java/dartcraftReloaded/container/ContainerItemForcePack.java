package dartcraftReloaded.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerItemForcePack extends Container {
    private ItemStack item;
    private InventoryPlayer playerInventory;
    public int slotCount;
    public IItemHandler itemHandler;
    public int slot;
    public String itemKey;
    public ContainerItemForcePack(ItemStack item, InventoryPlayer playerInventory, int slot) {
        super();
        this.slot = slot;
        itemHandler = item.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        slotCount = itemHandler.getSlots();
        itemKey = item.getItem().getUnlocalizedNameInefficiently(item);
        addMySlots(item);
        addPlayerSlots(playerInventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if (slot == -1) return true;
        if (slot == -2) return !player.getHeldItemMainhand().isEmpty();
        return !player.inventory.getStackInSlot(slot).isEmpty();
    }

    @Override
    public ItemStack slotClick(int slotid, int dragtype, ClickType clickType, EntityPlayer player) {
        if (slotid >= 0) {
            if (slot == -2) {
                if (getSlot(slotid).getStack() == player.getHeldItemMainhand()) return ItemStack.EMPTY;
            } else if (slot > -1) {
                if (getSlot(slotid).getStack() == player.inventory.getStackInSlot(slot)) return ItemStack.EMPTY;
            }
        }
        if (slotid >= 0) getSlot(slotid).inventory.markDirty();
        return super.slotClick(slotid, dragtype, clickType, player);

    }

    public void addPlayerSlots(InventoryPlayer playerInventory) {
        int originX = 8;
        int originY;
        if (slotCount == 8) originY = 54;
        else if (slotCount == 16) originY = 72;
        else if (slotCount == 24) originY = 90;
        else if (slotCount == 32) originY = 108;
        else originY = 126;


        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int x = originX + col * 18;
                int y = originY + row * 18;
                this.addSlotToContainer(new Slot(playerInventory, (col + row * 9) + 9, x, y));
            }
        }

        for (int row = 0; row < 9; row++) {
            int x = originX + row * 18;
            int y = originY + 58;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    public void addMySlots(ItemStack item) {
        if (itemHandler == null) return;
        int cols = 8;
        int rows = slotCount / cols;
        int slotIndex = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = 17 + col * 18;
                int y = 20 + row * 18;
                addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y));
                slotIndex++;
                if (slotIndex >= slotCount) break;
            }
        }
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            int bagSlotCount = inventorySlots.size() - player.inventory.mainInventory.size();
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < bagSlotCount) {
                if (!this.mergeItemStack(itemstack1, bagSlotCount, this.inventorySlots.size(), true)) return ItemStack.EMPTY;
            } else if (!this.mergeItemStack(itemstack1, 0, bagSlotCount, false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();
        }
        return itemstack;
    }

}
