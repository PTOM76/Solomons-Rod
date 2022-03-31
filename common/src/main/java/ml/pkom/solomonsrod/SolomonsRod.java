package ml.pkom.solomonsrod;

import com.google.common.base.Suppliers;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.block.BlockProperties;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

public class SolomonsRod {
    public static final String MOD_ID = "solomons_rod";
    // We can use this if we don't want to use DeferredRegister
    //public static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MOD_ID));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_REGISTRY);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MOD_ID, Registry.BLOCK_REGISTRY);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(MOD_ID, Registry.SOUND_EVENT_REGISTRY);

    public static final RegistrySupplier<Block> SOLOMONS_BLOCK = BLOCKS.register("solomon_block", () -> SolomonsBlock.SOLOMONS_BLOCK);
    public static final RegistrySupplier<Block> SOLOMONS_BLOCK_2 = BLOCKS.register("solomon_block2", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(3f, 3f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> SOLOMONS_BLOCK_3 = BLOCKS.register("solomon_block3", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(3f, 3f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Item> SOLOMONS_BLOCK_ITEM = ITEMS.register("solomon_block", () -> new BlockItem(SOLOMONS_BLOCK.get() ,new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistrySupplier<Item> SOLOMONS_BLOCK_2_ITEM = ITEMS.register("solomon_block2", () -> new BlockItem(SOLOMONS_BLOCK_2.get() ,new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> SOLOMONS_BLOCK_3_ITEM = ITEMS.register("solomon_block3", () -> new BlockItem(SOLOMONS_BLOCK_3.get() ,new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistrySupplier<Item> SOLOMONS_WAND = ITEMS.register("solomon_wand", () -> SolomonsWand.SOLOMONS_WAND);
    public static final RegistrySupplier<Item> DEMONS_WAND = ITEMS.register("demons_wand", () -> DemonsWand.DEMONS_WAND);


    public static void init() {
        Sounds.init();

        SOUNDS.register();
        BLOCKS.register();
        ITEMS.register();
        //System.out.println(SolomonsRodExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
