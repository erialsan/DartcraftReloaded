package dartcraftReloaded.blocks;

import dartcraftReloaded.DartcraftReloaded;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import static dartcraftReloaded.Constants.FORCE_BRICK;
public class BlockForceBrick extends BlockBase {

    public static final PropertyEnum<EnumDyeColor> VARIANT = PropertyEnum.create("variant", EnumDyeColor.class);


    public BlockForceBrick(){
        super(Material.ROCK, FORCE_BRICK);
        this.setHardness(2);
        this.setResistance(50);
        this.setHarvestLevel("pickaxe", 3);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumDyeColor.YELLOW));
    }

    public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }

    public void registerItemModel(Item itemBlock) {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            DartcraftReloaded.proxy.registerItemRenderer(itemBlock, color.getMetadata(), FORCE_BRICK+color.getName());
        }
    }


    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).getMetadata();
    }

    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            items.add(new ItemStack(this, 1, color.getMetadata()));
        }
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, EnumDyeColor.byMetadata(meta));
    }


    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).getMetadata();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

}
