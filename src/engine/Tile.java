package engine;

public class Tile 
{
	private int line;
	private int column;
	private int id;
	
	public Tile(int line, int column, int id)
	{
		this.line = line;
		this.column = column;
		this.id = id;
	}
	
	public int getLine()
	{
		return this.line;
	}
	
	public int getColumn()
	{
		return this.column;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	//not implemented
	public boolean isSolid()
	{
		/*if(id == SOLID)
		{
			return true;
		}*/
		
		return false;
	}
}
