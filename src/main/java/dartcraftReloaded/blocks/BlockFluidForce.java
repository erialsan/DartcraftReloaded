package dartcraftReloaded.blocks;

import dartcraftReloaded.fluids.ModFluids;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidForce extends BlockFluidClassic {

    public BlockFluidForce(){
        super(ModFluids.fluidForce, ModFluids.fluidForce.getMaterial());
        this.setRegistryName("fluidForce");
        this.setTranslationKey("fluid_force");
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (entityIn instanceof EntityLivingBase) {
            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 10, 10));
        }
    }
}
