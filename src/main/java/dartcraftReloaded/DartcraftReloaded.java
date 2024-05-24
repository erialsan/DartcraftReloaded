package dartcraftReloaded;

import dartcraftReloaded.fluids.ModFluids;
import dartcraftReloaded.handlers.*;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.client.tabDartcraft;
import dartcraftReloaded.proxy.CommonProxy;
import dartcraftReloaded.tileEntity.TileEntityInfuser;
import dartcraftReloaded.tileEntity.TileEntityInfuserRenderer;
import dartcraftReloaded.world.DCRWorldGen;
import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import static net.minecraftforge.fml.relauncher.Side.CLIENT;

@Mod(modid = Constants.modId, name = Constants.name, version = Constants.version, dependencies = "after: forestry")
public class DartcraftReloaded {

    public static final tabDartcraft creativeTab = new tabDartcraft();

    @Mod.Instance(Constants.modId)
    public static DartcraftReloaded instance;

    @SidedProxy(serverSide = "dartcraftReloaded.proxy.CommonProxy", clientSide = "dartcraftReloaded.proxy.ClientProxy")
    public static CommonProxy proxy;

    public static final ItemArmor.ArmorMaterial forceArmorMaterial = EnumHelper.addArmorMaterial("FORCEA", Constants.modId + ":force", 15, new int[]{3, 6, 8, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);
    public static final ItemTool.ToolMaterial forceToolMaterial = EnumHelper.addToolMaterial("FORCET", 3, 1561, 10.0F, 6.0F, 22);

    static {
        FluidRegistry.enableUniversalBucket();
    }

    public Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        GameRegistry.registerWorldGenerator(new DCRWorldGen(), 3);
        proxy.registerTileEntities();
        CapabilityHandler.register();
        PotionHandler.preInit(e);
        ModFluids.registerFluids();
        CompatHandler.preInit(e);
        EntityHandler.registerEntities();
        EntityHandler.registerEntityRenders();
        logger = e.getModLog();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e){
        NetworkRegistry.INSTANCE.registerGuiHandler(DartcraftReloaded.instance, new GUIHandler());
        GameRegistry.registerFuelHandler(new FuelHandler());
        proxy.registerSmeltingRecipes();
        EventHandler.init();
        PacketHandler.init();
        ModBlocks.registerOreDict();
        ModItems.registerOreDict();
        SoundHandler.registerSounds();
        CompatHandler.init(e);
    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e){
        CompatHandler.postInit(e);
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
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInfuser.class, new TileEntityInfuserRenderer());
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
            PotionHandler.registerPotions(event.getRegistry());
        }
        @SubscribeEvent
        public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
            RecipeHandler.registerRecipes(event.getRegistry());
        }
    }

}

