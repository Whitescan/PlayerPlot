package com.eclipsekingdom.playerplot.util;

import com.eclipsekingdom.playerplot.plot.validation.RemoteValidation;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RemoteAction {

    private Player player;
    private Runnable runnable;
    private String[] args;
    private String formatMessage;

    public RemoteAction(Player player, String[] args, String formatMessage, Runnable runnable) {
        this.player = player;
        this.runnable = runnable;
        this.args = args;
        this.formatMessage = formatMessage;
    }

    public void run() {
        RemoteValidation.Status status = RemoteValidation.clean(player, args, formatMessage);
        if (status == RemoteValidation.Status.VALID) {
            runnable.run();
        } else {
            player.sendMessage(ChatColor.RED + status.getMessage());
        }
    }


}
