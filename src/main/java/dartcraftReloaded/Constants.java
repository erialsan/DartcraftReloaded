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
            FORCE_LOG = "forceLog",
            FORCE_SAPLING = "forceSapling",
            ORE_POWER = "orePower",
            FORCE_LEAVES = "forceLeaves",
            INFUSER = "infuser",
            FORCE_FURNACE = "forceFurnace",
            LIT_FORCE_FURNACE = "litForceFurnace",
            FORCE_PLANKS = "forcePlanks",
            FORCE_TORCH = "forceTorch";

    // Items
    public static final String
            GEM_FORCEGEM = "gemForceGem",
            INGOT_FORCE = "ingotForce",
            NUGGET_FORCE = "nuggetForce",
            COOKIE_FORTUNE = "cookieFortune",
            SOUL_WAFER = "soulWafer",
            FORCE_HELMET = "forceHelmet",
            FORCE_CHEST = "forceChest",
            FORCE_LEGS = "forceLegs",
            FORCE_BOOTS = "forceBoots",
            FORCE_ROD = "forceRod",
            UPGRADE_TOME = "upgradeTome",
            GOLDEN_POWER_SOURCE = "goldenPowerSource",
            CLAW = "forceClaw",
            FORTUNE = "fortune",
            FLIGHT_TOKEN = "flightToken",
            CAMO_TOKEN = "camoToken",
            SIGHT_TOKEN = "sightToken",
            REPAIR_TOKEN = "repairToken",
            BOTTLED_WITHER = "bottledWither",
            INERT_CORE = "inertCore",
            FORCE_PICKAXE = "forcePickaxe",
            FORCE_AXE = "forceAxe",
            FORCE_SHOVEL = "forceShovel",
            FORCE_SHEARS = "forceShears",
            FORCE_SWORD = "forceSword",
            MAGNET_GLOVE = "magnetGlove",
            EXPERIENCE_TOME = "experienceTome",
            ITEM_TE = "itemTE",
            TEAR = "tear",
            ARROW_BUNDLE = "arrowBundle",
            FORCE_BOW = "forceBow",
            FILLED_JAR = "filledJar";


    // I LOVE BITMASKS
    public static final long
            PICKAXE = 1 << 0,
            AXE = 1 << 1,
            SHOVEL = 1 << 2,
            ROD = 1 << 3,
            SHEARS = 1 << 4,
            SWORD = 1 << 5,
            ARMOR = 1 << 6,
            BOW = 1 << 8;


    public static HashMap<Integer, Modifier> MODIFIER_REGISTRY = new HashMap<>();

    public static Modifier getByStack(ItemStack i) {
        for (Modifier j : MODIFIER_REGISTRY.values()) {
            if (j.getItem().getItem().equals(i.getItem()) && j.getItem().getItemDamage() == i.getItemDamage()) return j;
        }
        return null;
    }

    // Modifiers
    public static final Modifier
            FORCE = new Modifier(0, 3, 1, "Force", YELLOW, new ItemStack(ModItems.nuggetForce), SWORD | BOW), //good
            DAMAGE = new Modifier(1, 5, 1, "Damage", RED, new ItemStack(ModItems.claw), SWORD | BOW), //good
            HEAT = new Modifier(2, 3, 2, "Heat", RED, new ItemStack(Items.BLAZE_ROD), SWORD | AXE | PICKAXE | SHOVEL | ROD | ARMOR | BOW), //good
            SPEED = new Modifier(3, 5, 2, "Speed", WHITE, new ItemStack(Items.SUGAR), AXE | PICKAXE | SHOVEL | ROD | ARMOR | BOW | SWORD), //good
            LUMBERJACK = new Modifier(4, 1, 2, "Lumberjack", GOLD, new ItemStack(ModBlocks.forceLog), AXE), //good
            LUCK = new Modifier(5, 5, 3, "Luck", GREEN, new ItemStack(ModItems.fortune), PICKAXE | AXE | SHOVEL | SWORD | ROD | BOW | SHEARS | ARMOR), //good
            RAINBOW = new Modifier(6, 1, 3, "Rainbow", LIGHT_PURPLE, new ItemStack(Items.DYE, 1, 4), SHEARS), //good
            EXPERIENCE = new Modifier(7, 3, 3, "Experience", GREEN, new ItemStack(ModItems.soulWafer), PICKAXE | AXE | SHOVEL | SWORD | BOW), //good
            TOUCH = new Modifier(8, 1, 3, "Touch", GREEN, new ItemStack(Blocks.WEB), PICKAXE | AXE | SHOVEL), //good
            HOLDING = new Modifier(9, 1, 4, "Holding", AQUA, new ItemStack(Items.GLASS_BOTTLE), ROD), //good
            STURDY = new Modifier(10, 5, 4, "Sturdy", AQUA, new ItemStack(Blocks.OBSIDIAN), SWORD | AXE | PICKAXE | SHOVEL | ROD | ARMOR | BOW | SHEARS), //good
            SIGHT = new Modifier(11, 1, 4, "Sight", GREEN, new ItemStack(ModItems.sightToken), ARMOR | ROD), //good
            CAMO = new Modifier(12, 1, 4, "Camo", WHITE, new ItemStack(ModItems.camoToken), ARMOR | ROD), //good
            BLEED = new Modifier(13, 3, 5, "Bleed", RED, new ItemStack(Items.ARROW), SWORD | BOW), //good
            DIRECT = new Modifier(14, 1, 5, "Direct", LIGHT_PURPLE, new ItemStack(ModItems.magnetGlove), PICKAXE | AXE | SHOVEL | BOW | SWORD), //good
            BANE = new Modifier(15, 1, 5, "Bane", DARK_RED, new ItemStack(Items.FERMENTED_SPIDER_EYE), SWORD | BOW), //good
            LIGHT = new Modifier(16, 5, 5, "Light", YELLOW, new ItemStack(Blocks.GLOWSTONE), SWORD | BOW | ROD), //good
            HEALING = new Modifier(17, 2, 6, "Healing", LIGHT_PURPLE, new ItemStack(ModItems.inertCore), ARMOR | ROD), //good
            ENDER = new Modifier(18, 8, 6, "Ender", AQUA, new ItemStack(Items.ENDER_PEARL), SWORD | ROD), //good
            REPAIR = new Modifier(19, 3, 7, "Repair", LIGHT_PURPLE, new ItemStack(ModItems.repairToken), SWORD | AXE | PICKAXE | SHOVEL | ROD | ARMOR | BOW | SHEARS), //good
            QUIVER = new Modifier(20, 1, 7, "Quiver", AQUA, new ItemStack(ModItems.arrowBundle), BOW), //good
            IMPERVIOUS = new Modifier(21, 1, 8, "Impervious", WHITE, new ItemStack(Items.NETHER_STAR), SWORD | AXE | PICKAXE | SHOVEL | ROD | ARMOR | BOW | SHEARS); //good

    public static final String modId = "dartcraftreloaded";
    public static final String name = "Dartcraft Reloaded";
    public static final String version = "7.0";
}
