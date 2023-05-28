package dartcraftReloaded.entity;

import dartcraftReloaded.Constants;
import dartcraftReloaded.config.ConfigHandler;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityColdCow extends EntityCow {
    public static final ResourceLocation COLD_COW = LootTableList.register(new ResourceLocation(Constants.modId, "coldcow"));
    int timer;
    public EntityColdCow(World worldIn) {
        super(worldIn);
        this.setSize(0.9F, 2.8F);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return COLD_COW;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!world.isRemote) {
            if (timer++ > ConfigHandler.ticksUntilConversion) {
                EntityCow cow = new EntityCow(world);
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
