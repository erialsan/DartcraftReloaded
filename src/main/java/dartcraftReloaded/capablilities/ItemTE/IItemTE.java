package dartcraftReloaded.capablilities.ItemTE;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IItemTE extends INBTSerializable<NBTTagCompound> {
    void setNBT(NBTTagCompound nbt);
    void setBlockName(String name);
    void setBlockState(IBlockState state);
    NBTTagCompound getNBT();
    String getBlockName();
    IBlockState getBlockState();

}
