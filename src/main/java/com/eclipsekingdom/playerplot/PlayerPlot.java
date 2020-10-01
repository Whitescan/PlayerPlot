package com.eclipsekingdom.playerplot;

import com.eclipsekingdom.playerplot.data.Database;
import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.data.UserCache;
import com.eclipsekingdom.playerplot.data.event.DataLoadListener;
import com.eclipsekingdom.playerplot.loot.CommandLoot;
import com.eclipsekingdom.playerplot.loot.PlotDeedListener;
import com.eclipsekingdom.playerplot.loot.PlotDeedLoot;
import com.eclipsekingdom.playerplot.loot.PlotDeedType;
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

        //configs
        ConfigLoader.load();
        new PluginConfig();

        //language and enums that use language
        Language.load();
        PlotDeedType.init();

        //load integrations
        new PluginBase();
        if (PluginConfig.isUsingDatabase()) {
            this.database = new Database();
        }

        //initialize caches
        new PlotCache();
        new UserCache();

        //register commands
        getCommand("playerplot").setExecutor(new CommandPlayerPlot());
        getCommand(PluginConfig.getRootCommand()).setExecutor(new CommandPlot());
        getCommand("plotdeed").setExecutor(new CommandLoot(new PlotDeedLoot()));
        getCommand("toplot").setExecutor(new CommandToPlot());

        //register listeners
        if (Version.hasAutoComplete()) new AutoCompleteListener();
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
