package de.whitescan.playerplot.admin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.whitescan.playerplot.config.Language;
import de.whitescan.playerplot.config.Permissions;

public class CommandDelPlotCancel implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (Permissions.canDeletePlots(player)) {
				if (DeleteRequests.hasPending(player)) {
					DeleteRequests.remove(player);
					player.sendMessage(ChatColor.LIGHT_PURPLE + Language.SUCCESS_REQUEST_CANCEL.toString());
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
