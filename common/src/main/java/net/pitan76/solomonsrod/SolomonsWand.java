package net.pitan76.solomonsrod;

import net.minecraft.block.AirBlock;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.ShortPlantBlock;
import net.minecraft.util.Hand;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.event.item.EnchantableArgs;
import net.pitan76.mcpitanlib.api.event.item.ItemUseEvent;
import net.pitan76.mcpitanlib.api.event.item.ItemUseOnBlockEvent;
import net.pitan76.mcpitanlib.api.item.v2.CompatItem;
import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.sound.CompatSoundCategory;
import net.pitan76.mcpitanlib.api.util.*;
import net.pitan76.mcpitanlib.midohra.block.BlockState;
import net.pitan76.mcpitanlib.midohra.block.BlockWrapper;
import net.pitan76.mcpitanlib.midohra.block.MCBlocks;
import net.pitan76.mcpitanlib.midohra.item.ItemGroups;
import net.pitan76.mcpitanlib.midohra.item.ItemStack;
import net.pitan76.mcpitanlib.midohra.util.math.BlockPos;
import net.pitan76.mcpitanlib.midohra.util.math.Direction;
import net.pitan76.mcpitanlib.midohra.util.math.Vector3d;
import net.pitan76.mcpitanlib.midohra.world.World;

import static net.pitan76.solomonsrod.SolomonsRod._id;

public class SolomonsWand extends CompatItem {
    public static SolomonsWand SOLOMONS_WAND = of(_id("solomon_wand"));

    public SolomonsWand(CompatibleItemSettings settings) {
        super(settings);
    }

    public static SolomonsWand of(CompatIdentifier id) {
        CompatibleItemSettings settings = CompatibleItemSettings.of(id).addGroup(ItemGroups.TOOLS).enchantable(15);
        if (!Config.infiniteDurability) settings.maxDamage(Config.maxDamage);
        else settings.maxCount(1);

        return new SolomonsWand(settings);
    }

    public void deleteBlock(World world, Player user, BlockPos pos) {
        world.removeBlock(pos, false);
        world.playSound(null, user.getBlockPosM(), Sounds.ERASE_SOUND, CompatSoundCategory.MASTER, 1f, 1f);
    }

    @Override
    public CompatActionResult onRightClickOnBlock(ItemUseOnBlockEvent e) {
        World world = e.getMidohraWorld();
        BlockPos pos = e.getMidohraPos();
        BlockState state = e.getMidohraState();

        // 耐久値が0の場合はそのまま終了
        if (!Config.infiniteDurability && ItemStackUtil.isBreak(e.stack))
            return super.onRightClickOnBlock(e);

        if (e.isClient()) {
            if (WorldUtil.canSetBlock(world.toMinecraft(), pos.toMinecraft()) &&
                    canPlace(state.getBlock()))
                return e.success();

            return super.onRightClickOnBlock(e);
        }

        // ブロックを設置できない場合はそのまま終了
        if (!WorldUtil.canSetBlock(world.toMinecraft(), pos.toMinecraft()) ||
                !canPlace(state.getBlock()))
            return super.onRightClickOnBlock(e);

        // ブロックエンティティが存在する場合はそのまま音を鳴らして終了
        if (e.hasBlockEntity()) {
            world.playSound(null, e.player.getBlockPosM(), Sounds.NOCRASH_SOUND, CompatSoundCategory.MASTER, 1f, 1f);
            return e.success();
        }

        world.setBlockState(pos, SolomonsBlock.SOLOMONS_BLOCK.getDefaultMidohraState());
        world.playSound(null, pos, Sounds.CREATE_SOUND, CompatSoundCategory.MASTER, 1f, 1f);

        damageStackIfDamageable(e.player.getMidohraStackInHand(e.hand), e.player, e.hand);

        return e.success();
    }

    @Override
    public StackActionResult onRightClick(ItemUseEvent e) {

        // 耐久値が0の場合はそのまま終了
        if (!Config.infiniteDurability && ItemStackUtil.isBreak(e.stack))
            return super.onRightClick(e);

        if (e.isClient()) super.onRightClick(e);

        World world = e.getMidohraWorld();
        Player user = e.user;
        BlockPos blockPos = getPlacingPos(user);

        if (WorldUtil.canSetBlock(world.toMinecraft(), blockPos.toMinecraft()) &&
                canPlace(world.getBlockState(blockPos).getBlock()) && world.getBlockEntity(blockPos).isEmpty()) {
            world.setBlockState(blockPos, SolomonsBlock.SOLOMONS_BLOCK.getDefaultMidohraState());
            world.playSound(null, user.getBlockPosM(), Sounds.CREATE_SOUND, CompatSoundCategory.MASTER, 1f, 1f);

            damageStackIfDamageable(e.getStackM(), user, e.hand);

            return e.success();
        }
        return super.onRightClick(e);
    }

    public static void damageStackIfDamageable(ItemStack stack, Player player, Hand hand) {
        if (!Config.infiniteDurability && player.isServerPlayer()) {
            ItemStackUtil.damage(stack.toMinecraft(), 1, player.getPlayerEntity(), HandUtil.getEquipmentSlot(hand));
        }
    }

    public static BlockPos getPlacingPos(Player user) {
        Vector3d pos = user.getPosM();

        double posX = pos.x;
        double posY = pos.y;
        double posZ = pos.z;

        boolean notChange = false;
        if (user.getPitch() <= -25) {
            posY += 2;
            if (user.getPitch() <= -60 && user.getPitch() >= -90) {
                notChange = true;
            }
        }

        if (user.getPitch() <= 25 && user.getPitch() >= -25) {
            posY += 1;
        }

        if (user.getPitch() >= 50) {
            posY -= 1;
            if (user.getPitch() <= 90 && user.getPitch() >= 75) {
                notChange = true;
            }
        }

        Direction horizontalFacing = Direction.of(user.getHorizontalFacing());

        if (!notChange) {
            if (horizontalFacing == Direction.EAST)
                posX += 1;
            if (horizontalFacing == Direction.WEST)
                posX -= 1;
            if (horizontalFacing == Direction.NORTH)
                posZ -= 1;
            if (horizontalFacing == Direction.SOUTH)
                posZ += 1;
        }

        return Vector3d.of(posX, posY, posZ).toInt().toPos();
    }

    public static boolean canPlace(BlockWrapper block) {
        if (block == null) return true;
        if (block.isEmpty()) return true;
        if (block.instanceOf(AirBlock.class)) return true;
        if (block.instanceOf(FluidBlock.class)) return true;
        if (block.instanceOf(ShortPlantBlock.class)) return true;
        if (block.instanceOf(DeadBushBlock.class)) return true;
        return false;
    }

    @Override
    public boolean isEnchantable(EnchantableArgs args) {
        return true;
    }
}