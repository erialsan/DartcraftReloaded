package dartcraftReloaded.Items;

import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.Handlers.DCRGUIHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by BURN447 on 6/2/2018.
 */
public class ItemFortune extends ItemBase {
    private static String[] fortunes = new String[186];

    public ItemFortune(String name) {
        super(name);
        this.name = name;
        this.setCreativeTab(DartcraftReloaded.creativeTab);

        Arrays.fill(fortunes, "0");

        for (int i = 0; i <= 185; i++) {
            fortunes[i] = "text.dartcraftReloaded.fortune" + i;
        }

    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Read me.");
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        NBTTagCompound nbt;
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(stack.hasTagCompound()) {
            nbt = stack.getTagCompound();
        }
        else {
            nbt = new NBTTagCompound();
        }

        if(!nbt.hasKey("message")) {
            addMessage(stack, nbt);
        }

        playerIn.openGui(DartcraftReloaded.instance, DCRGUIHandler.FORTUNE, worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ());
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }



    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        NBTTagCompound nbt;
        ItemStack stack = player.getHeldItem(hand);
        if(stack.hasTagCompound()) {
            nbt = stack.getTagCompound();
        }
        else {
            nbt = new NBTTagCompound();
        }

        if(!nbt.hasKey("message")) {
            addMessage(stack, nbt);
        }

        player.openGui(DartcraftReloaded.instance, DCRGUIHandler.FORTUNE, worldIn, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
        return EnumActionResult.PASS;
    }

    public static void addMessage(ItemStack stack, NBTTagCompound nbt) {
        Random generator = new Random();
        int rand = generator.nextInt(fortunes.length);
        nbt.setString("message", fortunes[rand]);
        stack.setTagCompound(nbt);
    }
}
