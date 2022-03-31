package ml.pkom.solomonsrod;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;

public class DemonsWand extends SolomonsWand {
    public static DemonsWand DEMONS_WAND = new DemonsWand(new Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1));

    public DemonsWand(Properties properties) {
        super(properties);
        EntityEvent.LIVING_HURT.register((entity, damageSource, f) -> {
            Entity attacker = damageSource.getEntity();
            if (attacker instanceof Player) {
                Player player = (Player) attacker;
                if (player.getMainHandItem().getItem() instanceof DemonsWand) {
                    if(entity instanceof Animal || entity instanceof Slime || entity instanceof Villager || entity instanceof WaterAnimal) {
                        player.getLevel().playSound(null, player.getOnPos(), Sounds.BAM_SOUND.get(), SoundSource.MASTER, 1f, 1f);
                        entity.kill();
                        return EventResult.interruptTrue();
                    }
                }
            }
            return EventResult.pass();
        });
    }
}
