package dartcraftReloaded.capablilities.UpgradeTome;

import net.minecraft.nbt.NBTTagCompound;

import java.util.concurrent.Callable;

public class UpgradeTomeFactory implements Callable<IUpgradeTome> {
    @Override
    public IUpgradeTome call() throws Exception {
        return new IUpgradeTome() {

            @Override
            public int getLevel() {
                return level;
            }

            @Override
            public void setLevel(int level) {
                this.level = level;
            }

            @Override
            public int getUpgradePoints() {
                return points;
            }

            @Override
            public void setUpgradePoints(int points) {
                this.points = points;
            }

            @Override
            public NBTTagCompound serializeNBT() {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setInteger("points", points);
                nbt.setInteger("level", level);
                return nbt;
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
                this.points = nbt.getInteger("points");
                this.level = nbt.getInteger("level");
            }

            private int level = 1;
            private int points = 0;



        };
    }
}
