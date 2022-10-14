package de.whitescan.playerplot.border;

import java.util.UUID;

import org.bukkit.World;

import lombok.Getter;
import lombok.Setter;

public class ClientBorder {

	@Getter
	private UUID id;

	@Getter
	private World world;

	@Setter
	private PlayerPusher playerPusher;

	public ClientBorder(World world) {
		this.id = UUID.randomUUID();
		this.world = world;
	}

	public void stopPusher() {
		if (playerPusher != null && !playerPusher.isCancelled())
			playerPusher.cancel();
	}

}
