package net.pitan76.solomonsrod.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.pitan76.solomonsrod.SolomonsRod;
import net.pitan76.solomonsrod.SolomonsRodClient;

import java.util.Objects;

@Mod(SolomonsRod.MOD_ID)
public class SolomonsRodNeoForge {
    public SolomonsRodNeoForge(ModContainer modContainer) {
        IEventBus bus = modContainer.getEventBus();
        Objects.requireNonNull(bus).addListener(SolomonsRodNeoForge::onSetupClient);
        new SolomonsRod();
    }

    public static void onSetupClient(FMLClientSetupEvent event) {
        SolomonsRodClient.init();
    }
}
