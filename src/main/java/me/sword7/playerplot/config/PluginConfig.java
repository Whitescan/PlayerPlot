package me.sword7.playerplot.config;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PluginConfig {

    private static String languageFileField = "Language file";
    private static String languageFile = "en";

    private static String useAliasField = "Use /pplot root";
    private static boolean useAlias = false;

    private static String startingPlotNumField = "Starting Plot Amount";
    private static int startingPlotNum = 1;

    private static String maxPlotNumField = "Maximum Plot Amount";
    private static int maxPlotNum = 50;

    private static String unitSizeField = "Plot Unit Size";
    private static int unitSize = 25;

    private static String plotPvpField = "Plot pvp";
    private static boolean plotPvp = true;

    private static String blacklistWorldsString = "Plot world blacklist";
    private static List<String> blacklistWorlds = buildBlacklistWorld();

    private static List<String> buildBlacklistWorld() {
        List<String> blacklistWorlds = new ArrayList<>();
        blacklistWorlds.add("example-world");
        return blacklistWorlds;
    }

    private static String protectionWarningSection = "Protection warning";
    private static String warnParticleField = "particle";
    private static boolean warnParticle = true;
    private static String warnSoundField = "sound";
    private static boolean warnSound = true;
    private static String warnMessageField = "message";
    private static boolean warnMessage = true;

    private static String useDatabaseField = "Use database";
    private static boolean useDatabase = false;

    private static String hostField = "host";
    private static String host = "00.00.000.00";

    private static String portField = "port";
    private static String port = "3306";

    private static String databaseField = "database";
    private static String database = "myDatabase";

    private static String userField = "username";
    private static String user = "myUsername";

    private static String passField = "password";
    private static String pass = "myPassword";

    private static String sslField = "ssl";
    private static boolean ssl = false;

    private static String useDynmapField = "Use dynmap";
    private static boolean useDynmap = true;

    private static String showPlotsMarkersByDefaultField = "Show Plot Markers by Default";
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
                user = config.getString(userField, user);
                pass = config.getString(passField, pass);
                ssl = config.getBoolean(sslField, ssl);
                useDynmap = config.getBoolean(useDynmapField, useDynmap);
                showPlotMarkersByDefault = config.getBoolean(showPlotsMarkersByDefaultField, showPlotMarkersByDefault);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void reload() {
        load();
    }

    public static String getLanguageFile() {
        return languageFile;
    }

    public static boolean isUseAlias() {
        return useAlias;
    }

    public static String getRootCommand() {
        return useAlias ? "pplot" : "plot";
    }

    public static int getStartingPlotNum() {
        return startingPlotNum;
    }

    public static int getMaxPlotNum() {
        return maxPlotNum;
    }

    public static int getPlotUnitSideLength() {
        return unitSize;
    }

    public static boolean isPlotPvp() {
        return plotPvp;
    }

    public static boolean isAllowedPlotWorld(String world) {
        return !blacklistWorlds.contains(world);
    }

    public static boolean isAllowedPlotWorld(World world) {
        return world != null && !blacklistWorlds.contains(world.getName());
    }


    public static boolean isWarnParticle() {
        return warnParticle;
    }

    public static boolean isWarnSound() {
        return warnSound;
    }

    public static boolean isWarnMessage() {
        return warnMessage;
    }

    public static boolean isUsingDatabase() {
        return useDatabase;
    }

    public static String getHost() {
        return host;
    }

    public static String getPort() {
        return port;
    }

    public static String getDatabase() {
        return database;
    }

    public static String getUsername() {
        return user;
    }

    public static String getPassword() {
        return pass;
    }

    public static boolean isSsl() {
        return ssl;
    }

    public static boolean isUseDynmap() {
        return useDynmap;
    }

    public static boolean isShowPlotMarkersByDefault() {
        return showPlotMarkersByDefault;
    }

}
