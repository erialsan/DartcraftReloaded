package dartcraftReloaded.Items.Tools;

import dartcraftReloaded.Handlers.DCRSoundHandler;
import dartcraftReloaded.Items.ItemBase;
import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.DartcraftReloaded;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by BURN447 on 2/23/2018.
 */
public class ItemWoodenForceRod extends ItemBase {

    public ItemWoodenForceRod(String name){
        super(name);
        setHasSubtypes(true);
        setTranslationKey(name);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        this.setMaxDamage(3);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        worldIn.playSound(player, player.getPosition(), DCRSoundHandler.SPARKLE, SoundCategory.PLAYERS, 1.0f, 1.0f);
        ItemStack held = player.getHeldItem(hand);
        if (!worldIn.isRemote) {
            if (worldIn.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN)) {
                worldIn.setBlockState(pos, ModBlocks.infuser.getDefaultState(), 3);
                worldIn.notifyBlockUpdate(pos, Blocks.OBSIDIAN.getDefaultState(), ModBlocks.infuser.getDefaultState(), 3);
                held.damageItem(1, player);
            }
        }

        return EnumActionResult.SUCCESS;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Can only be used to make Infusers");
    }
}