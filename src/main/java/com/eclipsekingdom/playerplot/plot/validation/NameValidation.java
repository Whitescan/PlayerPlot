package com.eclipsekingdom.playerplot.plot.validation;

import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.sys.Language;

import java.util.UUID;

public class NameValidation {

    public static Status clean(String name, UUID playerID) {
        if (!name.matches("^[a-zA-Z0-9\\_\\-]+$")) {
            return Status.SPECIAL_CHARACTERS;
        } else if (name.length() > 20) {
            return Status.TOO_LONG;
        } else if (name.toLowerCase().equals(Language.MISC_HERE.toString())) {
            return Status.RESERVED_NAME;
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
        SPECIAL_CHARACTERS(Language.STATUS_SPECIAL_CHARACTERS.toString()),
        TOO_LONG(Language.STATUS_TOO_LONG.toString()),
        NAME_TAKEN(Language.STATUS_NAME_TAKEN.toString()),
        RESERVED_NAME(Language.STATUS_RESERVED_NAME.toString()),
        ;

        Status(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        private final String message;

    }

}
