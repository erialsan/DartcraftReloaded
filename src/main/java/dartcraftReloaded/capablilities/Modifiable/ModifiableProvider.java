package dartcraftReloaded.capablilities.Modifiable;

import dartcraftReloaded.handlers.DCRCapabilityHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ModifiableProvider implements ICapabilitySerializable<NBTBase>, ICapabilityProvider {
    private IModifiable instance = null;

    public ModifiableProvider(Capability<IModifiable> capability, EnumFacing facing){
        this.instance = DCRCapabilityHandler.CAPABILITY_MODIFIABLE.getDefaultInstance();
    }


    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == DCRCapabilityHandler.CAPABILITY_MODIFIABLE;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == DCRCapabilityHandler.CAPABILITY_MODIFIABLE ? DCRCapabilityHandler.CAPABILITY_MODIFIABLE.<T> cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return DCRCapabilityHandler.CAPABILITY_MODIFIABLE.getStorage().writeNBT(DCRCapabilityHandler.CAPABILITY_MODIFIABLE, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        DCRCapabilityHandler.CAPABILITY_MODIFIABLE.getStorage().readNBT(DCRCapabilityHandler.CAPABILITY_MODIFIABLE, instance, null, nbt);
    }

    public final Capability<IModifiable> getCapability(){
        return DCRCapabilityHandler.CAPABILITY_MODIFIABLE;
    }
}
