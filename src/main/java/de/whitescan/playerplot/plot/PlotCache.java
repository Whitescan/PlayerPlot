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
import de.whitescan.playerplot.logic.Friend;
import de.whitescan.playerplot.util.GridZone;
import de.whitescan.playerplot.util.MapUtil;
import de.whitescan.playerplot.util.PlotPoint;

public class PlotCache {

	private static Map<UUID, Plot> IDToPlot = new HashMap<>();
	private static Map<GridZone, List<Plot>> zoneToPlots = new HashMap<>();
	private static Map<UUID, List<Plot>> playerToPlots = new HashMap<>();
	private static Map<UUID, List<Plot>> playerToFPlots = new HashMap<>();
	private static Set<UUID> unsavedPlots = new HashSet<>();

	private static boolean usingDatabase = PluginConfig.isUseDatabase();
	private static PlotDatabase database = usingDatabase ? new PlotDatabase() : null;

	public PlotCache() {
		load();
	}

	private void load() {
		List<Plot> plots = (usingDatabase) ? database.fetchPlots() : PlotFlatFile.fetch();
		for (Plot plot : plots) {
			IDToPlot.put(plot.getID(), plot);
			assignToZones(plot);
			MapUtil.addItemToList(playerToPlots, plot.getOwnerID(), plot);
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

	public static void shutdown() {
		save();
		IDToPlot.clear();
		zoneToPlots.clear();
		playerToPlots.clear();
		playerToFPlots.clear();
	}

	public static void save() {
		if (usingDatabase) {
			for (UUID plotID : unsavedPlots) {
				database.storePlot(plotID, IDToPlot.get(plotID));
			}
		} else {
			for (UUID plotID : unsavedPlots) {
				PlotFlatFile.store(plotID, IDToPlot.get(plotID));
			}
			PlotFlatFile.save();
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
		return IDToPlot.values();
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
		IDToPlot.put(plot.getID(), plot);
		MapUtil.addItemToList(playerToPlots, plot.getOwnerID(), plot);
		unsavedPlots.add(plot.getID());
		assignToZones(plot);
	}

	public static void removePlot(Plot plot) {
		IDToPlot.remove(plot.getID());
		MapUtil.removeItemFromList(playerToPlots, plot.getOwnerID(), plot);
		unsavedPlots.add(plot.getID());
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
		unsavedPlots.add(plot.getID());
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
