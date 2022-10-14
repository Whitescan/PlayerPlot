package de.whitescan.playerplot.config;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import de.whitescan.playerplot.PlayerPlot;
import de.whitescan.playerplot.integration.BlueMap;
import de.whitescan.playerplot.integration.DynMap;
import de.whitescan.playerplot.integration.MapIntegration;
import lombok.Getter;

public class PluginBase {

	private static String dynmapNameSpace = "dynmap";
	private static String blueMapNameSpace = "bluemap";

	@Getter
	private static MapIntegration mapIntegration;

	@Getter
	private static boolean mapIntegrationEnabled = false;

	public PluginBase() {
		loadDependencies();
	}

	public void loadDependencies() {

		if (PluginConfig.isUseDynmap())
			loadDynmap();

		if (PluginConfig.isUseBlueMap())
			loadBlueMap();

	}

	private void loadDynmap() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(dynmapNameSpace);
		if (plugin != null && plugin.isEnabled()) {
			mapIntegration = new DynMap(plugin);
			mapIntegrationEnabled = true;
			PlayerPlot.getPlugin().getLogger().info(Language.CONSOLE_DETECT.fromPlugin(dynmapNameSpace));
		}
	}

	private void loadBlueMap() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(blueMapNameSpace);
		if (plugin != null && plugin.isEnabled()) {
			mapIntegration = new BlueMap();
			mapIntegrationEnabled = true;
			PlayerPlot.getPlugin().getLogger().info(Language.CONSOLE_DETECT.fromPlugin(blueMapNameSpace));
		}
	}

}
