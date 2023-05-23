package dartcraftReloaded.Events;

import dartcraftReloaded.Handlers.DCRCapabilityHandler;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class onLootingEvent {

    @SubscribeEvent
    public void onLooting(LootingLevelEvent event) {
        int level = event.getLootingLevel();

        if(event.getDamageSource().getTrueSource().hasCapability(DCRCapabilityHandler.CAPABILITY_PLAYERMOD, null)) {
            level += event.getDamageSource().getTrueSource().getCapability(DCRCapabilityHandler.CAPABILITY_PLAYERMOD, null).getLuckLevel();
        }

        event.setLootingLevel(level);
    }
}
