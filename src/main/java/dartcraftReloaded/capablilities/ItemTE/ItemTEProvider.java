package dartcraftReloaded.capablilities.ItemTE;

import dartcraftReloaded.handlers.DCRCapabilityHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemTEProvider implements ICapabilitySerializable<NBTBase>, ICapabilityProvider {
    private IItemTE instance = null;

    public ItemTEProvider(Capability<IItemTE> capability, EnumFacing facing){
        this.instance = DCRCapabilityHandler.CAPABILITY_TE.getDefaultInstance();
    }


    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == DCRCapabilityHandler.CAPABILITY_TE;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == DCRCapabilityHandler.CAPABILITY_TE ? DCRCapabilityHandler.CAPABILITY_TE.<T> cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return DCRCapabilityHandler.CAPABILITY_TE.getStorage().writeNBT(DCRCapabilityHandler.CAPABILITY_TE, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        DCRCapabilityHandler.CAPABILITY_TE.getStorage().readNBT(DCRCapabilityHandler.CAPABILITY_TE, instance, null, nbt);
    }

    public final Capability<IItemTE> getCapability(){
        return DCRCapabilityHandler.CAPABILITY_TE;
    }
}
