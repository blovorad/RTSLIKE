package gui;

import java.awt.Color;
import java.awt.Graphics;

import configuration.GameConfiguration;
import engine.Building;
import engine.Camera;
import engine.Fighter;
import engine.Worker;
import engine.Map;
import engine.Ressource;
import engine.Tile;

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
			System.out.println("on construit dans : " + width + "x" + height);
			rectXOfMinimap = (int)(1625 * GameConfiguration.SCALE_X);
			rectYOfMinimap = (int)(775 * GameConfiguration.SCALE_Y);
			rectWOfMinimap = 1920 - 1625;
			rectHOfMinimap = 1080 - 775;
			
			System.out.println("rect map " + rectXOfMinimap + "," + rectYOfMinimap + "," + rectWOfMinimap + "," + rectHOfMinimap);
			
			firstGridXOfMap = (int)(1650 * GameConfiguration.SCALE_X);
			firstGridYOfMap = (int)(800 * GameConfiguration.SCALE_Y);
			gridMapWidth = 2;
			gridMapHeight = 2;
			
			camX = (int)(1650 * GameConfiguration.SCALE_X);
			camY = (int)(800 * GameConfiguration.SCALE_Y);
			camW = (int)(((float)width / (float)GameConfiguration.TILE_SIZE) * GameConfiguration.SCALE_Y * gridMapWidth);
			camH = (int)(((float)height / (float)GameConfiguration.TILE_SIZE) * GameConfiguration.SCALE_Y * gridMapHeight);
		}
	}
	
	public void paint(Building building, Graphics graphics, Camera camera)
	{
		int tileSize = GameConfiguration.TILE_SIZE;
		
		graphics.setColor(Color.blue);
		graphics.fillRect(building.getPosition().getX() - camera.getX(), building.getPosition().getY() - camera.getY(), tileSize, tileSize);
	}
	
	public void paint(Worker worker, Graphics graphics, Camera camera)
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
	}
	
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
		
		//camera broken
		//draw rect of the camera on the minimap
		graphics.setColor(Color.white);
		graphics.drawRect((int)((camX + (camera.getX() * GameConfiguration.SCALE_X) / GameConfiguration.TILE_SIZE)), 
							(int)((camY + (camera.getY() * GameConfiguration.SCALE_Y) / GameConfiguration.TILE_SIZE)), 
							camW, camH);	
		
		//graphics.drawOval(camera.getCircleX(), camera.getCircleY(), camera.getCircleRayon(), camera.getCircleRayon());
	}
}
