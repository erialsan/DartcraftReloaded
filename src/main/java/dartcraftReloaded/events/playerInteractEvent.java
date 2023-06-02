package dartcraftReloaded.events;

import dartcraftReloaded.Constants;
import dartcraftReloaded.handlers.AsmHandler;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.items.ItemFilledJar;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.items.tools.ItemForceRod;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class playerInteractEvent {

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.EntityInteract event) {
        if (!event.getWorld().isRemote && event.getEntityPlayer() != null & event.getTarget().isNonBoss() && !(event.getTarget() instanceof EntityPlayer)) {
            ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
            if (stack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
                if (stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).hasModifier(Constants.HOLDING)) {
                    ItemStack jars = null;
                    for (ItemStack i : event.getEntityPlayer().inventory.mainInventory) {
                        if (i.getItem() == ModItems.emptyJar) jars = i;
                    }
                    if (event.getEntityPlayer().isCreative() || (jars != null && jars.getCount() > 0)) {
                        if (event.getTarget() instanceof EntityLiving) {
                            ItemStack filledJar = new ItemStack(ModItems.filledJar);
                            if (!event.getEntityPlayer().isCreative() && jars != null) jars.shrink(1);
                            ItemFilledJar.addTargetToLasso(filledJar, (EntityLiving) event.getTarget());
                            event.getWorld().removeEntity(event.getTarget());
                            event.setCanceled(true);
                            AsmHandler.giveItemToPlayerSilent(event.getEntityPlayer(), filledJar);
                            ItemForceRod.damage(stack, event.getEntityPlayer());
                            event.getEntityPlayer().inventory.markDirty();
                        }
                    }
                }
            }
        }
    }
}
