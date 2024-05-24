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
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemForcePickaxe extends ItemPickaxe implements IModifiableTool {


    public ItemForcePickaxe() {
        super(DartcraftReloaded.forceToolMaterial);
        this.setRegistryName(Constants.FORCE_PICKAXE);
        this.setTranslationKey(Constants.FORCE_PICKAXE);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
    }


    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, Constants.FORCE_PICKAXE);
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
        return InfusedActionHandler.pickDestroySpeed(stack, state, super.getDestroySpeed(stack, state), this.efficiency);
    }

    @Override
    public long getTool() {
        return Constants.PICKAXE;
    }
}