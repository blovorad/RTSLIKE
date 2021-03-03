package engine.map;

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

	public List<Tile> getGoldTiles() {
		return goldTiles;
	}

	public void setGoldTiles(List<Tile> goldTiles) {
		this.goldTiles = goldTiles;
	}
}
