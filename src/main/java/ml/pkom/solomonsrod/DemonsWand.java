package ml.pkom.solomonsrod;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

// 本当は再現したかったのですが、左クリックのメソッドがなかったのでMixinを使うしかないと思ったのですが、わからなかったのでわかったときには修正したいと思ってる。
public class DemonsWand extends Item {
    //group(ItemGroup.TOOLS). 未実装
    public static DemonsWand DEMONS_WAND = new DemonsWand(new FabricItemSettings().maxCount(1).maxDamage(192));

    public DemonsWand(FabricItemSettings settings) {
        super(settings);
    }

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
    }
}
