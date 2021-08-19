package me.sword7.playerplot.config;

import me.sword7.playerplot.integration.Dynmap;
import me.sword7.playerplot.util.SendConsole;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

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
