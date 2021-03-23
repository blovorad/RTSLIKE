package engine.manager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	private AbstractMap<Integer, List<BufferedImage>> graphicsUnitPlayer;
	private AbstractMap<Integer, List<BufferedImage>> graphicsUnitBot;
	private AbstractMap<Integer, BufferedImage> graphicsTile;
	private BufferedImage panelGaucheBas;
	
	public GraphicsManager() {
		this.setGraphicsEntity(new HashMap<Integer, BufferedImage>());
		this.setGraphicsTile(new HashMap<Integer, BufferedImage>());
		this.setGraphicsBot(new HashMap<Integer, BufferedImage>());
		this.setGraphicsPlayer(new HashMap<Integer, BufferedImage>());
		this.setGraphicsUnitPlayer(new HashMap<Integer, List<BufferedImage>>());
		this.setGraphicsUnitBot(new HashMap<Integer, List<BufferedImage>>());
		
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
			
			initUnitPlayerGraphics();
			initUnitBotGraphics();
			
			panelGaucheBas = ImageIO.read(new File("src/graphics/panelGaucheBas.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initUnitPlayerGraphics() {
		BufferedImage workerImage = null;
		BufferedImage archerImage = null;
		BufferedImage infantryImage = null;
		BufferedImage specialImage = null;
		BufferedImage cavalryImage = null;
		try {
			workerImage = ImageIO.read(new File("src/graphics/engineerPlayer.png"));
			archerImage = ImageIO.read(new File("src/graphics/archerPlayer.png"));
			infantryImage = ImageIO.read(new File("src/graphics/knightPlayer.png"));
			specialImage = ImageIO.read(new File("src/graphics/thiefPlayer.png"));
			cavalryImage = ImageIO.read(new File("src/graphics/cavalryPlayer.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		graphicsUnitPlayer.put(EntityConfiguration.INFANTRY, new ArrayList<BufferedImage>());
		graphicsUnitPlayer.put(EntityConfiguration.ARCHER, new ArrayList<BufferedImage>());
		graphicsUnitPlayer.put(EntityConfiguration.CAVALRY, new ArrayList<BufferedImage>());
		graphicsUnitPlayer.put(EntityConfiguration.SPECIAL_UNIT, new ArrayList<BufferedImage>());
		graphicsUnitPlayer.put(EntityConfiguration.WORKER, new ArrayList<BufferedImage>());
		
		List<BufferedImage> image = graphicsUnitPlayer.get(EntityConfiguration.WORKER);
		for(int i = 0; i < 4; i++) {
			BufferedImage imageBis = workerImage.getSubimage(i * 512, 5 * 512, 512, 512);
			int decoupeX = 80;
			int decoupeY = 80;
			image.add(imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 512 - decoupeY * 2));
		}
		graphicsUnitPlayer.put(EntityConfiguration.WORKER, image);
		
		image = graphicsUnitPlayer.get(EntityConfiguration.ARCHER);
		for(int i = 0; i < 4; i++) {
			BufferedImage imageBis = archerImage.getSubimage(i * 512, 5 * 512, 512, 512);
			int decoupeX = 80;
			int decoupeY = 80;
			image.add(imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 512 - decoupeY * 2));
		}
		graphicsUnitPlayer.put(EntityConfiguration.ARCHER, image);
		
		image = graphicsUnitPlayer.get(EntityConfiguration.SPECIAL_UNIT);
		for(int i = 0; i < 4; i++) {
			BufferedImage imageBis = specialImage.getSubimage(i * 512, 5 * 512, 512, 512);
			int decoupeX = 80;
			int decoupeY = 80;
			image.add(imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 512 - decoupeY * 2));
		}
		graphicsUnitPlayer.put(EntityConfiguration.SPECIAL_UNIT, image);
		
		image = graphicsUnitPlayer.get(EntityConfiguration.CAVALRY);
		for(int i = 0; i < 4; i++) {
			BufferedImage imageBis = cavalryImage.getSubimage(i * 512, 1 * 512, 512, 512);
			int decoupeX = 80;
			int decoupeY = 80;
			image.add(imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 512 - decoupeY * 2));
		}
		graphicsUnitPlayer.put(EntityConfiguration.CAVALRY, image);
		
		image = graphicsUnitPlayer.get(EntityConfiguration.INFANTRY);
		for(int i = 0; i < 4; i++) {
			BufferedImage imageBis = infantryImage.getSubimage(i * 512, 5 * 512, 512, 512);
			int decoupeX = 80;
			int decoupeY = 80;
			image.add(imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 512 - decoupeY * 2));
		}
		graphicsUnitPlayer.put(EntityConfiguration.INFANTRY, image);
	}
	
	public void initUnitBotGraphics() {
		BufferedImage workerImage = null;
		BufferedImage archerImage = null;
		BufferedImage infantryImage = null;
		BufferedImage specialImage = null;
		BufferedImage cavalryImage = null;
		try {
			workerImage = ImageIO.read(new File("src/graphics/engineerBot.png"));
			archerImage = ImageIO.read(new File("src/graphics/archerBot.png"));
			infantryImage = ImageIO.read(new File("src/graphics/knightBot.png"));
			specialImage = ImageIO.read(new File("src/graphics/thiefBot.png"));
			cavalryImage = ImageIO.read(new File("src/graphics/cavalryBot.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		graphicsUnitBot.put(EntityConfiguration.INFANTRY, new ArrayList<BufferedImage>());
		graphicsUnitBot.put(EntityConfiguration.ARCHER, new ArrayList<BufferedImage>());
		graphicsUnitBot.put(EntityConfiguration.CAVALRY, new ArrayList<BufferedImage>());
		graphicsUnitBot.put(EntityConfiguration.SPECIAL_UNIT, new ArrayList<BufferedImage>());
		graphicsUnitBot.put(EntityConfiguration.WORKER, new ArrayList<BufferedImage>());
		
		List<BufferedImage> image = graphicsUnitBot.get(EntityConfiguration.WORKER);
		for(int i = 0; i < 4; i++) {
			BufferedImage imageBis = workerImage.getSubimage(i * 512, 5 * 512, 512, 512);
			int decoupeX = 80;
			int decoupeY = 80;
			image.add(imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 512 - decoupeY * 2));
		}
		graphicsUnitBot.put(EntityConfiguration.WORKER, image);
		
		image = graphicsUnitBot.get(EntityConfiguration.ARCHER);
		for(int i = 0; i < 4; i++) {
			BufferedImage imageBis = archerImage.getSubimage(i * 512, 5 * 512, 512, 512);
			int decoupeX = 80;
			int decoupeY = 80;
			image.add(imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 512 - decoupeY * 2));
		}
		graphicsUnitBot.put(EntityConfiguration.ARCHER, image);
		
		image = graphicsUnitBot.get(EntityConfiguration.SPECIAL_UNIT);
		for(int i = 0; i < 4; i++) {
			BufferedImage imageBis = specialImage.getSubimage(i * 512, 5 * 512, 512, 512);
			int decoupeX = 80;
			int decoupeY = 80;
			image.add(imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 512 - decoupeY * 2));
		}
		graphicsUnitBot.put(EntityConfiguration.SPECIAL_UNIT, image);
		
		image = graphicsUnitBot.get(EntityConfiguration.CAVALRY);
		for(int i = 0; i < 4; i++) {
			BufferedImage imageBis = cavalryImage.getSubimage(i * 512, 1 * 512, 512, 512);
			int decoupeX = 80;
			int decoupeY = 80;
			image.add(imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 512 - decoupeY * 2));
		}
		graphicsUnitBot.put(EntityConfiguration.CAVALRY, image);
		
		image = graphicsUnitBot.get(EntityConfiguration.INFANTRY);
		for(int i = 0; i < 4; i++) {
			BufferedImage imageBis = infantryImage.getSubimage(i * 512, 5 * 512, 512, 512);
			int decoupeX = 80;
			int decoupeY = 80;
			image.add(imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 512 - decoupeY * 2));
		}
		graphicsUnitBot.put(EntityConfiguration.INFANTRY, image);
	}
	
	public BufferedImage getGraphicsForUnit(int id, int frame, int faction) {
		//System.out.println("entity : " + entity.getId() + ", " + entity.getDescription() + ", " + entity.getFrame());
		List<BufferedImage>image = null;
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			image = graphicsUnitPlayer.get(id);
		}
		else {
			image = graphicsUnitBot.get(id);
		}
		
		return image.get(frame);
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

	public AbstractMap<Integer, List<BufferedImage>> getGraphicsUnitPlayer() {
		return graphicsUnitPlayer;
	}

	public void setGraphicsUnitPlayer(AbstractMap<Integer, List<BufferedImage>> graphicsUnitPlayer) {
		this.graphicsUnitPlayer = graphicsUnitPlayer;
	}

	public AbstractMap<Integer, List<BufferedImage>> getGraphicsUnitBot() {
		return graphicsUnitBot;
	}

	public void setGraphicsUnitBot(AbstractMap<Integer, List<BufferedImage>> graphicsUnitBot) {
		this.graphicsUnitBot = graphicsUnitBot;
	}
}
