package dartcraftReloaded.capablilities.BaneModifier;

import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
public class BaneProvider implements ICapabilitySerializable<NBTBase>, ICapabilityProvider {


    private IBaneModifier instance = null;


    public BaneProvider(Capability<IBaneModifier> capability){
        if(capability != null){
            CapabilityHandler.CAPABILITY_BANE = capability;
            this.instance = CapabilityHandler.CAPABILITY_BANE.getDefaultInstance();
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityHandler.CAPABILITY_BANE)
            return capability == getCapability();
        else
            return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityHandler.CAPABILITY_BANE ? CapabilityHandler.CAPABILITY_BANE.<T> cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return CapabilityHandler.CAPABILITY_BANE.getStorage().writeNBT(CapabilityHandler.CAPABILITY_BANE, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        CapabilityHandler.CAPABILITY_BANE.getStorage().readNBT(CapabilityHandler.CAPABILITY_BANE, instance, null, nbt);
    }

    public Capability<IBaneModifier> getCapability(){
        return CapabilityHandler.CAPABILITY_BANE;
    }
}
