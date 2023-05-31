package dartcraftReloaded.items;

import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
public class ItemFilledJar extends ItemBase {
    public final static String NBT_ANIMAL = "Animal";
    public final static String NBT_ANIMAL_DISPLAY = "Animal_Metadata";


    public ItemFilledJar() {
        super(dartcraftReloaded.Constants.FILLED_JAR);
        setMaxStackSize(1);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return false;
    }


    private boolean hasAnimal(NBTTagCompound tagCompound) {
        return tagCompound != null && tagCompound.hasKey(NBT_ANIMAL, Constants.NBT.TAG_COMPOUND);
    }

    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItemMainhand();
        if (stack.getItem() != ModItems.filledJar) return EnumActionResult.PASS;
        if (!hasAnimal(stack.getTagCompound())) return EnumActionResult.FAIL;
        NBTTagCompound stackTags = stack.getTagCompound();
        NBTTagCompound entityTags = stackTags.getCompoundTag(NBT_ANIMAL);
        if (!entityTags.hasKey("id", Constants.NBT.TAG_STRING)) {
            stackTags.removeTag(NBT_ANIMAL);
            return EnumActionResult.FAIL;
        }

        if (worldIn.isRemote) return EnumActionResult.SUCCESS;

        if (!playerIn.canPlayerEdit(pos.offset(side), side, stack)) {
            return EnumActionResult.FAIL;
        }

        IBlockState iblockstate = worldIn.getBlockState(pos);
        pos = pos.offset(side);
        double d0 = 0.0D;

        if (side == EnumFacing.UP && iblockstate.getBlock() instanceof BlockFence) {
            d0 = 0.5D;
        }

        entityTags.setTag("Pos", this.newDoubleNBTList(pos.getX() + 0.5D, pos.getY() + d0, pos.getZ() + 0.5D));
        entityTags.setTag("Motion", this.newDoubleNBTList(0, 0, 0));
        entityTags.setFloat("FallDistance", 0);
        entityTags.setInteger("Dimension", worldIn.provider.getDimension());

        Entity entity = EntityList.createEntityFromNBT(entityTags, worldIn);

        if (entity != null) {
            worldIn.spawnEntity(entity);
            playerIn.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }

        playerIn.inventory.markDirty();

        return EnumActionResult.SUCCESS;
    }

    private NBTTagList newDoubleNBTList(double... numbers) {
        NBTTagList nbttaglist = new NBTTagList();

        for (double d0 : numbers) {
            nbttaglist.appendTag(new NBTTagDouble(d0));
        }

        return nbttaglist;
    }

    public static void addTargetToLasso(ItemStack stack, EntityLivingBase target) {

        float health = target.getHealth();
        float maxHealth = target.getMaxHealth();

        NBTTagCompound entityTags = new NBTTagCompound();

        if (!target.writeToNBTAtomically(entityTags)) {
            return;
        }

        target.setDead();

        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
        }
        nbt.setTag(NBT_ANIMAL, entityTags);
        NBTTagCompound display;
        if (nbt.hasKey(NBT_ANIMAL_DISPLAY, Constants.NBT.TAG_COMPOUND)) {
            display = nbt.getCompoundTag(NBT_ANIMAL_DISPLAY);
        } else {
            NBTTagCompound nbt2 = new NBTTagCompound();
            nbt.setTag(NBT_ANIMAL_DISPLAY, nbt2);
            display = nbt2;
        }
        display.setFloat("Health", health);
        display.setFloat("MaxHealth", maxHealth);

        String name = target.getName();
        nbt.setString("Name", name);
        stack.setStackDisplayName(TextFormatting.AQUA+"Bottled "+name);
    }

}
