package com.eclipsekingdom.playerplot.util.update;

public class Update {

    public static final String UPDATE_NOTES_URL = "https://www.spigotmc.org/resources/" + Spiget.PLUGIN_ID + "/update?update=";
    private String versionName;
    private String title;
    private String id;
    private String updateNotesUrl;

    public Update(String versionName, String title, String id) {
        this.versionName = versionName;
        this.title = title;
        this.id = id;
        this.updateNotesUrl = UPDATE_NOTES_URL + id;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getTitle() {
        return title;
    }

    public String getUpdateNotesUrl() {
        return updateNotesUrl;
    }

}
