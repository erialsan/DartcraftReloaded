package dartcraftReloaded.items.tools;

import dartcraftReloaded.Constants;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.capablilities.Modifiable.IModifiableTool;
import dartcraftReloaded.capablilities.Modifiable.ModifiableProvider;
import dartcraftReloaded.entity.EntityColdChicken;
import dartcraftReloaded.entity.EntityColdCow;
import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemForceShears extends ItemShears implements IModifiableTool {

    public ItemForceShears(){
        this.setRegistryName(Constants.FORCE_SHEARS);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        this.setTranslationKey(Constants.FORCE_SHEARS);
        setMaxDamage(768);
    }


    private void damage(ItemStack stack, EntityLivingBase entity) {
        IModifiable cap = stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        if (cap.hasModifier(Constants.IMPERVIOUS)) {
            stack.setItemDamage(0);
            return;
        }
        if (cap.hasModifier(Constants.REPAIR)) {
            if (Math.random() < .2*cap.getLevel(Constants.REPAIR)) {
                stack.setItemDamage(Math.max(stack.getItemDamage() - 1, 0));
                return;
            }
        }
        if (cap.hasModifier(Constants.STURDY)) {
            if (Math.random() > 1.0 / (1.0 + (double) cap.getLevel(Constants.STURDY))) {
                return;
            }
        }
        stack.damageItem(1, entity);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {


        if(entity instanceof EntityCow && !(entity instanceof EntityColdCow) && !player.world.isRemote) {
            BlockPos pos = entity.getPosition();
            float yaw = entity.rotationYaw;
            float pitch = entity.rotationPitch;
            entity.world.removeEntity(entity);
            EntityColdCow cow = new EntityColdCow(player.world);
            cow.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), yaw, pitch);
            cow.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
            cow.attackEntityAsMob(player);
            player.world.spawnEntity(cow);
            cow.attackEntityFrom(DamageSource.ON_FIRE, 1);
            player.world.spawnEntity(new EntityItem(player.world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.LEATHER, (int) (2+Math.random()*2*itemstack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).getLevel(Constants.LUCK)))));
            damage(itemstack, player);

        } else if(entity instanceof EntityChicken && !(entity instanceof EntityColdChicken) && !player.world.isRemote) {
            BlockPos pos = entity.getPosition();
            float yaw = entity.rotationYaw;
            float pitch = entity.rotationPitch;
            entity.world.removeEntity(entity);
            EntityColdChicken chicken = new EntityColdChicken(player.world);
            chicken.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), yaw, pitch);
            chicken.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
            chicken.attackEntityAsMob(player);
            player.world.spawnEntity(chicken);
            chicken.attackEntityFrom(DamageSource.ON_FIRE, 1);
            player.world.spawnEntity(new EntityItem(player.world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.LEATHER, (int) (2+Math.random()*2*itemstack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).getLevel(Constants.LUCK)))));
            damage(itemstack, player);

        } else if(itemstack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).hasModifier(Constants.RAINBOW)) {
            if (entity.world.isRemote) {
                return false;
            }
            if (entity instanceof IShearable) {
                IShearable target = (IShearable) entity;
                BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);
                if (target.isShearable(itemstack, entity.world, pos)) {
                    List<ItemStack> drops = target.onSheared(itemstack, entity.world, pos, EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.FORTUNE, itemstack) + itemstack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).getLevel(Constants.LUCK));

                    //Random Drop Color
                    Random random = new Random();
                    int color = random.nextInt(16);
                    ItemStack oldDrops = drops.get(0);
                    int numItems = oldDrops.getCount();
                    ItemStack newDrops = new ItemStack(Item.getItemFromBlock(Blocks.WOOL), numItems, color);
                    drops.remove(0);
                    drops.add(newDrops);

                    Random rand = new Random();
                    dropItems(entity, drops, rand);
                    damage(itemstack, player);
                }
                return true;
            }
            return false;
        }
        if (entity.world.isRemote) {
            return false;
        }
        if (entity instanceof net.minecraftforge.common.IShearable) {
            net.minecraftforge.common.IShearable target = (net.minecraftforge.common.IShearable)entity;
            BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);
            if (target.isShearable(itemstack, entity.world, pos)) {
                java.util.List<ItemStack> drops = target.onSheared(itemstack, entity.world, pos,
                        net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.FORTUNE, itemstack));

                java.util.Random rand = new java.util.Random();
                for(ItemStack stack : drops) {
                    net.minecraft.entity.item.EntityItem ent = entity.entityDropItem(stack, 1.0F);
                    ent.motionY += rand.nextFloat() * 0.05F;
                    ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                }
                damage(itemstack, player);
            }
            return true;
        }
        return false;    }

    private void dropItems(EntityLivingBase entity, List<ItemStack> drops, Random rand) {
        for (ItemStack stack : drops) {
            EntityItem ent = entity.entityDropItem(stack, 1.0F);
            ent.motionY += rand.nextFloat() * 0.05F;
            ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
            ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null))
            return new ModifiableProvider(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        else
            return null;
    }


    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, Constants.FORCE_SHEARS);
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> lores, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, lores, flagIn);
        stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).addText(lores);
    }

    @Override
    public long getTool() {
        return Constants.SHEARS;
    }
}
