package dartcraftReloaded.recipe;

import net.minecraft.item.ItemStack;

public class RecipeRod {
    public ItemStack input;
    public ItemStack output;
    public boolean needsFire;

    public RecipeRod(ItemStack input, ItemStack output, boolean needsFire) {
        this.input = input;
        this.output = output;
        this.needsFire = needsFire;
    }

    public RecipeRod(ItemStack input, ItemStack output) {
        this(input, output, false);
    }
}
