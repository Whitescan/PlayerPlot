package com.eclipsekingdom.playerplot.plotdeed;

import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.data.UserCache;
import com.eclipsekingdom.playerplot.data.UserData;
import com.eclipsekingdom.playerplot.sys.Language;
import com.eclipsekingdom.playerplot.sys.Permissions;
import com.eclipsekingdom.playerplot.sys.config.PluginConfig;
import com.eclipsekingdom.playerplot.util.PermInfo;
import com.eclipsekingdom.playerplot.util.PlotUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public class CommandWriteDeed implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Permissions.canWriteDeeds(player)) {
                UUID playerId = player.getUniqueId();
                if (UserCache.hasData(playerId)) {
                    int deedsToWrite = args.length > 0 ? PlotUtil.parseAmount(args[0]) : 1;
                    UserData userData = UserCache.getData(playerId);
                    PermInfo permInfo = UserCache.getPerms(playerId);
                    int unlockedPlots = userData.getUnlockedPlots();
                    if (unlockedPlots > 0) {
                        int unplacedPlots = PluginConfig.getStartingPlotNum() + unlockedPlots + permInfo.getPlotBonus() - PlotCache.getPlayerPlotsUsed(playerId);
                        int availableDeeds = Math.min(unlockedPlots, unplacedPlots);
                        if (availableDeeds >= deedsToWrite) {
                            Map<Integer, ItemStack> overflow = player.getInventory().addItem(PlotDeedType.DEFAULT.getPlotDeed().getItemStack(deedsToWrite));
                            int unSuccessful = 0;
                            for (ItemStack overItem : overflow.values()) {
                                unSuccessful += overItem.getAmount();
                            }
                            int writtenDeeds = deedsToWrite - unSuccessful;
                            userData.lockPlots(writtenDeeds);
                            player.sendMessage(ChatColor.LIGHT_PURPLE + Language.SUCCESS_WRITE_DEED.fromAmount(writtenDeeds));
                        } else {
                            player.sendMessage(ChatColor.RED + Language.WARN_INSUFFICIENT_UNCLAIMED_PLOTS.toString());
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + Language.WARN_NO_UNLOCKED_PLOTS.toString());
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + Language.WARN_NOT_PERMITTED.toString());
            }
        }

        return false;
    }
}
