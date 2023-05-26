package dartcraftReloaded.items.Tools;

import dartcraftReloaded.handlers.DCRSoundHandler;
import dartcraftReloaded.items.ItemBase;
import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.DartcraftReloaded;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
        this.setMaxDamage(2);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Block b = worldIn.getBlockState(pos).getBlock();
        if (b.equals(Blocks.OBSIDIAN) || b.equals(Blocks.SAPLING)) {
            if (worldIn.isRemote) {
                worldIn.playSound(player, player.getPosition(), DCRSoundHandler.SPARKLE, SoundCategory.PLAYERS, 1.0f, 1.0f);
                return EnumActionResult.SUCCESS;
            }
        }
        ItemStack held = player.getHeldItem(hand);
        if (!worldIn.isRemote) {
            if (b.equals(Blocks.SAPLING)) {
                IBlockState i = worldIn.getBlockState(pos);
                worldIn.setBlockState(pos, ModBlocks.forceSapling.getDefaultState(), 3);
                worldIn.notifyBlockUpdate(pos, i, ModBlocks.forceSapling.getDefaultState(), 3);
                held.damageItem(1, player);
                return EnumActionResult.SUCCESS;
            }
        }

        return EnumActionResult.PASS;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Can only be used to make Force Saplings");
    }
}