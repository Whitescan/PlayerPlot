package com.eclipsekingdom.playerplot;

import com.eclipsekingdom.playerplot.data.Database;
import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.data.UserCache;
import com.eclipsekingdom.playerplot.data.event.DataLoadListener;
import com.eclipsekingdom.playerplot.loot.CommandPlotDeed;
import com.eclipsekingdom.playerplot.loot.PlotDeedListener;
import com.eclipsekingdom.playerplot.plot.CommandPlot;
import com.eclipsekingdom.playerplot.plot.CommandRPlot;
import com.eclipsekingdom.playerplot.plot.protection.PlotProtection;
import com.eclipsekingdom.playerplot.sys.PluginBase;
import com.eclipsekingdom.playerplot.sys.Version;
import com.eclipsekingdom.playerplot.sys.config.ConfigLoader;
import com.eclipsekingdom.playerplot.sys.config.PluginConfig;
import com.eclipsekingdom.playerplot.sys.lang.Language;
import com.eclipsekingdom.playerplot.util.AutoCompleteListener;
import com.eclipsekingdom.playerplot.util.scanner.PlotScanner;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerPlot extends JavaPlugin {

    private static Plugin plugin;
    private static Database database;

    @Override
    public void onEnable() {
        this.plugin = this;

        ConfigLoader.load();

        new PluginConfig();
        new Language();
        new PluginBase();

        if (PluginConfig.isUsingDatabase()) {
            this.database = new Database();
        }

        new PlotCache();
        new UserCache();

        getCommand("playerplot").setExecutor(new CommandPlayerPlot());
        getCommand("plot").setExecutor(new CommandPlot());
        getCommand("rplot").setExecutor(new CommandRPlot());
        getCommand("plotdeed").setExecutor(new CommandPlotDeed());

        if (Version.current.isAutoCompleteSupported()) {
            new AutoCompleteListener();
        }

        new PlotProtection();
        new PlotDeedListener();
        new PlotScanner();
        new DataLoadListener();

    }

    @Override
    public void onDisable() {
        if (PluginBase.isDynmapDetected()) {
            PluginBase.getDynmap().shutdown();
        }
        UserCache.save();
        PlotCache.save();
        PlotScanner.shutdown();
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static boolean isUsingDatabase() {
        return database != null && database.isInitialized();
    }

    public static Database getPlotDatabase() {
        return database;
    }

}
