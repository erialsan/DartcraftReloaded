package dartcraftReloaded.container;

import dartcraftReloaded.tileEntity.TileEntityInfuser;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class InfuserTank extends FluidTank {

    TileEntityInfuser tile;

    public InfuserTank(int capacity, TileEntityInfuser tile) {
        super(capacity);
        this.tile = tile;
        setCanDrain(false);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        int i = super.fill(resource, doFill);
        tile.sync();
        return i;
    }
}
