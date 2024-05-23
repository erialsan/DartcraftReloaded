package dartcraftReloaded.handlers;

import dartcraftReloaded.capablilities.BaneModifier.BaneFactory;
import dartcraftReloaded.capablilities.BaneModifier.BaneModifierStorage;
import dartcraftReloaded.capablilities.BaneModifier.IBaneModifier;
import dartcraftReloaded.capablilities.ExperienceTome.ExperienceTomeFactory;
import dartcraftReloaded.capablilities.ExperienceTome.ExperienceTomeStorage;
import dartcraftReloaded.capablilities.ExperienceTome.IExperienceTome;
import dartcraftReloaded.capablilities.ItemTE.IItemTE;
import dartcraftReloaded.capablilities.ItemTE.ItemTEFactory;
import dartcraftReloaded.capablilities.ItemTE.ItemTEStorage;
import dartcraftReloaded.capablilities.Magnet.IMagnet;
import dartcraftReloaded.capablilities.Magnet.MagnetFactory;
import dartcraftReloaded.capablilities.Magnet.MagnetStorage;
import dartcraftReloaded.capablilities.Modifiable.IModifiable;
import dartcraftReloaded.capablilities.Modifiable.ModifiableFactory;
import dartcraftReloaded.capablilities.Modifiable.ModifiableStorage;
import dartcraftReloaded.Constants;
import dartcraftReloaded.capablilities.UpgradeTome.IUpgradeTome;
import dartcraftReloaded.capablilities.UpgradeTome.UpgradeTomeFactory;
import dartcraftReloaded.capablilities.UpgradeTome.UpgradeTomeStorage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHandler {

    public static final ResourceLocation BANE_CAP = new ResourceLocation(Constants.modId, "baneMod");


    @CapabilityInject(IExperienceTome.class)
    public static Capability<IExperienceTome> CAPABILITY_EXPTOME = null;

    @CapabilityInject(IUpgradeTome.class)
    public static Capability<IUpgradeTome> CAPABILITY_UPGRADETOME = null;

    @CapabilityInject(IBaneModifier.class)
    public static Capability<IBaneModifier> CAPABILITY_BANE = null;

    @CapabilityInject(IMagnet.class)
    public static Capability<IMagnet> CAPABILITY_MAGNET = null;

    @CapabilityInject(IModifiable.class)
    public static Capability<IModifiable> CAPABILITY_MODIFIABLE = null;

    @CapabilityInject(IItemTE.class)
    public static Capability<IItemTE> CAPABILITY_TE = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IExperienceTome.class, new ExperienceTomeStorage(), new ExperienceTomeFactory());
        CapabilityManager.INSTANCE.register(IBaneModifier.class, new BaneModifierStorage(), new BaneFactory());
        CapabilityManager.INSTANCE.register(IMagnet.class, new MagnetStorage(), new MagnetFactory());
        CapabilityManager.INSTANCE.register(IUpgradeTome.class, new UpgradeTomeStorage(), new UpgradeTomeFactory());
        CapabilityManager.INSTANCE.register(IModifiable.class, new ModifiableStorage(), new ModifiableFactory());
        CapabilityManager.INSTANCE.register(IItemTE.class, new ItemTEStorage(), new ItemTEFactory());

        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());

    }
}
