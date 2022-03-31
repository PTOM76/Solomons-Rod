package ml.pkom.solomonsrod.fabric;

import ml.pkom.solomonsrod.SolomonsRod;
import net.fabricmc.api.ModInitializer;

public class SolomonsRodFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SolomonsRod.init();
    }
}
