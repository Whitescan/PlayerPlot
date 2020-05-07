package com.eclipsekingdom.playerplot.util.scanner;

import com.eclipsekingdom.playerplot.plot.Plot;
import org.bukkit.entity.Player;

public interface IBorder {
    void show(Player player, Plot plot);
    void hide(Player player);
}
