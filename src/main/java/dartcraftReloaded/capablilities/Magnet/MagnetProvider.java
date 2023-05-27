package dartcraftReloaded.capablilities.Magnet;

import dartcraftReloaded.handlers.CapabilityHandler;
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
            CapabilityHandler.CAPABILITY_MAGNET = capability;
            this.facing = facing;
            this.instance = CapabilityHandler.CAPABILITY_MAGNET.getDefaultInstance();
        }
    }


    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityHandler.CAPABILITY_MAGNET)
            return capability == getCapability();
        else
            return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityHandler.CAPABILITY_MAGNET ? CapabilityHandler.CAPABILITY_MAGNET.<T> cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return CapabilityHandler.CAPABILITY_MAGNET.getStorage().writeNBT(CapabilityHandler.CAPABILITY_MAGNET, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        CapabilityHandler.CAPABILITY_MAGNET.getStorage().readNBT(CapabilityHandler.CAPABILITY_MAGNET, instance, null, nbt);
    }

    public final Capability<IMagnet> getCapability(){
        return CapabilityHandler.CAPABILITY_MAGNET;
    }
}
