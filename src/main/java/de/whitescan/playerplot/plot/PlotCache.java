package de.whitescan.playerplot.plot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.whitescan.playerplot.config.PluginBase;
import de.whitescan.playerplot.config.PluginConfig;
import de.whitescan.playerplot.database.FilePlotDatabase;
import de.whitescan.playerplot.database.IPlotDatabase;
import de.whitescan.playerplot.database.SQLPlotDatabase;
import de.whitescan.playerplot.logic.Friend;
import de.whitescan.playerplot.logic.Plot;
import de.whitescan.playerplot.util.GridZone;
import de.whitescan.playerplot.util.MapUtil;
import de.whitescan.playerplot.util.PlotPoint;

public class PlotCache {

	private static Map<UUID, Plot> iDToPlot = new HashMap<>();
	private static Map<GridZone, List<Plot>> zoneToPlots = new HashMap<>();
	private static Map<UUID, List<Plot>> playerToPlots = new HashMap<>();
	private static Map<UUID, List<Plot>> playerToFPlots = new HashMap<>();
	private static Set<UUID> unsavedPlots = new HashSet<>();

	private IPlotDatabase database;

	public PlotCache() {
		load();
	}

	private void load() {

		if (PluginConfig.isUseDatabase()) {
			this.database = new SQLPlotDatabase();
		} else {
			this.database = new FilePlotDatabase();
		}

		List<Plot> plots = database.fetch();

		for (Plot plot : plots) {
			iDToPlot.put(plot.getId(), plot);
			assignToZones(plot);
			MapUtil.addItemToList(playerToPlots, plot.getOwnerId(), plot);
			for (Friend friend : plot.getFriends()) {
				MapUtil.addItemToList(playerToFPlots, friend.getUuid(), plot);
			}
		}

		if (PluginBase.isMapIntegrationEnabled()) {
			PluginBase.getMapIntegration().registerPlots(plots);
		}

	}

	public static void assignToZones(Plot plot) {
		Set<GridZone> zones = new HashSet<>();
		for (PlotPoint corner : plot.getCorners()) {
			zones.add(GridZone.fromPlotPoint(corner));
		}

		for (GridZone gridZone : zones) {
			MapUtil.addItemToList(zoneToPlots, gridZone, plot);
		}
	}

	public void shutdown() {
		save();
		iDToPlot.clear();
		zoneToPlots.clear();
		playerToPlots.clear();
		playerToFPlots.clear();
	}

	public void save() {
		for (UUID plotID : unsavedPlots) {
			database.store(plotID, iDToPlot.get(plotID));
		}
		unsavedPlots.clear();
	}

	public static Plot getPlot(Location location) {
		GridZone zone = GridZone.fromLocation(location);
		if (zoneToPlots.containsKey(zone)) {
			for (Plot plot : zoneToPlots.get(zone)) {
				if (plot.contains(location)) {
					return plot;
				}
			}
		}
		return null;
	}

	public static boolean hasPlot(Location location) {
		GridZone zone = GridZone.fromLocation(location);
		if (zoneToPlots.containsKey(zone)) {
			for (Plot plot : zoneToPlots.get(zone)) {
				if (plot.contains(location)) {
					return true;
				}
			}
		}
		return false;
	}

	public static Collection<Plot> getAllPlots() {
		return iDToPlot.values();
	}

	public static Set<Plot> getPlotsNear(Location location, int sideLength) {
		Set<Plot> closePlots = new HashSet<>();
		for (PlotPoint plotCorner : PlotPoint.fromLocation(location).getCorners(sideLength)) {
			GridZone zone = GridZone.fromPlotPoint(plotCorner);
			if (zoneToPlots.containsKey(zone)) {
				for (Plot plot : zoneToPlots.get(zone)) {
					if (location.getWorld().getName().equals(plot.getWorld())) {
						closePlots.add(plot);
					}
				}
			}
		}
		return closePlots;
	}

	public static void registerPlot(Plot plot) {
		iDToPlot.put(plot.getId(), plot);
		MapUtil.addItemToList(playerToPlots, plot.getOwnerId(), plot);
		unsavedPlots.add(plot.getId());
		assignToZones(plot);
	}

	public static void removePlot(Plot plot) {
		iDToPlot.remove(plot.getId());
		MapUtil.removeItemFromList(playerToPlots, plot.getOwnerId(), plot);
		unsavedPlots.add(plot.getId());
		for (Friend friend : plot.getFriends()) {
			registerFriendRemove(friend, plot);
		}
		unassignFromZones(plot);
	}

	public static void unassignFromZones(Plot plot) {
		Set<GridZone> zones = new HashSet<>();
		for (PlotPoint corner : plot.getCorners()) {
			zones.add(GridZone.fromPlotPoint(corner));
		}
		for (GridZone gridZone : zones) {
			MapUtil.removeItemFromList(zoneToPlots, gridZone, plot);
		}
	}

	public static void touch(Plot plot) {
		unsavedPlots.add(plot.getId());
	}

	public static List<Plot> getFriendPlots(Player player) {
		List<Plot> toReturn = new ArrayList<>();
		List<Plot> fPlots = playerToFPlots.get(player.getUniqueId());
		if (fPlots != null) {
			toReturn.addAll(fPlots);
		}
		return toReturn;
	}

	public static void registerFriendAdd(Friend friend, Plot plot) {
		MapUtil.addItemToList(playerToFPlots, friend.getUuid(), plot);
	}

	public static void registerFriendRemove(Friend friend, Plot plot) {
		MapUtil.removeItemFromList(playerToFPlots, friend.getUuid(), plot);
	}

	public static List<Plot> getPlayerPlots(UUID playerID) {
		List<Plot> toReturn = new ArrayList<>();
		if (playerToPlots.containsKey(playerID)) {
			List<Plot> plots = playerToPlots.get(playerID);
			if (plots != null) {
				toReturn.addAll(plots);
			}
		}
		return toReturn;
	}

	public static Plot getPlayerPlot(UUID playerID, String plotName) {
		for (Plot plot : getPlayerPlots(playerID)) {
			if (plot.getName().equalsIgnoreCase(plotName)) {
				return plot;
			}
		}
		return null;
	}

	public static int getPlayerPlotsUsed(UUID playerID) {
		int plotsUsed = 0;
		for (Plot plot : getPlayerPlots(playerID)) {
			plotsUsed += plot.getComponents();
		}
		return plotsUsed;
	}
}
