package dartcraftReloaded.entity;

import dartcraftReloaded.config.ConfigHandler;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityColdChicken extends EntityChicken {
    int timer;

    public EntityColdChicken(World worldIn) {
        super(worldIn);
        this.setSize(0.9F, 2.8F);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return EntityColdCow.COLD_COW;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!world.isRemote) {
            if (timer++ > ConfigHandler.ticksUntilConversion) {
                EntityChicken cow = new EntityChicken(world);
                BlockPos pos = this.getPosition();
                float yaw = this.rotationYaw;
                float pitch = this.rotationPitch;
                world.removeEntity(this);
                cow.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), yaw, pitch);
                cow.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(cow);

            }
        }
    }
}