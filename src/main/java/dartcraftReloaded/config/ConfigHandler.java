package dartcraftReloaded.config;


import dartcraftReloaded.Constants;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Constants.modId)
public class ConfigHandler {

    @Config.Comment("These materials make force ingots 1:1. Default: ingotIron, ingotBronze")
    public static String[] ingots2 = {"ingotIron", "ingotBronze"};

    @Config.Comment("These materials make force ingots 1:1.5. Defualt: ingotGold, ingotSilver, ingotSteel, ingotRefinedIron")
    public static String[] ingots3 = {"ingotGold", "ingotSilver", "ingotSteel", "ingotRefinedIron"};

    @Config.Comment("Put a modifier's id (integer) in this list to disable it. ")
    public static int[] disabledModifiers = {};

    @Config.Comment("Luck/heat interaction? Default: true")
    public static boolean luckHeatEnabled = true;

    @Config.Comment("Infuser rf/t. Default: 80")
    public static int infuserConsumption = 80;

    @Config.Comment("Infuser recipe time multiplier. Default: 1")
    public static double infuserTimeMultiplier = 1.0;

    @Config.Comment("Infuser max time in ticks. -1 for no limit. Default: 400")
    public static int infuserMaxTime = 400;

    @Config.Comment("Cold animals regen timer. Default: 3600")
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


