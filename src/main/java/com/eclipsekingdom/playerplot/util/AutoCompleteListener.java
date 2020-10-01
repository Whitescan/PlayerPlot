package com.eclipsekingdom.playerplot.util;

import com.eclipsekingdom.playerplot.PlayerPlot;
import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.plotdeed.PlotDeedType;
import com.eclipsekingdom.playerplot.plot.Plot;
import com.eclipsekingdom.playerplot.sys.config.PluginConfig;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutoCompleteListener implements Listener {

    public AutoCompleteListener() {
        Plugin plugin = PlayerPlot.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onComplete(TabCompleteEvent e) {
        if (e.getSender() instanceof Player) {
            String rootCommand = PluginConfig.getRootCommand();
            String buffer = e.getBuffer();
            Player player = (Player) e.getSender();
            if (buffer.startsWith("/playerplot ")) {
                int args = numberOfFullArgs(buffer);
                if (args == 0) {
                    String root = "/playerplot";
                    e.setCompletions(getRefinedCompletions(root, buffer, PLUGIN_COMPLETIONS));
                } else {
                    e.setCompletions(Collections.EMPTY_LIST);
                }
            } else if (buffer.startsWith("/" + rootCommand + " @")) {
                int args = numberOfFullArgs(buffer);
                if (args == 0) {
                    String root = "/" + rootCommand;
                    e.setCompletions(getRefinedCompletions(root, buffer, getAtPlotNames(player)));
                } else if (args == 1) {
                    String root = "/" + rootCommand + " " + getArg(buffer, 0);
                    e.setCompletions(getRefinedCompletions(root, buffer, PLOT_ACTION_COMPLETIONS));
                } else if (args == 2) {
                    String base = "/" + rootCommand + " " + getArg(buffer, 0);
                    if (buffer.startsWith(base + " trust ")) {
                        String root = base + " trust";
                        e.setCompletions(getRefinedCompletions(root, buffer, onlineCompletions(player)));
                    } else if (buffer.startsWith(base + " untrust ")) {
                        String root = base + " untrust";
                        e.setCompletions(getRefinedCompletions(root, buffer, onlineCompletions(player)));
                    } else if (buffer.startsWith(base + " rename ")) {
                        e.setCompletions(Collections.EMPTY_LIST);
                    }
                }
            } else if (buffer.startsWith("/" + rootCommand + " ")) {
                int args = numberOfFullArgs(buffer);
                if (args == 0) {
                    String root = "/" + rootCommand;
                    e.setCompletions(getRefinedCompletions(root, buffer, PLOT_COMPLETIONS));
                } else if (args == 1) {
                    String base = "/" + rootCommand;
                    if (buffer.startsWith(base + " trust ")) {
                        String root = base + " trust";
                        e.setCompletions(getRefinedCompletions(root, buffer, onlineCompletions(player)));
                    } else if (buffer.startsWith(base + " untrust ")) {
                        String root = base + " untrust";
                        e.setCompletions(getRefinedCompletions(root, buffer, onlineCompletions(player)));
                    } else if (buffer.startsWith(base + " rename ") || buffer.startsWith(base + " claim ")) {
                        e.setCompletions(Collections.EMPTY_LIST);
                    } else if (buffer.startsWith(base + " scan ")) {
                        String root = base + " scan";
                        e.setCompletions(getRefinedCompletions(root, buffer, SCAN_COMPLETIONS));
                    }
                }
            } else if (buffer.startsWith("/toplot ")) {
                String root = "/toplot";
                e.setCompletions(getRefinedCompletions(root, buffer, getPlotNames(player)));
            } else if (buffer.startsWith("/plotdeed")) {
                int args = numberOfFullArgs(buffer);
                if (args == 0) {
                    String root = "/plotdeed";
                    List<String> completions = new ArrayList<>();
                    completions.addAll(PLOT_DEED_COMPLETIONS);
                    completions.add("list");
                    e.setCompletions(getRefinedCompletions(root, buffer, completions));
                } else if (args == 1) {
                    String root = "/plotdeed " + getArg(buffer, 0);
                    e.setCompletions(getRefinedCompletions(root, buffer, onlineCompletions()));
                } else {
                    e.setCompletions(Collections.EMPTY_LIST);
                }
            } else if (buffer.startsWith("/writedeed")){
                e.setCompletions(Collections.EMPTY_LIST);
            }
        }
    }

    private List<String> getRefinedCompletions(String root, String buffer, List<String> completions) {
        if (buffer.equalsIgnoreCase(root + " ")) {
            return completions;
        } else {
            List<String> refinedCompletions = new ArrayList<>();
            String bufferFromRoot = buffer.split(root + " ")[1];
            for (String completion : completions) {
                if (bufferFromRoot.length() < completion.length()) {
                    if (completion.substring(0, bufferFromRoot.length()).equalsIgnoreCase(bufferFromRoot)) {
                        refinedCompletions.add(completion);
                    }
                }
            }
            return refinedCompletions;
        }
    }

    private int numberOfFullArgs(String buffer) {
        int lastNotCompletedPenalty = endsInSpace(buffer) ? 0 : -1;
        return buffer.split(" ").length - 1 + lastNotCompletedPenalty;
    }

    private boolean endsInSpace(String buffer) {
        return ' ' == buffer.charAt(buffer.length() - 1);
    }

    private String getArg(String buffer, int arg) {
        return buffer.split(" ")[arg + 1];
    }

    private static final List<String> SCAN_COMPLETIONS = Collections.singletonList("-l");

    private static final List<String> PLOT_COMPLETIONS = ImmutableList.<String>builder()
            .add("help")
            .add("scan")
            .add("claim")
            .add("list")
            .add("flist")
            .add("rename")
            .add("free")
            .add("info")
            .add("trust")
            .add("untrust")
            .add("upgrade")
            .add("downgrade")
            .add("setcenter")
            .add("setspawn")
            .build();


    private static final List<String> PLUGIN_COMPLETIONS = ImmutableList.<String>builder()
            .add("help")
            .add("info")
            .add("update")
            .add("reload")
            .build();

    private static final List<String> PLOT_ACTION_COMPLETIONS = ImmutableList.<String>builder()
            .add("rename")
            .add("free")
            .add("info")
            .add("trust")
            .add("untrust")
            .add("upgrade")
            .add("downgrade")
            .add("setcenter")
            .build();

    private List<String> getAtPlotNames(Player player) {
        List<String> plotNames = new ArrayList<>();
        for (Plot plot : PlotCache.getPlayerPlots(player.getUniqueId())) {
            plotNames.add("@" + plot.getName());
        }
        plotNames.add("@here");
        return plotNames;
    }

    private List<String> getPlotNames(Player player) {
        List<String> plotNames = new ArrayList<>();
        for (Plot plot : PlotCache.getPlayerPlots(player.getUniqueId())) {
            plotNames.add(plot.getName());
        }
        return plotNames;
    }

    private static List<String> onlineCompletions(Player player) {
        List<String> onlinePlayerName = new ArrayList<>();
        for (Player oPlayer : Bukkit.getOnlinePlayers()) {
            if (oPlayer != player) {
                onlinePlayerName.add(oPlayer.getName());
            }
        }
        return onlinePlayerName;
    }

    private static List<String> onlineCompletions() {
        List<String> onlinePlayerName = new ArrayList<>();
        for (Player oPlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayerName.add(oPlayer.getName());
        }
        return onlinePlayerName;
    }

    public static final List<String> PLOT_DEED_COMPLETIONS = buildPlotDeedTypes();

    public static List<String> buildPlotDeedTypes() {
        List<String> plotDeedTypes = new ArrayList<>();
        for (PlotDeedType plotDeedType : PlotDeedType.values()) {
            plotDeedTypes.add(plotDeedType.toString());
        }
        return plotDeedTypes;
    }

}
