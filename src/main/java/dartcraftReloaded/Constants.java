package dartcraftReloaded;

import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.capablilities.Modifiable.Modifier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
            EMPTY_JAR = "emptyJar",
            FLIGHT_TOKEN = "flightToken",
            CAMO_TOKEN = "camoToken",
            SIGHT_TOKEN = "sightToken",
            REPAIR_TOKEN = "repairToken",
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
            FORCE_BOW = "forceBow",
            FILLED_JAR = "filledJar";


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
    public static final Modifier
            FORCE = new Modifier(0, 3, 0, "Force", YELLOW, new ItemStack(ModItems.nuggetForce), SWORD | BOW), //good
            DAMAGE = new Modifier(1, 5, 0, "Damage", RED, new ItemStack(ModItems.claw), SWORD | BOW), //good
            HEAT = new Modifier(2, 3, 1, "Heat", RED, new ItemStack(Items.BLAZE_ROD), SWORD | AXE | PICKAXE | SHOVEL | ROD | ARMOR | BOW), //good
            SPEED = new Modifier(3, 5, 1, "Speed", WHITE, new ItemStack(Items.SUGAR), AXE | PICKAXE | SHOVEL | ROD | ARMOR | BOW), //good
            LUMBERJACK = new Modifier(4, 1, 1, "Lumberjack", GOLD, new ItemStack(ModBlocks.forceLog), AXE), //good
            LUCK = new Modifier(5, 5, 2, "Luck", GREEN, new ItemStack(ModItems.fortune), PICKAXE | AXE | SHOVEL | SWORD | ROD | BOW | SHEARS | ARMOR), //good
            RAINBOW = new Modifier(6, 1, 2, "Rainbow", LIGHT_PURPLE, new ItemStack(Items.DYE, 1, 4), SHEARS), //good
            HOLDING = new Modifier(7, 1, 2, "Holding", AQUA, new ItemStack(ModItems.emptyJar), ROD), //good
            EXPERIENCE = new Modifier(8, 3, 2, "Experience", GREEN, new ItemStack(ModItems.soulWafer), PICKAXE | AXE | SHOVEL | SWORD | BOW), //good
            TOUCH = new Modifier(9, 1, 2, "Touch", GREEN, new ItemStack(Blocks.WEB), PICKAXE | AXE | SHOVEL), //good
            BANE = new Modifier(10, 1, 3, "Bane", DARK_RED, new ItemStack(Items.FERMENTED_SPIDER_EYE), SWORD | BOW), //good
            STURDY = new Modifier(11, 5, 3, "Sturdy", AQUA, new ItemStack(Blocks.OBSIDIAN), SWORD | AXE | PICKAXE | SHOVEL | ROD | ARMOR | BOW | SHEARS), //good
            SIGHT = new Modifier(12, 1, 3, "Sight", GREEN, new ItemStack(ModItems.sightToken), ARMOR | ROD), //good
            CAMO = new Modifier(13, 1, 3, "Camo", WHITE, new ItemStack(ModItems.camoToken), ARMOR | ROD), //good
            WING = new Modifier(14, 1, 4, "Wing", WHITE, new ItemStack(Items.FEATHER), ARMOR | SWORD | ROD),
            BLEED = new Modifier(15, 3, 4, "Bleed", RED, new ItemStack(Items.ARROW), SWORD | BOW), //good
            DIRECT = new Modifier(16, 1, 4, "Direct", LIGHT_PURPLE, new ItemStack(ModItems.magnetGlove), PICKAXE | AXE | SHOVEL | BOW | SWORD), //good
            ENDER = new Modifier(17, 1, 5, "Ender", AQUA, new ItemStack(Items.ENDER_PEARL), SWORD | ROD),
            REPAIR = new Modifier(18, 3, 5, "Repair", LIGHT_PURPLE, new ItemStack(ModItems.repairToken), SWORD | AXE | PICKAXE | SHOVEL | ROD | ARMOR | BOW | SHEARS), //good
            LIGHT = new Modifier(19, 5, 5, "Light", YELLOW, new ItemStack(Blocks.GLOWSTONE), SWORD | BOW | ROD), //good
            QUIVER = new Modifier(20, 1, 6, "Quiver", AQUA, new ItemStack(ModItems.arrowBundle), BOW), //good
            HEALING = new Modifier(21, 2, 6, "Healing", LIGHT_PURPLE, new ItemStack(ModItems.inertCore), ARMOR | ROD), //good
            FLIGHT = new Modifier(22, 1, 7, "Flight", LIGHT_PURPLE, new ItemStack(ModItems.flightToken), ARMOR),
            IMPERVIOUS = new Modifier(23, 1, 7, "Impervious", WHITE, new ItemStack(Items.NETHER_STAR), SWORD | AXE | PICKAXE | SHOVEL | ROD | ARMOR | BOW | SHEARS); //good

    public static final String modId = "dartcraftreloaded";
    public static final String name = "Dartcraft Reloaded";
    public static final String version = "6.1.0";
}
