package dartcraftReloaded.entity;

import dartcraftReloaded.Constants;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderColdCow extends RenderLiving<EntityColdCow> {
    public static final ResourceLocation TEXTURES = new ResourceLocation(Constants.modId ,"textures/entity/coldcow.png");

    public RenderColdCow(RenderManager manager) {
        super(manager, new ModelCow(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityColdCow entity) {
        return TEXTURES;
    }

    @Override
    protected void applyRotations(EntityColdCow entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    }

}
