package dartcraftReloaded.tileEntity;

import dartcraftReloaded.energy.DCREnergyStorage;
import dartcraftReloaded.fluids.FluidForce;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.items.ModItems;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;


/**
 * Created by BURN447 on 3/30/2018.
 */
public class TileEntityInfuser extends TileEntity implements ITickable, ICapabilityProvider, ITileEntityProvider, IFluidHandler {


    public final ItemStackHandler handler;
    public FluidTank tank;

    private NonNullList<ItemStack> infuserContents = NonNullList.create();

    public boolean canWork = false;

    public int processTime = 0;
    public DCREnergyStorage energyStorage = new DCREnergyStorage(MAX_POWER, 1000);
    public int energy = energyStorage.getEnergyStored();
    public int maxProcessTime = 17;

    public static int MAX_POWER = 10000;
    public static int RF_PER_TICK = 20;

    public int fluidContained;


    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.6875D, 0.875D);

    public TileEntityInfuser() {
        this.handler = new ItemStackHandler(11) {
            @Override
            protected int getStackLimit(int slot, ItemStack stack) {
                return 1;
            }
        };

        tank = new FluidTank(10000);
    }




    public int getField(int id)
    {
        switch(id)
        {
            case 0:
                return this.processTime;
            case 1:
                return energyStorage.getEnergyStored();
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch(id)
        {
            case 0:
                this.processTime = value;
                break;
            case 1:
                this.energy = value;
        }
    }



    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        //Items
        this.processTime = nbt.getInteger("time");
        this.energy = nbt.getInteger("energy");
        handler.deserializeNBT(nbt.getCompoundTag("ItemStackHandler"));
        ItemStackHelper.loadAllItems(nbt, this.infuserContents);
        tank.readFromNBT(nbt);
        energyStorage.readFromNBT(nbt);
        super.markDirty();
        super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        //Items
        nbt.setInteger("time", processTime);
        nbt.setInteger("energy", energy);
        nbt.setTag("ItemStackHandler", handler.serializeNBT());
        ItemStackHelper.saveAllItems(nbt, this.infuserContents);
        tank.writeToNBT(nbt);
        energyStorage.writeToNBT(nbt);
        return super.writeToNBT(nbt);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AABB;
    }

    @Override
    public void update() {
        fluidContained = tank.getFluidAmount();
        if (world != null) {
            if (!world.isRemote) {
                processForceGems();
                if (canWork) {
                    if (processTime == maxProcessTime) {
                        this.markDirty();
                        //processTool();
                    }
                    if (energyStorage.getEnergyStored() >= RF_PER_TICK) {
                        processTime++;
                        energyStorage.extractEnergy(RF_PER_TICK, false);
                    }
                }
            }
        }
    }

    //Processes force Gems in the force infuser slot
    private void processForceGems() {
        if (handler.getStackInSlot(9).getItem() == ModItems.gemForceGem) {
            FluidStack force = new FluidStack(FluidRegistry.getFluid("force"), 1000);

            if (tank.getFluidAmount() < tank.getCapacity() - 100) {
                fill(force, true);
                if (handler.getStackInSlot(9).getCount() > 1) {
                    handler.getStackInSlot(9).setCount(handler.getStackInSlot(9).getCount() - 1);
                } else
                    handler.setStackInSlot(9, ItemStack.EMPTY);

                markDirty();
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
            }
        }
    }


    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) this.handler;
        if (capability == FLUID_HANDLER_CAPABILITY)
            return (T) this.tank;
        if (capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(energyStorage);

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == FLUID_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY)
            return true;
        return super.hasCapability(capability, facing);
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityInfuser();
    }


    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[0];
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        FluidStack resourceCopy = resource.copy();

        if (tank.getFluid() != null) {
            if (tank.getFluid().getFluid() instanceof FluidForce || tank.getFluidAmount() == 0) {
                tank.fill(resourceCopy, true);
            }
        }
        if (tank.getFluid() == null) {
            tank.fill(resourceCopy, true);
        }
        return resource.amount;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (!isFluidEqual(resource))
            return null;
        if (!doDrain) {
            int amount = tank.getFluidAmount() - resource.amount < 0 ? tank.getFluidAmount() : resource.amount;
            return new FluidStack(tank.getFluid(), amount);
        }
        return tank.drain(resource.amount, doDrain);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return this.tank.drain(maxDrain, doDrain);
    }

    public float getFluidPercentage() {
        return (float) tank.getFluidAmount() / (float) tank.getCapacity();
    }

    public int getFluidGuiHeight(int maxHeight) {
        return (int) Math.ceil(getFluidPercentage() * (float) maxHeight);
    }

    protected boolean isFluidEqual(FluidStack fluid) {
        return isFluidEqual(fluid.getFluid());
    }

    protected boolean isFluidEqual(Fluid fluid) {
        return tank.getFluid().equals(fluid);
    }

    public static boolean isStackValid(ItemStack stack) {
        if (stack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null)) {
            return stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).getModifiers().size() == 0;
        }
        return false;
    }
}