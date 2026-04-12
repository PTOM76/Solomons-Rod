package net.pitan76.solomonsrod.renderer;

import net.pitan76.mcpitanlib.api.client.event.listener.WorldRenderContext;
import net.pitan76.mcpitanlib.api.client.event.listener.WorldRenderContextListener;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.util.VoxelShapeUtil;
import net.pitan76.mcpitanlib.api.util.client.ClientUtil;
import net.pitan76.mcpitanlib.midohra.client.render.CameraWrapper;
import net.pitan76.mcpitanlib.midohra.item.ItemStack;
import net.pitan76.mcpitanlib.midohra.util.math.BlockPos;
import net.pitan76.mcpitanlib.midohra.util.math.Vector3d;
import net.pitan76.solomonsrod.SolomonsWand;

import java.util.Optional;

public class HighlightRenderer implements WorldRenderContextListener {

    @Override
    public void render(WorldRenderContext e) {
        if (ClientUtil.getClientPlayer() == null) return;
        Player player = ClientUtil.getPlayer();

        Optional<ItemStack> stackOptional = player.getCurrentHandItem().map(ItemStack::of);
        if (!stackOptional.isPresent()) return;
        ItemStack stack = stackOptional.get();

        if (!(stack.getItem().instanceOf(SolomonsWand.class))) return;

        CameraWrapper camera = e.getCameraWrapper();
        BlockPos blockPos = SolomonsWand.getPlacingPos(player);

        Vector3d camPos = camera.getCameraPos();
        double x = blockPos.getX() - camPos.x;
        double y = blockPos.getY() - camPos.y;
        double z = blockPos.getZ() - camPos.z;

        e.push();
        e.translate(x, y, z);
        e.drawBox(VoxelShapeUtil.getBoundingBox(VoxelShapeUtil.fullCube()), 1f, 1f, 1f, 1f);
        e.pop();
    }
}
