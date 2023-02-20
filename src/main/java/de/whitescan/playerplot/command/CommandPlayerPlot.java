package de.whitescan.playerplot.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import de.whitescan.playerplot.PlayerPlot;
import de.whitescan.playerplot.config.Language;
import de.whitescan.playerplot.config.Permissions;
import de.whitescan.playerplot.config.Version;
import de.whitescan.playerplot.util.Help;

public class CommandPlayerPlot implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

		if (args.length > 0) {
			String sub = args[0].toLowerCase();
			if (sub.equals("help")) {
				Help.sendTo(sender);
			} else if (sub.equals("info")) {
				showInfo(sender);
			} else if (sub.equals("reload")) {
				processReload(sender);
			} else {
				showOverview(sender);
			}
		} else {
			showOverview(sender);
		}

		return true;
	}

	private void showInfo(CommandSender sender) {
		PluginDescriptionFile descriptionFile = PlayerPlot.getPlugin().getDescription();
		sender.sendMessage("");
		sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "__Player Plot_______");
		sender.sendMessage(ChatColor.GRAY + Language.PLUGIN_AUTHOR.toString() + ": " + ChatColor.WHITE
				+ descriptionFile.getAuthors().get(0));
		sender.sendMessage(ChatColor.GRAY + Language.PLUGIN_VERSION.toString() + ": " + ChatColor.WHITE
				+ descriptionFile.getVersion());
	}

	private void processReload(CommandSender sender) {
		if (Permissions.canReload(sender)) {
			PlayerPlot.reload();
			sender.sendMessage(
					ChatColor.LIGHT_PURPLE + "[PlayerPlot] " + ChatColor.GRAY + Language.PLUGIN_RELOAD.toString());
		} else {
			sender.sendMessage(ChatColor.RED + Language.WARN_NOT_PERMITTED.toString());
		}
	}

	private void showOverview(CommandSender sender) {
		sender.sendMessage("");
		sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "__Player Plot_______");
		sender.sendMessage(ChatColor.GRAY + Language.PLUGIN_DESCRIPTION.toString());
		sender.sendMessage("");

		if (Version.hasBungeeChat()) {
			sender.spigot().sendMessage(Language.PLUGIN_READ_MORE.getWithLink(ChatColor.GRAY,
					Language.PLUGIN_WIKI.toString(), "https://gitlab.com/sword7/playerplot/-/wikis/home"));
		} else {
			sender.sendMessage(ChatColor.GRAY
					+ Language.PLUGIN_READ_MORE.toString().replaceAll("\\[link\\]", Language.PLUGIN_WIKI.toString())
					+ " " + ChatColor.AQUA + "https://gitlab.com/sword7/playerplot/-/wikis/home");
		}
		sender.sendMessage(
				ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "------- " + Language.PLUGIN_OPTIONS + " -------");
		sender.sendMessage(ChatColor.LIGHT_PURPLE + "/playerplot help: " + ChatColor.WHITE + ChatColor.ITALIC
				+ Language.HELP_PLAYERPLOT_HELP);
		sender.sendMessage(ChatColor.LIGHT_PURPLE + "/playerplot info: " + ChatColor.WHITE + ChatColor.ITALIC
				+ Language.HELP_PLAYERPLOT_INFO);
		if (Permissions.canReload(sender)) {
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "/playerplot reload: " + ChatColor.WHITE + ChatColor.ITALIC
					+ Language.HELP_PLAYERPLOT_RELOAD);
		}
	}

}
