package dartcraftReloaded;

import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.capablilities.Modifiable.Modifier;
import net.minecraft.item.ItemStack;

/**
 * Created by BURN447 on 3/18/2018.
 */
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
            UPGRADE_CORE = "upgradeCore";


    // Tools
    public static final long
            PICKAXE = 0,
            AXE = 1,
            SHOVEL = 2,
            ROD = 4,
            SHEARS = 8,
            SWORD = 16,
            ARMOR = 32,
            BACKPACK = 64,
            CORE = 128;

    // Modifiers
    public static final Modifier
            FORCE = new Modifier(0, 3, 1, new ItemStack(ModItems.nuggetForce), SWORD | AXE);

    public static final String modId = "dartcraftreloaded";
    public static final String name = "Dartcraft Reloaded";
    public static final String version = "6.1.0";
}
