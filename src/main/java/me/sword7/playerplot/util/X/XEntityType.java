package me.sword7.playerplot.util.X;

import me.sword7.playerplot.config.Version;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public enum XEntityType {

    AREA_EFFECT_CLOUD,
    ARMOR_STAND(108),
    ARROW,
    BAT,
    BEE(115),
    BLAZE,
    BOAT,
    CAT,
    CAVE_SPIDER,
    CHICKEN,
    COD,
    COW,
    CREEPER,
    DOLPHIN(113),
    DONKEY,
    DRAGON_FIREBALL(109),
    DROPPED_ITEM,
    DROWNED(113),
    EGG,
    ELDER_GUARDIAN(108),
    ENDER_CRYSTAL,
    ENDER_DRAGON,
    ENDER_PEARL,
    ENDER_SIGNAL,
    ENDERMAN,
    ENDERMITE(108),
    EVOKER(111),
    EVOKER_FANGS(111),
    EXPERIENCE_ORB,
    FALLING_BLOCK,
    FIREBALL,
    FIREWORK,
    FISHING_HOOK,
    FOX(114),
    GHAST,
    GIANT,
    GUARDIAN(108),
    HOGLIN(116),
    HORSE,
    HUSK(110),
    ILLUSIONER(112),
    IRON_GOLEM,
    ITEM_FRAME,
    LLAMA(111),
    LLAMA_SPIT(111),
    MAGMA_CUBE,
    MINECART,
    MINECART_CHEST,
    MINECART_COMMAND,
    MINECART_FURNACE,
    MINECART_HOPPER,
    MINECART_MOB_SPAWNER,
    MINECART_TNT,
    MULE,
    MUSHROOM_COW,
    OCELOT,
    PAINTING,
    PANDA(114),
    PARROT(112),
    PHANTOM(113),
    PIG,
    PIGLIN(116),
    PIGLIN_BRUTE(116),
    PILLAGER(114),
    PLAYER,
    POLAR_BEAR(110),
    PRIMED_TNT,
    PUFFERFISH(113),
    RABBIT(108),
    RAVAGER(114),
    SALMON(113),
    SHEEP,
    SHULKER(109),
    SHULKER_BULLET(109),
    SILVERFISH,
    SKELETON,
    SKELETON_HORSE,
    SLIME,
    SMALL_FIREBALL(114),
    SNOWBALL,
    SNOWMAN,
    SPECTRAL_ARROW(109),
    SPIDER,
    SPLASH_POTION,
    SQUID,
    STRAY(110),
    STRIDER(116),
    THROWN_EXP_BOTTLE,
    TRADER_LLAMA(114),
    TRIDENT(113),
    TROPICAL_FISH(113),
    TURTLE(113),
    UNKNOWN,
    VEX(111),
    VILLAGER,
    VINDICATOR(111),
    WANDERING_TRADER(114),
    WITCH,
    WITHER,
    WITHER_SKELETON,
    WITHER_SKULL,
    WOLF,
    ZOGLIN(116),
    ZOMBIE,
    ZOMBIE_HORSE,
    ZOMBIE_VILLAGER,
    ZOMBIFIED_PIGLIN("PIG_ZOMBIE"),

    ;

    private List<String> legacy = new ArrayList<>();
    private int versionAdded;

    XEntityType() {
        this.versionAdded = 0;
    }

    XEntityType(int versionAdded) {
        this.versionAdded = versionAdded;
    }

    XEntityType(String legacyOne) {
        legacy.add(legacyOne);
    }


    public boolean isSupported() {
        return versionAdded <= Version.getValue() && parseEntityType() != null;
    }

    public EntityType parseEntityType() {
        EntityType entityType = entityTypeFrom(this.toString());
        int index = 0;
        while (entityType == null && index < legacy.size()) {
            entityType = entityTypeFrom(legacy.get(index));
            index++;
        }
        return entityType;
    }

    private EntityType entityTypeFrom(String s) {
        try {
            return EntityType.valueOf(s);
        } catch (Exception e) {
            return null;
        }
    }


}
