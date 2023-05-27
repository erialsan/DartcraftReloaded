package dartcraftReloaded.capablilities.ExperienceTome;

import net.minecraft.nbt.NBTTagCompound;

import java.util.concurrent.Callable;

/**
 * Created by BURN447 on 6/10/2018.
 */
public class ExperienceTomeFactory implements Callable<IExperienceTome> {
    @Override
    public IExperienceTome call() throws Exception {
        return new IExperienceTome() {

            private int experienceStored = 0;

            @Override
            public int getExperienceValue() {
                return experienceStored;
            }

            @Override
            public void addToExperienceValue(int a) {
                experienceStored += a;
            }

            @Override
            public void subtractFromExperienceValue(int a) {
                experienceStored -= a;
            }

            @Override
            public void setExperienceValue(int newExp) {
                experienceStored = newExp;
            }

            @Override
            public NBTTagCompound serializeNBT() {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setInteger("experience", experienceStored);
                return nbt;
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
                experienceStored = nbt.getInteger("experience");
            }
        };
    }
}
