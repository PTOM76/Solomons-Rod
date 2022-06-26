package ml.pkom.solomonsrod;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
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
    // We can use this if we don't want to use DeferredRegister
    //public static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MOD_ID));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_KEY);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registry.BLOCK_KEY);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(MOD_ID, Registry.SOUND_EVENT_KEY);

    public static final RegistrySupplier<Block> SOLOMONS_BLOCK = BLOCKS.register("solomon_block", () -> SolomonsBlock.SOLOMONS_BLOCK);
    public static final RegistrySupplier<Block> SOLOMONS_BLOCK_2 = BLOCKS.register("solomon_block2", () -> new Block(AbstractBlock.Settings.of(Material.METAL).strength(3f, 3f).requiresTool()));
    public static final RegistrySupplier<Block> SOLOMONS_BLOCK_3 = BLOCKS.register("solomon_block3", () -> new Block(AbstractBlock.Settings.of(Material.METAL).strength(3f, 3f).requiresTool()));

    public static final RegistrySupplier<Item> SOLOMONS_BLOCK_ITEM = ITEMS.register("solomon_block", () -> new BlockItem(SOLOMONS_BLOCK.get() ,new Item.Settings().group(ItemGroup.MISC)));
    public static final RegistrySupplier<Item> SOLOMONS_BLOCK_2_ITEM = ITEMS.register("solomon_block2", () -> new BlockItem(SOLOMONS_BLOCK_2.get() ,new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> SOLOMONS_BLOCK_3_ITEM = ITEMS.register("solomon_block3", () -> new BlockItem(SOLOMONS_BLOCK_3.get() ,new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

    public static final RegistrySupplier<Item> SOLOMONS_WAND = ITEMS.register("solomon_wand", () -> SolomonsWand.SOLOMONS_WAND);
    public static final RegistrySupplier<Item> DEMONS_WAND = ITEMS.register("demons_wand", () -> DemonsWand.DEMONS_WAND);

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    public static void init() {
        Sounds.init();

        SOUNDS.register();
        BLOCKS.register();
        ITEMS.register();
        //System.out.println(SolomonsRodExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
