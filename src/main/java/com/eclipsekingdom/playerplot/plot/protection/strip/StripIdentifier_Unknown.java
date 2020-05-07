package com.eclipsekingdom.playerplot.plot.protection.strip;

import org.bukkit.Material;

public class StripIdentifier_Unknown implements IStripIdentifier {

    @Override
    public boolean isStripLogAttempt(Material handMaterial, Material blockMaterial) {
        return false;
    }

}
