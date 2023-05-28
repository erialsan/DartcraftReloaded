package dartcraftReloaded.capablilities.Modifiable;

import dartcraftReloaded.Constants;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class Modifier {
    private int id;
    private int maxLevels;
    private int tier;
    private ItemStack item;
    private long allowedTools;
    private String name;
    private TextFormatting color;

    public Modifier(int id, int maxLevels, int tier, String name, TextFormatting color, ItemStack item, long allowedTools) {
        this.id = id;
        this.maxLevels = maxLevels;
        this.tier = tier;
        this.item = item;
        this.allowedTools = allowedTools;
        this.name = name;
        this.color = color;
        Constants.MODIFIER_REGISTRY.put(id, this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TextFormatting getColor() {
        return color;
    }

    public ItemStack getItem() {
        return item;
    }

    public long getAllowedTools() {
        return allowedTools;
    }

    public int getMaxLevels() {
        return maxLevels;
    }

    public int getTier() {
        return tier;
    }
}
