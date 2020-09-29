package com.eclipsekingdom.playerplot;

import com.eclipsekingdom.playerplot.sys.Language;
import com.eclipsekingdom.playerplot.sys.PluginHelp;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class CommandPlayerPlot implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length > 0) {
            String sub = args[0].toLowerCase();
            if (sub.equals("help")) {
                PluginHelp.showTo(sender);
            } else if (sub.equals("info")) {
                showInfo(sender);
            } else {
                showOverview(sender);
            }
        } else {
            showOverview(sender);
        }

        return true;
    }

    private void showInfo(CommandSender sender){
        PluginDescriptionFile descriptionFile = PlayerPlot.getPlugin().getDescription();
        sender.sendMessage("");
        sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "__Player Plot_______");
        sender.sendMessage(ChatColor.GRAY + Language.PLUGIN_AUTHOR.toString() + ": " + ChatColor.WHITE + descriptionFile.getAuthors().get(0));
        sender.sendMessage(ChatColor.GRAY + Language.PLUGIN_VERSION.toString() + ": " + ChatColor.WHITE + descriptionFile.getVersion());
        sender.sendMessage("");
    }

    private void showOverview(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "__Player Plot_______");
        sender.sendMessage(ChatColor.GRAY + Language.PLUGIN_DESCRIPTION.toString());
        sender.sendMessage("");
        sender.spigot().sendMessage(Language.PLUGIN_READ_MORE.getWithLink(ChatColor.GRAY, ChatColor.AQUA, Language.PLUGIN_WIKI.toString(), "https://gitlab.com/sword7/playerplot/-/wikis/home"));
        sender.sendMessage(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "------- " + Language.PLUGIN_OPTIONS + " -------");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/playerplot help: " + ChatColor.WHITE + ChatColor.ITALIC + Language.PLUGIN_HELP);
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/playerplot info: " + ChatColor.WHITE + ChatColor.ITALIC + Language.PLUGIN_INFO);
        sender.sendMessage("");
    }

}
