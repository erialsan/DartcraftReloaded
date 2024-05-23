package dartcraftReloaded.items.tools;

import dartcraftReloaded.Constants;
import dartcraftReloaded.DartcraftReloaded;
import com.google.common.collect.Multimap;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.capablilities.Modifiable.IModifiableTool;
import dartcraftReloaded.capablilities.Modifiable.ModifiableProvider;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.util.DartUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemForceSword extends ItemSword implements IModifiableTool {


    public ItemForceSword() {
        super(DartcraftReloaded.forceToolMaterial);
        this.setRegistryName(Constants.FORCE_SWORD);
        this.setTranslationKey(Constants.FORCE_SWORD);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        IModifiable cap = playerIn.getHeldItem(handIn).getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        if (playerIn.isSneaking() && cap.hasModifier(Constants.ENDER)) {
            RayTraceResult hit = DartUtils.enderTrace(worldIn, playerIn, 8 * cap.getLevel(Constants.ENDER));
            if (hit != null && hit.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos tppos = hit.getBlockPos();
                if (tppos != null && worldIn.getBlockState(tppos.up()).getBlock() == Blocks.AIR && worldIn.getBlockState(tppos.up(2)).getBlock() == Blocks.AIR) {
                    worldIn.playSound(null, playerIn.getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 0.6F, 1.0F);
                    playerIn.getCooldownTracker().setCooldown(this, 80);
                    DartUtils.teleport(playerIn, tppos);
                    damage(playerIn.getHeldItem(handIn), playerIn);
                    return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
                }
            }

        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, Constants.FORCE_SWORD);
    }
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        damage(stack, attacker);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D) {
            damage(stack, entityLiving);
        }

        return true;
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
            }
        }
        if (cap.hasModifier(Constants.STURDY)) {
            if (Math.random() > 1.0 / (1.0 + (double) cap.getLevel(Constants.STURDY))) {
                return;
            }
        }
        stack.damageItem(1, entity);
    }


    public boolean canHarvestBlock(IBlockState blockIn)
    {
        return blockIn.getBlock() == Blocks.WEB;
    }

    public float getAttackDamage() {
        return DartcraftReloaded.forceToolMaterial.getAttackDamage();
    }

    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.getAttackDamage(), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }


    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null))
            return new ModifiableProvider(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        else
            return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> lores, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, lores, flagIn);
        stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).addText(lores);
    }


    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        Block block = state.getBlock();

        if (block == Blocks.WEB)
        {
            return 15.0F;
        }
        else
        {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }


    @Override
    public long getTool() {
        return Constants.SWORD;
    }
}
