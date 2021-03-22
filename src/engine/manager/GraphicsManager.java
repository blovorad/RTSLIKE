package engine.manager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;

import javax.imageio.ImageIO;

import configuration.EntityConfiguration;
import configuration.MapConfiguration;

/**
 * 
 * @author gautier
 *
 */

public class GraphicsManager {
	private AbstractMap<Integer, BufferedImage> graphicsEntity;
	private AbstractMap<Integer, BufferedImage> graphicsPlayer;
	private AbstractMap<Integer, BufferedImage> graphicsBot;
	private AbstractMap<Integer, BufferedImage> graphicsTile;
	private BufferedImage panelGaucheBas;
	
	public GraphicsManager() {
		this.setGraphicsEntity(new HashMap<Integer, BufferedImage>());
		this.setGraphicsTile(new HashMap<Integer, BufferedImage>());
		this.setGraphicsBot(new HashMap<Integer, BufferedImage>());
		this.setGraphicsPlayer(new HashMap<Integer, BufferedImage>());
		
		try {
			graphicsTile.put(MapConfiguration.GRASS, ImageIO.read(new File("src/graphics/grass_1.png")));
			graphicsTile.put(MapConfiguration.ROCK, ImageIO.read(new File("src/graphics/stone_1.png")));
			graphicsTile.put(MapConfiguration.WOOD, ImageIO.read(new File("src/graphics/plant_3.png")));
			graphicsTile.put(MapConfiguration.WATER, ImageIO.read(new File("src/graphics/water_1.png")));
			
			graphicsEntity.put(EntityConfiguration.RESSOURCE, ImageIO.read(new File("src/graphics/gold_1.png")));
			
			graphicsBot.put(EntityConfiguration.FORGE, ImageIO.read(new File("src/graphics/forgeBot.png")));
			graphicsBot.put(EntityConfiguration.STORAGE, ImageIO.read(new File("src/graphics/storageBot.png")));
			graphicsBot.put(EntityConfiguration.STABLE, ImageIO.read(new File("src/graphics/stableBot.png")));
			graphicsBot.put(EntityConfiguration.HQ, ImageIO.read(new File("src/graphics/hqBot.png")));
			graphicsBot.put(EntityConfiguration.TOWER, ImageIO.read(new File("src/graphics/towerBot.png")));
			graphicsBot.put(EntityConfiguration.CASTLE, ImageIO.read(new File("src/graphics/castleBot.png")));
			graphicsBot.put(EntityConfiguration.BARRACK, ImageIO.read(new File("src/graphics/barrackBot.png")));
			graphicsBot.put(EntityConfiguration.ARCHER, ImageIO.read(new File("src/graphics/archerieBot.png")));
			
			graphicsPlayer.put(EntityConfiguration.FORGE, ImageIO.read(new File("src/graphics/forgePlayer.png")));
			graphicsPlayer.put(EntityConfiguration.STORAGE, ImageIO.read(new File("src/graphics/storagePlayer.png")));
			graphicsPlayer.put(EntityConfiguration.STABLE, ImageIO.read(new File("src/graphics/stablePlayer.png")));
			graphicsPlayer.put(EntityConfiguration.HQ, ImageIO.read(new File("src/graphics/hqPlayer.png")));
			graphicsPlayer.put(EntityConfiguration.TOWER, ImageIO.read(new File("src/graphics/towerPlayer.png")));
			graphicsPlayer.put(EntityConfiguration.CASTLE, ImageIO.read(new File("src/graphics/castlePlayer.png")));
			graphicsPlayer.put(EntityConfiguration.BARRACK, ImageIO.read(new File("src/graphics/barrackPlayer.png")));
			graphicsPlayer.put(EntityConfiguration.ARCHER, ImageIO.read(new File("src/graphics/archeriePlayer.png")));
			
			
			
			panelGaucheBas = ImageIO.read(new File("src/graphics/panelGaucheBas.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getGraphicsForBuilding(int id, int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			return graphicsPlayer.get(id);
		}
		else {
			return graphicsBot.get(id);
		}
	}
	
	public BufferedImage getGraphicsTile(int id) {
		return graphicsTile.get(id);
	}
	
	public BufferedImage getGraphicsEntity(int id) {
		return graphicsEntity.get(id);
	}

	public AbstractMap<Integer, BufferedImage> getGraphicsTile() {
		return graphicsTile;
	}

	public void setGraphicsTile(AbstractMap<Integer, BufferedImage> graphicsTile) {
		this.graphicsTile = graphicsTile;
	}

	public AbstractMap<Integer, BufferedImage> getGraphicsEntity() {
		return graphicsEntity;
	}

	public void setGraphicsEntity(AbstractMap<Integer, BufferedImage> graphicsEntity) {
		this.graphicsEntity = graphicsEntity;
	}

	public BufferedImage getPanelGaucheBas() {
		return panelGaucheBas;
	}

	public void setPanelGaucheBas(BufferedImage panelGaucheBas) {
		this.panelGaucheBas = panelGaucheBas;
	}

	public AbstractMap<Integer, BufferedImage> getGraphicsBot() {
		return graphicsBot;
	}

	public void setGraphicsBot(AbstractMap<Integer, BufferedImage> graphicsBot) {
		this.graphicsBot = graphicsBot;
	}

	public AbstractMap<Integer, BufferedImage> getGraphicsPlayer() {
		return graphicsPlayer;
	}

	public void setGraphicsPlayer(AbstractMap<Integer, BufferedImage> graphicsPlayer) {
		this.graphicsPlayer = graphicsPlayer;
	}
}
