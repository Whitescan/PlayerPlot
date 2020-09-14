package com.eclipsekingdom.playerplot.plot;

import com.eclipsekingdom.playerplot.PlayerPlot;
import com.eclipsekingdom.playerplot.util.Scheduler;
import com.eclipsekingdom.playerplot.util.X.XSound;
import com.eclipsekingdom.playerplot.util.border.Border;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlotBeam extends BukkitRunnable {

    private static Map<UUID, PlotBeam> playerToBeam = new HashMap<>();

    public static boolean isBeaming(UUID playerId) {
        return playerToBeam.containsKey(playerId);
    }

    public static PlotBeam getBeam(UUID playerId) {
        return playerToBeam.get(playerId);
    }

    public static void shutdown() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID playerId = player.getUniqueId();
            if (playerToBeam.containsKey(playerId)) {
                playerToBeam.get(playerId).end();
            }
        }
    }

    private static final XSound TELEPORT_SOUND = XSound.BLOCK_BEACON_POWER_SELECT;
    private static final Vector UP_VELOCITY = new Vector(0, 0.05, 0);
    private static final Vector DOWN_VELOCITY = new Vector(0, -0.05, 0);

    private Player player;
    private UUID playerId;
    private Location destination;
    private int frame;

    public PlotBeam(Player player, Location destination) {
        this.player = player;
        if (player.getVehicle() != null) player.getVehicle().eject();
        this.playerId = player.getUniqueId();
        this.destination = destination;
        this.frame = 0;
        runTaskTimer(PlayerPlot.getPlugin(), 0, 1);
        playerToBeam.put(playerId, this);
    }

    @Override
    public void run() {
        if (frame == 0) {
            showBeam(player, 20);
            playSound(player);
            player.setVelocity(UP_VELOCITY);
        } else if (frame < 20) {
            player.setVelocity(UP_VELOCITY.clone().multiply(frame));
        } else if (frame == 20) {
            player.teleport(getHighestUnObstructed(destination, 6));
            showBeam(player, 20);
            playSound(player);
            player.setVelocity(DOWN_VELOCITY);
            player.setFallDistance(0);
        } else if (frame < 40) {
            player.setVelocity(DOWN_VELOCITY.clone().multiply(40 - frame));
            player.setFallDistance(0);
        } else {
            endNaturally();
        }

        frame++;
    }

    public void endNaturally() {
        playerToBeam.remove(playerId);
        cancel();
    }

    public void end() {
        player.teleport(destination);
        playerToBeam.remove(playerId);
        cancel();
    }


    private static void playSound(Player player) {
        if (TELEPORT_SOUND.isSupported()) {
            player.playSound(player.getLocation(), TELEPORT_SOUND.parseSound(), 0.5f, 1.9f);
        }
    }

    private static void showBeam(Player player, int durationTicks) {
        UUID bId = Border.showBeam(player);
        Scheduler.runLater(() -> {
            Border.hide(player, bId);
        }, durationTicks);
    }

    private Location getHighestUnObstructed(Location destination, int maxHeight) {
        Vector toAdd = new Vector(0, 1, 0);
        Location loc = destination.clone();
        Block block = loc.getBlock();
        int height = 0;
        while (!block.getType().isSolid() && height <= maxHeight) {
            loc.add(toAdd);
            height++;
            block = loc.getBlock();
        }
        return loc;
    }


}
