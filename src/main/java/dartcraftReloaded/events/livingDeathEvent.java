package dartcraftReloaded.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by BURN447 on 8/6/2018.
 */
public class livingDeathEvent {

    @SubscribeEvent
    public void livingDeathEvent(LivingDeathEvent event) {
        if(event.getSource().getTrueSource() instanceof EntityPlayer) {
            
        }

    }
}
