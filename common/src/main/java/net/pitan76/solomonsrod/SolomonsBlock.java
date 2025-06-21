package net.pitan76.solomonsrod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.pitan76.mcpitanlib.api.block.args.v2.CollisionShapeEvent;
import net.pitan76.mcpitanlib.api.block.args.v2.OutlineShapeEvent;
import net.pitan76.mcpitanlib.api.block.v2.BlockSettingsBuilder;
import net.pitan76.mcpitanlib.api.block.v2.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.CompatibleMaterial;
import net.pitan76.mcpitanlib.api.block.v2.CompatBlock;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.event.block.AppendPropertiesArgs;
import net.pitan76.mcpitanlib.api.event.block.BlockBreakStartEvent;
import net.pitan76.mcpitanlib.api.event.block.BlockScheduledTickEvent;
import net.pitan76.mcpitanlib.api.event.block.EntityCollisionEvent;
import net.pitan76.mcpitanlib.api.sound.CompatSoundCategory;
import net.pitan76.mcpitanlib.api.util.*;
import net.pitan76.mcpitanlib.api.util.math.PosUtil;
import net.pitan76.mcpitanlib.core.serialization.CompatMapCodec;
import net.pitan76.mcpitanlib.midohra.block.BlockState;

public class SolomonsBlock extends CompatBlock {

    public static final CompatMapCodec<? extends SolomonsBlock> CODEC = CompatMapCodec.createCodecOfCompatBlock(SolomonsBlock::new);

    @Override
    public CompatMapCodec<? extends SolomonsBlock> getCompatCodec() {
        return CODEC;
    }

    protected static final VoxelShape SHAPE = VoxelShapeUtil.blockCuboid(0.1D, 0.1D, 0.1D, 15.5D, 16.0D, 15.5D);
    public static final BooleanProperty BROKEN = PropertyUtil.createBooleanProperty("broken");
    public static final BooleanProperty COOL_DOWN = PropertyUtil.createBooleanProperty("cooldown");

    public static BlockSettingsBuilder settingsBuilder = BlockSettingsBuilder
            .of(SolomonsRod._id("solomon_block"))
            .material(CompatibleMaterial.METAL)
            .strength(-1F, 0F)
            .dropsNothing();

    public static SolomonsBlock SOLOMONS_BLOCK = new SolomonsBlock(settingsBuilder.build());

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
        WorldUtil.setBlockState(e.world, e.pos, BlockStateUtil.with(e.state, COOL_DOWN, false));
    }

    @Override
    public void onEntityCollision(EntityCollisionEvent e) {
        if (e.isClient()) return;

        World world = e.getWorld();
        BlockPos pos = e.getBlockPos();
        BlockState state = BlockState.of(e.getState());

        //System.out.println("pos: " + pos + "entityPos: " + entity.getBlockPos());
        if (e.getEntityPos().equals(pos)) {
            WorldUtil.playSound(e.getWorld(), null, e.getEntityPos(), Sounds.NOCRASH_SOUND, CompatSoundCategory.MASTER, 1f, 1f);
            WorldUtil.removeBlock(e.getWorld(), pos, false);
            return;
        }

        if (e.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) e.getEntity();
            BlockPos cameraPos = PosUtil.flooredBlockPos(player.getCameraPosVec(1F));

            if (PosUtil.y(cameraPos) >= PosUtil.y(pos)) return;
        }

        if (!state.get(COOL_DOWN)) {
            if (state.get(BROKEN)) {
                WorldUtil.removeBlock(world, pos, false);
            } else {
                WorldUtil.scheduleBlockTick(world, pos, SOLOMONS_BLOCK, 5);
                WorldUtil.setBlockState(world, pos, state.with(BROKEN, true).with(COOL_DOWN, true));
            }
            WorldUtil.playSound(world, null, pos, Sounds.CRASH_SOUND, CompatSoundCategory.MASTER, 1f, 1f);
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

        Item mainHandItem = ItemStackUtil.getItem(player.getMainHandStack());
        if (mainHandItem instanceof SolomonsWand || mainHandItem instanceof DemonsWand) {
            SolomonsWand wand = (SolomonsWand) mainHandItem;
            wand.deleteBlock(e.getWorld(), player, e.getPos());
        }

        super.onBlockBreakStart(e);
    }
}