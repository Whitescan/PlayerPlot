package com.eclipsekingdom.playerplot.plot.protection.interactable;

import com.eclipsekingdom.playerplot.util.XMaterial;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;

import static org.bukkit.Material.*;

public class InteractableIdentifier_V1_11 implements IInteractableIdentifier {

    @Override
    public boolean isInteractable(Material material) {
        return interactableBlocks.contains(material);
    }

    private static ImmutableSet<Material> interactableBlocks = ImmutableSet.<Material>builder()

            .add(XMaterial.DAYLIGHT_DETECTOR.parseMaterial())
            .add(XMaterial.CAULDRON.parseMaterial())
            .add(TRAPPED_CHEST)
            .add(XMaterial.SPAWNER.parseMaterial())
            .add(ITEM_FRAME)
            .add(LEVER)
            .add(MINECART)
            .add(HOPPER)
            .add(XMaterial.CHEST.parseMaterial())
            .add(XMaterial.BREWING_STAND.parseMaterial())
            .add(XMaterial.FURNACE.parseMaterial())
            .add(ENDER_CHEST)
            .add(XMaterial.COMPARATOR.parseMaterial())
            .add(XMaterial.ENCHANTING_TABLE.parseMaterial())
            .add(NOTE_BLOCK)
            .add(ANVIL)
            .add(XMaterial.COMMAND_BLOCK.parseMaterial())
            .add(BEACON)
            .add(DISPENSER)
            .add(DROPPER)

            .add(ACACIA_FENCE_GATE).add(DARK_OAK_FENCE_GATE).add(BIRCH_FENCE_GATE).add(JUNGLE_FENCE_GATE).add(XMaterial.OAK_FENCE_GATE.parseMaterial()).add(SPRUCE_FENCE_GATE)

            .add(BLACK_SHULKER_BOX).add(BLUE_SHULKER_BOX).add(BROWN_SHULKER_BOX).add(CYAN_SHULKER_BOX).add(GRAY_SHULKER_BOX).add(GREEN_SHULKER_BOX).add(LIGHT_BLUE_SHULKER_BOX)
            .add(XMaterial.LIGHT_GRAY_SHULKER_BOX.parseMaterial()).add(LIME_SHULKER_BOX).add(MAGENTA_SHULKER_BOX).add(ORANGE_SHULKER_BOX).add(PINK_SHULKER_BOX).add(PURPLE_SHULKER_BOX).add(RED_SHULKER_BOX).add(WHITE_SHULKER_BOX)
            .add(YELLOW_SHULKER_BOX)

            .add(XMaterial.RED_BED.parseMaterial())

            .add(XMaterial.DARK_OAK_DOOR.parseMaterial())
            .add(XMaterial.ACACIA_DOOR.parseMaterial())
            .add(XMaterial.BIRCH_DOOR.parseMaterial())
            .add(XMaterial.JUNGLE_DOOR.parseMaterial())
            .add(XMaterial.OAK_DOOR.parseMaterial())
            .add(XMaterial.SPRUCE_DOOR.parseMaterial())
            .add(XMaterial.DARK_OAK_TRAPDOOR.parseMaterial())
            .add(XMaterial.ACACIA_TRAPDOOR.parseMaterial())
            .add(XMaterial.BIRCH_TRAPDOOR.parseMaterial())
            .add(XMaterial.JUNGLE_TRAPDOOR.parseMaterial())
            .add(XMaterial.OAK_TRAPDOOR.parseMaterial())
            .add(XMaterial.SPRUCE_TRAPDOOR.parseMaterial())
            .add(XMaterial.DARK_OAK_BUTTON.parseMaterial())
            .add(XMaterial.ACACIA_BUTTON.parseMaterial())
            .add(XMaterial.BIRCH_BUTTON.parseMaterial())
            .add(XMaterial.JUNGLE_BUTTON.parseMaterial())
            .add(XMaterial.OAK_BUTTON.parseMaterial())
            .add(XMaterial.SPRUCE_BUTTON.parseMaterial())

            .build();

}
