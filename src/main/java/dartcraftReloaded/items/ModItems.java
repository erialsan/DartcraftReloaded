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

public class ModItems {

    public static ItemBase gemForceGem = new ItemBase(GEM_FORCEGEM);
    public static ItemBase ingotForce = new ItemBase(INGOT_FORCE);
    public static ItemBase nuggetForce = new ItemBase(NUGGET_FORCE);
    public static ItemBaseFood cookieFortune = new ItemBaseFood(COOKIE_FORTUNE, 2, 0.1f, false);
    public static ItemBaseFood soulWafer = new ItemBaseFood(SOUL_WAFER, 2, 1, false);
    public static ItemArmor forceHelmet = new ItemArmor(DartcraftReloaded.forceArmorMaterial, EntityEquipmentSlot.HEAD, FORCE_HELMET);
    public static ItemArmor forceChest = new ItemArmor(DartcraftReloaded.forceArmorMaterial, EntityEquipmentSlot.CHEST, FORCE_CHEST);
    public static ItemArmor forceLegs = new ItemArmor(DartcraftReloaded.forceArmorMaterial, EntityEquipmentSlot.LEGS, FORCE_LEGS);
    public static ItemArmor forceBoots = new ItemArmor(DartcraftReloaded.forceArmorMaterial, EntityEquipmentSlot.FEET, FORCE_BOOTS);
    public static ItemForceRod forceRod = new ItemForceRod();
    public static ItemBase goldenPowerSource = new ItemBase(GOLDEN_POWER_SOURCE);
    public static ItemBase claw = new ItemBase(CLAW, "Drops from bats");
    public static ItemFortune fortune = new ItemFortune();
    public static ItemFilledJar filledJar = new ItemFilledJar();
    public static ItemBottledWither bottledWither = new ItemBottledWither();
    public static ItemInertCore inertCore = new ItemInertCore(INERT_CORE);
    public static ItemTE itemTE = new ItemTE();
    public static ItemBase tear = new ItemBase(TEAR, "Drops from cold animals");
    public static ItemBase arrowBundle = new ItemBase(ARROW_BUNDLE);
    public static ItemForceBow forceBow = new ItemForceBow();

    //Tools
    public static ItemForcePickaxe forcePickaxe = new ItemForcePickaxe();
    public static ItemForceAxe forceAxe = new ItemForceAxe();
    public static ItemForceSword forceSword = new ItemForceSword();
    public static ItemForceShovel forceShovel = new ItemForceShovel();
    public static ItemForceShears forceShears = new ItemForceShears();
    public static ItemBase magnetGlove = new ItemBase(MAGNET_GLOVE);

    //Tomes
    public static ItemExperienceTome experienceTome = new ItemExperienceTome();
    public static ItemUpgradeTome upgradeTome = new ItemUpgradeTome();

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(
                gemForceGem,
                ingotForce,
                nuggetForce,
                cookieFortune,
                soulWafer,
                forceHelmet,
                forceChest,
                forceLegs,
                forceBoots,
                forceRod,
                filledJar,
                forcePickaxe,
                forceAxe,
                forceSword,
                forceShovel,
                goldenPowerSource,
                claw,
                fortune,
                forceShears,
                experienceTome,
                magnetGlove,
                bottledWither,
                inertCore,
                upgradeTome,
                itemTE,
                tear,
                arrowBundle,
                forceBow
        );
    }

    public static void registerModels() {
        gemForceGem.registerItemModel();
        ingotForce.registerItemModel();
        nuggetForce.registerItemModel();
        cookieFortune.registerItemModel();
        soulWafer.registerItemModel();
        forceHelmet.registerItemModel();
        forceChest.registerItemModel();
        forceLegs.registerItemModel();
        forceBoots.registerItemModel();
        forceRod.registerItemModel();
        filledJar.registerItemModel();
        forcePickaxe.registerItemModel();
        forceAxe.registerItemModel();
        forceSword.registerItemModel();
        forceShovel.registerItemModel();
        goldenPowerSource.registerItemModel();
        claw.registerItemModel();
        fortune.registerItemModel();
        forceShears.registerItemModel();
        experienceTome.registerItemModel();
        magnetGlove.registerItemModel();
        bottledWither.registerItemModel();
        inertCore.registerItemModel();
        upgradeTome.registerItemModel();
        itemTE.registerItemModel();
        tear.registerItemModel();
        arrowBundle.registerItemModel();
        forceBow.registerItemModel();
    }

    public static void registerOreDict() {
        OreDictionary.registerOre("gemForceGem", gemForceGem);
        OreDictionary.registerOre("ingotForce", ingotForce);
        OreDictionary.registerOre("nuggetForce", nuggetForce);
        OreDictionary.registerOre("tear", tear);
    }
}
