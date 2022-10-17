package de.whitescan.playerplot.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import de.whitescan.playerplot.PlayerPlot;
import de.whitescan.playerplot.config.Permissions;
import de.whitescan.playerplot.config.PluginConfig;
import de.whitescan.playerplot.plot.Plot;
import de.whitescan.playerplot.plot.PlotCache;
import de.whitescan.playerplot.util.PermInfo;

public class UserCache implements Listener {

	private static Map<UUID, UserData> userToData = new HashMap<>();
	private static HashMap<UUID, PermInfo> userToPerms = new HashMap<>();

	private static UserFlatFile userFlatFile = new UserFlatFile();
	private static boolean usingDatabase = PluginConfig.isUseDatabase();
	private static UserDatabase database = usingDatabase ? new UserDatabase() : null;

	public UserCache() {
		Plugin plugin = PlayerPlot.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		load();
	}

	public static boolean hasData(UUID playerID) {
		return userToData.containsKey(playerID) && userToData.get(playerID) != null;
	}

	public static UserData getData(UUID playerID) {
		return userToData.get(playerID);
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		UUID playerID = player.getUniqueId();
		userToPerms.put(playerID, Permissions.getPermInfo(player));
		cache(playerID);
		processNameChange(player);
	}

	private static void processNameChange(Player player) {
		List<Plot> plotList = PlotCache.getPlayerPlots(player.getUniqueId());
		if (plotList.size() > 0) {
			Plot plot = plotList.get(0);
			if (!plot.getOwnerName().equalsIgnoreCase(player.getName())) {
				String ownerName = player.getName();
				plot.setOwnerName(ownerName);
				for (int i = 1; i < plotList.size(); i++) {
					plotList.get(i).setOwnerName(ownerName);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onQuit(PlayerQuitEvent e) {
		UUID playerID = e.getPlayer().getUniqueId();
		userToPerms.remove(playerID);
		UserData userData = userToData.get(playerID);
		if (userData != null) {
			forget(playerID);
		}
	}

	private void load() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			UUID playerID = player.getUniqueId();
			userToPerms.put(playerID, Permissions.getPermInfo(player));
			cache(playerID);
		}
	}

	public static void shutdown() {
		save();
		userToPerms.clear();
		userToData.clear();
	}

	public static void save() {
		for (UUID playerID : userToData.keySet()) {
			UserData userData = userToData.get(playerID);
			if (usingDatabase) {
				database.storeUserSync(playerID, userData);
			} else {
				userFlatFile.store(playerID, userData);
			}
		}
	}

	public static void cache(UUID playerID) {
		if (usingDatabase) {
			database.fetchUserDataAsync(playerID, (userData) -> {
				if (userData != null) {
					userToData.put(playerID, userData);
				}
			});
		} else {
			UserData userData = userFlatFile.fetch(playerID);
			if (userData != null) {
				userToData.put(playerID, userData);
			}
		}
	}

	public static void forget(UUID playerID) {
		UserData userData = userToData.get(playerID);
		if (usingDatabase) {
			database.storeUserAsync(playerID, userData);
		} else {
			userFlatFile.store(playerID, userData);
		}
		userToData.remove(playerID);
	}

	public static PermInfo getPerms(UUID playerID) {
		return userToPerms.get(playerID);
	}

}
