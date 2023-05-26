package dartcraftReloaded.capablilities.ExperienceTome;

import dartcraftReloaded.handlers.DCRCapabilityHandler;
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
    private IExperienceTome instacne = null;

    public ExperienceTomeProvider(Capability<IExperienceTome> capability, EnumFacing facing){
        if(capability != null){
            DCRCapabilityHandler.CAPABILITY_EXPTOME = capability;
            this.facing = facing;
            this.instacne = DCRCapabilityHandler.CAPABILITY_EXPTOME.getDefaultInstance();
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == DCRCapabilityHandler.CAPABILITY_EXPTOME)
            return capability == getCapability();
        else
            return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == DCRCapabilityHandler.CAPABILITY_EXPTOME ? DCRCapabilityHandler.CAPABILITY_EXPTOME.<T> cast(instacne) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return DCRCapabilityHandler.CAPABILITY_EXPTOME.getStorage().writeNBT(DCRCapabilityHandler.CAPABILITY_EXPTOME, instacne, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        DCRCapabilityHandler.CAPABILITY_EXPTOME.getStorage().readNBT(DCRCapabilityHandler.CAPABILITY_EXPTOME, instacne, null, nbt);
    }

    public final Capability<IExperienceTome> getCapability(){
        return DCRCapabilityHandler.CAPABILITY_EXPTOME;
    }
}
