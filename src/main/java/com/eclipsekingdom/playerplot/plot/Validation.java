package com.eclipsekingdom.playerplot.plot;

import com.eclipsekingdom.playerplot.config.Language;
import com.eclipsekingdom.playerplot.config.PluginConfig;
import com.eclipsekingdom.playerplot.util.PlotPoint;
import org.bukkit.Location;

import java.util.UUID;

public class Validation {

    public static NameStatus cleanName(String name, UUID playerID) {
        if (!name.matches("^[a-zA-Z0-9\\_\\-]+$")) {
            return NameStatus.SPECIAL_CHARACTERS;
        } else if (name.length() > 20) {
            return NameStatus.TOO_LONG;
        } else if (name.toLowerCase().equals(Language.MISC_HERE.toString())) {
            return NameStatus.RESERVED_NAME;
        } else {
            if (PlotCache.getPlayerPlot(playerID, name) != null) {
                return NameStatus.NAME_TAKEN;
            } else {
                return NameStatus.VALID;
            }
        }
    }

    public enum NameStatus {

        VALID(""),
        SPECIAL_CHARACTERS(Language.STATUS_SPECIAL_CHARACTERS.toString()),
        TOO_LONG(Language.STATUS_TOO_LONG.toString()),
        NAME_TAKEN(Language.STATUS_NAME_TAKEN.toString()),
        RESERVED_NAME(Language.STATUS_RESERVED_NAME.toString()),
        ;

        NameStatus(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        private final String message;

    }

    public static RegionStatus canPlotBeRegisteredAt(Location center, int sideLength, UUID selfID) {

        if (!PluginConfig.isAllowedPlotWorld(center.getWorld())) {
            return RegionStatus.INVALID_WORLD;
        } else if (overlapsPlayerPlot(center, sideLength, selfID)) {
            return RegionStatus.REGION_OCCUPIED;
        } else if (overlapsTownisPlot(center, sideLength)) {
            return RegionStatus.REGION_OCCUPIED;
        } else {
            return RegionStatus.VALID;
        }

    }

    public static RegionStatus canPlotBeUpgradedAt(String world, PlotPoint center, int sideLength, UUID selfID) {
        Location centerLoc = center.asLocationParts(world).getLocation();
        if(centerLoc != null){
            if (!PluginConfig.isAllowedPlotWorld(centerLoc.getWorld())) {
                return RegionStatus.INVALID_WORLD;
            } else if (overlapsPlayerPlot(centerLoc, sideLength, selfID)) {
                return RegionStatus.REGION_OCCUPIED;
            } else if (overlapsTownisPlot(centerLoc, sideLength)) {
                return RegionStatus.REGION_OCCUPIED;
            } else {
                return RegionStatus.VALID;
            }
        } else {
            return RegionStatus.INVALID_WORLD;
        }
    }


    public static boolean overlapsPlayerPlot(Location center, int sideLength, UUID selfID) {
        for (Plot plot : PlotCache.getPlotsNear(center, sideLength)) {
            if (!plot.getID().equals(selfID)) {
                boolean existingPlotBigger = plot.getSideLength() >= sideLength;
                PlotPoint[] existingCorners = plot.getCorners();
                PlotPoint[] targetCorners = PlotPoint.fromLocation(center).getCorners(sideLength);
                PlotPoint[] biggerPoints = existingPlotBigger ? existingCorners : targetCorners;
                PlotPoint[] smallerPoints = existingPlotBigger ? targetCorners : existingCorners;
                if (overlaps(biggerPoints, smallerPoints)) return true;
            }
        }

        return false;
    }

    private static boolean overlapsTownisPlot(Location center, int sideLength) {
        return false;
    }

    private static boolean overlaps(PlotPoint[] biggerPlot, PlotPoint[] smallerPlot) {
        boolean overlaps = false;
        for (PlotPoint plotCorner : smallerPlot) {
            if (regionContainsPoint(biggerPlot, plotCorner)) {
                overlaps = true;
                break;
            }
        }
        return overlaps;
    }

    private static boolean regionContainsPoint(PlotPoint[] container, PlotPoint plotCorner) {
        PlotPoint min = container[0];
        PlotPoint max = container[1];
        boolean withinXRange = (min.getX() <= plotCorner.getX() && plotCorner.getX() <= max.getX());
        boolean withinZRange = (min.getZ() <= plotCorner.getZ() && plotCorner.getZ() <= max.getZ());
        return (withinXRange && withinZRange);
    }


    public enum RegionStatus {

        VALID(""),
        INVALID_WORLD(Language.STATUS_INVALID_WORLD.toString()),
        REGION_OCCUPIED(Language.STATUS_REGION_OCCUPIED.toString());

        RegionStatus(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        private final String message;

    }


}
