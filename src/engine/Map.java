package engine;

public class Map 
{
	private Tile[][] tiles;
	
	private int columnCount;
	private int lineCount;
	
	public Map(int line, int column, int id)
	{
		this.columnCount = column;
		this.lineCount = line;
		
		tiles = new Tile[line][column];
		
		for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) 
		{
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) 
			{
				if(id == 1)
				{
					if(columnIndex > 90)
					{
						tiles[lineIndex][columnIndex] = new Tile(lineIndex, columnIndex, 1);
					}
					else if(lineIndex % 2 == 0)
					{
						tiles[lineIndex][columnIndex] = new Tile(lineIndex, columnIndex, 1);
					}
					else if(columnIndex % 2 == 0)
					{
						tiles[lineIndex][columnIndex] = new Tile(lineIndex, columnIndex, 2);
					}
					else
					{
						tiles[lineIndex][columnIndex] = new Tile(lineIndex, columnIndex, 0);
					}
					
				}
				else if(id == 2)
				{
					tiles[lineIndex][columnIndex] = new Tile(lineIndex, columnIndex, 1);
				}
				else if(id == 3)
				{
					tiles[lineIndex][columnIndex] = new Tile(lineIndex, columnIndex, 2);
				}
			}
		}
	}
	
	public Tile[][] getTiles()
	{
		return tiles;
	}
	
	public int getLineCount()
	{
		return this.lineCount;
	}
	
	public int getColumnCount()
	{
		return this.columnCount;
	}
	
	public Tile getTile(int line, int column)
	{
		return this.tiles[line][column];
	}
}
