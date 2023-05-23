package dartcraftReloaded.blocks;

import dartcraftReloaded.DartcraftReloaded;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by BURN447 on 2/4/2018.
 */
public class BlockBase extends Block {

    protected String name;
    public BlockBase(Material material, String name, SoundType sound) {
        super(material);
        this.name = name;
        this.setSoundType(sound);
        this.setHardness(2.0F);
        init();
    }

    public BlockBase(Material material, String name) {
        super(material);
        this.name = name;
        this.setHardness(2.0F);
        init();
    }

    public void init() {
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(DartcraftReloaded.creativeTab);
    }

    public void registerItemModel(Item itemBlock) {
        DartcraftReloaded.proxy.registerItemRenderer(itemBlock, 0, name);
    }

    public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }

    @Override
    public BlockBase setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }

    @Override
    public BlockStateContainer getBlockState() {
        return super.getBlockState();
    }

}
