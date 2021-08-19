package me.sword7.playerplot.util;

public class PermInfo {

    private int plotBonus;
    private int plotMax;

    public PermInfo(int plotBonus, int plotMax) {
        this.plotBonus = plotBonus;
        this.plotMax = plotMax > 0 ? plotMax : 0;
    }

    public int getPlotBonus() {
        return plotBonus;
    }

    public int getPlotMax() {
        return plotMax;
    }

}
