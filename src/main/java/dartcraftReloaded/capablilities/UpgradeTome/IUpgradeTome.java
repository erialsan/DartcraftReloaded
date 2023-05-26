package dartcraftReloaded.capablilities.UpgradeTome;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IUpgradeTome extends INBTSerializable<NBTTagCompound> {
    int getLevel();
    void setLevel(int level);
    int getUpgradePoints();
    void setUpgradePoints(int points);

}
