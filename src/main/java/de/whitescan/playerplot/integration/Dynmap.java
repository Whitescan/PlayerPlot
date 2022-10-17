package de.whitescan.playerplot.integration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.plugin.Plugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

import de.whitescan.playerplot.config.PluginConfig;
import de.whitescan.playerplot.plot.Plot;
import de.whitescan.playerplot.util.PlotPoint;

public class Dynmap implements MapIntegration {

	private MarkerAPI markerAPI;
	private DynmapAPI dynmapAPI;
	private MarkerSet playerPlotSet;
	private static int primaryColorInt = 0xCD93CD;
	private static int highlightColorInt = 0x7700B8;

	private Map<UUID, AreaMarker> plotToMarker = new HashMap<>();

	public Dynmap(Plugin plugin) {
		assert plugin instanceof DynmapAPI;
		this.dynmapAPI = (DynmapAPI) plugin;
		this.markerAPI = dynmapAPI.getMarkerAPI();
		this.playerPlotSet = markerAPI.createMarkerSet("playerplot.markerset", "Plots", markerAPI.getMarkerIcons(),
				false);
		playerPlotSet.setHideByDefault(!PluginConfig.isShowPlotMarkersByDefault());

	}

	@Override
	public void deletePlot(Plot plot) {
		UUID plotID = plot.getID();
		if (plotToMarker.containsKey(plotID)) {
			AreaMarker areaMarker = plotToMarker.get(plotID);
			areaMarker.deleteMarker();
			plotToMarker.remove(plotID);
		}
	}

	@Override
	public void updateMarker(Plot plot) {
		UUID plotID = plot.getID();
		if (plotToMarker.containsKey(plotID)) {
			PlotPoint min = plot.getMinCorner();
			PlotPoint max = plot.getMaxCorner();
			double[] x = new double[] { min.getX(), max.getX() };
			double[] z = new double[] { min.getZ(), max.getZ() };
			AreaMarker areaMarker = plotToMarker.get(plotID);
			areaMarker.setCornerLocations(x, z);
		} else {
			drawPlot(plot);
		}
	}

	@Override
	public void drawPlot(Plot plot) {
		UUID plotID = plot.getID();
		PlotPoint min = plot.getMinCorner();
		PlotPoint max = plot.getMaxCorner();
		double[] x = new double[] { min.getX(), max.getX() };
		double[] z = new double[] { min.getZ(), max.getZ() };
		AreaMarker areaMarker = playerPlotSet.createAreaMarker(plotID.toString(),
				plot.getName() + " ~ " + plot.getOwnerName() + " ~", true, plot.getWorld(), x, z, false);
		try {
			areaMarker.setFillStyle(0.5, primaryColorInt);
			areaMarker.setLineStyle(2, 0.77, highlightColorInt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		plotToMarker.put(plotID, areaMarker);
	}

	@Override
	public void shutdown() {
		for (AreaMarker marker : plotToMarker.values()) {
			marker.deleteMarker();
		}
	}

}
