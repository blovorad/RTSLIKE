package engine;

import configuration.EntityConfiguration;

public class Ressource extends Entity
{
	private Tile tileAttach;
	
	public Ressource(int hp, String description, Position position, Tile tile)
	{
		super(hp, hp, description, position, EntityConfiguration.RESSOURCE);
		tileAttach = tile;
	}

	public Tile getTileAttach() {
		return tileAttach;
	}

	public void setTileAttach(Tile tileAttach) {
		this.tileAttach = tileAttach;
	}
}
