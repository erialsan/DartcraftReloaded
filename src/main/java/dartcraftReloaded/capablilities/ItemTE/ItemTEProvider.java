package dartcraftReloaded.capablilities.ItemTE;

import dartcraftReloaded.handlers.CapabilityHandler;
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
        this.instance = CapabilityHandler.CAPABILITY_TE.getDefaultInstance();
    }


    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityHandler.CAPABILITY_TE;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityHandler.CAPABILITY_TE ? CapabilityHandler.CAPABILITY_TE.<T> cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return CapabilityHandler.CAPABILITY_TE.getStorage().writeNBT(CapabilityHandler.CAPABILITY_TE, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        CapabilityHandler.CAPABILITY_TE.getStorage().readNBT(CapabilityHandler.CAPABILITY_TE, instance, null, nbt);
    }

    public final Capability<IItemTE> getCapability(){
        return CapabilityHandler.CAPABILITY_TE;
    }
}
