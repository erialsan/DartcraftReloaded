package dartcraftReloaded.compat.ComatJEI;

import dartcraftReloaded.capablilities.Modifiable.Modifier;
import dartcraftReloaded.util.DartUtils;
import net.minecraft.item.ItemStack;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import mezz.jei.api.recipe.IRecipeWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecipeWrapperInfusing implements IRecipeWrapper {
    public static final IRecipeWrapperFactory<Modifier> FACTORY;
    public final Modifier theModifier;

    List<List<ItemStack>> slots = new ArrayList<>();

    public RecipeWrapperInfusing(final Modifier recipe) {
        this.theModifier = recipe;
        for (int i = 0; i < 8; i++) {
            ArrayList<ItemStack> inner = new ArrayList<>();
            for (int j = 0; j < theModifier.getMaxLevels(); j++) {
                if (theModifier.getMaxLevels() - i - j > 0) {
                    inner.add(theModifier.getItem());
                } else {
                    inner.add(ItemStack.EMPTY);
                }
            }
            slots.add(inner);
        }
    }

    public void getIngredients(final IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, slots);
        ingredients.setOutputLists(VanillaTypes.ITEM, Collections.singletonList(DartUtils.getToolsForModifier(theModifier)));
    }

    static {
        FACTORY = RecipeWrapperInfusing::new;
    }
}