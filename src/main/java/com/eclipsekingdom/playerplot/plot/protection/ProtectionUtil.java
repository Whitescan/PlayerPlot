package com.eclipsekingdom.playerplot.plot.protection;

import com.eclipsekingdom.playerplot.plot.protection.interactable.IInteractableIdentifier;
import com.eclipsekingdom.playerplot.plot.protection.strip.StripIdentifier;
import com.eclipsekingdom.playerplot.plot.protection.interactable.InteractableIdentifier;
import com.eclipsekingdom.playerplot.plot.protection.strip.IStripIdentifier;
import com.eclipsekingdom.playerplot.util.XMaterial;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import static org.bukkit.Material.*;

public class ProtectionUtil {

    private static IInteractableIdentifier iInteractableIdentifier = InteractableIdentifier.select();
    private static IStripIdentifier stripIdentifier = StripIdentifier.select();

    public static boolean isProtectedInteraction(Material handMaterial, Material blockMaterial) {
        return iInteractableIdentifier.isInteractable(blockMaterial) ||
                stripIdentifier.isStripLogAttempt(handMaterial, blockMaterial) ||
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
            .add(ITEM_FRAME)
            .add(PAINTING)
            .build();

    private static boolean isUsableItem(Material handItem) {
        return usableItems.contains(handItem);
    }

    private static ImmutableSet<Material> usableItems = ImmutableSet.<Material>builder()
            .add(WATER_BUCKET)
            .add(LAVA_BUCKET)
            .add(FLINT_AND_STEEL)
            .add(XMaterial.BONE_MEAL.parseMaterial())
            .build();


}
