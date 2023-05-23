package dartcraftReloaded.blocks;

import dartcraftReloaded.blocks.torch.BlockForceTorch;
import dartcraftReloaded.blocks.torch.BlockTimetorch;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import static dartcraftReloaded.Constants.*;
/**
 * Created by BURN447 on 2/4/2018.
 */
public class ModBlocks {

    public static BlockForceOre orePower = new BlockForceOre();
    public static BlockForceSapling forceSapling = new BlockForceSapling();
    public static BlockForceLog forceLog = new BlockForceLog();
    public static BlockForceLeaves forceLeaves = new BlockForceLeaves();
    public static BlockInfuser infuser = new BlockInfuser();
    public static BlockFluidForce blockFluidForce = new BlockFluidForce();
    public static BlockForceFurnace forceFurnace = new BlockForceFurnace(false, FORCE_FURNACE);
    public static BlockForceFurnace LIT_FORCEFURNACE = new BlockForceFurnace(true, LIT_FORCE_FURNACE);
    public static BlockBase forcePlanks = new BlockBase(Material.WOOD, FORCE_PLANKS, SoundType.WOOD);


    public static BlockForceBrick forceBrick = new BlockForceBrick();

    //Torches
    public static BlockForceTorch forceTorch = new BlockForceTorch();
    public static BlockTimetorch timetorch = new BlockTimetorch();

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                orePower,
                forceSapling,
                forceLog,
                forceLeaves,
                infuser,
                blockFluidForce,
                forceFurnace,
                forcePlanks,
                LIT_FORCEFURNACE,
                forceBrick,
                forceTorch,
                timetorch
        );

    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(
                orePower.createItemBlock(),
                forceSapling.createItemBlock(),
                forceLog.createItemBlock(),
                forceLeaves.createItemBlock(),
                infuser.createItemBlock(),
                forceFurnace.createItemBlock(),
                forcePlanks.createItemBlock(),
                forceTorch.createItemBlock(),
                timetorch.createItemBlock()
        );


        registry.register((new ItemMultiTexture(forceBrick, forceBrick, item -> EnumDyeColor.byMetadata(item.getMetadata()).getTranslationKey())).setRegistryName("forceBrick").setTranslationKey("forceBrick"));

    }

    public static void registerModels() {
        orePower.registerItemModel(Item.getItemFromBlock(orePower));
        forceLog.registerItemModel(Item.getItemFromBlock(forceLog));
        infuser.registerItemModel(Item.getItemFromBlock(infuser));
        forceFurnace.registerItemModel(Item.getItemFromBlock(forceFurnace));
        forcePlanks.registerItemModel(Item.getItemFromBlock(forcePlanks));
        forceSapling.registerItemModel(Item.getItemFromBlock(forceSapling));
        forceLeaves.registerItemModel(Item.getItemFromBlock(forceLeaves));
        LIT_FORCEFURNACE.registerItemModel(Item.getItemFromBlock(LIT_FORCEFURNACE));
        forceBrick.registerItemModel(Item.getItemFromBlock(forceBrick));
        forceTorch.registerItemModel(Item.getItemFromBlock(forceTorch));
        timetorch.registerItemModel(Item.getItemFromBlock(timetorch));


    }

    public static void registerOreDict() {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            OreDictionary.registerOre(FORCE_BRICK, new ItemStack(Item.getByNameOrId("dartcraftreloaded:forcebrick"), 1, color.getMetadata()));
        }
        OreDictionary.registerOre("logWood", forceLog);
        OreDictionary.registerOre("plankWood", forcePlanks);
    }
}
