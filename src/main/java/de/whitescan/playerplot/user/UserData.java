package de.whitescan.playerplot.user;

public class UserData {

	private int unlockedPlots;

	public UserData() {
		this.unlockedPlots = 0;
	}

	public UserData(int unlockedPlots) {
		this.unlockedPlots = unlockedPlots;
	}

	public int getUnlockedPlots() {
		return unlockedPlots;
	}

	public void unlockPlot() {
		unlockedPlots++;
	}

	public void lockPlots(int amount) {
		unlockedPlots -= amount;
	}

}
