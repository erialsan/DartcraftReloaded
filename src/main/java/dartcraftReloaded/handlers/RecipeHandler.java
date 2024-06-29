package dartcraftReloaded.handlers;

import dartcraftReloaded.Constants;
import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.config.ConfigHandler;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.recipe.RecipeRod;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

public class RecipeHandler {

    public static ArrayList<RecipeRod> rodRecipes = new ArrayList<>();
    public static void registerRecipes(IForgeRegistry<IRecipe> registry) {
        if (!Loader.isModLoaded("forestry")) {
            GameRegistry.addShapedRecipe(rl(Constants.INERT_CORE), rl(Constants.INERT_CORE), new ItemStack(ModItems.inertCore), "STS", "CDC", "STS", 'S', Blocks.SOUL_SAND, 'T', ModItems.tear, 'C', ModItems.claw, 'D', Items.DIAMOND);
        }
        registerForceIngots(ConfigHandler.ingots2, 2);
        registerForceIngots(ConfigHandler.ingots3, 3);
        GameRegistry.addSmelting(ModItems.filledJar, new ItemStack(ModItems.soulWafer), 5.0f);
        rodRecipes.add(new RecipeRod(new ItemStack(Items.BOOK), new ItemStack(ModItems.upgradeTome)));
        rodRecipes.add(new RecipeRod(new ItemStack(Blocks.OBSIDIAN), new ItemStack(ModBlocks.infuser)));
        rodRecipes.add(new RecipeRod(new ItemStack(Blocks.SAPLING), new ItemStack(ModBlocks.forceSapling)));
        rodRecipes.add(new RecipeRod(new ItemStack(ModItems.inertCore), new ItemStack(ModItems.bottledWither), true));
    }

    private static int id = 0;
    private static void registerForceIngots(String[] ores, int num) {
        for (String s : ores) {
            if (OreDictionary.doesOreNameExist(s)) {
                for (ItemStack i : OreDictionary.getOres(s)) {
                    int id_temp = id++;
                    GameRegistry.addShapedRecipe(rl(Constants.INGOT_FORCE+id_temp), rl(Constants.INGOT_FORCE+id_temp), new ItemStack(ModItems.ingotForce, num),"FI", "I ", 'I', i, 'F', ModItems.gemForceGem);
                }
            }
        }
    }

    private static ResourceLocation rl(String name) {
        return new ResourceLocation(Constants.modId, name);
    }
}
