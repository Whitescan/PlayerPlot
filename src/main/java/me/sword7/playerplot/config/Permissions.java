package me.sword7.playerplot.config;

import me.sword7.playerplot.util.PermInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.Set;

public class Permissions {

    private static final String LOOT_PERM = "playerplot.loot";
    private static final String BUILD_PERM = "playerplot.access";
    private static final String TP_PERM = "playerplot.teleport";
    private static final String RELOAD_PERM = "playerplot.reload";
    private static final String WRITE_PERM = "playerplot.write";

    private static String AMIN_DELETE_PERM = "playerplot.admin.delete";
    private static String ADMIN_VIEW_PERM = "playerplot.admin.view";

    private static String PLOT_SCAN_PERM = "playerplot.plot.scan";
    private static String PLOT_CLAIM_PERM = "playerplot.plot.claim";
    private static String PLOT_LIST_PERM = "playerplot.plot.list";
    private static String PLOT_FLIST_PERM = "playerplot.plot.flist";
    private static String PLOT_RENAME_PERM = "playerplot.plot.rename";
    private static String PLOT_FREE_PERM = "playerplot.plot.free";
    private static String PLOT_INFO_PERM = "playerplot.plot.info";
    private static String PLOT_TRUST_PERM = "playerplot.plot.trust";
    private static String PLOT_UNTRUST_PERM = "playerplot.plot.untrust";
    private static String PLOT_UPGRADE_PERM = "playerplot.plot.upgrade";
    private static String PLOT_DOWNGRADE_PERM = "playerplot.plot.downgrade";
    private static String PLOT_SET_CENTER_PERM = "playerplot.plot.setcenter";
    private static String PLOT_SET_SPAWN_PERM = "playerplot.plot.setspawn";

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

    private static boolean hasPermission(CommandSender sender, String permString) {
        return sender.hasPermission("playerplot.*") || sender.hasPermission(permString);
    }

    public static boolean canViewAllPlots(CommandSender sender) {
        return hasAdminPermission(sender, ADMIN_VIEW_PERM);
    }

    public static boolean canDeletePlots(CommandSender sender) {
        return hasAdminPermission(sender, AMIN_DELETE_PERM);
    }

    private static boolean hasAdminPermission(CommandSender sender, String permString) {
        return sender.hasPermission("playerplot.*") ||
                sender.hasPermission("playerplot.admin.*") ||
                sender.hasPermission(permString);
    }

    public static boolean canPlotScan(CommandSender sender) {
        return hasPlotPermission(sender, PLOT_SCAN_PERM);
    }

    public static boolean canPlotClaim(CommandSender sender) {
        return hasPlotPermission(sender, PLOT_CLAIM_PERM);
    }

    public static boolean canPlotList(CommandSender sender) {
        return hasPlotPermission(sender, PLOT_LIST_PERM);
    }

    public static boolean canPlotFList(CommandSender sender) {
        return hasPlotPermission(sender, PLOT_FLIST_PERM);
    }

    public static boolean canPlotRename(CommandSender sender) {
        return hasPlotPermission(sender, PLOT_RENAME_PERM);
    }

    public static boolean canPlotFree(CommandSender sender) {
        return hasPlotPermission(sender, PLOT_FREE_PERM);
    }

    public static boolean canPlotInfo(CommandSender sender) {
        return hasPlotPermission(sender, PLOT_INFO_PERM);
    }

    public static boolean canPlotTrust(CommandSender sender) {
        return hasPlotPermission(sender, PLOT_TRUST_PERM);
    }

    public static boolean canPlotUntrust(CommandSender sender) {
        return hasPlotPermission(sender, PLOT_UNTRUST_PERM);
    }

    public static boolean canPlotUpgrade(CommandSender sender) {
        return hasPlotPermission(sender, PLOT_UPGRADE_PERM);
    }

    public static boolean canPlotDowngrade(CommandSender sender) {
        return hasPlotPermission(sender, PLOT_DOWNGRADE_PERM);
    }

    public static boolean canPlotSetCenter(CommandSender sender) {
        return hasPlotPermission(sender, PLOT_SET_CENTER_PERM);
    }

    public static boolean canPlotSetSpawn(CommandSender sender) {
        return hasPlotPermission(sender, PLOT_SET_SPAWN_PERM);
    }

    private static boolean hasPlotPermission(CommandSender sender, String permString) {
        return sender.hasPermission("playerplot.*") ||
                sender.hasPermission("playerplot.plot.*") ||
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
