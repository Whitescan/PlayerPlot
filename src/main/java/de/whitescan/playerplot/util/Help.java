package de.whitescan.playerplot.util;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.whitescan.playerplot.config.Language;
import de.whitescan.playerplot.config.Permissions;
import de.whitescan.playerplot.config.PluginConfig;
import de.whitescan.playerplot.listener.UserCache;
import de.whitescan.playerplot.logic.UserData;
import de.whitescan.playerplot.plot.PlotCache;

public class Help {

	public static void sendTo(CommandSender sender) {
		sender.sendMessage("");
		sender.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "__Player Plot_______");
		sender.sendMessage(
				ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "------- " + Language.LABEL_COMMANDS + " -------");
		sender.sendMessage(ChatColor.GRAY.toString() + "() = " + Language.MISC_OPTIONAL + "  " + ChatColor.DARK_PURPLE
				+ "[] = " + Language.MISC_VARIABLE);
		sendPlotCommands(sender);
		sender.sendMessage(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "------------------------");
		sendPlotActionCommands(sender);
		if (Permissions.canSummon(sender) || Permissions.canDeletePlots(sender)
				|| Permissions.canViewAllPlots(sender)) {
			sender.sendMessage(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "------------------------");
			if (Permissions.canViewAllPlots(sender)) {
				sendCommand(sender, "&d/allplots: &f" + Language.HELP_ALL_PLOTS.toString());
			}
			if (Permissions.canDeletePlots(sender)) {
				sendCommand(sender, "&d/delplot: &f" + Language.HELP_DEL_PLOT.toString());
			}
			if (Permissions.canSummon(sender)) {
				sendCommand(sender, "&d/plotdeed &5[" + Language.ARG_PLOT_DEED + "] [" + Language.ARG_PLAYER + "] ["
						+ Language.ARG_AMOUNT + "]&d: &f" + Language.HELP_PLOT_DEED.toString());
			}
		}

	}

	public static void sendPlots(Player player) {
		UUID playerID = player.getUniqueId();
		UserData userData = UserCache.getData(playerID);
		PermInfo permInfo = UserCache.getPerms(playerID);
		int used = PlotCache.getPlayerPlotsUsed(playerID);
		int startingNum = PluginConfig.getStartingPlotNum();
		int permNum = permInfo.getPlotBonus();
		int unlockedNum = userData.getUnlockedPlots();
		int capacity = startingNum + permNum + unlockedNum;
		player.sendMessage("");
		player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + Language.LABEL_PLOTS + ChatColor.ITALIC + ""
				+ ChatColor.DARK_PURPLE + " -  (" + used + "/" + ChatColor.AQUA + capacity + ChatColor.DARK_PURPLE
				+ ")");
		String capacityComponents = "";
		if (startingNum > 0)
			capacityComponents += ChatColor.GRAY + "starting: " + ChatColor.AQUA + startingNum + " ";
		if (unlockedNum > 0)
			capacityComponents += ChatColor.GRAY + "unlocked: " + ChatColor.AQUA + unlockedNum + " ";
		if (permNum > 0)
			capacityComponents += ChatColor.GRAY + "permission: " + ChatColor.AQUA + permNum + " ";
		player.sendMessage(capacityComponents);
		player.sendMessage(
				ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "------- " + Language.LABEL_COMMANDS + " -------");
		player.sendMessage(ChatColor.GRAY.toString() + "() = " + Language.MISC_OPTIONAL + "  " + ChatColor.DARK_PURPLE
				+ "[] = " + Language.MISC_VARIABLE);
		sendPlotCommands(player);
		player.sendMessage(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "------------------------");
		sendPlotActionCommands(player);
	}

	private static void sendCommand(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	private static void sendPlotCommands(CommandSender sender) {
		String rootCommand = PluginConfig.getRootCommand();
		sendCommand(sender, "&d/" + rootCommand);
		sendCommand(sender, "&d  > scan: &f" + Language.HELP_PLOT_SCAN.toString());
		sendCommand(sender, "&d  > claim &7([" + Language.ARG_PLOT + "])&d: &f" + Language.HELP_PLOT_CLAIM.toString());
		sendCommand(sender, "&d  > list: &f" + Language.HELP_PLOT_LIST.toString());
		sendCommand(sender, "&d  > flist: &f" + Language.HELP_PLOT_FLIST.toString());
		sendCommand(sender, "&d/toplot &5[plot]&d: &f" + Language.HELP_TOPLOT.toString());
		sendCommand(sender, "&d/writedeed &5[" + Language.ARG_AMOUNT + "]&d: &f" + Language.HELP_WRITE_DEED.toString());
	}

	private static void sendPlotActionCommands(CommandSender sender) {
		String rootCommand = PluginConfig.getRootCommand();
		sendCommand(sender, "&d/" + rootCommand + " &7(@[" + Language.ARG_PLOT + "])");
		sendCommand(sender, "&d  > rename &5[" + Language.ARG_NAME + "]&d: &f" + Language.HELP_PLOT_RENAME.toString());
		sendCommand(sender, "&d  > free: &f" + Language.HELP_PLOT_FREE.toString());
		sendCommand(sender, "&d  > info: &f" + Language.HELP_PLOT_INFO.toString());
		sendCommand(sender, "&d  > trust &5[" + Language.ARG_PLAYER + "]&d: &f" + Language.HELP_PLOT_TRUST.toString());
		sendCommand(sender,
				"&d  > untrust &5[" + Language.ARG_PLAYER + "]&d: &f" + Language.HELP_PLOT_UNTRUST.toString());
		sendCommand(sender, "&d  > upgrade: &f" + Language.HELP_PLOT_UPGRADE.toString());
		sendCommand(sender, "&d  > downgrade: &f" + Language.HELP_PLOT_DOWNGRADE.toString());
		sendCommand(sender, "&d  > setcenter: &f" + Language.HELP_PLOT_SET_CENTER.toString());
		sendCommand(sender, "&d  > setspawn: &f" + Language.HELP_PLOT_SET_SPAWN.toString());
	}

}
