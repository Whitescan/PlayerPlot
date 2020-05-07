package com.eclipsekingdom.playerplot.sys.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class PluginConfig {

    private static File file = new File("plugins/PlayerPlot", "config.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    private static String languageFileString = "Language file";
    private static String languageFile = "en";

    private static String startingPlotNumString = "Starting Plot Amount";
    private static int startingPlotNum = 1;

    private static String maxPlotNumString = "Maximum Plot Amount";
    private static int maxPlotNum = 50;

    private static String unitSizeString = "Plot Unit Size";
    private static int unitSize = 25;

    private static String useDatabaseString = "Use database";
    private static boolean useDatabase = false;

    private static String hostString = "host";
    private static String host = "00.00.000.00";

    private static String portString = "port";
    private static String port = "3306";

    private static String databaseString = "database";
    private static String database = "myDatabase";

    private static String userString = "username";
    private static String user = "myUsername";

    private static String passString = "password";
    private static String pass = "myPassword";

    private static String sslString = "ssl";
    private static boolean ssl = false;

    private static String useDynmapString = "Use dynmap";
    private static boolean useDynmap = true;

    private static String showPlotsMarkersByDefaultString = "Show Plot Markers by Default";
    private static boolean showPlotMarkersByDefault = true;

    public PluginConfig() {
        load();
    }


    private void load() {
        if (file.exists()) {
            try {
                languageFile = config.getString(languageFileString, languageFile);
                startingPlotNum = config.getInt(startingPlotNumString, startingPlotNum);
                maxPlotNum = config.getInt(maxPlotNumString, maxPlotNum);
                unitSize = config.getInt(unitSizeString, unitSize);
                useDatabase = config.getBoolean(useDatabaseString, useDatabase);
                host = config.getString(hostString, host);
                port = config.getString(portString, port);
                database = config.getString(databaseString, database);
                user = config.getString(userString, user);
                pass = config.getString(passString, pass);
                ssl = config.getBoolean(sslString, ssl);
                useDynmap = config.getBoolean(useDynmapString, useDynmap);
                showPlotMarkersByDefault = config.getBoolean(showPlotsMarkersByDefaultString, showPlotMarkersByDefault);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    public static String getLanguageFile() {
        return languageFile;
    }

    public static boolean isUseDynmap() {
        return useDynmap;
    }

    public static boolean isShowPlotMarkersByDefault() {
        return showPlotMarkersByDefault;
    }

}
