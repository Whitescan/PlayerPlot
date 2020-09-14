package com.eclipsekingdom.playerplot.data;

import com.eclipsekingdom.playerplot.data.event.UserDataLoadEvent;
import com.eclipsekingdom.playerplot.util.PlotUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserFlatFile {

    private static String DIR = "plugins/PlayerPlot/Data/users";
    private static File userDir = new File(DIR);

    public static UserData fetch(UUID playerID) {
        File userDataFile = new File(DIR, playerID + ".yml");
        if (userDataFile.exists()) {
            try {
                UserData userData = extractUserData(userDataFile, playerID);
                UserDataLoadEvent.Result result = userData != null ? UserDataLoadEvent.Result.SUCCESS : UserDataLoadEvent.Result.ERROR;
                PlotUtil.callEvent(new UserDataLoadEvent(result, playerID, userData));
                return userData;
            } catch (Exception e) {
                e.printStackTrace();
                PlotUtil.callEvent(new UserDataLoadEvent(UserDataLoadEvent.Result.ERROR, playerID, null));
                return null;
            }
        } else {
            UserData userData = new UserData();
            PlotUtil.callEvent(new UserDataLoadEvent(UserDataLoadEvent.Result.SUCCESS, playerID, userData));
            return userData;
        }
    }

    private static UserData extractUserData(File userDataFile, UUID playerID) {
        FileConfiguration userDataConfig = YamlConfiguration.loadConfiguration(userDataFile);
        int bonusPlots = userDataConfig.getInt("unlockedPlots");
        UserData userData = new UserData(bonusPlots);

        return userData;
    }

    public static List<UserData> fetchAllUsers() {
        List<UserData> userDataList = new ArrayList<>();

        String contents[] = userDir.list();

        for (String fileName : contents) {
            try {
                File userFile = new File(DIR, fileName);
                if (userFile.exists() && !userFile.isDirectory()) {
                    UUID uuid = UUID.fromString(fileName.split("\\.")[0]);
                    UserData userData = extractUserData(userFile, uuid);
                    if (userData != null) {
                        userDataList.add(userData);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return userDataList;
    }

    public static void store(UUID playerID, UserData userData) {
        if (userData != null) {
            File userDataFile = new File(DIR, playerID + ".yml");
            FileConfiguration userDataConfig = YamlConfiguration.loadConfiguration(userDataFile);
            userDataConfig.set("unlockedPlots", userData.getUnlockedPlots());
            save(userDataConfig, userDataFile);
        }
    }

    public static void save(FileConfiguration fileConfiguration, File file) {
        try {
            fileConfiguration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
