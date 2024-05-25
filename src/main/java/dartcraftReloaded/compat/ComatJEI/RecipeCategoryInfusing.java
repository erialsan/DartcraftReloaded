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

public class RecipeCategoryInfusing implements IRecipeCategory<RecipeWrapperInfusing> {
    public static final String NAME = "dcr.category.infusing";
    private final IDrawable background;
    private final IDrawable icon;
    public static final ResourceLocation ICON;

    public RecipeCategoryInfusing(final IGuiHelper helper) {
        this.background = helper.createDrawable(new ResourceLocation(Constants.modId, "textures/gui/jei.png"), 0, 0, 112, 113);
        this.icon = helper.drawableBuilder(RecipeCategoryInfusing.ICON, 0, 0, 16, 16).setTextureSize(16, 16).build();
    }

    public String getUid() {
        return NAME;
    }

    public String getTitle() {
        return "Tool Infusing";
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

    public void setRecipe(final IRecipeLayout recipeLayout, final RecipeWrapperInfusing recipeWrapper, final IIngredients ingredients) {
        final List<List<ItemStack>> outputItem = ingredients.getOutputs(VanillaTypes.ITEM);
        final List<List<ItemStack>> inputItem = ingredients.getInputs(VanillaTypes.ITEM);
        final IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        if (outputItem.size() == 1) {
            guiItemStacks.init(0, false, 47, 48);
            guiItemStacks.set(0, outputItem.get(0));
        }
        if (inputItem.size() == 8) {
            guiItemStacks.init(1, true, 47, 11);
            guiItemStacks.init(2, true, 71, 23);
            guiItemStacks.init(3, true, 83, 48);
            guiItemStacks.init(4, true, 71, 72);
            guiItemStacks.init(5, true, 47, 84);
            guiItemStacks.init(6, true, 23, 72);
            guiItemStacks.init(7, true, 11, 48);
            guiItemStacks.init(8, true, 23, 23);
            guiItemStacks.set(1, inputItem.get(0));
            guiItemStacks.set(2, inputItem.get(1));
            guiItemStacks.set(3, inputItem.get(2));
            guiItemStacks.set(4, inputItem.get(3));
            guiItemStacks.set(5, inputItem.get(4));
            guiItemStacks.set(6, inputItem.get(5));
            guiItemStacks.set(7, inputItem.get(6));
            guiItemStacks.set(8, inputItem.get(7));

        }
        guiItemStacks.addTooltipCallback(new ModifierTooltipCallback());
    }

    static {
        ICON = new ResourceLocation(Constants.modId, "textures/items/gem_forcegem.png");
    }
}