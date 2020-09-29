package com.eclipsekingdom.playerplot.sys;

import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.data.UserCache;
import com.eclipsekingdom.playerplot.data.UserData;
import com.eclipsekingdom.playerplot.sys.config.PluginConfig;
import com.eclipsekingdom.playerplot.util.PermInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PluginHelp {

    private static String rootCommand = PluginConfig.getRootCommand();

    public static void showTo(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "__Player Plot_______");
        sender.sendMessage(ChatColor.GRAY.toString() + "() = " + Language.MISC_OPTIONAL + " " + ChatColor.DARK_PURPLE + "[] = " + Language.MISC_VARIABLE);
        sender.sendMessage(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "------- " + Language.LABEL_COMMANDS + " -------");
        sendPlotCommands(sender);
        sender.sendMessage(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "------------------");
        sendPlotActionCommands(sender);
        sender.sendMessage(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "------------------");
        sendPlotDeedCommand(sender);
        sender.sendMessage("");
    }

    public static void showPlots(Player player) {
        UUID playerID = player.getUniqueId();
        UserData userData = UserCache.getData(playerID);
        PermInfo permInfo = UserCache.getPerms(playerID);
        int used = PlotCache.getPlayerPlotsUsed(playerID);
        int capacity = PluginConfig.getStartingPlotNum() + permInfo.getPlotBonus() + userData.getUnlockedPlots();
        player.sendMessage("");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + Language.LABEL_PLOTS + ChatColor.ITALIC + "" + ChatColor.DARK_PURPLE + " -  (" + used + "/" + capacity + ")");
        player.sendMessage(ChatColor.GRAY.toString() + "() = " + Language.MISC_OPTIONAL + " " + ChatColor.DARK_PURPLE + "[] = " + Language.MISC_VARIABLE);
        player.sendMessage(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "------- " + Language.LABEL_COMMANDS + " -------");
        sendPlotCommands(player);
        player.sendMessage(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "------------------");
        sendPlotActionCommands(player);
        player.sendMessage("");
    }


    private static void sendCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    private static void sendPlotCommands(CommandSender sender) {
        sendCommand(sender, "&d/" + rootCommand);
        sendCommand(sender, "&d  > scan: &f&o" + Language.HELP_PLOT_SCAN.toString());
        sendCommand(sender, "&d  > claim &7([" + Language.ARG_PLOT + "])&d: &f&o" + Language.HELP_PLOT_CLAIM.toString());
        sendCommand(sender, "&d  > list: &f&o" + Language.HELP_PLOT_LIST.toString());
        sendCommand(sender, "&d  > flist: &f&o" + Language.HELP_PLOT_FLIST.toString());
        sendCommand(sender, "&d/toplot &5[plot]&d: &f&o" + Language.HELP_TOPLOT.toString());
    }


    private static void sendPlotActionCommands(CommandSender sender) {
        sendCommand(sender, "&d/" + rootCommand + " &7(@[" + Language.ARG_PLOT + "])");
        sendCommand(sender, "&d  > rename &5[" + Language.ARG_NAME + "]&d: &f&o" + Language.HELP_PLOT_RENAME.toString());
        sendCommand(sender, "&d  > free: &f&o" + Language.HELP_PLOT_FREE.toString());
        sendCommand(sender, "&d  > info: &f&o" + Language.HELP_PLOT_INFO.toString());
        sendCommand(sender, "&d  > trust &5[" + Language.ARG_PLAYER + "]&d: &f&o" + Language.HELP_PLOT_TRUST.toString());
        sendCommand(sender, "&d  > untrust &5[" + Language.ARG_PLAYER + "]&d: &f&o" + Language.HELP_PLOT_UNTRUST.toString());
        sendCommand(sender, "&d  > upgrade: &f&o" + Language.HELP_PLOT_UPGRADE.toString());
        sendCommand(sender, "&d  > downgrade: &f&o" + Language.HELP_PLOT_DOWNGRADE.toString());
        sendCommand(sender, "&d  > setcenter: &f&o" + Language.HELP_PLOT_SET_CENTER.toString());
        sendCommand(sender, "&d  > setspawn: &f&o" + Language.HELP_PLOT_SET_SPAWN.toString());
    }


    private static void sendPlotDeedCommand(CommandSender sender) {
        sendCommand(sender, "&d/plotdeed &5[" + Language.ARG_PLAYER + "] [" + Language.ARG_AMOUNT + "]&d: &f&o" + Language.HELP_PLOT_DEED.toString());
    }

}
