package com.eclipsekingdom.playerplot;

import com.eclipsekingdom.playerplot.sys.Language;
import com.eclipsekingdom.playerplot.sys.Permissions;
import com.eclipsekingdom.playerplot.sys.PluginHelp;
import com.eclipsekingdom.playerplot.util.Scheduler;
import com.eclipsekingdom.playerplot.util.update.Spiget;
import com.eclipsekingdom.playerplot.util.update.Update;
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
            } else if (sub.equals("update")) {
                fetchUpdate(sender);
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
        sender.sendMessage(ChatColor.GRAY + Language.PLUGIN_AUTHOR.toString() + ": " + ChatColor.WHITE + descriptionFile.getAuthors().get(0));
        sender.sendMessage(ChatColor.GRAY + Language.PLUGIN_VERSION.toString() + ": " + ChatColor.WHITE + descriptionFile.getVersion());
    }


    private void fetchUpdate(final CommandSender sender) {
        Scheduler.runAsync(() -> {
            try {
                if (Spiget.isNewVersion()) {
                    final Update update = Spiget.getLatestUpdate();
                    Scheduler.run(() -> {
                        sender.sendMessage("");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "__Player Plot_______");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + update.getVersionName() + " " + ChatColor.DARK_PURPLE + ChatColor.ITALIC + "- " + update.getTitle());
                        sender.sendMessage(ChatColor.GRAY + Language.PLUGIN_NEW_UPDATE.toString());
                        sender.spigot().sendMessage(Language.PLUGIN_VIEW_UPDATE_NOTES.getWithLink(ChatColor.GRAY, "SpigotMC", update.getUpdateNotesUrl()));
                    });
                } else {
                    Scheduler.run(() -> {
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "[PlayerPlot] " + ChatColor.GRAY + Language.PLUGIN_UP_TO_DATE.fromPlugin("Player Plot"));
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                Scheduler.run(() -> {
                    sender.sendMessage(ChatColor.DARK_PURPLE + "[PlayerPlot] " + ChatColor.RED + Language.PLUGIN_UPDATE_ERROR.toString());
                });
            }
        });
    }

    private void processReload(CommandSender sender){
        PlayerPlot.reload();
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "[PlayerPlot] " + ChatColor.GRAY + Language.PLUGIN_RELOAD.toString());
    }

    private void showOverview(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "__Player Plot_______");
        sender.sendMessage(ChatColor.GRAY + Language.PLUGIN_DESCRIPTION.toString());
        sender.sendMessage("");
        sender.spigot().sendMessage(Language.PLUGIN_READ_MORE.getWithLink(ChatColor.GRAY, Language.PLUGIN_WIKI.toString(), "https://gitlab.com/sword7/playerplot/-/wikis/home"));
        sender.sendMessage(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "------- " + Language.PLUGIN_OPTIONS + " -------");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/playerplot help: " + ChatColor.WHITE + ChatColor.ITALIC + Language.PLUGIN_HELP);
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "/playerplot info: " + ChatColor.WHITE + ChatColor.ITALIC + Language.PLUGIN_INFO);
        if (Permissions.canUpdate(sender)) {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "/playerplot update: " + ChatColor.WHITE + ChatColor.ITALIC + Language.PLUGIN_HELP);
        }
        if (Permissions.canReload(sender)) {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "/playerplot reload: " + ChatColor.WHITE + ChatColor.ITALIC + Language.PLUGIN_INFO);
        }
    }

}
