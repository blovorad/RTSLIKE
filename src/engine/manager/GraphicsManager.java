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
	private AbstractMap<Integer, BufferedImage> graphicsTile;
	
	public GraphicsManager() {
		this.setGraphicsEntity(new HashMap<Integer, BufferedImage>());
		this.setGraphicsTile(new HashMap<Integer, BufferedImage>());
		
		try {
			graphicsTile.put(MapConfiguration.GRASS, ImageIO.read(new File("src/graphics/grass_1.png")));
			graphicsTile.put(MapConfiguration.ROCK, ImageIO.read(new File("src/graphics/stone_1.png")));
			graphicsTile.put(MapConfiguration.WOOD, ImageIO.read(new File("src/graphics/plant_3.png")));
			graphicsTile.put(MapConfiguration.WATER, ImageIO.read(new File("src/graphics/water_1.png")));
			graphicsEntity.put(EntityConfiguration.RESSOURCE, ImageIO.read(new File("src/graphics/gold_1.png")));
			
		} catch (IOException e) {
			e.printStackTrace();
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
}
