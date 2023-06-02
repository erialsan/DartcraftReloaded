package dartcraftReloaded.handlers;

import dartcraftReloaded.Constants;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityHandler {
    public static void registerEntities() {
        registerEntity("coldcow", EntityColdCow.class, 201, 50, 11437146, 000000);
        registerEntity("coldchicken", EntityColdChicken.class, 202, 50, 11437146, 000000);
        registerEntity("bottle", EntityBottle.class, 203, 50, 11437146, 000000);
    }

    private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2) {
        EntityRegistry.registerModEntity(new ResourceLocation(Constants.modId + ":" + name), entity, name, id, DartcraftReloaded.instance, range, 1, true, color1, color2);
    }

    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityColdCow.class, RenderColdCow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityColdChicken.class, RenderColdChicken::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBottle.class, RenderBottle::new);
    }
}
