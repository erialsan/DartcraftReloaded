package dartcraftReloaded.handlers;

import dartcraftReloaded.compat.CompatForestry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CompatHandler {
    public static void preInit(FMLPreInitializationEvent e) {

    }

    public static void init(FMLInitializationEvent e) {
        if (Loader.isModLoaded("forestry")) {
            CompatForestry.registerRecipes();
        }

    }

    public static void postInit(FMLPostInitializationEvent e) {

    }
}
