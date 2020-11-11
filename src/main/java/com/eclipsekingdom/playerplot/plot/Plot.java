package com.eclipsekingdom.playerplot.plot;

import com.eclipsekingdom.playerplot.config.Permissions;
import com.eclipsekingdom.playerplot.util.Friend;
import com.eclipsekingdom.playerplot.util.LocationParts;
import com.eclipsekingdom.playerplot.util.PlotPoint;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

public class Plot {

    private final UUID ID;
    private final UUID ownerID;
    private String ownerName;
    private String name;
    private String world;
    private List<Friend> friends = new ArrayList<>();
    private Set<UUID> trustedIDs = new HashSet<>();
    private PlotPoint minCorner;
    private PlotPoint maxCorner;
    private int components = 1;
    private LocationParts spawnParts;

    //initialized
    private int sideLength;
    private PlotPoint center;
    private PlotPoint[] corners;

    public Plot(Player player, Location location, String name, int sideLength) {
        this.ID = UUID.randomUUID();
        this.ownerID = player.getUniqueId();
        this.ownerName = player.getName();
        this.name = name;
        this.world = location.getWorld().getName();
        this.minCorner = PlotPoint.fromLocation(location).getMinCorner(sideLength);
        this.maxCorner = PlotPoint.fromLocation(location).getMaxCorner(sideLength);

        initialize();
    }

    public Plot(UUID plotID, String name, UUID ownerID, String ownerName, PlotPoint minCorner, PlotPoint maxCorner, String world, int components, List<Friend> friends, LocationParts spawnParts) {
        this.ID = plotID;
        this.name = name;
        this.ownerID = ownerID;
        this.ownerName = ownerName;
        this.minCorner = minCorner;
        this.maxCorner = maxCorner;
        this.components = components;
        this.world = world;
        this.friends = friends;
        this.spawnParts = spawnParts;

        initialize();
    }

    public void setRegion(PlotPoint minCorner, PlotPoint maxCorner) {
        this.minCorner = minCorner;
        this.maxCorner = maxCorner;
        initialize();
    }

    private void initialize() {
        for (Friend friend : friends) {
            trustedIDs.add(friend.getUuid());
        }
        trustedIDs.add(ownerID);
        this.sideLength = calculateSideLength();
        this.center = calculatePlotCenter();
        this.corners = calculateCorners();
    }

    public void setCenter(Location location) {
        this.world = location.getWorld().getName();
        this.minCorner = PlotPoint.fromLocation(location).getMinCorner(sideLength);
        this.maxCorner = PlotPoint.fromLocation(location).getMaxCorner(sideLength);
        this.center = calculatePlotCenter();
        this.corners = calculateCorners();
    }

    private int calculateSideLength() {
        return maxCorner.getX() - minCorner.getX() + 1;
    }

    private PlotPoint calculatePlotCenter() {
        boolean evenLength = sideLength % 2 == 0;
        int offSet = evenLength ? 0 : -1;
        int x = maxCorner.getX() - ((sideLength + offSet) / 2);
        int z = maxCorner.getZ() - ((sideLength + offSet) / 2);
        return new PlotPoint(x, z);
    }

    private PlotPoint[] calculateCorners() {
        PlotPoint[] corners = new PlotPoint[4];
        corners[0] = minCorner; //bottom left
        corners[1] = maxCorner; //top right
        corners[2] = new PlotPoint(minCorner.getX(), maxCorner.getZ()); //top left
        corners[3] = new PlotPoint(maxCorner.getX(), minCorner.getZ()); //bottom right
        return corners;
    }

    public void setSpawn(Location spawn) {
        this.spawnParts = new LocationParts(spawn);
    }

    public void removeSpawn() {
        this.spawnParts = null;
    }

    public LocationParts getSpawnParts() {
        return spawnParts;
    }

    public Location getSpawn() {
        if (spawnParts != null) {
            return spawnParts.getLocation();
        } else {
            World world = Bukkit.getWorld(this.world);
            if (world != null) {
                return world.getHighestBlockAt(center.asLocation(world)).getLocation().add(0.5, 1, 0.5);
            } else {
                return null;
            }
        }
    }

    public UUID getID() {
        return ID;
    }

    public UUID getOwnerID() {
        return ownerID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorld() {
        return world;
    }

    public int getComponents() {
        return components;
    }

    public void incrementComponents() {
        components++;
    }

    public void decrementComponents() {
        components--;
    }

    public boolean contains(Location location) {
        return (location != null && location.getWorld().getName().equals(world) && withinXRange(location) && withinZRange(location));
    }

    private boolean withinXRange(Location location) {
        int blockX = location.getBlockX();
        return ((minCorner.getX() <= blockX) && (blockX <= maxCorner.getX()));
    }

    private boolean withinZRange(Location location) {
        int blockZ = location.getBlockZ();
        return ((minCorner.getZ() <= blockZ) && (blockZ <= maxCorner.getZ()));
    }


    public PlotPoint getMinCorner() {
        return minCorner;
    }

    public PlotPoint getMaxCorner() {
        return maxCorner;
    }

    public PlotPoint[] getCorners() {
        return corners;
    }

    public int getSideLength() {
        return sideLength;
    }

    public PlotPoint getCenter() {
        return center;
    }

    public boolean isAllowed(Player player) {
        return Permissions.canBuildEverywhere(player) || trustedIDs.contains(player.getUniqueId());
    }

    public boolean isFriend(String friendName) {
        for (Friend friend : friends) {
            if (friend.getName().equalsIgnoreCase(friendName)) {
                return true;
            }
        }
        return false;
    }

    public Friend getFriend(String friendName) {
        for (Friend friend : friends) {
            if (friend.getName().equalsIgnoreCase(friendName)) {
                return friend;
            }
        }
        return null;
    }

    public void addFriend(Friend friend) {
        friends.add(friend);
        trustedIDs.add(friend.getUuid());
    }

    public void removeFriend(String friendName) {
        for (int i = friends.size() - 1; i >= 0; i--) {
            Friend friend = friends.get(i);
            if (friend.getName().equalsIgnoreCase(friendName)) {
                friends.remove(friend);
                trustedIDs.remove(friend.getUuid());
            }
        }
    }

    public List<Friend> getFriends() {
        return friends;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Plot) {
            return (this.ID.equals(((Plot) o).ID));
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 37).
                append(ID).
                toHashCode();
    }

}
