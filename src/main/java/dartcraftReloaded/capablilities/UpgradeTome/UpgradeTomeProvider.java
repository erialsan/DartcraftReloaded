package dartcraftReloaded.capablilities.UpgradeTome;

import dartcraftReloaded.handlers.DCRCapabilityHandler;
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
            DCRCapabilityHandler.CAPABILITY_UPGRADETOME = capability;
            this.instance = DCRCapabilityHandler.CAPABILITY_UPGRADETOME.getDefaultInstance();
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == DCRCapabilityHandler.CAPABILITY_UPGRADETOME)
            return capability == getCapability();
        else
            return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == DCRCapabilityHandler.CAPABILITY_UPGRADETOME ? DCRCapabilityHandler.CAPABILITY_UPGRADETOME.<T> cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return DCRCapabilityHandler.CAPABILITY_UPGRADETOME.getStorage().writeNBT(DCRCapabilityHandler.CAPABILITY_UPGRADETOME, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        DCRCapabilityHandler.CAPABILITY_UPGRADETOME.getStorage().readNBT(DCRCapabilityHandler.CAPABILITY_UPGRADETOME, instance, null, nbt);
    }

    public final Capability<IUpgradeTome> getCapability(){
        return DCRCapabilityHandler.CAPABILITY_UPGRADETOME;
    }
}
