package net.pitan76.solomonsrod;

import net.minecraft.util.shape.VoxelShape;
import net.pitan76.mcpitanlib.api.block.args.v2.CollisionShapeEvent;
import net.pitan76.mcpitanlib.api.block.args.v2.OutlineShapeEvent;
import net.pitan76.mcpitanlib.api.block.v2.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.CompatibleMaterial;
import net.pitan76.mcpitanlib.api.block.v2.CompatBlock;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.event.block.AppendPropertiesArgs;
import net.pitan76.mcpitanlib.api.event.block.BlockBreakStartEvent;
import net.pitan76.mcpitanlib.api.event.block.BlockScheduledTickEvent;
import net.pitan76.mcpitanlib.api.event.block.EntityCollisionEvent;
import net.pitan76.mcpitanlib.api.sound.CompatSoundCategory;
import net.pitan76.mcpitanlib.api.state.property.BooleanProperty;
import net.pitan76.mcpitanlib.api.util.*;
import net.pitan76.mcpitanlib.core.serialization.CompatMapCodec;
import net.pitan76.mcpitanlib.midohra.block.BlockState;
import net.pitan76.mcpitanlib.midohra.entity.EntityWrapper;
import net.pitan76.mcpitanlib.midohra.item.ItemStack;
import net.pitan76.mcpitanlib.midohra.item.ItemWrapper;
import net.pitan76.mcpitanlib.midohra.util.math.BlockPos;
import net.pitan76.mcpitanlib.midohra.util.math.Vector3d;
import net.pitan76.mcpitanlib.midohra.world.World;

import java.util.Optional;

public class SolomonsBlock extends CompatBlock {

    public static final CompatMapCodec<? extends SolomonsBlock> CODEC = CompatMapCodec.createCodecOfCompatBlock(SolomonsBlock::new);

    @Override
    public CompatMapCodec<? extends SolomonsBlock> getCompatCodec() {
        return CODEC;
    }

    protected static final VoxelShape SHAPE = VoxelShapeUtil.blockCuboid(0.1D, 0.1D, 0.1D, 15.5D, 16.0D, 15.5D);
    public static final BooleanProperty BROKEN = BooleanProperty.of("broken");
    public static final BooleanProperty COOL_DOWN = BooleanProperty.of("cooldown");

    public static CompatibleBlockSettings settings = CompatibleBlockSettings
            .of(SolomonsRod._id("solomon_block"), CompatibleMaterial.METAL)
            .strength(-1F, 0F)
            .dropsNothing();

    public static SolomonsBlock SOLOMONS_BLOCK = new SolomonsBlock(settings);

    public SolomonsBlock(CompatibleBlockSettings settings) {
        super(settings);
        setDefaultState(getDefaultMidohraState().with(BROKEN, false).with(COOL_DOWN, false));
    }

    @Override
    public void appendProperties(AppendPropertiesArgs args) {
        super.appendProperties(args);
        args.addProperty(BROKEN);
        args.addProperty(COOL_DOWN);
    }

    @Override
    public VoxelShape getOutlineShape(OutlineShapeEvent e) {
        return VoxelShapeUtil.fullCube();
    }

    @Override
    public VoxelShape getCollisionShape(CollisionShapeEvent e) {
        return SHAPE;
    }

    @Override
    public void scheduledTick(BlockScheduledTickEvent e) {
        World world = World.of(e.world);
        BlockPos pos = BlockPos.of(e.pos);
        BlockState state = BlockState.of(e.state);

        world.setBlockState(pos, state.with(COOL_DOWN, false));
    }

    @Override
    public void onEntityCollision(EntityCollisionEvent e) {
        if (e.isClient()) return;

        World world = World.of(e.getWorld());
        BlockPos pos = BlockPos.of(e.getBlockPos());
        BlockState state = BlockState.of(e.getState());

        BlockPos entityPos = BlockPos.of(e.getEntityPos());

        //System.out.println("pos: " + pos + "entityPos: " + entity.getBlockPos());
        if (entityPos.equals(pos)) {
            world.playSound(null, entityPos, Sounds.NOCRASH_SOUND, CompatSoundCategory.MASTER, 1f, 1f);
            world.removeBlock(pos, false);
            return;
        }

        Optional<Player> optPlayer = EntityWrapper.of(e.getEntity()).toPlayer();

        if (optPlayer.isPresent()) {
            Player player = optPlayer.get();
            BlockPos cameraPos = Vector3d.of(player.getEntity().getCameraPosVec(1F)).toInt().toPos();

            if (cameraPos.getY() >= pos.getY()) return;
        }

        if (!state.get(COOL_DOWN)) {
            if (state.get(BROKEN)) {
                world.removeBlock(pos, false);
            } else {
                WorldUtil.scheduleBlockTick(world.toMinecraft(), pos.toMinecraft(), SOLOMONS_BLOCK, 5);
                world.setBlockState(pos, state.with(BROKEN, true).with(COOL_DOWN, true));
            }
            world.playSound(null, pos, Sounds.CRASH_SOUND, CompatSoundCategory.MASTER, 1f, 1f);
        }
    }

    @Override
    public void onBlockBreakStart(BlockBreakStartEvent e) {
        if (e.isClient()) {
            super.onBlockBreakStart(e);
            return;
        }

        Player player = e.player;
        if (player.getMainHandStack() == null) {
            super.onBlockBreakStart(e);
            return;
        }

        ItemStack stack = ItemStack.of(player.getMainHandStack());
        ItemWrapper mainHandItem = stack.getItem();
        if (mainHandItem.instanceOf(SolomonsWand.class) || mainHandItem.instanceOf(DemonsWand.class)) {
            SolomonsWand wand = mainHandItem.getCompatItem(SolomonsWand.class);
            wand.deleteBlock(World.of(e.getWorld()), player, BlockPos.of(e.getPos()));
        }

        super.onBlockBreakStart(e);
    }
}