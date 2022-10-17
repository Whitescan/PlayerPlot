package de.whitescan.playerplot.integration;

import java.util.List;

import de.whitescan.playerplot.plot.Plot;

public interface MapIntegration {

	public default void registerPlots(List<Plot> plotList) {
		for (Plot plot : plotList)
			drawPlot(plot);
	}

	public default void registerPlot(Plot plot) {
		drawPlot(plot);
	}

	public default void updatePlot(Plot plot) {
		deletePlot(plot);
		registerPlot(plot);
	}

	public void deletePlot(Plot plot);

	public void updateMarker(Plot plot);

	public void drawPlot(Plot plot);

	public void shutdown();

}
