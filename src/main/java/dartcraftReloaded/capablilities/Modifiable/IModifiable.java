package dartcraftReloaded.capablilities.Modifiable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.List;
// Capability
public interface IModifiable extends INBTSerializable<NBTTagCompound> {
    void setModifier(int id, int level);
    void setModifiers(HashMap<Integer, Integer> modifiers);
    HashMap<Integer, Integer> getModifiers();
    boolean hasModifier(int id);
    boolean hasModifier(Modifier m);
    int getLevel(int id);
    int getLevel(Modifier m);

    void addText(List<String> text);

}
