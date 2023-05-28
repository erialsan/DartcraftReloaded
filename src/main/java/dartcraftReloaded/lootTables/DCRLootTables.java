package dartcraftReloaded.lootTables;

import dartcraftReloaded.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class DCRLootTables {
    public static final ResourceLocation batLoot = register("batLoot");

    private static ResourceLocation register(String id) {
        return LootTableList.register(new ResourceLocation(Constants.modId, id));
    }

}
