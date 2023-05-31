package dartcraftReloaded.events;

import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.handlers.AsmHandler;
import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
public class livingDropsEvent {

    @SubscribeEvent
    public void livingDropsEvent(LivingDropsEvent event){
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            IModifiable cap = player.getHeldItemMainhand().getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
            if (cap != null) {
                if (cap.hasModifier(Constants.DIRECT)) {
                    for (EntityItem drop : event.getDrops()) {
                        AsmHandler.giveItemToPlayerSilent(player, drop.getItem());
                    }
                    event.setCanceled(true);
                }
                if (cap.hasModifier(Constants.EXPERIENCE)) {
                    player.world.spawnEntity(new EntityXPOrb(player.world, event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, cap.getLevel(Constants.EXPERIENCE) * 5));
                }
            }
        }
    }
}