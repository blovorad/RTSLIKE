package engine.map;

import javax.swing.JPanel;

import configuration.GameConfiguration;

/**
 * @author gautier
 * this class represent the minimap who will be print on the right side of screen
 */
public class Minimap {
	
	/**
	 * where the minimap start X
	 */
	private int firstGridXOfMap;
	/**
	 * where the minimap start Y
	 */
	private int firstGridYOfMap;
	/**
	 * max size width of map
	 */
	private int gridMapWidth;
	/**
	 * max size height of map
	 */
	private int gridMapHeight;
	
	/**
	 * constructor of minimap
	 * @param minimapPanel the panel where the minimap will be print
	 */
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