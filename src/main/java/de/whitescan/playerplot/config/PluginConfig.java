package de.whitescan.playerplot.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;

public class PluginConfig {

	private static String languageFileField = "Language file";
	@Getter
	private static String languageFile = "en";

	private static String useAliasField = "Use /pplot root";
	private static boolean useAlias = false;

	private static String startingPlotNumField = "Starting Plot Amount";
	@Getter
	private static int startingPlotNum = 1;

	private static String maxPlotNumField = "Maximum Plot Amount";
	@Getter
	private static int maxPlotNum = 50;

	private static String unitSizeField = "Plot Unit Size";
	@Getter
	private static int unitSize = 25;

	private static String plotPvpField = "Plot pvp";
	@Getter
	private static boolean plotPvp = true;

	private static String blacklistWorldsString = "Plot world blacklist";
	@Getter
	private static List<String> blacklistWorlds = buildBlacklistWorld();

	private static List<String> buildBlacklistWorld() {
		List<String> blacklistWorlds = new ArrayList<>();
		blacklistWorlds.add("example-world");
		return blacklistWorlds;
	}

	private static String protectionWarningSection = "Protection warning";
	private static String warnParticleField = "particle";
	@Getter
	private static boolean warnParticle = true;
	private static String warnSoundField = "sound";
	@Getter
	private static boolean warnSound = true;
	private static String warnMessageField = "message";
	@Getter
	private static boolean warnMessage = true;

	private static String useDatabaseField = "Use database";
	@Getter
	private static boolean useDatabase = false;

	private static String hostField = "host";
	@Getter
	private static String host = "00.00.000.00";

	private static String portField = "port";
	@Getter
	private static String port = "3306";

	private static String databaseField = "database";
	@Getter
	private static String database = "myDatabase";

	private static String userField = "username";
	@Getter
	private static String username = "myUsername";

	private static String passField = "password";
	@Getter
	private static String password = "myPassword";

	private static String sslField = "ssl";
	@Getter
	private static boolean ssl = false;

	private static String useDynmapField = "Use dynmap";
	@Getter
	private static boolean useDynmap = true;

	private static String useBlueMapField = "Use bluemap";
	@Getter
	private static boolean useBlueMap = false;

	private static String showPlotsMarkersByDefaultField = "Show Plot Markers by Default";
	@Getter
	private static boolean showPlotMarkersByDefault = true;

	public PluginConfig() {
		load();
	}

	private static void load() {
		File file = new File("plugins/PlayerPlot", "config.yml");
		if (file.exists()) {
			try {
				FileConfiguration config = YamlConfiguration.loadConfiguration(file);
				languageFile = config.getString(languageFileField, languageFile);
				useAlias = config.getBoolean(useAliasField, useAlias);
				startingPlotNum = config.getInt(startingPlotNumField, startingPlotNum);
				maxPlotNum = config.getInt(maxPlotNumField, maxPlotNum);
				unitSize = config.getInt(unitSizeField, unitSize);
				plotPvp = config.getBoolean(plotPvpField, plotPvp);
				blacklistWorlds = config.getStringList(blacklistWorldsString);
				warnParticle = config.getBoolean(protectionWarningSection + "." + warnParticleField, warnParticle);
				warnSound = config.getBoolean(protectionWarningSection + "." + warnSoundField, warnSound);
				warnMessage = config.getBoolean(protectionWarningSection + "." + warnMessageField, warnMessage);
				useDatabase = config.getBoolean(useDatabaseField, useDatabase);
				host = config.getString(hostField, host);
				port = config.getString(portField, port);
				database = config.getString(databaseField, database);
				username = config.getString(userField, username);
				password = config.getString(passField, password);
				ssl = config.getBoolean(sslField, ssl);
				useDynmap = config.getBoolean(useDynmapField, useDynmap);
				useBlueMap = config.getBoolean(useBlueMapField, useBlueMap);
				showPlotMarkersByDefault = config.getBoolean(showPlotsMarkersByDefaultField, showPlotMarkersByDefault);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void reload() {
		load();
	}

	public static boolean isAllowedPlotWorld(String world) {
		return !blacklistWorlds.contains(world);
	}

	public static boolean isAllowedPlotWorld(World world) {
		return world != null && !blacklistWorlds.contains(world.getName());
	}

	public static String getRootCommand() {
		return useAlias ? "pplot" : "plot";
	}
}
