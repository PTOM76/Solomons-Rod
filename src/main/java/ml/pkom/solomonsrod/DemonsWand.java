package ml.pkom.solomonsrod;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;

public class DemonsWand extends SolomonsWand {
    public static DemonsWand DEMONS_WAND = new DemonsWand(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1));

    public DemonsWand(FabricItemSettings settings) {
        super(settings);
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player.getMainHandStack().getItem() instanceof DemonsWand) {
                if(entity instanceof AnimalEntity || entity instanceof SlimeEntity || entity instanceof VillagerEntity || entity instanceof WaterCreatureEntity) {
                    player.world.playSound(null, player.getBlockPos(), Sounds.BAM_SOUND_EVENT, SoundCategory.MASTER, 1f, 1f);
                    entity.kill();
                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.PASS;
        });
    }

    /*
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(entity instanceof AnimalEntity || entity instanceof SlimeEntity || entity instanceof VillagerEntity || entity instanceof WaterCreatureEntity) {
            user.world.playSound(null, user.getBlockPos(), Sounds.BAM_SOUND_EVENT, SoundCategory.MASTER, 1f, 1f);
            entity.kill();
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

     */
}
