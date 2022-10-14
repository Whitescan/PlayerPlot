package de.whitescan.playerplot.plotdeed;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ILoot {

	ItemStack valueOf(String s) throws Exception;

	String getListLabel();

	List<String> getLootTypes();

	String getRoot();

	String getType();

	ItemStack getDefault();

}
