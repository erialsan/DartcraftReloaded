package dartcraftReloaded.capablilities.UpgradeTome;

import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UpgradeTomeProvider implements ICapabilitySerializable<NBTBase>, ICapabilityProvider {

    private IUpgradeTome instance = null;

    public UpgradeTomeProvider(Capability<IUpgradeTome> capability, EnumFacing facing){
        if(capability != null){
            CapabilityHandler.CAPABILITY_UPGRADETOME = capability;
            this.instance = CapabilityHandler.CAPABILITY_UPGRADETOME.getDefaultInstance();
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityHandler.CAPABILITY_UPGRADETOME)
            return capability == getCapability();
        else
            return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityHandler.CAPABILITY_UPGRADETOME ? CapabilityHandler.CAPABILITY_UPGRADETOME.<T> cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return CapabilityHandler.CAPABILITY_UPGRADETOME.getStorage().writeNBT(CapabilityHandler.CAPABILITY_UPGRADETOME, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        CapabilityHandler.CAPABILITY_UPGRADETOME.getStorage().readNBT(CapabilityHandler.CAPABILITY_UPGRADETOME, instance, null, nbt);
    }

    public final Capability<IUpgradeTome> getCapability(){
        return CapabilityHandler.CAPABILITY_UPGRADETOME;
    }
}
