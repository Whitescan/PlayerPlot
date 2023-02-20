package de.whitescan.playerplot.logic;

import java.util.UUID;

import lombok.Getter;

public class Request {

	@Getter
	private UUID id;
	@Getter
	private Plot plot;

	public Request(Plot plot) {
		this.id = UUID.randomUUID();
		this.plot = plot;
	}

}
