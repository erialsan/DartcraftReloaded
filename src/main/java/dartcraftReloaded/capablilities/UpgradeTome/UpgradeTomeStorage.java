package dartcraftReloaded.capablilities.UpgradeTome;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class UpgradeTomeStorage implements Capability.IStorage<IUpgradeTome> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IUpgradeTome> capability, IUpgradeTome instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("level", instance.getLevel());
        nbt.setInteger("points", instance.getUpgradePoints());

        return nbt;
    }

    @Override
    public void readNBT(Capability<IUpgradeTome> capability, IUpgradeTome instance, EnumFacing side, NBTBase nbtIn) {
        if(nbtIn instanceof NBTTagCompound) {
            NBTTagCompound nbt = (NBTTagCompound) nbtIn;
            instance.setLevel(nbt.getInteger("level"));
            instance.setUpgradePoints(nbt.getInteger("points"));
        }
    }
}
