package me.sword7.playerplot.util;

import me.sword7.playerplot.plot.Plot;
import me.sword7.playerplot.plot.PlotCache;
import me.sword7.playerplot.config.PluginConfig;
import org.bukkit.ChatColor;

import java.util.UUID;

public class PlotUtil {

    public static int parseAmount(String string) {
        int amt;
        try {
            amt = Integer.parseInt(string);
            if (amt < 0) {
                amt = 0;
            }
            return amt;
        } catch (Exception e) {
            return 1;
        }
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
        String displayString = ChatColor.DARK_PURPLE + plot.getName();
        displayString += ChatColor.LIGHT_PURPLE + " (" + length + "x" + length + ")[" + ChatColor.AQUA + plot.getComponents() + ChatColor.LIGHT_PURPLE + "] ";
        displayString += ChatColor.GRAY + "(" + plot.getWorld() + ", " + center.getX() + ", " + center.getZ() + ")";
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
