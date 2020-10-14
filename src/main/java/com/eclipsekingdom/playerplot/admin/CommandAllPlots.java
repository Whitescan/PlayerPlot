package com.eclipsekingdom.playerplot.admin;

import com.eclipsekingdom.playerplot.plot.Plot;
import com.eclipsekingdom.playerplot.plot.PlotCache;
import com.eclipsekingdom.playerplot.config.Language;
import com.eclipsekingdom.playerplot.config.Permissions;
import com.eclipsekingdom.playerplot.util.InfoList;
import com.eclipsekingdom.playerplot.util.PlotPoint;
import com.eclipsekingdom.playerplot.util.PlotUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandAllPlots implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Permissions.canViewAllPlots(player)) {
                List<String> items = new ArrayList<>();
                for (Plot plot : PlotCache.getAllPlots()) {
                    items.add(getListString(plot));
                }
                InfoList infoList = new InfoList(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + Language.LABEL_ALL_PLOTS.toString() + " (" + items.size() + ")" + ":", items, 7);
                int page = args.length > 0 ? PlotUtil.parseAmount(args[0]) : 1;
                infoList.displayTo(player, page);
            } else {
                player.sendMessage(ChatColor.RED + Language.WARN_NOT_PERMITTED.toString());
            }
        }

        return false;
    }

    private String getListString(Plot plot) {
        String name = plot.getName();
        String ownerName = plot.getOwnerName();
        PlotPoint center = plot.getCenter();
        String displayString = ChatColor.DARK_PURPLE + name + " - " + ChatColor.LIGHT_PURPLE + ownerName;
        displayString += " " + ChatColor.GRAY + "(" + plot.getWorld().getName() + ", " + center.getX() + ", " + center.getZ() + ")";
        return displayString;

    }


}
