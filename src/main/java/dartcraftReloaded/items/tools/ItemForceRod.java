package dartcraftReloaded.items.tools;

import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.Modifiable.IModifiableTool;
import dartcraftReloaded.capablilities.Modifiable.ModifiableProvider;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.handlers.InfusedActionHandler;
import dartcraftReloaded.items.ItemBase;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.util.DartUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

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


    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null))
            return new ModifiableProvider();
        else
            return null;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return InfusedActionHandler.rodItemRightClick(worldIn, playerIn, handIn, this);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return InfusedActionHandler.rodItemUse(player, worldIn, pos, hand, facing);
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