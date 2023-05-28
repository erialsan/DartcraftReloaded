package dartcraftReloaded.entity;

import dartcraftReloaded.Constants;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderColdChicken extends RenderLiving<EntityColdChicken> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(Constants.modId ,"textures/entity/coldchicken.png");

    public RenderColdChicken(RenderManager manager) {
        super(manager, new ModelChicken(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityColdChicken entity) {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(EntityColdChicken entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

}
