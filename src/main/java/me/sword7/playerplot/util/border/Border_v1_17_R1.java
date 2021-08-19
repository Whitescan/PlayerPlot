package me.sword7.playerplot.util.border;

import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.world.level.border.WorldBorder;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;


public class Border_v1_17_R1 implements IBorder {

    @Override
    public void show(Player player, World world, double x, double z, double size) {
        WorldBorder worldBorder = new WorldBorder();
        worldBorder.world = ((CraftWorld) world).getHandle();
        worldBorder.setCenter(x, z);
        worldBorder.setSize(size);
        worldBorder.setDamageAmount(0);
        worldBorder.setWarningDistance(0);
        ((CraftPlayer) player).getHandle().b.sendPacket(new ClientboundInitializeBorderPacket(worldBorder));
    }

    @Override
    public void hide(Player player, World world) {
        WorldBorder worldBorder = new WorldBorder();
        worldBorder.world = ((CraftWorld) world).getHandle();
        ((CraftPlayer) player).getHandle().b.sendPacket(new ClientboundSetBorderLerpSizePacket(worldBorder));
    }

}
