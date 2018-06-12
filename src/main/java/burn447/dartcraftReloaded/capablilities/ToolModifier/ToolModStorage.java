package burn447.dartcraftReloaded.capablilities.ToolModifier;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class ToolModStorage implements Capability.IStorage<IToolModifier> {


    public ToolModStorage(){
    }

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IToolModifier> capability, IToolModifier instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setFloat("efficiency", instance.getEfficiency());
        nbt.setBoolean("heat", instance.hasHeat());
        nbt.setFloat("knockback", instance.getKnockback());
        nbt.setBoolean("grinding", instance.hasGrinding());
        nbt.setBoolean("silkTouch", instance.hasTouch());
        nbt.setFloat("damage", instance.getAttackDamage());
        nbt.setBoolean("lumberjack", instance.hasLumberJack());
        nbt.setBoolean("ender", instance.hasEnder());
        nbt.setBoolean("rainbow", instance.hasRainbow());

        //Luck
        nbt.setBoolean("luckOne", instance.hasLuckLevel(1));            nbt.setBoolean("luckTwo", instance.hasLuckLevel(2));
        nbt.setBoolean("luckThree", instance.hasLuckLevel(3));          nbt.setBoolean("luckFour", instance.hasLuckLevel(4));

            //Light
        nbt.setBoolean("lightOne", instance.hasLightLevel(1));          nbt.setBoolean("lightTwo", instance.hasLightLevel(2));
        nbt.setBoolean("lightThree", instance.hasLightLevel(3));        nbt.setBoolean("lightFour", instance.hasLightLevel(4));
        nbt.setBoolean("lightFive", instance.hasLightLevel(5));

            //Sturdy
        nbt.setBoolean("sturdyOne", instance.hasSturdyLevel(1));        nbt.setBoolean("sturdyTwo", instance.hasSturdyLevel(2));
        nbt.setBoolean("sturdyThree", instance.hasSturdyLevel(3));

        return nbt;
    }

    @Override
    public void readNBT(Capability<IToolModifier> capability, IToolModifier instance, EnumFacing side, NBTBase nbtIn) {
        if(nbtIn instanceof NBTTagCompound){
            NBTTagCompound nbt = (NBTTagCompound) nbtIn;
            instance.setEfficiency(nbt.getFloat("efficiency"));
            instance.setHeat(nbt.getBoolean("heat"));
            instance.setKnockback(nbt.getFloat("knockback"));
            instance.setGrinding(nbt.getBoolean("grinding"));
            instance.setTouch(nbt.getBoolean("silkTouch"));
            instance.setAttackDamage(nbt.getFloat("damage"));
            instance.setLumberJack(nbt.getBoolean("lumberjack"));
            instance.setEnder(nbt.getBoolean("ender"));
            instance.setRainbow(nbt.getBoolean("rainbow"));

            //Luck
            instance.setLuckOne(nbt.getBoolean("luckOne"));     instance.setLuckTwo(nbt.getBoolean("luckTwo"));
            instance.setLuckThree(nbt.getBoolean("luckThree")); instance.setLuckFour(nbt.getBoolean("luckFour"));

            //Light
            instance.setLightOne(nbt.getBoolean("lightOne"));       instance.setLightTwo(nbt.getBoolean("lightTwo"));
            instance.setLightThree(nbt.getBoolean("lightThree"));   instance.setLightFour(nbt.getBoolean("lightFour"));
            instance.setLightFive(nbt.getBoolean("lightFive"));

            //Sturdy
            instance.setSturdy(1, nbt.getBoolean("sturdyOne"));     instance.setSturdy(2, nbt.getBoolean("sturdyTwo"));
            instance.setSturdy(3, nbt.getBoolean("sturdyThree"));

        }
    }


}
