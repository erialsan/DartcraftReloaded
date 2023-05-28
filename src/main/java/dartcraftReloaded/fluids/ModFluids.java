package dartcraftReloaded.fluids;

import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {

    public static FluidForce fluidForce = new FluidForce();

    public static void registerFluids(){
        FluidRegistry.registerFluid(fluidForce);
    }
}
