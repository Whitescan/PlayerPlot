package me.sword7.playerplot.plotdeed;

import me.sword7.playerplot.config.Language;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlotDeedLoot implements ILoot {


    @Override
    public ItemStack valueOf(String s) throws Exception {
        try {
            return PlotDeedType.valueOf(s).getPlotDeed().getItemStack();
        } catch (Exception e) {
            throw new IllegalAccessException();
        }
    }

    @Override
    public String getListLabel() {
        return Language.LABEL_PLOT_DEEDS.toString();
    }

    @Override
    public List<String> getLootTypes() {
        List<String> lootTypes = new ArrayList<>();
        for (PlotDeedType plotDeedType : PlotDeedType.values()) {
            lootTypes.add(plotDeedType.toString());
        }
        return lootTypes;
    }

    @Override
    public String getRoot() {
        return "plotdeed";
    }

    @Override
    public String getType() {
        return Language.ARG_PLOT_DEED.toString();
    }

    @Override
    public ItemStack getDefault() {
        return PlotDeedType.DEFAULT.getPlotDeed().getItemStack();
    }

}
