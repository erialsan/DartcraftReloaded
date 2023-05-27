package dartcraftReloaded.capablilities.ExperienceTome;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Created by BURN447 on 6/10/2018.
 */
public interface IExperienceTome extends INBTSerializable<NBTTagCompound> {

    int getExperienceValue();
    void addToExperienceValue(int a);
    void subtractFromExperienceValue(int a);
    void setExperienceValue(int newExp);

}
