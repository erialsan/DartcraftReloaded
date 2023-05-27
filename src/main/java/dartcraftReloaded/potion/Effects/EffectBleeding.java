package dartcraftReloaded.potion.Effects;

import dartcraftReloaded.handlers.PotionHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

/**
 * Created by BURN447 on 6/14/2018.
 */
public class EffectBleeding extends PotionEffect {

    public EffectBleeding(int duration) {
        super(PotionHandler.potionBleeding, duration, 0, false, true);
    }

    @Override
    public void performEffect(EntityLivingBase entityIn) {
        entityIn.attackEntityFrom(DamageSource.GENERIC, 1);
    }
}
