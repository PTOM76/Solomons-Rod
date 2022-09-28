package ml.pkom.solomonsrod;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import ml.pkom.mcpitanlibarch.api.registry.ArchRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SolomonsRod {
    public static final String MOD_ID = "solomons_rod";

    public static ArchRegistry registry = ArchRegistry.createRegistry(MOD_ID);

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(MOD_ID, Registry.SOUND_EVENT_KEY);

    public static RegistrySupplier<Block> SOLOMONS_BLOCK;
    public static RegistrySupplier<Block> SOLOMONS_BLOCK_2;
    public static RegistrySupplier<Block> SOLOMONS_BLOCK_3;

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    public static void init() {
        Sounds.init();

        SOUNDS.register();
        SOLOMONS_BLOCK = registry.registerBlock(id("solomon_block"), () -> SolomonsBlock.SOLOMONS_BLOCK);
        SOLOMONS_BLOCK_2 = registry.registerBlock(id("solomon_block2"), () -> new Block(AbstractBlock.Settings.of(Material.METAL).strength(3f, 3f).requiresTool()));
        SOLOMONS_BLOCK_3 = registry.registerBlock(id("solomon_block3"), () -> new Block(AbstractBlock.Settings.of(Material.METAL).strength(3f, 3f).requiresTool()));

        registry.registerItem(id("solomon_block"), () -> new BlockItem(SOLOMONS_BLOCK.get() ,new Item.Settings().group(ItemGroup.MISC)));
        registry.registerItem(id("solomon_block2"), () -> new BlockItem(SOLOMONS_BLOCK_2.get() ,new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
        registry.registerItem(id("solomon_block3"), () -> new BlockItem(SOLOMONS_BLOCK_3.get() ,new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

        registry.registerItem(id("solomon_wand"), () -> SolomonsWand.SOLOMONS_WAND);
        registry.registerItem(id("demons_wand"), () -> DemonsWand.DEMONS_WAND);

        //System.out.println(SolomonsRodExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
