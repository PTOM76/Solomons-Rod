package net.pitan76.solomonsrod;

import net.pitan76.mcpitanlib.api.CommonModInitializer;
import net.pitan76.mcpitanlib.api.block.v2.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.CompatibleMaterial;
import net.pitan76.mcpitanlib.api.item.v2.ItemSettingsBuilder;
import net.pitan76.mcpitanlib.api.util.block.BlockUtil;
import net.pitan76.mcpitanlib.api.util.CompatIdentifier;
import net.pitan76.mcpitanlib.midohra.block.BlockWrapper;
import net.pitan76.mcpitanlib.midohra.item.ItemGroups;
import net.pitan76.mcpitanlib.midohra.registry.MidohraRegistry;

public class SolomonsRod extends CommonModInitializer {
    public static final String MOD_ID = "solomons_rod";
    public static final String MOD_NAME = "Solomons Rod";

    public static SolomonsRod INSTANCE;

    public static BlockWrapper SOLOMONS_BLOCK;
    public static BlockWrapper SOLOMONS_BLOCK_2;
    public static BlockWrapper SOLOMONS_BLOCK_3;

    public void init() {
        INSTANCE = this;
        MidohraRegistry registry = MidohraRegistry.of(super.registry);

        Config.init();
        Sounds.init();

        SOLOMONS_BLOCK = registry.registerRawBlock(_id("solomon_block"), () -> SolomonsBlock.SOLOMONS_BLOCK);
        SOLOMONS_BLOCK_2 = registry.registerRawBlock(_id("solomon_block2"), () -> BlockUtil.create(CompatibleBlockSettings.of(_id("solomon_block2"), CompatibleMaterial.METAL).strength(3f, 3f).requiresTool()));
        SOLOMONS_BLOCK_3 = registry.registerRawBlock(_id("solomon_block3"), () -> BlockUtil.create(CompatibleBlockSettings.of(_id("solomon_block3"), CompatibleMaterial.METAL).strength(3f, 3f).requiresTool()));

        registry.registerBlockItem(_id("solomon_block"), SOLOMONS_BLOCK, ItemSettingsBuilder.of().addGroup(ItemGroups.BUILDING_BLOCKS));
        registry.registerBlockItem(_id("solomon_block2"), SOLOMONS_BLOCK_2, ItemSettingsBuilder.of().addGroup(ItemGroups.BUILDING_BLOCKS));
        registry.registerBlockItem(_id("solomon_block3"), SOLOMONS_BLOCK_3, ItemSettingsBuilder.of().addGroup(ItemGroups.BUILDING_BLOCKS));

        registry.registerRawItem(_id("solomon_wand"), () -> SolomonsWand.SOLOMONS_WAND);
        registry.registerRawItem(_id("demons_wand"), () -> DemonsWand.DEMONS_WAND);
    }
    
    public static CompatIdentifier _id(String path) {
        return new CompatIdentifier(MOD_ID, path);
    }

    @Override
    public String getId() {
        return MOD_ID;
    }

    @Override
    public String getName() {
        return MOD_NAME;
    }
}
