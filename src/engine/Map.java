package engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import configuration.MapConfiguration;

public class Map 
{
	private Tile[][] tiles;
	
	private int columnCount;
	private int lineCount;
	
	private List<Tile> goldTiles;
	
	public Map(int line, int column, int id, String fileName)
	{
		this.columnCount = column;
		this.lineCount = line;
		this.setGoldTiles(new ArrayList<Tile>());
		
		tiles = new Tile[line][column];
		
		Scanner scan = null;
		
		try
		{
			scan = new Scanner(new File(fileName));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) 
		{
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) 
			{
				if(scan.hasNextInt() != true)
				{
					System.out.println("erreur format map invalide");
					System.exit(1);
				}
				int index = scan.nextInt();
				
				Tile tile = new Tile(lineIndex, columnIndex, index);
				tiles[columnIndex][lineIndex] = tile;
				
				if(index == MapConfiguration.GOLD)
				{
					goldTiles.add(tile);
				}
				/*tiles[lineIndex][columnIndex] = new Tile(lineIndex, columnIndex, 0);
				
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
				}*/
			}
		}
	}
	
	public boolean existTiles(Position position)
	{
		if( position.getX() < 0  && position.getX() >= this.columnCount )
		{
			return false;
		}
		else if(position.getY() < 0 && position.getY() >= this.lineCount )
		{
			return false;
		}
		return true;
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

	public List<Tile> getGoldTiles() {
		return goldTiles;
	}

	public void setGoldTiles(List<Tile> goldTiles) {
		this.goldTiles = goldTiles;
	}
}
