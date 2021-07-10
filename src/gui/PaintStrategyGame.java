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
import engine.Mouse;
import engine.Position;
import engine.entity.building.ProductionBuilding;
import engine.entity.unit.Unit;
import engine.manager.GraphicsManager;
import engine.map.Fog;
import engine.map.FogCase;
import engine.map.Map;
import engine.map.Minimap;
import engine.map.Tile;
import engine.math.SelectionRect;

/**
 * class who charge to paint the game window
 * @author gautier
 *
 */

public class PaintStrategyGame 
{	
	//that makes a rect
	/**
	 * Collider for scrolling left upper side
	 */
	private int camX;
	/**
	 * Collider for scrolling left upper side
	 */
	private int camY;
	/**
	 * Collider for scrolling right down side
	 */
	private int camW;
	/**
	 * Collider for scrolling right down side
	 */
	private int camH;
	
	/**
	 * to check if we need to print life barre down or up on the current selected unit
	 */
	private int lifeBarreY;
	/**
	 * rect of rally point of building
	 */
	private SelectionRect flagDestinationRect;
	
	
	/**
	 * 
	 * @param width of the window
	 * @param height of the window
	 * @param minimapPanel panel take to get coordinate of panel
	 * @param minimap to take where minimap is realy place
	 */
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
	
	/**
	 * painting the selection rect when you drag mouse on screen
	 * @param rectangle current rect to draw
	 * @param graphics where to draw it
	 */
	public void paint(SelectionRect rectangle, Graphics graphics){
		if(rectangle.isActive()){
			graphics.setColor(Color.green);
			graphics.drawRect(rectangle.getX(), rectangle.getY(), rectangle.getW(), rectangle.getH());
		}
	}
	
	/**
	 * to paint the current rect around unit that is selected
	 * @param unit current unit
	 * @param graphics where to paint it, in term of panel
	 * @param camera to paint it at the good position in function of camera position
	 */
	public void paintRectSelectionUnit(Unit unit, Graphics graphics, Camera camera){
		int unitSize = EntityConfiguration.UNIT_SIZE;
		int cavalrySize = EntityConfiguration.CAVALRY_SIZE;
		
		graphics.setColor(new Color(255,255,255,100));
		if(unit.getId() == EntityConfiguration.CAVALRY) {
			graphics.drawRect(unit.getPosition().getX() - camera.getX(), unit.getPosition().getY() - camera.getY(), cavalrySize, cavalrySize);
		}
		else {
			graphics.drawRect(unit.getPosition().getX() - camera.getX(), unit.getPosition().getY() - camera.getY(), unitSize, unitSize);
		}
	}
	
	/**
	 * Same as paintRectSelectionUnit
	 * @param building
	 * @param graphics
	 * @param camera
	 * @see paintRectSelectionUnit
	 */
	public void paintSelectionRectBuilding(Entity building, Graphics graphics, Camera camera) {
		int tileSize = GameConfiguration.TILE_SIZE;
		
		graphics.setColor(Color.white);
		graphics.drawRect(building.getPosition().getX() - camera.getX(), building.getPosition().getY() - camera.getY(), tileSize, tileSize);
	}
	
	public void paintRallyPoint(ProductionBuilding building, Graphics graphics, Camera camera) {
		if(building.getRallyPoint() != null) {
			Position p = building.getRallyPoint();
			flagDestinationRect.updateAlpha();
			graphics.setColor(new Color(255, 255, 255, flagDestinationRect.getAlpha()));
			graphics.fillRect(p.getX() - camera.getX() - flagDestinationRect.getW() / 2, p.getY() - camera.getY() - flagDestinationRect.getH() / 2, flagDestinationRect.getW(), flagDestinationRect.getH());
		}
	}
	
	/**
	 * Same as paintRectSelectionUnit
	 * @param ressource
	 * @param graphics
	 * @param camera
	 * @see paintRectSelectionUnit
	 */
	public void paintSelectionRectRessource(Entity ressource, Graphics graphics, Camera camera) {
		int tileSize = GameConfiguration.TILE_SIZE;
		
		graphics.setColor(Color.white);
		graphics.drawRect(ressource.getPosition().getX() - camera.getX(), ressource.getPosition().getY() - camera.getY(), tileSize, tileSize);
	}
	
	/**
	 * Here you can paint all entity of the game
	 * @param entity to paint
	 * @param graphics where to paint it, in term of panel
	 * @param camera to place entity at the good place on screen
	 */
	public void paint(Entity entity, Graphics graphics, Camera camera)
	{
		int tileSize = GameConfiguration.TILE_SIZE;
		int unitSize = EntityConfiguration.UNIT_SIZE;
		int cavalrySize = EntityConfiguration.CAVALRY_SIZE;
		
		
		int width = GameConfiguration.WINDOW_WIDTH;
		int height = GameConfiguration.WINDOW_HEIGHT;
		if(GameConfiguration.launchInFullScreen) {
			width = Toolkit.getDefaultToolkit().getScreenSize().width;
			height = Toolkit.getDefaultToolkit().getScreenSize().height;
		}
		Position p = entity.getPosition();
		Animation animation = entity.getAnimation();

		if(entity.getId() == EntityConfiguration.CAVALRY) {
			if(p.getX() + cavalrySize - camera.getX() >= 0 && p.getY() - camera.getY() + cavalrySize >= 0 && p.getX() - camera.getX() <= width && p.getY() - camera.getY() <= height) {
				graphics.drawImage(animation.getFrame(), p.getX() - camera.getX(), p.getY() - camera.getY(), cavalrySize, cavalrySize, null);
			}
		}
		else if(entity.getId() >= EntityConfiguration.INFANTRY &&  entity.getId() <= EntityConfiguration.WORKER) {
			if(p.getX() + unitSize - camera.getX() >= 0 && p.getY() - camera.getY() + unitSize >= 0 && p.getX() - camera.getX() <= width && p.getY() - camera.getY() <= height) {	
				graphics.drawImage(animation.getFrame(), p.getX() - camera.getX(), p.getY() - camera.getY(), unitSize, unitSize, null);
			}
		}
		else {
			if(p.getX() + tileSize - camera.getX() >= 0 && p.getY() - camera.getY() + tileSize >= 0 && p.getX() - camera.getX() <= width && p.getY() - camera.getY() <= height) {
				graphics.drawImage(animation.getFrame(), p.getX() - camera.getX(), p.getY() - camera.getY(), tileSize, tileSize, null);
			}
		}
		paintLifeBarre(entity, graphics, camera);
	}
	
	/**
	 * method calls when mouse id is >-1, that print what building you need to build
	 * @param mouse current mouse, taking mouse position
	 * @param graphics where to paint it, in term of panel
	 * @param map to check if you need to print red or green if we want to build on solid tile
	 * @param camera to paint it a the good position
	 */
	public void paintMouseBuilding(Mouse mouse, Graphics graphics, Map map, Camera camera) {
		int x = (mouse.getPosition().getX() + camera.getX()) / GameConfiguration.TILE_SIZE;
		int y = (mouse.getPosition().getY() + camera.getY()) / GameConfiguration.TILE_SIZE;

		if(map.getTile(y, x).isSolid() == true) {
			graphics.setColor(Color.red);
		}
		else {
			graphics.setColor(Color.green);
		}
		graphics.fillRect(mouse.getPosition().getX() - GameConfiguration.TILE_SIZE / 2, mouse.getPosition().getY() - GameConfiguration.TILE_SIZE / 2, GameConfiguration.TILE_SIZE, GameConfiguration.TILE_SIZE);
		graphics.drawImage(mouse.getFrame(), mouse.getPosition().getX() - GameConfiguration.TILE_SIZE / 2, mouse.getPosition().getY() - GameConfiguration.TILE_SIZE / 2, GameConfiguration.TILE_SIZE, GameConfiguration.TILE_SIZE, null);
	}
	
	/**
	 * to paint life barre when you select a entity
	 * @param entity to paint life bare
	 * @param graphics where to paint it, in term of panel
	 * @param camera to paint it at the good position
	 */
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
	
	/**
	 * to paint fog of war
	 * @param fog fog of war
	 * @param graphics
	 * @param camera
	 */
	public void paint(Fog fog, Graphics graphics, Camera camera) {
		boolean[][] removeFog = fog.getFog();
		FogCase[][] dynamicFog = fog.getDynamicFog();
		int tileSize = GameConfiguration.TILE_SIZE;
		
		for (int lineIndex = 0; lineIndex < fog.getLineCount(); lineIndex++) 
		{
			for (int columnIndex = 0; columnIndex < fog.getColumnCount(); columnIndex++) 
			{
				if(!GameConfiguration.debug_mod) {
					if(dynamicFog[lineIndex][columnIndex].getVisible() == true) {
						graphics.setColor(new Color(0,0,0, 100));
						graphics.fillRect(columnIndex * tileSize - camera.getX(), lineIndex * tileSize - camera.getY(), tileSize, tileSize);
					}
				}
				if(removeFog[lineIndex][columnIndex] == true) {
					graphics.setColor(Color.black);
					graphics.fillRect(columnIndex * tileSize - camera.getX(), lineIndex * tileSize - camera.getY(), tileSize, tileSize);
				}
			}
		}
	}
	
	/**
	 * To paint all map
	 * @param map to paint
	 * @param graphics
	 * @param camera
	 * @param graphicsManager to get grass tile that use under all tile
	 */
	public void paint(Map map, Graphics graphics, Camera camera, GraphicsManager graphicsManager)
	{
		graphics.drawImage(graphicsManager.getPanelGaucheBas(), 0, 0, GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT, null);
		int tileSize = GameConfiguration.TILE_SIZE;
		
		Tile[][] tiles = map.getTiles();
		int width = GameConfiguration.WINDOW_WIDTH;
		int height = GameConfiguration.WINDOW_HEIGHT;
		if(GameConfiguration.launchInFullScreen) {
			width = Toolkit.getDefaultToolkit().getScreenSize().width;
			height = Toolkit.getDefaultToolkit().getScreenSize().height;
		}
		//draw map
		for (int lineIndex = 0; lineIndex < map.getLineCount(); lineIndex++) 
		{
			for (int columnIndex = 0; columnIndex < map.getColumnCount(); columnIndex++) 
			{
				Tile tile = tiles[lineIndex][columnIndex];
//tilePos.x + tilePos.w >= 0 && tilePos.y + tilePos.h >= 0 && tilePos.x <= GAME_WIDTH && tilePos.y <= GAME_HEIGHT
				if(tile.getColumn() * tileSize - camera.getX() + tileSize >= 0 
					&& tile.getLine() * tileSize - camera.getY() + tileSize >= 0 
					&& tile.getColumn() * tileSize - camera.getX() <= width && tile.getLine() * tileSize - camera.getY() <= height) {
					Animation animation = tile.getAnimation();
					graphics.drawImage(graphicsManager.getGrassTile(), tile.getColumn() * tileSize - camera.getX(), tile.getLine() * tileSize - camera.getY(), tileSize, tileSize, null);
					graphics.drawImage(animation.getFrame(), tile.getColumn() * tileSize - camera.getX(), tile.getLine() * tileSize - camera.getY(), tileSize, tileSize, null);
					/*graphics.setColor(Color.white);
					graphics.drawRect(tile.getColumn() * tileSize - camera.getX(), tile.getLine() * tileSize - camera.getY(), tileSize, tileSize);*/
				}
			}
		}
	}
	
	/**
	 * paint all gui, like minimap and all panel, entity on minimap
	 * @param map map to paint
	 * @param fog fog to paint on minimap
	 * @param entities all entity to paint
	 * @param graphics
	 * @param camera
	 * @param infoTargetPanel left bottom panel
	 * @param infoUpPanel left upper panel
	 * @param minimapPanel right bottom panel
	 * @param minimap to get minimap size
	 * @param graphicsManager to get panel texture
	 */
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
	
	/**
	 * call by paintGui to paint specificaly all entity
	 * @param entity to paint
	 * @param graphics
	 * @param camera
	 * @param minimap to know where to paint entity precisly on minimap
	 */
	public void paintEntityGui(Entity entity, Graphics graphics, Camera camera, Minimap minimap)
	{	
		if(entity.getFaction() == EntityConfiguration.PLAYER_FACTION)
		{
			graphics.setColor(Color.green);
		}
		else if(entity.getFaction() == EntityConfiguration.BOT_FACTION)
		{
			graphics.setColor(Color.red);
		}
		else if(entity.getId() == EntityConfiguration.RESSOURCE)
		{
			graphics.setColor(Color.yellow);
		}
		graphics.fillRect(entity.getPosition().getX() / GameConfiguration.TILE_SIZE * minimap.getGridMapWidth() + minimap.getFirstGridXOfMap(), 
				entity.getPosition().getY() / GameConfiguration.TILE_SIZE * minimap.getGridMapHeight() + minimap.getFirstGridYOfMap(), 
				minimap.getGridMapWidth(), minimap.getGridMapHeight());
	}
}