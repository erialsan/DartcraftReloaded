package dartcraftReloaded.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BURN447 on 7/6/2018.
 */
public class onLivingUpdate {

    public static List<EntityPlayer> flightList = new ArrayList<EntityPlayer>();

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
    }
}
