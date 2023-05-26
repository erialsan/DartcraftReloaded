package dartcraftReloaded.items.Tools;

import dartcraftReloaded.capablilities.ExperienceTome.IExperienceTome;
import dartcraftReloaded.handlers.DCRCapabilityHandler;
import dartcraftReloaded.handlers.DCRPacketHandler;
import dartcraftReloaded.handlers.DCRSoundHandler;
import dartcraftReloaded.items.ItemBase;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.items.NonBurnable.EntityNonBurnableItem;
import dartcraftReloaded.items.NonBurnable.ItemInertCore;
import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.networking.SoundMessage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;


/**
 * Created by BURN447 on 2/23/2018.
 */
public class ItemForceRod extends ItemBase {
    public ItemForceRod(String name){
        super(name);
        setHasSubtypes(true);
        setTranslationKey(name);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        this.setMaxDamage(50);
        this.setMaxStackSize(1);
    }

    private EnumActionResult doAction(ItemStack i, EntityPlayer p, World w) {
        i.damageItem(1, p);
        DCRPacketHandler.sendToClient(new SoundMessage(0), (EntityPlayerMP) p);
        return EnumActionResult.SUCCESS;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //worldIn.playSound(player, player.getPosition(), DCRSoundHandler.SPARKLE, SoundCategory.PLAYERS, 1.0f, 1.0f);
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
                                return EnumActionResult.SUCCESS;
                            }
                        }
                    }
                }
            } else if (worldIn.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN)) {
                worldIn.setBlockState(pos, ModBlocks.infuser.getDefaultState(), 3);
                worldIn.notifyBlockUpdate(pos, Blocks.OBSIDIAN.getDefaultState(), ModBlocks.infuser.getDefaultState(), 3);
                return doAction(held, player, worldIn);
            } else if (worldIn.getBlockState(pos).getBlock().equals(Blocks.SAPLING)) {
                IBlockState i = worldIn.getBlockState(pos);
                worldIn.setBlockState(pos, ModBlocks.forceSapling.getDefaultState(), 3);
                worldIn.notifyBlockUpdate(pos, i, ModBlocks.forceSapling.getDefaultState(), 3);
                return doAction(held, player, worldIn);
            }
            else {
                List<Entity> list = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())));
                //If it is a subset of items, it will drop swap an item
                for(Entity i: list) {
                    if (i instanceof EntityItem) {
                        //Armor
                        if (((EntityItem) i).getItem().getItem() == Items.BOOK) {
                            worldIn.removeEntity(i);
                            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.upgradeTome)));
                            return doAction(held, player, worldIn);
                        } else if (((EntityItem) i).getItem().getItem() == ModItems.experienceTome) {
                            IExperienceTome cap = ((EntityItem) i).getItem().getCapability(DCRCapabilityHandler.CAPABILITY_EXPTOME, null);
                            int num = (int) cap.getExperienceValue() / 100;
                            worldIn.removeEntity(i);
                            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.upgradeCore, num)));
                            return doAction(held, player, worldIn);
                        } else if(((EntityItem) i).getItem().getItem() instanceof ItemArmor) {
                            if (((ItemArmor) ((EntityItem) i).getItem().getItem()).armorType == EntityEquipmentSlot.CHEST) {
                                if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 6)));
                                    return doAction(held, player, worldIn);

                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 6)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.forceChest, 1)));
                                    return doAction(held, player, worldIn);
                                }
                            }
                            if (((ItemArmor) ((EntityItem) i).getItem().getItem()).armorType == EntityEquipmentSlot.LEGS) {
                                if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 5)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 5)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.forceLegs, 1)));
                                    return doAction(held, player, worldIn);
                                }
                            }
                            if (((ItemArmor) ((EntityItem) i).getItem().getItem()).armorType == EntityEquipmentSlot.FEET) {
                                if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 3)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 3)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.forceBoots, 1)));
                                    return doAction(held, player, worldIn);
                                }
                            }
                            if (((ItemArmor) ((EntityItem) i).getItem().getItem()).armorType == EntityEquipmentSlot.HEAD) {
                                if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 4)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 4)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) ((EntityItem) i).getItem().getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.forceHelmet, 1)));
                                    return doAction(held, player, worldIn);
                                }
                            }
                        }
                    }
                }
            }
        }

        return EnumActionResult.PASS;
    }

}