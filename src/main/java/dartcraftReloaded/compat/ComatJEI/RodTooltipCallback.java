package dartcraftReloaded.compat.ComatJEI;

import dartcraftReloaded.handlers.RecipeHandler;
import dartcraftReloaded.recipe.RecipeRod;
import mezz.jei.api.gui.ITooltipCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class RodTooltipCallback implements ITooltipCallback<ItemStack> {
    public void onTooltip(final int slotIndex, final boolean input, final ItemStack ingredient, final List<String> tooltip) {
        RecipeRod recipe = null;
        for (RecipeRod r : RecipeHandler.rodRecipes) {
            if (input) {
                if (r.input.getItem() == ingredient.getItem() && r.input.getItemDamage() == ingredient.getItemDamage()) recipe = r;
            } else {
                if (r.output.getItem() == ingredient.getItem() && r.output.getItemDamage() == ingredient.getItemDamage()) recipe = r;
            }
        }
        if (recipe != null && recipe.needsFire) tooltip.add(TextFormatting.RED+"Must be on fire!");
    }
}
