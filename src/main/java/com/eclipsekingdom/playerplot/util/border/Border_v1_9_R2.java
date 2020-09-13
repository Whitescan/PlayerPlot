package com.eclipsekingdom.playerplot.util.border;

import net.minecraft.server.v1_9_R2.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_9_R2.WorldBorder;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Border_v1_9_R2 implements IBorder {

    @Override
    public void show(Player player, World world, double x, double z, double size) {
        WorldBorder worldBorder = new WorldBorder();
        worldBorder.world = ((CraftWorld) world).getHandle();
        worldBorder.setCenter(x, z);
        worldBorder.setSize(size);
        worldBorder.setDamageAmount(0);
        worldBorder.setWarningDistance(0);
        PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
    }

    @Override
    public void hide(Player player, World world) {
        WorldBorder worldBorder = new WorldBorder();
        worldBorder.world = ((CraftWorld) world).getHandle();
        PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.LERP_SIZE);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
    }

}