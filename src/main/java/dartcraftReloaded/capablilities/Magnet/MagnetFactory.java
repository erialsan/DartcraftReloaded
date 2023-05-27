package dartcraftReloaded.capablilities.Magnet;

import net.minecraft.nbt.NBTTagCompound;

import java.util.concurrent.Callable;

public class MagnetFactory implements Callable<IMagnet> {
    @Override
    public IMagnet call() throws Exception {
        return new IMagnet() {
            boolean active;

            @Override
            public boolean isActivated() {
                return active;
            }

            @Override
            public void activate() {
                active = true;
            }

            @Override
            public void deactivate() {
                active = false;
            }

            @Override
            public void setActivation(boolean active) {
                this.active = active;
            }

            @Override
            public NBTTagCompound serializeNBT() {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setBoolean("active", active);
                return nbt;
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
                active = nbt.getBoolean("active");
            }
        };
    }
}
