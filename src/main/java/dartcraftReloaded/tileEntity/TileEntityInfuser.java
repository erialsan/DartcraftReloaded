package dartcraftReloaded.tileEntity;

import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.capablilities.Modifiable.IModifiableTool;
import dartcraftReloaded.capablilities.Modifiable.Modifier;
import dartcraftReloaded.capablilities.UpgradeTome.IUpgradeTome;
import dartcraftReloaded.config.ConfigHandler;
import dartcraftReloaded.container.InfuserItemStackHandler;
import dartcraftReloaded.container.InfuserSlotWrapper;
import dartcraftReloaded.energy.DCREnergyStorage;
import dartcraftReloaded.container.InfuserTank;
import dartcraftReloaded.fluids.FluidForce;
import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.handlers.SoundHandler;
import dartcraftReloaded.items.ModItems;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;


public class TileEntityInfuser extends TileEntity implements ITickable, ICapabilityProvider, ITileEntityProvider, IFluidHandler {


    public final InfuserItemStackHandler handler;
    public final InfuserSlotWrapper wrapper;
    public InfuserTank tank;
    public int processTime = -1;
    public DCREnergyStorage energyStorage = new DCREnergyStorage(MAX_POWER, 1000);
    private int lastEnergy;
    public int maxProcessTime = 0;

    public static int MAX_POWER = 10000;
    public int fluidContained;

    public int tickCount;
    public float pageFlip;
    public float pageFlipPrev;
    public float flipT;
    public float flipA;
    public float bookSpread;
    public float bookSpreadPrev;
    public float bookRotation;
    public float bookRotationPrev;
    public float tRot;
    private static final Random rand = new Random();

    public TileEntityInfuser() {
        this.handler = new InfuserItemStackHandler(11, this) {
            @Override
            public int getSlotLimit(int slot) {
                return slot == 9 ? 64 : 1; // 64 for force gems
            }
        };
        this.wrapper = new InfuserSlotWrapper(handler);

        tank = new InfuserTank(10000, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.processTime = nbt.getInteger("time");
        this.maxProcessTime = nbt.getInteger("maxProcessTime");
        handler.deserializeNBT(nbt.getCompoundTag("ItemStackHandler"));
        tank.readFromNBT(nbt);
        energyStorage.readFromNBT(nbt);
        super.readFromNBT(nbt);
        markDirty();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("time", processTime);
        nbt.setInteger("maxProcessTime", maxProcessTime);
        nbt.setTag("ItemStackHandler", handler.serializeNBT());
        tank.writeToNBT(nbt);
        energyStorage.writeToNBT(nbt);
        return super.writeToNBT(nbt);
    }

    @Override
    public final SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return new SPacketUpdateTileEntity(this.pos, -1, compound);
    }

    @Override
    public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public final NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        return compound;
    }

    @Override
    public final void handleUpdateTag(NBTTagCompound compound) {
        this.readFromNBT(compound);
    }

    @Override
    public void update() {
        this.bookSpreadPrev = this.bookSpread;
        this.bookRotationPrev = this.bookRotation;
        EntityPlayer entityplayer = this.world.getClosestPlayer((float)this.pos.getX() + 0.5F, (float)this.pos.getY() + 0.5F, (float)this.pos.getZ() + 0.5F, 3.0, false);
        if (entityplayer != null) {
            double d0 = entityplayer.posX - (double)((float)this.pos.getX() + 0.5F);
            double d1 = entityplayer.posZ - (double)((float)this.pos.getZ() + 0.5F);
            this.tRot = (float) MathHelper.atan2(d1, d0);
            this.bookSpread += 0.1F;
            if (this.bookSpread < 0.5F || rand.nextInt(40) == 0) {
                float f1 = this.flipT;

                do {
                    this.flipT += (float)(rand.nextInt(4) - rand.nextInt(4));
                } while(f1 == this.flipT);
            }
        } else {
            this.tRot += 0.02F;
            this.bookSpread -= 0.1F;
        }

        while(this.bookRotation >= 3.1415927F) {
            this.bookRotation -= 6.2831855F;
        }

        while(this.bookRotation < -3.1415927F) {
            this.bookRotation += 6.2831855F;
        }

        while(this.tRot >= 3.1415927F) {
            this.tRot -= 6.2831855F;
        }

        while(this.tRot < -3.1415927F) {
            this.tRot += 6.2831855F;
        }

        float f2;
        for(f2 = this.tRot - this.bookRotation; f2 >= 3.1415927F; f2 -= 6.2831855F) {
        }

        while(f2 < -3.1415927F) {
            f2 += 6.2831855F;
        }

        this.bookRotation += f2 * 0.4F;
        this.bookSpread = MathHelper.clamp(this.bookSpread, 0.0F, 1.0F);
        ++this.tickCount;
        this.pageFlipPrev = this.pageFlip;
        float f = (this.flipT - this.pageFlip) * 0.4F;
        f = MathHelper.clamp(f, -0.2F, 0.2F);
        this.flipA += (f - this.flipA) * 0.9F;
        this.pageFlip += this.flipA;



        fluidContained = tank.getFluidAmount();
        if (world != null) {
            if (!world.isRemote) {
                if (energyStorage.getEnergyStored() != lastEnergy) {
                    lastEnergy = energyStorage.getEnergyStored();
                    sync();
                }
                processForceGems();
                if (processTime >= 0) {
                    if (energyStorage.getEnergyStored() >= ConfigHandler.infuserConsumption) {
                        energyStorage.extractEnergy(ConfigHandler.infuserConsumption, false);
                        processTime++;
                    }
                    sync();
                }
                if (processTime >= maxProcessTime && maxProcessTime > 0) {
                    processTime = -1;
                    processItem();
                    sync();
                }
            }
        }

    }

    public static int getPoints(Modifier m) {
        switch(m.getTier()) {
            case 0:
                return 1;
            case 1:
                return 4;
            case 2:
                return 16;
            case 3:
                return 32;
            case 4:
                return 32;
            case 5:
                return 64;
            case 6:
                return 64;
            case 7:
                return 128;
        }
        return 0;
    }

    public static int getThreshold(int level) {
        switch(level) {
            case 1:
                return 48;
            case 2:
                return 64;
            case 3:
                return 96;
            case 4:
                return 128;
            case 5:
                return 160;
            case 6:
                return 192;
            case 7:
                return 256;
        }
        return Integer.MAX_VALUE;
    }

    private void processItem() {
        HashMap<Modifier, Integer> recipe = getRecipe();
        ItemStack tool = handler.getStackInSlot(8);
        IModifiable cap = tool.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null);
        if (cap == null) return;
        int points = 0;
        IUpgradeTome tome = null;
        ItemStack stack = handler.getStackInSlot(10);
        if (stack.hasCapability(CapabilityHandler.CAPABILITY_UPGRADETOME, null)) {
            tome = stack.getCapability(CapabilityHandler.CAPABILITY_UPGRADETOME, null);
        }
        for (Map.Entry<Modifier, Integer> entry : recipe.entrySet()) {
            if (entry.getValue() > 0) {
                points += getPoints(entry.getKey());
            }
            cap.setModifier(entry.getKey().getId(), entry.getValue());
            if (tome != null) {
                if (!tome.getSeenModifiers().contains(entry.getKey().getId())) {
                    if (entry.getValue() > 0) {
                        points += 25;
                        tome.addSeenModifier(entry.getKey().getId());
                    }
                }
            }
        }
        if (tome != null) {
            tome.setUpgradePoints(tome.getUpgradePoints() + points);
        }

        for (int i = 0; i < 8; i++) {
            handler.setStackInSlot(i, ItemStack.EMPTY);
        }

        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.SPARKLE, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    public void onButton() {
        if (!modifiersEmpty() && !handler.getStackInSlot(8).isEmpty() && tank.getFluidAmount() >= 1000 && processTime == -1) {
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundHandler.START, SoundCategory.BLOCKS, 1.0f, 1.0f);
            tank.drain(1000, true);
            maxProcessTime = getMaxProcessTime();
            processTime = 0;
            sync();
        }
    }

    private HashMap<Modifier, Integer> getRecipe() {
        HashMap<Modifier, Integer> map = new HashMap<>();
        for (Modifier m : Constants.MODIFIER_REGISTRY.values()) {
            map.put(m, 0);
        }
        for (int i = 0; i < 8; i++) {
            Modifier m = Constants.getByStack(handler.getStackInSlot(i));
            if (m != null) {
                map.put(m, map.get(m) + 1);
            }
        }
        return map;
    }

    //Processes force Gems in the force infuser slot
    private void processForceGems() {
        if (handler.getStackInSlot(9).getItem() == ModItems.gemForceGem) {
            FluidStack force = new FluidStack(FluidRegistry.getFluid("force"), 1000);

            if (tank.getFluidAmount() < tank.getCapacity() - 100) {
                fill(force, true);
                if (handler.getStackInSlot(9).getCount() > 1) {
                    handler.getStackInSlot(9).setCount(handler.getStackInSlot(9).getCount() - 1);
                } else handler.setStackInSlot(9, ItemStack.EMPTY);

                sync();
            }
        }
    }

    public void sync() {
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
        markDirty();
    }


    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) this.wrapper;
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

    public int getMaxProcessTime() {
        int time = 10;
        for (int i = 0; i < 8; i++) {
            Modifier m = Constants.getByStack(handler.getStackInSlot(i));
            if (m != null) {
                time += 20*(m.getTier());
            }
        }
        double time2 = ConfigHandler.infuserTimeMultiplier * time;
        if (ConfigHandler.infuserMaxTime == -1) return (int) time2;
        return (int) Math.min(ConfigHandler.infuserTimeMultiplier * time, ConfigHandler.infuserMaxTime);
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

    public long getValidToolsCombinedModifiers() {
        long total = Long.MAX_VALUE;
        for (int i = 0; i < 8; i++) {
            Modifier m = Constants.getByStack(handler.getStackInSlot(i));
            if (m != null) {
                total &= m.getAllowedTools();
            }
        }
        return total;
    }

    public boolean modifiersEmpty() {
        for (int i = 0; i < 8; i++) {
            if (!handler.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    public boolean isStackValid(ItemStack stack) {
        if (stack.getItem() instanceof IModifiableTool) {
            long code = ((IModifiableTool) stack.getItem()).getTool();
            if (stack.hasCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null) && stack.getCapability(CapabilityHandler.CAPABILITY_MODIFIABLE, null).getModifiers().size() == 0) {
                if (modifiersEmpty()) return true;
                else {
                    return ((code & getValidToolsCombinedModifiers()) == code);
                }
            }
        }

        return false;
    }

    public boolean isModifierValid(ItemStack stack) {
        Modifier m = Constants.getByStack(stack);
        if (m == null) return false;
        HashMap<Modifier, Integer> map = getRecipe();
        if (map.get(m) >= m.getMaxLevels()) return false;
        if (getBookLevel() >= m.getTier()) {
            ItemStack tool = handler.getStackInSlot(8);
            if (tool.getItem() instanceof IModifiableTool) {
                long code = ((IModifiableTool) tool.getItem()).getTool();
                return (code & m.getAllowedTools()) == code;
            } else return tool.isEmpty();
        }
        return false;
    }

    public boolean validRecipe() {
        if (!handler.getStackInSlot(8).isEmpty()) {
            for (int i = 0; i < 8; i++) {
                if (!handler.getStackInSlot(i).isEmpty()) return true;
            }
        }
        return false;
    }

    public int getBookLevel() {
        if (handler.getStackInSlot(10).isEmpty()) return 0;
        ItemStack stack = handler.getStackInSlot(10);
        if (stack.hasCapability(CapabilityHandler.CAPABILITY_UPGRADETOME, null)) {
            IUpgradeTome cap = stack.getCapability(CapabilityHandler.CAPABILITY_UPGRADETOME, null);
            if (cap != null) return cap.getLevel();
        }
        return 0;
    }
}