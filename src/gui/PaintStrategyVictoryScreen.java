package gui;

import java.awt.Graphics;
import java.awt.Toolkit;

import configuration.GameConfiguration;
import engine.manager.GraphicsManager;

public class PaintStrategyVictoryScreen {
	public void paint(Graphics graphics, GraphicsManager graphicsManager) {
		if(GameConfiguration.launchInFullScreen) {
			graphics.drawImage(graphicsManager.getPanelGaucheBas(), 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, null);
		}
		else {
			graphics.drawImage(graphicsManager.getPanelGaucheBas(), 0, 0, GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT, null);
		}
	}
}
