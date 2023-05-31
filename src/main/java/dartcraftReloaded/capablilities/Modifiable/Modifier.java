package dartcraftReloaded.capablilities.Modifiable;

import dartcraftReloaded.Constants;
import dartcraftReloaded.config.ConfigHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;

public class Modifier {
    private final int id;
    private final int maxLevels;
    private final int tier;
    private final ItemStack item;
    private final long allowedTools;
    private final String name;
    private final TextFormatting color;

    public Modifier(int id, int maxLevels, int tier, String name, TextFormatting color, ItemStack item, long allowedTools) {
        this.id = id;
        this.maxLevels = maxLevels;
        this.tier = tier;
        this.item = item;
        this.allowedTools = allowedTools;
        this.name = name;
        this.color = color;
        if (Arrays.stream(ConfigHandler.disabledModifiers).noneMatch(i -> i == id)) {
            Constants.MODIFIER_REGISTRY.put(id, this);
        }
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
