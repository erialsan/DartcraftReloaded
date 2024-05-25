package dartcraftReloaded.handlers;

import dartcraftReloaded.Constants;
import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.capablilities.ItemTE.IItemTE;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.entity.EntityColdChicken;
import dartcraftReloaded.entity.EntityColdCow;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.items.nonburnable.EntityNonBurnableItem;
import dartcraftReloaded.items.nonburnable.ItemInertCore;
import dartcraftReloaded.items.tools.ItemForceRod;
import dartcraftReloaded.networking.SoundMessage;
import dartcraftReloaded.recipe.RecipeRod;
import dartcraftReloaded.util.DartUtils;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.List;
import java.util.Random;

public class InfusedActionHandler {

    protected static Random itemRand = new Random();

    public static boolean enderTeleport(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) return true;
        IModifiable cap = playerIn.getHeldItem(handIn).getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        if (playerIn.isSneaking() && cap.hasModifier(Constants.ENDER)) {
            RayTraceResult hit = DartUtils.enderTrace(worldIn, playerIn, 8 * cap.getLevel(Constants.ENDER));
            if (hit != null && hit.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos tppos = hit.getBlockPos();
                if (tppos != null && !worldIn.getBlockState(tppos.up()).causesSuffocation() && !worldIn.getBlockState(tppos.up(2)).causesSuffocation()) {
                    worldIn.playSound(null, playerIn.getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 0.6F, 1.0F);
                    playerIn.getCooldownTracker().setCooldown(playerIn.getHeldItem(handIn).getItem(), 80);
                    DartUtils.teleport(playerIn, tppos);
                    damage(playerIn.getHeldItem(handIn), playerIn);
                    return true;
                }
            }
        }
        return false;
    }

    public static ActionResult<ItemStack> rodItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn, ItemForceRod rod) {
        if (worldIn.isRemote) return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        IModifiable cap = playerIn.getHeldItem(handIn).getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        boolean flag = false;
        if (playerIn.isSneaking() && cap.hasModifier(Constants.ENDER)) {
            RayTraceResult hit = DartUtils.enderTrace(worldIn, playerIn, 8*cap.getLevel(Constants.ENDER));
            if (hit != null && hit.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos tppos = hit.getBlockPos();
                if (tppos != null && !worldIn.getBlockState(tppos.up()).causesSuffocation() && !worldIn.getBlockState(tppos.up(2)).causesSuffocation()) {
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
            playerIn.getCooldownTracker().setCooldown(rod, 80);
            rodDoAction(playerIn.getHeldItemMainhand(), playerIn, worldIn);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    public static EnumActionResult rodItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing) {
        ItemStack held = player.getHeldItem(hand);
        if (worldIn.getTileEntity(pos) != null && held.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).hasModifier(Constants.HOLDING)) {
            if (worldIn.isRemote) {
                worldIn.playSound(player, pos, SoundHandler.SPARKLE, SoundCategory.PLAYERS, 1.0f, 1.0f);
                return EnumActionResult.SUCCESS;
            } else {
                ItemStack te = new ItemStack(ModItems.itemTE);
                IItemTE cap = te.getCapability(CapabilityHandler.CAPABILITY_TE, null);
                te.setStackDisplayName(TextFormatting.AQUA+"Packaged " + cap.getBlockName());
                if (writeNBT(worldIn, pos, cap)) {
                    EntityItem droppedItem = new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), te);
                    worldIn.spawnEntity(droppedItem);
                    return rodDoAction(held, player, worldIn);
                }
            }
        }
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
                return rodDoAction(held, player, worldIn);
            }
        }
        if (held.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).hasModifier(Constants.HEAT)) {
            if (!worldIn.isRemote) {
                worldIn.setBlockState(pos.offset(facing), Blocks.FIRE.getDefaultState(), 3);
                return rodDoAction(held, player, worldIn);
            }
        }
        if (!worldIn.isRemote) {
            BlockPos hitPos = pos.offset(facing);
            List<Entity> list = worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(hitPos.getX() - .5, hitPos.getY() - .5, hitPos.getZ() - .5, hitPos.getX() + 1.5, hitPos.getY() + 1.5, hitPos.getZ() + 1.5));
            if (worldIn.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN)) {
                worldIn.setBlockState(pos, ModBlocks.infuser.getDefaultState(), 3);
                worldIn.notifyBlockUpdate(pos, Blocks.OBSIDIAN.getDefaultState(), ModBlocks.infuser.getDefaultState(), 3);
                return rodDoAction(held, player, worldIn);
            } else if (worldIn.getBlockState(pos).getBlock().equals(Blocks.SAPLING)) {
                IBlockState i = worldIn.getBlockState(pos);
                worldIn.setBlockState(pos, ModBlocks.forceSapling.getDefaultState(), 3);
                worldIn.notifyBlockUpdate(pos, i, ModBlocks.forceSapling.getDefaultState(), 3);
                return rodDoAction(held, player, worldIn);
            } else {
                boolean flag = false;
                for(Entity i: list) {
                    if (i instanceof EntityItem) {
                        ItemStack item = ((EntityItem) i).getItem();
                        for (RecipeRod recipe : RecipeHandler.rodRecipes) {
                            if (item.getItem() == recipe.input.getItem() && item.getItemDamage() == recipe.input.getItemDamage()) {
                                if (recipe.needsFire) {
                                    if (worldIn.getBlockState(pos.offset(facing)).getBlock().equals(Blocks.FIRE)) {
                                        flag = true;
                                        ItemStack newStack = recipe.output;
                                        newStack.setCount(newStack.getCount() * item.getCount());
                                        worldIn.removeEntity(i);
                                        worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), newStack));
                                    }
                                } else {
                                    ItemStack newStack = recipe.output;
                                    newStack.setCount(newStack.getCount() * item.getCount());
                                    worldIn.removeEntity(i);
                                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), newStack));
                                }
                            }
                        }
                    }
                }
                if (flag) {
                    worldIn.setBlockToAir(pos.offset(facing));
                }
            }
        }

        return EnumActionResult.PASS;
    }

    public static EnumActionResult rodDoAction(ItemStack i, EntityPlayer p, World w) {
        damage(i, p);
        PacketHandler.sendToClient(new SoundMessage(0), (EntityPlayerMP) p);
        return EnumActionResult.SUCCESS;
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

    public static float getDestroySpeed(float superSpeed, ItemStack stack) {
        IModifiable cap = stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        if (cap.hasModifier(Constants.SPEED)) {
            superSpeed *= cap.getLevel(Constants.SPEED);
        }
        return superSpeed;
    }

    public static float pickDestroySpeed(ItemStack stack, IBlockState state, float superSpeed, float effeciency) {

        Material material = state.getMaterial();
        float speed = material != Material.IRON && material != Material.ANVIL && material != Material.ROCK ? superSpeed : effeciency;

        IModifiable cap = stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        if (cap.hasModifier(Constants.SPEED)) {
            speed *= cap.getLevel(Constants.SPEED);
        }
        return speed;
    }

    public static void bowPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft, ItemBow bow)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            IModifiable cap = stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
            ItemStack itemstack = bow.findAmmo(entityplayer);
            boolean flag = entityplayer.capabilities.isCreativeMode || cap.hasModifier(Constants.QUIVER);
            int i = bow.getMaxItemUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag)
            {
                if (itemstack.isEmpty())
                {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = ItemBow.getArrowVelocity(i);
                if (cap.hasModifier(Constants.SPEED)) {
                    f *= cap.getLevel(Constants.SPEED);
                }

                if ((double)f >= 0.1D)
                {
                    boolean flag1 = entityplayer.capabilities.isCreativeMode || cap.hasModifier(Constants.QUIVER) || (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));

                    if (!worldIn.isRemote)
                    {
                        ItemArrow itemarrow = (ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.ARROW);
                        EntityArrow entityarrow = itemarrow.createArrow(worldIn, itemstack, entityplayer);
                        entityarrow = bow.customizeArrow(entityarrow);
                        entityarrow.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);

                        if (f >= 1.0F)
                        {
                            entityarrow.setIsCritical(true);
                        }

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack) + 2*cap.getLevel(Constants.DAMAGE);

                        if (j > 0)
                        {
                            entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack) + cap.getLevel(Constants.FORCE);
                        if (k > 0)
                        {
                            entityarrow.setKnockbackStrength(k);
                        }

                        int fireTime = EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0 ? 100 : 0;
                        if (fireTime > 0) {
                            entityarrow.setFire(fireTime);
                        }

                        damage(stack, entityplayer);

                        if (flag1 || entityplayer.capabilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW))
                        {
                            entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
                        }

                        worldIn.spawnEntity(entityarrow);
                    }

                    worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    if (!flag1 && !entityplayer.capabilities.isCreativeMode)
                    {
                        itemstack.shrink(1);

                        if (itemstack.isEmpty())
                        {
                            entityplayer.inventory.deleteStack(itemstack);
                        }
                    }

                    entityplayer.addStat(StatList.getObjectUseStats(bow));
                }
            }
        }
    }

    public static boolean shearInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {


        if (entity instanceof EntityCow && !(entity instanceof EntityColdCow) && !player.world.isRemote) {
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
            player.world.spawnEntity(new EntityItem(player.world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.LEATHER, (int) (2 + Math.random() * 2 * itemstack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).getLevel(Constants.LUCK)))));
            damage(itemstack, player);

        } else if (entity instanceof EntityChicken && !(entity instanceof EntityColdChicken) && !player.world.isRemote) {
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
            player.world.spawnEntity(new EntityItem(player.world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.LEATHER, (int) (2 + Math.random() * 2 * itemstack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).getLevel(Constants.LUCK)))));
            damage(itemstack, player);

        } else if (itemstack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).hasModifier(Constants.RAINBOW)) {
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
                    for (ItemStack stack : drops) {
                        EntityItem ent = entity.entityDropItem(stack, 1.0F);
                        ent.motionY += rand.nextFloat() * 0.05F;
                        ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                        ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    }
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
            net.minecraftforge.common.IShearable target = (net.minecraftforge.common.IShearable) entity;
            BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);
            if (target.isShearable(itemstack, entity.world, pos)) {
                java.util.List<ItemStack> drops = target.onSheared(itemstack, entity.world, pos,
                        net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.FORTUNE, itemstack));

                java.util.Random rand = new java.util.Random();
                for (ItemStack stack : drops) {
                    net.minecraft.entity.item.EntityItem ent = entity.entityDropItem(stack, 1.0F);
                    ent.motionY += rand.nextFloat() * 0.05F;
                    ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                }
                damage(itemstack, player);
            }
            return true;
        }
        return false;
    }

    private static boolean writeNBT(World world, BlockPos pos, IItemTE cap){
        IBlockState state = world.getBlockState(pos);
        String blockName = state.getBlock().getLocalizedName();
        TileEntity tileEntity = world.getTileEntity(pos);
        NBTTagCompound nbt = new NBTTagCompound();

        if(tileEntity != null){
            tileEntity.writeToNBT(nbt);
            world.removeTileEntity(pos);
            world.setBlockToAir(pos);
            world.markBlockRangeForRenderUpdate(pos, pos);
            cap.setNBT(nbt);
            cap.setBlockName(blockName);
            cap.setBlockState(state);
            world.notifyBlockUpdate(pos, state, Blocks.AIR.getDefaultState(), 3);
            return true;
        }
        return false;
    }

}
