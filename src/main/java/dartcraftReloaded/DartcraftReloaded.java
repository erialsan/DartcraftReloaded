package dartcraftReloaded;

import dartcraftReloaded.fluids.ModFluids;
import dartcraftReloaded.handlers.*;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.client.tabDartcraft;
import dartcraftReloaded.proxy.CommonProxy;
import dartcraftReloaded.world.DCRWorldGen;
import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

/**
 * Created by BURN447 on 2/4/2018.
 */

@Mod(modid = Constants.modId, name = Constants.name, version = Constants.version)
public class DartcraftReloaded {

    public static final tabDartcraft creativeTab = new tabDartcraft();

    @Mod.Instance(Constants.modId)
    public static DartcraftReloaded instance;

    @SidedProxy(serverSide = "dartcraftReloaded.proxy.CommonProxy", clientSide = "dartcraftReloaded.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static final ItemArmor.ArmorMaterial forceArmorMaterial = EnumHelper.addArmorMaterial("FORCE", Constants.modId + ":force", 15, new int[]{1, 2, 3, 1}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 4.0F);
    public static final ItemTool.ToolMaterial forceToolMaterial = EnumHelper.addToolMaterial("FORCE", 3, 1561, 8.0F, 8.0F, 22);

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        GameRegistry.registerWorldGenerator(new DCRWorldGen(), 3);
        proxy.registerTileEntities();
        DCRCapabilityHandler.register();
        DCROreDictionaryHandler.registerOreDictionary();
        DCRPotionHandler.preInit(e);
        ModFluids.registerFluids();
        ModFluids.setUpFluids();

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e){
        NetworkRegistry.INSTANCE.registerGuiHandler(DartcraftReloaded.instance, new DCRGUIHandler());
        GameRegistry.registerFuelHandler(new DCRFuelHandler());
        proxy.registerSmeltingRecipes();
        DCREventHandler.init();
        DCRPacketHandler.init();
        ModBlocks.registerOreDict();
        ModItems.registerOreDict();
        DCRSoundHandler.registerSounds();
    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e){
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event){
            ModItems.register(event.getRegistry());
            ModBlocks.registerItemBlocks(event.getRegistry());
        }
        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            ModBlocks.register(event.getRegistry());
            ModBlocks.registerModels();
        }

        @SideOnly(CLIENT)
        @SubscribeEvent
        public static void registerModels(RegistryEvent.Register<Block> event) {
            ModBlocks.registerModels();
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ModItems.registerModels();
            ModBlocks.registerModels();
        }

        @SubscribeEvent
        public static void registerPotions(RegistryEvent.Register<Potion> event) {
            DCRPotionHandler.registerPotions(event.getRegistry());
        }
    }

}

