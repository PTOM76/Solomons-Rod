package ml.pkom.solomonsrod;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class SolomonsBlock extends Block {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.1D, 0.1D, 0.1D, 15.5D, 16.0D, 15.5D);
    public static final BooleanProperty BROKEN = BooleanProperty.of("broken");
    public static final BooleanProperty COOL_DOWN = BooleanProperty.of("cooldown");

    public static SolomonsBlock SOLOMONS_BLOCK = new SolomonsBlock(FabricBlockSettings
            .of(Material.METAL)
            .strength(-1F, 0F)
            .dropsNothing()
    );

    public SolomonsBlock(FabricBlockSettings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(BROKEN, false).with(COOL_DOWN, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(BROKEN);
        stateManager.add(COOL_DOWN);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, state.with(COOL_DOWN, false));
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient()) {
            System.out.println("pos: " + pos + "entityPos: " + entity.getBlockPos());
            if (entity.getBlockPos().equals(pos)) {
                world.playSound(null, entity.getBlockPos(), Sounds.NOCRASH_SOUND_EVENT, SoundCategory.MASTER, 1f, 1f);
                world.removeBlock(pos, false);
                return;
            }
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                if (new BlockPos(player.getCameraPosVec(1F)).getY() >= pos.getY()) return;
            }
            if (!state.get(COOL_DOWN).booleanValue()) {
                if (state.get(BROKEN).booleanValue()) {
                    world.removeBlock(pos, false);
                } else {
                    world.getBlockTickScheduler().schedule(pos, SOLOMONS_BLOCK, 5);
                    world.setBlockState(pos, state.with(BROKEN, true).with(COOL_DOWN, true));
                }
                world.playSound(null, pos, Sounds.CRASH_SOUND_EVENT, SoundCategory.MASTER, 1f, 1f);
            }
        }
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (world.isClient()) return;
        if (player.getMainHandStack() != null)
            if (player.getMainHandStack().getItem() instanceof SolomonsWand || player.getMainHandStack().getItem() instanceof DemonsWand) {
                SolomonsWand wand = (SolomonsWand) player.getMainHandStack().getItem();
                wand.deleteBlock(world, player, pos);
            }
        super.onBlockBreakStart(state, world, pos, player);
    }
}
