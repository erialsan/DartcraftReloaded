package dartcraftReloaded.items.tools;

import dartcraftReloaded.Constants;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.capablilities.Modifiable.IModifiableTool;
import dartcraftReloaded.capablilities.Modifiable.ModifiableProvider;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.handlers.InfusedActionHandler;
import dartcraftReloaded.util.DartUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemForceAxe extends ItemAxe implements IModifiableTool {

    public ItemForceAxe() {
        super(DartcraftReloaded.forceToolMaterial, 8.0F, 8.0F);
        this.setRegistryName(Constants.FORCE_AXE);
        this.setTranslationKey(Constants.FORCE_AXE);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        this.attackSpeed = -2.0F;
        this.attackDamage = 8.0F;
    }


    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, Constants.FORCE_AXE);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        if (stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).hasModifier(Constants.LUMBERJACK)) {
            if (DartUtils.isTree(player.getEntityWorld(), pos)) {
                DartUtils.fellTree(stack, pos, player);
                return true;
            }
        }
        return false;
    }


    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null))
            return new ModifiableProvider();
        else
            return null;
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
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return InfusedActionHandler.getDestroySpeed(super.getDestroySpeed(stack, state), stack);
    }

    @Override
    public long getTool() {
        return Constants.AXE;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> lores, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, lores, flagIn);
        stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).addText(lores);
    }

}
