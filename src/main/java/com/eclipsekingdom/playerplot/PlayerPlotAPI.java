package com.eclipsekingdom.playerplot;

import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.data.UserCache;
import com.eclipsekingdom.playerplot.data.UserData;
import com.eclipsekingdom.playerplot.plot.Plot;
import org.bukkit.entity.Player;

import org.bukkit.Location;

public class PlayerPlotAPI {

    private PlayerPlotAPI() {
    }

    private static PlayerPlotAPI playerPlotAPI = new PlayerPlotAPI();

    public static PlayerPlotAPI getInstance() {
        return playerPlotAPI;
    }

    public boolean hasAccessAt(Player player, Location location) {
        Plot plot = PlotCache.getPlot(location);
        return plot != null? plot.isAllowed(player) : true;
    }

    public boolean isPlotAt(Location location){
        return PlotCache.getPlot(location) != null;
    }

    public Plot getPlotAt(Location location){
        return PlotCache.getPlot(location);
    }

    public boolean hasUserData(Player player){
        return UserCache.hasData(player.getUniqueId());
    }

    public UserData getUserData(Player player){
        return UserCache.getData(player.getUniqueId());

    }


}
