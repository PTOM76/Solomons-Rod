package ml.pkom.solomonsrod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import ml.pkom.solomonsrod.SolomonsWand;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// ハイライトさせたかったがとりあえずわかんないのでこのまま放置
@Mixin(AirBlock.class)
public class SelectAirPlacedMixin {
    // Tessellator tessellator = Tessellator.getInstance();
    @Inject(at = @At("TAIL"), method = "getOutlineShape", cancellable = true)
    public void inject(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> ci) {
        if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT))
            if (net.minecraft.client.MinecraftClient.getInstance().player != null) {
                PlayerEntity player = net.minecraft.client.MinecraftClient.getInstance().player;
                if (player.getMainHandStack().getItem() instanceof SolomonsWand) {
                    //ci.setReturnValue(VoxelShapes.fullCube());
                }
            }
    }
}
