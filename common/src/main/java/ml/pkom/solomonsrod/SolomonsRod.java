package ml.pkom.solomonsrod;

import ml.pkom.mcpitanlibarch.api.event.registry.RegistryEvent;
import ml.pkom.mcpitanlibarch.api.item.DefaultItemGroups;
import ml.pkom.mcpitanlibarch.api.item.ExtendSettings;
import ml.pkom.mcpitanlibarch.api.registry.ArchRegistry;
import ml.pkom.mcpitanlibarch.api.util.BlockUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
public class SolomonsRod {
    public static final String MOD_ID = "solomons_rod";

    public static ArchRegistry registry = ArchRegistry.createRegistry(MOD_ID);

    public static RegistryEvent<Block> SOLOMONS_BLOCK;
    public static RegistryEvent<Block> SOLOMONS_BLOCK_2;
    public static RegistryEvent<Block> SOLOMONS_BLOCK_3;

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    public static void init() {
        Sounds.init();

        SOLOMONS_BLOCK = registry.registerBlock(id("solomon_block"), () -> SolomonsBlock.SOLOMONS_BLOCK);
        SOLOMONS_BLOCK_2 = registry.registerBlock(id("solomon_block2"), () -> new Block(BlockUtil.requiresTool(AbstractBlock.Settings.of(Material.METAL).strength(3f, 3f))));
        SOLOMONS_BLOCK_3 = registry.registerBlock(id("solomon_block3"), () -> new Block(BlockUtil.requiresTool(AbstractBlock.Settings.of(Material.METAL).strength(3f, 3f))));

        registry.registerItem(id("solomon_block"), () -> new BlockItem(SOLOMONS_BLOCK.get(), new ExtendSettings().addGroup(DefaultItemGroups.MISC, id("solomon_block"))));
        registry.registerItem(id("solomon_block2"), () -> new BlockItem(SOLOMONS_BLOCK_2.get(), new ExtendSettings().addGroup(DefaultItemGroups.BUILDING_BLOCKS, id("solomon_block2"))));
        registry.registerItem(id("solomon_block3"), () -> new BlockItem(SOLOMONS_BLOCK_3.get(), new ExtendSettings().addGroup(DefaultItemGroups.BUILDING_BLOCKS, id("solomon_block3"))));

        registry.registerItem(id("solomon_wand"), () -> SolomonsWand.SOLOMONS_WAND);
        registry.registerItem(id("demons_wand"), () -> DemonsWand.DEMONS_WAND);

        registry.allRegister();
        //System.out.println(SolomonsRodExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
