package com.eclipsekingdom.playerplot.sys;

import com.eclipsekingdom.playerplot.sys.config.PluginConfig;
import com.eclipsekingdom.playerplot.util.Dynmap;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import static com.eclipsekingdom.playerplot.sys.Language.CONSOLE_DETECT;

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
            SendConsole.info(CONSOLE_DETECT.fromPlugin(dynmapNameSpace));
        }
    }

    public static Dynmap getDynmap() {
        return dynmap;
    }

    public static boolean isDynmapDetected() {
        return dynmapDetected;
    }
}
