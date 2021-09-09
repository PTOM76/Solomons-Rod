package ml.pkom.solomonsrod;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class SolomonsWand extends Item {
    public static SolomonsWand SOLOMONS_WAND = new SolomonsWand(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1));

    public SolomonsWand(FabricItemSettings settings) {
        super(settings);
    }

    public void deleteBlock(World world, PlayerEntity user, BlockPos pos) {
        if (!world.isClient()) {
            world.removeBlock(pos, false);
            world.playSound(null, user.getBlockPos(), Sounds.ERASE_SOUND_EVENT, SoundCategory.MASTER, 1f, 1f);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
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
                world.playSound(null, user.getBlockPos(), Sounds.CREATE_SOUND_EVENT, SoundCategory.MASTER, 1f, 1f);
                return TypedActionResult.success(user.getStackInHand(hand));
            }
        }
        return super.use(world, user, hand);
    }

    public static boolean canPlace(Block block) {
        //payer.sendMessage(new LiteralText(block.getClass().toString()), false);
        if (block == null) return true;
        if (block instanceof AirBlock) return true;
        if (block instanceof FluidBlock) return true;
        if (block instanceof FernBlock) return true;
        if (block instanceof DeadBushBlock) return true;
        return false;
    }

    /*
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient()) {
            World world = context.getWorld();
            BlockPos blockPos = new BlockPos(context.getHitPos());

            if (world.canSetBlock(blockPos) && world.getBlockState(blockPos).isAir() && world.getBlockEntity(blockPos) == null) {
                world.setBlockState(blockPos, SolomonsBlock.SOLOMONS_BLOCK.getDefaultState());
                world.playSound(null, blockPos, Sounds.CREATE_SOUND_EVENT, SoundCategory.MASTER, 1f, 1f);
                return ActionResult.SUCCESS;
            }
        }
        return super.useOnBlock(context);
    }]
     */
}
