package dartcraftReloaded.blocks;

import dartcraftReloaded.DartcraftReloaded;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import static dartcraftReloaded.Constants.FORCE_TORCH;

public class BlockForceTorch extends BlockTorch {

    public BlockForceTorch() {
        this.setRegistryName(FORCE_TORCH);
        this.setTranslationKey(FORCE_TORCH);
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        this.setLightLevel(0.9375F);
        this.setSoundType(SoundType.WOOD);
    }

    public void registerItemModel(Item itemBlock) {
        DartcraftReloaded.proxy.registerItemRenderer(itemBlock, 0, FORCE_TORCH);
    }

    public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }
}
