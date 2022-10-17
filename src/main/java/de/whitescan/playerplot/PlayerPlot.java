package de.whitescan.playerplot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.whitescan.playerplot.border.Border;
import de.whitescan.playerplot.command.CommandAllPlots;
import de.whitescan.playerplot.command.CommandDelPlot;
import de.whitescan.playerplot.command.CommandDelPlotCancel;
import de.whitescan.playerplot.command.CommandDelPlotConfirm;
import de.whitescan.playerplot.command.CommandLoot;
import de.whitescan.playerplot.command.CommandPlayerPlot;
import de.whitescan.playerplot.command.CommandPlot;
import de.whitescan.playerplot.command.CommandToPlot;
import de.whitescan.playerplot.command.CommandWriteDeed;
import de.whitescan.playerplot.config.ConfigLoader;
import de.whitescan.playerplot.config.Language;
import de.whitescan.playerplot.config.PluginBase;
import de.whitescan.playerplot.config.PluginConfig;
import de.whitescan.playerplot.config.Version;
import de.whitescan.playerplot.database.DatabaseConnection;
import de.whitescan.playerplot.listener.AutoCompleteListener;
import de.whitescan.playerplot.listener.PlotDeedListener;
import de.whitescan.playerplot.listener.PlotListener;
import de.whitescan.playerplot.listener.PlotScanner;
import de.whitescan.playerplot.listener.ProtectionListener;
import de.whitescan.playerplot.listener.UserCache;
import de.whitescan.playerplot.logic.Plot;
import de.whitescan.playerplot.logic.PlotDeedLoot;
import de.whitescan.playerplot.logic.PlotDeedType;
import de.whitescan.playerplot.logic.Request;
import de.whitescan.playerplot.plot.PlotBeam;
import de.whitescan.playerplot.plot.PlotCache;
import lombok.Getter;

public final class PlayerPlot extends JavaPlugin {

	// Setup

	@Getter
	private static Plugin plugin;

	@Getter
	private PlayerPlotAPI playerPlotAPI = PlayerPlotAPI.getInstance();

	private PlotCache plotCache;

	// Runtime

	private static Map<UUID, Request> playerToRequest = new HashMap<>();

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
		this.plotCache = new PlotCache();
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
		plotCache.shutdown();
		if (PluginConfig.isUseDatabase())
			DatabaseConnection.shutdown();
		Border.shutdown();
		PlotBeam.shutdown();
	}

	public static void reload() {
		ConfigLoader.load();
		PluginConfig.reload();
		Language.reload();
	}

	public static Request add(Player player, Plot plot) {
		Request request = new Request(plot);
		playerToRequest.put(player.getUniqueId(), request);
		return request;
	}

	public static void remove(Player player) {
		playerToRequest.remove(player.getUniqueId());
	}

	public static boolean hasPending(Player player) {
		return playerToRequest.containsKey(player.getUniqueId());
	}

	public static Request getPending(Player player) {
		return playerToRequest.get(player.getUniqueId());
	}

}
