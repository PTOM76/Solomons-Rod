package ml.pkom.solomonsrod;

import ml.pkom.mcpitanlibarch.api.event.item.ItemUseEvent;
import ml.pkom.mcpitanlibarch.api.event.item.ItemUseOnBlockEvent;
import ml.pkom.mcpitanlibarch.api.item.DefaultItemGroups;
import ml.pkom.mcpitanlibarch.api.item.ExtendItem;
import ml.pkom.mcpitanlibarch.api.item.ExtendSettings;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class SolomonsWand extends ExtendItem {
    public static SolomonsWand SOLOMONS_WAND = new SolomonsWand(new ExtendSettings().addGroup(DefaultItemGroups.TOOLS, SolomonsRod.id("solomon_wand")).maxCount(1));

    public SolomonsWand(Settings settings) {
        super(settings);
    }

    public void deleteBlock(World world, PlayerEntity user, BlockPos pos) {
        if (!world.isClient()) {
            world.removeBlock(pos, false);
            world.playSound(null, user.getBlockPos(), Sounds.ERASE_SOUND.getOrNull(), SoundCategory.MASTER, 1f, 1f);
        }
    }

    @Override
    public ActionResult onRightClickOnBlock(ItemUseOnBlockEvent e) {
        World world = e.world;
        BlockPos blockPos = new BlockPos(e.hit.getPos());
        if (!e.world.isClient()) {
            if (world.canSetBlock(blockPos) && canPlace(world.getBlockState(blockPos).getBlock())) {
                if (world.getBlockEntity(blockPos) == null) {
                    world.setBlockState(blockPos, SolomonsBlock.SOLOMONS_BLOCK.getDefaultState());
                    world.playSound(null, blockPos, Sounds.CREATE_SOUND.getOrNull(), SoundCategory.MASTER, 1f, 1f);
                    return ActionResult.SUCCESS;
                }
                world.playSound(null, e.player.getPlayerEntity().getBlockPos(), Sounds.NOCRASH_SOUND.getOrNull(), SoundCategory.MASTER, 1f, 1f);
                return ActionResult.SUCCESS;
            }
            return super.onRightClickOnBlock(e);
        }
        if (world.canSetBlock(blockPos) && canPlace(world.getBlockState(blockPos).getBlock())) return ActionResult.SUCCESS;
        return super.onRightClickOnBlock(e);
    }

    @Override
    public TypedActionResult<ItemStack> onRightClick(ItemUseEvent e) {
        World world = e.world;
        if (!world.isClient()) {
            PlayerEntity user = e.user.getPlayerEntity();
            double posX = user.getX();
            double posY = user.getY();
            double posZ = user.getZ();
            boolean notChange = false;
            if (user.getPitch(1F) <= -25) {
                posY += 2;
                if (user.getPitch(1F) <= -60 && user.getPitch(1F) >= -90) {
                    notChange = true;
                }
            }

            if (user.getPitch(1F) <= 25 && user.getPitch(1F) >= -25) {
                posY += 1;
            }

            if (user.getPitch(1F) >= 50) {
                posY -= 1;
                if (user.getPitch(1F) <= 90 && user.getPitch(1F) >= 75) {
                    notChange = true;
                }
            }

            if (!notChange) {
                if (user.getHorizontalFacing() == Direction.EAST)
                    posX += 1;
                if (user.getHorizontalFacing() == Direction.WEST)
                    posX -= 1;
                if (user.getHorizontalFacing() == Direction.NORTH)
                    posZ -= 1;
                if (user.getHorizontalFacing() == Direction.SOUTH)
                    posZ += 1;
            }

            BlockPos blockPos = new BlockPos(posX, posY, posZ);
            //if (world.canSetBlock(blockPos) && world.getBlockState(blockPos).isAir() && world.getBlockEntity(blockPos) == null) {
            if (world.canSetBlock(blockPos) && canPlace(world.getBlockState(blockPos).getBlock()) && world.getBlockEntity(blockPos) == null) {
                world.setBlockState(blockPos, SolomonsBlock.SOLOMONS_BLOCK.getDefaultState());
                world.playSound(null, user.getBlockPos(), Sounds.CREATE_SOUND.getOrNull(), SoundCategory.MASTER, 1f, 1f);
                return TypedActionResult.success(user.getStackInHand(e.hand));
            }
        }
        return super.onRightClick(e);
    }

    public static boolean canPlace(Block block) {
        if (block == null) return true;
        if (block instanceof AirBlock) return true;
        if (block instanceof FluidBlock) return true;
        if (block instanceof FernBlock) return true;
        if (block instanceof DeadBushBlock) return true;
        return false;
    }
}