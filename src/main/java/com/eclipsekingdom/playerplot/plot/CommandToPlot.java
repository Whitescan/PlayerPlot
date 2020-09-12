package com.eclipsekingdom.playerplot.plot;

import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.sys.Language;
import com.eclipsekingdom.playerplot.sys.Permissions;
import com.eclipsekingdom.playerplot.util.X.XSound;
import com.eclipsekingdom.playerplot.util.scanner.PlotScanner;
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
                        Location targetLoc = targetPlot.getCenter().asLocation(w);
                        XSound tpSound = XSound.BLOCK_BEACON_POWER_SELECT;
                        if (tpSound.isSupported()) player.playSound(player.getLocation(), tpSound.parseSound(), 0.5f, 1.9f);
                        player.teleport(w.getHighestBlockAt(targetLoc).getLocation().add(0.5, 1, 0.5));
                        PlotScanner.showPlot(player, new Plot(player, player.getLocation(), "portal",5), 1);
                        if (tpSound.isSupported()) player.playSound(player.getLocation(), tpSound.parseSound(), 0.5f, 1.9f);
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
