package com.eclipsekingdom.playerplot.util.border;

import com.eclipsekingdom.playerplot.config.Version;
import com.eclipsekingdom.playerplot.plot.Plot;
import com.eclipsekingdom.playerplot.util.PlotPoint;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Border {

    private static Map<UUID, ClientBorder> playerToBorder = new HashMap<>();

    public static boolean hasBorder(UUID playerId) {
        return playerToBorder.containsKey(playerId);
    }

    public static ClientBorder getBorder(UUID playerId) {
        return playerToBorder.get(playerId);
    }

    public static void shutdown() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Border.hide(player);
        }
        playerToBorder.clear();
    }

    private static IBorder border = select();

    public static IBorder select() {
        switch (Version.getCurrent()) {
            case v1_16_R3:
                return new Border_V1_16_R3();
            case v1_16_R2:
                return new Border_v1_16_R2();
            case v1_16_R1:
                return new Border_v1_16_R1();
            case v1_15_R1:
                return new Border_v1_15_R1();
            case v1_14_R1:
                return new Border_v1_14_R1();
            case v1_13_R2:
                return new Border_v1_13_R2();
            case v1_13_R1:
                return new Border_v1_13_R1();
            case v1_12_R1:
                return new Border_v1_12_R1();
            case v1_11_R1:
                return new Border_v1_11_R1();
            case v1_10_R1:
                return new Border_v1_10_R1();
            case v1_9_R2:
                return new Border_v1_9_R2();
            case v1_9_R1:
                return new Border_v1_9_R1();
            case v1_8_R3:
                return new Border_v1_8_R3();
            case v1_8_R2:
                return new Border_v1_8_R2();
            default:
                return new Border_Unknown();
        }
    }

    public static UUID showPlot(Player player, Plot plot) {
        World world = player.getWorld();
        PlotPoint plotCenter = plot.getCenter();
        double size = plot.getMaxCorner().getX() - plot.getMinCorner().getX();
        double offSet = size % 2 == 0 ? 0.5 : 1.0;
        double x = plotCenter.getX() + offSet;
        double z = plotCenter.getZ() + offSet;
        return showBorder(player, world, x, z, size);
    }

    public static UUID showBeam(Player player) {
        Location loc = player.getLocation();
        World world = loc.getWorld();
        return showBorder(player, world, loc.getX(), loc.getZ(), 2);
    }

    private static UUID showBorder(Player player, World world, double x, double z, double size) {
        ClientBorder clientBorder = new ClientBorder(world);
        clientBorder.setPlayerPusher(new PlayerPusher(player, x, z, size));
        playerToBorder.put(player.getUniqueId(), clientBorder);
        border.show(player, world, x, z, size);
        return clientBorder.getId();
    }

    public static void hide(Player player, UUID pid) {
        UUID playerId = player.getUniqueId();
        if (playerToBorder.containsKey(playerId)) {
            ClientBorder clientBorder = playerToBorder.get(playerId);
            if (clientBorder.getId().equals(pid)) {
                border.hide(player, clientBorder.getWorld());
                playerToBorder.remove(playerId);
                clientBorder.stopPusher();
            }
        }
    }

    private static void hide(Player player) {
        UUID playerId = player.getUniqueId();
        if (playerToBorder.containsKey(playerId)) {
            ClientBorder clientBorder = playerToBorder.get(playerId);
            border.hide(player, clientBorder.getWorld());
            clientBorder.stopPusher();
        }
    }

}
