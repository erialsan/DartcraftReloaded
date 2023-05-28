package dartcraftReloaded.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

public class PotionBleeding extends Potion {

    public PotionBleeding(){
        super(true, 0);
        this.setRegistryName("bleeding");
        this.setPotionName("Bleeding");
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean hasStatusIcon() {
        return true;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.attackEntityFrom(DamageSource.GENERIC, amplifier);
    }
}
