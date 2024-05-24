package dartcraftReloaded.capablilities.UpgradeTome;

import dartcraftReloaded.tileEntity.TileEntityInfuser;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class UpgradeTomeFactory implements Callable<IUpgradeTome> {
    @Override
    public IUpgradeTome call() {
        return new IUpgradeTome() {

            private int level = 1;
            private int points = 0;
            private List<Integer> seen = new ArrayList<>();

            @Override
            public int getLevel() {
                return level;
            }

            @Override
            public void setLevel(int level) {
                this.level = level;
                if (this.level < 1) this.level = 1;
                if (this.level > 8) this.level = 8;
            }

            @Override
            public int getUpgradePoints() {
                return points;
            }

            @Override
            public void setUpgradePoints(int points) {
                this.points = points;
                if (this.points < 0) this.points = 0;
                while (this.points > TileEntityInfuser.getThreshold(getLevel())) {
                    this.points -= TileEntityInfuser.getThreshold(getLevel());
                    setLevel(getLevel() + 1);
                }
            }

            @Override
            public List<Integer> getSeenModifiers() {
                return seen;
            }

            @Override
            public void addSeenModifier(int mod) {
                seen.add(mod);
            }

            @Override
            public NBTTagCompound serializeNBT() {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setInteger("points", points);
                nbt.setInteger("level", level);
                int[] array = new int[seen.size()];
                for (int i = 0; i < seen.size(); i++) {
                    array[i] = seen.get(i);
                }
                nbt.setIntArray("seen", array);
                return nbt;
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
                this.points = nbt.getInteger("points");
                this.level = nbt.getInteger("level");
                int[] array = nbt.getIntArray("seen");
                seen.clear();
                for (int i : array) {
                    seen.add(i);
                }
            }
        };
    }
}
