package de.whitescan.playerplot.logic;

import java.util.UUID;

import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
public class Friend {

	@Getter
	private UUID uuid;
	@Getter
	private String name;

	public Friend(Player player) {
		this(player.getUniqueId(), player.getName());
	}

}
