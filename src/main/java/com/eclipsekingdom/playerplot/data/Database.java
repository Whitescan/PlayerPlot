package com.eclipsekingdom.playerplot.data;

import com.eclipsekingdom.playerplot.PlayerPlot;
import com.eclipsekingdom.playerplot.sys.config.PluginConfig;
import com.eclipsekingdom.playerplot.data.event.UserDataLoadEvent;
import com.eclipsekingdom.playerplot.plot.Plot;
import com.eclipsekingdom.playerplot.util.Friend;
import com.eclipsekingdom.playerplot.util.PlotPoint;
import com.eclipsekingdom.playerplot.util.PlotUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

public class Database {

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    private Connection connection;

    private boolean initialized = false;

    public boolean isInitialized() {
        return initialized;
    }

    public Database() {
        this.host = PluginConfig.getHost();
        this.port = Integer.parseInt(PluginConfig.getPort());
        this.database = PluginConfig.getDatabase();
        this.username = PluginConfig.getUsername();
        this.password = PluginConfig.getPassword();
        initialize();
    }

    public void initialize() {
        try {
            openConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS PUser (" +
                    "uuid char(36)," +
                    "unlockedPlots int(4) DEFAULT 0," +
                    "PRIMARY KEY (uuid)" +
                    ");");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS PPlot (" +
                    "uuid char(36)," +
                    "name varchar(20)," +
                    "ownerID varchar(36)," +
                    "ownerName varchar(16)," +
                    "minX int," +
                    "minZ int," +
                    "maxX int," +
                    "maxZ int," +
                    "world varchar(36)," +
                    "components smallint," +
                    "PRIMARY KEY (uuid)" +
                    ");");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS PTrusts (" +
                    "plotID char(36)," +
                    "friendID varchar(36)," +
                    "friendName varchar(16)," +
                    "PRIMARY KEY (plotID, friendID) " +
                    ");");
            initialized = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        synchronized (this) {
            if (connection != null && !connection.isClosed()) return;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true&useSSL=" + PluginConfig.isSsl() + "&maxReconnects=108", this.username, this.password);
            connection.setNetworkTimeout(Executors.newFixedThreadPool(1), 0);
        }
    }

    public void fetchUserDataSync(UUID playerID) {
        fetchUserData(playerID);
    }

    public void fetchUserDataAsync(UUID playerID) {
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                fetchUserData(playerID);
            }
        };

        r.runTaskAsynchronously(PlayerPlot.getPlugin());
    }

    public void fetchUserData(UUID playerID) {
        try {
            openConnection();
            ResultSet userResult = connection.createStatement().executeQuery("SELECT * FROM PUser WHERE uuid = '" + playerID + "';");
            UserData userData;
            if (!userResult.next()) {
                userData = new UserData();
            } else {
                int unlockedPlots = userResult.getInt("unlockedPlots");
                userData = new UserData(unlockedPlots);
            }
            if (userData != null) {
                UserCache.put(playerID, userData);
                PlotUtil.callEvent(new UserDataLoadEvent(UserDataLoadEvent.Result.SUCCESS, playerID, userData));
            } else {
                PlotUtil.callEvent(new UserDataLoadEvent(UserDataLoadEvent.Result.ERROR, playerID, null));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            PlotUtil.callEvent(new UserDataLoadEvent(UserDataLoadEvent.Result.ERROR, playerID, null));
        } catch (SQLException e) {
            e.printStackTrace();
            PlotUtil.callEvent(new UserDataLoadEvent(UserDataLoadEvent.Result.ERROR, playerID, null));
        }
    }

    public void storeUserAsync(UUID playerID, UserData userData) {
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                storeUser(playerID, userData);
            }
        };
        r.runTaskAsynchronously(PlayerPlot.getPlugin());
    }

    public void storeUserSync(UUID playerID, UserData userData) {
        storeUser(playerID, userData);
    }

    private void storeUser(UUID playerID, UserData userData) {

        int unlockedPlots = userData.getUnlockedPlots();
        try {
            openConnection();
            connection.createStatement().executeUpdate("REPLACE INTO PUser (uuid, unlockedPlots)" +
                    "VALUES ('" + playerID + "', " + unlockedPlots + "');");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Plot> fetchPlots() {
        List<Plot> plots = new ArrayList<>();
        try {
            openConnection();
            ResultSet plotResults = connection.createStatement().executeQuery("SELECT * FROM PPlot;");
            while (plotResults.next()) {
                Plot plot = extractPlot(plotResults);
                plots.add(plot);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return plots;
    }

    private Plot extractPlot(ResultSet plotResults) throws SQLException {
        UUID plotID = UUID.fromString(plotResults.getString("uuid"));
        String name = plotResults.getString("name");
        UUID ownerID = UUID.fromString(plotResults.getString("ownerID"));
        String ownerName = plotResults.getString("ownerName");
        int minX = plotResults.getInt("minX");
        int minZ = plotResults.getInt("minZ");
        PlotPoint min = new PlotPoint(minX, minZ);
        int maxX = plotResults.getInt("maxX");
        int maxZ = plotResults.getInt("maxZ");
        PlotPoint max = new PlotPoint(maxX, maxZ);
        World world = Bukkit.getWorld(plotResults.getString("world"));
        int components = plotResults.getInt("components");
        ResultSet friendsResult = connection.createStatement().executeQuery("SELECT * FROM PTrusts WHERE plotID = '" + plotID + "';");
        List<Friend> friends = new ArrayList<>();
        while (friendsResult.next()) {
            UUID friendID = UUID.fromString(friendsResult.getString("friendID"));
            String friendName = friendsResult.getString("friendName");
            friends.add(new Friend(friendID, friendName));
        }
        return new Plot(plotID, name, ownerID, ownerName, min, max, world, components, friends);
    }

    public void storePlot(UUID plotID, Plot plot) {
        try {
            openConnection();
            if (plot != null) {
                String name = plot.getName();
                UUID ownerID = plot.getOwnerID();
                String ownerName = plot.getOwnerName();
                PlotPoint min = plot.getMinCorner();
                int minX = min.getX();
                int minZ = min.getZ();
                PlotPoint max = plot.getMaxCorner();
                int maxX = max.getX();
                int maxZ = max.getZ();
                String world = plot.getWorld().getName();
                int components = plot.getComponents();
                List<Friend> friends = plot.getFriends();
                connection.createStatement().executeUpdate("REPLACE INTO PPlot (uuid, name, ownerID, ownerName, minX, minZ, maxX, maxZ, world, components) " +
                        "VALUES ('" + plotID + "', '" + name + "', '" + ownerID + "', '" + ownerName + "', " + minX + ", " + minZ + ", " + maxX + ", " + maxZ + ", '" + world + "', " + components + ");");

                connection.createStatement().executeUpdate("DELETE FROM PTrusts WHERE plotID = '" + plotID + "'");
                for (Friend friend : friends) {
                    connection.createStatement().executeUpdate("INSERT INTO PTrusts (plotID, friendID, friendName) " +
                            "VALUES ('" + plotID + "','" + friend.getUuid() + "', '" + friend.getName() + "');");
                }
            } else {
                connection.createStatement().executeUpdate("DELETE FROM PTrusts WHERE plotID = '" + plotID + "'");
                connection.createStatement().executeUpdate("DELETE FROM PPlot WHERE uuid = '" + plotID + "'");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void shutdown() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
        }

    }


}
