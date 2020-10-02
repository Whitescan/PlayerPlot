package com.eclipsekingdom.playerplot.sys;

import com.eclipsekingdom.playerplot.sys.config.PluginConfig;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public enum Language {

    PLUGIN_DESCRIPTION("Plugin - description", "Player Plot is a self-serve protection plugin. It allows users to unlock and claim protected regions."),
    PLUGIN_READ_MORE("Plugin - read more", "Read more on the [link]."),
    PLUGIN_WIKI("Plugin - wiki", "Player Plot wiki"),
    PLUGIN_OPTIONS("Plugin - options", "Options"),
    PLUGIN_HELP("Plugin - help", "show plugin commands"),
    PLUGIN_INFO("Plugin - info", "get plugin information"),
    PLUGIN_AUTHOR("Plugin - author", "Author"),
    PLUGIN_VERSION("Plugin - version", "Version"),
    PLUGIN_NEW_UPDATE("Plugin - new update", "A new version of Player Plot is available."),
    PLUGIN_VIEW_UPDATE_NOTES("Plugin - view update notes", "View on [link]."),
    PLUGIN_UPDATE_ERROR("Plugin - update error", "Unable to fetch update."),
    PLUGIN_UP_TO_DATE("Plugin - up to date", "Plugin is up to date."),
    PLUGIN_RELOAD("Plugin - reload", "Reload complete."),

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
    LABEL_SWAMP_PLOT_DEED("Label - swamp plot deed", "Swamp Plot Deed"),
    LABEL_MOUNTAIN_PLOT_DEED("Label - mountain plot deed", "Mountain Plot Deed"),
    LABEL_OCEAN_PLOT_DEED("Label - ocean plot deed", "Ocean Plot Deed"),
    LABEL_PLOT_DEEDS("Label - plot deeds", "Plot Deeds"),
    LABEL_ALL_PLOTS("Label - all plots", "All Plots"),
    LABEL_CONFIRM("Label - confirm", "Confirm"),
    LABEL_CANCEL("Label - cancel", "Cancel"),

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
    HELP_PLOT_SET_CENTER("Help - plot setcenter", "set plot center"),
    HELP_PLOT_SET_SPAWN("Help - plot setspawn", "set plot spawn point"),
    HELP_TOPLOT("Help - toplot", "teleport to plot"),
    HELP_PLOT_DEED("Help - plotdeed", "give plot deeds to player"),
    HELP_WRITE_DEED("Help - writedeed", "write plot deeds"),
    HELP_ALL_PLOTS("Help - allplots", "view all plots"),
    HELP_DEL_PLOT("help - delplot", "delete plot"),

    CONSOLE_DETECT("Console - plugin detected", "%plugin% detected"),

    ARG_AMOUNT("Arg - amount", "amount"),
    ARG_PLAYER("Arg - player", "player"),
    ARG_NAME("Arg - name", "name"),
    ARG_PLOT("Arg - plot", "plot"),
    ARG_PLOT_DEED("Arg - plot deed", "plot deed"),

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
    WARN_NOT_STANDING_IN_PLOT("Warn - not standing in plot", "You are not standing in a plot."),
    WARN_NOT_OWNER("Warn - not owner of plot", "You are not the owner of this plot."),
    WARN_OUTSIDE_PLOT_BOUNDS("Warn - outside plot bounds", "Spawn must be within plot bounds."),
    WARN_UNKNOWN_TYPE("Warn - unknown type", "Unknown type."),
    WARN_INSUFFICIENT_UNCLAIMED_PLOTS("Warn - insufficient unclaimed plots", "You do not have enough unclaimed plots."),
    WARN_NO_UNLOCKED_PLOTS("Warn - no unlocked plots", "You can only write deeds for unlocked plots."),
    WARN_NO_DELETE_REQUESTS("Warn - no delete requests", "You do not have any pending plot deletion requests."),

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
    SUCCESS_REQUEST_CANCEL("Success - request cancel", "Request cancelled."),
    SUCCESS_ITEMS_SENT("Success - items sent", "%amount% items sent to %player%"),
    SUCCESS_SPAWN_SET("Success - spawn set", "Spawn point set."),
    SUCCESS_WRITE_DEED("Success - write deed", "You wrote %amount% plot deeds."),

    MISC_HERE("Misc - here", "here"),
    MISC_FORMAT("Misc - format", "Format is %format%"),
    MISC_ONE_USE("Misc - one use", "1 use only - click to activate"),
    MISC_PLOT_DEED_LORE("Misc - plot deed lore", "a serious looking piece of paper"),
    MISC_OPTIONAL("Misc - optional", "optional"),
    MISC_VARIABLE("Misc - variable", "variable"),

    STATUS_REGION_OCCUPIED("Status - region occupied", "Another region is too close. Your plot would overlap."),
    STATUS_SPECIAL_CHARACTERS("Status - name special characters", "Plot names must consist of only a-z, A-Z, 0-9, _, and -"),
    STATUS_TOO_LONG("Status - name too long", "Plot names must be 20 characters or less"),
    STATUS_NAME_TAKEN("Status - name taken", "You already have a plot with that name"),
    STATUS_RESERVED_NAME("Status - reserved name", "That name is reserved by Player Plot"),

    INFO_CONFIRM_DELETE("Info - confirm delete", "Delete %player%'s plot? [confirm] [cancel]"),
    INFO_CONFIRM_HOVER("Info - hover confirm", "» Click to confirm"),
    INFO_CANCEL_HOVER("Info - hover cancel", "» Click to cancel"),
    INFO_REQUEST_DURATION("Info - request duration", "The request is valid for %seconds% seconds."),

    SCANNER_PLOT_OVERVIEW("Scanner - plot overview", "%plot% ~ %player% ~"),
    SCANNER_NO_PLOTS("Scanner - no plots", "No plots detected."),

    ;

    public static void load() {
        File file = new File("plugins/PlayerPlot/Locale", PluginConfig.getLanguageFile() + ".yml");
        if (file.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            try {
                for (Language message : Language.values()) {
                    MessageSetting setting = message.getMessageSetting();
                    if (config.contains(setting.getLabel())) {
                        setting.setMessage(config.getString(setting.getLabel(), setting.getMessage()));
                    } else {
                        config.set(setting.getLabel(), setting.getMessage());
                    }
                }
                config.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void reload() {
        load();
    }

    public class MessageSetting {

        private String label;
        private String message;

        public MessageSetting(String label, String message) {
            this.label = label;
            this.message = message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getLabel() {
            return label;
        }

        public String getMessage() {
            return message;
        }

    }

    private MessageSetting messageSetting;

    Language(String messageSetting, String messageDefault) {
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

    public String fromPlayerAndPlot(String playerName, String plotName) {
        return get().replaceAll("%player%", playerName).replaceAll("%plot%", plotName);
    }

    public String fromPlayerAndAmount(String playerName, int amount) {
        return get().replaceAll("%player%", playerName).replaceAll("%amount%", String.valueOf(amount));
    }

    public String fromAmount(int amount) {
        return get().replaceAll("%amount%", String.valueOf(amount));
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

    public String fromSeconds(int seconds) {
        return get().replaceAll("%seconds%", String.valueOf(seconds));
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

    public TextComponent getWithLink(ChatColor baseColor, String name, String link) {
        String string = get();
        TextComponent linkComponent = new TextComponent(name);
        linkComponent.setColor(ChatColor.AQUA.asBungee());
        linkComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
        TextComponent base = new TextComponent("");
        base.setColor(baseColor.asBungee());
        String[] subs = string.split("\\[link\\]");
        for (int i = 0; i < subs.length; i++) {
            TextComponent textComponent = new TextComponent(subs[i]);
            textComponent.setColor(baseColor.asBungee());
            base.addExtra(textComponent);
            if (i < subs.length - 1) {
                base.addExtra(linkComponent);
            }
        }
        return base;
    }

    public TextComponent getWithPlayerConfirmDeny(ChatColor baseColor, ChatColor playerColor, String playerName, String confirmCommand, String cancelCommand) {
        String string = get();

        TextComponent confirmComponent = new TextComponent("[" + Language.LABEL_CONFIRM.toString() + "]");
        confirmComponent.setColor(ChatColor.GREEN.asBungee());
        confirmComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + Language.INFO_CONFIRM_HOVER.toString())));
        confirmComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, confirmCommand));

        TextComponent cancelComponent = new TextComponent("[" + Language.LABEL_CANCEL.toString() + "]");
        cancelComponent.setColor(ChatColor.RED.asBungee());
        cancelComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + Language.INFO_CANCEL_HOVER.toString())));
        cancelComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cancelCommand));

        TextComponent playerComponent = new TextComponent(playerName);
        playerComponent.setColor(playerColor.asBungee());

        TextComponent base = new TextComponent();
        base.setColor(baseColor.asBungee());

        String[] confirmSubs = string.split("\\[confirm\\]");
        for (int i = 0; i < confirmSubs.length; i++) {

            TextComponent confirmSub = new TextComponent();
            confirmSub.setColor(baseColor.asBungee());

            String[] cancelSubs = confirmSubs[i].split("\\[cancel\\]");
            for (int j = 0; j < cancelSubs.length; j++) {

                TextComponent cancelSub = new TextComponent();
                cancelSub.setColor(baseColor.asBungee());

                String[] playerSubs = cancelSubs[j].split("%player%");
                for (int k = 0; k < playerSubs.length; k++) {

                    TextComponent playerSub = new TextComponent(playerSubs[k]);
                    playerSub.setColor(baseColor.asBungee());

                    cancelSub.addExtra(playerSub);
                    if (k < playerSubs.length - 1 || cancelSubs[j].endsWith("%player%")) cancelSub.addExtra(playerComponent);

                }

                confirmSub.addExtra(cancelSub);
                if (j < cancelSubs.length - 1 || confirmSubs[i].endsWith("[cancel]")) confirmSub.addExtra(cancelComponent);

            }

            base.addExtra(confirmSub);
            if (i < confirmSubs.length - 1 || string.endsWith("[confirm]")) base.addExtra(confirmComponent);

        }


        return base;
    }

}
