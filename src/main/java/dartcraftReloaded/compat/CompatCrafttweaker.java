package dartcraftReloaded.compat;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import dartcraftReloaded.Constants;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.dartcraftreloaded.Infuser")
@ZenRegister
public final class CompatCrafttweaker {

    @ZenMethod
    public static void setTier(int id, int tier) {
        int tier2 = tier;
        if (tier2 < 1) tier2 = 1;
        if (tier2 > 8) tier2 = 8;
        if (Constants.MODIFIER_REGISTRY.containsKey(id)) {
            Constants.MODIFIER_REGISTRY.get(id).setTier(tier2);
        }
    }

    @ZenMethod
    public static void setItem(int id, IItemStack item2) {
        ItemStack item = CraftTweakerMC.getItemStack(item2);
        if (item != null && item != ItemStack.EMPTY) {
            item.setCount(1);
            if (Constants.MODIFIER_REGISTRY.containsKey(id)) {
                Constants.MODIFIER_REGISTRY.get(id).setItem(item);
            }
        }
    }
}