package com.eclipsekingdom.playerplot.sys;

import com.eclipsekingdom.playerplot.sys.config.PluginConfig;
import com.eclipsekingdom.playerplot.util.PermInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.Set;

public class Permissions {

    private static final String LOOT_PERM = "playerplot.loot";
    private static final String BUILD_PERM = "playerplot.access";
    private static final String TP_PERM = "playerplot.teleport";
    private static final String RELOAD_PERM = "playerplot.reload";
    private static final String UPDATE_PERM = "playerplot.update";
    private static final String WRITE_PERM = "playerplot.write";

    private static String DELETE_PERM = "playerplot.admin.delete";
    private static String VIEW_PERM = "playerplot.admin.view";


    public static boolean canWriteDeeds(CommandSender sender) {
        return hasPermission(sender, WRITE_PERM);
    }

    public static boolean canSummon(CommandSender sender) {
        return hasPermission(sender, LOOT_PERM);
    }

    public static boolean canBuildEverywhere(CommandSender sender) {
        return hasPermission(sender, BUILD_PERM);
    }

    public static boolean canTeleport(CommandSender sender) {
        return hasPermission(sender, TP_PERM);
    }

    public static boolean canReload(CommandSender sender) {
        return hasPermission(sender, RELOAD_PERM);
    }

    public static boolean canUpdate(CommandSender sender) {
        return hasPermission(sender, UPDATE_PERM);
    }

    private static boolean hasPermission(CommandSender sender, String permString) {
        return sender.hasPermission("playerplot.*") || sender.hasPermission(permString);
    }

    public static boolean canViewAllPlots(CommandSender sender) {
        return hasAdminPermission(sender, VIEW_PERM);
    }

    public static boolean canDeletePlots(CommandSender sender) {
        return hasAdminPermission(sender, DELETE_PERM);
    }

    private static boolean hasAdminPermission(CommandSender sender, String permString) {
        return sender.hasPermission("playerplot.*") ||
                sender.hasPermission("playerplot.admin.*") ||
                sender.hasPermission(permString);
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
