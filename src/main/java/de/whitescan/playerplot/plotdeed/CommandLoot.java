package de.whitescan.playerplot.plotdeed;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.whitescan.playerplot.config.Language;
import de.whitescan.playerplot.config.Permissions;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommandLoot implements CommandExecutor {

	private ILoot loot;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Permissions.canSummon(sender)) {
			if (args.length == 0) {// /loot
				if (sender instanceof Player) {
					giveLoot((Player) sender, loot.getDefault(), 1);
				} else {
					sendHelp(sender);
				}
			} else if (args.length == 1) { // /loot {list/[type]/[amount]}
				if (args[0].equalsIgnoreCase("list")) {
					showTypeList(sender);
				} else if (isInt(args[0])) {
					int amount = getCleanedAmount(args[0]);
					if (sender instanceof Player) {
						giveLoot((Player) sender, loot.getDefault(), amount);
					} else {
						sendHelp(sender);
					}
				} else {
					ItemStack itemStack;
					try {
						itemStack = loot.valueOf(args[0].toUpperCase());
					} catch (Exception e) {
						sender.sendMessage(ChatColor.RED + Language.WARN_UNKNOWN_TYPE.toString());
						return false;
					}
					if (sender instanceof Player) {
						giveLoot((Player) sender, itemStack, 1);
					} else {
						sendHelp(sender);
					}
				}
			} else if (args.length == 2) { // /loot [type] {[player]/[amount]}
				ItemStack itemStack;
				try {
					itemStack = loot.valueOf(args[0].toUpperCase());
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED + Language.WARN_UNKNOWN_TYPE.toString());
					return false;
				}
				if (isInt(args[1])) {
					int amount = getCleanedAmount(args[1]);
					if (sender instanceof Player) {
						giveLoot((Player) sender, itemStack, amount);
					} else {
						sendHelp(sender);
					}
				} else {
					Player player = Bukkit.getServer().getPlayer(args[1]);
					if (player != null) {
						giveLoot(player, itemStack, 1);
						sender.sendMessage(ChatColor.LIGHT_PURPLE
								+ Language.SUCCESS_ITEMS_SENT.fromPlayerAndAmount(player.getName(), 1));
					} else {
						sender.sendMessage(ChatColor.RED + Language.WARN_PLAYER_NOT_FOUND.fromPlayer(args[1]));
					}
				}
			} else if (args.length >= 3) { /// loot [type] [player] [amount]
				ItemStack itemStack;
				try {
					itemStack = loot.valueOf(args[0].toUpperCase());
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED + Language.WARN_UNKNOWN_TYPE.toString());
					return false;
				}
				Player player = Bukkit.getServer().getPlayer(args[1]);
				if (player != null) {
					int amount = isInt(args[2]) ? getCleanedAmount(args[2]) : 1;
					giveLoot(player, itemStack, amount);
					sender.sendMessage(ChatColor.LIGHT_PURPLE
							+ Language.SUCCESS_ITEMS_SENT.fromPlayerAndAmount(player.getName(), amount));
				} else {
					sender.sendMessage(ChatColor.RED + Language.WARN_PLAYER_NOT_FOUND.fromPlayer(args[1]));
				}

			}
		} else {
			sender.sendMessage(ChatColor.RED + Language.WARN_NOT_PERMITTED.toString());
		}
		return false;
	}

	private void showTypeList(CommandSender sender) {
		sender.sendMessage(ChatColor.LIGHT_PURPLE + loot.getListLabel() + ":");
		for (String type : loot.getLootTypes()) {
			sender.sendMessage(ChatColor.GRAY + "- " + type);
		}
	}

	private int getCleanedAmount(String validIntString) {
		int amount = Integer.parseInt(validIntString);
		if (amount < 0)
			amount = 0;
		if (amount > 255)
			amount = 255;
		return amount;
	}

	private void giveLoot(Player player, ItemStack itemStack, int amount) {
		if (amount > 0) {
			itemStack.setAmount(amount);
			Map<Integer, ItemStack> overflow = player.getInventory().addItem(itemStack);
			for (ItemStack overItem : overflow.values()) {
				player.getWorld().dropItemNaturally(player.getLocation(), overItem);
			}
		}
	}

	private void sendHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + Language.MISC_FORMAT.fromFormat("/" + loot.getRoot() + " [" + loot.getType()
				+ "] [" + Language.ARG_PLAYER + "] [" + Language.ARG_AMOUNT + "]"));
	}

	private boolean isInt(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
