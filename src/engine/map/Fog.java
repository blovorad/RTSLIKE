package engine.map;

import java.util.ArrayList;
import java.util.List;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Entity;
import engine.entity.unit.Unit;
import engine.math.Collision;

/**
 * 
 * @author gautier
 *	this class is used for manage fog of war and dynamic fog
 */

public class Fog {
	/**
	 * represent the fog who will be remove by a unit that pass on the tile
	 */
	private boolean[][]fog;
	/**
	 * represent the fog who mask unit if you don't are in range of them
	 */
	private FogCase[][] dynamicFog;
	/**
	 * fog size height
	 */
	private int lineCount;
	/**
	 * fog size width
	 */
	private int columnCount;
	/**
	 * list use to know if a fogCase is already analyze on each iteration
	 */
	private List<FogCase> lockedList;
	
	/**
	 * this constructor is the same as Map constructor
	 * @param lineCount size height of fog
	 * @param columnCount size width of fog
	 */
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
	
	/**
	 * this method permit to reset dynamic fog in a accordance with lockedList
	 */
	public void resetDynamicFog() {
		if(!GameConfiguration.debug_mod) {
			for(FogCase fogCase : lockedList) {
				fogCase.setLocked(false);
				fogCase.setVisible(true);
			}
			lockedList.clear();
		}
	}
	
	/**
	 * core function who remove fog and dynamicFog, change the drawingList of all entity and map in function of dynamicFog and fog
	 * @param x to start iteration
	 * @param y to start iteration
	 * @param sightRange line of sight of entity
	 * @param entity current entity who remove fog
	 * @param drawingList using if you are player, drawingList represent what is print to screen
	 * @param waitingList using if you are player, represent what will be added in first when update of entitiesManager is call
	 * @param removeList same as waitingList but in case of remove
	 * @param botEntities represent botEntities
	 */
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
									if(botEntity.getId() >= EntityConfiguration.INFANTRY && botEntity.getId() <= EntityConfiguration.WORKER) {
										if(!removeList.contains(botEntity)) {
											removeList.add(botEntity);
										}
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
	
	/**
	 * method using with bot, specific for IA vision that check if a unit is in range
	 * @param playerUnits 
	 * @param unitWaitingEnnemieVisible represent the unit who will be added to vision of bot
	 * @param unitRemoveEnnemieVisible represent the unit who will be remove to vision of bot
	 */
	public void checkUnit(List<Unit> playerUnits, List<Unit> unitWaitingEnnemieVisible, List<Unit> unitRemoveEnnemieVisible) {
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
					for(Unit unit : playerUnits) {
						if(Collision.collideFogUnit(columnIndex, lineIndex, widthTab, heightTab, unit)) {
							if(!unitWaitingEnnemieVisible.contains(unit)) {
								unitWaitingEnnemieVisible.add(unit);
							}
						}
						else {
							if(!unitRemoveEnnemieVisible.contains(unit)) {
								unitRemoveEnnemieVisible.add(unit);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * this method is call just after a checkUnit and checkBuilding because unit can be seen by a unit but not in a another unit range
	 * so to present that a unit is seeing we will remove of unitRemoveEnnemieVisible each unit that are in unitWaitingEnnemieVisible
	 * @param unitEnnemieVisible
	 * @param unitWaitingEnnemieVisible
	 * @param unitRemoveEnnemieVisible
	 */
	public void checkingPlayerTargetUnitInFog(List<Unit> unitEnnemieVisible, List<Unit> unitWaitingEnnemieVisible, List<Unit> unitRemoveEnnemieVisible) {
		for(Unit entity : unitWaitingEnnemieVisible) {
			if(unitRemoveEnnemieVisible.contains(entity)) {
				unitRemoveEnnemieVisible.remove(entity);
			}
		}
		
		for(Unit entity : unitEnnemieVisible) {
			if(unitWaitingEnnemieVisible.contains(entity)) {
				unitWaitingEnnemieVisible.remove(entity);
			}
		}
	}
	
	/**
	 * same method as checkUnit
	 * @param playerBuildings
	 * @param buildingWaitingEnnemieVisible
	 * @param buildingRemoveEnnemieVisible
	 */
	public void checkBuilding(List<Entity> playerBuildings, List<Entity> buildingWaitingEnnemieVisible, List<Entity> buildingRemoveEnnemieVisible){
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
					for(Entity entity : playerBuildings) {
						if(Collision.collideFogEntity(columnIndex, lineIndex, widthTab, heightTab, entity)) {
							if(!buildingWaitingEnnemieVisible.contains(entity)) {
								buildingWaitingEnnemieVisible.add(entity);
							}
						}
						else {
							if(!buildingRemoveEnnemieVisible.contains(entity)) {
								buildingRemoveEnnemieVisible.add(entity);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * same method as checkUnit
	 * @param buildingEnnemieVisible
	 * @param buildingWaitingEnnemieVisible
	 * @param buildingRemoveEnnemieVisible
	 */
	public void checkingPlayerTargetBuildingInFog(List<Entity> buildingEnnemieVisible, List<Entity> buildingWaitingEnnemieVisible, List<Entity> buildingRemoveEnnemieVisible){
		for(Entity entity : buildingWaitingEnnemieVisible) {
			if(buildingRemoveEnnemieVisible.contains(entity)) {
				buildingRemoveEnnemieVisible.remove(entity);
			}
		}
		
		for(Entity entity : buildingEnnemieVisible) {
			if(buildingWaitingEnnemieVisible.contains(entity)) {
				buildingWaitingEnnemieVisible.remove(entity);
			}
		}
	}
	
	/**
	 * same method as checkingPlayerTargetUnitInFog but for the player
	 * @param drawingList
	 * @param waitingList
	 * @param removeList
	 */
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