package dartcraftReloaded.blocks.torch;

import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.tileEntity.TileEntityTimeTorch;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static dartcraftReloaded.Constants.TIME_TORCH;

public class BlockTimetorch extends BlockTorch implements ITileEntityProvider {

    public BlockTimetorch() {
        this.setSoundType(SoundType.WOOD);
        this.setLightLevel(0.9375F);
        this.setTranslationKey(TIME_TORCH);
        this.setRegistryName(TIME_TORCH);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
    }

    public void registerItemModel(Item itemBlock) {
        DartcraftReloaded.proxy.registerItemRenderer(itemBlock, 0, TIME_TORCH);
    }

    public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityTimeTorch();
    }
}