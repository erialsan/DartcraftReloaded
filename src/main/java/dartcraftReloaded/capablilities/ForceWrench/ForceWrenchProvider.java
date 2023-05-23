package dartcraftReloaded.capablilities.ForceWrench;

import dartcraftReloaded.Handlers.DCRCapabilityHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by BURN447 on 7/17/2018.
 */
public class ForceWrenchProvider implements ICapabilitySerializable<NBTBase>, ICapabilityProvider {

    private EnumFacing facing = null;
    private IForceWrench instance = null;

    public ForceWrenchProvider(Capability<IForceWrench> capability, EnumFacing facing){
        if(capability != null){
            DCRCapabilityHandler.CAPABILITY_FORCEWRENCH = capability;
            this.facing = facing;
            this.instance = DCRCapabilityHandler.CAPABILITY_FORCEWRENCH.getDefaultInstance();
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == DCRCapabilityHandler.CAPABILITY_FORCEWRENCH)
            return capability == getCapability();
        else
            return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == DCRCapabilityHandler.CAPABILITY_FORCEWRENCH ? DCRCapabilityHandler.CAPABILITY_FORCEWRENCH.<T> cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return DCRCapabilityHandler.CAPABILITY_FORCEWRENCH.getStorage().writeNBT(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        DCRCapabilityHandler.CAPABILITY_FORCEWRENCH.getStorage().readNBT(DCRCapabilityHandler.CAPABILITY_FORCEWRENCH, instance, null, nbt);
    }

    public final Capability<IForceWrench> getCapability(){
        return DCRCapabilityHandler.CAPABILITY_FORCEWRENCH;
    }
}
