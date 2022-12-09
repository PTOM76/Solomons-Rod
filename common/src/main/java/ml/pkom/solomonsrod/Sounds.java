package ml.pkom.solomonsrod;

import ml.pkom.mcpitanlibarch.api.event.registry.RegistryEvent;
import net.minecraft.sound.SoundEvent;

public class Sounds {

    public static final RegistryEvent<SoundEvent> CREATE_SOUND = register("create");
    public static final RegistryEvent<SoundEvent> CRASH_SOUND = register("crash");
    public static final RegistryEvent<SoundEvent> ERASE_SOUND = register("erase");
    public static final RegistryEvent<SoundEvent> BAM_SOUND = register("bam");
    public static final RegistryEvent<SoundEvent> NOCRASH_SOUND = register("nocrash");
    public static void init(){

    }

    private static RegistryEvent<SoundEvent> register(String id) {
        return SolomonsRod.registry.registerSoundEvent(SolomonsRod.id(id));
    }
}
