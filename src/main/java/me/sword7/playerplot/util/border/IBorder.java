package me.sword7.playerplot.util.border;

import org.bukkit.World;
import org.bukkit.entity.Player;

public interface IBorder {

    void show(Player player, World world, double x, double z, double size);

    void hide(Player player, World world);
}
