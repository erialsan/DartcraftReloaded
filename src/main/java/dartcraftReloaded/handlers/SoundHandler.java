package dartcraftReloaded.handlers;

import dartcraftReloaded.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundHandler {
    public static SoundEvent SPARKLE;
    public static SoundEvent START;

    public static void registerSounds()
    {
        SPARKLE = registerSound("sparkle");
        START = registerSound("start");
    }

    private static SoundEvent registerSound(String name)
    {
        ResourceLocation location = new ResourceLocation(Constants.modId, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}
