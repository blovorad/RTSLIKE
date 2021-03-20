package engine;
/**
 * 
 * @author gautier
 *
 */
public class Mouse {
	
	private int id = -1;
	private Position position;
	
	public Mouse()
	{
		setPosition(new Position(0,0));
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void reset()
	{
		id = -1;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
