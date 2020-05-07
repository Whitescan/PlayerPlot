package com.eclipsekingdom.playerplot.sys;

import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.data.UserCache;
import com.eclipsekingdom.playerplot.data.UserData;
import com.eclipsekingdom.playerplot.sys.config.PluginConfig;
import com.eclipsekingdom.playerplot.util.PermInfo;
import com.google.common.collect.ImmutableMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

import static com.eclipsekingdom.playerplot.sys.lang.Message.*;

public class PluginHelp {

    public static void showTo(CommandSender sender) {

        sender.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Player Plot");
        sender.sendMessage(ChatColor.YELLOW + "-------" + ChatColor.GOLD + " " + LABEL_COMMANDS + " " + ChatColor.YELLOW + "-------");
        for (Map.Entry<String, String> entry : plotCommandToDescription.entrySet()) {
            sender.sendMessage(getFormatted(entry));
        }

        sender.sendMessage(ChatColor.GOLD + "- - - - - - -");
        for (Map.Entry<String, String> entry : remotePlotCommandToDescription.entrySet()) {
            sender.sendMessage(getFormatted(entry));
        }

        sender.sendMessage(ChatColor.GOLD + "- - - - - - -");
        if (Permissions.canSummonPlotDeed(sender)) {
            for (Map.Entry<String, String> entry : plotdeedCommandToDescription.entrySet()) {
                sender.sendMessage(getFormatted(entry));
            }
        }

    }

    public static void showPlots(Player player) {
        UUID playerID = player.getUniqueId();
        UserData userData = UserCache.getData(playerID);
        PermInfo permInfo = UserCache.getPerms(playerID);
        int used = PlotCache.getPlayerPlotsUsed(playerID);
        int capacity = PluginConfig.getStartingPlotNum() + permInfo.getPlotBonus() + userData.getUnlockedPlots();
        player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + LABEL_PLOTS + ChatColor.ITALIC + "" + ChatColor.DARK_PURPLE + " -  (" + used + "/" + capacity + ")");
        player.sendMessage(ChatColor.YELLOW + "-------" + ChatColor.GOLD + " " + LABEL_COMMANDS + " " + ChatColor.YELLOW + "-------");
        for (Map.Entry<String, String> entry : plotCommandToDescription.entrySet()) {
            player.sendMessage(getFormatted(entry));
        }
        player.sendMessage(ChatColor.GOLD + "- - - - - - -");
        for (Map.Entry<String, String> entry : remotePlotCommandToDescription.entrySet()) {
            player.sendMessage(getFormatted(entry));
        }
    }

    private static String getFormatted(Map.Entry<String, String> commandToDescription) {
        String commandComponent = ChatColor.GOLD + "/" + commandToDescription.getKey();
        commandComponent = commandComponent.replace("[", ChatColor.RED + "[");
        commandComponent = commandComponent.replace("]", "]" + ChatColor.GOLD);

        String descriptionComponent = ChatColor.RESET + commandToDescription.getValue();
        descriptionComponent = descriptionComponent.replace("[", ChatColor.RED + "[");
        descriptionComponent = descriptionComponent.replace("]", "]" + ChatColor.RESET);

        return commandComponent + ": " + descriptionComponent;
    }

    private static ImmutableMap<String, String> plotCommandToDescription = new ImmutableMap.Builder<String, String>()
            .put("plot scan", HELP_PLOT_SCAN.toString())
            .put("plot claim [plot]", HELP_PLOT_CLAIM.toString())
            .put("plot rename [plot]", HELP_PLOT_RENAME.toString())
            .put("plot free", HELP_PLOT_FREE.toString())
            .put("plot info", HELP_PLOT_INFO.toString())
            .put("plot list", HELP_PLOT_LIST.toString())
            .put("plot flist", HELP_PLOT_FLIST.toString())
            .put("plot trust [" + ARG_PLAYER + "]", HELP_PLOT_TRUST.toString())
            .put("plot untrust [" + ARG_PLAYER + "]", HELP_PLOT_UNTRUST.toString())
            .put("plot upgrade", HELP_PLOT_UPGRADE.toString())
            .put("plot downgrade", HELP_PLOT_DOWNGRADE.toString())
            .put("plot setcenter", HELP_PLOT_CENTER.toString())
            .build();

    private static ImmutableMap<String, String> remotePlotCommandToDescription = new ImmutableMap.Builder<String, String>()
            .put("rplot [plot] rename  [" + ARG_NAME + "]", HELP_PLOT_RENAME.toString())
            .put("rplot [plot] free ", HELP_PLOT_FREE.toString())
            .put("rplot [plot] info", HELP_PLOT_INFO.toString())
            .put("rplot [plot] setcenter", HELP_PLOT_CENTER.toString())
            .build();

    private static ImmutableMap<String, String> plotdeedCommandToDescription = new ImmutableMap.Builder<String, String>()
            .put("plotdeed [" + ARG_PLAYER + "] [" + ARG_AMOUNT + "]", HELP_PLOT_DEED.toString())
            .build();


}
