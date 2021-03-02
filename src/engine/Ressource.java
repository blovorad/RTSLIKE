package engine;

public class Ressource extends Entity
{
	private Tile tileAttach;
	
	public Ressource(int hp, String description, Position position, Tile tile)
	{
		super(hp, description, position, -1);
		tileAttach = tile;
	}

	public Tile getTileAttach() {
		return tileAttach;
	}

	public void setTileAttach(Tile tileAttach) {
		this.tileAttach = tileAttach;
	}
	
	
}
