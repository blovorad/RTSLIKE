package engine.map;

import javax.swing.JPanel;

import configuration.GameConfiguration;

/**
 * @author gautier
 */
public class Minimap {
	
	//variable for generate Minimap	
	private int firstGridXOfMap;
	private int firstGridYOfMap;
	private int gridMapWidth;
	private int gridMapHeight;
	
	public Minimap(JPanel minimapPanel) {
		
		int panelOffset = 10;
		int panelWidth = minimapPanel.getWidth();
		int panelHeight = minimapPanel.getHeight();
		int panelX = minimapPanel.getX() + panelWidth / 2;
		int panelY = minimapPanel.getY();
		
		firstGridXOfMap = panelX + panelOffset;
		firstGridYOfMap = panelY + panelOffset;
		gridMapWidth = (panelWidth / 2) / GameConfiguration.COLUMN_COUNT;
		gridMapHeight = panelHeight / GameConfiguration.LINE_COUNT;
	}
	
	
	public int getFirstGridXOfMap() {
		return firstGridXOfMap;
	}
	public void setFirstGridXOfMap(int firstGridXOfMap) {
		this.firstGridXOfMap = firstGridXOfMap;
	}
	public int getFirstGridYOfMap() {
		return firstGridYOfMap;
	}
	public void setFirstGridYOfMap(int firstGridYOfMap) {
		this.firstGridYOfMap = firstGridYOfMap;
	}
	public int getGridMapWidth() {
		return gridMapWidth;
	}
	public void setGridMapWidth(int gridMapWidth) {
		this.gridMapWidth = gridMapWidth;
	}
	public int getGridMapHeight() {
		return gridMapHeight;
	}
	public void setGridMapHeight(int gridMapHeight) {
		this.gridMapHeight = gridMapHeight;
	}	
}
