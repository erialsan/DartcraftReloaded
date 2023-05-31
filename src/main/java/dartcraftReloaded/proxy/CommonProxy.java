package dartcraftReloaded.proxy;

import dartcraftReloaded.handlers.CapabilityHandler;
import dartcraftReloaded.items.ModItems;
import dartcraftReloaded.blocks.ModBlocks;
import dartcraftReloaded.tileEntity.TileEntityForceFurnace;
import dartcraftReloaded.tileEntity.TileEntityInfuser;
import dartcraftReloaded.tileEntity.TileEntityTimeTorch;
import dartcraftReloaded.Constants;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
public class CommonProxy {

    public void registerItemRenderer(Item item, int meta, String id) {
    }

    public String localize(String unlocalized, Object... args) {
        return I18n.translateToLocalFormatted(unlocalized, args);
    }

    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityInfuser.class, Constants.modId + ":blockInfuser");
        GameRegistry.registerTileEntity(TileEntityForceFurnace.class, Constants.modId + ":blockFurnace");
        GameRegistry.registerTileEntity(TileEntityTimeTorch.class, Constants.modId + ":torchTime");
    }

    public void registerSmeltingRecipes() {
        GameRegistry.addSmelting(ModBlocks.orePower, new ItemStack(ModItems.gemForceGem, 2), 2.0F);
        GameRegistry.addSmelting(ModBlocks.forceLog, new ItemStack(ModItems.goldenPowerSource), 2.0F);
    }

    @Mod.EventHandler
    public void preInit(){
        CapabilityHandler.register();
    }

    @Mod.EventHandler
    public void init() {

    }
}