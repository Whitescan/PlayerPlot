package com.eclipsekingdom.playerplot.integration;

import com.eclipsekingdom.apihero.ApiHero;
import com.eclipsekingdom.apihero.hero.region.RegionHero;
import com.eclipsekingdom.playerplot.PlayerPlot;
import com.eclipsekingdom.playerplot.plot.Plot;
import com.eclipsekingdom.playerplot.plot.PlotCache;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerPlotHero extends RegionHero {

    public PlayerPlotHero() {
        super(PlayerPlot.getPlugin());
        ApiHero.getRegistrar().register(this);
    }

    @Override
    public boolean isProtected(Location location) {
        return PlotCache.hasPlot(location);
    }

    @Override
    public boolean hasAccess(Player player, Location location) {
        Plot plot = PlotCache.getPlot(location);
        if (plot != null) {
            return plot.isAllowed(player);
        } else {
            return false;
        }
    }
}
