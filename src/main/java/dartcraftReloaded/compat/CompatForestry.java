package dartcraftReloaded.compat;

import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.config.ConfigHandler;
import dartcraftReloaded.fluids.ModFluids;
import dartcraftReloaded.items.ModItems;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class CompatForestry {
    public static void registerRecipes() {
        Fluid force = ModFluids.fluidForce;
        if (force != null) {
            RecipeManagers.carpenterManager.addRecipe(10, new FluidStack(force, 1000), ItemStack.EMPTY, new ItemStack(ModItems.inertCore), "STS", "CDC", "STS", 'S', Blocks.SOUL_SAND, 'T', ModItems.tear, 'C', ModItems.claw, 'D', Items.DIAMOND);
            RecipeManagers.squeezerManager.addRecipe(5, new ItemStack(ModItems.gemForceGem), new FluidStack(force, 1000));
            RecipeManagers.squeezerManager.addRecipe(5, new ItemStack(ModBlocks.forceLog), new FluidStack(force, 100));
            registerForceIngots(ConfigHandler.ingots2, 2);
            registerForceIngots(ConfigHandler.ingots3, 3);
        }
    }

    private static void registerForceIngots(String[] ores, int num) {
        for (String s : ores) {
            if (OreDictionary.doesOreNameExist(s)) {
                for (ItemStack i : OreDictionary.getOres(s)) {
                    RecipeManagers.carpenterManager.addRecipe(5, new FluidStack(ModFluids.fluidForce, 500), ItemStack.EMPTY, new ItemStack(ModItems.ingotForce, num), "II", "  ", 'I', i);
                }
            }
        }
    }
}
