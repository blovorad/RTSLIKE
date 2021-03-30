package configuration;

import java.awt.Toolkit;

/**
 * 
 * @author gautier
 *
 */

public class GameConfiguration 
{
	public final static int GAME_SPEED = 1000/30;
	
	public final static int WINDOW_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;

	public final static int WINDOW_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public final static boolean launchInFullScreen = true;
	
	public final static int COLUMN_COUNT = 100;
	
	public final static int LINE_COUNT = 100;
	
	public final static int TILE_SIZE = 64;
	
	public final static int INMENU = 0;
	public final static int INGAME = 1;
	public final static int INOPTION = 2;
	public final static int INPAUSEMENU = 3;
	
	public final static int LEFT = 0;
	public final static int RIGHT = 1;
	
}
