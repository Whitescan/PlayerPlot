package de.whitescan.playerplot.util.border;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.whitescan.playerplot.PlayerPlot;

public class PlayerPusher extends BukkitRunnable {

	private static final double SPEED = 0.025;

	private Player player;
	private World world;
	private double x;
	private double z;
	private double bx1;
	private double bz1;
	private double bx2;
	private double bz2;
	private boolean cancelled = false;

	public PlayerPusher(Player player, double x, double z, double size) {
		double rad = size / 2.0;
		this.x = x;
		this.z = z;
		this.bx1 = x - rad;
		this.bz1 = z - rad;
		this.bx2 = x + rad;
		this.bz2 = z + rad;
		this.player = player;
		this.world = player.getWorld();
		this.player = player;
		runTaskTimer(PlayerPlot.getPlugin(), 0, 5);
	}

	@Override
	public void run() {
		Location playerLoc = player.getLocation();
		double px = playerLoc.getX();
		double pz = playerLoc.getZ();
		if (px - bx1 <= -0.2 || bx2 - px <= -0.2) {
			Location towards = new Location(world, x, playerLoc.getY(), playerLoc.getZ(), 0, 0);
			player.setVelocity(towards.subtract(playerLoc).toVector().multiply(SPEED));
		} else if (pz - bz1 <= -0.2 || bz2 - pz < -0.2) {
			Location towards = new Location(world, playerLoc.getX(), playerLoc.getY(), z, 0, 0);
			player.setVelocity(towards.subtract(playerLoc).toVector().multiply(SPEED));
		} else {
			cancel();
		}
	}

	@Override
	public void cancel() {
		super.cancel();
		this.cancelled = true;
	}

	public boolean isCancelled() {
		return cancelled;
	}

}
