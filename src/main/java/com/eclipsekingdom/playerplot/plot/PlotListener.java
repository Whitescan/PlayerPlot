package com.eclipsekingdom.playerplot.plot;

import com.eclipsekingdom.playerplot.PlayerPlot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class PlotListener implements Listener {

    public PlotListener() {
        Plugin plugin = PlayerPlot.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        UUID playerId = e.getPlayer().getUniqueId();
        if (PlotBeam.isBeaming(playerId)) {
            PlotBeam.getBeam(playerId).end();
        }
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        UUID playerId = e.getEntity().getUniqueId();
        if (PlotBeam.isBeaming(playerId)) {
            PlotBeam.getBeam(playerId).endNaturally();
        }
    }


}
