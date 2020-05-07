package com.eclipsekingdom.playerplot.plot.validation;

import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.plot.Plot;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static com.eclipsekingdom.playerplot.sys.lang.Message.WARN_PLOT_NOT_FOUND;

public class RemoteValidation {

    public static Status clean(Player player, String[] args, String formatString) {
        if (args.length > 0) {
            String targetName = args[0];
            Plot target = PlotCache.getPlayerPlot(player.getUniqueId(), targetName);
            if (target != null) {
                return Status.VALID;
            } else {
                return Status.PLOT_NOT_FOUND.setMessage(WARN_PLOT_NOT_FOUND.coloredFromPlot(targetName, ChatColor.RED, ChatColor.DARK_PURPLE));
            }
        } else {
            return Status.INVALID_FORMAT.setMessage(formatString);
        }
    }

    public enum Status {

        VALID(""),
        PLOT_NOT_FOUND(""),
        INVALID_FORMAT(""),
        ;

        private String message;

        Status(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public Status setMessage(String message) {
            this.message = message;
            return this;
        }


    }

}
