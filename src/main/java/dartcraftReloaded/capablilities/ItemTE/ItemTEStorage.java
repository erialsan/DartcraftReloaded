package dartcraftReloaded.capablilities.ItemTE;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class ItemTEStorage implements Capability.IStorage<IItemTE> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IItemTE> capability, IItemTE instance, EnumFacing side) {
        NBTTagCompound nbt2 = new NBTTagCompound();
        if (instance.getNBT() != null && instance.getBlockState() != null || instance.getBlockName() != null) {
            nbt2.setTag("nbt", instance.getNBT());
            nbt2.setString("name", instance.getBlockName());
            NBTTagCompound blockstate = new NBTTagCompound();
            NBTUtil.writeBlockState(blockstate, instance.getBlockState());
            nbt2.setTag("state", blockstate);
        }
        return nbt2;
    }

    @Override
    public void readNBT(Capability<IItemTE> capability, IItemTE instance, EnumFacing side, NBTBase nbtIn) {
        if(nbtIn instanceof NBTTagCompound){
            NBTTagCompound nbt = ((NBTTagCompound) nbtIn);
            if (nbt.hasKey("nbt") && nbt.hasKey("name") && nbt.hasKey("state")) {
                instance.setNBT(nbt.getCompoundTag("nbt"));
                instance.setBlockName(nbt.getString("name"));
                instance.setBlockState(NBTUtil.readBlockState(nbt.getCompoundTag("state")));
            }
        }
    }
}
