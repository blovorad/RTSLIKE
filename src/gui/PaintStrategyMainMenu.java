package gui;

import java.awt.Graphics;
import java.awt.Toolkit;

import engine.manager.GraphicsManager;

/**
 * 
 * @author gautier
 *
 */

public class PaintStrategyMainMenu {
	
	public void paint(Graphics graphics, GraphicsManager graphicsManager) {
		graphics.drawImage(graphicsManager.getPanelGaucheBas(), 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
	}
}
