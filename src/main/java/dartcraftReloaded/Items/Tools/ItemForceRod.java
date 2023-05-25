package dartcraftReloaded.Items.Tools;

import dartcraftReloaded.Handlers.DCRSoundHandler;
import dartcraftReloaded.Items.ItemBase;
import dartcraftReloaded.Items.ModItems;
import dartcraftReloaded.Items.NonBurnable.EntityNonBurnableItem;
import dartcraftReloaded.Items.NonBurnable.ItemInertCore;
import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.capablilities.ForceRod.ForceRodProvider;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.Constants;
import dartcraftReloaded.Handlers.DCRCapabilityHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static dartcraftReloaded.Constants.MODIFIERS.MOD_ENDER;
import static dartcraftReloaded.Constants.MODIFIERS.MOD_HEALING;

/**
 * Created by BURN447 on 2/23/2018.
 */
public class ItemForceRod extends ItemBase {

    public List<Constants.MODIFIERS> applicableModifers = new ArrayList<>();

    public ItemForceRod(String name){
        super(name);
        setHasSubtypes(true);
        setTranslationKey(name);
        setApplicableModifers();
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        this.setMaxDamage(50);
        this.setMaxStackSize(1);
    }

    private void doAction(ItemStack i, EntityPlayer p, World w) {
        i.damageItem(1, p);
        //DCRPacketHandler.packetHandler.sendToAll(new SoundMessage(p.getPosition(), 0));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        worldIn.playSound(player, player.getPosition(), DCRSoundHandler.SPARKLE, SoundCategory.PLAYERS, 1.0f, 1.0f);
        ItemStack held = player.getHeldItem(hand);
        if (!worldIn.isRemote) {
            if (worldIn.getBlockState(pos.offset(facing)).getBlock().equals(Blocks.FIRE)) {
                worldIn.setBlockToAir(pos.offset(facing));
                List<Entity> list = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())));
                boolean bw = false;
                for (Entity i: list) {
                    if(i instanceof EntityItem) {
                        if(((EntityItem) i).getItem().getItem() instanceof ItemInertCore) {
                            EntityItem bottledWither = new EntityNonBurnableItem(worldIn, pos.getX(), pos.getY()  + 1, pos.getZ(), new ItemStack(ModItems.bottledWither, ((EntityItem) i).getItem().getCount()));
                            worldIn.spawnEntity(bottledWither);
                            doAction(held, player, worldIn);
                            bw = true;
                        }
                    }
                }
                if(bw) {
                    for(Entity i: list) {
                        if(i instanceof EntityItem) {
                            if (((EntityItem) i).getItem().getItem() instanceof ItemInertCore) {
                                worldIn.removeEntity(i);
                            }
                        }
                    }
                }
            } else if (worldIn.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN)) {
                worldIn.setBlockState(pos, ModBlocks.infuser.getDefaultState(), 3);
                worldIn.notifyBlockUpdate(pos, Blocks.OBSIDIAN.getDefaultState(), ModBlocks.infuser.getDefaultState(), 3);
                doAction(held, player, worldIn);
            }
            else {
                List<Entity> list = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())));
                //If it is a subset of items, it will drop swap an item
                for(Entity i: list) {
                    if (i instanceof EntityItem) {
                        //Armor
                        if(((EntityItem) i).getItem().getItem() instanceof ItemArmor) {
                            if (((ItemArmor) ((EntityItem) i).getItem().getItem()).armorType == EntityEquipmentSlot.CHEST) {
                                if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 6)));
                                    doAction(held, player, worldIn);

                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 6)));
                                    doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.forceChest, 1)));
                                    doAction(held, player, worldIn);
                                }
                            }
                            if (((ItemArmor) ((EntityItem) i).getItem().getItem()).armorType == EntityEquipmentSlot.LEGS) {
                                if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 5)));
                                    doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 5)));
                                    doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.forceLegs, 1)));
                                    doAction(held, player, worldIn);
                                }
                            }
                            if (((ItemArmor) ((EntityItem) i).getItem().getItem()).armorType == EntityEquipmentSlot.FEET) {
                                if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 3)));
                                    doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 3)));
                                    doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.forceBoots, 1)));
                                    doAction(held, player, worldIn);
                                }
                            }
                            if (((ItemArmor) ((EntityItem) i).getItem().getItem()).armorType == EntityEquipmentSlot.HEAD) {
                                if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 4)));
                                    doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 4)));
                                    doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.forceHelmet, 1)));
                                    doAction(held, player, worldIn);
                                }
                            }
                        }
                    }
                }
            }
        }


        if(held.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).getHomeLocation() != null){
            held.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).teleportPlayerToLocation(player, held.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).getHomeLocation());
        }

        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        ItemStack stack = playerIn.getHeldItem(handIn);
        PotionEffect regenOne = new PotionEffect(MobEffects.REGENERATION, 100, 0);
        PotionEffect regenTwo = new PotionEffect(MobEffects.REGENERATION, 100, 1);
        PotionEffect regenThree = new PotionEffect(MobEffects.REGENERATION, 100, 2);

        PotionEffect invisibility = new PotionEffect(MobEffects.INVISIBILITY, 1000, 0);

        PotionEffect nightVision = new PotionEffect(MobEffects.NIGHT_VISION, 1000, 0);

        PotionEffect sight = new PotionEffect(MobEffects.GLOWING, 1000, 0);

        if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).isRodOfHealing(3)){
            playerIn.addPotionEffect(regenThree);
        }
        else if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).isRodOfHealing(2)){
            playerIn.addPotionEffect(regenTwo);
        }
        else if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).isRodOfHealing(1)){
            playerIn.addPotionEffect(regenOne);
        }

        if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).hasCamoModifier())
            playerIn.addPotionEffect(invisibility);

        if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).hasEnderModifier()){
            if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).getHomeLocation() == null){
                stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).setHomeLocation(playerIn.getPosition());
            }
            else{
                stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).teleportPlayerToLocation(playerIn, stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).getHomeLocation());
            }
        }

        if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).hasSightModifier()){
            playerIn.addPotionEffect(nightVision);
        }

        if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).hasLight()) {
            playerIn.addPotionEffect(sight);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null))
            return new ForceRodProvider(DCRCapabilityHandler.CAPABILITY_FORCEROD, null);
        else
            return null;
    }

    public void setApplicableModifers(){
        applicableModifers.add(MOD_HEALING);
        applicableModifers.add(MOD_ENDER);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).isRodOfHealing(3))
            tooltip.add("Healing III");
        else if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).isRodOfHealing(2))
            tooltip.add("Healing II");
        else if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).isRodOfHealing(1))
            tooltip.add("Healing I");

        if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).hasCamoModifier())
            tooltip.add("Camo");

        if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).hasEnderModifier())
            tooltip.add("Ender");

        if(stack.getCapability(DCRCapabilityHandler.CAPABILITY_FORCEROD, null).hasSightModifier())
            tooltip.add("Sight");
    }
}