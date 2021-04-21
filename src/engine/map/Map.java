package engine.map;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import configuration.MapConfiguration;
import engine.manager.GraphicsManager;


/**
 * 
 * @author gautier
 *	this class represent the map of the game
 */

public class Map {
	/**
	 * tile tab
	 */
	private Tile[][] tiles;
	
	/**
	 * size width map
	 */
	private int columnCount;
	/**
	 * size height map
	 */
	private int lineCount;
	
	/**
	 * list of each ressource of map
	 */
	private List<Tile> goldTiles;
	
	/**
	 * constructor of map
	 * @param line size height map
	 * @param column size width map
	 * @param id of the map
	 * @param fileName to search the correct file to read map
	 * @param graphicsManager needing to give bufferedImage on a tile
	 */
	public Map(int line, int column, int id, String fileName, GraphicsManager graphicsManager){
		this.columnCount = column;
		this.lineCount = line;
		this.setGoldTiles(new ArrayList<Tile>());
		
		tiles = new Tile[line][column];
		
		Scanner scan = null;
		
		try{
			scan = new Scanner(new File(fileName));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				if(scan.hasNextInt() != true){
					System.out.println("erreur format map invalide");
					System.exit(1);
				}
				int index = scan.nextInt();
				
				Tile tile = new Tile(lineIndex, columnIndex, index, graphicsManager);
				tiles[lineIndex][columnIndex] = tile;
				
				if(index == MapConfiguration.GOLD){
					goldTiles.add(tile);
				}
			}
		}
	}
	
	public Tile[][] getTiles(){
		return tiles;
	}
	
	public int getLineCount(){
		return this.lineCount;
	}
	
	public int getColumnCount(){
		return this.columnCount;
	}
	
	public Tile getTile(int line, int column){
		return this.tiles[line][column];
	}

	public List<Tile> getGoldTiles() {
		return goldTiles;
	}

	public void setGoldTiles(List<Tile> goldTiles) {
		this.goldTiles = goldTiles;
	}
}
