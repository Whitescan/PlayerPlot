package de.whitescan.playerplot.admin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.whitescan.playerplot.plot.Plot;

public class DeleteRequests {

	private static Map<UUID, Request> playerToRequest = new HashMap<>();

	public static Request add(Player player, Plot plot) {
		Request request = new Request(plot);
		playerToRequest.put(player.getUniqueId(), request);
		return request;
	}

	public static void remove(Player player) {
		playerToRequest.remove(player.getUniqueId());
	}

	public static boolean hasPending(Player player) {
		return playerToRequest.containsKey(player.getUniqueId());
	}

	public static Request getPending(Player player) {
		return playerToRequest.get(player.getUniqueId());
	}

	public static class Request {

		private UUID id;
		private Plot plot;

		private Request(Plot plot) {
			this.id = UUID.randomUUID();
			this.plot = plot;
		}

		public UUID getId() {
			return id;
		}

		public Plot getPlot() {
			return plot;
		}
	}

}
