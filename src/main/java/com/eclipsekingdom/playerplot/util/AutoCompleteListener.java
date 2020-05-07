package com.eclipsekingdom.playerplot.util;

import com.eclipsekingdom.playerplot.PlayerPlot;
import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.plot.Plot;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteListener implements Listener {

    public AutoCompleteListener() {
        Plugin plugin = PlayerPlot.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onComplete(TabCompleteEvent e) {
        if (e.getSender() instanceof Player) {
            String buffer = e.getBuffer();
            Player player = (Player) e.getSender();
            if (buffer.contains("/plot trust ")) {
                e.setCompletions(getRefinedCompletions("/plot trust", buffer, onlineCompletions(player)));
            } else if (buffer.contains("/plot untrust ")) {
                e.setCompletions(getRefinedCompletions("/plot untrust", buffer, onlineCompletions(player)));
            } else if (buffer.contains("/plot ")) {
                e.setCompletions(getRefinedCompletions("/plot", buffer, plotCompletions));
            } else if (buffer.contains("/rplot ") && numberOfFullArgs(buffer) > 0) {
                String root = "/rplot " + getArg(buffer, 0);
                e.setCompletions(getRefinedCompletions(root, buffer, rPlotCompletions));
            } else if (buffer.contains("/rplot ")) {
                e.setCompletions(getRefinedCompletions("/rplot", buffer, getPlotNames(player)));
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

    private static final List<String> plotCompletions = ImmutableList.<String>builder()
            .add("help")
            .add("scan")
            .add("claim")
            .add("rename")
            .add("free")
            .add("info")
            .add("list")
            .add("flist")
            .add("trust")
            .add("untrust")
            .add("upgrade")
            .add("downgrade")
            .add("setcenter")
            .build();

    private static final List<String> rPlotCompletions = ImmutableList.<String>builder()
            .add("rename")
            .add("free")
            .add("info")
            .add("setcenter")
            .build();

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
}
