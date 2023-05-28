package dartcraftReloaded;

import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.capablilities.Modifiable.Modifier;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

import static net.minecraft.util.text.TextFormatting.*;

public class Constants {

    // Blocks
    public static final String
            FORCE_BRICK = "forceBrick",
            FORCE_BRICK_SLAB = "forceBrickSlab",
            FORCE_BRICK_STAIR = "forceBrickStair",
            FORCE_LOG = "forceLog",
            FORCE_SAPLING = "forceSapling",
            ORE_POWER = "orePower",
            FORCE_LEAVES = "forceLeaves",
            INFUSER = "infuser",
            FORCE_FURNACE = "forceFurnace",
            LIT_FORCE_FURNACE = "litForceFurnace",
            FORCE_PLANKS = "forcePlanks",
            FORCE_TORCH = "forceTorch",
            TIME_TORCH = "timeTorch";

    // Items
    public static final String
            GEM_FORCEGEM = "gemForceGem",
            INGOT_FORCE = "ingotForce",
            NUGGET_FORCE = "nuggetForce",
            STICK_FORCE = "stickForce",
            COOKIE_FORTUNE = "cookieFortune",
            SOUL_WAFER = "soulWafer",
            FORCE_HELMET = "forceHelmet",
            FORCE_CHEST = "forceChest",
            FORCE_LEGS = "forceLegs",
            FORCE_BOOTS = "forceBoots",
            FORCE_ROD = "forceRod",
            WOODEN_FORCE_ROD = "woodenForceRod",
            UPGRADE_TOME = "upgradeTome",
            FORCE_WRENCH = "forceWrench",
            GOLDEN_POWER_SOURCE = "goldenPowerSource",
            CLAW = "forceClaw",
            FORTUNE = "fortune",
            FORCE_GEAR = "forceGear",
            SNOW_COOKIE = "snowCookie",
            EMPTY_JAR = "emptyJar",
            FORCE_PACK = "forcePack",
            FORCE_BELT = "forceBelt",
            BOTTLED_WITHER = "bottledWither",
            INERT_CORE = "inertCore",
            FORCE_PICKAXE = "forcePickaxe",
            FORCE_AXE = "forceAxe",
            FORCE_SHOVEL = "forceShovel",
            FORCE_SHEARS = "forceShears",
            FORCE_SWORD = "forceSword",
            FORCE_MITT = "forceMitt",
            MAGNET_GLOVE = "magnetGlove",
            EXPERIENCE_TOME = "experienceTome",
            ITEM_TE = "itemTE",
            UPGRADE_CORE = "upgradeCore",
            TEAR = "tear",
            ARROW_BUNDLE = "arrowBundle",
            FORCE_BOW = "forceBow";


    // I LOVE BITMASKS
    public static final long
            PICKAXE = 1,
            AXE = 2,
            SHOVEL = 4,
            ROD = 8,
            SHEARS = 16,
            SWORD = 32,
            ARMOR = 64,
            CORE = 128,
            BOW = 256;


    public static HashMap<Integer, Modifier> MODIFIER_REGISTRY = new HashMap<>();

    public static Modifier getByStack(ItemStack i) {
        for (Modifier j : MODIFIER_REGISTRY.values()) {
            if (j.getItem().getItem().equals(i.getItem()) && j.getItem().getItemDamage() == i.getItemDamage()) return j;
        }
        return null;
    }

    // Modifiers
    // TODO: test luck on bow
    public static final Modifier
            FORCE = new Modifier(0, 3, 0, "Force", YELLOW, new ItemStack(ModItems.nuggetForce), SWORD | AXE), //good
            DAMAGE = new Modifier(1, 5, 0, "Damage", RED, new ItemStack(ModItems.claw), SWORD | AXE | CORE | BOW), //good
            HEAT = new Modifier(2, 3, 1, "Heat", RED, new ItemStack(Items.BLAZE_ROD), SWORD | AXE | PICKAXE | SHOVEL | ROD | ARMOR | CORE | BOW), //good
            SPEED = new Modifier(3, 5, 1, "Speed", WHITE, new ItemStack(Items.SUGAR), AXE | PICKAXE | SHOVEL | ROD | ARMOR | CORE | BOW), //good
            LUMBERJACK = new Modifier(4, 1, 1, "Lumberjack", GOLD, new ItemStack(ModBlocks.forceLog), AXE), //good
            LUCK = new Modifier(5, 5, 2, "Luck", GREEN, new ItemStack(ModItems.fortune), PICKAXE | AXE | SHOVEL | SWORD | ROD | BOW | SHEARS), //good
            RAINBOW = new Modifier(6, 1, 2, "Rainbow", LIGHT_PURPLE, new ItemStack(Items.DYE, 1, 4), SHEARS),
            HOLDING = new Modifier(7, 1, 2, "Holding", GRAY, new ItemStack(ModItems.emptyJar), ROD),
            EXPERIENCE = new Modifier(8, 3, 2, "Experience", GREEN, new ItemStack(ModItems.soulWafer), PICKAXE | AXE | SHOVEL | SWORD | CORE),
            QUIVER = new Modifier(17, 1, 4, "Quiver", GRAY, new ItemStack(ModItems.arrowBundle), BOW);

    public static final String modId = "dartcraftreloaded";
    public static final String name = "Dartcraft Reloaded";
    public static final String version = "6.1.0";
}
