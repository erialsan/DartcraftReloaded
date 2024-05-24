package dartcraftReloaded.capablilities.Modifiable;

import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class ModifiableProvider implements ICapabilitySerializable<NBTBase>, ICapabilityProvider {
    private IModifiable instance = null;

    public ModifiableProvider(){
        this.instance = CapabilityHandler.CAPABILITY_MODIFIABLE.getDefaultInstance();
    }


    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityHandler.CAPABILITY_MODIFIABLE;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityHandler.CAPABILITY_MODIFIABLE ? CapabilityHandler.CAPABILITY_MODIFIABLE.<T> cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return CapabilityHandler.CAPABILITY_MODIFIABLE.getStorage().writeNBT(CapabilityHandler.CAPABILITY_MODIFIABLE, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        CapabilityHandler.CAPABILITY_MODIFIABLE.getStorage().readNBT(CapabilityHandler.CAPABILITY_MODIFIABLE, instance, null, nbt);
    }

    public final Capability<IModifiable> getCapability(){
        return CapabilityHandler.CAPABILITY_MODIFIABLE;
    }
}
