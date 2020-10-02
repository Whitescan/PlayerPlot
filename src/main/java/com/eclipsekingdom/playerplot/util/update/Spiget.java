package com.eclipsekingdom.playerplot.util.update;

import com.eclipsekingdom.playerplot.PlayerPlot;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Spiget {

    public static final String PLUGIN_ID = "68033";
    private static final Version VERSION = new Version(PlayerPlot.getPlugin().getDescription().getVersion());
    private static final String USER_AGENT = "PlayerPlot";
    private static final String LATEST_VERSIONS_URL = "https://api.spiget.org/v2/resources/" + PLUGIN_ID + "/versions/latest";
    private static final String LATEST_UPDATE_URL = "https://api.spiget.org/v2/resources/" + PLUGIN_ID + "/updates/latest";

    public static boolean isNewVersion() throws Exception {
        InputStreamReader reader = openReader(LATEST_VERSIONS_URL);
        JSONObject mainObject = (JSONObject) new JSONParser().parse(reader);
        String name = String.valueOf(mainObject.get("name"));
        Version oldVersion = VERSION;
        Version newVersion = new Version(name);
        return oldVersion.isOutdated(newVersion);
    }

    public static Update getLatestUpdate() throws Exception {
        InputStreamReader reader = openReader(LATEST_VERSIONS_URL);
        JSONObject mainObject = (JSONObject) new JSONParser().parse(reader);
        String versionName = String.valueOf(mainObject.get("name"));

        reader = openReader(LATEST_UPDATE_URL);
        mainObject = (JSONObject) new JSONParser().parse(reader);
        String title = String.valueOf(mainObject.get("title"));
        String id = String.valueOf(mainObject.get("id"));

        return new Update(versionName, title, id);
    }

    private static InputStreamReader openReader(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("User-Agent", USER_AGENT);
        InputStream inputStream = connection.getInputStream();
        return new InputStreamReader(inputStream);
    }


}
