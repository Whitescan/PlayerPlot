package de.whitescan.playerplot.util;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationParts {

	private Location location;
	private String worldName;
	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;

	public LocationParts(Location location) {
		this.location = location;
		this.worldName = location.getWorld().getName();
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.yaw = location.getYaw();
		this.pitch = location.getPitch();
	}

	public LocationParts(String worldName, double x, double y, double z, float yaw, float pitch) {
		this.worldName = worldName;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public Location getLocation() {
		if (location == null) {
			World world = Bukkit.getWorld(worldName);
			if (world != null) {
				this.location = new Location(world, x, y, z, yaw, pitch);
			}
		}
		return location;
	}

	public String getWorldName() {
		return worldName;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	@Override
	public String toString() {
		return worldName + "_" + x + "_" + y + "_" + z + "_" + yaw + "_" + pitch;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Location) {
			Location l = (Location) o;
			return this.worldName.equals(l.getWorld().getName()) && this.x == l.getX() && this.y == l.getY()
					&& this.z == l.getZ() && this.yaw == location.getYaw() && this.pitch == location.getPitch();
		} else if (o instanceof LocationParts) {
			LocationParts l = (LocationParts) o;
			return this.worldName.equals(l.worldName) && this.x == l.x && this.y == l.y && this.z == l.z
					&& this.yaw == l.yaw && this.pitch == l.pitch;
		} else {
			return super.equals(o);
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(worldName).append(x).append(y).append(z).append(yaw).append(pitch)
				.toHashCode();
	}

	public static LocationParts parseParts(String string) {
		try {
			String[] parts = string.split("_");
			int partsLength = parts.length;
			String worldName = parts[0];
			for (int i = 1; i < partsLength - 5; i++) {
				worldName += ("_" + parts[i]);
			}
			double x = Double.parseDouble(parts[partsLength - 5]);
			double y = Double.parseDouble(parts[partsLength - 4]);
			double z = Double.parseDouble(parts[partsLength - 3]);
			float yaw = Float.parseFloat(parts[partsLength - 2]);
			float pitch = Float.parseFloat(parts[partsLength - 1]);
			return new LocationParts(worldName, x, y, z, yaw, pitch);
		} catch (Exception e) {
			return null;
		}
	}

}
