package dartcraftReloaded.items;

import dartcraftReloaded.Constants;
import dartcraftReloaded.items.nonburnable.ItemInertCore;
import dartcraftReloaded.items.tools.*;
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

    public static ItemBase gemForceGem = new ItemBase(GEM_FORCEGEM);
    public static ItemBase ingotForce = new ItemBase(INGOT_FORCE);
    public static ItemBase nuggetForce = new ItemBase(NUGGET_FORCE);
    public static ItemBase stickForce = new ItemBase(STICK_FORCE);
    public static ItemBaseFood cookieFortune = new ItemBaseFood(COOKIE_FORTUNE, 2, 0.1f, false);
    public static ItemBaseFood soulWafer = new ItemBaseFood(SOUL_WAFER, 2, 1, false);
    public static ItemArmor forceHelmet = new ItemArmor(DartcraftReloaded.forceArmorMaterial, EntityEquipmentSlot.HEAD, FORCE_HELMET);
    public static ItemArmor forceChest = new ItemArmor(DartcraftReloaded.forceArmorMaterial, EntityEquipmentSlot.CHEST, FORCE_CHEST);
    public static ItemArmor forceLegs = new ItemArmor(DartcraftReloaded.forceArmorMaterial, EntityEquipmentSlot.LEGS, FORCE_LEGS);
    public static ItemArmor forceBoots = new ItemArmor(DartcraftReloaded.forceArmorMaterial, EntityEquipmentSlot.FEET, FORCE_BOOTS);
    public static ItemForceRod forceRod = new ItemForceRod();
    public static ItemWoodenForceRod woodenForceRod = new ItemWoodenForceRod();
    public static ItemForceWrench forceWrench = new ItemForceWrench();
    public static ItemBase goldenPowerSource = new ItemBase(GOLDEN_POWER_SOURCE);
    public static ItemBase claw = new ItemBase(CLAW);
    public static ItemFortune fortune = new ItemFortune();
    public static ItemBase forceGear = new ItemBase(FORCE_GEAR);
    public static ItemBase snowCookie = new ItemBase(SNOW_COOKIE);
    public static ItemForcePack forcePack = new ItemForcePack(Constants.FORCE_PACK+"1", 8);
    public static ItemForcePack forcePack2 = new ItemForcePack(Constants.FORCE_PACK+"2", 16);
    public static ItemForcePack forcePack3 = new ItemForcePack(Constants.FORCE_PACK+"3", 24);
    public static ItemForcePack forcePack4 = new ItemForcePack(Constants.FORCE_PACK+"4", 32);
    public static ItemForcePack forcePack5 = new ItemForcePack(Constants.FORCE_PACK+"5", 40);

    public static ItemForceBelt forceBelt = new ItemForceBelt();
    public static ItemBottledWither bottledWither = new ItemBottledWither();
    public static ItemInertCore inertCore = new ItemInertCore(INERT_CORE);
    public static ItemTE itemTE = new ItemTE();
    public static ItemUpgradeCore upgradeCore = new ItemUpgradeCore();
    public static ItemBase tear = new ItemBase(TEAR);
    public static ItemBase arrowBundle = new ItemBase(ARROW_BUNDLE);
    public static ItemForceBow forceBow = new ItemForceBow();

    //Tools
    public static ItemForcePickaxe forcePickaxe = new ItemForcePickaxe();
    public static ItemForceAxe forceAxe = new ItemForceAxe();
    public static ItemForceSword forceSword = new ItemForceSword();
    public static ItemForceShovel forceShovel = new ItemForceShovel();
    public static ItemForceShears forceShears = new ItemForceShears();
    public static ItemForceMitt forceMitt = new ItemForceMitt();
    public static ItemMagnetGlove magnetGlove = new ItemMagnetGlove();

    //Experience Tome
    public static ItemExperienceTome experienceTome = new ItemExperienceTome();

    public static ItemUpgradeTome upgradeTome = new ItemUpgradeTome();

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
                forcePack2,
                forcePack3,
                forcePack4,
                forcePack5,
                forceBelt,
                bottledWither,
                inertCore,
                upgradeTome,
                itemTE,
                upgradeCore,
                tear,
                arrowBundle,
                forceBow
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
        forcePack2.registerItemModel();
        forcePack3.registerItemModel();
        forcePack4.registerItemModel();
        forcePack5.registerItemModel();
        forceBelt.registerItemModel();
        bottledWither.registerItemModel();
        inertCore.registerItemModel();
        upgradeTome.registerItemModel();
        itemTE.registerItemModel();
        upgradeCore.registerItemModel();
        tear.registerItemModel();
        arrowBundle.registerItemModel();
        forceBow.registerItemModel();
    }

    public static void registerOreDict() {
        OreDictionary.registerOre("gemForceGem", gemForceGem);
        OreDictionary.registerOre("ingotForce", ingotForce);
        OreDictionary.registerOre("nuggetForce", nuggetForce);
        OreDictionary.registerOre("gearForce", forceGear);
        OreDictionary.registerOre("tear", tear);
    }
}
