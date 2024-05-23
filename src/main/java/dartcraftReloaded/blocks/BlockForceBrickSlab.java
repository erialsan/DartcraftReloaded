package dartcraftReloaded.blocks;

import dartcraftReloaded.DartcraftReloaded;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static dartcraftReloaded.Constants.FORCE_BRICK_SLAB;

public class BlockForceBrickSlab extends BlockSlab {
    public static final PropertyEnum<EnumDyeColor> VARIANT = PropertyEnum.create("variant", EnumDyeColor.class);

    public BlockForceBrickSlab() {
        super(Material.ROCK);
        IBlockState iblockstate = this.blockState.getBaseState();
        iblockstate = iblockstate.withProperty(HALF, EnumBlockHalf.BOTTOM);
        this.setDefaultState(iblockstate.withProperty(VARIANT, EnumDyeColor.YELLOW));
        this.setHardness(2);
        this.setResistance(50);
        this.setHarvestLevel("pickaxe", 3);
        this.setSoundType(SoundType.STONE);
        setCreativeTab(DartcraftReloaded.creativeTab);
        setTranslationKey(FORCE_BRICK_SLAB);
        setRegistryName(FORCE_BRICK_SLAB);

    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(Blocks.WOODEN_SLAB);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(Blocks.WOODEN_SLAB, 1, state.getValue(VARIANT).getMetadata());
    }

    public String getTranslationKey(int meta) {
        return super.getTranslationKey() + "." + EnumDyeColor.byMetadata(meta).getTranslationKey();
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    public IProperty<?> getVariantProperty() {
        return VARIANT;
    }

    public Comparable<?> getTypeForItem(ItemStack stack) {
        return EnumDyeColor.byMetadata(stack.getMetadata() & 7);
    }

    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            items.add(new ItemStack(this, 1, color.getMetadata()));
        }

    }

    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, EnumDyeColor.byMetadata(meta & 7));
        iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);

        return iblockstate;
    }

    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i |= state.getValue(VARIANT).getMetadata();
        if (state.getValue(HALF) == EnumBlockHalf.TOP) {
            i |= 8;
        }

        return i;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF, VARIANT);
    }

    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).getMetadata();
    }

    public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }

    public void registerItemModel(Item itemBlock) {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            DartcraftReloaded.proxy.registerItemRenderer(itemBlock, color.getMetadata(), FORCE_BRICK_SLAB+color.getName());
        }
    }
}

