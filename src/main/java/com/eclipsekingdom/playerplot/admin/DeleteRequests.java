package com.eclipsekingdom.playerplot.admin;

import com.eclipsekingdom.playerplot.plot.Plot;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DeleteRequests {

    private static Map<UUID, Request> playerToRequest = new HashMap<>();

    public static void add(Player player, Plot plot) {
        playerToRequest.put(player.getUniqueId(), new Request(plot));
    }

    public static void cancel(Player player) {
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
