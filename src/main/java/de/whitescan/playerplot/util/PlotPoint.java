package de.whitescan.playerplot.util;

import org.bukkit.Location;
import org.bukkit.World;

public class PlotPoint {

	private final int x;
	private final int z;

	public PlotPoint(int x, int z) {
		this.x = x;
		this.z = z;
	}

	public PlotPoint[] getCorners(int sideLength) {
		PlotPoint[] corners = new PlotPoint[4];
		corners[0] = getMinCorner(sideLength); // bottom left
		corners[1] = getMaxCorner(sideLength);// top right
		corners[2] = new PlotPoint(corners[0].getX(), corners[1].getZ());// top left
		corners[3] = new PlotPoint(corners[1].getX(), corners[0].getZ());// bottom right
		return corners;
	}

	public PlotPoint getMaxCorner(int sideLength) {
		int effectiveHalfLength = sideLength % 2 == 0 ? sideLength / 2 : (sideLength - 1) / 2;
		return new PlotPoint(x + effectiveHalfLength, z + effectiveHalfLength);
	}

	public PlotPoint getMinCorner(int sideLength) {
		int effectiveHalfLength = sideLength % 2 == 0 ? (sideLength / 2) - 1 : (sideLength - 1) / 2; // note the -1 when
																										// sideLength
																										// even
		return new PlotPoint(x - effectiveHalfLength, z - effectiveHalfLength);
	}

	public Location asLocation(World world) {
		return new Location(world, x, 64, z, 0, 0);
	}

	public LocationParts asLocationParts(String world) {
		return new LocationParts(world, x, 64, z, 0, 0);
	}

	public static PlotPoint fromLocation(Location location) {
		return new PlotPoint(location.getBlockX(), location.getBlockZ());
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

}
