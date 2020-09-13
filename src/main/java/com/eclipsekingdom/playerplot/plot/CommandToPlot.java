package com.eclipsekingdom.playerplot.plot;

import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.sys.Language;
import com.eclipsekingdom.playerplot.sys.Permissions;
import com.eclipsekingdom.playerplot.util.X.XSound;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandToPlot implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Permissions.canTeleport(sender)) {
                if (args.length > 0) {
                    Plot targetPlot = PlotCache.getPlayerPlot(player.getUniqueId(), args[0]);
                    if (targetPlot != null) {
                        World w = targetPlot.getWorld();
                        Location targetLoc = targetPlot.getSpawn() != null ? targetPlot.getSpawn().getLocation() : null;
                        if (targetLoc == null)
                            targetLoc = w.getHighestBlockAt(targetPlot.getCenter().asLocation(w)).getLocation().add(0.5, 1, 0.5);
                        XSound tpSound = XSound.BLOCK_BEACON_POWER_SELECT;
                        if (tpSound.isSupported())
                            player.playSound(player.getLocation(), tpSound.parseSound(), 0.5f, 1.9f);
                        player.teleport(targetLoc);
                        PlotScanner.showPlot(player, new Plot(player, targetLoc, "portal", 3), 1);
                        if (tpSound.isSupported()) player.playSound(targetLoc, tpSound.parseSound(), 0.5f, 1.9f);
                        return true;
                    } else {
                        player.sendMessage(Language.WARN_PLOT_NOT_FOUND.coloredFromPlot(args[0], ChatColor.RED, ChatColor.DARK_RED));
                        return false;
                    }
                } else {
                    player.sendMessage(ChatColor.RED + Language.MISC_FORMAT.fromFormat("/toplot [plot]"));
                    return false;
                }
            } else {
                player.sendMessage(ChatColor.RED + Language.WARN_NOT_PERMITTED.toString());
                return false;
            }
        } else {
            return false;
        }
    }

}
