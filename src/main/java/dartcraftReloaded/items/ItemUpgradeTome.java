package dartcraftReloaded.items;

import dartcraftReloaded.DartcraftReloaded;
import dartcraftReloaded.capablilities.UpgradeTome.UpgradeTomeProvider;
import dartcraftReloaded.handlers.DCRCapabilityHandler;
import dartcraftReloaded.capablilities.ExperienceTome.ExperienceTomeProvider;
import dartcraftReloaded.capablilities.UpgradeTome.IUpgradeTome;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;

import static dartcraftReloaded.Constants.UPGRADE_TOME;

public class ItemUpgradeTome extends Item {

    public ItemUpgradeTome() {
        this.setCreativeTab(DartcraftReloaded.creativeTab);
        this.setTranslationKey(UPGRADE_TOME);
        this.setRegistryName(UPGRADE_TOME);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        IUpgradeTome cap = stack.getCapability(DCRCapabilityHandler.CAPABILITY_UPGRADETOME, null);
        if (cap != null) {
            tooltip.add("§b Level "+cap.getLevel());
            tooltip.add(cap.getUpgradePoints()+" §b Upgrade Points");
        }
    }

    public void registerItemModel() {
        DartcraftReloaded.proxy.registerItemRenderer(this, 0, UPGRADE_TOME);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if (!stack.hasCapability(DCRCapabilityHandler.CAPABILITY_UPGRADETOME, null))
            return new UpgradeTomeProvider(DCRCapabilityHandler.CAPABILITY_UPGRADETOME, null);
        else
            return null;
    }


}