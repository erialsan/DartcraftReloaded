package dartcraftReloaded.events;

import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.w3c.dom.Entity;

public class livingAttackEvent {

    @SubscribeEvent
    public void livingAttackEvent(LivingAttackEvent event){
        if (event.getEntity() instanceof EntityPlayer) {
            int heat = 0;
            int sturdy = 0;
            for (ItemStack i : ((EntityPlayer) event.getEntity()).inventory.armorInventory) {
                if (i.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
                    IModifiable j = i.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
                    if (j.hasModifier(Constants.HEAT)) {
                        heat += j.getLevel(Constants.HEAT);
                    }
                }
            }
            if (heat > 0) {
                if (event.getSource().getTrueSource() != null) {
                    event.getSource().getTrueSource().setFire(heat * 2);
                }
            }
        }
    }
}
