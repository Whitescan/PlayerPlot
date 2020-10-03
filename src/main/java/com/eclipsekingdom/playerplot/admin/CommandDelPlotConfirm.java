package com.eclipsekingdom.playerplot.admin;

import com.eclipsekingdom.playerplot.plot.Plot;
import com.eclipsekingdom.playerplot.plot.PlotCache;
import com.eclipsekingdom.playerplot.sys.Language;
import com.eclipsekingdom.playerplot.sys.Permissions;
import com.eclipsekingdom.playerplot.sys.PluginBase;
import com.eclipsekingdom.playerplot.util.Dynmap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDelPlotConfirm implements CommandExecutor {

    private boolean usingDynmap = PluginBase.isDynmapDetected();
    private Dynmap dynmap = PluginBase.getDynmap();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Permissions.canDeletePlots(player)) {
                if (DeleteRequests.hasPending(player)) {
                    Plot plot = DeleteRequests.getPending(player).getPlot();
                    DeleteRequests.remove(player);
                    PlotCache.removePlot(plot);
                    if (usingDynmap) dynmap.deletePlot(plot);
                    player.sendMessage(ChatColor.LIGHT_PURPLE + Language.SUCCESS_PLOT_DELETE.toString());
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
