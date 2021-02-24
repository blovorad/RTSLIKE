package gui;

import java.awt.Color;
import java.awt.Graphics;

import configuration.GameConfiguration;
import engine.Building;
import engine.Camera;
import engine.Fighter;
import engine.Worker;
import engine.Map;
import engine.Mouse;
import engine.SelectionRect;
import engine.Ressource;
import engine.Tile;
import engine.Unit;

public class PaintStrategy 
{
	//variable for generate Minimap
	private int rectXOfMinimap;
	private int rectYOfMinimap;
	private int rectWOfMinimap;
	private int rectHOfMinimap;
	
	private int firstGridXOfMap;
	private int firstGridYOfMap;
	private int gridMapWidth;
	private int gridMapHeight;
	
	private int camX;
	private int camY;
	private int camW;
	private int camH;
	
	public PaintStrategy(int width, int height)
	{
		if(width == 1920 && height == 1080)
		{
			rectXOfMinimap = 1625;
			rectYOfMinimap = 775;
			rectWOfMinimap = width - 1625;
			rectHOfMinimap = height - 775;
			
			firstGridXOfMap = 1650;
			firstGridYOfMap = 800;
			gridMapWidth = 2;
			gridMapHeight = 2;
			
			camX = 1650;
			camY = 800;
			camW = (width / GameConfiguration.TILE_SIZE) * gridMapWidth + 39;
			camH = (height / GameConfiguration.TILE_SIZE) * gridMapHeight + 68;
		}
		else if(width == 1366 && height == 768)
		{
			rectXOfMinimap = 1100;
			rectYOfMinimap = 525;
			rectWOfMinimap = width - 1100;
			rectHOfMinimap = height - 525;
			
			System.out.println("rect map " + rectXOfMinimap + "," + rectYOfMinimap + "," + rectWOfMinimap + "," + rectHOfMinimap);
			
			firstGridXOfMap = 1125;
			firstGridYOfMap = 550;
			gridMapWidth = 2;
			gridMapHeight = 2;
			
			camX = 1125;
			camY = 550;
			camW = (width / GameConfiguration.TILE_SIZE) * gridMapWidth + 58;
			camH = (height / GameConfiguration.TILE_SIZE) * gridMapHeight + 76;
		}
		else if(width == 1280 && height == 720)
		{
			rectXOfMinimap = 1000;
			rectYOfMinimap = 500;
			rectWOfMinimap = width - 1000;
			rectHOfMinimap = height - 500;
			
			System.out.println("rect map " + rectXOfMinimap + "," + rectYOfMinimap + "," + rectWOfMinimap + "," + rectHOfMinimap);
			
			firstGridXOfMap = 1025;
			firstGridYOfMap = 515;
			gridMapWidth = 2;
			gridMapHeight = 2;
			
			camX = 1025;
			camY = 515;
			camW = (width / GameConfiguration.TILE_SIZE) * gridMapWidth + 58;
			camH = (height / GameConfiguration.TILE_SIZE) * gridMapHeight + 78;
		}
		System.out.println("on construit dans : " + width + "x" + height);
		System.out.println("rect map " + rectXOfMinimap + "," + rectYOfMinimap + "," + rectWOfMinimap + "," + rectHOfMinimap);
		System.out.println("camera dimension : " + camW + "," + camH);
	}
	
	public void paint(SelectionRect rectangle, Graphics graphics, Camera camera)
	{
		if(rectangle.isActive())
		{
			graphics.setColor(Color.green);
			graphics.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getW(), rectangle.getH());
		}
	}
	
	public void paint(Building building, Graphics graphics, Camera camera)
	{
		int tileSize = GameConfiguration.TILE_SIZE;
		//System.out.println("on paint sur : " + (building.getPosition().getX() - camera.getX()) + "," + (building.getPosition().getY() - camera.getX()));
		graphics.setColor(Color.green);
		graphics.fillRect(building.getPosition().getX() - camera.getX(), building.getPosition().getY() - camera.getY(), tileSize, tileSize);
	}
	
	public void paint(Unit unit, Graphics graphics, Camera camera)
	{
		int tileSize = GameConfiguration.TILE_SIZE;
		
		graphics.setColor(Color.white);
		graphics.fillRect(unit.getPosition().getX() - camera.getX(), unit.getPosition().getY() - camera.getY(), tileSize, tileSize);
	}
	
	/*public void paint(Worker worker, Graphics graphics, Camera camera)
	{
		int tileSize = GameConfiguration.TILE_SIZE;
		
		graphics.setColor(Color.green);
		graphics.fillRect(worker.getPosition().getX() - camera.getX(), worker.getPosition().getY() - camera.getY(), tileSize, tileSize);
	}
	
	public void paint(Fighter fighter, Graphics graphics, Camera camera)
	{
		int tileSize = GameConfiguration.TILE_SIZE;
		
		graphics.setColor(Color.white);
		graphics.fillRect(fighter.getPosition().getX() - camera.getX(), fighter.getPosition().getY() - camera.getY(), tileSize, tileSize);
	}*/
	
	public void paint(Ressource ressource, Graphics graphics, Camera camera)
	{
		int tileSize = GameConfiguration.TILE_SIZE;
		
		graphics.setColor(Color.gray);
		graphics.fillRect(ressource.getPosition().getX() - camera.getX(), ressource.getPosition().getY() - camera.getY(), tileSize, tileSize);
	}
	
	public void paint(Map map, Graphics graphics, Camera camera)
	{
		int tileSize = GameConfiguration.TILE_SIZE;
		
		Tile[][] tiles = map.getTiles();
		//draw map
		for (int lineIndex = 0; lineIndex < map.getLineCount(); lineIndex++) 
		{
			for (int columnIndex = 0; columnIndex < map.getColumnCount(); columnIndex++) 
			{
				Tile tile = tiles[lineIndex][columnIndex];
				
				if(tile.getId() == 0)
				{
					graphics.setColor(Color.black);
				}
				else if(tile.getId() == 1)
				{
					graphics.setColor(Color.orange);
				}
				else if(tile.getId() == 2)
				{
					graphics.setColor(Color.blue);
				}
				graphics.fillRect(tile.getColumn() * tileSize - camera.getX(), tile.getLine() * tileSize - camera.getY(), tileSize, tileSize);
			}
		}
	}
	
	public void paintGui(Map map, Graphics graphics, Camera camera)
	{	
		Tile[][] tiles = map.getTiles();
		
		//draw the rect of the minimap
		graphics.setColor(new Color(168,104,38));
		graphics.fillRect(rectXOfMinimap, rectYOfMinimap, rectWOfMinimap, rectHOfMinimap);
				
		//draw the minimap
		for (int lineIndex = 0; lineIndex < map.getLineCount(); lineIndex++) 
		{
			for (int columnIndex = 0; columnIndex < map.getColumnCount(); columnIndex++) 
			{
				Tile tile = tiles[lineIndex][columnIndex];
						
				if(tile.getId() == 0)
				{
					graphics.setColor(Color.black);
				}
				else if(tile.getId() == 1)
				{
					graphics.setColor(Color.orange);
				}
				else
				{
					graphics.setColor(Color.blue);
				}
				graphics.fillRect(tile.getColumn() * gridMapWidth + firstGridXOfMap, tile.getLine() * gridMapHeight + firstGridYOfMap, gridMapWidth, gridMapHeight);
			}
		}
		graphics.setColor(new Color(168,104,38));
		graphics.fillRect(0, 0, 500, 50);
				
		//draw rect of the camera on the minimap
		graphics.setColor(Color.white);
		graphics.drawRect(camX + camera.getX() / GameConfiguration.TILE_SIZE, camY + camera.getY() / GameConfiguration.TILE_SIZE, camW, camH);	
				
		//graphics.drawRect(camera.getRectX(), camera.getRectY(), camera.getRectW(), camera.getRectH());
	}
}
