package dartcraftReloaded.Handlers;

import dartcraftReloaded.Events.*;
import dartcraftReloaded.Events.*;
import dartcraftReloaded.util.PlayerUtils;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by BURN447 on 6/1/2018.
 */
public class DCREventHandler {

    public static void init(){
        MinecraftForge.EVENT_BUS.register(new onHarvestEvent());
        MinecraftForge.EVENT_BUS.register(new attachCapabilitiesEvent());
        MinecraftForge.EVENT_BUS.register(new enderTeleportEvent());
        MinecraftForge.EVENT_BUS.register(new attackEntityEvent());
        MinecraftForge.EVENT_BUS.register(new onLivingUpdate());
        MinecraftForge.EVENT_BUS.register(new PlayerUtils());
        MinecraftForge.EVENT_BUS.register(new livingDeathEvent());
        MinecraftForge.EVENT_BUS.register(new livingDropsEvent());
        MinecraftForge.EVENT_BUS.register(new lootTableLoadEvent());
    }


}
