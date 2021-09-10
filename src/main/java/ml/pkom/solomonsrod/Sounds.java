package ml.pkom.solomonsrod;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Sounds {
    public static final Identifier CREATE_SOUND = SolomonsRod.id("create");
    public static SoundEvent CREATE_SOUND_EVENT = new SoundEvent(CREATE_SOUND);

    public static final Identifier CRASH_SOUND = SolomonsRod.id("crash");
    public static SoundEvent CRASH_SOUND_EVENT = new SoundEvent(CRASH_SOUND);

    public static final Identifier ERASE_SOUND = SolomonsRod.id("erase");
    public static SoundEvent ERASE_SOUND_EVENT = new SoundEvent(ERASE_SOUND);

    public static final Identifier BAM_SOUND = SolomonsRod.id("bam");
    public static SoundEvent BAM_SOUND_EVENT = new SoundEvent(BAM_SOUND);

    public static final Identifier NOCRASH_SOUND = SolomonsRod.id("nocrash");
    public static SoundEvent NOCRASH_SOUND_EVENT = new SoundEvent(NOCRASH_SOUND);

    public static void init(){
        Registry.register(Registry.SOUND_EVENT, CREATE_SOUND, CREATE_SOUND_EVENT);
        Registry.register(Registry.SOUND_EVENT, CRASH_SOUND, CRASH_SOUND_EVENT);
        Registry.register(Registry.SOUND_EVENT, ERASE_SOUND, ERASE_SOUND_EVENT);
        Registry.register(Registry.SOUND_EVENT, BAM_SOUND, BAM_SOUND_EVENT);
        Registry.register(Registry.SOUND_EVENT, NOCRASH_SOUND, NOCRASH_SOUND_EVENT);
    }
}
