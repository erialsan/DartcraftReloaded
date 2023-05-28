package dartcraftReloaded.handlers;

import dartcraftReloaded.events.*;
import net.minecraftforge.common.MinecraftForge;

public class EventHandler {

    public static void init(){
        MinecraftForge.EVENT_BUS.register(new onHarvestEvent());
        MinecraftForge.EVENT_BUS.register(new attachCapabilitiesEvent());
        MinecraftForge.EVENT_BUS.register(new enderTeleportEvent());
        MinecraftForge.EVENT_BUS.register(new attackEntityEvent());
        MinecraftForge.EVENT_BUS.register(new onLivingUpdate());
        MinecraftForge.EVENT_BUS.register(new livingDeathEvent());
        MinecraftForge.EVENT_BUS.register(new livingDropsEvent());
        MinecraftForge.EVENT_BUS.register(new lootTableLoadEvent());
        MinecraftForge.EVENT_BUS.register(new livingAttackEvent());
        MinecraftForge.EVENT_BUS.register(new onLootingEvent());
        MinecraftForge.EVENT_BUS.register(new projectileImpactEvent());
        MinecraftForge.EVENT_BUS.register(new livingHurtEvent());

    }

}
