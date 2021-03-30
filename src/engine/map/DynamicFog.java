package engine.map;

import configuration.GameConfiguration;
import engine.Entity;

public class DynamicFog {
	
	private FogCase[][]fog;
	private int lineCount;
	private int columnCount;
	
	public DynamicFog(int lineCount, int columnCount) {
		this.setLineCount(lineCount);
		this.setColumnCount(columnCount);
		
		fog = new FogCase[lineCount][columnCount];
		
		for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				fog[lineIndex][columnIndex].setVisible(false);
			}
		}
	}
	
	public void clearFog(Entity entity) {
		for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				if(fog[lineIndex][columnIndex].getLocked() == false) {
					int x = entity.getPosition().getX() - entity.getSightRange() / 6;
					int y = entity.getPosition().getY() - entity.getSightRange() / 6;
					double dist =  Math.sqrt(Math.pow(x - columnIndex * GameConfiguration.TILE_SIZE, 2) + Math.pow(y - lineIndex * GameConfiguration.TILE_SIZE, 2));
					if(dist < entity.getSightRange()) {
						fog[lineIndex][columnIndex].setVisible(true);
					}
				}
			}
		}
	}
	
	public void resetDynamicFog() {
		for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				if(fog[lineIndex][columnIndex].getLocked() == false) {
					fog[lineIndex][columnIndex].setVisible(false);
				}
			}
		}
	}
	
	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public int getLineCount() {
		return lineCount;
	}

	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}
}
