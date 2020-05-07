package com.eclipsekingdom.playerplot.plot.validation;

import com.eclipsekingdom.playerplot.data.PlotCache;

import java.util.UUID;

import static com.eclipsekingdom.playerplot.sys.lang.Message.*;

public class NameValidation {

    public static Status clean(String name, UUID playerID) {
        if (!name.matches("^[a-zA-Z0-9\\_\\-]+$")) {
            return Status.SPECIAL_CHARACTERS;
        } else if (name.length() > 20) {
            return Status.TOO_LONG;
        } else {
            if (PlotCache.getPlayerPlot(playerID, name) != null) {
                return Status.NAME_TAKEN;
            } else {
                return Status.VALID;
            }
        }
    }

    public enum Status {

        VALID(""),
        SPECIAL_CHARACTERS(STATUS_SPECIAL_CHARACTERS.toString()),
        TOO_LONG(STATUS_TOO_LONG.toString()),
        NAME_TAKEN(STATUS_NAME_TAKEN.toString());

        Status(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        private final String message;

    }

}
