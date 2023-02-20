package de.whitescan.playerplot.logic;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public interface ILoot {

	ItemStack valueOf(String s) throws Exception;

	String getListLabel();

	List<String> getLootTypes();

	String getRoot();

	String getType();

	ItemStack getDefault();

}
