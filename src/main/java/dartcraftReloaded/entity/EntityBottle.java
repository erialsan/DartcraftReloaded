package dartcraftReloaded.entity;

import dartcraftReloaded.handlers.SoundHandler;
import dartcraftReloaded.items.ItemFilledJar;
import dartcraftReloaded.items.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBottle extends EntityThrowable {
    private static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(EntityBottle.class, DataSerializers.ITEM_STACK);

    public EntityBottle(World worldIn) {
        super(worldIn);
    }

    public EntityBottle(World worldIn, EntityLivingBase throwerIn, ItemStack thrownBottle) {
        super(worldIn, throwerIn);
        this.setItem(thrownBottle);
    }

    protected void entityInit() {
        this.getDataManager().register(ITEM, ItemStack.EMPTY);
    }

    public ItemStack getBottle() {
        ItemStack itemstack = this.getDataManager().get(ITEM);
        if (itemstack.getItem() == ModItems.filledJar) return itemstack;
        return null;
    }

    public void setItem(ItemStack stack) {
        this.getDataManager().set(ITEM, stack);
        this.getDataManager().setDirty(ITEM);
    }

    protected float getGravityVelocity() {
        return 0.05F;
    }

    protected void onImpact(RayTraceResult result) {
        BlockPos pos = result.getBlockPos();
        if (!this.world.isRemote) {
            ItemStack itemstack = this.getBottle();

            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                ItemFilledJar.spawn(itemstack, world, pos, result.sideHit);
            }

            this.setDead();
        }
        if (world != null && SoundHandler.SPARKLE != null) {
            //world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundHandler.SPARKLE, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        ItemStack itemstack = new ItemStack(compound.getCompoundTag("Bottle"));
        if (itemstack.isEmpty()) {
            this.setDead();
        } else {
            this.setItem(itemstack);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        ItemStack itemstack = this.getBottle();
        if (!itemstack.isEmpty()) {
            compound.setTag("Bottle", itemstack.writeToNBT(new NBTTagCompound()));
        }

    }
}
