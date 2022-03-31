package ml.pkom.solomonsrod;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class SolomonsBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(0.1D, 0.1D, 0.1D, 15.5D, 16.0D, 15.5D);
    public static final BooleanProperty BROKEN = BooleanProperty.create("broken");
    public static final BooleanProperty COOL_DOWN = BooleanProperty.create("cooldown");

    public static SolomonsBlock SOLOMONS_BLOCK = new SolomonsBlock(Properties
            .of(Material.METAL)
            .strength(-1.0F, 3600000.0F)
            .noDrops()
    );

    public SolomonsBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().getOwner().defaultBlockState().setValue(BROKEN, false).setValue(COOL_DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(BROKEN);
        stateManager.add(COOL_DOWN);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.block();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        super.tick(state, world, pos, random);
        world.setBlock(pos, state.setValue(COOL_DOWN, false), 3);

    }



    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float f) {
        if (!world.isClientSide()) {
            System.out.println("pos: " + pos + "entityPos: " + entity.getOnPos());
            if (entity.getOnPos().equals(pos)) {
                world.playSound(null, entity.getOnPos(), Sounds.NOCRASH_SOUND.get(), SoundSource.MASTER, 1f, 1f);
                world.removeBlock(pos, false);
                return;
            }
            if (entity instanceof Player) {
                Player player = (Player) entity;
                //getCameraPosVec
                if (new BlockPos(player.getEyePosition(1F)).getY() >= pos.getY()) return;
            }
            if (!state.getValue(COOL_DOWN).booleanValue()) {
                if (state.getValue(BROKEN).booleanValue()) {
                    world.removeBlock(pos, false);
                } else {
                    world.scheduleTick(pos, SOLOMONS_BLOCK, 5);
                    world.setBlock(pos, state.setValue(BROKEN, true).setValue(COOL_DOWN, true), 3);
                }
                world.playSound(null, pos, Sounds.CRASH_SOUND.get(), SoundSource.MASTER, 1f, 1f);
            }
        }
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (world.isClientSide()) return;
        if (player.getMainHandItem() != null)
            if (player.getMainHandItem().getItem() instanceof SolomonsWand || player.getMainHandItem().getItem() instanceof DemonsWand) {
                SolomonsWand wand = (SolomonsWand) player.getMainHandItem().getItem();
                wand.deleteBlock(world, player, pos);
            }
        super.playerWillDestroy(world, pos, state, player);
    }
}
