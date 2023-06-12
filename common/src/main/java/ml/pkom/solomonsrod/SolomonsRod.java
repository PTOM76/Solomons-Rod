package ml.pkom.solomonsrod;

import ml.pkom.mcpitanlibarch.api.block.CompatibleBlockSettings;
import ml.pkom.mcpitanlibarch.api.block.CompatibleMaterial;
import ml.pkom.mcpitanlibarch.api.event.registry.RegistryEvent;
import ml.pkom.mcpitanlibarch.api.item.CompatibleItemSettings;
import ml.pkom.mcpitanlibarch.api.item.DefaultItemGroups;
import ml.pkom.mcpitanlibarch.api.registry.ArchRegistry;
import ml.pkom.mcpitanlibarch.api.util.BlockUtil;
import ml.pkom.mcpitanlibarch.api.util.ItemUtil;
import net.minecraft.block.Block;
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
        SOLOMONS_BLOCK_2 = registry.registerBlock(id("solomon_block2"), () -> BlockUtil.of(CompatibleBlockSettings.of(CompatibleMaterial.METAL).strength(3f, 3f).requiresTool()));
        SOLOMONS_BLOCK_3 = registry.registerBlock(id("solomon_block3"), () -> BlockUtil.of(CompatibleBlockSettings.of(CompatibleMaterial.METAL).strength(3f, 3f).requiresTool()));

        registry.registerItem(id("solomon_block"), () -> ItemUtil.ofBlock(SOLOMONS_BLOCK.get(), new CompatibleItemSettings().addGroup(DefaultItemGroups.MISC, id("solomon_block"))));
        registry.registerItem(id("solomon_block2"), () -> ItemUtil.ofBlock(SOLOMONS_BLOCK_2.get(), new CompatibleItemSettings().addGroup(DefaultItemGroups.BUILDING_BLOCKS, id("solomon_block2"))));
        registry.registerItem(id("solomon_block3"), () -> ItemUtil.ofBlock(SOLOMONS_BLOCK_3.get(), new CompatibleItemSettings().addGroup(DefaultItemGroups.BUILDING_BLOCKS, id("solomon_block3"))));

        registry.registerItem(id("solomon_wand"), () -> SolomonsWand.SOLOMONS_WAND);
        registry.registerItem(id("demons_wand"), () -> DemonsWand.DEMONS_WAND);

        registry.allRegister();
        //System.out.println(SolomonsRodExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
