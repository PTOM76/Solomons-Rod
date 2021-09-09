package ml.pkom.solomonsrod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SolomonsRod implements ModInitializer {

    public static final String MOD_ID = "solomons_rod";

    Block SOLOMONS_BLOCK_2 = new Block(FabricBlockSettings.of(Material.METAL).strength(3f, 3f).breakByHand(false).requiresTool().breakByTool(FabricToolTags.PICKAXES, 0));
    Block SOLOMONS_BLOCK_3 = new Block(FabricBlockSettings.of(Material.METAL).strength(3f, 3f).breakByHand(false).requiresTool().breakByTool(FabricToolTags.PICKAXES, 0));

    @Override
    public void onInitialize() {
        Sounds.init();

        Registry.register(Registry.BLOCK, id("solomon_block"), SolomonsBlock.SOLOMONS_BLOCK);
        Registry.register(Registry.ITEM, id("solomon_block"), new BlockItem(SolomonsBlock.SOLOMONS_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
        Registry.register(Registry.ITEM, id("solomon_wand"), SolomonsWand.SOLOMONS_WAND);
        Registry.register(Registry.ITEM, id("demons_wand"), DemonsWand.DEMONS_WAND);

        Registry.register(Registry.BLOCK, id("solomon_block2"), SOLOMONS_BLOCK_2);
        Registry.register(Registry.ITEM, id("solomon_block2"), new BlockItem(SOLOMONS_BLOCK_2, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));

        Registry.register(Registry.BLOCK, id("solomon_block3"), SOLOMONS_BLOCK_3);
        Registry.register(Registry.ITEM, id("solomon_block3"), new BlockItem(SOLOMONS_BLOCK_3, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
    }

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }
}
