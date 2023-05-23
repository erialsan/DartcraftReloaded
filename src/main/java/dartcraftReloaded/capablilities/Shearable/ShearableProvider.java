package dartcraftReloaded.capablilities.Shearable;

import dartcraftReloaded.Handlers.DCRCapabilityHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by BURN447 on 7/6/2018.
 */
public class ShearableProvider implements ICapabilitySerializable<NBTBase>, ICapabilityProvider {

    private EnumFacing facing = null;
    private IShearableMob instance = null;

    public ShearableProvider(Capability<IShearableMob> capability, EnumFacing facing){
        if(capability != null){
            DCRCapabilityHandler.CAPABILITY_SHEARABLE = capability;
            this.facing = facing;
            this.instance = DCRCapabilityHandler.CAPABILITY_SHEARABLE.getDefaultInstance();
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == DCRCapabilityHandler.CAPABILITY_SHEARABLE)
            return capability == getCapability();
        else
            return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == DCRCapabilityHandler.CAPABILITY_SHEARABLE ? DCRCapabilityHandler.CAPABILITY_SHEARABLE.<T> cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return DCRCapabilityHandler.CAPABILITY_SHEARABLE.getStorage().writeNBT(DCRCapabilityHandler.CAPABILITY_SHEARABLE, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        DCRCapabilityHandler.CAPABILITY_SHEARABLE.getStorage().readNBT(DCRCapabilityHandler.CAPABILITY_SHEARABLE, instance, null, nbt);
    }

    public final Capability<IShearableMob> getCapability(){
        return DCRCapabilityHandler.CAPABILITY_SHEARABLE;
    }
}
