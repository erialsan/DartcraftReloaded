package dartcraftReloaded.events;

import dartcraftReloaded.capablilities.BaneModifier.BaneProvider;
import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class attachCapabilitiesEvent {

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof EntityEnderman && !event.getObject().hasCapability(CapabilityHandler.CAPABILITY_BANE, null)){
                event.addCapability(CapabilityHandler.BANE_CAP, new BaneProvider(CapabilityHandler.CAPABILITY_BANE, null));
        }
    }
}
