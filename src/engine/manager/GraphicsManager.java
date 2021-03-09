package engine.manager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * 
 * @author gautier
 *
 */

public class GraphicsManager {
	private AbstractMap<Integer, BufferedImage> graphics;
	
	public GraphicsManager() {
		this.graphics = new HashMap<Integer, BufferedImage>();
		
		//squelette chargement image a affiner
		BufferedImage buff = null;
		try {
			buff = ImageIO.read(new File("src/graphics/mainMenuBackground.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		graphics.put(0, buff);
	}
	
	public BufferedImage getImage(int id) {
		return graphics.get(id);
	}

	public AbstractMap<Integer, BufferedImage> getGraphics() {
		return graphics;
	}

	public void setGraphics(AbstractMap<Integer, BufferedImage> graphics) {
		this.graphics = graphics;
	}
}
