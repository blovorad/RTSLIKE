package configuration;

import java.awt.Toolkit;

public class GameConfiguration 
{
	public final static int GAME_SPEED = 1000/30;
	
	public final static int WINDOW_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	
	public final static int WINDOW_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public final static int COLUMN_COUNT = 100;
	
	public final static int LINE_COUNT = 100;
	
	public final static float SCALE_X = (float)WINDOW_WIDTH/(float)1920.0;
	
	public final static float SCALE_Y = (float)WINDOW_HEIGHT/(float)1080.0;
	
	public final static int TILE_SIZE = 32;
}
