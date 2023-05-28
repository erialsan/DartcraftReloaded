package dartcraftReloaded.items;

import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.handlers.GUIHandler;
import dartcraftReloaded.container.BackpackCaps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class ItemForcePack extends ItemBase {
    private int size;
    public ItemForcePack(String name, int size) {
        super(name);
        this.size = size;
    }

    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
            if (handler instanceof ItemStackHandler) {
                nbt.setTag("inv", ((ItemStackHandler) handler).serializeNBT());
                return nbt;
            }
        }
        return stack.getTagCompound();
    }

    @Override
    public void readNBTShareTag(ItemStack stack, NBTTagCompound nbt) {
        stack.setTagCompound(nbt);
        if (nbt != null) {
            if (nbt.hasKey("inv")) {
                IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if (handler instanceof ItemStackHandler) {
                    ((ItemStackHandler) handler).deserializeNBT(nbt.getCompoundTag("inv"));
                }
            }
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new BackpackCaps(size, stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote) {
            player.openGui(DartcraftReloaded.instance, GUIHandler.PACK, world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItemMainhand());
    }
}
