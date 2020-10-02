package com.eclipsekingdom.playerplot.admin;

import com.eclipsekingdom.playerplot.plot.Plot;
import com.eclipsekingdom.playerplot.plot.PlotCache;
import com.eclipsekingdom.playerplot.sys.Language;
import com.eclipsekingdom.playerplot.sys.Permissions;
import com.eclipsekingdom.playerplot.util.Scheduler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDelPlot implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Permissions.canDeletePlots(player)) {
                Plot plot = PlotCache.getPlot(player.getLocation());
                if (plot != null) {
                    DeleteRequests.add(player, plot);
                    player.spigot().sendMessage(Language.INFO_CONFIRM_DELETE.getWithPlayerConfirmDeny(ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE, plot.getOwnerName(), "/delplotconfirm", "/delplotcancel"));
                    player.sendMessage(ChatColor.GRAY + Language.INFO_REQUEST_DURATION.fromSeconds(25));
                    Scheduler.runLater(() -> {
                        DeleteRequests.cancel(player);
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
