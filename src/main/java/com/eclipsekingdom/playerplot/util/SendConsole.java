package com.eclipsekingdom.playerplot.util;

import com.eclipsekingdom.playerplot.PlayerPlot;

import java.util.logging.Logger;

public class SendConsole {

    private static Logger logger = PlayerPlot.getPlugin().getLogger();
    private static boolean debug = false;

    public static void info(String message) {
        logger.info(message);
    }

    public static void debug(String message) {
        if (debug) {
            logger.info("[DEBUG] " + message);
        }
    }

    public static void warn(String message) {
        logger.warning("[WARN] " + message);
    }

    public static void error(String message) {
        logger.warning("[ERROR] " + message);
    }

}
