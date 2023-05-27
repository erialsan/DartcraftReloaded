package dartcraftReloaded.fluids;

import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by BURN447 on 6/22/2018.
 */
public class ModFluids {

    public static FluidForce fluidForce = new FluidForce();

    public static void registerFluids(){
        FluidRegistry.registerFluid(fluidForce);
    }
}
