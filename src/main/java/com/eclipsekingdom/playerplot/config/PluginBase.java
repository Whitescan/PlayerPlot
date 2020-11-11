package com.eclipsekingdom.playerplot.config;

import com.eclipsekingdom.playerplot.integration.Dynmap;
import com.eclipsekingdom.playerplot.integration.PlayerPlotHero;
import com.eclipsekingdom.playerplot.util.SendConsole;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import static com.eclipsekingdom.playerplot.config.Language.CONSOLE_DETECT;

public class PluginBase {

    private static String dynmapNameSpace = "dynmap";
    private static String apiHeroNameSpace = "ApiHero";

    private static Dynmap dynmap;
    private static boolean dynmapDetected = false;

    public PluginBase() {
        loadDependencies();
    }

    public void loadDependencies() {
        if (PluginConfig.isUseDynmap()) {
            loadDynmap();
            loadApiHero();
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

    private void loadApiHero() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(apiHeroNameSpace);
        if (plugin != null && plugin.isEnabled()) {
            new PlayerPlotHero();
            SendConsole.info(CONSOLE_DETECT.fromPlugin(apiHeroNameSpace));
        }
    }

    public static Dynmap getDynmap() {
        return dynmap;
    }

    public static boolean isDynmapDetected() {
        return dynmapDetected;
    }
}
