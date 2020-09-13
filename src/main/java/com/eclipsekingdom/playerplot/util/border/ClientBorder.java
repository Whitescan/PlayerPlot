package com.eclipsekingdom.playerplot.util.border;

import org.bukkit.World;

import java.util.UUID;

public class ClientBorder {

    private UUID id;
    private World world;
    private PlayerPusher playerPusher;

    public ClientBorder(World world) {
        this.id = UUID.randomUUID();
        this.world = world;
    }

    public void setPlayerPusher(PlayerPusher playerPusher) {
        this.playerPusher = playerPusher;
    }

    public UUID getId() {
        return id;
    }

    public World getWorld() {
        return world;
    }

    public void stopPusher() {
        if (playerPusher != null && !playerPusher.isCancelled()) {
            playerPusher.cancel();
        }
    }

}
