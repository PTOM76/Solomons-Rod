package ml.pkom.solomonsrod.forge;

import ml.pkom.solomonsrod.SolomonsRodExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class SolomonsRodExpectPlatformImpl {
    /**
     * This is our actual method to {@link SolomonsRodExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
