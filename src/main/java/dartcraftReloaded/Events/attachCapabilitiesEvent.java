package dartcraftReloaded.Events;

import dartcraftReloaded.capablilities.BaneModifier.BaneProvider;
import dartcraftReloaded.capablilities.PlayerModifier.PlayerModifierProvider;
import dartcraftReloaded.capablilities.Shearable.ShearableProvider;
import dartcraftReloaded.Handlers.DCRCapabilityHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by BURN447 on 6/16/2018.
 */
public class attachCapabilitiesEvent {

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof EntityEnderman && !event.getObject().hasCapability(DCRCapabilityHandler.CAPABILITY_BANE, null)){
                event.addCapability(DCRCapabilityHandler.BANE_CAP, new BaneProvider(DCRCapabilityHandler.CAPABILITY_BANE, null));
        }

        if(event.getObject() instanceof EntityPlayer && !event.getObject().hasCapability(DCRCapabilityHandler.CAPABILITY_PLAYERMOD, null)){
            event.addCapability(DCRCapabilityHandler.PLAYER_CAP, new PlayerModifierProvider(DCRCapabilityHandler.CAPABILITY_PLAYERMOD, null));
        }

        if(event.getObject() instanceof EntityCow || event.getObject() instanceof EntityChicken && !event.getObject().hasCapability(DCRCapabilityHandler.CAPABILITY_SHEARABLE, null)){
            event.addCapability(DCRCapabilityHandler.SHEAR_CAP, new ShearableProvider(DCRCapabilityHandler.CAPABILITY_SHEARABLE, null));
        }
    }
}
