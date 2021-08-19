package me.sword7.playerplot;

import me.sword7.playerplot.admin.CommandAllPlots;
import me.sword7.playerplot.admin.CommandDelPlot;
import me.sword7.playerplot.admin.CommandDelPlotCancel;
import me.sword7.playerplot.admin.CommandDelPlotConfirm;
import me.sword7.playerplot.config.*;
import me.sword7.playerplot.plot.*;
import me.sword7.playerplot.plotdeed.*;
import me.sword7.playerplot.user.UserCache;
import me.sword7.playerplot.util.AutoCompleteListener;
import me.sword7.playerplot.util.border.Border;
import me.sword7.playerplot.util.storage.DatabaseConnection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerPlot extends JavaPlugin {

    private static Plugin plugin;
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

        //initialize caches
        new PlotCache();
        new UserCache();

        //register commands
        getCommand("playerplot").setExecutor(new CommandPlayerPlot());
        getCommand(PluginConfig.getRootCommand()).setExecutor(new CommandPlot());
        getCommand("plotdeed").setExecutor(new CommandLoot(new PlotDeedLoot()));
        getCommand("toplot").setExecutor(new CommandToPlot());
        getCommand("writedeed").setExecutor(new CommandWriteDeed());
        getCommand("allplots").setExecutor(new CommandAllPlots());
        getCommand("delplot").setExecutor(new CommandDelPlot());
        getCommand("delplotconfirm").setExecutor(new CommandDelPlotConfirm());
        getCommand("delplotcancel").setExecutor(new CommandDelPlotCancel());

        //register listeners
        if (Version.hasAutoComplete()) new AutoCompleteListener();
        new ProtectionListener();
        new PlotListener();
        new PlotDeedListener();
        new PlotScanner();

    }

    @Override
    public void onDisable() {
        if (PluginBase.isDynmapDetected()) {
            PluginBase.getDynmap().shutdown();
        }
        UserCache.shutdown();
        PlotCache.shutdown();
        if (PluginConfig.isUsingDatabase()) DatabaseConnection.shutdown();
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


}
