package com.eclipsekingdom.playerplot.util;

import com.eclipsekingdom.playerplot.sys.Version;
import com.eclipsekingdom.playerplot.util.X.XMaterial;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.HashSet;
import java.util.Set;

public class ProtectionUtil {

    public static boolean isProtectedInteraction(Material handMaterial, Material blockMaterial) {
        return interactableMaterials.contains(blockMaterial) ||
                isStripLogAttempt(handMaterial, blockMaterial) ||
                isPlaceableItem(handMaterial) ||
                isUsableItem(handMaterial);
    }

    public static boolean isInteractableAtEntity(EntityType entityType) {
        return interactableAtEntities.contains(entityType);
    }

    private static ImmutableSet<EntityType> interactableAtEntities = ImmutableSet.<EntityType>builder()
            .add(EntityType.ARMOR_STAND)
            .build();


    public static boolean isInteractableEntity(EntityType entityType) {
        return interactableEntities.contains(entityType);
    }

    private static ImmutableSet<EntityType> interactableEntities = ImmutableSet.<EntityType>builder()
            .add(EntityType.ITEM_FRAME)
            .add(EntityType.VILLAGER)
            .add(EntityType.LEASH_HITCH)
            .add(EntityType.MINECART_HOPPER)
            .add(EntityType.MINECART_COMMAND)
            .add(EntityType.MINECART_CHEST)
            .add(EntityType.MINECART_FURNACE)
            .add(EntityType.MINECART_MOB_SPAWNER)
            .build();


    private static boolean isPlaceableItem(Material handItem) {
        return placeableItems.contains(handItem);
    }

    private static ImmutableSet<Material> placeableItems = ImmutableSet.<Material>builder()
            .add(Material.ITEM_FRAME)
            .add(Material.PAINTING)
            .build();

    private static boolean isUsableItem(Material handItem) {
        return usableItems.contains(handItem);
    }

    private static ImmutableSet<Material> usableItems = ImmutableSet.<Material>builder()
            .add(Material.WATER_BUCKET)
            .add(Material.LAVA_BUCKET)
            .add(Material.FLINT_AND_STEEL)
            .add(XMaterial.BONE_MEAL.parseMaterial())
            .build();

    private static Set<Material> interactableMaterials = buildInteractableMaterials();

    private static Set<Material> buildInteractableMaterials() {
        Set<XMaterial> xMaterials = new HashSet<>();

        xMaterials.add(XMaterial.DAYLIGHT_DETECTOR);
        xMaterials.add(XMaterial.CAULDRON);
        xMaterials.add(XMaterial.TRAPPED_CHEST);
        xMaterials.add(XMaterial.SPAWNER);
        xMaterials.add(XMaterial.ITEM_FRAME);
        xMaterials.add(XMaterial.LEVER);
        xMaterials.add(XMaterial.HOPPER);
        xMaterials.add(XMaterial.CHEST);
        xMaterials.add(XMaterial.BREWING_STAND);
        xMaterials.add(XMaterial.FURNACE);
        xMaterials.add(XMaterial.COMPARATOR);
        xMaterials.add(XMaterial.NOTE_BLOCK);
        xMaterials.add(XMaterial.ANVIL);
        xMaterials.add(XMaterial.CHIPPED_ANVIL);
        xMaterials.add(XMaterial.DAMAGED_ANVIL);
        xMaterials.add(XMaterial.COMMAND_BLOCK);
        xMaterials.add(XMaterial.BEACON);
        xMaterials.add(XMaterial.DISPENSER);
        xMaterials.add(XMaterial.DROPPER);


        xMaterials.add(XMaterial.OAK_FENCE_GATE);
        xMaterials.add(XMaterial.SPRUCE_FENCE_GATE);
        xMaterials.add(XMaterial.BIRCH_FENCE_GATE);
        xMaterials.add(XMaterial.JUNGLE_FENCE_GATE);
        xMaterials.add(XMaterial.ACACIA_FENCE_GATE);
        xMaterials.add(XMaterial.DARK_OAK_FENCE_GATE);
        xMaterials.add(XMaterial.WARPED_FENCE_GATE);
        xMaterials.add(XMaterial.CRIMSON_FENCE_GATE);

        xMaterials.add(XMaterial.OAK_TRAPDOOR);
        xMaterials.add(XMaterial.SPRUCE_TRAPDOOR);
        xMaterials.add(XMaterial.BIRCH_TRAPDOOR);
        xMaterials.add(XMaterial.JUNGLE_TRAPDOOR);
        xMaterials.add(XMaterial.ACACIA_TRAPDOOR);
        xMaterials.add(XMaterial.DARK_OAK_TRAPDOOR);
        xMaterials.add(XMaterial.WARPED_TRAPDOOR);
        xMaterials.add(XMaterial.CRIMSON_TRAPDOOR);

        xMaterials.add(XMaterial.OAK_DOOR);
        xMaterials.add(XMaterial.SPRUCE_DOOR);
        xMaterials.add(XMaterial.BIRCH_DOOR);
        xMaterials.add(XMaterial.JUNGLE_DOOR);
        xMaterials.add(XMaterial.ACACIA_DOOR);
        xMaterials.add(XMaterial.DARK_OAK_DOOR);
        xMaterials.add(XMaterial.WARPED_DOOR);
        xMaterials.add(XMaterial.CRIMSON_DOOR);

        xMaterials.add(XMaterial.SHULKER_BOX);
        xMaterials.add(XMaterial.WHITE_SHULKER_BOX);
        xMaterials.add(XMaterial.ORANGE_SHULKER_BOX);
        xMaterials.add(XMaterial.MAGENTA_SHULKER_BOX);
        xMaterials.add(XMaterial.LIGHT_BLUE_SHULKER_BOX);
        xMaterials.add(XMaterial.YELLOW_SHULKER_BOX);
        xMaterials.add(XMaterial.LIME_SHULKER_BOX);
        xMaterials.add(XMaterial.PINK_SHULKER_BOX);
        xMaterials.add(XMaterial.GRAY_SHULKER_BOX);
        xMaterials.add(XMaterial.LIGHT_GRAY_SHULKER_BOX);
        xMaterials.add(XMaterial.CYAN_SHULKER_BOX);
        xMaterials.add(XMaterial.PURPLE_SHULKER_BOX);
        xMaterials.add(XMaterial.BLUE_SHULKER_BOX);
        xMaterials.add(XMaterial.BROWN_SHULKER_BOX);
        xMaterials.add(XMaterial.GREEN_SHULKER_BOX);
        xMaterials.add(XMaterial.RED_SHULKER_BOX);
        xMaterials.add(XMaterial.BLACK_SHULKER_BOX);

        xMaterials.add(XMaterial.BLAST_FURNACE);
        xMaterials.add(XMaterial.SMOKER);
        xMaterials.add(XMaterial.LOOM);
        xMaterials.add(XMaterial.BARREL);
        xMaterials.add(XMaterial.BLAST_FURNACE);
        xMaterials.add(XMaterial.BEE_NEST);


        xMaterials.add(XMaterial.WHITE_BED);
        xMaterials.add(XMaterial.ORANGE_BED);
        xMaterials.add(XMaterial.MAGENTA_BED);
        xMaterials.add(XMaterial.LIGHT_BLUE_BED);
        xMaterials.add(XMaterial.YELLOW_BED);
        xMaterials.add(XMaterial.LIME_BED);
        xMaterials.add(XMaterial.PINK_BED);
        xMaterials.add(XMaterial.GRAY_BED);
        xMaterials.add(XMaterial.LIGHT_GRAY_BED);
        xMaterials.add(XMaterial.CYAN_BED);
        xMaterials.add(XMaterial.PURPLE_BED);
        xMaterials.add(XMaterial.BLUE_BED);
        xMaterials.add(XMaterial.BROWN_BED);
        xMaterials.add(XMaterial.GREEN_BED);
        xMaterials.add(XMaterial.RED_BED);
        xMaterials.add(XMaterial.BLACK_BED);


        xMaterials.add(XMaterial.OAK_BUTTON);
        xMaterials.add(XMaterial.SPRUCE_BUTTON);
        xMaterials.add(XMaterial.BIRCH_BUTTON);
        xMaterials.add(XMaterial.JUNGLE_BUTTON);
        xMaterials.add(XMaterial.ACACIA_BUTTON);
        xMaterials.add(XMaterial.DARK_OAK_BUTTON);
        xMaterials.add(XMaterial.WARPED_BUTTON);
        xMaterials.add(XMaterial.CRIMSON_BUTTON);

        Set<Material> interactableMaterials = new HashSet<>();
        for (XMaterial xMaterial : xMaterials) {
            if (xMaterial.isSupported()) {
                interactableMaterials.add(xMaterial.parseMaterial());
            }
        }
        return interactableMaterials;
    }


    private static boolean isStripLogAttempt(Material handMaterial, Material blockMaterial) {
        return (Version.getValue() >= 114 && axeItems.contains(handMaterial) && logMaterials.contains(blockMaterial));
    }


    private static Set<Material> logMaterials = buildLogMaterials();

    private static Set<Material> buildLogMaterials() {
        Set<XMaterial> xMaterials = new HashSet<>();

        xMaterials.add(XMaterial.OAK_WOOD);
        xMaterials.add(XMaterial.SPRUCE_WOOD);
        xMaterials.add(XMaterial.BIRCH_WOOD);
        xMaterials.add(XMaterial.JUNGLE_WOOD);
        xMaterials.add(XMaterial.ACACIA_WOOD);
        xMaterials.add(XMaterial.DARK_OAK_WOOD);
        xMaterials.add(XMaterial.WARPED_HYPHAE);
        xMaterials.add(XMaterial.CRIMSON_HYPHAE);


        xMaterials.add(XMaterial.OAK_LOG);
        xMaterials.add(XMaterial.SPRUCE_LOG);
        xMaterials.add(XMaterial.BIRCH_LOG);
        xMaterials.add(XMaterial.JUNGLE_LOG);
        xMaterials.add(XMaterial.ACACIA_LOG);
        xMaterials.add(XMaterial.DARK_OAK_LOG);
        xMaterials.add(XMaterial.WARPED_STEM);
        xMaterials.add(XMaterial.CRIMSON_STEM);

        Set<Material> logMaterials = new HashSet<>();
        for (XMaterial xMaterial : xMaterials) {
            if (xMaterial.isSupported()) {
                logMaterials.add(xMaterial.parseMaterial());
            }
        }
        return logMaterials;
    }

    private static Set<Material> axeItems = buildAxeItems();

    private static Set<Material> buildAxeItems() {
        Set<XMaterial> xMaterials = new HashSet<>();

        xMaterials.add(XMaterial.WOODEN_AXE);
        xMaterials.add(XMaterial.STONE_AXE);
        xMaterials.add(XMaterial.IRON_AXE);
        xMaterials.add(XMaterial.GOLDEN_AXE);
        xMaterials.add(XMaterial.DIAMOND_AXE);
        xMaterials.add(XMaterial.NETHERITE_AXE);

        Set<Material> axeItems = new HashSet<>();
        for (XMaterial xMaterial : xMaterials) {
            if (xMaterial.isSupported()) {
                axeItems.add(xMaterial.parseMaterial());
            }
        }
        return axeItems;
    }

}
