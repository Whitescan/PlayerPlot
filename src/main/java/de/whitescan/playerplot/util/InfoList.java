package de.whitescan.playerplot.util;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import de.whitescan.playerplot.config.Language;

public class InfoList {

	public InfoList(String title, List<String> items, int pageItemCount) {
		this.title = title;
		this.items = items;
		this.pageItemCount = pageItemCount;
	}

	public void displayTo(CommandSender sender, int page) {
		if (page < 1) {
			page = 1;
		}
		sender.sendMessage(title);
		sender.sendMessage(ChatColor.DARK_GRAY + "> " + Language.LABEL_PAGE.toString().toLowerCase() + " " + page);

		int startIndex = (page - 1) * pageItemCount;
		for (int i = startIndex; i < startIndex + pageItemCount; i++) {
			int num = i + 1;
			String prefix = num + ". ";
			if (i < items.size()) {
				sender.sendMessage(prefix + items.get(i));
			} else {
				sender.sendMessage(prefix + "-");
			}
		}
	}

	private final String title;
	private final List<String> items;
	private final int pageItemCount;

}
