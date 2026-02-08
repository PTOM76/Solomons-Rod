package net.pitan76.solomonsrod.renderer;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.pitan76.mcpitanlib.api.client.event.listener.WorldRenderContext;
import net.pitan76.mcpitanlib.api.client.event.listener.WorldRenderContextListener;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.util.VoxelShapeUtil;
import net.pitan76.mcpitanlib.api.util.client.ClientUtil;
import net.pitan76.mcpitanlib.api.util.math.PosUtil;
import net.pitan76.mcpitanlib.midohra.client.render.CameraWrapper;
import net.pitan76.mcpitanlib.midohra.util.math.Vector3d;
import net.pitan76.solomonsrod.SolomonsWand;

import java.util.Optional;

public class HighlightRenderer implements WorldRenderContextListener {

    @Override
    public void render(WorldRenderContext e) {
        if (ClientUtil.getClientPlayer() == null) return;
        Player player = ClientUtil.getPlayer();

        Optional<ItemStack> stackOptional = player.getCurrentHandItem();
        if (!stackOptional.isPresent()) return;
        ItemStack stack = stackOptional.get();

        if (!(stack.getItem() instanceof SolomonsWand)) return;

        CameraWrapper camera = e.getCameraWrapper();
        BlockPos blockPos = SolomonsWand.getPlacingPos(player);

        Vector3d camPos = camera.getCameraPos();
        double x = PosUtil.x(blockPos) - camPos.x;
        double y = PosUtil.y(blockPos) - camPos.y;
        double z = PosUtil.z(blockPos) - camPos.z;

        e.push();
        e.translate(x, y, z);
        e.drawBox(VoxelShapeUtil.getBoundingBox(VoxelShapeUtil.fullCube()), 1f, 1f, 1f, 1f);
        e.pop();
    }
}
