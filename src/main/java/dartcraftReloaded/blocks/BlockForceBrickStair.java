package dartcraftReloaded.blocks;

import dartcraftReloaded.DartcraftReloaded;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockForceBrickStair extends BlockStairs {
    protected BlockForceBrickStair(IBlockState modelState) {
        super(modelState);
        setCreativeTab(DartcraftReloaded.creativeTab);
    }

    public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }

    public void registerItemModel(Item itemBlock, String name) {
        DartcraftReloaded.proxy.registerItemRenderer(itemBlock, 0, name);
    }
}
