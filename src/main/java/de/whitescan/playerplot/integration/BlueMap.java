package de.whitescan.playerplot.integration;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.api.BlueMapMap;
import de.bluecolored.bluemap.api.markers.MarkerSet;
import de.bluecolored.bluemap.api.markers.POIMarker;
import de.whitescan.playerplot.logic.Plot;

public class BlueMap implements MapIntegration {

	private MarkerSet markerSet;

	public BlueMap() {
		this.markerSet = MarkerSet.builder().label("Plots").build();
	}

	@Override
	public void deletePlot(Plot plot) {

		markerSet.getMarkers().remove(plot.getId().toString());

		BlueMapAPI.getInstance().ifPresent(api -> {

			api.getWorld(Bukkit.getPlayer(plot.getOwnerId()).getWorld()).ifPresent(world -> {
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

		POIMarker marker = POIMarker.toBuilder().label(plot.getDisplayName())
				.position(plot.getCenter().getX(), 63, plot.getCenter().getZ()).maxDistance(1000).build();

		markerSet.getMarkers().put(plot.getId().toString(), marker);

		if (BlueMapAPI.getInstance().isPresent()) {
			draw(Bukkit.getWorld(plot.getWorld()), marker);

		} else {

			BlueMapAPI.onEnable(api -> {
				draw(Bukkit.getWorld(plot.getWorld()), marker);
			});

		}

	}

	private void draw(World world, POIMarker marker) {
		BlueMapAPI.getInstance().get().getWorld(world).ifPresent(blueMapWorld -> {
			for (BlueMapMap map : blueMapWorld.getMaps()) {
				map.getMarkerSets().put("plots", markerSet);
			}
		});
	}

	@Override
	public void shutdown() {
	}

}
