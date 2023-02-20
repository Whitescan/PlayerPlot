package de.whitescan.playerplot.listener;

import static de.whitescan.playerplot.config.Language.WARN_PLOT_MAX;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import de.whitescan.playerplot.PlayerPlot;
import de.whitescan.playerplot.config.PluginConfig;
import de.whitescan.playerplot.config.Version;
import de.whitescan.playerplot.logic.PlotDeed;
import de.whitescan.playerplot.logic.UserData;
import de.whitescan.playerplot.util.PermInfo;
import de.whitescan.playerplot.util.xseries.XSound;

public class PlotDeedListener implements Listener {

	public PlotDeedListener() {
		Plugin plugin = PlayerPlot.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
			ItemStack itemStack = e.getItem();
			if (PlotDeed.isPlotDeed(itemStack)) {
				e.setCancelled(true);
				Player player = e.getPlayer();
				UUID playerID = player.getUniqueId();
				if (UserCache.hasData(playerID)) {
					UserData userData = UserCache.getData(playerID);
					PermInfo permInfo = UserCache.getPerms(playerID);
					int availablePlots = PluginConfig.getStartingPlotNum() + userData.getUnlockedPlots()
							+ permInfo.getPlotBonus();
					if ((availablePlots < permInfo.getPlotMax())) {
						userData.unlockPlot();
						player.sendMessage(ChatColor.LIGHT_PURPLE + "+1 plot");
						if (XSound.ITEM_BOOK_PAGE_TURN.isSupported()) {
							player.playSound(player.getLocation(), XSound.ITEM_BOOK_PAGE_TURN.parseSound(), 2f, 1f);
						}
						consumeItem(player, itemStack);
					} else {
						player.sendMessage(ChatColor.RED + WARN_PLOT_MAX.toString());
					}
				}
			}
		}
	}

	@Deprecated
	private void consumeItem(Player player, ItemStack itemStack) {
		if (Version.isNormalItemConsume()) {
			itemStack.setAmount(itemStack.getAmount() - 1);
		} else {
			if (Version.hasOffhand()) {
				ItemStack toSet = itemStack.getAmount() > 1
						? new ItemStack(itemStack.getType(), itemStack.getAmount() - 1)
						: null;
				if (toSet != null) {
					toSet.setItemMeta(itemStack.getItemMeta());
				}
				PlayerInventory playerInventory = player.getInventory();
				if (itemStack.equals(playerInventory.getItemInMainHand())) {
					playerInventory.setItemInMainHand(toSet);
				} else if (itemStack.equals(playerInventory.getItemInOffHand())) {
					playerInventory.setItemInOffHand(toSet);
				}
			} else {
				ItemStack toSet = itemStack.getAmount() > 1
						? new ItemStack(itemStack.getType(), itemStack.getAmount() - 1)
						: null;
				if (toSet != null) {
					toSet.setItemMeta(itemStack.getItemMeta());
				}
				player.setItemInHand(toSet);
			}
		}
	}

	@EventHandler
	public void onCraft(CraftItemEvent e) {
		for (ItemStack itemStack : e.getInventory()) {
			if (PlotDeed.isPlotDeed(itemStack)) {
				e.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler
	public void onPrepareCraft(PrepareItemCraftEvent e) {
		for (ItemStack itemStack : e.getInventory()) {
			if (PlotDeed.isPlotDeed(itemStack)) {
				e.getInventory().setItem(0, new ItemStack(Material.AIR, 1));
				return;
			}
		}
	}

}