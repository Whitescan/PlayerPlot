package com.eclipsekingdom.playerplot.util.scanner;

import com.eclipsekingdom.playerplot.plot.Plot;
import com.eclipsekingdom.playerplot.util.PlotPoint;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_12_R1.WorldBorder;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Border_v1_12_R1 implements IBorder {

    @Override
    public void show(Player player, Plot plot) {
        WorldBorder worldBorder = new WorldBorder();
        worldBorder.world = ((CraftWorld) player.getWorld()).getHandle();
        PlotPoint center = plot.getCenter();
        int length = plot.getMaxCorner().getX() - plot.getMinCorner().getX();
        double offSet = length % 2 == 0 ? 0.5 : 1.0;
        worldBorder.setCenter(center.getX() + offSet, center.getZ() + offSet);
        worldBorder.setSize(length);
        worldBorder.setDamageAmount(0);
        worldBorder.setWarningDistance(0);
        PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
    }

    @Override
    public void hide(Player player) {
        WorldBorder worldBorder = new WorldBorder();
        worldBorder.world = ((CraftWorld) player.getWorld()).getHandle();
        PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.LERP_SIZE);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
    }
}