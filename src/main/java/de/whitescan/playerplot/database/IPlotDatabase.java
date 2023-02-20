package de.whitescan.playerplot.database;

import java.util.List;
import java.util.UUID;

import de.whitescan.playerplot.logic.Plot;

public interface IPlotDatabase {

	public List<Plot> fetch();

	public void store(UUID id, Plot plot);

}
