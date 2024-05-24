package dartcraftReloaded.capablilities.UpgradeTome;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public interface IUpgradeTome extends INBTSerializable<NBTTagCompound> {
    int getLevel();
    void setLevel(int level);
    int getUpgradePoints();
    void setUpgradePoints(int points);
    List<Integer> getSeenModifiers();
    void addSeenModifier(int mod);
}
