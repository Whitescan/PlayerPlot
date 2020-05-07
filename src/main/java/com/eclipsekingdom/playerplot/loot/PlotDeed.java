package com.eclipsekingdom.playerplot.loot;

import com.eclipsekingdom.playerplot.util.ItemUtil;
import com.eclipsekingdom.playerplot.sys.Version;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static com.eclipsekingdom.playerplot.sys.lang.Message.*;

public class PlotDeed {

    private static String plotDeedLoreID = ChatColor.DARK_GRAY + MISC_PLOT_DEED_LORE.toString();

    private static ItemStack itemStack = buildItemStack();

    private static ItemStack buildItemStack() {
        ItemStack plotDeed = new ItemStack(Material.PAPER);
        ItemMeta meta = plotDeed.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + LABEL_PLOT_DEED.toString());
        List<String> lore = new ArrayList<>();
        lore.add(plotDeedLoreID);
        lore.add(ChatColor.RED + MISC_ONE_USE.toString());
        lore.add(ChatColor.GRAY + "+1 plot");
        meta.setLore(lore);
        if (Version.current.isModelDataSupported()) meta.setCustomModelData(11);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        plotDeed.setItemMeta(meta);
        return plotDeed;
    }


    public static ItemStack build() {
        return itemStack.clone();
    }

    public static boolean isPlotDeed(ItemStack itemStack) {
        if (ItemUtil.hasLoreID(itemStack)) {
            return plotDeedLoreID.equals(ItemUtil.getLoreID(itemStack));
        } else {
            return false;
        }
    }

}
