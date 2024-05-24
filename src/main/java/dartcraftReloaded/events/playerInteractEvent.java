package dartcraftReloaded.events;

import dartcraftReloaded.Constants;
import dartcraftReloaded.handlers.AsmHandler;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.handlers.InfusedActionHandler;
import dartcraftReloaded.items.ItemFilledJar;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.items.tools.ItemForceRod;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class playerInteractEvent {

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.EntityInteract event) {
        if (!event.getWorld().isRemote && event.getEntityPlayer() != null & event.getTarget().isNonBoss() && !(event.getTarget() instanceof EntityPlayer) && event.getEntityPlayer().isSneaking()) {
            ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
            if (stack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
                if (stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).hasModifier(Constants.HOLDING)) {
                    if (event.getTarget() instanceof EntityLiving) {
                        ItemStack filledJar = new ItemStack(ModItems.filledJar);
                        ItemFilledJar.addTargetToLasso(filledJar, (EntityLiving) event.getTarget());
                        EntityItem droppedItem = new EntityItem(event.getWorld(), event.getTarget().posX, event.getTarget().posY + 1, event.getTarget().posZ, filledJar);
                        event.getWorld().removeEntity(event.getTarget());
                        event.setCanceled(true);
                        event.getWorld().spawnEntity(droppedItem);
                        InfusedActionHandler.damage(stack, event.getEntityPlayer());
                        event.getEntityPlayer().inventory.markDirty();
                    }
                }
            }
        }
    }
}
