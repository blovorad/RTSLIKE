package engine.map;

import java.util.ArrayList;
import java.util.List;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Entity;
import engine.math.Collision;

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
	private List<FogCase> lockedList;
	
	public Fog(int lineCount, int columnCount) {
		this.setLineCount(lineCount);
		this.setColumnCount(columnCount);
		this.lockedList = new ArrayList<FogCase>();
		
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
		if(!GameConfiguration.debug_mod) {
			for(FogCase fogCase : lockedList) {
				fogCase.setLocked(false);
				fogCase.setVisible(true);
			}
			lockedList.clear();
		}
	}
	
	public void clearFog(int x, int y, int sightRange, Entity entity, List<Entity>drawingList, List<Entity> waitingList, List<Entity> removeList, List<Entity> botEntities) {
		int xTab = x / GameConfiguration.TILE_SIZE;
		int yTab = y / GameConfiguration.TILE_SIZE;
		
		if(entity.getId() == EntityConfiguration.SITE_CONSTRUCTION) {
			xTab = entity.getPosition().getX() / GameConfiguration.TILE_SIZE;
			yTab =  entity.getPosition().getY() / GameConfiguration.TILE_SIZE;
		}
		
		if(xTab < 0) {
			xTab = 0;
		}
		else if(xTab >= GameConfiguration.COLUMN_COUNT) {
			xTab = GameConfiguration.COLUMN_COUNT - 1;
		}
		
		if(yTab < 0) {
			yTab = 0;
		}
		else if(yTab >= GameConfiguration.LINE_COUNT) {
			yTab = GameConfiguration.LINE_COUNT - 1;
		}
		
		int widthTab = (x + sightRange) / GameConfiguration.TILE_SIZE;
		int heightTab = (y + sightRange) / GameConfiguration.TILE_SIZE;
		if(entity.getId() == EntityConfiguration.SITE_CONSTRUCTION) {
			widthTab = entity.getPosition().getX() / GameConfiguration.TILE_SIZE + 1;
			heightTab =  entity.getPosition().getY() / GameConfiguration.TILE_SIZE + 1;
		}
		
		if(widthTab > GameConfiguration.COLUMN_COUNT) {
			widthTab = GameConfiguration.COLUMN_COUNT;
		}
		if(heightTab > GameConfiguration.LINE_COUNT) {
			heightTab = GameConfiguration.LINE_COUNT;
		}
		
		//System.out.println("TAB : " + xTab + "," + yTab + ", et " + widthTab + "," +heightTab);
		for (int lineIndex = yTab; lineIndex < heightTab; lineIndex++) {
			for (int columnIndex = xTab; columnIndex < widthTab; columnIndex++) {
				if(lineIndex < GameConfiguration.LINE_COUNT && columnIndex < GameConfiguration.COLUMN_COUNT) {
					fog[lineIndex][columnIndex] = false;
					if(!GameConfiguration.debug_mod) {
						if(botEntities != null) {
							for(Entity botEntity : botEntities) {
								if(Collision.collideFogEntity(xTab, yTab, widthTab, heightTab, botEntity)) {
									if(!waitingList.contains(botEntity)) {
										waitingList.add(botEntity);
									}
								}
								else {
									if(!removeList.contains(botEntity)) {
										removeList.add(botEntity);
									}
								}
							}
						}
						dynamicFog[lineIndex][columnIndex].setVisible(false);
						dynamicFog[lineIndex][columnIndex].setLocked(true);
						lockedList.add(dynamicFog[lineIndex][columnIndex]);
					}
				}
			}
		}
	}
	
	public void checkUnit(List<Entity> playerList, List<Entity> waitingList, List<Entity> removeList) {
		for (int lineIndex = 0; lineIndex < GameConfiguration.LINE_COUNT; lineIndex++) {
			for (int columnIndex = 0; columnIndex < GameConfiguration.COLUMN_COUNT; columnIndex++) {
				if(fog[lineIndex][columnIndex] == false) {
					int widthTab = columnIndex + 1;
					int heightTab = lineIndex + 1;
					if(widthTab > GameConfiguration.COLUMN_COUNT - 1) {
						widthTab = GameConfiguration.COLUMN_COUNT - 1;
					}
					if(heightTab > GameConfiguration.LINE_COUNT - 1) {
						heightTab = GameConfiguration.LINE_COUNT - 1;
					}
					for(Entity entity : playerList) {
						if(Collision.collideFogEntity(columnIndex, lineIndex, widthTab, heightTab, entity)) {
							if(!waitingList.contains(entity)) {
								waitingList.add(entity);
							}
						}
						else {
							if(!removeList.contains(entity)) {
								removeList.add(entity);
							}
						}
					}
				}
			}
		}
	}
	
	public void checkingPlayerTargetInFog(List<Entity> visibleList, List<Entity> waitingList, List<Entity> removeList) {
		for(Entity entity : waitingList) {
			if(removeList.contains(entity)) {
				removeList.remove(entity);
			}
		}
		
		for(Entity entity : visibleList) {
			if(waitingList.contains(entity)) {
				waitingList.remove(entity);
			}
		}
	}
	
	public void checkingBotTargetInFog(List<Entity> drawingList, List<Entity> waitingList, List<Entity> removeList){
		if(!GameConfiguration.debug_mod) {
			for(Entity entity : waitingList) {
				if(removeList.contains(entity)) {
					removeList.remove(entity);
				}
			}
			
			for(Entity entity : drawingList) {
				if(waitingList.contains(entity)) {
					waitingList.remove(entity);
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
