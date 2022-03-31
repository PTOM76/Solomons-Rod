package ml.pkom.solomonsrod.fabric;

import ml.pkom.solomonsrod.SolomonsRodExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class SolomonsRodExpectPlatformImpl {
    /**
     * This is our actual method to {@link SolomonsRodExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
