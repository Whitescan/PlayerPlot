package com.eclipsekingdom.playerplot.sys;

import com.eclipsekingdom.playerplot.sys.config.PluginConfig;
import com.eclipsekingdom.playerplot.util.PermInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.Set;

public class Permissions {

    private static String LOOT_PERM = "playerplot.loot";
    private static String BUILD_PERM = "playerplot.access";

    public static boolean canSummonPlotDeed(CommandSender sender) {
        return hasPermission(sender, LOOT_PERM);
    }

    public static boolean canBuildEverywhere(CommandSender sender) {
        return hasPermission(sender, BUILD_PERM);
    }

    private static boolean hasPermission(CommandSender sender, String permString) {
        return (sender.hasPermission("playerplot.*") || sender.hasPermission(permString));
    }

    public static PermInfo getPermInfo(Player player) {
        Set<PermissionAttachmentInfo> perms = player.getEffectivePermissions();
        int bonusPlots = 0;
        int maxPlots = PluginConfig.getMaxPlotNum();
        for (PermissionAttachmentInfo perm : perms) {
            String permString = perm.getPermission();
            try {
                if (permString.startsWith("plot.bonus.")) {
                    int newBonus = Integer.parseInt(permString.split("plot\\.bonus\\.")[1]);
                    if (newBonus > bonusPlots) bonusPlots = newBonus;
                } else if (permString.startsWith("plot.cap.")) {
                    int newCap = Integer.parseInt(permString.split("plot\\.cap\\.")[1]);
                    if (newCap > maxPlots) maxPlots = newCap;
                }
            } catch (Exception e) {
                //do nothing
            }

        }

        return new PermInfo(bonusPlots, maxPlots);
    }

}
