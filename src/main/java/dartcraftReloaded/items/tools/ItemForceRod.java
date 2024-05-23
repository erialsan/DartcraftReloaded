package dartcraftReloaded.items.tools;

import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.ExperienceTome.IExperienceTome;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.capablilities.Modifiable.IModifiableTool;
import dartcraftReloaded.capablilities.Modifiable.ModifiableProvider;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.handlers.PacketHandler;
import dartcraftReloaded.items.ItemBase;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.items.nonburnable.EntityNonBurnableItem;
import dartcraftReloaded.items.nonburnable.ItemInertCore;
import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.networking.SoundMessage;
import dartcraftReloaded.util.DartUtils;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.network.NetHandlerPlayServer;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class ItemForceRod extends ItemBase implements IModifiableTool {



    public ItemForceRod(){
        super(Constants.FORCE_ROD);
        setHasSubtypes(true);
        setTranslationKey(Constants.FORCE_ROD);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        this.setMaxDamage(50);
        this.setMaxStackSize(1);
    }

    private EnumActionResult doAction(ItemStack i, EntityPlayer p, World w) {
        damage(i, p);
        PacketHandler.sendToClient(new SoundMessage(0), (EntityPlayerMP) p);
        return EnumActionResult.SUCCESS;
    }
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null))
            return new ModifiableProvider(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        else
            return null;
    }



    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        IModifiable cap = playerIn.getHeldItem(handIn).getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        boolean flag = false;
        if (playerIn.isSneaking() && cap.hasModifier(Constants.ENDER)) {
            RayTraceResult hit = DartUtils.enderTrace(worldIn, playerIn, 8*cap.getLevel(Constants.ENDER));
            if (hit != null && hit.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos tppos = hit.getBlockPos();
                if (tppos != null && worldIn.getBlockState(tppos.up()).getBlock() == Blocks.AIR && worldIn.getBlockState(tppos.up(2)).getBlock() == Blocks.AIR) {
                    worldIn.playSound(null, playerIn.getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 0.6F, 1.0F);
                    flag = true;
                    DartUtils.teleport(playerIn, tppos);
                }
            }

        }
        if (cap.hasModifier(Constants.LUCK)) {
            playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(26), cap.getLevel(Constants.LUCK) * 30 * 20));
            flag = true;
        }
        if (cap.hasModifier(Constants.SPEED)) {
            playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(1), cap.getLevel(Constants.SPEED) * 30 * 20, cap.getLevel(Constants.SPEED) * 2));
            if (cap.getLevel(Constants.SPEED) / 2 > 0) {
                playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(3), cap.getLevel(Constants.SPEED) * 30 * 20, cap.getLevel(Constants.SPEED) / 2));
            }
            flag = true;
        }
        if (cap.hasModifier(Constants.SIGHT)) {
            playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(16), 30*20));
            flag = true;
        }
        if (cap.hasModifier(Constants.CAMO)) {
            playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(14), 30*20));
            flag = true;
        }
        if (cap.hasModifier(Constants.HEALING)) {
            if (cap.getLevel(Constants.HEALING) == 1) {
                playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(10), 5*20, 1));
                playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(22), 2*60*20));
            } else {
                playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(22), 2*60*20, 3));
                playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(10), 30*20, 1));
                playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(12), 5*60*20));
                playerIn.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 5*60*20));
            }
            flag = true;
        }
        if (flag) {
            playerIn.getCooldownTracker().setCooldown(this, 80);
            doAction(playerIn.getHeldItemMainhand(), playerIn, worldIn);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        if (!worldIn.isRemote && held.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).hasModifier(Constants.LIGHT)) {
            BlockPos blockClicked = pos;
            EnumFacing sideClicked = facing;
            if (facing == EnumFacing.DOWN) {
                sideClicked = EnumFacing.UP;
                blockClicked.add(0, -2, 0);
            }
            if (worldIn.getBlockState(blockClicked).isTopSolid() && worldIn.mayPlace(Blocks.TORCH, blockClicked.offset(sideClicked), false, sideClicked, player)) {
                IBlockState state = worldIn.getBlockState(blockClicked.offset(sideClicked));
                worldIn.setBlockState(blockClicked.offset(sideClicked), Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, sideClicked));
                worldIn.notifyBlockUpdate(blockClicked.offset(sideClicked), state, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, sideClicked), 3);
                worldIn.notifyLightSet(blockClicked.offset(sideClicked));
                return doAction(held, player, worldIn);
            }
        }
        if (held.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).hasModifier(Constants.HEAT)) {
            if (!worldIn.isRemote) {
                worldIn.setBlockState(pos.offset(facing), Blocks.FIRE.getDefaultState(), 3);
                return doAction(held, player, worldIn);
            }
        }
        if (!worldIn.isRemote) {
            BlockPos hitPos = pos.offset(facing);
            List<Entity> list = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(hitPos.getX() - .5, hitPos.getY() - .5, hitPos.getZ() - .5, hitPos.getX() + 1.5, hitPos.getY() + 1.5, hitPos.getZ() + 1.5));

            if (worldIn.getBlockState(pos.offset(facing)).getBlock().equals(Blocks.FIRE)) {
                worldIn.setBlockToAir(pos.offset(facing));
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
                for(Entity i: list) {
                    if (i instanceof EntityItem) {
                        ItemStack item = ((EntityItem) i).getItem();
                        if (item.getItem() == Items.ENCHANTED_BOOK) {
                            NBTTagCompound nbt = item.getTagCompound();
                            if (nbt != null) {
                                NBTTagList enchantments = ItemEnchantedBook.getEnchantments(item);
                                if (enchantments.toString().contains("id:70s")) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.repairToken)));
                                    return doAction(held, player, worldIn);
                                }

                            }
                        } else if (item.getItem() == Items.POTIONITEM) {
                            NBTTagCompound nbt = item.getTagCompound();
                            if (nbt != null) {
                                String potion = nbt.getString("Potion");
                                if (potion.equals("minecraft:long_night_vision")) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.sightToken)));
                                    return doAction(held, player, worldIn);
                                } else if (potion.equals("minecraft:long_invisibility")) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.camoToken)));
                                    return doAction(held, player, worldIn);
                                }
                            }
                        } else if (item.getItem() == Items.BOOK) {
                            worldIn.removeEntity(i);
                            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.upgradeTome)));
                            return doAction(held, player, worldIn);
                        //} else if (item.getItem() == ModItems.experienceTome) {
                        //    IExperienceTome cap = item.getCapability(CapabilityHandler.CAPABILITY_EXPTOME, null);
                        //    int num = cap.getExperienceValue() / 100;
                        //    worldIn.removeEntity(i);
                        //    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.upgradeCore, num)));
                        //    return doAction(held, player, worldIn);
                        } else if(item.getItem() instanceof ItemArmor) {
                            if (((ItemArmor) item.getItem()).armorType == EntityEquipmentSlot.CHEST) {
                                if (((ItemArmor) item.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 6)));
                                    return doAction(held, player, worldIn);

                                }
                                else if (((ItemArmor) item.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 6)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) item.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.forceChest, 1)));
                                    return doAction(held, player, worldIn);
                                }
                            }
                            if (((ItemArmor) item.getItem()).armorType == EntityEquipmentSlot.LEGS) {
                                if (((ItemArmor) item.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 5)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) item.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 5)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) item.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.forceLegs, 1)));
                                    return doAction(held, player, worldIn);
                                }
                            }
                            if (((ItemArmor) item.getItem()).armorType == EntityEquipmentSlot.FEET) {
                                if (((ItemArmor) item.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 3)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) item.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 3)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) item.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(ModItems.forceBoots, 1)));
                                    return doAction(held, player, worldIn);
                                }
                            }
                            if (((ItemArmor) item.getItem()).armorType == EntityEquipmentSlot.HEAD) {
                                if (((ItemArmor) item.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.IRON) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.IRON_INGOT, 4)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) item.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD) {
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Items.GOLD_INGOT, 4)));
                                    return doAction(held, player, worldIn);
                                }
                                else if (((ItemArmor) item.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
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


    public static void damage(ItemStack stack, EntityLivingBase entity) {
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

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> lores, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, lores, flagIn);
        stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).addText(lores);
    }

    @Override
    public long getTool() {
        return Constants.ROD;
    }
}