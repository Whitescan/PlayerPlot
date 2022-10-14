package de.whitescan.playerplot.config;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import de.whitescan.playerplot.integration.Dynmap;
import de.whitescan.playerplot.util.SendConsole;

public class PluginBase {

	private static String dynmapNameSpace = "dynmap";

	private static Dynmap dynmap;
	private static boolean dynmapDetected = false;

	public PluginBase() {
		loadDependencies();
	}

	public void loadDependencies() {
		if (PluginConfig.isUseDynmap()) {
			loadDynmap();
		}
	}

	private void loadDynmap() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(dynmapNameSpace);
		if (plugin != null && plugin.isEnabled()) {
			dynmap = new Dynmap(plugin);
			dynmapDetected = true;
			SendConsole.info(Language.CONSOLE_DETECT.fromPlugin(dynmapNameSpace));
		}
	}

	public static Dynmap getDynmap() {
		return dynmap;
	}

	public static boolean isDynmapDetected() {
		return dynmapDetected;
	}
}
