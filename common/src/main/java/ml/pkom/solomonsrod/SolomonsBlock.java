package ml.pkom.solomonsrod;

import ml.pkom.mcpitanlibarch.api.block.ExtendBlock;
import ml.pkom.mcpitanlibarch.api.event.block.BlockScheduledTickEvent;
import ml.pkom.mcpitanlibarch.api.util.BlockUtil;
import ml.pkom.mcpitanlibarch.api.util.math.PosUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SolomonsBlock extends ExtendBlock {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.1D, 0.1D, 0.1D, 15.5D, 16.0D, 15.5D);
    public static final BooleanProperty BROKEN = BooleanProperty.of("broken");
    public static final BooleanProperty COOL_DOWN = BooleanProperty.of("cooldown");

    public static SolomonsBlock SOLOMONS_BLOCK = new SolomonsBlock(BlockUtil.dropsNothing(Settings
            .of(MAtetrial.METAL)
            .strength(-1F, 0F))
    );

    public SolomonsBlock(Settings settings) {
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
    public void scheduledTick(BlockScheduledTickEvent e) {
        e.world.setBlockState(e.pos, e.state.with(COOL_DOWN, false));
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient()) {
            //System.out.println("pos: " + pos + "entityPos: " + entity.getBlockPos());
            if (entity.getBlockPos().equals(pos)) {
                world.playSound(null, entity.getBlockPos(), Sounds.NOCRASH_SOUND.getOrNull(), SoundCategory.MASTER, 1f, 1f);
                world.removeBlock(pos, false);
                return;
            }
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                if (PosUtil.flooredBlockPos(player.getCameraPosVec(1F)).getY() >= pos.getY()) return;
            }
            if (!state.get(COOL_DOWN).booleanValue()) {
                if (state.get(BROKEN).booleanValue()) {
                    world.removeBlock(pos, false);
                } else {
                    //world.getBlockTickScheduler().schedule(pos, SOLOMONS_BLOCK, 5);
                    world.createAndScheduleBlockTick(pos, SOLOMONS_BLOCK, 5);
                    world.setBlockState(pos, state.with(BROKEN, true).with(COOL_DOWN, true));
                }
                world.playSound(null, pos, Sounds.CRASH_SOUND.getOrNull(), SoundCategory.MASTER, 1f, 1f);
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