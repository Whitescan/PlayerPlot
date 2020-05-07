package com.eclipsekingdom.playerplot.util;

import com.eclipsekingdom.playerplot.PlayerPlot;
import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.data.UserCache;
import com.eclipsekingdom.playerplot.data.event.DataLoadListener;
import com.eclipsekingdom.playerplot.plot.Plot;
import com.eclipsekingdom.playerplot.sys.config.PluginConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.UUID;

import static com.eclipsekingdom.playerplot.sys.lang.Message.*;

public class PlotUtil {

    public static void callEvent(Event event) {
        Bukkit.getScheduler().runTask(PlayerPlot.getPlugin(), () -> {
            Bukkit.getServer().getPluginManager().callEvent(event);
        });
    }

    public static void fetchUnloadedData(Player player) {
        player.sendMessage(ChatColor.DARK_PURPLE + "[PlayerPlot] " + ChatColor.RED + STATUS_UNLOADED_DATA);
        DataLoadListener.registerWaitingPlayer(player);
        UserCache.cache(player.getUniqueId());
    }

    public static int getUpgradeLength(int relSize) {
        relSize++;
        int newLength;
        int unitSideLength = PluginConfig.getPlotUnitSideLength();
        int unitSquared = unitSideLength * unitSideLength;
        newLength = (int) Math.round(Math.sqrt(Math.round(unitSquared * relSize)));
        return newLength;
    }

    public static int getDowngradeLength(int relSize) {
        relSize--;
        int newLength;
        int unitSideLength = PluginConfig.getPlotUnitSideLength();
        int unitSquared = unitSideLength * unitSideLength;
        newLength = (int) Math.round(Math.sqrt(Math.round(unitSquared * relSize)));
        return newLength;
    }

    public static String getListString(Plot plot) {
        int length = plot.getSideLength();
        PlotPoint center = plot.getCenter();
        String displayString = ChatColor.DARK_PURPLE + plot.getName() + " (" + length + "x" + length + ") ";
        displayString += ChatColor.GRAY + LABEL_CENTER.toString().toLowerCase() + ": (" + center.getX() + "," + center.getZ() + ")";
        displayString += " " + LABEL_WORLD.toString().toLowerCase() + ": " + plot.getWorld().getName();
        displayString += " " + LABEL_COMPONENTS.toString().toLowerCase() + ": " + plot.getComponents();
        return displayString;
    }

    public static String getFListString(Plot plot) {
        String displayString = ChatColor.DARK_PURPLE + plot.getName() + ChatColor.GRAY + " - " + plot.getOwnerName();
        return displayString;
    }

    public static String getFriendsAsString(Plot plot) {
        String friends = "";
        for (Friend friend : plot.getFriends()) {
            friends += (", " + friend.getName());
        }
        if (friends.length() > 2) {
            return friends.substring(2);
        } else {
            return "-";
        }
    }

    public static String getDefaultName(UUID playerID) {
        String plotBase = "plot_";
        int num = 1;
        String attempt = plotBase + num;
        while (PlotCache.getPlayerPlot(playerID, attempt) != null) {
            num++;
            attempt = plotBase + num;
        }
        return attempt;
    }


}
