package dartcraftReloaded.capablilities.ExperienceTome;

import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by BURN447 on 6/10/2018.
 */
public class ExperienceTomeProvider implements ICapabilitySerializable<NBTBase>, ICapabilityProvider {

    private EnumFacing facing = null;
    private IExperienceTome instance = null;

    public ExperienceTomeProvider(Capability<IExperienceTome> capability, EnumFacing facing){
        if(capability != null){
            CapabilityHandler.CAPABILITY_EXPTOME = capability;
            this.facing = facing;
            this.instance = CapabilityHandler.CAPABILITY_EXPTOME.getDefaultInstance();
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityHandler.CAPABILITY_EXPTOME)
            return capability == getCapability();
        else
            return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityHandler.CAPABILITY_EXPTOME ? CapabilityHandler.CAPABILITY_EXPTOME.cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return CapabilityHandler.CAPABILITY_EXPTOME.getStorage().writeNBT(CapabilityHandler.CAPABILITY_EXPTOME, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        CapabilityHandler.CAPABILITY_EXPTOME.getStorage().readNBT(CapabilityHandler.CAPABILITY_EXPTOME, instance, null, nbt);
    }

    public final Capability<IExperienceTome> getCapability(){
        return CapabilityHandler.CAPABILITY_EXPTOME;
    }
}
