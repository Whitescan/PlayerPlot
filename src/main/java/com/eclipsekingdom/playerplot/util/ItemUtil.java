package com.eclipsekingdom.playerplot.util;


import org.bukkit.inventory.ItemStack;

public class ItemUtil {

    public static boolean hasLoreID(ItemStack itemStack){
        return (itemStack != null
                && itemStack.hasItemMeta()
                && itemStack.getItemMeta().hasLore()
                && itemStack.getItemMeta().getLore().size() > 0
                && itemStack.getItemMeta().getLore().get(0) != null
        );
    }

    public static String getLoreID(ItemStack itemStack){
        return itemStack.getItemMeta().getLore().get(0);
    }

}
