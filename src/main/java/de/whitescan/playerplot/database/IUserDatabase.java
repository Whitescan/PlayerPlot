package de.whitescan.playerplot.database;

import java.util.UUID;

import de.whitescan.playerplot.logic.UserData;

public interface IUserDatabase {

	public UserData fetch(UUID playerId);

	public void store(UUID playerId, UserData userData);

}
