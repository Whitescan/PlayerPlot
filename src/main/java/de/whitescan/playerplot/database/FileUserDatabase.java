package de.whitescan.playerplot.database;

import java.io.File;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.whitescan.playerplot.logic.UserData;

public class FileUserDatabase implements IUserDatabase {

	private static String DIR = "plugins/PlayerPlot/Data/users";

	@Override
	public UserData fetch(UUID playerID) {
		File userDataFile = new File(DIR, playerID + ".yml");
		if (userDataFile.exists()) {
			try {
				FileConfiguration userDataConfig = YamlConfiguration.loadConfiguration(userDataFile);
				int bonusPlots = userDataConfig.getInt("unlockedPlots");
				UserData userData = new UserData(bonusPlots);
				return userData;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			UserData userData = new UserData();
			return userData;
		}
	}

	@Override
	public void store(UUID playerID, UserData userData) {
		if (userData != null) {
			File userDataFile = new File(DIR, playerID + ".yml");
			FileConfiguration userDataConfig = YamlConfiguration.loadConfiguration(userDataFile);
			userDataConfig.set("unlockedPlots", userData.getUnlockedPlots());
			save(userDataConfig, userDataFile);
		}
	}

	public void save(FileConfiguration fileConfiguration, File file) {
		try {
			fileConfiguration.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
