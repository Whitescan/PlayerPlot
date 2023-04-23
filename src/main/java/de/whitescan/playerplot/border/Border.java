package de.whitescan.playerplot.border;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.whitescan.playerplot.logic.Plot;
import de.whitescan.playerplot.util.PlotPoint;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.world.level.border.WorldBorder;

public class Border {

	private static Map<UUID, ClientBorder> playerToBorder = new HashMap<>();

	public static boolean hasBorder(UUID playerId) {
		return playerToBorder.containsKey(playerId);
	}

	public static ClientBorder getBorder(UUID playerId) {
		return playerToBorder.get(playerId);
	}

	public static void shutdown() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			Border.hide(player);
		}
		playerToBorder.clear();
	}

	public static void show(Player player, World world, double x, double z, double size) {
		WorldBorder worldBorder = new WorldBorder();
		worldBorder.world = ((CraftWorld) world).getHandle();
		worldBorder.setCenter(x, z);
		worldBorder.setSize(size);
		worldBorder.setDamagePerBlock(0);
		worldBorder.setDamageSafeZone(0);
		((CraftPlayer) player).getHandle().connection.send(new ClientboundInitializeBorderPacket(worldBorder));
	}

	public static void hide(Player player, World world) {
		WorldBorder worldBorder = new WorldBorder();
		worldBorder.world = ((CraftWorld) world).getHandle();
		((CraftPlayer) player).getHandle().connection.send(new ClientboundSetBorderLerpSizePacket(worldBorder));
	}

	public static UUID showPlot(Player player, Plot plot) {
		World world = player.getWorld();
		PlotPoint plotCenter = plot.getCenter();
		double size = plot.getMaxCorner().getX() - plot.getMinCorner().getX();
		double offSet = size % 2 == 0 ? 0.5 : 1.0;
		double x = plotCenter.getX() + offSet;
		double z = plotCenter.getZ() + offSet;
		return showBorder(player, world, x, z, size);
	}

	public static UUID showBeam(Player player) {
		Location loc = player.getLocation();
		World world = loc.getWorld();
		return showBorder(player, world, loc.getX(), loc.getZ(), 2);
	}

	private static UUID showBorder(Player player, World world, double x, double z, double size) {
		ClientBorder clientBorder = new ClientBorder(world);
		clientBorder.setPlayerPusher(new PlayerPusher(player, x, z, size));
		playerToBorder.put(player.getUniqueId(), clientBorder);
		show(player, world, x, z, size);
		return clientBorder.getId();
	}

	public static void hide(Player player, UUID pid) {
		UUID playerId = player.getUniqueId();
		if (playerToBorder.containsKey(playerId)) {
			ClientBorder clientBorder = playerToBorder.get(playerId);
			if (clientBorder.getId().equals(pid)) {
				hide(player, clientBorder.getWorld());
				playerToBorder.remove(playerId);
				clientBorder.stopPusher();
			}
		}
	}

	private static void hide(Player player) {
		UUID playerId = player.getUniqueId();
		if (playerToBorder.containsKey(playerId)) {
			ClientBorder clientBorder = playerToBorder.get(playerId);
			hide(player, clientBorder.getWorld());
			clientBorder.stopPusher();
		}
	}

}
