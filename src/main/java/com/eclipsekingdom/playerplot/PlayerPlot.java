package com.eclipsekingdom.playerplot;

import com.eclipsekingdom.playerplot.data.Database;
import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.data.UserCache;
import com.eclipsekingdom.playerplot.data.event.DataLoadListener;
import com.eclipsekingdom.playerplot.loot.CommandPlotDeed;
import com.eclipsekingdom.playerplot.loot.PlotDeedListener;
import com.eclipsekingdom.playerplot.plot.*;
import com.eclipsekingdom.playerplot.sys.Language;
import com.eclipsekingdom.playerplot.sys.PluginBase;
import com.eclipsekingdom.playerplot.sys.Version;
import com.eclipsekingdom.playerplot.sys.config.ConfigLoader;
import com.eclipsekingdom.playerplot.sys.config.PluginConfig;
import com.eclipsekingdom.playerplot.util.AutoCompleteListener;
import com.eclipsekingdom.playerplot.util.border.Border;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerPlot extends JavaPlugin {

    private static Plugin plugin;
    private static Database database;
    private PlayerPlotAPI playerPlotAPI = PlayerPlotAPI.getInstance();

    @Override
    public void onEnable() {
        this.plugin = this;

        ConfigLoader.load();

        new PluginConfig();
        Language.load();
        new PluginBase();

        if (PluginConfig.isUsingDatabase()) {
            this.database = new Database();
        }

        new PlotCache();
        new UserCache();

        getCommand("playerplot").setExecutor(new CommandPlayerPlot());
        getCommand(PluginConfig.getRootCommand()).setExecutor(new CommandPlot());
        getCommand("plotdeed").setExecutor(new CommandPlotDeed());
        getCommand("toplot").setExecutor(new CommandToPlot());

        if (Version.hasAutoComplete()) {
            new AutoCompleteListener();
        }

        new PlotProtection();
        new PlotListener();
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
        Border.shutdown();
        PlotBeam.shutdown();
    }

    public static void reload() {
        ConfigLoader.load();
        PluginConfig.reload();
        Language.reload();
        //TODO - update perm info with new maxPlots from reload (or only load in if has override and compare greater on unlock)
        //TODO - add plot file/database reload once new storage system is implemented
    }


    public static Plugin getPlugin() {
        return plugin;
    }

    public PlayerPlotAPI getPlayerPlotAPI() {
        return playerPlotAPI;
    }

    public static boolean isUsingDatabase() {
        return database != null && database.isInitialized();
    }

    public static Database getPlotDatabase() {
        return database;
    }

}
