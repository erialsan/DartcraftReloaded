package dartcraftReloaded.config;


import dartcraftReloaded.Constants;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Constants.modId)
public class ConfigHandler {

    @Config.Comment("Enable Time Torch. Default: true")
    public static boolean timetorchEnabled = true;

    @Config.Comment("Print in Log when Time Torch is placed and by who. Default: false")
    public static boolean timeTorchLogging = false;

    @Config.Comment("These ingots make force ingots 1:1. Default: ingotIron, ingotBronze")
    public static String[] ingots2 = {"ingotIron", "ingotBronze"};

    @Config.Comment("These ingots make force ingots 1:1.5. Defualt: ingotGold, ingotSilver, ingotSteel, ingotRefinedIron")
    public static String[] ingots3 = {"ingotGold", "ingotSilver", "ingotSteel", "ingotRefinedIron"};

    @Config.Comment("Luck/heat and luck/grinding interaction? Default: true")
    public static boolean luckHeatEnabled = true;

    @Config.Comment("Infuser rf/t Default: 80")
    public static int infuserConsumption = 80;

    @Config.Comment("Cold animals regen timer Default: 3600")
    public static int ticksUntilConversion = 3600;

    @Mod.EventBusSubscriber(modid = Constants.modId)
    private static class EventHandler {

        /**
         * Inject the new values and save to the config file when the config has been changed from the GUI.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Constants.modId)) {
                ConfigManager.sync(Constants.modId, Config.Type.INSTANCE);
            }
        }
    }
}


