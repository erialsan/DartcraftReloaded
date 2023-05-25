package dartcraftReloaded.Handlers;

import dartcraftReloaded.capablilities.BaneModifier.BaneFactory;
import dartcraftReloaded.capablilities.BaneModifier.BaneModifierStorage;
import dartcraftReloaded.capablilities.BaneModifier.IBaneModifier;
import dartcraftReloaded.capablilities.ExperienceTome.ExperienceTomeFactory;
import dartcraftReloaded.capablilities.ExperienceTome.ExperienceTomeStorage;
import dartcraftReloaded.capablilities.ExperienceTome.IExperienceTome;
import dartcraftReloaded.capablilities.ForceRod.ForceRodFactory;
import dartcraftReloaded.capablilities.ForceRod.ForceRodStorage;
import dartcraftReloaded.capablilities.ForceRod.IForceRodModifier;
import dartcraftReloaded.capablilities.ForceWrench.ForceWrenchFactory;
import dartcraftReloaded.capablilities.ForceWrench.ForceWrenchStorage;
import dartcraftReloaded.capablilities.ForceWrench.IForceWrench;
import dartcraftReloaded.capablilities.Magnet.IMagnet;
import dartcraftReloaded.capablilities.Magnet.MagnetFactory;
import dartcraftReloaded.capablilities.Magnet.MagnetStorage;
import dartcraftReloaded.capablilities.PlayerModifier.IPlayerModifier;
import dartcraftReloaded.capablilities.PlayerModifier.PlayerModifierFactory;
import dartcraftReloaded.capablilities.PlayerModifier.PlayerModifierStorage;
import dartcraftReloaded.capablilities.Shearable.IShearableMob;
import dartcraftReloaded.capablilities.Shearable.ShearableFactory;
import dartcraftReloaded.capablilities.Shearable.ShearableStorage;
import dartcraftReloaded.capablilities.ToolModifier.IToolModifier;
import dartcraftReloaded.capablilities.ToolModifier.ToolFactory;
import dartcraftReloaded.capablilities.ToolModifier.ToolModStorage;
import dartcraftReloaded.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class DCRCapabilityHandler {

    public static final ResourceLocation BANE_CAP = new ResourceLocation(Constants.modId, "baneMod");
    public static final ResourceLocation PLAYER_CAP = new ResourceLocation(Constants.modId, "playerMod");
    public static final ResourceLocation SHEAR_CAP = new ResourceLocation(Constants.modId, "shearable");

    @CapabilityInject(IToolModifier.class)
    public static Capability<IToolModifier> CAPABILITY_TOOLMOD = null;

    @CapabilityInject(IForceRodModifier.class)
    public static Capability<IForceRodModifier> CAPABILITY_FORCEROD = null;

    @CapabilityInject(IExperienceTome.class)
    public static Capability<IExperienceTome> CAPABILITY_EXPTOME = null;

    @CapabilityInject(IBaneModifier.class)
    public static Capability<IBaneModifier> CAPABILITY_BANE = null;

    @CapabilityInject(IPlayerModifier.class)
    public static Capability<IPlayerModifier> CAPABILITY_PLAYERMOD = null;

    @CapabilityInject(IShearableMob.class)
    public static Capability<IShearableMob> CAPABILITY_SHEARABLE = null;

    @CapabilityInject(IForceWrench.class)
    public static Capability<IForceWrench> CAPABILITY_FORCEWRENCH = null;

    @CapabilityInject(IMagnet.class)
    public static Capability<IMagnet> CAPABILITY_MAGNET = null;

    public static void register(){
        CapabilityManager.INSTANCE.register(IToolModifier.class, new ToolModStorage(), new ToolFactory());
        CapabilityManager.INSTANCE.register(IForceRodModifier.class, new ForceRodStorage(), new ForceRodFactory());
        CapabilityManager.INSTANCE.register(IExperienceTome.class, new ExperienceTomeStorage(), new ExperienceTomeFactory());
        CapabilityManager.INSTANCE.register(IBaneModifier.class, new BaneModifierStorage(), new BaneFactory());
        CapabilityManager.INSTANCE.register(IShearableMob.class, new ShearableStorage(), new ShearableFactory());
        CapabilityManager.INSTANCE.register(IForceWrench.class, new ForceWrenchStorage(), new ForceWrenchFactory());
        CapabilityManager.INSTANCE.register(IPlayerModifier.class, new PlayerModifierStorage(), new PlayerModifierFactory());
        CapabilityManager.INSTANCE.register(IMagnet.class, new MagnetStorage(), new MagnetFactory());

        MinecraftForge.EVENT_BUS.register(new DCRCapabilityHandler());
    }
}
