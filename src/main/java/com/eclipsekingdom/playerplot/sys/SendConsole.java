package com.eclipsekingdom.playerplot.sys;

import org.bukkit.Bukkit;

public class SendConsole {

    private static final String namespace = "PlayerPlot";
    private static boolean debug = false;

    public static void info(String message) {
        Bukkit.getConsoleSender().sendMessage("[" + namespace + "] " + message);
    }

    public static void debug(String message) {
        if (debug) {
            Bukkit.getConsoleSender().sendMessage("[" + namespace + "] [DEBUG] " + message);
        }
    }

    public static void warn(String message) {
        Bukkit.getConsoleSender().sendMessage("[" + namespace + "] [WARN] " + message);
    }

}
