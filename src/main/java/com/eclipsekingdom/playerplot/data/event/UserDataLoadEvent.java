package com.eclipsekingdom.playerplot.data.event;

import com.eclipsekingdom.playerplot.data.UserData;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

import static com.eclipsekingdom.playerplot.sys.Language.STATUS_LOAD_ERROR;

public class UserDataLoadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Result result;
    private final UUID playerID;
    private final UserData userData;

    public UserDataLoadEvent(Result result, UUID playerID, UserData userData) {
        this.result = result;
        this.playerID = playerID;
        this.userData = userData;
    }

    public final static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public final HandlerList getHandlers() {
        return handlers;
    }

    public final Result getResult() {
        return result;
    }

    public final UUID getPlayerID() {
        return playerID;
    }

    public final UserData getUserData() {
        return userData;
    }

    public boolean hasUserData() {
        return userData != null;
    }

    public enum Result {
        SUCCESS("Success"),
        ERROR(STATUS_LOAD_ERROR.toString()),
        ;

        private String message;

        Result(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}


