package ml.pkom.solomonsrod;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;

public class SolomonsWand extends Item {
    public static SolomonsWand SOLOMONS_WAND = new SolomonsWand(new Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1));
//    public static SolomonsWand SOLOMONS_WAND = new SolomonsWand(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1));

    public SolomonsWand(Properties properties) {
        super(properties);
    }

    public void deleteBlock(Level world, Player user, BlockPos pos) {
        if (!world.isClientSide()) {
            world.removeBlock(pos, false);
            world.playSound(null, user.getOnPos(), Sounds.ERASE_SOUND.get(), SoundSource.MASTER, 1f, 1f);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos blockPos = new BlockPos(context.getClickedPos());
        if (!context.getLevel().isClientSide()) {
            if (canPlace(world, blockPos)) {
                if (world.getBlockEntity(blockPos) == null) {
                    world.setBlock(blockPos, SolomonsBlock.SOLOMONS_BLOCK.getStateDefinition().getOwner().defaultBlockState(), 3);
                    world.playSound(null, blockPos, Sounds.CREATE_SOUND.get(), SoundSource.MASTER, 1f, 1f);
                    return InteractionResult.SUCCESS;
                }
                world.playSound(null, context.getPlayer().getOnPos(), Sounds.NOCRASH_SOUND.get(), SoundSource.MASTER, 1f, 1f);
                return InteractionResult.SUCCESS;
            }
            return super.useOn(context);
        }
        if (canPlace(world, blockPos)) return InteractionResult.SUCCESS;
        return super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (!world.isClientSide()) {
            double posX = user.getX();
            double posY = user.getY();
            double posZ = user.getZ();
            boolean notChange = false;
            float pitch = user.getRotationVector().x;
            if (pitch <= -25) {
                posY += 2;
                if (pitch <= -60 && pitch >= -90) {
                    notChange = true;
                }
            }

            if (pitch <= 25 && pitch >= -25) {
                posY += 1;
            }

            if (pitch >= 50) {
                posY -= 1;
                if (pitch <= 90 && pitch >= 75) {
                    notChange = true;
                }
            }

            if (!notChange) {
                if (user.getDirection() == Direction.EAST)
                    posX += 1;
                if (user.getDirection() == Direction.WEST)
                    posX -= 1;
                if (user.getDirection() == Direction.NORTH)
                    posZ -= 1;
                if (user.getDirection() == Direction.SOUTH)
                    posZ += 1;
            }

            BlockPos blockPos = new BlockPos(posX, posY, posZ);
            //if (world.canSetBlock(blockPos) && world.getBlockState(blockPos).isAir() && world.getBlockEntity(blockPos) == null) {
            if (canPlace(world, blockPos)) {
                world.setBlock(blockPos, SolomonsBlock.SOLOMONS_BLOCK.getStateDefinition().getOwner().defaultBlockState(), 3);
                world.playSound(null, user.getOnPos(), Sounds.CREATE_SOUND.get(), SoundSource.MASTER, 1f, 1f);
                return InteractionResultHolder.success(user.getItemInHand(hand));
            }
        }
        return super.use(world, user, hand);
    }

    public static boolean canPlace(Level world, BlockPos blockPos) {
        Block block = world.getBlockState(blockPos).getBlock();
        if (block == null) return true;
        if (block instanceof AirBlock) return true;
        if (block instanceof MagmaBlock) return true;
        if (block instanceof WaterlilyBlock) return true;
        if (block instanceof BushBlock) return true;
        return false;
    }
}
