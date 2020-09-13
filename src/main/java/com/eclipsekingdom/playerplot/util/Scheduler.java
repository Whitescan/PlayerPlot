package com.eclipsekingdom.playerplot.util;

import com.eclipsekingdom.playerplot.PlayerPlot;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Scheduler {

    private static Plugin plugin = PlayerPlot.getPlugin();
    private static BukkitScheduler scheduler = Bukkit.getScheduler();

    public static void run(Runnable r) {
        scheduler.runTask(plugin, r);
    }

    public static void runAsync(Runnable r) {
        scheduler.runTaskAsynchronously(plugin, r);
    }

    public static void runLater(Runnable r, int ticks) {
        scheduler.runTaskLater(plugin, r, ticks);
    }

    public static void runLaterAsync(Runnable r, int ticks) {
        scheduler.runTaskLaterAsynchronously(plugin, r, ticks);
    }

}
