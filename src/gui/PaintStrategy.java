package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Camera;
import engine.Entity;
import engine.entity.unit.Unit;
import engine.map.Map;
import engine.map.Tile;
import engine.math.SelectionRect;

/**
 * 
 * @author gautier
 *
 */

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
			rectXOfMinimap = 1225;
			rectYOfMinimap = 625;
			rectWOfMinimap = width - 1225;
			rectHOfMinimap = height - 625;
			
			System.out.println("rect map " + rectXOfMinimap + "," + rectYOfMinimap + "," + rectWOfMinimap + "," + rectHOfMinimap);
			
			firstGridXOfMap = 1250;
			firstGridYOfMap = 650;
			gridMapWidth = 1;
			gridMapHeight = 1;
			
			camX = 1250;
			camY = 650;
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
			graphics.drawRect(rectangle.getX(), rectangle.getY(), rectangle.getW(), rectangle.getH());
		}
	}
	
	public void paintRectSelectionUnit(Unit unit, Graphics graphics, Camera camera){
		int tileSize = GameConfiguration.TILE_SIZE;
		
		graphics.setColor(Color.white);
		graphics.drawRect(unit.getPosition().getX() - camera.getX(), unit.getPosition().getY() - camera.getY(), tileSize, tileSize);
	}
	
	
	public void paintSelectionRectBuilding(Entity building, Graphics graphics, Camera camera) {
		int tileSize = GameConfiguration.TILE_SIZE;
		
		graphics.setColor(Color.white);
		graphics.drawRect(building.getPosition().getX() - camera.getX(), building.getPosition().getY() - camera.getY(), tileSize, tileSize);
	}
	
	public void paint(Entity entity, Graphics graphics, Camera camera)
	{
		int tileSize = GameConfiguration.TILE_SIZE;
		
		if(entity.getId() >= 5 && entity.getId() <= 12){
			if(entity.getFaction() == EntityConfiguration.PLAYER_FACTION){
				if(entity.isHit() && entity.getTimerHit() % 2 == 1) {
					graphics.setColor(Color.red);
				}
				else {
					graphics.setColor(Color.green);
				}
			}
			else{
				if(entity.isHit() && entity.getTimerHit() % 2 == 1) {
					graphics.setColor(Color.red);
				}
				else {
					graphics.setColor(Color.orange);
				}
			}
		}
		else if(entity.getId() >= 0 && entity.getId() <= 4){
			if(entity.getFaction() == EntityConfiguration.PLAYER_FACTION){
				if(entity.isHit() && entity.getTimerHit() % 2 == 1) {
					graphics.setColor(Color.red);
				}
				else {
					graphics.setColor(Color.black);
				}
			}
			else {
				if(entity.isHit() && entity.getTimerHit() % 2 == 1) {
					graphics.setColor(Color.red);
				}
				else {
					graphics.setColor(new Color(145, 40, 59));
				}
			}
		}
		else if(entity.getId() == 13){
			graphics.setColor(Color.yellow);
		}
		graphics.fillRect(entity.getPosition().getX() - camera.getX(), entity.getPosition().getY() - camera.getY(), tileSize, tileSize);
		//graphics.drawImage(entity.getTexture(), entity.getPosition().getX() - camera.getX(), entity.getPosition().getY() - camera.getY(), tileSize, tileSize, null);
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
		
		for(Entity entity : entities)
		{
			paintEntityGui(entity, graphics, camera);
		}
		
		graphics.setColor(new Color(168,104,38));
		graphics.fillRect(0, 0, 550, 50);
				
		//draw rect of the camera on the minimap
		graphics.setColor(Color.white);
		graphics.drawRect(camX + camera.getX() / GameConfiguration.TILE_SIZE, camY + camera.getY() / GameConfiguration.TILE_SIZE, camW, camH);	
				
		//graphics.drawRect(camera.getRectX(), camera.getRectY(), camera.getRectW(), camera.getRectH());
	}
	
	public void paintEntityGui(Entity entity, Graphics graphics, Camera camera)
	{	
		if(entity.getFaction() == EntityConfiguration.PLAYER_FACTION)
		{
			graphics.setColor(Color.green);
		}
		else if(entity.getFaction() == EntityConfiguration.BOT_FACTION)
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
}
