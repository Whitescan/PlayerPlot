package me.sword7.playerplot.util;

import me.sword7.playerplot.config.Version;
import me.sword7.playerplot.util.X.XEntityType;
import me.sword7.playerplot.util.X.XMaterial;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class ProtectionUtil {

    public static boolean isProtectedInteraction(Material handMaterial, Material blockMaterial) {
        return INTERACTABLE_MATERIALS.contains(blockMaterial) ||
                isStripLogAttempt(handMaterial, blockMaterial) ||
                isPlaceableItem(handMaterial) ||
                isUsableItem(handMaterial);
    }

    public static boolean isInteractableAtEntity(EntityType entityType) {
        return INTERACTABLE_AT_ENTITIES.contains(entityType);
    }

    private static ImmutableSet<EntityType> INTERACTABLE_AT_ENTITIES = ImmutableSet.<EntityType>builder()
            .add(EntityType.ARMOR_STAND)
            .build();


    public static boolean isInteractableEntity(EntityType entityType) {
        return INTERACTABLE_ENTITIES.contains(entityType);
    }

    private static final ImmutableSet<EntityType> INTERACTABLE_ENTITIES = ImmutableSet.<EntityType>builder()
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
        return PLACEABLE_ITEMS.contains(handItem);
    }

    private static final ImmutableSet<Material> PLACEABLE_ITEMS = ImmutableSet.<Material>builder()
            .add(Material.ITEM_FRAME)
            .add(Material.PAINTING)
            .build();

    private static boolean isUsableItem(Material handItem) {
        return USABLE_ITEMS.contains(handItem);
    }

    private static ImmutableSet<Material> USABLE_ITEMS = ImmutableSet.<Material>builder()
            .add(Material.WATER_BUCKET)
            .add(Material.LAVA_BUCKET)
            .add(Material.FLINT_AND_STEEL)
            .add(XMaterial.BONE_MEAL.parseMaterial())
            .build();

    private static final Set<Material> INTERACTABLE_MATERIALS = buildInteractableMaterials();

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
        return (Version.getValue() >= 114 && AXE_ITEMS.contains(handMaterial) && LOG_MATERIALS.contains(blockMaterial));
    }

    private static final Set<Material> LOG_MATERIALS = buildLogMaterials();

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

    private static final Set<Material> AXE_ITEMS = buildAxeItems();

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

    public static boolean isMonster(Entity entity) {
        return MONSTER_MOBS.contains(entity.getType());
    }

    private static final Set<EntityType> MONSTER_MOBS = buildMonstersMobs();

    private static Set<EntityType> buildMonstersMobs() {
        Set<XEntityType> xEntityTypes = new HashSet<>();
        xEntityTypes.add(XEntityType.BLAZE);
        xEntityTypes.add(XEntityType.CAVE_SPIDER);
        xEntityTypes.add(XEntityType.CREEPER);
        xEntityTypes.add(XEntityType.DRAGON_FIREBALL);
        xEntityTypes.add(XEntityType.DROWNED);
        xEntityTypes.add(XEntityType.EGG);
        xEntityTypes.add(XEntityType.ELDER_GUARDIAN);
        xEntityTypes.add(XEntityType.ENDER_DRAGON);
        xEntityTypes.add(XEntityType.ENDERMAN);
        xEntityTypes.add(XEntityType.ENDERMITE);
        xEntityTypes.add(XEntityType.EVOKER);
        xEntityTypes.add(XEntityType.EVOKER_FANGS);
        xEntityTypes.add(XEntityType.FIREBALL);
        xEntityTypes.add(XEntityType.GHAST);
        xEntityTypes.add(XEntityType.GIANT);
        xEntityTypes.add(XEntityType.GUARDIAN);
        xEntityTypes.add(XEntityType.HOGLIN);
        xEntityTypes.add(XEntityType.HUSK);
        xEntityTypes.add(XEntityType.ILLUSIONER);
        xEntityTypes.add(XEntityType.LLAMA_SPIT);
        xEntityTypes.add(XEntityType.MAGMA_CUBE);
        xEntityTypes.add(XEntityType.PHANTOM);
        xEntityTypes.add(XEntityType.PIGLIN);
        xEntityTypes.add(XEntityType.PIGLIN_BRUTE);
        xEntityTypes.add(XEntityType.PILLAGER);
        xEntityTypes.add(XEntityType.RAVAGER);
        xEntityTypes.add(XEntityType.SHULKER);
        xEntityTypes.add(XEntityType.SHULKER_BULLET);
        xEntityTypes.add(XEntityType.SILVERFISH);
        xEntityTypes.add(XEntityType.SKELETON);
        xEntityTypes.add(XEntityType.SLIME);
        xEntityTypes.add(XEntityType.SMALL_FIREBALL);
        xEntityTypes.add(XEntityType.SNOWBALL);
        xEntityTypes.add(XEntityType.SLIME);
        xEntityTypes.add(XEntityType.SPIDER);
        xEntityTypes.add(XEntityType.STRAY);
        xEntityTypes.add(XEntityType.UNKNOWN);
        xEntityTypes.add(XEntityType.VEX);
        xEntityTypes.add(XEntityType.VINDICATOR);
        xEntityTypes.add(XEntityType.WITCH);
        xEntityTypes.add(XEntityType.WITHER);
        xEntityTypes.add(XEntityType.WITHER_SKELETON);
        xEntityTypes.add(XEntityType.WITHER_SKULL);
        xEntityTypes.add(XEntityType.ZOGLIN);
        xEntityTypes.add(XEntityType.ZOMBIE);
        xEntityTypes.add(XEntityType.ZOMBIE_VILLAGER);
        xEntityTypes.add(XEntityType.ZOMBIFIED_PIGLIN);

        Set<EntityType> monsters = new HashSet<>();
        for (XEntityType entityType : xEntityTypes) {
            if (entityType.isSupported()) monsters.add(entityType.parseEntityType());
        }

        return monsters;
    }

    public static boolean isFighting(Entity entity) {
        return entity instanceof Creature &&
                AGGROABLE_MOBS.contains(entity.getType()) &&
                ((Creature) entity).getTarget() instanceof Player;
    }

    private static final Set<EntityType> AGGROABLE_MOBS = buildAggroableMobs();

    private static Set<EntityType> buildAggroableMobs() {
        Set<XEntityType> xEntityTypes = new HashSet<>();
        xEntityTypes.add(XEntityType.BEE);
        xEntityTypes.add(XEntityType.CAT);
        xEntityTypes.add(XEntityType.DOLPHIN);
        xEntityTypes.add(XEntityType.FOX);
        xEntityTypes.add(XEntityType.IRON_GOLEM);
        xEntityTypes.add(XEntityType.LLAMA);
        xEntityTypes.add(XEntityType.OCELOT);
        xEntityTypes.add(XEntityType.PANDA);
        xEntityTypes.add(XEntityType.POLAR_BEAR);
        xEntityTypes.add(XEntityType.PUFFERFISH);
        xEntityTypes.add(XEntityType.SNOWMAN);
        xEntityTypes.add(XEntityType.WOLF);

        Set<EntityType> monsters = new HashSet<>();
        for (XEntityType entityType : xEntityTypes) {
            if (entityType.isSupported()) monsters.add(entityType.parseEntityType());
        }

        return monsters;
    }


    public static boolean isSmallBlock(Material material) {
        return SMALL_MATERIALS.contains(material);
    }

    private static final Set<Material> SMALL_MATERIALS = buildSmallMaterials();

    private static Set<Material> buildSmallMaterials() {
        Set<XMaterial> xMaterials = new HashSet<>();
        xMaterials.add(XMaterial.RAIL);
        xMaterials.add(XMaterial.ACTIVATOR_RAIL);
        xMaterials.add(XMaterial.DETECTOR_RAIL);
        xMaterials.add(XMaterial.POWERED_RAIL);
        xMaterials.add(XMaterial.STONE_PRESSURE_PLATE);
        xMaterials.add(XMaterial.OAK_PRESSURE_PLATE);
        xMaterials.add(XMaterial.SPRUCE_PRESSURE_PLATE);
        xMaterials.add(XMaterial.BIRCH_PRESSURE_PLATE);
        xMaterials.add(XMaterial.JUNGLE_PRESSURE_PLATE);
        xMaterials.add(XMaterial.ACACIA_PRESSURE_PLATE);
        xMaterials.add(XMaterial.DARK_OAK_PRESSURE_PLATE);
        xMaterials.add(XMaterial.CRIMSON_PRESSURE_PLATE);
        xMaterials.add(XMaterial.WARPED_PRESSURE_PLATE);
        xMaterials.add(XMaterial.POLISHED_BLACKSTONE_PRESSURE_PLATE);
        xMaterials.add(XMaterial.SNOW);
        xMaterials.add(XMaterial.OAK_TRAPDOOR);
        xMaterials.add(XMaterial.SPRUCE_TRAPDOOR);
        xMaterials.add(XMaterial.BIRCH_TRAPDOOR);
        xMaterials.add(XMaterial.JUNGLE_TRAPDOOR);
        xMaterials.add(XMaterial.ACACIA_TRAPDOOR);
        xMaterials.add(XMaterial.DARK_OAK_TRAPDOOR);
        xMaterials.add(XMaterial.CRIMSON_TRAPDOOR);
        xMaterials.add(XMaterial.WARPED_TRAPDOOR);
        xMaterials.add(XMaterial.LILY_PAD);
        xMaterials.add(XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE);
        xMaterials.add(XMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE);
        xMaterials.add(XMaterial.IRON_TRAPDOOR);
        xMaterials.add(XMaterial.WHITE_CARPET);
        xMaterials.add(XMaterial.ORANGE_CARPET);
        xMaterials.add(XMaterial.MAGENTA_CARPET);
        xMaterials.add(XMaterial.LIGHT_BLUE_CARPET);
        xMaterials.add(XMaterial.YELLOW_CARPET);
        xMaterials.add(XMaterial.LIME_CARPET);
        xMaterials.add(XMaterial.PINK_CARPET);
        xMaterials.add(XMaterial.GRAY_CARPET);
        xMaterials.add(XMaterial.LIGHT_GRAY_CARPET);
        xMaterials.add(XMaterial.CYAN_CARPET);
        xMaterials.add(XMaterial.PURPLE_CARPET);
        xMaterials.add(XMaterial.BLUE_CARPET);
        xMaterials.add(XMaterial.BROWN_CARPET);
        xMaterials.add(XMaterial.GREEN_CARPET);
        xMaterials.add(XMaterial.RED_CARPET);
        xMaterials.add(XMaterial.BLACK_CARPET);
        xMaterials.add(XMaterial.NETHER_SPROUTS);
        xMaterials.add(XMaterial.STRING);

        Set<Material> smallMaterials = new HashSet<>();
        for (XMaterial xMaterial : xMaterials) {
            if (xMaterial.isSupported()) smallMaterials.add(xMaterial.parseMaterial());
        }

        return smallMaterials;
    }

    public static boolean isMediumBlock(Material material) {
        return MEDIUM_MATERIALS.contains(material);
    }

    private static final Set<Material> MEDIUM_MATERIALS = buildMediumMaterials();

    private static Set<Material> buildMediumMaterials() {
        Set<XMaterial> xMaterials = new HashSet<>();
        xMaterials.add(XMaterial.OAK_SLAB);
        xMaterials.add(XMaterial.SPRUCE_SLAB);
        xMaterials.add(XMaterial.BIRCH_SLAB);
        xMaterials.add(XMaterial.JUNGLE_SLAB);
        xMaterials.add(XMaterial.ACACIA_SLAB);
        xMaterials.add(XMaterial.DARK_OAK_SLAB);
        xMaterials.add(XMaterial.CRIMSON_SLAB);
        xMaterials.add(XMaterial.WARPED_SLAB);
        xMaterials.add(XMaterial.STONE_SLAB);
        xMaterials.add(XMaterial.SMOOTH_STONE_SLAB);
        xMaterials.add(XMaterial.SANDSTONE_SLAB);
        xMaterials.add(XMaterial.CUT_SANDSTONE_SLAB);
        xMaterials.add(XMaterial.PETRIFIED_OAK_SLAB);
        xMaterials.add(XMaterial.COBBLESTONE_SLAB);
        xMaterials.add(XMaterial.BRICK_SLAB);
        xMaterials.add(XMaterial.STONE_BRICK_SLAB);
        xMaterials.add(XMaterial.NETHER_BRICK_SLAB);
        xMaterials.add(XMaterial.QUARTZ_SLAB);
        xMaterials.add(XMaterial.RED_SANDSTONE_SLAB);
        xMaterials.add(XMaterial.CUT_RED_SANDSTONE_SLAB);
        xMaterials.add(XMaterial.PURPUR_SLAB);
        xMaterials.add(XMaterial.PRISMARINE_SLAB);
        xMaterials.add(XMaterial.PRISMARINE_BRICK_SLAB);
        xMaterials.add(XMaterial.DARK_PRISMARINE_SLAB);
        xMaterials.add(XMaterial.POLISHED_GRANITE_SLAB);
        xMaterials.add(XMaterial.SMOOTH_RED_SANDSTONE_SLAB);
        xMaterials.add(XMaterial.MOSSY_STONE_BRICK_SLAB);
        xMaterials.add(XMaterial.POLISHED_DIORITE_SLAB);
        xMaterials.add(XMaterial.MOSSY_COBBLESTONE_SLAB);
        xMaterials.add(XMaterial.END_STONE_BRICK_SLAB);
        xMaterials.add(XMaterial.SMOOTH_SANDSTONE_SLAB);
        xMaterials.add(XMaterial.SMOOTH_QUARTZ_SLAB);
        xMaterials.add(XMaterial.GRANITE_SLAB);
        xMaterials.add(XMaterial.ANDESITE_SLAB);
        xMaterials.add(XMaterial.RED_NETHER_BRICK_SLAB);
        xMaterials.add(XMaterial.POLISHED_ANDESITE_SLAB);
        xMaterials.add(XMaterial.DIORITE_SLAB);
        xMaterials.add(XMaterial.POLISHED_BLACKSTONE_BRICK_SLAB);
        xMaterials.add(XMaterial.POLISHED_BLACKSTONE_SLAB);

        xMaterials.add(XMaterial.SEA_PICKLE);
        xMaterials.add(XMaterial.DANDELION);
        xMaterials.add(XMaterial.POPPY);
        xMaterials.add(XMaterial.BLUE_ORCHID);
        xMaterials.add(XMaterial.ALLIUM);
        xMaterials.add(XMaterial.AZURE_BLUET);
        xMaterials.add(XMaterial.RED_TULIP);
        xMaterials.add(XMaterial.ORANGE_TULIP);
        xMaterials.add(XMaterial.WHITE_TULIP);
        xMaterials.add(XMaterial.PINK_TULIP);
        xMaterials.add(XMaterial.OXEYE_DAISY);
        xMaterials.add(XMaterial.CORNFLOWER);
        xMaterials.add(XMaterial.LILY_OF_THE_VALLEY);
        xMaterials.add(XMaterial.WITHER_ROSE);
        xMaterials.add(XMaterial.BROWN_MUSHROOM);
        xMaterials.add(XMaterial.RED_MUSHROOM);
        xMaterials.add(XMaterial.CRIMSON_FUNGUS);
        xMaterials.add(XMaterial.WARPED_FUNGUS);

        xMaterials.add(XMaterial.LEVER);
        xMaterials.add(XMaterial.CAMPFIRE);
        xMaterials.add(XMaterial.SOUL_CAMPFIRE);

        xMaterials.add(XMaterial.STONE_BUTTON);
        xMaterials.add(XMaterial.OAK_BUTTON);
        xMaterials.add(XMaterial.SPRUCE_BUTTON);
        xMaterials.add(XMaterial.BIRCH_BUTTON);
        xMaterials.add(XMaterial.JUNGLE_BUTTON);
        xMaterials.add(XMaterial.ACACIA_BUTTON);
        xMaterials.add(XMaterial.DARK_OAK_BUTTON);
        xMaterials.add(XMaterial.CRIMSON_BUTTON);
        xMaterials.add(XMaterial.WARPED_BUTTON);
        xMaterials.add(XMaterial.POLISHED_BLACKSTONE_BUTTON);
        xMaterials.add(XMaterial.DAYLIGHT_DETECTOR);
        xMaterials.add(XMaterial.CONDUIT);
        xMaterials.add(XMaterial.PLAYER_HEAD);
        xMaterials.add(XMaterial.PLAYER_WALL_HEAD);
        xMaterials.add(XMaterial.CREEPER_HEAD);
        xMaterials.add(XMaterial.CREEPER_WALL_HEAD);
        xMaterials.add(XMaterial.ZOMBIE_HEAD);
        xMaterials.add(XMaterial.ZOMBIE_WALL_HEAD);
        xMaterials.add(XMaterial.SKELETON_SKULL);
        xMaterials.add(XMaterial.SKELETON_WALL_SKULL);
        xMaterials.add(XMaterial.WITHER_SKELETON_SKULL);
        xMaterials.add(XMaterial.WITHER_SKELETON_WALL_SKULL);

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
        xMaterials.add(XMaterial.FLOWER_POT);

        xMaterials.add(XMaterial.CARROTS);
        xMaterials.add(XMaterial.POTATOES);
        xMaterials.add(XMaterial.WHEAT);
        xMaterials.add(XMaterial.WHEAT_SEEDS);
        xMaterials.add(XMaterial.BEETROOTS);
        xMaterials.add(XMaterial.MELON_STEM);
        xMaterials.add(XMaterial.ATTACHED_MELON_STEM);
        xMaterials.add(XMaterial.PUMPKIN_STEM);
        xMaterials.add(XMaterial.ATTACHED_PUMPKIN_STEM);

        xMaterials.add(XMaterial.CAKE);

        Set<Material> smallMaterials = new HashSet<>();
        for (XMaterial xMaterial : xMaterials) {
            if (xMaterial.isSupported()) smallMaterials.add(xMaterial.parseMaterial());
        }

        return smallMaterials;
    }


}
