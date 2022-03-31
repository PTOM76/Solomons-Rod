package ml.pkom.solomonsrod;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class Sounds {

    public static final RegistrySupplier<SoundEvent> CREATE_SOUND = register("create");
    public static final RegistrySupplier<SoundEvent> CRASH_SOUND = register("crash");
    public static final RegistrySupplier<SoundEvent> ERASE_SOUND = register("erase");
    public static final RegistrySupplier<SoundEvent> BAM_SOUND = register("bam");
    public static final RegistrySupplier<SoundEvent> NOCRASH_SOUND = register("nocrash");
    public static void init(){

    }

    private static RegistrySupplier<SoundEvent> register(String id) {
        return SolomonsRod.SOUNDS.register(id, () -> new SoundEvent(new ResourceLocation(SolomonsRod.MOD_ID, id)));
    }
}
