package gui;

import java.awt.Color;
import java.awt.Graphics;

import configuration.GameConfiguration;
import engine.Building;
import engine.Camera;
import engine.Fighter;
import engine.Gatherer;
import engine.Map;
import engine.Ressource;
import engine.Tile;

public class PaintStrategy 
{
	public void paint(Building building, Graphics graphics, Camera camera)
	{
		int tileSize = GameConfiguration.TILE_SIZE;
		
		graphics.setColor(Color.blue);
		graphics.fillRect(building.getPosition().getX() - camera.getX(), building.getPosition().getY(), tileSize, tileSize);
	}
	
	public void paint(Gatherer gatherer, Graphics graphics, Camera camera)
	{
		int tileSize = GameConfiguration.TILE_SIZE;
		
		graphics.setColor(Color.green);
		graphics.fillRect(gatherer.getPosition().getX() - camera.getX(), gatherer.getPosition().getY(), tileSize, tileSize);
	}
	
	public void paint(Fighter fighter, Graphics graphics, Camera camera)
	{
		int tileSize = GameConfiguration.TILE_SIZE;
		
		graphics.setColor(Color.white);
		graphics.fillRect(fighter.getPosition().getX() - camera.getX(), fighter.getPosition().getY(), tileSize, tileSize);
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
		graphics.fillRect((int)(1625 * GameConfiguration.SCALE_X), 
							(int)(775 * GameConfiguration.SCALE_Y), 
							GameConfiguration.COLUMN_COUNT + (int)(50 * GameConfiguration.SCALE_X), 
							(GameConfiguration.LINE_COUNT + (int)(50 * GameConfiguration.SCALE_Y)));
		
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
				graphics.fillRect(tile.getColumn() + (int)(1650 * GameConfiguration.SCALE_X), tile.getLine() + (int)(800 * GameConfiguration.SCALE_Y), 1, 1);
			}
		}
		graphics.setColor(new Color(168,104,38));
		graphics.fillRect(0, 0, 400, 50);
		
		
		//camera broken
		//draw rect of the camera on the minimap
		graphics.setColor(Color.white);
		graphics.drawRect((int)(((1650 * GameConfiguration.SCALE_X) + (camera.getX() * GameConfiguration.SCALE_X) / GameConfiguration.TILE_SIZE)), 
							(int)(((800 * GameConfiguration.SCALE_Y) + (camera.getY() * GameConfiguration.SCALE_Y) / GameConfiguration.TILE_SIZE)), 
							(int)(((float)GameConfiguration.WINDOW_WIDTH / (float)GameConfiguration.TILE_SIZE) / GameConfiguration.SCALE_X), 
							(int)(((float)GameConfiguration.WINDOW_HEIGHT / (float)GameConfiguration.TILE_SIZE) / GameConfiguration.SCALE_Y));	
	}
}
