package com.eclipsekingdom.playerplot.plot;

import com.eclipsekingdom.playerplot.sys.Language;
import com.eclipsekingdom.playerplot.sys.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.eclipsekingdom.playerplot.sys.Language.ARG_PLOT;

public class CommandToPlot implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Permissions.canTeleport(sender)) {
                if (args.length > 0) {
                    Plot targetPlot = PlotCache.getPlayerPlot(player.getUniqueId(), args[0]);
                    if (targetPlot != null) {
                        Location targetLoc = targetPlot.getSpawn();
                        new PlotBeam(player, targetLoc);
                        return true;
                    } else {
                        player.sendMessage(Language.WARN_PLOT_NOT_FOUND.coloredFromPlot(args[0], ChatColor.RED, ChatColor.DARK_RED));
                        return false;
                    }
                } else {
                    player.sendMessage(ChatColor.RED + Language.MISC_FORMAT.fromFormat("/toplot [" + ARG_PLOT + "]"));
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
