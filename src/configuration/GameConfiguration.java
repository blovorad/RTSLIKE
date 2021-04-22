package configuration;

/**
 * 
 * @author gautier
 * this class contain all constant for setup configuration
 */

public class GameConfiguration 
{
	/**
	 * game speed, is like fps
	 */
	public final static int GAME_SPEED = 1000/30;
	
	/**
	 * screen width
	 */
	public final static int WINDOW_WIDTH = 1366;//Toolkit.getDefaultToolkit().getScreenSize().width;
	/**
	 * screen height
	 */
	public final static int WINDOW_HEIGHT = 768;//Toolkit.getDefaultToolkit().getScreenSize().height;
	
	/**
	 * if true then window width and window height is useless
	 */
	public final static boolean launchInFullScreen = true;
	/**
	 * if you want debug, if true you can see the IA and no dynamic fog
	 */
	public final static boolean debug_mod = true;
	
	/**
	 * MAP WIDTH
	 */
	public final static int COLUMN_COUNT = 100;
	
	/**
	 * MAP HEIGHT
	 */
	public final static int LINE_COUNT = 100;
	
	/**
	 * SIZE OF EACH TILE
	 */
	public final static int TILE_SIZE = 64;
	
	/**
	 * state of the game
	 */
	public final static int INMENU = 0;
	/**
	 * state of the game
	 */
	public final static int INGAME = 1;
	/**
	 * state of the game
	 */
	public final static int INOPTION = 2;
	/**
	 * state of the game
	 */
	public final static int INPAUSEMENU = 3;
	/**
	 * state of the game
	 */
	public final static int BOTWIN = 4;
	/**
	 * state of the game
	 */
	public final static int PLAYERWIN = 5;
	
	/**
	 * direction constant
	 */
	public final static int LEFT = 0;
	/**
	 * direction constant
	 */
	public final static int RIGHT = 1;
}