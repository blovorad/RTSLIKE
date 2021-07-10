package engine.manager;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import configuration.PositionConfiguration;
import engine.Faction;
import engine.Position;
import engine.map.Map;
import engine.map.Tile;
import factionConfiguration.ForFighter;
import factionConfiguration.ForWorker;

/**
 * 
 *	this class is require to build a game, when you press startGame in Main menu screen
 *	@author maxime
 */

public class GameBuilder {
	
	/**
	 * static method who build a map
	 * @param selectedMap the current map to build
	 * @param graphicsManager to get all texture of the map
	 * @param manager require to give him all ressource that need to be build
	 * @return the current map build
	 */
	public static Map buildMap(int selectedMap, GraphicsManager graphicsManager, EntitiesManager manager) {
		
		Map map;
		map = new Map(GameConfiguration.LINE_COUNT, GameConfiguration.COLUMN_COUNT, selectedMap, "src/file/map" + selectedMap + ".txt", graphicsManager);
		manager.addRessource(map.getGoldTiles());
		
		return map;
	}
	
	/**
	 * this method build all faction of the game
	 * @param manager to add all faction
	 * @param idfaction1 faction player1
	 * @param idfaction2 faction player2
	 * @param map to place all unit and building with tile position
	 */
	public static void buildFaction(EntitiesManager manager, int idfaction1, int idfaction2, Map map, int population, int money) {
		
		Faction faction1 = new Faction(idfaction1, population, money);
		Faction faction2 = new Faction(idfaction2, population, money);
		Faction gaia = new Faction(4, population, money);
		
		manager.getFactionManager().addFaction(EntityConfiguration.PLAYER_FACTION, faction1);
		manager.getFactionManager().addFaction(EntityConfiguration.BOT_FACTION, faction2);
		manager.getFactionManager().addFaction(EntityConfiguration.GAIA_FACTION, gaia);
		manager.createBotManager(manager.getGraphicsManager(), map);
		
		//cr�ation du joueur
		Tile tile2 = map.getTile(PositionConfiguration.TILE_1_X, PositionConfiguration.TILE_1_Y);
		Tile tile = map.getTile(PositionConfiguration.TILE_2_X, PositionConfiguration.TILE_2_Y);
		ForWorker patron = manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getRace().getPatronWorkers().get(EntityConfiguration.WORKER);
		ForFighter patronFighter = manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getRace().getPatronFighters().get(EntityConfiguration.CAVALRY);
		
		Position p1 = new Position(PositionConfiguration.UNIT_1_X * GameConfiguration.TILE_SIZE, PositionConfiguration.UNIT_1_Y * GameConfiguration.TILE_SIZE);
		Position p2 = new Position(PositionConfiguration.UNIT_2_X * GameConfiguration.TILE_SIZE, PositionConfiguration.UNIT_2_Y * GameConfiguration.TILE_SIZE);
		Position p3 = new Position(PositionConfiguration.UNIT_3_X * GameConfiguration.TILE_SIZE, PositionConfiguration.UNIT_3_Y * GameConfiguration.TILE_SIZE);
		Position p4 = new Position(PositionConfiguration.UNIT_4_X * GameConfiguration.TILE_SIZE, PositionConfiguration.UNIT_4_Y * GameConfiguration.TILE_SIZE);
		Position p5 = new Position(PositionConfiguration.UNIT_5_X * GameConfiguration.TILE_SIZE, PositionConfiguration.UNIT_5_Y * GameConfiguration.TILE_SIZE);
		
		manager.createBuilding(EntityConfiguration.HQ, EntityConfiguration.PLAYER_FACTION, new Position(tile.getColumn() * GameConfiguration.TILE_SIZE, tile.getLine() * GameConfiguration.TILE_SIZE), tile, null);
		manager.createBuilding(EntityConfiguration.STORAGE, EntityConfiguration.PLAYER_FACTION, new Position(tile2.getColumn() * GameConfiguration.TILE_SIZE, tile2.getLine() * GameConfiguration.TILE_SIZE), tile2, null);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.PLAYER_FACTION, patron, p1, null);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.PLAYER_FACTION, patron, p2, null);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.PLAYER_FACTION, patron, p3, null);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.PLAYER_FACTION, patron, p4, null);
		manager.createFighter(EntityConfiguration.CAVALRY, EntityConfiguration.PLAYER_FACTION, patronFighter, p5, null);

		//cr�ation d'un ennemie
		tile = map.getTile(PositionConfiguration.TILE_3_X, PositionConfiguration.TILE_3_Y);
		tile2 = map.getTile(PositionConfiguration.TILE_4_X, PositionConfiguration.TILE_4_Y);
		patron = manager.getFactionManager().getFactions().get(EntityConfiguration.BOT_FACTION).getRace().getPatronWorkers().get(EntityConfiguration.WORKER);
		patronFighter = manager.getFactionManager().getFactions().get(EntityConfiguration.BOT_FACTION).getRace().getPatronFighters().get(EntityConfiguration.CAVALRY);
		
		p1 = new Position(PositionConfiguration.UNIT_6_X * GameConfiguration.TILE_SIZE, PositionConfiguration.UNIT_6_Y * GameConfiguration.TILE_SIZE);
		p2 = new Position(PositionConfiguration.UNIT_7_X * GameConfiguration.TILE_SIZE, PositionConfiguration.UNIT_7_Y * GameConfiguration.TILE_SIZE);
		p3 = new Position(PositionConfiguration.UNIT_8_X * GameConfiguration.TILE_SIZE, PositionConfiguration.UNIT_8_Y * GameConfiguration.TILE_SIZE);
		p4 = new Position(PositionConfiguration.UNIT_9_X * GameConfiguration.TILE_SIZE, PositionConfiguration.UNIT_9_Y * GameConfiguration.TILE_SIZE);
		p5 = new Position(PositionConfiguration.UNIT_10_X * GameConfiguration.TILE_SIZE, PositionConfiguration.UNIT_10_Y * GameConfiguration.TILE_SIZE);

		manager.createBuilding(EntityConfiguration.HQ, EntityConfiguration.BOT_FACTION, new Position(tile.getColumn() * GameConfiguration.TILE_SIZE, tile.getLine() * GameConfiguration.TILE_SIZE), tile, null);
		manager.createBuilding(EntityConfiguration.STORAGE, EntityConfiguration.BOT_FACTION, new Position(tile2.getColumn() * GameConfiguration.TILE_SIZE, tile2.getLine() * GameConfiguration.TILE_SIZE), tile2, null);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.BOT_FACTION, patron, p1, null);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.BOT_FACTION, patron, p2, null);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.BOT_FACTION, patron, p3, null);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.BOT_FACTION, patron, p4, null);
		manager.createFighter(EntityConfiguration.CAVALRY, EntityConfiguration.BOT_FACTION, patronFighter, p5, null);
	}
	
}