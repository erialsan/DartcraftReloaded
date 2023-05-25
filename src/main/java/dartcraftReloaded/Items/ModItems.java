package dartcraftReloaded.Items;

import dartcraftReloaded.Items.NonBurnable.ItemInertCore;
import dartcraftReloaded.Items.Tools.*;
import dartcraftReloaded.DartcraftReloaded;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import static dartcraftReloaded.Constants.*;

/**
 * Created by BURN447 on 2/4/2018.
 */
public class ModItems {

    public static ItemBase gemForceGem = new ItemBase(GEM_FORCEGEM).setCreativeTab(DartcraftReloaded.creativeTab);
    public static ItemBase ingotForce = new ItemBase(INGOT_FORCE).setCreativeTab(DartcraftReloaded.creativeTab);
    public static ItemBase nuggetForce = new ItemBase(NUGGET_FORCE).setCreativeTab(DartcraftReloaded.creativeTab);
    public static ItemBase stickForce = new ItemBase(STICK_FORCE).setCreativeTab(DartcraftReloaded.creativeTab);
    public static ItemBaseFood cookieFortune = new ItemBaseFood(COOKIE_FORTUNE, 2, 0.1f, false);
    public static ItemBaseFood soulWafer = new ItemBaseFood(SOUL_WAFER, 2, 1, false);
    public static ItemArmor forceHelmet = new ItemArmor(DartcraftReloaded.forceArmorMaterial, EntityEquipmentSlot.HEAD, FORCE_HELMET);
    public static ItemArmor forceChest = new ItemArmor(DartcraftReloaded.forceArmorMaterial, EntityEquipmentSlot.CHEST, FORCE_CHEST);
    public static ItemArmor forceLegs = new ItemArmor(DartcraftReloaded.forceArmorMaterial, EntityEquipmentSlot.LEGS, FORCE_LEGS);
    public static ItemArmor forceBoots = new ItemArmor(DartcraftReloaded.forceArmorMaterial, EntityEquipmentSlot.FEET, FORCE_BOOTS);
    public static ItemForceRod forceRod = new ItemForceRod(FORCE_ROD);
    public static ItemWoodenForceRod woodenForceRod = new ItemWoodenForceRod(WOODEN_FORCE_ROD);
    public static ItemForceWrench forceWrench = new ItemForceWrench(FORCE_WRENCH);
    public static ItemBase goldenPowerSource = new ItemBase(GOLDEN_POWER_SOURCE).setCreativeTab(DartcraftReloaded.creativeTab);
    public static ItemBase claw = new ItemBase(CLAW).setCreativeTab(DartcraftReloaded.creativeTab);
    public static ItemFortune fortune = new ItemFortune(FORTUNE);
    public static ItemBase forceGear = new ItemBase(FORCE_GEAR).setCreativeTab(DartcraftReloaded.creativeTab);
    public static ItemBase snowCookie = new ItemBase(SNOW_COOKIE).setCreativeTab(DartcraftReloaded.creativeTab);
    public static ItemForcePack forcePack = new ItemForcePack(FORCE_PACK);
    public static ItemForceBelt forceBelt = new ItemForceBelt(FORCE_BELT);
    public static ItemBottledWither bottledWither = new ItemBottledWither(BOTTLED_WITHER);
    public static ItemInertCore inertCore = new ItemInertCore(INERT_CORE);

    //Tools
    public static ItemForcePickaxe forcePickaxe = new ItemForcePickaxe(FORCE_PICKAXE);
    public static ItemForceAxe forceAxe = new ItemForceAxe(FORCE_AXE);
    public static ItemForceSword forceSword = new ItemForceSword(FORCE_SWORD);
    public static ItemForceShovel forceShovel = new ItemForceShovel(FORCE_SHOVEL);
    public static ItemForceShears forceShears = new ItemForceShears(FORCE_SHEARS);
    public static ItemForceMitt forceMitt = new ItemForceMitt(FORCE_MITT);
    public static ItemMagnetGlove magnetGlove = new ItemMagnetGlove(MAGNET_GLOVE);

    //Experience Tome
    public static ItemExperienceTome experienceTome = new ItemExperienceTome(EXPERIENCE_TOME);

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                gemForceGem,
                ingotForce,
                nuggetForce,
                stickForce,
                cookieFortune,
                soulWafer,
                forceHelmet,
                forceChest,
                forceLegs,
                forceBoots,
                forceRod,
                woodenForceRod,
                forceWrench,
                forcePickaxe,
                forceAxe,
                forceSword,
                forceShovel,
                goldenPowerSource,
                claw,
                fortune,
                forceShears,
                experienceTome,
                forceGear,
                snowCookie,
                forceMitt,
                magnetGlove,
                forcePack,
                forceBelt,
                bottledWither,
                inertCore
        );
    }

    public static void registerModels() {
        gemForceGem.registerItemModel();
        ingotForce.registerItemModel();
        nuggetForce.registerItemModel();
        stickForce.registerItemModel();
        cookieFortune.registerItemModel();
        soulWafer.registerItemModel();
        forceHelmet.registerItemModel();
        forceChest.registerItemModel();
        forceLegs.registerItemModel();
        forceBoots.registerItemModel();
        forceRod.registerItemModel();
        woodenForceRod.registerItemModel();
        forceWrench.registerItemModel();
        forcePickaxe.registerItemModel();
        forceAxe.registerItemModel();
        forceSword.registerItemModel();
        forceShovel.registerItemModel();
        goldenPowerSource.registerItemModel();
        claw.registerItemModel();
        fortune.registerItemModel();
        forceShears.registerItemModel();
        experienceTome.registerItemModel();
        forceGear.registerItemModel();
        snowCookie.registerItemModel();
        forceMitt.registerItemModel();
        magnetGlove.registerItemModel();
        forcePack.registerItemModel();
        forceBelt.registerItemModel();
        bottledWither.registerItemModel();
        inertCore.registerItemModel();
    }

    public static void registerOreDict() {
        OreDictionary.registerOre("gemForceGem", gemForceGem);
        OreDictionary.registerOre("ingotForce", ingotForce);
        OreDictionary.registerOre("nuggetForce", nuggetForce);
        OreDictionary.registerOre("gearForce", forceGear);
    }
}
