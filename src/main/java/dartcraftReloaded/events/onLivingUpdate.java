package dartcraftReloaded.events;

import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.util.ReflectionUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class onLivingUpdate {

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            int speedLevel = 0;
            int luckLevel = 0;
            boolean sight = false;
            boolean camo = false;
            for (ItemStack i : player.inventory.armorInventory) {
                if (i.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
                    IModifiable cap = i.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
                    if (cap.hasModifier(Constants.SPEED)) {
                        speedLevel += cap.getLevel(Constants.SPEED);
                    }
                    if (cap.hasModifier(Constants.LUCK)) {
                        luckLevel += cap.getLevel(Constants.LUCK);
                    }
                    if (cap.hasModifier(Constants.SIGHT)) sight = true;
                    if (cap.hasModifier(Constants.CAMO)) camo = true;
                }
            }
            if (speedLevel > 0) {
                player.addPotionEffect(new PotionEffect(Potion.getPotionById(1), 8, speedLevel));
                if (speedLevel / 4 > 0) {
                    player.addPotionEffect(new PotionEffect(Potion.getPotionById(3), 8, speedLevel / 4));
                }
            }
            if (luckLevel > 0) {
                player.addPotionEffect(new PotionEffect(Potion.getPotionById(26), 8, luckLevel));
            }
            if (sight) {
                player.addPotionEffect(new PotionEffect(Potion.getPotionById(16), 8));
            }
            if (camo) {
                player.addPotionEffect(new PotionEffect(Potion.getPotionById(14), 8));
            }


            ItemStack heldItem = player.getHeldItemMainhand();
            if (!(heldItem.getItem() instanceof ItemBow)) {
                heldItem = player.getHeldItem(EnumHand.OFF_HAND);
            }
            if ((heldItem.getItem() instanceof ItemBow)) {

                if (player.isHandActive()) {
                    if (heldItem.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
                    IModifiable cap = heldItem.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
                        if (cap.hasModifier(Constants.SPEED)) {
                            for (int i = 0; i < cap.getLevel(Constants.SPEED); i++) {
                                ReflectionUtil.callPrivateMethod(EntityLivingBase.class, player, "updateActiveHand", "func_184608_ct");
                            }
                        }
                    }
                }
            }
        }
    }
}
