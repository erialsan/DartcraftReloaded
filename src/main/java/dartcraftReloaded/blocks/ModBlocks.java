package dartcraftReloaded.blocks;

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
public class ModBlocks {

    public static BlockForceOre orePower = new BlockForceOre();
    public static BlockForceSapling forceSapling = new BlockForceSapling();
    public static BlockForceLog forceLog = new BlockForceLog();
    public static BlockForceLeaves forceLeaves = new BlockForceLeaves();
    public static BlockInfuser infuser = new BlockInfuser();
    public static BlockFluidForce blockFluidForce = new BlockFluidForce();
    public static BlockForceFurnace forceFurnace = new BlockForceFurnace(false, FORCE_FURNACE);
    public static BlockForceFurnace litForceFurnace = new BlockForceFurnace(true, LIT_FORCE_FURNACE);
    public static BlockBase forcePlanks = new BlockBase(Material.WOOD, FORCE_PLANKS, SoundType.WOOD);


    public static BlockForceBrick forceBrick = new BlockForceBrick();

    public static BlockForceBrickSlab forceBrickSlab = new BlockForceBrickSlab();
    public static BlockForceBrickStair[] forceBrickStairs = new BlockForceBrickStair[16];
    static {
        for (int i = 0; i < 16; i++) {
            String key = "forceBrickStair."+EnumDyeColor.values()[i].getTranslationKey();
            forceBrickStairs[i] = (BlockForceBrickStair) new BlockForceBrickStair(forceBrick.getStateFromMeta(i)).setRegistryName(key).setTranslationKey(key);
        }
    }
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
                litForceFurnace,
                forceBrick,
                forceBrickSlab,
                forceTorch,
                timetorch
        );

        registry.registerAll(forceBrickStairs);

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

        for (int i = 0; i < 16; i++) {
            registry.register(forceBrickStairs[i].createItemBlock());
        }

        registerDyeBlock(registry, forceBrick, FORCE_BRICK);
        registerDyeBlock(registry, forceBrickSlab, FORCE_BRICK_SLAB);
    }

    public static void registerModels() {
        orePower.registerItemModel(Item.getItemFromBlock(orePower));
        forceLog.registerItemModel(Item.getItemFromBlock(forceLog));
        infuser.registerItemModel(Item.getItemFromBlock(infuser));
        forceFurnace.registerItemModel(Item.getItemFromBlock(forceFurnace));
        forcePlanks.registerItemModel(Item.getItemFromBlock(forcePlanks));
        forceSapling.registerItemModel(Item.getItemFromBlock(forceSapling));
        forceLeaves.registerItemModel(Item.getItemFromBlock(forceLeaves));
        litForceFurnace.registerItemModel(Item.getItemFromBlock(litForceFurnace));
        forceBrick.registerItemModel(Item.getItemFromBlock(forceBrick));
        forceTorch.registerItemModel(Item.getItemFromBlock(forceTorch));
        timetorch.registerItemModel(Item.getItemFromBlock(timetorch));
        forceBrickSlab.registerItemModel(Item.getItemFromBlock(forceBrickSlab));
        for (int i = 0; i < 16; i++) {
            forceBrickStairs[i].registerItemModel(Item.getItemFromBlock(forceBrickStairs[i]), "forceBrickStair."+EnumDyeColor.values()[i].getTranslationKey());
        }

    }

    public static void registerOreDict() {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            OreDictionary.registerOre(FORCE_BRICK, new ItemStack(Item.getByNameOrId("dartcraftreloaded:forcebrick"), 1, color.getMetadata()));
        }
        OreDictionary.registerOre("logWood", forceLog);
        OreDictionary.registerOre("plankWood", forcePlanks);
    }

    private static void registerDyeBlock(IForgeRegistry<Item> registry, Block block, String name) {
        registry.register((new ItemMultiTexture(block, block, item -> EnumDyeColor.byMetadata(item.getMetadata()).getTranslationKey())).setRegistryName(name).setTranslationKey(name));
    }
}
