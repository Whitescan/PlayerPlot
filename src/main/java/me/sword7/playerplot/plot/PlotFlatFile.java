package me.sword7.playerplot.plot;

import me.sword7.playerplot.util.Friend;
import me.sword7.playerplot.util.LocationParts;
import me.sword7.playerplot.util.PlotPoint;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlotFlatFile {

    private static File file = new File("plugins/PlayerPlot/Data", "plots.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    public static void store(UUID plotID, Plot plot) {
        String key = plotID.toString();
        if (plot != null) {

            PlotPoint minCorner = plot.getMinCorner();
            PlotPoint maxCorner = plot.getMaxCorner();
            String baseString = plot.getName() + "=" + plot.getOwnerName() + "=" + plot.getOwnerID() + "=" +
                    plot.getWorld() + "=" + minCorner.getX() + "_" + minCorner.getZ() + "<" + maxCorner.getX() + "_" + maxCorner.getZ() + "=" + plot.getComponents();
            config.set(key + ".baseData", baseString);

            List<String> friendsList = new ArrayList<>();
            for (Friend friend : plot.getFriends()) {
                friendsList.add(friend.getName() + "=" + friend.getUuid());
            }

            String friendKey = key + ".friendData";
            if (friendsList.size() > 0) {
                config.set(friendKey, friendsList);
            } else {
                config.set(friendKey, null);
            }

            LocationParts spawnParts = plot.getSpawnParts();
            if (spawnParts != null) {
                config.set(key + ".spawn", spawnParts.toString());
            } else {
                config.set(key + ".spawn", null);
            }
        } else {
            config.set(key, null);
        }
    }

    public static List<Plot> fetch() {
        List<Plot> plots = new ArrayList<>();
        for (String plotIDString : config.getRoot().getKeys(false)) {
            try {
                String key = plotIDString;
                UUID plotID = UUID.fromString(plotIDString);

                String baseString = config.getString(key + ".baseData");
                String[] baseParts = baseString.split("=");
                String plotName = baseParts[0];
                String ownerName = baseParts[1];
                UUID ownerID = UUID.fromString(baseParts[2]);
                String world = baseParts[3];
                String coordString = baseParts[4];
                String[] coordParts = coordString.split("<");

                String minCornerString = coordParts[0];
                String[] minCornerStringParts = minCornerString.split("_");
                int minX = Integer.parseInt(minCornerStringParts[0]);
                int minZ = Integer.parseInt(minCornerStringParts[1]);
                PlotPoint min = new PlotPoint(minX, minZ);

                String maxCornerString = coordParts[1];
                String[] maxCornerStringParts = maxCornerString.split("_");
                int maxX = Integer.parseInt(maxCornerStringParts[0]);
                int maxZ = Integer.parseInt(maxCornerStringParts[1]);
                PlotPoint max = new PlotPoint(maxX, maxZ);

                int components = Integer.parseInt(baseParts[5]);

                List<Friend> friends = new ArrayList<>();
                if (config.contains(key + ".friendData")) {
                    List<String> friendsList = config.getStringList(key + ".friendData");
                    for (String friendString : friendsList) {
                        String[] friendParts = friendString.split("=");
                        String friendName = friendParts[0];
                        UUID friendID = UUID.fromString(friendParts[1]);
                        friends.add(new Friend(friendID, friendName));
                    }
                }

                LocationParts spawn = config.contains(key + ".spawn") ? LocationParts.parseParts(config.getString(key + ".spawn")) : null;
                plots.add(new Plot(plotID, plotName, ownerID, ownerName, min, max, world, components, friends, spawn));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return plots;
    }

    public static void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
