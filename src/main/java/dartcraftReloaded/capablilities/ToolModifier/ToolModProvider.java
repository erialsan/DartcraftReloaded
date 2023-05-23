package dartcraftReloaded.capablilities.ToolModifier;

import dartcraftReloaded.Handlers.DCRCapabilityHandler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ToolModProvider implements ICapabilitySerializable<NBTBase>, ICapabilityProvider {

    private EnumFacing facing = null;

    private IToolModifier instance = null;


    public ToolModProvider(Capability<IToolModifier> capability, EnumFacing facing){
        this.facing = facing;
        this.instance = DCRCapabilityHandler.CAPABILITY_TOOLMOD.getDefaultInstance();
    }


    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == DCRCapabilityHandler.CAPABILITY_TOOLMOD;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == DCRCapabilityHandler.CAPABILITY_TOOLMOD ? DCRCapabilityHandler.CAPABILITY_TOOLMOD.<T> cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return DCRCapabilityHandler.CAPABILITY_TOOLMOD.getStorage().writeNBT(DCRCapabilityHandler.CAPABILITY_TOOLMOD, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        DCRCapabilityHandler.CAPABILITY_TOOLMOD.getStorage().readNBT(DCRCapabilityHandler.CAPABILITY_TOOLMOD, instance, null, nbt);
    }

    public final Capability<IToolModifier> getCapability(){
        return DCRCapabilityHandler.CAPABILITY_TOOLMOD;
    }
}
