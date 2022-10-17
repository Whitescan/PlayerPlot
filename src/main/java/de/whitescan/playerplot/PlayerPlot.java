package de.whitescan.playerplot;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.whitescan.playerplot.admin.CommandAllPlots;
import de.whitescan.playerplot.admin.CommandDelPlot;
import de.whitescan.playerplot.admin.CommandDelPlotCancel;
import de.whitescan.playerplot.admin.CommandDelPlotConfirm;
import de.whitescan.playerplot.border.Border;
import de.whitescan.playerplot.config.ConfigLoader;
import de.whitescan.playerplot.config.Language;
import de.whitescan.playerplot.config.PluginBase;
import de.whitescan.playerplot.config.PluginConfig;
import de.whitescan.playerplot.config.Version;
import de.whitescan.playerplot.plot.CommandPlot;
import de.whitescan.playerplot.plot.CommandToPlot;
import de.whitescan.playerplot.plot.PlotBeam;
import de.whitescan.playerplot.plot.PlotCache;
import de.whitescan.playerplot.plot.PlotListener;
import de.whitescan.playerplot.plot.PlotScanner;
import de.whitescan.playerplot.plot.ProtectionListener;
import de.whitescan.playerplot.plotdeed.CommandLoot;
import de.whitescan.playerplot.plotdeed.CommandWriteDeed;
import de.whitescan.playerplot.plotdeed.PlotDeedListener;
import de.whitescan.playerplot.plotdeed.PlotDeedLoot;
import de.whitescan.playerplot.plotdeed.PlotDeedType;
import de.whitescan.playerplot.storage.DatabaseConnection;
import de.whitescan.playerplot.user.UserCache;
import de.whitescan.playerplot.util.AutoCompleteListener;

public final class PlayerPlot extends JavaPlugin {

	private static Plugin plugin;
	private PlayerPlotAPI playerPlotAPI = PlayerPlotAPI.getInstance();

	@Override
	public void onEnable() {
		PlayerPlot.plugin = this;

		// configs
		ConfigLoader.load();
		new PluginConfig();

		// language and enums that use language
		Language.load();
		PlotDeedType.init();

		// load integrations
		new PluginBase();

		// initialize caches
		new PlotCache();
		new UserCache();

		// register commands
		getCommand("playerplot").setExecutor(new CommandPlayerPlot());
		getCommand(PluginConfig.getRootCommand()).setExecutor(new CommandPlot());
		getCommand("plotdeed").setExecutor(new CommandLoot(new PlotDeedLoot()));
		getCommand("toplot").setExecutor(new CommandToPlot());
		getCommand("writedeed").setExecutor(new CommandWriteDeed());
		getCommand("allplots").setExecutor(new CommandAllPlots());
		getCommand("delplot").setExecutor(new CommandDelPlot());
		getCommand("delplotconfirm").setExecutor(new CommandDelPlotConfirm());
		getCommand("delplotcancel").setExecutor(new CommandDelPlotCancel());

		// register listeners
		if (Version.hasAutoComplete())
			new AutoCompleteListener();
		new ProtectionListener();
		new PlotListener();
		new PlotDeedListener();
		new PlotScanner();

	}

	@Override
	public void onDisable() {
		if (PluginBase.isMapIntegrationEnabled()) {
			PluginBase.getMapIntegration().shutdown();
		}
		UserCache.shutdown();
		PlotCache.shutdown();
		if (PluginConfig.isUseDatabase())
			DatabaseConnection.shutdown();
		Border.shutdown();
		PlotBeam.shutdown();
	}

	public static void reload() {
		ConfigLoader.load();
		PluginConfig.reload();
		Language.reload();
		// TODO - update perm info with new maxPlots from reload (or only load in if has
		// override and compare greater on unlock)
		// TODO - add plot file/database reload once new storage system is implemented
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public PlayerPlotAPI getPlayerPlotAPI() {
		return playerPlotAPI;
	}

}
