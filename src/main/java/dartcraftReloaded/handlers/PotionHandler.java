package dartcraftReloaded.handlers;

import dartcraftReloaded.potion.PotionBleeding;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class PotionHandler {

    public static Potion potionBleeding;

    public static void preInit(FMLPreInitializationEvent event){
        potionBleeding = new PotionBleeding();
    }

    public static void registerPotions(IForgeRegistry<Potion> registry){
        registry.register(potionBleeding);
    }
}
