package dartcraftReloaded.capablilities.ItemTE;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;

import java.util.concurrent.Callable;

public class ItemTEFactory implements Callable<IItemTE> {
    @Override
    public IItemTE call() {
        return new IItemTE() {

            private NBTTagCompound nbt;
            private String name;
            private IBlockState state;

            @Override
            public void setNBT(NBTTagCompound nbt) {
                this.nbt = nbt;
            }

            @Override
            public void setBlockName(String name) {
                this.name = name;
            }

            @Override
            public void setBlockState(IBlockState state) {
                this.state = state;
            }

            @Override
            public NBTTagCompound getNBT() {
                return nbt;
            }

            @Override
            public String getBlockName() {
                return name;
            }

            @Override
            public IBlockState getBlockState() {
                return state;
            }

            @Override
            public NBTTagCompound serializeNBT() {
                NBTTagCompound nbt2 = new NBTTagCompound();
                nbt2.setTag("nbt", nbt);
                nbt2.setString("name", name);
                NBTTagCompound blockstate = new NBTTagCompound();
                NBTUtil.writeBlockState(blockstate, state);
                nbt2.setTag("state", blockstate);
                return nbt2;
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
                this.nbt = nbt.getCompoundTag("nbt");
                this.name = nbt.getString("name");
                this.state = NBTUtil.readBlockState(nbt.getCompoundTag("state"));
            }
        };
    }
}
