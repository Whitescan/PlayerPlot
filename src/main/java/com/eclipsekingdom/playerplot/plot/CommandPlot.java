package com.eclipsekingdom.playerplot.plot;

import com.eclipsekingdom.playerplot.PlayerPlot;
import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.data.UserCache;
import com.eclipsekingdom.playerplot.data.UserData;
import com.eclipsekingdom.playerplot.plot.validation.NameValidation;
import com.eclipsekingdom.playerplot.plot.validation.RegionValidation;
import com.eclipsekingdom.playerplot.sys.Language;
import com.eclipsekingdom.playerplot.sys.Permissions;
import com.eclipsekingdom.playerplot.sys.PluginBase;
import com.eclipsekingdom.playerplot.sys.PluginHelp;
import com.eclipsekingdom.playerplot.sys.config.PluginConfig;
import com.eclipsekingdom.playerplot.util.*;
import com.eclipsekingdom.playerplot.util.X.XSound;
import com.eclipsekingdom.playerplot.util.scanner.PlotScanner;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.eclipsekingdom.playerplot.sys.Language.*;

public class CommandPlot implements CommandExecutor {

    private boolean usingDynmap = PluginBase.isDynmapDetected();
    private Dynmap dynmap = PluginBase.getDynmap();

    private ImmutableSet<String> plotActionArgs = new ImmutableSet.Builder<String>()
            .add("info")
            .add("free")
            .add("trust")
            .add("untrust")
            .add("upgrade")
            .add("downgrade")
            .add("setcenter")
            .add("setspawn")
            .add("rename")
            .build();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID playerID = player.getUniqueId();
            if (UserCache.hasData(playerID)) {
                if (args.length == 0) {
                    PluginHelp.showPlots(player);
                } else {
                    String sub = args[0].toLowerCase();
                    if (sub.equals("scan")) {
                        processScan(player);
                    } else if (sub.equals("claim")) {
                        processClaim(player, args);
                    } else if (sub.equals("list")) {
                        processList(player, args);
                    } else if (sub.equals("flist")) {
                        processFList(player, args);
                    } else if (plotActionArgs.contains(args[0]) || args[0].startsWith("@")) {
                        processPlotAction(player, args, (plot, effectiveArgs) -> {
                            if (effectiveArgs.length > 0) {
                                String effectiveSub = effectiveArgs[0].toLowerCase();
                                switch (effectiveSub) {
                                    case "info":
                                        processInfo(player, plot);
                                        break;
                                    case "free":
                                        processFree(player, plot);
                                        break;
                                    case "trust":
                                        processTrust(player, plot, effectiveArgs);
                                        break;
                                    case "untrust":
                                        processUntrust(player, plot, effectiveArgs);
                                        break;
                                    case "upgrade":
                                        processUpgrade(player, plot);
                                        break;
                                    case "downgrade":
                                        processDowngrade(player, plot);
                                        break;
                                    case "setcenter":
                                        processSetCenter(player, plot);
                                        break;
                                    case "setspawn":
                                        processSetSpawn(player, plot);
                                        break;
                                    case "rename":
                                        processRename(player, plot, effectiveArgs);
                                        break;
                                    default:
                                        PluginHelp.showPlots(player);
                                        break;
                                }
                            } else {
                                PluginHelp.showPlots(player);
                            }
                        });
                    } else {
                        PluginHelp.showPlots(player);
                    }
                }
            } else {
                PlotUtil.fetchUnloadedData(player);
            }
        }

        return false;
    }


    private void processPlotAction(Player player, String[] args, IPlotAction action) {
        if (args[0].startsWith("@")) {
            String[] effectiveArgs = Arrays.copyOfRange(args, 1, args.length);
            if (!args[0].equals("@here")) {
                String targetName = args[0].substring(1);
                Plot targetPlot = PlotCache.getPlayerPlot(player.getUniqueId(), targetName);
                if (targetPlot != null) {
                    action.run(targetPlot, effectiveArgs);
                } else {
                    player.sendMessage(Language.WARN_PLOT_NOT_FOUND.coloredFromPlot(targetName, ChatColor.RED, ChatColor.DARK_RED));
                }
            } else {
                processStandingAction(player, effectiveArgs, action);
            }
        } else {
            processStandingAction(player, args, action);
        }
    }

    private void processStandingAction(Player player, String[] args, IPlotAction action) {
        Plot targetPlot = PlotCache.getPlot(player.getLocation());
        if (targetPlot != null) {
            if (targetPlot.getOwnerID().equals(player.getUniqueId())) {
                action.run(targetPlot, args);
            } else {
                player.sendMessage(ChatColor.RED + Language.WARN_NOT_OWNER.toString());
            }
        } else {
            player.sendMessage(ChatColor.RED + Language.WARN_NOT_STANDING_IN_PLOT.toString());
        }
    }

    private void processScan(Player player) {
        Plot plot = PlotCache.getPlot(player.getLocation());
        if (plot != null) {
            if (XSound.BLOCK_BEACON_POWER_SELECT.isSupported()) {
                player.playSound(player.getLocation(), XSound.BLOCK_BEACON_POWER_SELECT.parseSound(), 1f, 0.77f);
            }
            player.sendMessage(ChatColor.LIGHT_PURPLE + "[PlayerPlot] " + ChatColor.DARK_PURPLE + SCANNER_PLOT_OVERVIEW.fromPlayerAndPlot(plot.getOwnerName(), plot.getName()));
            PlotScanner.showPlot(player, plot, 5);
        } else {
            player.sendMessage(ChatColor.DARK_PURPLE + "[PlayerPlot] " + ChatColor.RED + SCANNER_NO_PLOTS);
        }
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

    private void processClaim(Player player, String[] args) {
        UUID playerID = player.getUniqueId();
        UserData userData = UserCache.getData(playerID);
        PermInfo permInfo = UserCache.getPerms(playerID);
        if (PlotCache.getPlayerPlotsUsed(playerID) < PluginConfig.getStartingPlotNum() + userData.getUnlockedPlots() + permInfo.getPlotBonus()) {
            int unitSideLength = PluginConfig.getPlotUnitSideLength();
            RegionValidation.Status regionStatus = RegionValidation.canPlotBeRegisteredAt(player.getLocation(), unitSideLength, null);
            if (regionStatus == RegionValidation.Status.VALID) {
                String plotName = args.length > 1 ? args[1] : PlotUtil.getDefaultName(playerID);
                NameValidation.Status nameStatus = NameValidation.clean(plotName, playerID);
                if (nameStatus == NameValidation.Status.VALID) {
                    Plot plot = new Plot(player, player.getLocation(), plotName, unitSideLength);
                    PlotCache.registerPlot(plot);
                    player.sendMessage(SUCCESS_PLOT_CLAIM.coloredFromPlot(plot.getName(), ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE));
                    PlotScanner.showPlot(player, plot, 7);
                    if (usingDynmap) dynmap.registerPlot(plot);
                    if (XSound.BLOCK_BEACON_ACTIVATE.isSupported()) {
                        player.playSound(player.getLocation(), XSound.BLOCK_BEACON_ACTIVATE.parseSound(), 1f, 1f);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + nameStatus.getMessage());
                }
            } else {
                player.sendMessage(ChatColor.RED + regionStatus.getMessage());
            }
        } else {
            player.sendMessage(ChatColor.RED + WARN_PLOT_LIMIT.toString());
        }
    }

    private void processFree(Player player, Plot plot) {
        PlotScanner.showPlot(player, plot, 1);
        PlotCache.removePlot(plot);
        if (usingDynmap) dynmap.deletePlot(plot);
        player.sendMessage(SUCCESS_PLOT_FREE.coloredFromPlot(plot.getName(), ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE));
        if (XSound.BLOCK_BEACON_DEACTIVATE.isSupported()) {
            player.playSound(player.getLocation(), XSound.BLOCK_BEACON_DEACTIVATE.parseSound(), 1f, 1f);
        }
    }

    public void processList(Player player, String[] args) {
        List<String> items = new ArrayList<>();
        for (Plot plot : PlotCache.getPlayerPlots(player.getUniqueId())) {
            items.add(PlotUtil.getListString(plot));
        }
        UUID playerID = player.getUniqueId();
        UserData userData = UserCache.getData(playerID);
        PermInfo permInfo = UserCache.getPerms(playerID);
        int used = PlotCache.getPlayerPlotsUsed(playerID);
        int capacity = PluginConfig.getStartingPlotNum() + userData.getUnlockedPlots() + permInfo.getPlotBonus();
        String title = ChatColor.LIGHT_PURPLE + LABEL_PLOTS.toString() + " (" + used + "/" + capacity + ")";
        InfoList infoList = new InfoList(title, items, 7);
        int page = args.length > 0 ? Amount.parse(args[0]) : 1;
        infoList.displayTo(player, page);
    }

    public void processFList(Player player, String[] args) {
        List<String> items = new ArrayList<>();
        for (Plot plot : PlotCache.getFriendPlots(player)) {
            items.add(PlotUtil.getFListString(plot));
        }
        String title = ChatColor.LIGHT_PURPLE + LABEL_FRIEND_PLOTS.toString();
        InfoList infoList = new InfoList(title, items, 7);
        int page = args.length > 0 ? Amount.parse(args[0]) : 1;
        infoList.displayTo(player, page);
    }

    private void processTrust(Player player, Plot plot, String[] args) {
        if (args.length > 1) {
            String targetName = args[1];
            Player target = Bukkit.getPlayer(targetName);
            if (target != null) {
                Friend friend = new Friend(target);
                if (!plot.getFriends().contains(friend)) {
                    if (!plot.getOwnerID().equals(friend.getUuid())) {
                        plot.addFriend(friend);
                        PlotCache.registerFriendAdd(friend, plot);
                        PlotCache.touch(plot);
                        player.sendMessage(SUCCESS_PLOT_TRUST.coloredFromPlayerAndPlot(target.getName(), plot.getName(), ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE));
                        target.sendMessage(SUCCESS_INVITED.coloredFromPlayerAndPlot(player.getName(), plot.getName(), ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE));
                    } else {
                        player.sendMessage(ChatColor.RED + WARN_ADD_SELF.toString());
                    }
                } else {
                    player.sendMessage(WARN_ALREADY_FRIEND.coloredFromPlayerAndPlot(target.getName(), plot.getName(), ChatColor.RED, ChatColor.DARK_RED));
                }
            } else {
                player.sendMessage(WARN_PLAYER_OFFLINE.coloredFromPlayer(targetName, ChatColor.RED, ChatColor.DARK_RED));
            }
        } else {
            player.sendMessage(ChatColor.RED + MISC_FORMAT.fromFormat("/" + PluginConfig.getRootCommand() + " trust [" + ARG_PLAYER + "]"));
        }
    }

    private void processUntrust(Player player, Plot plot, String[] args) {
        if (args.length > 1) {
            String friendName = args[1];
            if (plot.isFriend(friendName)) {
                Friend friend = plot.getFriend(friendName);
                plot.removeFriend(friendName);
                PlotCache.touch(plot);
                PlotCache.registerFriendRemove(friend, plot);
                player.sendMessage(SUCCESS_PLOT_UNTRUST.coloredFromPlayerAndPlot(friendName, plot.getName(), ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE));
            } else {
                player.sendMessage(WARN_NOT_FRIEND.coloredFromPlayerAndPlot(friendName, plot.getName(), ChatColor.RED, ChatColor.DARK_RED));
            }
        } else {
            player.sendMessage(ChatColor.RED + MISC_FORMAT.fromFormat("/" + PluginConfig.getRootCommand() + " untrust [" + ARG_PLAYER + "]"));
        }
    }

    private void processUpgrade(Player player, Plot plot) {
        UUID playerID = player.getUniqueId();
        UserData userData = UserCache.getData(playerID);
        PermInfo permInfo = UserCache.getPerms(playerID);
        if (PlotCache.getPlayerPlotsUsed(playerID) < PluginConfig.getStartingPlotNum() + userData.getUnlockedPlots() + permInfo.getPlotBonus()) {
            int newSideLength = PlotUtil.getUpgradeLength(plot.getComponents());
            PlotPoint center = plot.getCenter();
            RegionValidation.Status regionStatus = RegionValidation.canPlotBeUpgradedAt(plot.getWorld(), center, newSideLength, plot.getID());
            if (regionStatus == RegionValidation.Status.VALID) {
                PlotScanner.showPlot(player, plot, 1);
                if (XSound.BLOCK_BEACON_ACTIVATE.isSupported()) {
                    player.playSound(player.getLocation(), XSound.BLOCK_BEACON_ACTIVATE.parseSound(), 1, 1);
                }
                PlotPoint newMin = center.getMinCorner(newSideLength);
                PlotPoint newMax = center.getMaxCorner(newSideLength);
                plot.setRegion(newMin, newMax);
                plot.incrementComponents();
                PlotCache.touch(plot);

                if (usingDynmap) dynmap.updateMarker(plot);

                Bukkit.getScheduler().runTaskLater(PlayerPlot.getPlugin(), () -> {
                    PlotScanner.showPlot(player, plot, 7);
                    player.sendMessage(SUCCESS_PLOT_UPGRADE.coloredFromPlot(plot.getName(), ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE));
                }, 23);

            } else {
                player.sendMessage(ChatColor.RED + regionStatus.getMessage());
            }
        } else {
            player.sendMessage(ChatColor.RED + WARN_PLOT_LIMIT.toString());
        }
    }


    private void processDowngrade(Player player, Plot plot) {
        PlotPoint center = plot.getCenter();
        int newSideLength = PlotUtil.getDowngradeLength(plot.getComponents());
        if (newSideLength >= PluginConfig.getPlotUnitSideLength()) {

            PlotScanner.showPlot(player, plot, 1);
            if (XSound.BLOCK_BEACON_DEACTIVATE.isSupported()) {
                player.playSound(player.getLocation(), XSound.BLOCK_BEACON_DEACTIVATE.parseSound(), 1f, 1f);
            }

            PlotPoint newMin = center.getMinCorner(newSideLength);
            PlotPoint newMax = center.getMaxCorner(newSideLength);
            plot.setRegion(newMin, newMax);
            plot.decrementComponents();
            PlotCache.touch(plot);

            if (usingDynmap) dynmap.updateMarker(plot);

            Bukkit.getScheduler().scheduleSyncDelayedTask(PlayerPlot.getPlugin(), () -> {
                PlotScanner.showPlot(player, plot, 7);
                player.sendMessage(SUCCESS_PLOT_DOWNGRADE.coloredFromPlot(plot.getName(), ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE));
            }, 23);

        } else {
            player.sendMessage(WARN_NOT_DOWNGRADEABLE.coloredFromPlot(plot.getName(), ChatColor.RED, ChatColor.DARK_RED));
        }
    }

    private void processSetCenter(Player player, Plot plot) {
        Location location = player.getLocation();
        RegionValidation.Status regionStatus = RegionValidation.canPlotBeRegisteredAt(location, plot.getSideLength(), plot.getID());
        if (regionStatus == RegionValidation.Status.VALID) {
            plot.setCenter(location);
            PlotCache.touch(plot);
            player.sendMessage(SUCCESS_PLOT_CENTER.coloredFromPlot(plot.getName(), ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE));
            PlotScanner.showPlot(player, plot, 7);
            if (usingDynmap) dynmap.updatePlot(plot);
            if (XSound.BLOCK_BEACON_ACTIVATE.isSupported())
                player.playSound(location, XSound.BLOCK_BEACON_ACTIVATE.parseSound(), 1f, 1f);
        } else {
            player.sendMessage(ChatColor.RED + regionStatus.getMessage());
        }
    }

    private void processSetSpawn(Player player, Plot plot) { //TODO downgrade, setcenter remove if out of bounds,
        if (Permissions.canTeleport(player)) {
            Location location = player.getLocation();
            if (plot.contains(location)) {
                plot.setSpawn(new LocationParts(location));
                PlotCache.touch(plot);
                player.sendMessage(ChatColor.LIGHT_PURPLE + SUCCESS_SPAWN_SET.toString());
            } else {
                player.sendMessage(ChatColor.RED + WARN_OUTSIDE_PLOT_BOUNDS.toString());
            }
        } else {
            player.sendMessage(ChatColor.RED + WARN_NOT_PERMITTED.toString());
        }
    }

    private void processRename(Player player, Plot plot, String[] args) {
        if (args.length > 1) {
            String newName = args[1];
            NameValidation.Status nameStatus = NameValidation.clean(newName, player.getUniqueId());
            if (nameStatus == NameValidation.Status.VALID) {
                plot.setName(newName);
                PlotCache.touch(plot);
                if (usingDynmap) dynmap.updatePlot(plot);
                player.sendMessage(SUCCESS_PLOT_RENAME.coloredFromPlot(newName, ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE));
            } else {
                player.sendMessage(ChatColor.RED + nameStatus.getMessage());
            }
        } else {
            player.sendMessage(ChatColor.RED + MISC_FORMAT.fromFormat("/" + PluginConfig.getRootCommand() + " rename [" + ARG_NAME + "]"));
        }
    }

}
