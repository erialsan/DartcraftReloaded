package dartcraftReloaded.items.tools;

import dartcraftReloaded.Constants;
import dartcraftReloaded.DartcraftReloaded;
import com.google.common.collect.Multimap;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.capablilities.Modifiable.IModifiableTool;
import dartcraftReloaded.capablilities.Modifiable.ModifiableProvider;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.handlers.InfusedActionHandler;
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
        if (InfusedActionHandler.enderTeleport(worldIn, playerIn, handIn)) return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, Constants.FORCE_SWORD);
    }
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        InfusedActionHandler.damage(stack, attacker);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D) {
            InfusedActionHandler.damage(stack, entityLiving);
        }

        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return blockIn.getBlock() == Blocks.WEB;
    }

    @Override
    public float getAttackDamage() {
        return DartcraftReloaded.forceToolMaterial.getAttackDamage();
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null))
            return new ModifiableProvider();
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
