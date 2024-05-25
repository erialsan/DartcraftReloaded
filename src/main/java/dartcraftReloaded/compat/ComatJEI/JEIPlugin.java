package dartcraftReloaded.compat.ComatJEI;

import dartcraftReloaded.Constants;
import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.capablilities.Modifiable.Modifier;
import dartcraftReloaded.handlers.RecipeHandler;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.recipe.RecipeRod;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.IModPlugin;
import net.minecraft.item.ItemStack;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
    public void registerCategories(final IRecipeCategoryRegistration registry) {
        final IJeiHelpers helpers = registry.getJeiHelpers();
        registry.addRecipeCategories(new RecipeCategoryInfusing(helpers.getGuiHelper()));
        registry.addRecipeCategories(new RecipeCategoryRod(helpers.getGuiHelper()));
    }

    public void register(final IModRegistry registry) {
        registry.handleRecipes(Modifier.class, RecipeWrapperInfusing::new, RecipeCategoryInfusing.NAME);
        registry.addRecipes(Constants.MODIFIER_REGISTRY.values(), RecipeCategoryInfusing.NAME);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.infuser), RecipeCategoryInfusing.NAME);

        registry.handleRecipes(RecipeRod.class, RecipeWrapperRod::new, RecipeCategoryRod.NAME);
        registry.addRecipes(RecipeHandler.rodRecipes, RecipeCategoryRod.NAME);
        registry.addRecipeCatalyst(new ItemStack(ModItems.forceRod), RecipeCategoryRod.NAME);
    }
}
