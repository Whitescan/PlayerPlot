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

import static com.eclipsekingdom.playerplot.sys.Language.*;

public class PluginHelp {

    private static String rootCommand = PluginConfig.getRootCommand();

    public static void showTo(CommandSender sender) {

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "__Player Plot_______");
        sender.sendMessage(ChatColor.YELLOW + "-------" + ChatColor.GOLD + " " + LABEL_COMMANDS + " " + ChatColor.YELLOW + "-------");
        sendPlotCommands(sender);
        sender.sendMessage(ChatColor.GOLD + "- - - - - - -");
        sendPlotActionCommands(sender);
        sender.sendMessage(ChatColor.GOLD + "- - - - - - -");
        sendPlotDeedCommand(sender);
    }

    public static void showPlots(Player player) {
        UUID playerID = player.getUniqueId();
        UserData userData = UserCache.getData(playerID);
        PermInfo permInfo = UserCache.getPerms(playerID);
        int used = PlotCache.getPlayerPlotsUsed(playerID);
        int capacity = PluginConfig.getStartingPlotNum() + permInfo.getPlotBonus() + userData.getUnlockedPlots();
        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + LABEL_PLOTS + ChatColor.ITALIC + "" + ChatColor.DARK_PURPLE + " -  (" + used + "/" + capacity + ")");
        player.sendMessage(ChatColor.YELLOW + "-------" + ChatColor.GOLD + " " + LABEL_COMMANDS + " " + ChatColor.YELLOW + "-------");
        sendPlotCommands(player);
        player.sendMessage(ChatColor.GOLD + "- - - - - - -");
        sendPlotActionCommands(player);
    }


    private static void sendCommand(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    private static void sendPlotCommands(CommandSender sender) {
        sendCommand(sender, "&6/" + rootCommand + " scan: &f" + HELP_PLOT_SCAN.toString());
        sendCommand(sender, "&6/" + rootCommand + " claim &7([" + ARG_PLOT + "])&6: &f" + HELP_PLOT_CLAIM.toString());
        sendCommand(sender, "&6/" + rootCommand + " list: &f" + HELP_PLOT_LIST.toString());
        sendCommand(sender, "&6/" + rootCommand + " flist: &f" + HELP_PLOT_FLIST.toString());
        sendCommand(sender, "&6/toplot &c[plot]&6: &f" + HELP_TOPLOT.toString());
    }


    private static void sendPlotActionCommands(CommandSender sender) {
        sendCommand(sender, "&6/" + rootCommand + " &7(@[" + ARG_PLOT + "])&6 rename &c[" + ARG_NAME + "]&6: &f" + HELP_PLOT_RENAME.toString());
        sendCommand(sender, "&6/" + rootCommand + " &7(@[" + ARG_PLOT + "])&6 free: &f" + HELP_PLOT_FREE.toString());
        sendCommand(sender, "&6/" + rootCommand + " &7(@[" + ARG_PLOT + "])&6 info: &f" + HELP_PLOT_INFO.toString());
        sendCommand(sender, "&6/" + rootCommand + " &7(@[" + ARG_PLOT + "])&6 trust &c[" + ARG_PLAYER + "]&6: &f" + HELP_PLOT_TRUST.toString());
        sendCommand(sender, "&6/" + rootCommand + " &7(@[" + ARG_PLOT + "])&6 untrust &c[" + ARG_PLAYER + "]&6: &f" + HELP_PLOT_UNTRUST.toString());
        sendCommand(sender, "&6/" + rootCommand + " &7(@[" + ARG_PLOT + "])&6 upgrade: &f" + HELP_PLOT_UPGRADE.toString());
        sendCommand(sender, "&6/" + rootCommand + " &7(@[" + ARG_PLOT + "])&6 downgrade: &f" + HELP_PLOT_DOWNGRADE.toString());
        sendCommand(sender, "&6/" + rootCommand + " &7(@[" + ARG_PLOT + "])&6 setcenter: &f" + HELP_PLOT_SET_CENTER.toString());
        sendCommand(sender, "&6/" + rootCommand + " &7(@[" + ARG_PLOT + "])&6 setspawn: &f" + HELP_PLOT_SET_SPAWN.toString());
    }


    private static void sendPlotDeedCommand(CommandSender sender) {
        sendCommand(sender, "&6/plotdeed &c[" + ARG_PLAYER + "] [" + ARG_AMOUNT + "]&6: &f" + HELP_PLOT_DEED.toString());
    }

}
