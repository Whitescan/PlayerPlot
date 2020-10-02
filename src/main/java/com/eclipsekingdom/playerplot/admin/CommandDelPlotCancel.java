package com.eclipsekingdom.playerplot.admin;

import com.eclipsekingdom.playerplot.sys.Language;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDelPlotCancel implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (DeleteRequests.hasPending(player)) {
                DeleteRequests.remove(player);
                player.sendMessage(ChatColor.LIGHT_PURPLE + Language.SUCCESS_REQUEST_CANCEL.toString());
            } else {
                player.sendMessage(ChatColor.RED + Language.WARN_NO_DELETE_REQUESTS.toString());
            }
        }

        return false;
    }

}
