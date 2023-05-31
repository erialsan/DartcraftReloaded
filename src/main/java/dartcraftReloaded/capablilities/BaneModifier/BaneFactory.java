package dartcraftReloaded.capablilities.BaneModifier;

import net.minecraft.nbt.NBTTagCompound;

import java.util.concurrent.Callable;

public class BaneFactory implements Callable<IBaneModifier> {
    @Override
    public IBaneModifier call() throws Exception {
        return new IBaneModifier() {

            boolean canDoAbility = true;

            @Override
            public boolean canDoAbility() {
                return canDoAbility;
            }

            @Override
            public void setAbility(boolean canTeleport) {
                this.canDoAbility = canTeleport;
            }

            @Override
            public NBTTagCompound serializeNBT() {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setBoolean("canTeleport", canDoAbility);
                return nbt;
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
                canDoAbility = nbt.getBoolean("canTeleport");
            }
        };
    }
}
