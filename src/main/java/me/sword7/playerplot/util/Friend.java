package me.sword7.playerplot.util;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Friend {

    private UUID uuid;
    private String name;

    public Friend(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public Friend(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }


    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UUID) {
            UUID uuid = (UUID) o;
            return (this.uuid.equals(uuid));
        } else if (o instanceof String) {
            String string = (String) o;
            return (this.name.equalsIgnoreCase(string));
        } else if (o instanceof Friend) {
            Friend friend = (Friend) o;
            return (this.uuid.equals(friend.getUuid()) && this.name.equalsIgnoreCase(friend.getName()));
        } else if (o instanceof Player) {
            Player player = (Player) o;
            return (this.uuid.equals(player.getUniqueId()) && this.name.equalsIgnoreCase(player.getName()));
        } else if (o instanceof OfflinePlayer) {
            OfflinePlayer offlinePlayer = (OfflinePlayer) o;
            return (this.uuid.equals(offlinePlayer.getUniqueId()) && this.name.equalsIgnoreCase(offlinePlayer.getName()));
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 29).
                append(uuid).
                append(name).
                toHashCode();
    }

}
