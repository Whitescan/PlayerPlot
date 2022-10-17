package de.whitescan.playerplot.logic;

import lombok.Getter;

public class UserData {

	@Getter
	private int unlockedPlots;

	public UserData(int unlockedPlots) {
		this.unlockedPlots = unlockedPlots;
	}

	public UserData() {
		this(0);
	}

	public void unlockPlot() {
		unlockedPlots++;
	}

	public void lockPlots(int amount) {
		unlockedPlots -= amount;
	}

}
