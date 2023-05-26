package dartcraftReloaded.capablilities.Modifiable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.HashMap;

public class ModifiableStorage implements Capability.IStorage<IModifiable> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IModifiable> capability, IModifiable instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        HashMap<Integer, Integer> modifiers = instance.getModifiers();
        int[] keys = new int[modifiers.size()];
        int counter = 0;
        for (int i : modifiers.keySet()) {
            keys[counter] = i;
            counter++;
            nbt.setInteger(String.valueOf(i), modifiers.get(i));
        }

        nbt.setIntArray("keys", keys);
        return nbt;
    }

    @Override
    public void readNBT(Capability<IModifiable> capability, IModifiable instance, EnumFacing side, NBTBase nbtIn) {
        if(nbtIn instanceof NBTTagCompound){
            NBTTagCompound nbt = ((NBTTagCompound) nbtIn);
            HashMap<Integer, Integer> modifiers = new HashMap<>();
            int[] keys = nbt.getIntArray("keys");
            for (int i : keys) {
                modifiers.put(i, nbt.getInteger(String.valueOf(i)));
            }
            instance.setModifiers(modifiers);
        }
    }
}
