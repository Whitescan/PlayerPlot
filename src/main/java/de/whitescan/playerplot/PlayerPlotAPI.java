package de.whitescan.playerplot;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.whitescan.playerplot.listener.UserCache;
import de.whitescan.playerplot.logic.Plot;
import de.whitescan.playerplot.logic.UserData;
import de.whitescan.playerplot.plot.PlotCache;

public class PlayerPlotAPI {

	private PlayerPlotAPI() {
	}

	private static PlayerPlotAPI playerPlotAPI = new PlayerPlotAPI();

	public static PlayerPlotAPI getInstance() {
		return playerPlotAPI;
	}

	public boolean hasAccessAt(Player player, Location location) {
		Plot plot = PlotCache.getPlot(location);
		return plot != null ? plot.isAllowed(player) : true;
	}

	public boolean isPlotAt(Location location) {
		return PlotCache.getPlot(location) != null;
	}

	public Plot getPlotAt(Location location) {
		return PlotCache.getPlot(location);
	}

	public boolean hasUserData(Player player) {
		return UserCache.hasData(player.getUniqueId());
	}

	public UserData getUserData(Player player) {
		return UserCache.getData(player.getUniqueId());

	}

}
