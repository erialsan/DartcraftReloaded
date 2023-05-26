package dartcraftReloaded.capablilities.Modifiable;

import net.minecraft.item.ItemStack;

public class Modifier {
    private int id;
    private int maxLevels;
    private int tier;
    private ItemStack item;
    private long allowedTools;

    public Modifier(int id, int maxLevels, int tier, ItemStack item, long allowedTools) {
        this.id = id;
        this.maxLevels = maxLevels;
        this.tier = tier;
        this.item = item;
        this.allowedTools = allowedTools;
    }

    public int getId() {
        return id;
    }
}
