package dartcraftReloaded.compat.ComatJEI;

import dartcraftReloaded.recipe.RecipeRod;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import mezz.jei.api.recipe.IRecipeWrapper;

public class RecipeWrapperRod implements IRecipeWrapper {
    public static final IRecipeWrapperFactory<RecipeRod> FACTORY;
    public final RecipeRod r;

    public RecipeWrapperRod(RecipeRod r) {
        this.r = r;
    }

    public void getIngredients(final IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, r.input);
        ingredients.setOutput(VanillaTypes.ITEM, r.output);
    }

    static {
        FACTORY = RecipeWrapperRod::new;
    }
}