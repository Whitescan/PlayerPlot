package de.whitescan.playerplot.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.whitescan.playerplot.PlayerPlot;
import de.whitescan.playerplot.config.Language;
import de.whitescan.playerplot.config.Permissions;
import de.whitescan.playerplot.config.PluginBase;
import de.whitescan.playerplot.integration.MapIntegration;
import de.whitescan.playerplot.logic.Plot;
import de.whitescan.playerplot.plot.PlotCache;

public class CommandDelPlotConfirm implements CommandExecutor {

	private boolean usingDynmap = PluginBase.isMapIntegrationEnabled();
	private MapIntegration dynmap = PluginBase.getMapIntegration();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (Permissions.canDeletePlots(player)) {
				if (PlayerPlot.hasPending(player)) {
					Plot plot = PlayerPlot.getPending(player).getPlot();
					PlayerPlot.remove(player);
					PlotCache.removePlot(plot);
					if (usingDynmap)
						dynmap.deletePlot(plot);
					player.sendMessage(ChatColor.LIGHT_PURPLE + Language.SUCCESS_PLOT_DELETE.toString());
				} else {
					player.sendMessage(ChatColor.RED + Language.WARN_NO_DELETE_REQUESTS.toString());
				}
			} else {
				player.sendMessage(ChatColor.RED + Language.WARN_NOT_PERMITTED.toString());
			}
		}

		return false;
	}

}
