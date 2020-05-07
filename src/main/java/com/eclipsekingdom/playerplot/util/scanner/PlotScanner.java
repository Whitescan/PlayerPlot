package com.eclipsekingdom.playerplot.util.scanner;

import com.eclipsekingdom.playerplot.PlayerPlot;
import com.eclipsekingdom.playerplot.plot.Plot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlotScanner implements Listener {

    private static Map<UUID, UUID> scanningPlayers = new HashMap<>();
    private static IBorder border = Border.select();

    public PlotScanner() {
        Plugin plugin = PlayerPlot.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static void shutdown() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (scanningPlayers.containsKey(player.getUniqueId())) {
                border.hide(player);
            }
        }
        scanningPlayers.clear();
    }

    @EventHandler
    public void onPunch(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (scanningPlayers.containsKey(player.getUniqueId())) {
            Action action = e.getAction();
            if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) {
                scanningPlayers.remove(player.getUniqueId());
                border.hide(player);
            }
        }
    }

    public static void showPlot(Player player, Plot plot, int duration) {
        UUID playerID = player.getUniqueId();
        if (!scanningPlayers.containsKey(playerID)) {
            border.show(player, plot);
            UUID scannerID = UUID.randomUUID();
            Bukkit.getScheduler().runTaskLater(PlayerPlot.getPlugin(), () -> {
                hidePlot(player, scannerID);
            }, 20 * duration);
            scanningPlayers.put(playerID, scannerID);
        }
    }

    private static void hidePlot(Player player, UUID scannerID) {
        UUID playerID = player.getUniqueId();
        if (scanningPlayers.containsKey(playerID)) {
            UUID currentID = scanningPlayers.get(playerID);
            if (currentID.equals(scannerID)) {
                border.hide(player);
                scanningPlayers.remove(playerID);
            }
        }
    }

}
