package me.sword7.playerplot.admin;

import me.sword7.playerplot.plot.Plot;
import me.sword7.playerplot.plot.PlotCache;
import me.sword7.playerplot.config.Language;
import me.sword7.playerplot.config.Permissions;
import me.sword7.playerplot.config.PluginBase;
import me.sword7.playerplot.integration.Dynmap;
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
