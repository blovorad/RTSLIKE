package engine.map;

import configuration.GameConfiguration;

/**
 * 
 * @author gautier
 *
 */

public class Fog {
	
	private boolean[][]fog;
	private int lineCount;
	private int columnCount;
	
	public Fog(int lineCount, int columnCount) {
		this.setLineCount(lineCount);
		this.setColumnCount(columnCount);
		
		fog = new boolean[lineCount][columnCount];
		
		for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				fog[lineIndex][columnIndex] = true;
			}
		}
	}
	
	public void clearFog(int x, int y, int sightRange) {
		
		for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				double dist =  Math.sqrt(Math.pow(x - columnIndex * GameConfiguration.TILE_SIZE, 2) + Math.pow(y - lineIndex * GameConfiguration.TILE_SIZE, 2));
				if(dist < sightRange) {
					fog[lineIndex][columnIndex] = false;
				}
			}
		}
	}

	public boolean[][] getFog() {
		return fog;
	}

	public void setFog(boolean[][] fog) {
		this.fog = fog;
	}

	public int getLineCount() {
		return lineCount;
	}

	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

}
