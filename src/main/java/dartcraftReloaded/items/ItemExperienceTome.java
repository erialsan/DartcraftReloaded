package dartcraftReloaded.items;

import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.ExperienceTome.ExperienceTomeProvider;
import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.util.DartUtils;
import dartcraftReloaded.util.ServerHelper;
import dartcraftReloaded.util.StringHelper;
import dartcraftReloaded.handlers.CapabilityHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import scala.Int;

import javax.annotation.Nullable;
import java.util.List;

public class ItemExperienceTome extends Item {

    public ItemExperienceTome(){
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        this.setTranslationKey(Constants.EXPERIENCE_TOME);
        this.setRegistryName(Constants.EXPERIENCE_TOME);
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("EXP: "+getExperience(stack));
    }

    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, Constants.EXPERIENCE_TOME);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if(!stack.hasCapability(CapabilityHandler.CAPABILITY_EXPTOME, null))
            return new ExperienceTomeProvider(CapabilityHandler.CAPABILITY_EXPTOME, null);
        else
            return null;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (DartUtils.isFakePlayer(player) || hand != EnumHand.MAIN_HAND) {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }
        int exp;
        int curLevel = player.experienceLevel;

        if (player.isSneaking()) {
            if (getExtraPlayerExperience(player) > 0) {
                exp = Math.min(getTotalExpForLevel(player.experienceLevel + 1) - getTotalExpForLevel(player.experienceLevel) - getExtraPlayerExperience(player), getExperience(stack));
                setPlayerExperience(player, getPlayerExperience(player) + exp);
                if (player.experienceLevel < curLevel + 1 && getPlayerExperience(player) >= getTotalExpForLevel(curLevel + 1)) {
                    setPlayerLevel(player, curLevel + 1);
                }
                modifyExperience(stack, -exp);
            } else {
                exp = Math.min(getTotalExpForLevel(player.experienceLevel + 1) - getTotalExpForLevel(player.experienceLevel), getExperience(stack));
                setPlayerExperience(player, getPlayerExperience(player) + exp);
                if (player.experienceLevel < curLevel + 1 && getPlayerExperience(player) >= getTotalExpForLevel(curLevel + 1)) {
                    setPlayerLevel(player, curLevel + 1);
                }
                modifyExperience(stack, -exp);
            }
        } else {
            if (getExtraPlayerExperience(player) > 0) {
                exp = Math.min(getExtraPlayerExperience(player), getSpace(stack));
                setPlayerExperience(player, getPlayerExperience(player) - exp);
                if (player.experienceLevel < curLevel) {
                    setPlayerLevel(player, curLevel);
                }
                modifyExperience(stack, exp);
            } else if (player.experienceLevel > 0) {
                exp = Math.min(getTotalExpForLevel(player.experienceLevel) - getTotalExpForLevel(player.experienceLevel - 1), getSpace(stack));
                setPlayerExperience(player, getPlayerExperience(player) - exp);
                if (player.experienceLevel < curLevel - 1) {
                    setPlayerLevel(player, curLevel - 1);
                }
                modifyExperience(stack, exp);
            }
        }
        return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    public static int getPlayerExperience(EntityPlayer player) {

        return getTotalExpForLevel(player.experienceLevel) + getExtraPlayerExperience(player);
    }

    public static int getExtraPlayerExperience(EntityPlayer player) {

        return Math.round(player.experience * player.xpBarCap());
    }

    public static void setPlayerExperience(EntityPlayer player, int exp) {

        player.experienceLevel = 0;
        player.experience = 0.0F;
        player.experienceTotal = 0;

        addExperienceToPlayer(player, exp);
    }

    public static void setPlayerLevel(EntityPlayer player, int level) {

        player.experienceLevel = level;
        player.experience = 0.0F;
    }

    public static void addExperienceToPlayer(EntityPlayer player, int exp) {

        int i = Integer.MAX_VALUE - player.experienceTotal;

        if (exp > i) {
            exp = i;
        }
        player.experience += (float) exp / (float) player.xpBarCap();
        for (player.experienceTotal += exp; player.experience >= 1.0F; player.experience /= (float) player.xpBarCap()) {
            player.experience = (player.experience - 1.0F) * (float) player.xpBarCap();
            addExperienceLevelToPlayer(player, 1);
        }
    }

    public static void addExperienceLevelToPlayer(EntityPlayer player, int levels) {

        player.experienceLevel += levels;

        if (player.experienceLevel < 0) {
            player.experienceLevel = 0;
            player.experience = 0.0F;
            player.experienceTotal = 0;
        }
    }

    public static int getTotalExpForLevel(int level) {

        return level >= 32 ? (9 * level * level - 325 * level + 4440) / 2 : level >= 17 ? (5 * level * level - 81 * level + 720) / 2 : (level * level + 6 * level);
    }

    public static void modifyExperience(ItemStack stack, int exp) {
        int storedExp = getExperience(stack) + exp;
        stack.getCapability(CapabilityHandler.CAPABILITY_EXPTOME, null).setExperienceValue(storedExp);

    }

    public static int getExperience(ItemStack stack) {
        return stack.getCapability(CapabilityHandler.CAPABILITY_EXPTOME, null).getExperienceValue();
    }

    public static int getSpace(ItemStack stack) {
        return Integer.MAX_VALUE - getExperience(stack);
    }
}
