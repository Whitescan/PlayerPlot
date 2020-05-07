package com.eclipsekingdom.playerplot.util;

import com.eclipsekingdom.playerplot.plot.validation.StandingValidation;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StandingAction {

    private Player player;
    private Runnable runnable;

    public StandingAction(Player player, Runnable runnable) {
        this.player = player;
        this.runnable = runnable;
    }

    public void run() {
        StandingValidation.Status status = StandingValidation.clean(player);
        if (status == StandingValidation.Status.VALID) {
            runnable.run();
        } else {
            player.sendMessage(ChatColor.RED + status.getMessage());
        }
    }


}
