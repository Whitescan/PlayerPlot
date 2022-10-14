package de.whitescan.playerplot.integration;

import de.bluecolored.bluemap.api.markers.MarkerSet;
import de.bluecolored.bluemap.api.markers.POIMarker;
import de.whitescan.playerplot.plot.Plot;
import de.whitescan.playerplot.util.PlotPoint;

public class BlueMap implements MapIntegration {

	@Override
	public void deletePlot(Plot plot) {
		MarkerSet set = MarkerSet.builder().build();
		set.getMarkers().remove(plot.getID().toString());
	}

	@Override
	public void updateMarker(Plot plot) {
		drawPlot(plot);
	}

	@Override
	public void drawPlot(Plot plot) {

		String displayName = plot.getName() + " ~ " + plot.getOwnerName() + " ~ ";
		PlotPoint min = plot.getMinCorner();
		PlotPoint max = plot.getMaxCorner();

		POIMarker marker = POIMarker.toBuilder().label(displayName).position(min.getX(), 63, max.getZ()).build();
		MarkerSet set = MarkerSet.builder().build();
		set.getMarkers().put(plot.getID().toString(), marker);

	}

	@Override
	public void shutdown() {
	}

}
