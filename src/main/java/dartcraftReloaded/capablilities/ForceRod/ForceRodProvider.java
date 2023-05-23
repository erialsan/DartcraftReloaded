package dartcraftReloaded.capablilities.ForceRod;

import dartcraftReloaded.Handlers.DCRCapabilityHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by BURN447 on 6/7/2018.
 */
public class ForceRodProvider implements ICapabilitySerializable<NBTBase>, ICapabilityProvider {

    private EnumFacing facing = null;
    private IForceRodModifier instance = null;

    public ForceRodProvider(Capability<IForceRodModifier> capability, EnumFacing facing){
        if(capability != null){
            DCRCapabilityHandler.CAPABILITY_FORCEROD = capability;
            this.facing = facing;
            this.instance = DCRCapabilityHandler.CAPABILITY_FORCEROD.getDefaultInstance();
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == DCRCapabilityHandler.CAPABILITY_FORCEROD)
            return capability == getCapability();
        else
            return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == DCRCapabilityHandler.CAPABILITY_FORCEROD ? DCRCapabilityHandler.CAPABILITY_FORCEROD.<T> cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return DCRCapabilityHandler.CAPABILITY_FORCEROD.getStorage().writeNBT(DCRCapabilityHandler.CAPABILITY_FORCEROD, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        DCRCapabilityHandler.CAPABILITY_FORCEROD.getStorage().readNBT(DCRCapabilityHandler.CAPABILITY_FORCEROD, instance, null, nbt);
    }

    public final Capability<IForceRodModifier> getCapability(){
        return DCRCapabilityHandler.CAPABILITY_FORCEROD;
    }
}
