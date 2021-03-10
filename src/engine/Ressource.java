package engine;

import java.awt.image.BufferedImage;

import configuration.EntityConfiguration;
import engine.map.Tile;

public class Ressource extends Entity
{
	private Tile tileAttach;
	
	public Ressource(int hp, String description, Position position, Tile tile, int faction, BufferedImage texture)
	{
		super(hp, hp, description, position, EntityConfiguration.RESSOURCE, faction, texture, 0);
		tileAttach = tile;
	}

	public Tile getTileAttach() {
		return tileAttach;
	}

	public void setTileAttach(Tile tileAttach) {
		this.tileAttach = tileAttach;
	}
}
