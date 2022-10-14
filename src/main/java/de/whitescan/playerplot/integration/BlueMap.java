package de.whitescan.playerplot.integration;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.BlueMapMap;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import de.bluecolored.bluemap.api.markers.POIMarker;
import de.whitescan.playerplot.plot.Plot;
import de.whitescan.playerplot.util.PlotPoint;

public class BlueMap implements MapIntegration {

	private MarkerSet markerSet;

	public BlueMap() {
		this.markerSet = MarkerSet.builder().label("Plots").build();
	}

	@Override
	public void deletePlot(Plot plot) {

		markerSet.getMarkers().remove(plot.getID().toString());

		BlueMapAPI.getInstance().ifPresent(api -> {

			api.getWorld(Bukkit.getPlayer(plot.getOwnerID()).getWorld()).ifPresent(world -> {
				for (BlueMapMap map : world.getMaps()) {
					map.getMarkerSets().put("plots", markerSet);
				}
			});

		});

	}

	@Override
	public void updateMarker(Plot plot) {
		drawPlot(plot);
	}

	@Override
	public void drawPlot(Plot plot) {

		Bukkit.getLogger().warning("DEBUG: drawPlot " + plot.getDisplayName());

		PlotPoint min = plot.getMinCorner();
		PlotPoint max = plot.getMaxCorner();

		POIMarker marker = POIMarker.toBuilder().label(plot.getDisplayName()).position(min.getX(), 63, max.getZ())
				.maxDistance(1000).build();

		markerSet.getMarkers().put(plot.getID().toString(), marker);

		if (BlueMapAPI.getInstance().isPresent()) {
			Bukkit.getLogger().warning("DEBUG: BlueMapAPI was present");
			draw(Bukkit.getPlayer(plot.getOwnerID()).getWorld(), marker);

		} else {

			Bukkit.getLogger().warning("DEBUG: BlueMapAPI was not present, enableing listener...");
			BlueMapAPI.onEnable(api -> {
				draw(Bukkit.getPlayer(plot.getOwnerID()).getWorld(), marker);
			});

		}

	}

	private void draw(World world, POIMarker marker) {
		Bukkit.getLogger().warning("DEBUG: Drawing marker: " + marker.getLabel());
		BlueMapAPI.getInstance().get().getWorld(world).ifPresent(blueMapWorld -> {
			for (BlueMapMap map : blueMapWorld.getMaps()) {
				map.getMarkerSets().put("plots", markerSet);
				Bukkit.getLogger().warning("DEBUG: Finished marker: " + marker.getLabel());
			}
		});
	}

	@Override
	public void shutdown() {
	}

}
