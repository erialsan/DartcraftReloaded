package dartcraftReloaded.events;

import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class livingHurtEvent {

    @SubscribeEvent
    public void livingHurtEvent(LivingHurtEvent event){
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            ItemStack i = ((EntityPlayer) event.getSource().getTrueSource()).getHeldItemMainhand();
            if (i.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
                IModifiable cap = i.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
                if (cap.hasModifier(Constants.DAMAGE)) {
                    event.setAmount(event.getAmount() + cap.getLevel(Constants.DAMAGE) + 0.5F);
                }
            }
        }
    }
}
