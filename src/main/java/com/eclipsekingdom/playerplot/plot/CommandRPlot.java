package com.eclipsekingdom.playerplot.plot;

import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.plot.validation.NameValidation;
import com.eclipsekingdom.playerplot.plot.validation.RegionValidation;
import com.eclipsekingdom.playerplot.sys.PluginBase;
import com.eclipsekingdom.playerplot.sys.PluginHelp;
import com.eclipsekingdom.playerplot.sys.Version;
import com.eclipsekingdom.playerplot.util.Dynmap;
import com.eclipsekingdom.playerplot.util.PlotPoint;
import com.eclipsekingdom.playerplot.util.PlotUtil;
import com.eclipsekingdom.playerplot.util.scanner.PlotScanner;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.eclipsekingdom.playerplot.sys.lang.Message.*;

public class CommandRPlot implements CommandExecutor {

    private boolean usingDynmap = PluginBase.isDynmapDetected();
    private Dynmap dynmap = PluginBase.getDynmap();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                String targetName = args[0];
                Plot target = PlotCache.getPlayerPlot(player.getUniqueId(), targetName);
                if (target != null) {
                    if (args.length > 1) {
                        switch (args[1].toLowerCase()) {
                            case "info":
                                processInfo(player, target);
                                break;
                            case "free":
                                processFree(player, target);
                                break;
                            case "setcenter":
                                processSetCenter(player, target);
                                break;
                            case "rename":
                                processRename(player, target, args);
                                break;
                            default:
                                PluginHelp.showPlots(player);
                                break;
                        }
                    } else {
                        processInfo(player, target);
                    }
                } else {
                    sender.sendMessage(WARN_PLOT_NOT_FOUND.coloredFromPlot(targetName, ChatColor.RED, ChatColor.DARK_PURPLE));
                }
            } else {
                PluginHelp.showPlots(player);
            }

        }

        return false;
    }

    private void processInfo(Player player, Plot plot) {
        player.sendMessage(ChatColor.DARK_PURPLE + "- - - " + plot.getName() + " - - -");
        PlotPoint minCorner = plot.getMinCorner();
        PlotPoint maxCorner = plot.getMaxCorner();
        int length = maxCorner.getX() - minCorner.getX() + 1;
        player.sendMessage(ChatColor.LIGHT_PURPLE + LABEL_AREA.toString() + ": " + ChatColor.RESET + length + " x " + length + "");
        PlotPoint center = plot.getCenter();
        player.sendMessage(ChatColor.LIGHT_PURPLE + LABEL_CENTER.toString() + ": " + ChatColor.RESET + "x:" + center.getX() + " z:" + center.getZ());
        player.sendMessage(ChatColor.LIGHT_PURPLE + LABEL_MIN_CORNER.toString() + ": " + ChatColor.RESET + "x:" + minCorner.getX() + " z:" + minCorner.getZ());
        player.sendMessage(ChatColor.LIGHT_PURPLE + LABEL_MAX_CORNER.toString() + ": " + ChatColor.RESET + "x:" + maxCorner.getX() + " z:" + maxCorner.getZ());
        player.sendMessage(ChatColor.LIGHT_PURPLE + LABEL_WORLD.toString() + ": " + ChatColor.RESET + plot.getWorld().getName());
        player.sendMessage(ChatColor.LIGHT_PURPLE + LABEL_COMPONENTS.toString() + ": " + ChatColor.RESET + plot.getComponents());
        player.sendMessage(ChatColor.LIGHT_PURPLE + LABEL_FRIENDS.toString() + ":");
        player.sendMessage(PlotUtil.getFriendsAsString(plot));
    }

    private void processFree(Player player, Plot plot) {
        PlotCache.removePlot(plot);
        if (usingDynmap) dynmap.deletePlot(plot);
        player.sendMessage(SUCCESS_PLOT_FREE.coloredFromPlot(plot.getName(), ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE));

    }

    private void processSetCenter(Player player, Plot plot) {
        Location location = player.getLocation();
        RegionValidation.Status regionStatus = RegionValidation.canPlotBeRegisteredAt(player.getLocation(), plot.getSideLength(), plot.getID());
        if (regionStatus == RegionValidation.Status.VALID) {
            plot.setCenter(location);
            PlotCache.reportPlotModification(plot);
            player.sendMessage(SUCCESS_PLOT_CENTER.coloredFromPlot(plot.getName(), ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE));
            PlotScanner.showPlot(player, plot, 7);
            if (usingDynmap) dynmap.updatePlot(plot);
            if (Version.current.canPlayBorderSound())
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1f, 1f);
        } else {
            player.sendMessage(ChatColor.RED + regionStatus.getMessage());
        }
    }

    private void processRename(Player player, Plot plot, String[] args) {

        if (args.length > 2) {
            String newName = args[2];
            NameValidation.Status nameStatus = NameValidation.clean(newName, player.getUniqueId());
            if (nameStatus == NameValidation.Status.VALID) {
                plot.setName(newName);
                PlotCache.reportPlotModification(plot);
                if (usingDynmap) {
                    dynmap.deletePlot(plot);
                    dynmap.registerPlot(plot);
                }
                player.sendMessage(SUCCESS_PLOT_RENAME.coloredFromPlot(newName, ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE));
            } else {
                player.sendMessage(ChatColor.RED + nameStatus.getMessage());
            }
        } else {
            player.sendMessage(ChatColor.RED + MISC_FORMAT.fromFormat("/rplot [plot] rename [" + ARG_NAME + "]"));
        }

    }


}
