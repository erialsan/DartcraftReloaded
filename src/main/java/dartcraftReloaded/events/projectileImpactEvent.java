package dartcraftReloaded.events;

import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.handlers.PotionHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class projectileImpactEvent {

    @SubscribeEvent
    public void onProjectileImpact(ProjectileImpactEvent.Arrow event) {
        if (event.getArrow().shootingEntity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getArrow().shootingEntity;
            if (player.getHeldItemMainhand().hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
                IModifiable cap = player.getHeldItemMainhand().getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
                if (cap.hasModifier(Constants.HEAT)) {
                    event.getEntity().setFire(cap.getLevel(Constants.HEAT) * 4);
                }
            }
        }
    }
}
