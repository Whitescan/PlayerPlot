package com.eclipsekingdom.playerplot.data.event;

import com.eclipsekingdom.playerplot.PlayerPlot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DataLoadListener implements Listener {

    private static Set<UUID> waitingPlayers = new HashSet<>();

    public DataLoadListener() {
        Plugin plugin = PlayerPlot.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDataLoad(UserDataLoadEvent e) {
        UUID playerID = e.getPlayerID();
        if (waitingPlayers.contains(playerID)) {
            Player player = Bukkit.getPlayer(playerID);
            if (player != null) {
                UserDataLoadEvent.Result result = e.getResult();
                ChatColor chatColor = result == UserDataLoadEvent.Result.SUCCESS ? ChatColor.LIGHT_PURPLE : ChatColor.RED;
                player.sendMessage(chatColor + result.getMessage());
            }
            waitingPlayers.remove(playerID);
        }
    }

    public static void registerWaitingPlayer(Player player) {
        waitingPlayers.add(player.getUniqueId());
    }

}
