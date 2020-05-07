package com.eclipsekingdom.playerplot.plot.validation;

import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.plot.Plot;
import org.bukkit.entity.Player;

import static com.eclipsekingdom.playerplot.sys.lang.Message.STATUS_NOT_OWNER;
import static com.eclipsekingdom.playerplot.sys.lang.Message.STATUS_NOT_STANDING_IN_PLOT;

public class StandingValidation {

    public static Status clean(Player player) {
        Plot requestedPlot = PlotCache.getPlot(player.getLocation());
        if (requestedPlot != null) {
            if (requestedPlot.getOwnerID().equals(player.getUniqueId())) {
                return Status.VALID;
            } else {
                return Status.NOT_OWNER_ERROR;
            }
        } else {
            return Status.NOT_STANDING_IN_PLOT;
        }
    }

    public enum Status {

        VALID(""),
        NOT_STANDING_IN_PLOT(STATUS_NOT_STANDING_IN_PLOT.toString()),
        NOT_OWNER_ERROR(STATUS_NOT_OWNER.toString());

        Status(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        private final String message;

    }


}
