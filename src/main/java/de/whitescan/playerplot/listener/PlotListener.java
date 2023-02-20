package de.whitescan.playerplot.listener;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import de.whitescan.playerplot.PlayerPlot;
import de.whitescan.playerplot.plot.PlotBeam;

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
