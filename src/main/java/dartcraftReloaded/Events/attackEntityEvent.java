package dartcraftReloaded.Events;

import dartcraftReloaded.Potion.Potions.PotionBleeding;
import dartcraftReloaded.util.DartUtils;
import dartcraftReloaded.Handlers.DCRCapabilityHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by BURN447 on 6/19/2018.
 */
public class attackEntityEvent {

    @SubscribeEvent
    public void AttackEntityEvent(AttackEntityEvent event) {
        //Bane/Bleed
        if(event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = event.getEntityPlayer();
            if(player.getHeldItemMainhand().hasCapability(DCRCapabilityHandler.CAPABILITY_TOOLMOD, null)) {
                if(player.getHeldItemMainhand().getCapability(DCRCapabilityHandler.CAPABILITY_TOOLMOD, null).hasBleed()) {
                    Potion Bane = new PotionBleeding();
                    PotionEffect BaneEffect = new PotionEffect(Bane, 200, player.getHeldItemMainhand().getCapability(DCRCapabilityHandler.CAPABILITY_TOOLMOD, null).getBleedLevel());
                    if(event.getTarget() instanceof EntityLivingBase) {
                        ((EntityLivingBase) event.getTarget()).addPotionEffect(BaneEffect);
                        DartUtils.getLogger().info("added Bane to " + event.getTarget().getName());
                    }
                }
            }
        }
    }
}
