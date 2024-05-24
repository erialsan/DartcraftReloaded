package dartcraftReloaded.compat.ComatJEI;

import dartcraftReloaded.Constants;
import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.capablilities.Modifiable.Modifier;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.IModPlugin;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
    public void registerCategories(final IRecipeCategoryRegistration registry) {
        final IJeiHelpers helpers = registry.getJeiHelpers();
        registry.addRecipeCategories(new RecipeCategory(helpers.getGuiHelper()));

    }

    public void register(final IModRegistry registry) {
        registry.handleRecipes(Modifier.class, RecipeWrapper::new, RecipeCategory.NAME);
        registry.addRecipes(Constants.MODIFIER_REGISTRY.values(), RecipeCategory.NAME);
        registry.addRecipeCatalyst(ModBlocks.infuser.createItemBlock(), RecipeCategory.NAME);
    }
}
