package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import configuration.GameConfiguration;
import engine.Camera;
import engine.Entity;
import engine.Fighter;
<<<<<<< HEAD
=======
import engine.Worker;
import engine.entity.building.Building;
>>>>>>> branch 'malinx' of https://github.com/blovorad/RTSLIKE.git
import engine.Map;
import engine.Mouse;
import engine.Ressource;
import engine.SelectionRect;
import engine.Tile;
import engine.Unit;
import engine.Worker;

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
			gridMapWidth = 1;
			gridMapHeight = 1;
			
			camX = 1650;
			camY = 800;
			camW = (width / GameConfiguration.TILE_SIZE);
			camH = (height / GameConfiguration.TILE_SIZE);
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
			gridMapWidth = 1;
			gridMapHeight = 1;
			
			camX = 1125;
			camY = 550;
			camW = (width / GameConfiguration.TILE_SIZE);
			camH = (height / GameConfiguration.TILE_SIZE);
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
			gridMapWidth = 1;
			gridMapHeight = 1;
			
			camX = 1025;
			camY = 515;
			camW = (width / GameConfiguration.TILE_SIZE);
			camH = (height / GameConfiguration.TILE_SIZE);
		}
		/*System.out.println("on construit dans : " + width + "x" + height);
		System.out.println("rect map " + rectXOfMinimap + "," + rectYOfMinimap + "," + rectWOfMinimap + "," + rectHOfMinimap);
		System.out.println("camera dimension : " + camW + "," + camH);*/
	}
	
	public void paint(SelectionRect rectangle, Graphics graphics, Camera camera)
	{
		if(rectangle.isActive())
		{
			graphics.setColor(Color.green);
			graphics.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getW(), rectangle.getH());
		}
	}
	
	public void paint(Entity entity, Graphics graphics, Camera camera)
	{
		int tileSize = GameConfiguration.TILE_SIZE;
		
		if(entity.getId() >= 5 && entity.getId() <= 12)
		{
			graphics.setColor(Color.green);
		}
		else if(entity.getId() >= 0 && entity.getId() <= 4)
		{
			graphics.setColor(Color.white);
		}
		else if(entity.getId() == 13)
		{
			graphics.setColor(Color.yellow);
		}
		graphics.fillRect(entity.getPosition().getX() - camera.getX(), entity.getPosition().getY() - camera.getY(), tileSize, tileSize);
	}
	
	/*public void paint(Building building, Graphics graphics, Camera camera)
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
	}*/
	
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
	}

	
	public void paint(Ressource ressource, Graphics graphics, Camera camera)
	{
		int tileSize = GameConfiguration.TILE_SIZE;
		
		graphics.setColor(Color.yellow);
		graphics.fillRect(ressource.getPosition().getX() - camera.getX(), ressource.getPosition().getY() - camera.getY(), tileSize, tileSize);
	}*/
	
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
				
				graphics.setColor(tile.getColor());
				graphics.fillRect(tile.getColumn() * tileSize - camera.getX(), tile.getLine() * tileSize - camera.getY(), tileSize, tileSize);
			}
		}
	}
	
	public void paintGui(Map map, List<Entity> entities, Graphics graphics, Camera camera)
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
						
				graphics.setColor(tile.getColor());
		
				graphics.fillRect(tile.getColumn() * gridMapWidth + firstGridXOfMap, tile.getLine() * gridMapHeight + firstGridYOfMap, gridMapWidth, gridMapHeight);
			}
		}
		
		/*for(Unit unit : units)
		{
			this.paintUnitGui(unit, graphics, camera);
		}
		
		for(Building building : buildings)
		{
			this.paintBuildingGui(building, graphics, camera);
		}
		
		for(Ressource ressource : ressources)
		{
			this.paintRessourceGui(ressource, graphics, camera);
		}*/
		
		for(Entity entity : entities)
		{
			paintEntityGui(entity, graphics, camera);
		}
		
		graphics.setColor(new Color(168,104,38));
		graphics.fillRect(0, 0, 500, 50);
				
		//draw rect of the camera on the minimap
		graphics.setColor(Color.white);
		graphics.drawRect(camX + camera.getX() / GameConfiguration.TILE_SIZE, camY + camera.getY() / GameConfiguration.TILE_SIZE, camW, camH);	
				
		//graphics.drawRect(camera.getRectX(), camera.getRectY(), camera.getRectW(), camera.getRectH());
	}
	
	public void paintEntityGui(Entity entity, Graphics graphics, Camera camera)
	{	
		if(entity.getId() >= 5 && entity.getId() <= 12)
		{
			graphics.setColor(Color.green);
		}
		else if(entity.getId() >= 0 && entity.getId() <= 4)
		{
			graphics.setColor(Color.white);
		}
		else if(entity.getId() == 13)
		{
			graphics.setColor(Color.yellow);
		}
		graphics.fillRect(entity.getPosition().getX() / GameConfiguration.TILE_SIZE * gridMapWidth + firstGridXOfMap, 
				entity.getPosition().getY() / GameConfiguration.TILE_SIZE * gridMapHeight + firstGridYOfMap, 
				gridMapWidth, gridMapHeight);
	}
	
	/*public void paintUnitGui(Unit unit, Graphics graphics, Camera camera)
	{
		graphics.setColor(Color.white);
		graphics.fillRect(unit.getPosition().getX() / GameConfiguration.TILE_SIZE * gridMapWidth + firstGridXOfMap, 
				unit.getPosition().getY() / GameConfiguration.TILE_SIZE * gridMapHeight + firstGridYOfMap, 
				gridMapWidth, gridMapHeight);
	}
	
	public void paintBuildingGui(Building building, Graphics graphics, Camera camera)
	{
		graphics.setColor(Color.white);
		graphics.fillRect(building.getPosition().getX() / GameConfiguration.TILE_SIZE * gridMapWidth + firstGridXOfMap, 
				building.getPosition().getY() / GameConfiguration.TILE_SIZE * gridMapHeight + firstGridYOfMap, 
				gridMapWidth, gridMapHeight);
	}
	
	public void paintRessourceGui(Ressource ressource, Graphics graphics, Camera camera)
	{
		graphics.setColor(Color.ORANGE);
		graphics.fillRect(ressource.getPosition().getX() / 
				GameConfiguration.TILE_SIZE * gridMapWidth + firstGridXOfMap, 
				ressource.getPosition().getY() / GameConfiguration.TILE_SIZE * gridMapHeight + firstGridYOfMap, 
				gridMapWidth, gridMapHeight);
	}*/
}
