package com.eclipsekingdom.playerplot.sys.lang;

import org.bukkit.ChatColor;

public enum Message {

    LABEL_COMMANDS("Label - commands", "Commands"),
    LABEL_PLOTS("Label - plots", "Plots"),
    LABEL_FRIEND_PLOTS("Label - friend plots", "Friend Plots"),
    LABEL_PAGE("Label - page", "Page"),
    LABEL_AREA("Label - area", "Area"),
    LABEL_CENTER("Label - center", "Center"),
    LABEL_MIN_CORNER("Label - min corner", "Min Corner"),
    LABEL_MAX_CORNER("Label - max corner", "Max Corner"),
    LABEL_WORLD("Label - world", "World"),
    LABEL_COMPONENTS("Label - components", "Components"),
    LABEL_FRIENDS("Label - friends", "Friends"),
    LABEL_PLOT_DEED("Label - plot deed", "Plot Deed"),

    HELP_PLOT_SCAN("Help - plot scan", "display plot boundary"),
    HELP_PLOT_CLAIM("Help - plot claim", "claim a plot"),
    HELP_PLOT_RENAME("Help - plot rename", "rename a plot"),
    HELP_PLOT_FREE("Help - plot free", "delete a plot"),
    HELP_PLOT_INFO("Help - plot info", "get plot details"),
    HELP_PLOT_LIST("Help - plot list", "list your plots"),
    HELP_PLOT_FLIST("Help - plot flist", "list friends' plots"),
    HELP_PLOT_TRUST("Help - plot trust", "add player to plot"),
    HELP_PLOT_UNTRUST("Help - plot untrust", "remove player from plot"),
    HELP_PLOT_UPGRADE("Help - plot upgrade", "increase plot size"),
    HELP_PLOT_DOWNGRADE("Help - plot downgrade", "decrease plot size"),
    HELP_PLOT_CENTER("Help - plot center", "set plot center"),
    HELP_PLOT_DEED("Help - plot deed", "give plot deeds to player"),

    CONSOLE_DETECT("Console - plugin detected", "%plugin% detected"),
    CONSOLE_FILE_ERROR("Console - file error", "Error saving %file%"),

    ARG_AMOUNT("Arg - amount", "amount"),
    ARG_PLAYER("Arg - player", "player"),
    ARG_NAME("Arg - name", "name"),

    WARN_NOT_PERMITTED("Warn - no permission", "You do not have permission for this command."),
    WARN_PLOT_LIMIT("Warn - plot limit", "You have reached your plot limit."),
    WARN_NOT_FRIEND("Warn - not friend", "%player% is not trusted by %plot%."),
    WARN_NOT_DOWNGRADEABLE("Warn - not downgradeable", "%plot% can not be downgraded any further."),
    WARN_ALREADY_FRIEND("Warn - already friend", "%player% is already added to %plot%."),
    WARN_ADD_SELF("Warn - add self", "You are already your own best friend!"),
    WARN_PLAYER_OFFLINE("Warn - player offline", "Player %player% is not online."),
    WARN_PLAYER_NOT_FOUND("Warn - player not found", "Player %player% not found"),
    WARN_PLOT_MAX("Warn - plot max", "You already have the maximum number of plots."),
    WARN_PROTECTED("Warn - protected", "This region is protected."),
    WARN_PLOT_NOT_FOUND("Warn - plot not found", "Plot %plot% not found."),

    SUCCESS_PLOT_UPGRADE("Success - plot upgrade", "%plot% was upgraded."),
    SUCCESS_PLOT_DOWNGRADE("Success - plot downgrade", "Plot %plot% was downgraded."),
    SUCCESS_PLOT_RENAME("Success - plot rename", "Plot successfully renamed to %plot%."),
    SUCCESS_PLOT_TRUST("Success - plot trust", "%player% was added to %plot%."),
    SUCCESS_PLOT_UNTRUST("Success - plot untrust", "%player% was removed from %plot%."),
    SUCCESS_PLOT_CLAIM("Success - plot claim", "Plot %plot% was claimed."),
    SUCCESS_PLOT_FREE("Success - plot free", "Plot %plot% was deleted."),
    SUCCESS_PLOT_CENTER("Success - plot center", "%plot% center updated."),
    SUCCESS_INVITED("Success - invited", "%player% has invited you to their plot, %plot%."),
    SUCCESS_PLOT_DELETE("Success - plot delete", "Plot deleted."),
    SUCCESS_ITEMS_SENT("Success - items sent", "items sent to %player%"),

    MISC_FORMAT("Misc - format", "Format is %format%"),
    MISC_ONE_USE("Misc - one use", "1 use only - click to activate"),
    MISC_PLOT_DEED_LORE("Misc - plot deed lore", "a serious looking piece of paper"),

    STATUS_NOT_STANDING_IN_PLOT("Status - not standing in plot", "You are not standing in a plot."),
    STATUS_NOT_OWNER("Status - not owner of plot", "You are not the owner of this plot."),
    STATUS_REGION_OCCUPIED("Status - region occupied", "Another region is too close. Your plot would overlap."),
    STATUS_SUCCESS("Status - success", "Success."),
    STATUS_LOAD_ERROR("Status - load error", "Unable to load data."),
    STATUS_UNLOADED_DATA("Status - unloaded data", "Your user data is unloaded. Fetching data . . ."),
    STATUS_SPECIAL_CHARACTERS("Status - name special characters", "Plot names must consist of only a-z, A-Z, 0-9, _, and -"),
    STATUS_TOO_LONG("Status - name too long", "Plot names must be 20 characters or less"),
    STATUS_NAME_TAKEN("Status - name taken", "You already have a plot with that name"),

    SCANNER_PLOT_OVERVIEW("Scanner - plot overview", "%plot% ~ %player% ~"),
    SCANNER_NO_PLOTS("Scanner - no plots", "No plots detected."),

    ;

    private MessageSetting messageSetting;

    Message(String messageSetting, String messageDefault) {
        this.messageSetting = new MessageSetting(messageSetting, messageDefault);
    }

    public MessageSetting getMessageSetting() {
        return messageSetting;
    }

    @Override
    public String toString() {
        return get();
    }

    private String get() {
        return ChatColor.translateAlternateColorCodes('&', messageSetting.getMessage());
    }

    public String fromFile(String fileName) {
        return get().replaceAll("%file%", fileName);
    }

    public String fromPlayerAndPlot(String playerName, String plotName) {
        return get().replaceAll("%player%", playerName).replaceAll("%plot%", plotName);
    }

    public String coloredFromPlayerAndPlot(String playerName, String plotName, ChatColor base, ChatColor highlight) {
        return base + get().replaceAll("%player%", highlight + playerName + base).replaceAll("%plot%", highlight + plotName + base);
    }

    public String coloredFromPlayer(String playerName, ChatColor base, ChatColor highlight) {
        return base + get().replaceAll("%player%", highlight + playerName + base);
    }

    public String coloredFromPlot(String plotName, ChatColor base, ChatColor highlight) {
        return base + get().replaceAll("%plot%", highlight + plotName + base);
    }

    public String fromPlugin(String pluginName) {
        return get().replaceAll("%plugin%", pluginName);
    }

    public String fromFormat(String format) {
        return get().replaceAll("%format%", format);
    }

    public String fromPlayer(String player) {
        return get().replaceAll("%player%", player);
    }

}
