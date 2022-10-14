package de.whitescan.playerplot.admin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.whitescan.playerplot.config.Language;
import de.whitescan.playerplot.config.Permissions;
import de.whitescan.playerplot.config.Version;
import de.whitescan.playerplot.plot.Plot;
import de.whitescan.playerplot.plot.PlotCache;
import de.whitescan.playerplot.util.Scheduler;

import java.util.UUID;

public class CommandDelPlot implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (Permissions.canDeletePlots(player)) {
				Plot plot = PlotCache.getPlot(player.getLocation());
				if (plot != null) {
					DeleteRequests.Request request = DeleteRequests.add(player, plot);
					UUID requestId = request.getId();
					if (Version.hasBungeeChat()) {
						player.spigot()
								.sendMessage(Language.INFO_CONFIRM_DELETE.getWithPlayerConfirmDeny(
										ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE, plot.getOwnerName(),
										"/delplotconfirm", "/delplotcancel"));
					} else {
						player.sendMessage(Language.INFO_CONFIRM_DELETE_LEGACY.coloredFromPlayer(plot.getOwnerName(),
								ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE));
						player.sendMessage(ChatColor.GRAY + Language.INFO_CONFIRM_DELETE_LEGACY_TIP.toString()
								.replaceAll("\\[confirm\\]", ChatColor.GREEN + "/delplotconfirm" + ChatColor.GRAY)
								.replaceAll("\\[cancel\\]", ChatColor.RED + "/delplotcancel" + ChatColor.GRAY));
					}
					player.sendMessage(ChatColor.GRAY + Language.INFO_REQUEST_DURATION.fromSeconds(25));
					Scheduler.runLater(() -> {
						if (DeleteRequests.hasPending(player)) {
							DeleteRequests.Request currentRequest = DeleteRequests.getPending(player);
							if (currentRequest.getId().equals(requestId)) {
								DeleteRequests.remove(player);
							}
						}
					}, 20 * 25);
				} else {
					player.sendMessage(ChatColor.RED + Language.WARN_NOT_STANDING_IN_PLOT.toString());
				}
			} else {
				player.sendMessage(ChatColor.RED + Language.WARN_NOT_PERMITTED.toString());
			}
		}
		return false;

	}

}
