package com.eclipsekingdom.playerplot.user;

import com.eclipsekingdom.playerplot.util.Scheduler;
import com.eclipsekingdom.playerplot.util.storage.CallbackQuery;
import com.eclipsekingdom.playerplot.util.storage.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

public class UserDatabase {

    private static DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    public UserDatabase() {
        initialize();
    }

    public void initialize() {
        try {
            databaseConnection.openConnection();
            Statement statement = databaseConnection.getConnection().createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS PUser (" +
                    "uuid CHAR(36)," +
                    "unlockedPlots INT(4) DEFAULT 0," +
                    "PRIMARY KEY (uuid)" +
                    ");");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchUserDataSync(UUID playerID, CallbackQuery<UserData> callback) {
        fetchUserData(playerID, callback);
    }

    public void fetchUserDataAsync(UUID playerID, CallbackQuery<UserData> callback) {
        Scheduler.runAsync(() -> {
            fetchUserData(playerID, callback);
        });
    }

    private void fetchUserData(UUID playerID, CallbackQuery<UserData> callback) {
        try {
            databaseConnection.openConnection();
            ResultSet userResult = databaseConnection.getConnection().createStatement().executeQuery("SELECT * FROM PUser WHERE uuid = '" + playerID + "';");
            final UserData userData;
            if (userResult.next()) {
                int unlockedPlots = userResult.getInt("unlockedPlots");
                userData = new UserData(unlockedPlots);
            } else {
                userData = new UserData();
            }
            Scheduler.run(() -> {
                callback.onQueryDone(userData);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storeUserAsync(UUID playerID, UserData userData) {
        Scheduler.runAsync(() -> {
            storeUser(playerID, userData);
        });
    }

    public void storeUserSync(UUID playerID, UserData userData) {
        storeUser(playerID, userData);
    }

    private void storeUser(UUID playerID, UserData userData) {

        int unlockedPlots = userData.getUnlockedPlots();
        try {
            databaseConnection.openConnection();
            databaseConnection.getConnection().createStatement().executeUpdate("REPLACE INTO PUser (uuid, unlockedPlots)" +
                    "VALUES ('" + playerID + "', " + unlockedPlots + ");");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
