package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JPanel;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Animation;
import engine.Camera;
import engine.Entity;
import engine.Position;
import engine.entity.unit.Unit;
import engine.manager.GraphicsManager;
import engine.map.Fog;
import engine.map.Map;
import engine.map.Minimap;
import engine.map.Tile;
import engine.math.SelectionRect;

/**
 * 
 * @author gautier
 *
 */

public class PaintStrategyGame 
{	
	private int camX;
	private int camY;
	private int camW;
	private int camH;
	
	private int lifeBarreY;
	private SelectionRect flagDestinationRect;

	public PaintStrategyGame(int width, int height, JPanel minimapPanel, Minimap minimap){
		int panelOffset = 10;
		int flagWidthAndHeight = 10;
		int panelWidth = minimapPanel.getWidth();
		int panelX = minimapPanel.getX() + panelWidth / 2;
		int panelY = minimapPanel.getY();
		
		camX = panelX + panelOffset;
		camY = panelY + panelOffset;
		if(GameConfiguration.launchInFullScreen) {
			camW = minimap.getGridMapWidth() * (Toolkit.getDefaultToolkit().getScreenSize().width / GameConfiguration.TILE_SIZE);
			camH = minimap.getGridMapHeight() * (Toolkit.getDefaultToolkit().getScreenSize().height / GameConfiguration.TILE_SIZE);
		}
		else {
			camW = minimap.getGridMapWidth() * (width / GameConfiguration.TILE_SIZE);
			camH = minimap.getGridMapHeight() * (height / GameConfiguration.TILE_SIZE);
		}
		lifeBarreY = 10;
		flagDestinationRect = new SelectionRect(0, 0, flagWidthAndHeight, flagWidthAndHeight);
	}
	
	public void paint(SelectionRect rectangle, Graphics graphics, Camera camera){
		if(rectangle.isActive()){
			graphics.setColor(Color.green);
			graphics.drawRect(rectangle.getX(), rectangle.getY(), rectangle.getW(), rectangle.getH());
		}
	}
	
	public void paintRectSelectionUnit(Unit unit, Graphics graphics, Camera camera){
		int tileSize = EntityConfiguration.UNIT_SIZE;
		int cavalrySize = EntityConfiguration.CAVALRY_SIZE;
		
		graphics.setColor(new Color(255,255,255,100));
		if(unit.getId() == EntityConfiguration.CAVALRY) {
			graphics.drawRect(unit.getPosition().getX() - camera.getX(), unit.getPosition().getY() - camera.getY(), cavalrySize, cavalrySize);
		}
		else {
			graphics.drawRect(unit.getPosition().getX() - camera.getX(), unit.getPosition().getY() - camera.getY(), tileSize, tileSize);
		}
	}
	
	
	public void paintSelectionRectBuilding(Entity building, Graphics graphics, Camera camera) {
		int tileSize = GameConfiguration.TILE_SIZE;
		
		graphics.setColor(Color.white);
		graphics.drawRect(building.getPosition().getX() - camera.getX(), building.getPosition().getY() - camera.getY(), tileSize, tileSize);
		if(building.getDestination() != null) {
			Position p = building.getDestination();
			flagDestinationRect.updateAlpha();
			graphics.setColor(new Color(255, 255, 255, flagDestinationRect.getAlpha()));
			graphics.fillRect(p.getX() - camera.getX() - flagDestinationRect.getW() / 2, p.getY() - camera.getY() - flagDestinationRect.getH() / 2, flagDestinationRect.getW(), flagDestinationRect.getH());
		}
	}
	
	public void paintSelectionRectRessource(Entity ressource, Graphics graphics, Camera camera) {
		int tileSize = GameConfiguration.TILE_SIZE;
		
		graphics.setColor(Color.white);
		graphics.drawRect(ressource.getPosition().getX() - camera.getX(), ressource.getPosition().getY() - camera.getY(), tileSize, tileSize);
	}
	
	public void paint(Entity entity, Graphics graphics, Camera camera, GraphicsManager graphicsManager)
	{
		int tileSize = GameConfiguration.TILE_SIZE;
		int unitSize = EntityConfiguration.UNIT_SIZE;
		int cavalrySize = EntityConfiguration.CAVALRY_SIZE;
		
		Animation animation = entity.getAnimation();
		
		if(entity.getId() == EntityConfiguration.CAVALRY) {
			graphics.drawImage(animation.getFrame(), entity.getPosition().getX() - camera.getX(), entity.getPosition().getY() - camera.getY(), cavalrySize, cavalrySize, null);
		}
		else if(entity.getId() >= EntityConfiguration.INFANTRY &&  entity.getId() <= EntityConfiguration.WORKER) {
			graphics.drawImage(animation.getFrame(), entity.getPosition().getX() - camera.getX(), entity.getPosition().getY() - camera.getY(), unitSize, unitSize, null);
		}
		else if(entity.getId() >= EntityConfiguration.FORGE && entity.getId() <= EntityConfiguration.ARCHERY) {
			graphics.drawImage(animation.getFrame(), entity.getPosition().getX() - camera.getX(), entity.getPosition().getY() - camera.getY(), tileSize, tileSize, null);
		}
		else if(entity.getId() == EntityConfiguration.SITE_CONSTRUCTION) {
			graphics.drawImage(animation.getFrame(), entity.getPosition().getX() - camera.getX(), entity.getPosition().getY() - camera.getY(), tileSize, tileSize, null);
		}
		else if(entity.getId() == EntityConfiguration.RESSOURCE) {
			graphics.drawImage(animation.getFrame(), entity.getPosition().getX() - camera.getX(), entity.getPosition().getY() - camera.getY(), tileSize, tileSize, null);
		}
		
		paintLifeBarre(entity, graphics, camera);
	}
	
	public void paintLifeBarre(Entity entity, Graphics graphics, Camera camera) {
		if(entity.isSelected()) {
			Position p = entity.getPosition();
			int widthLife = EntityConfiguration.UNIT_SIZE - (int)((((float)entity.getHpMax() - (float)entity.getHp()) / (float)entity.getHpMax()) * (float)EntityConfiguration.UNIT_SIZE);
			if(p.getY() > lifeBarreY) {
				graphics.setColor(Color.red);
				graphics.fillRect(p.getX() - camera.getX(), p.getY() - lifeBarreY - camera.getY(), EntityConfiguration.UNIT_SIZE, lifeBarreY / 2);
				graphics.setColor(Color.green);
				graphics.fillRect(p.getX() - camera.getX(), p.getY() - lifeBarreY - camera.getY(), widthLife, lifeBarreY / 2);
			}
			else {
				graphics.setColor(Color.red);
				graphics.fillRect(p.getX() - camera.getX(), p.getY() + lifeBarreY + EntityConfiguration.UNIT_SIZE - camera.getY(), EntityConfiguration.UNIT_SIZE, lifeBarreY / 2);
				graphics.setColor(Color.green);
				graphics.fillRect(p.getX() - camera.getX(), p.getY() + lifeBarreY + EntityConfiguration.UNIT_SIZE - camera.getY(), widthLife, lifeBarreY / 2);
			}
		}
	}
	
	public void paint(Fog fog, Graphics graphics, Camera camera) {
		graphics.setColor(Color.black);
		boolean[][] removeFog = fog.getFog();
		int tileSize = GameConfiguration.TILE_SIZE;
		for (int lineIndex = 0; lineIndex < fog.getLineCount(); lineIndex++) 
		{
			for (int columnIndex = 0; columnIndex < fog.getColumnCount(); columnIndex++) 
			{
				if(removeFog[lineIndex][columnIndex] == true) {
					graphics.fillRect(columnIndex * tileSize - camera.getX(), lineIndex * tileSize - camera.getY(), tileSize, tileSize);
				}
			}
		}
	}
	
	public void paint(Map map, Graphics graphics, Camera camera, GraphicsManager graphicsManager)
	{
		graphics.drawImage(graphicsManager.getPanelGaucheBas(), 0, 0, GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT, null);
		int tileSize = GameConfiguration.TILE_SIZE;
		
		Tile[][] tiles = map.getTiles();
		//draw map
		for (int lineIndex = 0; lineIndex < map.getLineCount(); lineIndex++) 
		{
			for (int columnIndex = 0; columnIndex < map.getColumnCount(); columnIndex++) 
			{
				Tile tile = tiles[lineIndex][columnIndex];
				Animation animation = tile.getAnimation();
				graphics.drawImage(graphicsManager.getGrassTile(), tile.getColumn() * tileSize - camera.getX(), tile.getLine() * tileSize - camera.getY(), tileSize, tileSize, null);
				graphics.drawImage(animation.getFrame(), tile.getColumn() * tileSize - camera.getX(), tile.getLine() * tileSize - camera.getY(), tileSize, tileSize, null);
			}
		}
	}
	
	public void paintGui(Map map, Fog fog, List<Entity> entities, Graphics graphics, Camera camera, JPanel infoTargetPanel, JPanel infoUpPanel, JPanel minimapPanel, Minimap minimap, GraphicsManager graphicsManager)
	{	
		Tile[][] tiles = map.getTiles();
		boolean[][] removeFog = fog.getFog();
		
		//draw the rect of the minimap
		graphics.drawImage(graphicsManager.getPanelGaucheBas(), minimapPanel.getX() + minimapPanel.getWidth() / 2, minimapPanel.getY(), minimapPanel.getWidth() / 2, minimapPanel.getHeight(), null);
				
		//draw the minimap
		for (int lineIndex = 0; lineIndex < map.getLineCount(); lineIndex++) 
		{
			for (int columnIndex = 0; columnIndex < map.getColumnCount(); columnIndex++) 
			{
				Tile tile = tiles[lineIndex][columnIndex];
				graphics.setColor(tile.getColor());
					
				graphics.fillRect(tile.getColumn() * minimap.getGridMapWidth() + minimap.getFirstGridXOfMap(), tile.getLine() * minimap.getGridMapHeight() + minimap.getFirstGridYOfMap(), minimap.getGridMapWidth(), minimap.getGridMapHeight());
			}
		}
		
		for(Entity entity : entities)
		{
			paintEntityGui(entity, graphics, camera, minimap);
		}
		
		graphics.setColor(Color.black);
		for (int lineIndex = 0; lineIndex < fog.getLineCount(); lineIndex++) 
		{
			for (int columnIndex = 0; columnIndex < fog.getColumnCount(); columnIndex++) 
			{
				if(removeFog[lineIndex][columnIndex] == true) {
					graphics.fillRect(columnIndex * minimap.getGridMapWidth() + minimap.getFirstGridXOfMap(), lineIndex * minimap.getGridMapHeight() + minimap.getFirstGridYOfMap(), minimap.getGridMapWidth(), minimap.getGridMapHeight());
				}
			}
		}
				
		//draw rect of the camera on the minimap
		graphics.setColor(Color.white);
		graphics.drawRect(camX + (camera.getX() *  minimap.getGridMapWidth()) / GameConfiguration.TILE_SIZE, camY + (camera.getY() * minimap.getGridMapHeight()) / GameConfiguration.TILE_SIZE, camW, camH);	
		
		graphics.drawImage(graphicsManager.getPanelGaucheBas(), infoTargetPanel.getX(), infoTargetPanel.getY(), infoTargetPanel.getWidth(), infoTargetPanel.getHeight(), null);
		graphics.drawImage(graphicsManager.getPanelGaucheBas(), infoUpPanel.getX(), infoUpPanel.getY(), infoUpPanel.getWidth(), infoUpPanel.getHeight(), null);
	}
	
	public void paintEntityGui(Entity entity, Graphics graphics, Camera camera, Minimap minimap)
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
		graphics.fillRect(entity.getPosition().getX() / GameConfiguration.TILE_SIZE * minimap.getGridMapWidth() + minimap.getFirstGridXOfMap(), 
				entity.getPosition().getY() / GameConfiguration.TILE_SIZE * minimap.getGridMapHeight() + minimap.getFirstGridYOfMap(), 
				minimap.getGridMapWidth(), minimap.getGridMapHeight());
	}
}
