package engine.manager;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Faction;
import engine.Position;
import engine.map.Map;
import engine.map.Tile;
import factionConfiguration.ForWorker;

/**
 * 
 * @author maxime
 *
 */

public class GameBuilder {

	public static Map buildMap(int selectedMap, GraphicsManager graphicsManager, EntitiesManager manager) {
		
		Map map;
		map = new Map(GameConfiguration.LINE_COUNT, GameConfiguration.COLUMN_COUNT, selectedMap, "src/file/map" + selectedMap + ".txt", graphicsManager);
		manager.addRessource(map.getGoldTiles());
		
		return map;
	}
	
	public static void buildFaction(EntitiesManager manager, int idfaction1, int idfaction2, Map map) {
		
		Faction faction1 = new Faction(idfaction1);
		Faction faction2 = new Faction(idfaction1);
		Faction gaia = new Faction(4);
		
		manager.getFactionManager().addFaction(EntityConfiguration.PLAYER_FACTION, faction1);
		manager.getFactionManager().addFaction(EntityConfiguration.BOT_FACTION, faction2);
		manager.getFactionManager().addFaction(EntityConfiguration.GAIA_FACTION, gaia);
		manager.createBotManager(manager.getGraphicsManager(), map);
		
		//création du joueur
		Tile tile2 = map.getTile(5, 5);
		Tile tile = map.getTile(9, 5);
		ForWorker patron = manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getRace().getPatronWorkers().get(EntityConfiguration.WORKER);
		
		Position p1 = new Position(8 * GameConfiguration.TILE_SIZE, 4 * GameConfiguration.TILE_SIZE);
		Position p2 = new Position(8 * GameConfiguration.TILE_SIZE, 6 * GameConfiguration.TILE_SIZE);
		Position p3 = new Position(10 * GameConfiguration.TILE_SIZE, 4 * GameConfiguration.TILE_SIZE);
		Position p4 = new Position(10 * GameConfiguration.TILE_SIZE, 6 * GameConfiguration.TILE_SIZE);
		
		manager.createBuilding(EntityConfiguration.HQ, EntityConfiguration.PLAYER_FACTION, new Position(tile.getColumn() * GameConfiguration.TILE_SIZE, tile.getLine() * GameConfiguration.TILE_SIZE), tile);
		manager.createBuilding(EntityConfiguration.STORAGE, EntityConfiguration.PLAYER_FACTION, new Position(tile2.getColumn() * GameConfiguration.TILE_SIZE, tile2.getLine() * GameConfiguration.TILE_SIZE), tile2);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.PLAYER_FACTION, patron, p1, null);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.PLAYER_FACTION, patron, p2, null);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.PLAYER_FACTION, patron, p3, null);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.PLAYER_FACTION, patron, p4, null);
		
		//création d'un ennemie
		tile = map.getTile(84, 84);
		tile2 = map.getTile(82, 88);
		patron = manager.getFactionManager().getFactions().get(EntityConfiguration.BOT_FACTION).getRace().getPatronWorkers().get(EntityConfiguration.WORKER);
	
		p1 = new Position(83 * GameConfiguration.TILE_SIZE, 83 * GameConfiguration.TILE_SIZE);
		p2 = new Position(83 * GameConfiguration.TILE_SIZE, 85 * GameConfiguration.TILE_SIZE);
		p3 = new Position(85 * GameConfiguration.TILE_SIZE, 83 * GameConfiguration.TILE_SIZE);
		p4 = new Position(85 * GameConfiguration.TILE_SIZE, 85 * GameConfiguration.TILE_SIZE);

		manager.createBuilding(EntityConfiguration.HQ, EntityConfiguration.BOT_FACTION, new Position(tile.getColumn() * GameConfiguration.TILE_SIZE, tile.getLine() * GameConfiguration.TILE_SIZE), tile);
		manager.createBuilding(EntityConfiguration.STORAGE, EntityConfiguration.BOT_FACTION, new Position(tile2.getColumn() * GameConfiguration.TILE_SIZE, tile2.getLine() * GameConfiguration.TILE_SIZE), tile2);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.BOT_FACTION, patron, p1, null);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.BOT_FACTION, patron, p2, null);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.BOT_FACTION, patron, p3, null);
		manager.createWorker(EntityConfiguration.WORKER, EntityConfiguration.BOT_FACTION, patron, p4, null);
	}
	
}
