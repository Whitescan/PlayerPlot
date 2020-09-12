package com.eclipsekingdom.playerplot.loot;

import com.eclipsekingdom.playerplot.sys.Permissions;
import com.eclipsekingdom.playerplot.sys.Language;
import com.eclipsekingdom.playerplot.util.Amount;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandPlotDeed implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (Permissions.canSummonPlotDeed(sender)) {
            if (args.length > 0) {
                String argOne = args[0];
                try {
                    int amount = Integer.parseInt(argOne);
                    if (sender instanceof Player) {
                        giveLoot((Player) sender, amount);
                    } else {
                        sender.sendMessage(ChatColor.RED + Language.MISC_FORMAT.fromFormat("/plotdeed [" + Language.ARG_PLAYER + "] [" + Language.ARG_AMOUNT + "]"));
                    }
                } catch (Exception e) {
                    Player player = Bukkit.getServer().getPlayer(argOne);
                    if (player != null) {
                        int amount = args.length > 1 ? Amount.parse(args[1]) : 1;
                        giveLoot(player, amount);
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + Language.SUCCESS_ITEMS_SENT.fromPlayer(player.getName()));
                    } else {
                        player.sendMessage(ChatColor.RED + Language.WARN_PLAYER_NOT_FOUND.fromPlayer(argOne));
                    }
                }
            } else {
                if (sender instanceof Player) {
                    giveLoot((Player) sender, 1);
                } else {
                    sender.sendMessage(ChatColor.RED + Language.MISC_FORMAT.fromFormat("/plotdeed [" + Language.ARG_PLAYER + "] [" + Language.ARG_AMOUNT + "]"));
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + Language.WARN_NOT_PERMITTED.toString());
        }

        return false;
    }

    private void giveLoot(Player player, int amount) {
        ItemStack item = PlotDeed.build();
        item.setAmount(amount);
        player.getInventory().addItem(item);
    }


}
