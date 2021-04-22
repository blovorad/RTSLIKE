package engine;

import java.awt.image.BufferedImage;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.manager.GraphicsManager;

/**
 * 
 * @author gautier
 *	this class is used to manage animation
 */
public class Animation {
	/**
	 * if the animation has a real animation like more 1 frame
	 */
	private boolean hasAnimation;
	/**
	 * currentFrame, used to know wich frame we need to print
	 */
	private float currentFrame;
	/**
	 * maxFrame of the animation
	 */
	private int maxFrame;
	/**
	 * direction of the animation to do a flip, can be LEFT or RIGHT
	 */
	private int direction;
	/**
	 * state of the frame that can be Attack, Harvest, Repair, WALK, IDDLE, DIE
	 */
	private int frameState;
	/**
	 * left images of the frame
	 */
	private BufferedImage[][] leftImages;
	/**
	 * right images of the frame
	 */
	private BufferedImage[][] rightImages;
	
	/**
	 * Constructor of this class default
	 * @param maxFrame number of frame
	 * @param hasAnimation if he has an animation
	 * @param graphicsManager to get animation bufferedImage
	 * @param id id to entity who need animation
	 * @param faction faction of the entity
	 */
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
	
	/**
	 * using this constructor if taking bufferedImage directly is more speed
	 * @param maxFrame number of frame
	 * @param hasAnimation if he has an animation
	 * @param graphics if we give him bufferredImage directly
	 */
	public Animation(int maxFrame, boolean hasAnimation, BufferedImage[][] graphics) {
		this.maxFrame = maxFrame;
		this.currentFrame = 0;
		this.frameState = 0;
		this.hasAnimation = hasAnimation;
		this.setDirection(GameConfiguration.LEFT);
		leftImages = graphics;
		rightImages = new BufferedImage[0][0];
	}
	
	/**
	 * core function who charge to modify animation in term of situation
	 */
	public void update() {
		if(hasAnimation) {
			currentFrame += (1f / GameConfiguration.GAME_SPEED) * 8;
			if(currentFrame >= maxFrame) {
				currentFrame = 0;
			}
		}
	}
	
	/**
	 * this method permit to print a frame
	 * @return BufferedImage the current frame to draw
	 */
	public BufferedImage getFrame() {
		if(direction == GameConfiguration.LEFT) {
			return leftImages[frameState][(int)currentFrame];
		}
		else {
			return rightImages[frameState][(int)currentFrame];
		}
	}
		
	/**
	 * this method init frame of entity, who have a animation
	 * @param graphicsManager to get animation bufferedImage
	 * @param id id to entity who need animation
	 * @param faction faction of the entity
	 */
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
	
	/**
	 * this method init frame of entity, who haven't a animation
	 * @param graphicsManager to get animation bufferedImage
	 * @param id id to entity who need animation
	 * @param faction faction of the entity
	 */
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
		else if(id == EntityConfiguration.SITE_CONSTRUCTION) {
			leftImages = graphicsManager.getSiteConstruction();
		}
	}
	
	/**
	 * true if entity has animation
	 * @return boolean
	 */
	public boolean isHasAnimation() {
		return this.hasAnimation;
	}
	
	/**
	 * setting currentFrame
	 * @param frame frame to set
	 */
	public void setCurrentFrame(float frame) {
		this.currentFrame = frame;
	}
	
	/**
	 * get the currentFrame
	 * @return float the currentFrame
	 */
	public float getCurrentFrame() {
		return currentFrame;
	}
	
	/**
	 * get the direction of the frame, left or right
	 * @return int direction of the frame
	 */
	public int getDirection() {
		return direction;
	}
	
	/**
	 * set the direction of the frame
	 * @param direction setting direction
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	/**
	 * get the current frameState, harvest, attack....
	 * @return int frameState
	 */
	public int getFrameState() {
		return this.frameState;
	}
	
	/**
	 * set the current FrameState
	 * @param state
	 */
	public void setFrameState(int state) {
		this.frameState = state;
	}
}
