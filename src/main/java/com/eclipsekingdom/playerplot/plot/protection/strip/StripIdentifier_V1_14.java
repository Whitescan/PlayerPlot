package com.eclipsekingdom.playerplot.plot.protection.strip;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;

import java.util.Set;

import static org.bukkit.Material.*;

public class StripIdentifier_V1_14 implements IStripIdentifier {

    @Override
    public boolean isStripLogAttempt(Material handMaterial, Material blockMaterial) {
        return (axeItems.contains(handMaterial) && logItems.contains(blockMaterial));
    }

    private static Set<Material> logItems = ImmutableSet.<Material>builder()
            .add(DARK_OAK_DOOR)
            .add(ACACIA_DOOR)
            .add(BIRCH_DOOR)
            .add(JUNGLE_DOOR)
            .add(OAK_DOOR)
            .add(SPRUCE_DOOR).build();

    private static Set<Material> axeItems = ImmutableSet.<Material>builder()
            .add(WOODEN_AXE)
            .add(STONE_AXE)
            .add(IRON_AXE)
            .add(GOLDEN_AXE)
            .add(DIAMOND_AXE)
            .build();

}
