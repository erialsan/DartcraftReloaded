package dartcraftReloaded.events;

import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class enderTeleportEvent {

    @SubscribeEvent
    public void onEnderTeleportEvent(EnderTeleportEvent event){
        if(event.getEntity() instanceof EntityEnderman){
            EntityEnderman enderman = ((EntityEnderman) event.getEntity());
            if(!enderman.getCapability(CapabilityHandler.CAPABILITY_BANE, null).canDoAbility()){
                event.setCanceled(true);
            }
        }
    }
}
