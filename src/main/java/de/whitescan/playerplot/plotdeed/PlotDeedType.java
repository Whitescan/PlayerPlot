package de.whitescan.playerplot.plotdeed;

import org.bukkit.ChatColor;

import de.whitescan.playerplot.config.Language;

public enum PlotDeedType {

	DEFAULT(440, Language.LABEL_PLOT_DEED, ChatColor.DARK_PURPLE),
	SWAMP(441, Language.LABEL_SWAMP_PLOT_DEED, ChatColor.DARK_GREEN),
	MOUNTAIN(442, Language.LABEL_MOUNTAIN_PLOT_DEED, ChatColor.RED),
	OCEAN(443, Language.LABEL_OCEAN_PLOT_DEED, ChatColor.BLUE),;

	private Language title;
	private ChatColor color;
	private int customModelData;
	private PlotDeed plotDeed;

	PlotDeedType(int customModelData, Language title, ChatColor color) {
		this.title = title;
		this.color = color;
		this.customModelData = customModelData;
	}

	public static void init() {
		for (PlotDeedType plotDeedType : PlotDeedType.values()) {
			plotDeedType.plotDeed = new PlotDeed(plotDeedType.customModelData,
					plotDeedType.color + plotDeedType.title.toString());
		}
	}

	public PlotDeed getPlotDeed() {
		return plotDeed;
	}

}
