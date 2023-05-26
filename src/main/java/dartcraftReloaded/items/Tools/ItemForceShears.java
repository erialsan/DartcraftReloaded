package dartcraftReloaded.items.Tools;

import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.handlers.DCRCapabilityHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.util.List;
import java.util.Random;

/**
 * Created by BURN447 on 6/9/2018.
 */
public class ItemForceShears extends ItemShears {

    String name;

    public ItemForceShears(String name){
        this.setRegistryName(name);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        this.setTranslationKey(name);
        this.name = name;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {


        if(entity instanceof EntityCow) {
            if (entity.hasCapability(DCRCapabilityHandler.CAPABILITY_SHEARABLE, null)) {
                if(entity.getCapability(DCRCapabilityHandler.CAPABILITY_SHEARABLE, null).canBeSheared()) {

                    if (entity.world.isRemote) {
                        return false;
                    }

                    java.util.Random rand = new java.util.Random();
                    int i = 1 + rand.nextInt(3);

                    java.util.List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
                    for (int j = 0; j < i; ++j)
                        ret.add(new ItemStack(Items.LEATHER, 1));

                    entity.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
                    dropItems(entity, ret, rand);
                    entity.getCapability(DCRCapabilityHandler.CAPABILITY_SHEARABLE, null).setSheared(true);
                    return true;
                }
            }
        }
        if(entity instanceof EntityChicken) {
            if (entity.hasCapability(DCRCapabilityHandler.CAPABILITY_SHEARABLE, null)) {
                if(entity.getCapability(DCRCapabilityHandler.CAPABILITY_SHEARABLE, null).canBeSheared()) {

                    if (entity.world.isRemote) {
                        return false;
                    }

                    java.util.Random rand = new java.util.Random();
                    int i = 1 + rand.nextInt(3);

                    java.util.List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
                    for (int j = 0; j < i; ++j)
                        ret.add(new ItemStack(Items.FEATHER, 1));

                    entity.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
                    dropItems(entity, ret, rand);
                    entity.getCapability(DCRCapabilityHandler.CAPABILITY_SHEARABLE, null).setSheared(true);
                    return true;
                }
            }
        }
        return super.itemInteractionForEntity(itemstack, player, entity, hand);
    }

    private void dropItems(EntityLivingBase entity, List<ItemStack> drops, Random rand) {
        for (ItemStack stack : drops) {
            net.minecraft.entity.item.EntityItem ent = entity.entityDropItem(stack, 1.0F);
            ent.motionY += rand.nextFloat() * 0.05F;
            ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
            ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
        }
    }


    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}
