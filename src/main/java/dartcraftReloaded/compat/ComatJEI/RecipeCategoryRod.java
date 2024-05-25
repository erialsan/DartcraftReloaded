package dartcraftReloaded.compat.ComatJEI;

import dartcraftReloaded.Constants;
import mezz.jei.api.gui.IGuiItemStackGroup;
import net.minecraft.item.ItemStack;
import java.util.List;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.IGuiHelper;
import net.minecraft.util.ResourceLocation;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;

public class RecipeCategoryRod implements IRecipeCategory<RecipeWrapperRod> {
    public static final String NAME = "dcr.category.rod";
    private final IDrawable background;
    private final IDrawable icon;
    public static final ResourceLocation ICON;

    public RecipeCategoryRod(final IGuiHelper helper) {
        this.background = helper.createDrawable(new ResourceLocation(Constants.modId, "textures/gui/rod.png"), 0, 0, 105, 22);
        this.icon = helper.drawableBuilder(ICON, 0, 0, 16, 16).setTextureSize(16, 16).build();
    }

    public String getUid() {
        return NAME;
    }

    public String getTitle() {
        return "Rod Transmutation";
    }

    public String getModName() {
        return "Dartcraft Reloaded";
    }

    public IDrawable getBackground() {
        return this.background;
    }

    public IDrawable getIcon() {
        return this.icon;
    }

    public void setRecipe(final IRecipeLayout recipeLayout, final RecipeWrapperRod recipeWrapper, final IIngredients ingredients) {
        final List<List<ItemStack>> outputItem = ingredients.getOutputs(VanillaTypes.ITEM);
        final List<List<ItemStack>> inputItem = ingredients.getInputs(VanillaTypes.ITEM);
        final IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 2, 2);
        guiItemStacks.init(1, false, 85, 2);

        guiItemStacks.set(0, inputItem.get(0));
        guiItemStacks.set(1, outputItem.get(0));

        guiItemStacks.addTooltipCallback(new RodTooltipCallback());
    }

    static {
        ICON = new ResourceLocation(Constants.modId, "textures/items/forcerod.png");
    }
}