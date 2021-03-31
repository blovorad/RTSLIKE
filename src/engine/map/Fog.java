package engine.map;

import java.util.List;

import configuration.GameConfiguration;
import engine.Entity;

/**
 * 
 * @author gautier
 *
 */

public class Fog {
	
	private boolean[][]fog;
	private FogCase[][] dynamicFog;
	private int lineCount;
	private int columnCount;
	
	public Fog(int lineCount, int columnCount) {
		this.setLineCount(lineCount);
		this.setColumnCount(columnCount);
		
		fog = new boolean[lineCount][columnCount];
		dynamicFog = new FogCase[lineCount][columnCount];
		
		for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				fog[lineIndex][columnIndex] = true;
				dynamicFog[lineIndex][columnIndex] = new FogCase();
			}
		}
	}
	
	public void resetDynamicFog() {
		for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				dynamicFog[lineIndex][columnIndex].setLocked(false);
			}
		}
	}
	
	public void clearFog(int x, int y, int sightRange, Entity entity, List<Entity>drawingList) {
		for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				double dist =  Math.sqrt(Math.pow(x - columnIndex * GameConfiguration.TILE_SIZE, 2) + Math.pow(y - lineIndex * GameConfiguration.TILE_SIZE, 2));
				if(dist < sightRange) {
					fog[lineIndex][columnIndex] = false;
					if(drawingList != null) {
						if(!drawingList.contains(entity)) {
							//drawingList.add(entity);
						}
					}
					dynamicFog[lineIndex][columnIndex].setVisible(false);
					dynamicFog[lineIndex][columnIndex].setLocked(true);
				}
				else {
					if(dynamicFog[lineIndex][columnIndex].getLocked() == false) {
						dynamicFog[lineIndex][columnIndex].setVisible(true);
						if(drawingList != null) {
							if(drawingList.contains(entity)) {
								//drawingList.remove(entity);
							}
						}
					}
				}
			}
		}
	}

	public boolean[][] getFog() {
		return fog;
	}
	
	public FogCase[][] getDynamicFog(){
		return dynamicFog;
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
