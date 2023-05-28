package dartcraftReloaded.potion;

import dartcraftReloaded.handlers.PotionHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;

import javax.vecmath.Vector3d;
import java.util.List;

public class PotionMagnet extends Potion {
    public PotionMagnet() {
        super(false, 0);
        this.setRegistryName("magnet");
        this.setPotionName("Magnet");
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
        return true;
    }

    @Override
    public boolean shouldRender(PotionEffect effect) {
        return false;
    }


    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        //Inspired by Botania Code

        double x = entity.posX;
        double y = entity.posY;
        double z = entity.posZ;
        double range = 10.0d;
        PotionEffect activePotionEffect = entity.getActivePotionEffect(PotionHandler.potionMagnet);
        if(activePotionEffect != null) {
            range += activePotionEffect.getAmplifier() * 0.3f;
        }

        List<EntityItem> items = entity.getEntityWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x - range, y - range, z - range, x + range, y + range, z + range));
        for(EntityItem item : items) {
            if(item.getItem().isEmpty() || item.isDead) {
                continue;
            }

            // constant force!
            float strength = 0.07f;

            // calculate direction: item -> player
            Vector3d vec = new Vector3d(x, y, z);
            vec.sub(new Vector3d(item.posX, item.posY, item.posZ));

            vec.normalize();
            vec.scale(strength);

            // we calculated the movement vector and set it to the correct strength.. now we apply it \o/
            item.motionX += vec.x;
            item.motionY += vec.y;
            item.motionZ += vec.z;
        }
    }

}
