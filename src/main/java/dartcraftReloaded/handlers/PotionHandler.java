package dartcraftReloaded.handlers;

import dartcraftReloaded.potion.PotionBleeding;
import dartcraftReloaded.potion.PotionMagnet;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class PotionHandler {

    public static Potion potionBleeding;
    public static Potion potionMagnet;

    public static void preInit(FMLPreInitializationEvent event){
        potionBleeding = new PotionBleeding();
        potionMagnet = new PotionMagnet();
    }

    public static void registerPotions(IForgeRegistry<Potion> registry){
        registry.register(potionBleeding);
        registry.register(potionMagnet);
    }
}
