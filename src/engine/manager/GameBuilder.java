package engine.manager;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Faction;
import engine.map.Map;

public class GameBuilder {

	public static Map buildMap(int selectedMap, GraphicsManager graphicsManager, EntitiesManager manager) {
		
		Map map;
		
		map = new Map(GameConfiguration.LINE_COUNT, GameConfiguration.COLUMN_COUNT, selectedMap, "src/file/map" + selectedMap + ".txt", graphicsManager);
		manager.addRessource(map.getGoldTiles());
		
		return map;
	}
	
	public static void buildFaction(EntitiesManager manager, int idfaction1, int idfaction2) {
		
		Faction faction1 = new Faction(idfaction1);
		Faction faction2 = new Faction(idfaction1);
		Faction gaia = new Faction(4);
		
		manager.getFactionManager().addFaction(EntityConfiguration.PLAYER_FACTION, faction1);
		manager.getFactionManager().addFaction(EntityConfiguration.BOT_FACTION, faction2);
		manager.getFactionManager().addFaction(EntityConfiguration.GAIA_FACTION, gaia);
	}
	
}
