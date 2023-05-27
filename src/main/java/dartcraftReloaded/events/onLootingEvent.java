package dartcraftReloaded.events;

import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class onLootingEvent {

    @SubscribeEvent
    public void onLooting(LootingLevelEvent event) {
        if (event.getDamageSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getDamageSource().getTrueSource();
            if (player.getHeldItemMainhand().hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
                IModifiable cap = player.getHeldItemMainhand().getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
                if (cap.hasModifier(Constants.LUCK)) {
                    event.setLootingLevel(event.getLootingLevel() + cap.getLevel(Constants.LUCK));
                }
            }
        }
    }
}
