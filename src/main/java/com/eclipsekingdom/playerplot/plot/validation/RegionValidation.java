package com.eclipsekingdom.playerplot.plot.validation;

import com.eclipsekingdom.playerplot.data.PlotCache;
import com.eclipsekingdom.playerplot.plot.Plot;
import com.eclipsekingdom.playerplot.util.PlotPoint;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

import static com.eclipsekingdom.playerplot.sys.lang.Message.STATUS_REGION_OCCUPIED;

public class RegionValidation {

    public static Status canPlotBeRegisteredAt(Location center, int sideLength, UUID selfID) {

        if (overlapsPlayerPlot(center, sideLength, selfID)) {
            return Status.REGION_OCCUPIED;
        } else if (overlapsTownisPlot(center, sideLength)) {
            return Status.REGION_OCCUPIED;
        } else {
            return Status.VALID;
        }

    }

    public static Status canPlotBeUpgradedAt(World world, PlotPoint center, int sideLength, UUID selfID) {
        Location centerLoc = center.asLocation(world);
        if (overlapsPlayerPlot(center.asLocation(world), sideLength, selfID)) {
            return Status.REGION_OCCUPIED;
        } else if (overlapsTownisPlot(centerLoc, sideLength)) {
            return Status.REGION_OCCUPIED;
        } else {
            return Status.VALID;
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


    public enum Status {

        VALID(""),
        REGION_OCCUPIED(STATUS_REGION_OCCUPIED.toString());

        Status(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        private final String message;

    }


}
