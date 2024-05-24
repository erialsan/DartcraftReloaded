package dartcraftReloaded.compat.ComatJEI;

import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.capablilities.Modifiable.Modifier;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.util.DartUtils;
import mezz.jei.api.gui.ITooltipCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class ModifierTooltipCallback implements ITooltipCallback<ItemStack> {
    public void onTooltip(final int slotIndex, final boolean input, final ItemStack ingredient, final List<String> tooltip) {

        if (slotIndex > 0) {
            Modifier m = Constants.getByStack(ingredient);
            if (m == null) return;
            tooltip.add("");
            tooltip.add(m.getColor()+"Modifier: "+m.getName());
            tooltip.add(m.getColor()+"Tier: "+m.getTier());
            tooltip.add(m.getColor()+"Max levels: "+m.getMaxLevels());
        } else {
            Modifier m = null;
            if (ingredient.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
                IModifiable cap = ingredient.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
                for (Modifier mod : Constants.MODIFIER_REGISTRY.values()) {
                    if (cap.getLevel(mod) > 0) m = mod;
                }
            }
            if (m != null) {
                tooltip.add("");
                tooltip.add(TextFormatting.BOLD+"Allowed Tools:");
                tooltip.addAll(DartUtils.getToolNames(m));
            }
        }
    }
}