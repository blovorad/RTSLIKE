package engine;

import java.awt.image.BufferedImage;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.manager.GraphicsManager;

/**
 * 
 * @author gautier
 *
 */
public class Animation {
	
	private boolean hasAnimation;
	private float currentFrame;
	private int maxFrame;
	private int direction;
	private int frameState;
	private BufferedImage[][] leftImages;
	private BufferedImage[][] rightImages;
	
	public Animation(int maxFrame, boolean hasAnimation, GraphicsManager graphicsManager, int id, int faction) {
		this.maxFrame = maxFrame;
		this.currentFrame = 0;
		this.frameState = 0;
		this.hasAnimation = hasAnimation;
		this.setDirection(GameConfiguration.LEFT);
		
		if(maxFrame > 0) {
			getEntityWithAnimationTexture(graphicsManager, id, faction);
		}
		else {
			getEntityNoAnimationTexture(graphicsManager, id, faction);
		}
	}

	public Animation(int maxFrame, boolean hasAnimation, BufferedImage[][] graphics) {
		this.maxFrame = maxFrame;
		this.currentFrame = 0;
		this.frameState = 0;
		this.hasAnimation = hasAnimation;
		this.setDirection(GameConfiguration.LEFT);
		leftImages = graphics;
		rightImages = new BufferedImage[0][0];
	}
	
	public void update() {
		if(hasAnimation) {
			currentFrame += (1f / GameConfiguration.GAME_SPEED) * 8;
			if(currentFrame >= maxFrame) {
				currentFrame = 0;
			}
		}
	}
	
	public BufferedImage getFrame() {
		if(direction == GameConfiguration.LEFT) {
			return leftImages[frameState][(int)currentFrame];
		}
		else {
			return rightImages[frameState][(int)currentFrame];
		}
	}
	
	public void getEntityWithAnimationTexture(GraphicsManager graphicsManager, int id, int faction) {
		leftImages = new BufferedImage[4][maxFrame];
		rightImages = new BufferedImage[4][maxFrame];
		
		if(id == EntityConfiguration.WORKER) {
			leftImages = graphicsManager.getLeftWorker(faction);
			rightImages = graphicsManager.getRightWorker(faction);
		}
		if(id == EntityConfiguration.INFANTRY) {
			leftImages = graphicsManager.getLeftInfantry(faction);
			rightImages = graphicsManager.getRightInfantry(faction);
		}
		if(id == EntityConfiguration.ARCHER) {
			leftImages = graphicsManager.getLeftArcher(faction);
			rightImages = graphicsManager.getRightArcher(faction);
		}
		if(id == EntityConfiguration.CAVALRY) {
			leftImages = graphicsManager.getLeftCavalry(faction);
			rightImages = graphicsManager.getRightCavalry(faction);
		}
		if(id == EntityConfiguration.SPECIAL_UNIT) {
			leftImages = graphicsManager.getLeftSpecial(faction);
			rightImages = graphicsManager.getRightSpecial(faction);
		}
	}
	
	public void getEntityNoAnimationTexture(GraphicsManager graphicsManager, int id, int faction)
	{
		rightImages = new BufferedImage[0][0];
		if(id == EntityConfiguration.RESSOURCE) {
			leftImages = graphicsManager.getGold();
		}
		else if(id == EntityConfiguration.BARRACK) {
			leftImages = graphicsManager.getBarrack(faction);
		}
		else if(id == EntityConfiguration.ARCHERY) {
			leftImages = graphicsManager.getArchery(faction);
		}
		else if(id == EntityConfiguration.STABLE) {
			leftImages = graphicsManager.getStable(faction);
		}
		else if(id == EntityConfiguration.CASTLE) {
			leftImages = graphicsManager.getCastle(faction);
		}
		else if(id == EntityConfiguration.FORGE) {
			leftImages = graphicsManager.getForge(faction);
		}
		else if(id == EntityConfiguration.HQ) {
			leftImages = graphicsManager.getHq(faction);
		}
		else if(id == EntityConfiguration.TOWER) {
			leftImages = graphicsManager.getTower(faction);
		}
		else if(id == EntityConfiguration.STORAGE) {
			leftImages = graphicsManager.getStorage(faction);
		}
	}
	
	public boolean isHasAnimation() {
		return this.hasAnimation;
	}
	
	public float getCurrentFrame() {
		return currentFrame;
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public int getFrameState() {
		return this.frameState;
	}
	
	public void setFrameState(int state) {
		this.frameState = state;
	}

	public BufferedImage[][] getRightImages() {
		return rightImages;
	}

	public void setRightImages(BufferedImage[][] rightImages) {
		this.rightImages = rightImages;
	}

	public BufferedImage[][] getLeftImages() {
		return leftImages;
	}

	public void setLeftImages(BufferedImage[][] leftImages) {
		this.leftImages = leftImages;
	}
}
