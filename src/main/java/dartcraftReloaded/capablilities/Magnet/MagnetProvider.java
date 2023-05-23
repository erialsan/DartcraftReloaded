package dartcraftReloaded.capablilities.Magnet;

import dartcraftReloaded.Handlers.DCRCapabilityHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MagnetProvider implements ICapabilitySerializable<NBTBase>, ICapabilityProvider {

    private EnumFacing facing = null;

    private IMagnet instance = null;


    public MagnetProvider(Capability<IMagnet> capability, EnumFacing facing){
        if(capability != null){
            DCRCapabilityHandler.CAPABILITY_MAGNET = capability;
            this.facing = facing;
            this.instance = DCRCapabilityHandler.CAPABILITY_MAGNET.getDefaultInstance();
        }
    }


    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == DCRCapabilityHandler.CAPABILITY_MAGNET)
            return capability == getCapability();
        else
            return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == DCRCapabilityHandler.CAPABILITY_MAGNET ? DCRCapabilityHandler.CAPABILITY_MAGNET.<T> cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return DCRCapabilityHandler.CAPABILITY_MAGNET.getStorage().writeNBT(DCRCapabilityHandler.CAPABILITY_MAGNET, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        DCRCapabilityHandler.CAPABILITY_MAGNET.getStorage().readNBT(DCRCapabilityHandler.CAPABILITY_MAGNET, instance, null, nbt);
    }

    public final Capability<IMagnet> getCapability(){
        return DCRCapabilityHandler.CAPABILITY_MAGNET;
    }
}
