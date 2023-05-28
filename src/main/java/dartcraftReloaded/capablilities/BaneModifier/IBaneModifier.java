package dartcraftReloaded.capablilities.BaneModifier;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
public interface IBaneModifier extends INBTSerializable<NBTTagCompound> {

    boolean canTeleport();
    void setTeleportAbility(boolean canTeleport);
}
