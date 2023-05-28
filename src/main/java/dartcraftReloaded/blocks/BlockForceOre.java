package dartcraftReloaded.blocks;

import dartcraftReloaded.items.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

import static dartcraftReloaded.Constants.ORE_POWER;

public class BlockForceOre extends BlockBase {

    public BlockForceOre() {
        super(Material.ROCK, ORE_POWER);
        setHardness(3f);
        setResistance(5f);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
        return ModItems.gemForceGem;
    }

    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return this.quantityDropped(random) + random.nextInt(fortune + 1);
    }

    public int quantityDropped(Random random)
    {
        return 2 + random.nextInt(2);
    }



}
