package com.eclipsekingdom.playerplot.plot.protection.interactable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import org.bukkit.Tag;

import static org.bukkit.Material.*;

public class InteractableIdentifier_V1_14 implements IInteractableIdentifier {

    @Override
    public boolean isInteractable(Material material) {
        return interactableBlocks.contains(material) || hasInteractableTag(material);
    }

    private static ImmutableSet<Material> interactableBlocks = ImmutableSet.<Material>builder()

            .add(DAYLIGHT_DETECTOR)
            .add(CAULDRON)
            .add(TRAPPED_CHEST)
            .add(SPAWNER)
            .add(ITEM_FRAME)
            .add(LEVER)
            .add(MINECART)
            .add(HOPPER)
            .add(CHEST)
            .add(BREWING_STAND)
            .add(FURNACE)
            .add(ENDER_CHEST)
            .add(COMPARATOR)
            .add(ENCHANTING_TABLE)
            .add(NOTE_BLOCK)
            .add(ANVIL)
            .add(COMMAND_BLOCK)
            .add(BEACON)
            .add(DISPENSER)
            .add(DROPPER)

            .add(ACACIA_FENCE_GATE).add(DARK_OAK_FENCE_GATE).add(BIRCH_FENCE_GATE).add(JUNGLE_FENCE_GATE).add(OAK_FENCE_GATE).add(SPRUCE_FENCE_GATE)

            .add(SHULKER_BOX).add(BLACK_SHULKER_BOX).add(BLUE_SHULKER_BOX).add(BROWN_SHULKER_BOX).add(CYAN_SHULKER_BOX).add(GRAY_SHULKER_BOX).add(GREEN_SHULKER_BOX).add(LIGHT_BLUE_SHULKER_BOX)
            .add(LIGHT_GRAY_SHULKER_BOX).add(LIME_SHULKER_BOX).add(MAGENTA_SHULKER_BOX).add(ORANGE_SHULKER_BOX).add(PINK_SHULKER_BOX).add(PURPLE_SHULKER_BOX).add(RED_SHULKER_BOX).add(WHITE_SHULKER_BOX)
            .add(YELLOW_SHULKER_BOX)

            .add(BLAST_FURNACE)
            .add(SMOKER)
            .add(LOOM)
            .add(BARREL)

            .build();

    private boolean hasInteractableTag(Material material) {
        for (Tag tag : interactableBlockTags) {
            if (tag.isTagged(material)) {
                return true;
            }
        }
        return false;
    }

    private static ImmutableList<Tag> interactableBlockTags = ImmutableList.<Tag>builder()
            .add(Tag.BEDS)
            .add(Tag.DOORS)
            .add(Tag.BUTTONS)
            .add(Tag.TRAPDOORS)
            .build();

}
