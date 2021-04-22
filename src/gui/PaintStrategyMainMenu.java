package gui;

import java.awt.Graphics;
import java.awt.Toolkit;

import configuration.GameConfiguration;
import engine.manager.GraphicsManager;

/**
 * 
 * @author gautier
 * class who charge to paint main Menu
 */

public class PaintStrategyMainMenu {
	
	/**
	 * method who paint panel of the main maneu
	 * @param graphics current place where to paint
	 * @param graphicsManager to get the texture to paint
	 */
	public void paint(Graphics graphics, GraphicsManager graphicsManager) {
		if(GameConfiguration.launchInFullScreen) {
			graphics.drawImage(graphicsManager.getPanelGaucheBas(), 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
		}
		else {
			graphics.drawImage(graphicsManager.getPanelGaucheBas(), 0, 0, GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT, null);
		}
	}
}