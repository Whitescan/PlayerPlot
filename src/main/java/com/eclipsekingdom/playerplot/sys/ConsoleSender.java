package com.eclipsekingdom.playerplot.sys;

import org.bukkit.Bukkit;

public class ConsoleSender {
    public static void sendMessage(String message){
        Bukkit.getConsoleSender().sendMessage("[PlayerPlot] " + message);
    }
}
