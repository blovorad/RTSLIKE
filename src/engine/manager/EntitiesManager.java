package engine.manager;

import java.util.ArrayList;
import java.util.List;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Camera;
import engine.Entity;
import engine.Position;
import engine.Ressource;
import engine.entity.building.Archery;
import engine.entity.building.AttackBuilding;
import engine.entity.building.Barrack;
import engine.entity.building.Castle;
import engine.entity.building.Forge;
import engine.entity.building.Hq;
import engine.entity.building.PopulationBuilding;
import engine.entity.building.ProductionBuilding;
import engine.entity.building.SiteConstruction;
import engine.entity.building.Stable;
import engine.entity.building.StorageBuilding;
import engine.entity.building.Tower;
import engine.entity.unit.Fighter;
import engine.entity.unit.Unit;
import engine.entity.unit.Worker;
import engine.map.Fog;
import engine.map.Map;
import engine.map.Tile;
import engine.math.Collision;
import factionConfiguration.ForAttackBuilding;
import factionConfiguration.ForFighter;
import factionConfiguration.ForPopulationBuilding;
import factionConfiguration.ForProductionBuilding;
import factionConfiguration.ForStorageBuilding;
import factionConfiguration.ForUpgrade;
import factionConfiguration.ForWorker;
import gui.GameDisplay;


/**
 * 
 * core class of the game, it manage all entity of the game, calling all update function of the game
 * manage if you win or loose, and call the botManager
 * @author gautier
 */

public class EntitiesManager 
{
	/**
	 * contain all faction
	 */
	private FactionManager factionManager;
	/**
	 * contain all graphics
	 */
	private GraphicsManager graphicsManager;
	/**
	 * contain all sound and manage them
	 */
	private AudioManager audioManager;
	/**
	 * manager of the IA
	 */
	private BotManager botManager;
	/**
	 * camera need to have his position
	 */
	private Camera camera;
	/**
	 * fog of war of the player
	 */
	private Fog playerFog;
	/**
	 * map of the game
	 */
	private Map map;
	
	private GameDisplay dashboard;
	
	/**
	 * represents all list that is needing in the game
	 */
	private List<Entity> drawingList = new ArrayList<Entity>();
	private List<Entity> playerEntities = new ArrayList<Entity>();
	
	private List<Unit> selectedUnits = new ArrayList<Unit>();
	private List<Fighter>selectedFighters = new ArrayList<Fighter>();
	private List<Worker>selectedWorkers = new ArrayList<Worker>();

	private List<Worker> playerWorkers = new ArrayList<Worker>();
	private List<Fighter> playerFighters = new ArrayList<Fighter>();
	
	private ProductionBuilding selectedProdBuilding;
	private AttackBuilding selectedAttackBuilding;
	private StorageBuilding selectedStorageBuilding;
	private Ressource selectedRessource;
	private SiteConstruction selectedSiteConstruction;
	private PopulationBuilding selectedPopulationBuilding;
	
	private List<Fighter> fighters;
	private List<Fighter> removeFighters = new ArrayList<Fighter>();
	
	private List<Worker> workers;
	private List<Worker> removeWorkers = new ArrayList<Worker>();
	
	private List<Unit> units;
	private List<Unit> playerUnits = new ArrayList<Unit>();
	private List<Entity> playerBuildings = new ArrayList<Entity>();
	
	private List<AttackBuilding> attackBuildings;
	private List<AttackBuilding> removeAttackBuildings = new ArrayList<AttackBuilding>();
	
	private List<PopulationBuilding> populationBuildings;
	private List<PopulationBuilding> removePopulationBuildings = new ArrayList<PopulationBuilding>();

	private List<ProductionBuilding> prodBuildings;
	private List<ProductionBuilding> removeProdBuildings = new ArrayList<ProductionBuilding>();
	
	private List<StorageBuilding> storageBuildings;
	
	private List<StorageBuilding> playerStorageBuildings;
	private List<ProductionBuilding> playerProdBuildings;
	private List<AttackBuilding> playerAttackBuildings;
	private List<PopulationBuilding> playerPopulationBuildings;
	
	private List<StorageBuilding> botStorageBuildings;
	private List<ProductionBuilding> botProdBuildings;
	private List<AttackBuilding> botAttackBuildings;
	private List<Fighter> botFighters;
	private List<Worker> botWorkers;
	private List<SiteConstruction> botSiteConstructions;
	private List<Entity> botEntities;
	private List<PopulationBuilding> botPopulationBuildings;
	
	private List<StorageBuilding> removeStorageBuildings = new ArrayList<StorageBuilding>();
	
	private List<Ressource> ressources;
	private List<Ressource> removeRessources = new ArrayList<Ressource>();
	
	private List<SiteConstruction> siteConstructions;
	private List<SiteConstruction> removeSiteConstructions = new ArrayList<SiteConstruction>();
	
	private List<Entity> waitingToAdd = new ArrayList<Entity>();
	private List<Entity> removeList = new ArrayList<Entity>();
	
	private boolean playerWin = false;
	private boolean botWin = false;
	
	public EntitiesManager(AudioManager audioManager, Camera camera) 
	{
		this.audioManager = audioManager;
		this.camera = camera;
		this.graphicsManager = new GraphicsManager();
		this.factionManager = new FactionManager();
		this.fighters = new ArrayList<Fighter>();
		this.workers = new ArrayList<Worker>();
		this.units = new ArrayList<Unit>();
		this.ressources = new ArrayList<Ressource>();
		this.setSelectedUnits(new ArrayList<Unit>());
		this.prodBuildings = new ArrayList<ProductionBuilding>();
		this.attackBuildings = new ArrayList<AttackBuilding>();
		this.storageBuildings = new ArrayList<StorageBuilding>();
		this.siteConstructions = new ArrayList<SiteConstruction>();
		this.populationBuildings = new ArrayList<PopulationBuilding>();
		
		this.playerStorageBuildings = new ArrayList<StorageBuilding>();
		this.playerAttackBuildings = new ArrayList<AttackBuilding>();
		this.playerProdBuildings = new ArrayList<ProductionBuilding>();
		this.playerPopulationBuildings = new ArrayList<PopulationBuilding>();
		
		this.botStorageBuildings = new ArrayList<StorageBuilding>();
		this.botProdBuildings = new ArrayList<ProductionBuilding>();
		this.botAttackBuildings = new ArrayList<AttackBuilding>();
		this.botSiteConstructions = new ArrayList<SiteConstruction>();
		this.botFighters = new ArrayList<Fighter>();
		this.botWorkers = new ArrayList<Worker>();
		this.botEntities = new ArrayList<Entity>();
		this.botPopulationBuildings = new ArrayList<PopulationBuilding>();
		
		this.selectedAttackBuilding = null;
		this.selectedProdBuilding = null;
		this.selectedStorageBuilding = null;
		this.selectedSiteConstruction = null;
		this.selectedPopulationBuilding = null;
	}
	
	public void createBotManager(GraphicsManager graphicsManager, Map map) {
		this.botManager = new BotManager(factionManager, map, factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).getRealMaxPop());
	}
	
	public void setPlayerFog(Fog fog) {
		this.playerFog = fog;
	}
	
	public void setDashboard(GameDisplay dashboard) {
		this.dashboard = dashboard;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	/**
	 * this core method, adding all entity that need to be print on screen,
	 * then update the botManager
	 * after that it will update all entity of the game and remove entity when hp <= 0
	 */
	public void update() 
	{
		if(waitingToAdd.isEmpty() == false) {
			for(Entity entity : waitingToAdd) {
				if(drawingList.contains(entity) == false) {
					drawingList.add(entity);
				}
			}
			waitingToAdd.clear();
		}
		if(removeList.isEmpty() == false) {
			drawingList.removeAll(removeList);
			removeList.clear();
		}
		
		if(!GameConfiguration.IA_NOT_ALIVE && botManager != null) {
			botManager.update(botEntities, botStorageBuildings, botAttackBuildings, botProdBuildings, botWorkers, botFighters, ressources, botSiteConstructions, playerBuildings, playerUnits, botPopulationBuildings, factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).getPopulationCount(), factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).getMaxPopulation());
			if(botManager.getBuildingInAttempt() == true) {
				Tile tile = botManager.getTileToBuild();
				Position p = new Position(tile.getColumn() * GameConfiguration.TILE_SIZE, tile.getLine() * GameConfiguration.TILE_SIZE);
				createConstructionSite(botManager.getIdToBuild(), EntityConfiguration.BOT_FACTION, p, botManager.getTileToBuild());
				botManager.buildBuilding();
			}
		}
		
		for(Fighter fighter : fighters) {
			if(fighter.getFaction() == EntityConfiguration.PLAYER_FACTION) {
				fighter.update(units, playerFog, map);
			}
			else {
				fighter.update(units, null, map);
			}
			if(fighter.getCurrentAction() == EntityConfiguration.ATTACK) {
				if(Collision.collideForFx(fighter, camera)) {
					audioManager.startFx(6);
				}
			}
			if(fighter.isRemove()){
				removeFighters.add(fighter);
				factionManager.getFactions().get(fighter.getFaction()).setPopulationCount(factionManager.getFactions().get(fighter.getFaction()).getPopulationCount() - 1);
			}
		}
		
		for(Worker worker : workers) {
			if(worker.getFaction() == EntityConfiguration.PLAYER_FACTION) {
				worker.update(ressources, playerStorageBuildings, units, playerFog, map);
			}
			else {
				worker.update(ressources, botStorageBuildings, units, null, map);
			}
			
			if(worker.getCurrentAction() == EntityConfiguration.ATTACK) {
				if(Collision.collideForFx(worker, camera)) {
					audioManager.startFx(6);
				}
			}
			else if(worker.getCurrentAction() == EntityConfiguration.HARVEST && worker.getRessource() != null && Collision.collideUnit(worker.getRessource().getPosition(), worker)) {
				if(Collision.collideForFx(worker, camera) && worker.getRessource() != null) {
					audioManager.startFx(8);
				}
			}
			else if((worker.getCurrentAction() == EntityConfiguration.REPAIR || worker.getCurrentAction() == EntityConfiguration.CONSTRUCT) && worker.getSpeed().hasSpeed() == false) {
				if(Collision.collideForFx(worker, camera)) {
					audioManager.startFx(11);
				}
			}
			if(worker.isRemove()) {
				removeWorkers.add(worker);
				factionManager.getFactions().get(worker.getFaction()).setPopulationCount(factionManager.getFactions().get(worker.getFaction()).getPopulationCount() - 1);
			}
		}
		
		for(PopulationBuilding building : populationBuildings) {
			building.update();
			if(building.isRemove()) {
				this.factionManager.getFactions().get(building.getFaction()).reduceMaxPopulation(building.getPopulation());
				removePopulationBuildings.add(building);
			}
		}
		
		for(ProductionBuilding building : prodBuildings) {
			building.update(factionManager.getFactions().get(building.getFaction()).getPopulationCount(), factionManager.getFactions().get(building.getFaction()).getMaxPopulation());
			if(building.getIsProducing()) {
				if(building.getTimer() <= 0) {
					int id = building.produce();
					if(id >= EntityConfiguration.INFANTRY && id <= EntityConfiguration.SPECIAL_UNIT) {
						ForFighter patron = factionManager.getFactions().get(building.getFaction()).getRace().getPatronFighters().get(id);
						createFighter(id, building.getFaction(), patron, new Position(building.getPosition().getX() - 50, building.getPosition().getY() - 50), building.getRallyPoint());
					}
					else if(id == EntityConfiguration.WORKER) {
						ForWorker patron = factionManager.getFactions().get(building.getFaction()).getRace().getPatronWorkers().get(id);
						createWorker(id, building.getFaction(), patron, new Position(building.getPosition().getX() - 50, building.getPosition().getY()- 50), building.getRallyPoint());
					}
					else if(id >= EntityConfiguration.ARMOR_UPGRADE && id <= EntityConfiguration.AGE_UPGRADE_2) {
						createUpgrade(id, building.getFaction());
					}
					//System.out.println("producing unit");
				}
			}
			if(building.isRemove()) {
				this.factionManager.getFactions().get(building.getFaction()).checkUpgrade(building.getElementCount());
				building.remove();
				removeProdBuildings.add(building);
			}
		}
		
		for(AttackBuilding building : attackBuildings) {
			building.update(units);
			if(building.isRemove()) {
				removeAttackBuildings.add(building);
			}
		}
		
		for(StorageBuilding building : storageBuildings) {
			building.update();
			if(building.getRessourceStock() > 0) {
				int money = factionManager.getFactions().get(building.getFaction()).getMoneyCount();
				factionManager.getFactions().get(building.getFaction()).setMoneyCount(money + building.takeRessourceStock());
			}
			if(building.isRemove()) {
				removeStorageBuildings.add(building);
			}
		}
		
		for(Ressource ressource : ressources) {
			ressource.update();
			if(ressource.isRemove()) {
				if(ressource.isSelected()) {
					this.clearSelectedRessource();
				}
				ressource.remove();
				removeRessources.add(ressource);
			}
		}
		
		for(SiteConstruction siteConstruction : siteConstructions) {
			siteConstruction.update();
			if(siteConstruction.isRemove()) {
				if(siteConstruction.getHp() > 0) {
					createBuilding(siteConstruction.getBuildingId(), siteConstruction.getFaction(), siteConstruction.getPosition(), siteConstruction.getTile(), siteConstruction);
				}
				removeSiteConstructions.add(siteConstruction);
			}
		}
		
		if(botAttackBuildings.isEmpty() && botProdBuildings.isEmpty() && botStorageBuildings.isEmpty()) {
			playerWin = true;
		}
		if(playerAttackBuildings.isEmpty() && playerProdBuildings.isEmpty() && playerStorageBuildings.isEmpty()) {
			botWin = true;
		}
		
		cleanList();	
	}
	
	public void cleanList() {
		
		//removing ressource
		if(removeRessources.isEmpty() == false) {
			if(removeRessources.contains(selectedRessource)) {
				clearSelectedRessource();
			}
			ressources.removeAll(removeRessources);
			drawingList.removeAll(removeRessources);
			removeRessources.clear();
		}
		
		//removing storage building
		if(removeStorageBuildings.isEmpty() == false) {
			if(removeStorageBuildings.contains(selectedStorageBuilding)) {
				clearSelectedStorageBuilding();
			}
			playerBuildings.removeAll(removeStorageBuildings);
			botEntities.removeAll(removeStorageBuildings);
			botStorageBuildings.removeAll(removeStorageBuildings);
			storageBuildings.removeAll(removeStorageBuildings);
			drawingList.removeAll(removeStorageBuildings);
			playerStorageBuildings.removeAll(removeStorageBuildings);
			botStorageBuildings.removeAll(removeStorageBuildings);
			playerEntities.removeAll(removeStorageBuildings);
			removeStorageBuildings.clear();
		}
		
		//removing attack building
		if(removeAttackBuildings.isEmpty() == false) {
			if(removeAttackBuildings.contains(selectedAttackBuilding)) {
				clearSelectedAttackBuilding();
			}
			playerBuildings.removeAll(removeAttackBuildings);
			playerAttackBuildings.removeAll(removeAttackBuildings);
			botEntities.removeAll(removeAttackBuildings);
			botAttackBuildings.removeAll(removeAttackBuildings);
			attackBuildings.removeAll(removeAttackBuildings);
			drawingList.removeAll(removeAttackBuildings);
			playerEntities.removeAll(removeAttackBuildings);
			removeAttackBuildings.clear();
		}
		
		//removing population building
		if(removePopulationBuildings.isEmpty() == false) {
			if(removePopulationBuildings.contains(selectedPopulationBuilding)) {
				clearSelectedPopulationBuilding();
			}
			playerBuildings.removeAll(removePopulationBuildings);
			playerPopulationBuildings.removeAll(removePopulationBuildings);
			botEntities.removeAll(removePopulationBuildings);
			botPopulationBuildings.removeAll(removePopulationBuildings);
			populationBuildings.removeAll(removePopulationBuildings);
			drawingList.removeAll(removePopulationBuildings);
			playerEntities.removeAll(removePopulationBuildings);
			removePopulationBuildings.clear();
		}
		
		//removing production building
		if(removeProdBuildings.isEmpty() == false) {
			if(removeProdBuildings.contains(selectedProdBuilding)) {
				clearSelectedProdBuilding();
			}
			playerBuildings.removeAll(removeProdBuildings);
			playerProdBuildings.removeAll(removeProdBuildings);
			botEntities.removeAll(removeProdBuildings);
			botProdBuildings.removeAll(removeProdBuildings);
			prodBuildings.removeAll(removeProdBuildings);
			drawingList.removeAll(removeProdBuildings);
			playerEntities.removeAll(removeProdBuildings);
			removeProdBuildings.clear();
		}
		
		//removing worker
		if(removeWorkers.isEmpty() == false) {
			playerUnits.removeAll(removeWorkers);
            selectedWorkers.removeAll(removeWorkers);
            botEntities.removeAll(removeWorkers);
            botWorkers.removeAll(removeWorkers);
            workers.removeAll(removeWorkers);
            drawingList.removeAll(removeWorkers);
            units.removeAll(removeWorkers);
            selectedUnits.removeAll(removeWorkers);
            playerEntities.removeAll(removeWorkers);
            selectedWorkers.removeAll(removeWorkers);
            playerWorkers.removeAll(removeWorkers);
            removeWorkers.clear();
        }
        
        //removing fighter
        if(removeFighters.isEmpty() == false) {
        	playerUnits.removeAll(removeFighters);
            selectedFighters.removeAll(removeFighters);
            botEntities.removeAll(removeFighters);
            botFighters.removeAll(removeFighters);
            fighters.removeAll(removeFighters);
            drawingList.removeAll(removeFighters);
            units.removeAll(removeFighters);
            selectedUnits.removeAll(removeFighters);
            playerEntities.removeAll(removeFighters);
            selectedFighters.removeAll(removeFighters);
            playerFighters.removeAll(removeFighters);
            removeFighters.clear();
        }
		
		//removing constructionSite
		if(removeSiteConstructions.isEmpty() == false) {
			if(removeSiteConstructions.contains(selectedSiteConstruction)) {
				clearSelectedSiteConstruction();
			}
			playerBuildings.removeAll(removeSiteConstructions);
			botEntities.removeAll(removeSiteConstructions);
			botSiteConstructions.removeAll(removeSiteConstructions);
			siteConstructions.removeAll(removeSiteConstructions);
			drawingList.removeAll(removeSiteConstructions);
			playerEntities.removeAll(removeSiteConstructions);
			removeSiteConstructions.clear();
		}
	}
	
	/**
	 * creating a construction site
	 * @param id of the building who will be construct
	 * @param faction of the site construction
	 * @param position of the site construction
	 * @param tile who are attach of the site construction
	 * @return the current site construction
	 */
	public SiteConstruction createConstructionSite(int id, int faction, Position position, Tile tile) {
		SiteConstruction sc = null;
		
		if(id == EntityConfiguration.FORGE){
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			sc = new SiteConstruction(id, 1, patronBuilding.getHpMax(), patronBuilding.getDescription(), position, EntityConfiguration.SITE_CONSTRUCTION, faction, GameConfiguration.TILE_SIZE, 0, graphicsManager, tile);
		}
		else if(id == EntityConfiguration.STABLE){
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			sc = new SiteConstruction(id, 1, patronBuilding.getHpMax(), patronBuilding.getDescription(), position, EntityConfiguration.SITE_CONSTRUCTION, faction, GameConfiguration.TILE_SIZE, 0, graphicsManager, tile);
		}
		else if(id == EntityConfiguration.BARRACK){
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			sc = new SiteConstruction(id, 1, patronBuilding.getHpMax(), patronBuilding.getDescription(), position, EntityConfiguration.SITE_CONSTRUCTION, faction, GameConfiguration.TILE_SIZE, 0, graphicsManager, tile);
		}
		else if(id == EntityConfiguration.ARCHERY){
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			sc = new SiteConstruction(id, 1, patronBuilding.getHpMax(), patronBuilding.getDescription(), position, EntityConfiguration.SITE_CONSTRUCTION, faction, GameConfiguration.TILE_SIZE, 0, graphicsManager, tile);
		}
		else if(id == EntityConfiguration.HQ){
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			sc = new SiteConstruction(id, 1, patronBuilding.getHpMax(), patronBuilding.getDescription(), position, EntityConfiguration.SITE_CONSTRUCTION, faction, GameConfiguration.TILE_SIZE, 0, graphicsManager, tile);
		}
		else if(id == EntityConfiguration.CASTLE){
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			sc = new SiteConstruction(id, 1, patronBuilding.getHpMax(), patronBuilding.getDescription(), position, EntityConfiguration.SITE_CONSTRUCTION, faction, GameConfiguration.TILE_SIZE, 0, graphicsManager, tile);
		}
		else if(id == EntityConfiguration.STORAGE){
			ForStorageBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getStorageBuildings().get(id);
			//System.out.println("DESCRIPTION : " + patronBuilding.getDescription() + ", et la faction : " + faction);
			sc = new SiteConstruction(id, 1, patronBuilding.getHpMax(), patronBuilding.getDescription(), position, EntityConfiguration.SITE_CONSTRUCTION, faction, GameConfiguration.TILE_SIZE, 0, graphicsManager, tile);
		}
		else if(id == EntityConfiguration.TOWER){
			ForAttackBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getAttackBuildings().get(id);
			sc = new SiteConstruction(id, 1, patronBuilding.getHpMax(), patronBuilding.getDescription(), position, EntityConfiguration.SITE_CONSTRUCTION, faction, GameConfiguration.TILE_SIZE, 0, graphicsManager, tile);
		}
		else if(id == EntityConfiguration.HOUSE) {
			ForPopulationBuilding patronBuilding = factionManager.getFactions().get(faction).getRace().getPopulationBuildings().get(id);
			sc = new SiteConstruction(id, 1, patronBuilding.getHpMax(), patronBuilding.getDescription(), position, EntityConfiguration.SITE_CONSTRUCTION, faction, GameConfiguration.TILE_SIZE, 0, graphicsManager, tile);
		}
		else{
			System.out.println("invalide ID");
		}
		
		if(sc != null) {
			tile.setSolid(true);
			if(faction == EntityConfiguration.PLAYER_FACTION) {
				playerEntities.add(sc);
				playerBuildings.add(sc);
			}
			else {
				botEntities.add(sc);
				botSiteConstructions.add(sc);
				botEntities.add(sc);
			}
			this.waitingToAdd.add(sc);
			this.siteConstructions.add(sc);
			//System.out.println("ajout d'un site de construction");
		}
	return sc;
	}
	
	/**
	 * creating a fighter
	 * @param id of the fighter can be INFANTRY,ARCHER,..
	 * @param faction of the fighter
	 * @param patron of the fighter
	 * @param position of the fighter
	 * @param destination if a rally point is set
	 * @see EntityConfiguration
	 * @see ForFighter
	 */
	public void createFighter(int id, int faction, ForFighter patron, Position position, Position destination)
	{
		Fighter fighter = null;
		
		if(id == EntityConfiguration.INFANTRY) {
			fighter = new Fighter(patron.getHp(), 0, patron.getAttackRange(), patron.getAttackSpeed(), patron.getMaxSpeed(), patron.getDamage(), patron.getArmor(), EntityConfiguration.AGRESSIF_STATE, position, id, patron.getDescription(), patron.getHpMax(), faction, destination, patron.getSightRange(), 4, graphicsManager);
		}
		else if(id == EntityConfiguration.ARCHER) {
			fighter = new Fighter(patron.getHp(), 0, patron.getAttackRange(), patron.getAttackSpeed(), patron.getMaxSpeed(), patron.getDamage(), patron.getArmor(), EntityConfiguration.AGRESSIF_STATE, position, id, patron.getDescription(), patron.getHpMax(), faction, destination, patron.getSightRange(), 4, graphicsManager);
		}
		else if(id == EntityConfiguration.CAVALRY) {
			fighter = new Fighter(patron.getHp(), 0, patron.getAttackRange(), patron.getAttackSpeed(), patron.getMaxSpeed(), patron.getDamage(), patron.getArmor(), EntityConfiguration.AGRESSIF_STATE, position, id, patron.getDescription(), patron.getHpMax(), faction, destination, patron.getSightRange(), 4, graphicsManager);
		}
		else if(id == EntityConfiguration.SPECIAL_UNIT) {
			fighter = new Fighter(patron.getHp(), 0, patron.getAttackRange(), patron.getAttackSpeed(), patron.getMaxSpeed(), patron.getDamage(), patron.getArmor(), EntityConfiguration.AGRESSIF_STATE, position, id, patron.getDescription(), patron.getHpMax(), faction, destination, patron.getSightRange(), 4, graphicsManager);
		}
		else {
			System.out.println("invalid id production fighter : " + id);
		}
		
		if(fighter == null) {
			System.out.println("error adding worker in tab cause fighter is null");
		}
		else {
			if(faction == EntityConfiguration.PLAYER_FACTION) {
				playerEntities.add(fighter);
				playerFighters.add(fighter);
				playerUnits.add(fighter);
				audioManager.startFx(7);
			}
			else {
				botEntities.add(fighter);
				botFighters.add(fighter);
			}
			this.drawingList.add(fighter);
			this.units.add(fighter);
			this.fighters.add(fighter);
			this.factionManager.getFactions().get(faction).setPopulationCount(this.factionManager.getFactions().get(faction).getPopulationCount() + 1);
		}
	}
	
	/**
	 * creating a worker
	 * @param id of the worker can be WORKER
	 * @param faction of the worker
	 * @param patron of the worker
	 * @param position of the worker
	 * @param destination if a rally point is set
	 * @see EntityConfiguration
	 * @see ForWorker
	 */
	public void createWorker(int id, int faction, ForWorker patron, Position position, Position destination)
	{
		Worker worker = null;
		
		if(id == EntityConfiguration.WORKER) {
			worker = new Worker(patron.getHp(), EntityConfiguration.IDDLE, patron.getAttackRange(), patron.getAttackSpeed(), patron.getMaxSpeed(), patron.getDamage(), patron.getHarvest(), patron.getRepair(), patron.getArmor(), patron.getRepairSpeed(), position, id, patron.getDescription(), patron.getHpMax(), faction, destination, patron.getHarvestSpeed(), patron.getRessourceMax(), patron.getSightRange(), 4, graphicsManager);
		}
		else {
			System.out.print("invalid id production worker : " + id);
		}
		
		if(worker == null) {
			System.out.println("error adding worker in tab cause worker is null");
		}
		else {
			if(faction == EntityConfiguration.PLAYER_FACTION) {
				playerEntities.add(worker);
				playerUnits.add(worker);
				playerWorkers.add(worker);
				audioManager.startFx(7);
			}
			else {
				botEntities.add(worker);
				botWorkers.add(worker);
				botEntities.add(worker);
			}
			this.drawingList.add(worker);
			this.units.add(worker);
			this.workers.add(worker);
			this.factionManager.getFactions().get(faction).setPopulationCount(this.factionManager.getFactions().get(faction).getPopulationCount() + 1);
		}
	}
	
	/**
	 * create an upgrade
	 * @param id of the upgrade
	 * @param faction id of the faction
	 * @see ForUpgrade
	 */
	public void createUpgrade(int id, int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			audioManager.startFx(2);
		}
		if(id == EntityConfiguration.AGE_UPGRADE || id == EntityConfiguration.AGE_UPGRADE_2) {
			//System.out.println("upgrade age !");
			if(faction == EntityConfiguration.PLAYER_FACTION) {
				factionManager.getFactions().get(faction).setUpgradeAge(true);
			}
			factionManager.getFactions().get(faction).setAge(factionManager.getFactions().get(faction).getAge() + 1);
		}
		else {
			ForUpgrade upgrade = factionManager.getFactions().get(faction).getRace().getForgeUpgrades().get(id);
			if(faction == EntityConfiguration.PLAYER_FACTION) {
				factionManager.getFactions().get(faction).setStatUpgrade(true);
			}
			if(id== EntityConfiguration.ARMOR_UPGRADE) {
				System.out.println("upgrade armure !");
				for(Fighter fighter : fighters) {
					if(fighter.getFaction() == faction) {
						fighter.setArmor(fighter.getArmor() + upgrade.getEffect());
					}
				}
				for(Worker worker : workers) {
					if(worker.getFaction() == faction) {
						worker.setArmor(worker.getArmor() + upgrade.getEffect());
					}
				}
				for(Unit unit : units) {
					if(unit.getFaction() == faction) {
						unit.setArmor(unit.getArmor() + upgrade.getEffect());
					}
				}
				for(Unit unit : selectedUnits) {
					if(unit.getFaction() == faction) {
						unit.setArmor(unit.getArmor() + upgrade.getEffect());
					}
				}
			}
			else if(id== EntityConfiguration.DAMAGE_UPGRADE){
				System.out.println("upgrade damage !");
				for(Fighter fighter : fighters) {
					if(fighter.getFaction() == faction) {
						fighter.setDamage(fighter.getDamage() + upgrade.getEffect());
					}
				}
				for(Worker worker : workers) {
					if(worker.getFaction() == faction) {
						worker.setDamage(worker.getDamage() + upgrade.getEffect());
					}
				}
				for(Unit unit : units) {
					if(unit.getFaction() == faction) {
						unit.setDamage(unit.getDamage() + upgrade.getEffect());
					}
				}
				for(Unit unit : selectedUnits) {
					if(unit.getFaction() == faction) {
						unit.setDamage(unit.getDamage() + upgrade.getEffect());
					}
				}
			}
			else if(id == EntityConfiguration.ATTACK_RANGE_UPGRADE) {
				System.out.println("upgrade attack range !");
				for(Fighter fighter : fighters) {
					if(fighter.getFaction() == faction) {
						fighter.setAttackRange(fighter.getAttackRange() + upgrade.getEffect());
					}
				}
				for(Worker worker : workers) {
					if(worker.getFaction() == faction) {
						worker.setAttackRange(worker.getAttackRange() + upgrade.getEffect());
					}
				}
				for(Unit unit : units) {
					if(unit.getFaction() == faction) {
						unit.setAttackRange(unit.getAttackRange() + upgrade.getEffect());
					}
				}
				for(Unit unit : selectedUnits) {
					if(unit.getFaction() == faction) {
						unit.setAttackRange(unit.getAttackRange() + upgrade.getEffect());
					}
				}
			}
			else if(id == EntityConfiguration.ATTACK_SPEED_UPGRADE) {
				System.out.println("upgrade attack speed !");
				for(Fighter fighter : fighters) {
					if(fighter.getFaction() == faction) {
						fighter.setAttackSpeed(fighter.getAttackSpeed() + upgrade.getEffect());
					}
				}
				for(Worker worker : workers) {
					if(worker.getFaction() == faction) {
						worker.setAttackSpeed(worker.getAttackSpeed() + upgrade.getEffect());
					}
				}
				for(Unit unit : units) {
					if(unit.getFaction() == faction) {
						unit.setAttackSpeed(unit.getAttackSpeed() + upgrade.getEffect());
					}
				}
				for(Unit unit : selectedUnits) {
					if(unit.getFaction() == faction) {
						unit.setAttackSpeed(unit.getAttackSpeed() + upgrade.getEffect());
					}
				}
			}
			else if(id == EntityConfiguration.SIGHT_RANGE_UPGRADE) {
				System.out.println("upgrade sight range !");
				for(Fighter fighter : fighters) {
					if(fighter.getFaction() == faction) {
						fighter.setSightRange(fighter.getSightRange() + upgrade.getEffect());
					}
				}
				for(Worker worker : workers) {
					if(worker.getFaction() == faction) {
						worker.setSightRange(worker.getSightRange() + upgrade.getEffect());
					}
				}
				for(Unit unit : units) {
					if(unit.getFaction() == faction) {
						unit.setSightRange(unit.getSightRange() + upgrade.getEffect());
					}
				}
				for(Unit unit : selectedUnits) {
					if(unit.getFaction() == faction) {
						unit.setSightRange(unit.getSightRange() + upgrade.getEffect());
					}
				}
			}
		}
	}
	
	/**
	 * creating a building
	 * @param id of the building can be BARRACK,ARCHERY....
	 * @param faction of the building
	 * @param position of the building
	 * @param tile who are attach to the building
	 * @see EntityConfiguration
	 * @see Patron
	 */
	public void createBuilding(int id, int faction, Position position, Tile tile, SiteConstruction sc) {
		ProductionBuilding bprod = null;
		AttackBuilding battack = null;
		StorageBuilding bstorage = null;
		PopulationBuilding bpopbuilding = null;
			
		if(id == EntityConfiguration.FORGE){
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			bprod = new Forge(position, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, factionManager.getFactions().get(faction).getRace().getForgeUpgrades(), patronBuilding.getSightRange(), graphicsManager);
		}
		else if(id == EntityConfiguration.STABLE){
			ForFighter patronFighter = factionManager.getFactions().get(faction).getRace().getPatronFighters().get(EntityConfiguration.CAVALRY);
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			bprod = new Stable(position, patronFighter, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, patronBuilding.getSightRange(), graphicsManager);
		}
		else if(id == EntityConfiguration.BARRACK){
			ForFighter patronFighter = factionManager.getFactions().get(faction).getRace().getPatronFighters().get(EntityConfiguration.INFANTRY);
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			bprod = new Barrack(position, patronFighter, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, patronBuilding.getSightRange(), graphicsManager);
		}
		else if(id == EntityConfiguration.ARCHERY){
			ForFighter patronFighter = factionManager.getFactions().get(faction).getRace().getPatronFighters().get(EntityConfiguration.ARCHER);
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			bprod = new Archery(position, patronFighter, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, patronBuilding.getSightRange(), graphicsManager);
		}
		else if(id == EntityConfiguration.HQ){
			ForWorker patronWorker = factionManager.getFactions().get(faction).getRace().getPatronWorkers().get(EntityConfiguration.WORKER);
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			bprod = new Hq(position, patronWorker, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, factionManager.getFactions().get(faction).getRace().getHQUpgrades(), patronBuilding.getSightRange(), graphicsManager);
		}
		else if(id == EntityConfiguration.CASTLE){
			ForFighter patronFighter = factionManager.getFactions().get(faction).getRace().getPatronFighters().get(EntityConfiguration.SPECIAL_UNIT);
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			bprod = new Castle(position, patronFighter, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, patronBuilding.getSightRange(), graphicsManager);
		}
		else if(id == EntityConfiguration.STORAGE){
			ForStorageBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getStorageBuildings().get(id);
			bstorage = new StorageBuilding(position, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, patronBuilding.getSightRange(), graphicsManager);
		}
		else if(id == EntityConfiguration.TOWER){
			ForAttackBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getAttackBuildings().get(id);
			battack = new Tower(position, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, patronBuilding.getSightRange(), patronBuilding.getAttackDamage(), patronBuilding.getAttackSpeed(), patronBuilding.getAttackRange(), graphicsManager);
		}
		else if(id == EntityConfiguration.HOUSE) {
			ForPopulationBuilding patronBuilding = factionManager.getFactions().get(faction).getRace().getPopulationBuildings().get(id);
			bpopbuilding = new PopulationBuilding(patronBuilding.getHp(), patronBuilding.getHpMax(), patronBuilding.getDescription(), position, id, faction, patronBuilding.getSightRange(), graphicsManager, patronBuilding.getPopulation());
		}
		else{
			System.out.println("invalide ID");
		}
		
		if(bprod != null) {
			tile.setSolid(true);
			this.drawingList.add(bprod);
			this.prodBuildings.add(bprod);
			if(faction == EntityConfiguration.PLAYER_FACTION) {
				playerEntities.add(bprod);
				playerProdBuildings.add(bprod);
				playerBuildings.add(bprod);
			}
			else {
				botEntities.add(bprod);
				botProdBuildings.add(bprod);
			}
			if(sc != null && this.getSelectedSiteConstruction() == sc) {
				this.clearSelectedSiteConstruction();
				this.setSelectedProdBuilding(bprod);
				this.dashboard.setDescriptionPanelForBuilding(bprod, this.getFactionManager().getFactions().get(bprod.getFaction()).getSearchingUpgrades());
			}
			this.factionManager.getFactions().get(faction).setBuildingCount(this.factionManager.getFactions().get(faction).getBuildingCount() + 1);
			//System.out.println("ajout Building production");
		}
		else if(bstorage != null){
			tile.setSolid(true);
			this.drawingList.add(bstorage);
			this.storageBuildings.add(bstorage);
			if(faction == EntityConfiguration.PLAYER_FACTION) {
				playerEntities.add(bstorage);
				playerBuildings.add(bstorage);
				this.playerStorageBuildings.add(bstorage);
			}
			else if(faction == EntityConfiguration.BOT_FACTION) {
				this.botStorageBuildings.add(bstorage);
				botEntities.add(bstorage);
			}
			if(sc != null && this.getSelectedSiteConstruction() == sc) {
				this.clearSelectedSiteConstruction();
				this.setSelectedStorageBuilding(bstorage);
				this.dashboard.setDescriptionPanelForBuilding(bstorage);
			}
			this.factionManager.getFactions().get(faction).setBuildingCount(this.factionManager.getFactions().get(faction).getBuildingCount() + 1);
			//System.out.println("ajout building storage");
		}
		else if(battack != null) {
			tile.setSolid(true);
			this.drawingList.add(battack);
			this.attackBuildings.add(battack);
			if(faction == EntityConfiguration.PLAYER_FACTION) {
				playerEntities.add(battack);
				playerAttackBuildings.add(battack);
				playerBuildings.add(battack);
			}
			else {
				botEntities.add(battack);
				botAttackBuildings.add(battack);
			}
			if(sc != null && this.getSelectedSiteConstruction() == sc) {
				this.clearSelectedSiteConstruction();
				this.setSelectedAttackBuilding(battack);
				this.dashboard.setDescriptionPanelForBuilding(battack);
			}
			this.factionManager.getFactions().get(faction).setBuildingCount(this.factionManager.getFactions().get(faction).getBuildingCount() + 1);
			//System.out.println("ajout building attack");
		}
		else if(bpopbuilding != null) {
			tile.setSolid(true);
			this.drawingList.add(bpopbuilding);
			this.populationBuildings.add(bpopbuilding);
			if(faction == EntityConfiguration.PLAYER_FACTION) {
				playerEntities.add(bpopbuilding);
				playerPopulationBuildings.add(bpopbuilding);
				playerBuildings.add(bpopbuilding);
			}
			else {
				botEntities.add(bpopbuilding);
				botPopulationBuildings.add(bpopbuilding);
			}
			if(sc != null && this.getSelectedSiteConstruction() == sc) {
				this.clearSelectedSiteConstruction();
				this.setSelectedPopulationBuilding(bpopbuilding);
				this.dashboard.setDescriptionPanelForBuilding(bpopbuilding);
			}
			this.factionManager.getFactions().get(faction).addMaxPopulation(bpopbuilding.getPopulation());
			this.factionManager.getFactions().get(faction).setBuildingCount(this.factionManager.getFactions().get(faction).getBuildingCount() + 1);
		}
		
		
	}
	
	/**
	 * select unit
	 * @param unit
	 */
	public void addSelectedUnit(Unit unit){
		unit.setSelected(true);
		this.selectedUnits.add(unit);
	}
	
	/**
	 * select a fighter
	 * @param fighter
	 */
	public void addSelectedFighter(Fighter fighter) {
		this.selectedFighters.add(fighter);
	}
	
	/**
	 * select a worker
	 * @param worker
	 */
	public void addSelectedWorker(Worker worker) {
		this.selectedWorkers.add(worker);
	}
	
	/**
	 * creating all ressource
	 * @param listPositionRessources
	 */
	public void addRessource(List<Tile> listPositionRessources){
		for(Tile t : listPositionRessources){
			Ressource r = new Ressource(200, "ressource en or", new Position(t.getColumn() * GameConfiguration.TILE_SIZE, t.getLine() * GameConfiguration.TILE_SIZE), t, EntityConfiguration.GAIA_FACTION, graphicsManager);
			this.ressources.add(r);
			this.drawingList.add(r);
		}
	}
	
	/**
	 * clear(pass to null) the current selectedRessource
	 */
	public void clearSelectedRessource() {
		if(this.selectedRessource != null) {
			this.selectedRessource.setSelected(false);
		}
		this.selectedRessource = null;
	}
	
	/**
	 * clear all list of selectedBuilding
	 */
	public void clearSelectedBuildings(){
		this.clearSelectedAttackBuilding();
		this.clearSelectedStorageBuilding();
		this.clearSelectedProdBuilding();
		this.clearSelectedSiteConstruction();
	}
	
	/**
	 * cleaning the game when you quit a game
	 */
	public void clean(){
		drawingList.clear();
		playerEntities.clear();
		
		selectedUnits.clear();
		selectedFighters.clear();
		selectedWorkers.clear();

		playerWorkers.clear();
		playerFighters.clear();
		
		clearSelectedRessource();
		
		fighters.clear();
		removeFighters.clear();
		
		workers.clear();
		removeWorkers.clear();
		
		units.clear();
		
		attackBuildings.clear();
		removeAttackBuildings.clear();

		prodBuildings.clear();
		removeProdBuildings.clear();
		
		storageBuildings.clear();
		
		playerStorageBuildings.clear();
		playerProdBuildings.clear();
		playerAttackBuildings.clear();
		playerBuildings.clear();
		playerUnits.clear();
		playerPopulationBuildings.clear();
		
		botStorageBuildings.clear();
		botProdBuildings.clear();
		botAttackBuildings.clear();
		botFighters.clear();
		botWorkers.clear();
		botSiteConstructions.clear();
		botEntities.clear();
		botPopulationBuildings.clear();
		
		removeStorageBuildings.clear();
		
		populationBuildings.clear();
		removePopulationBuildings.clear();
		
		ressources.clear();
		removeRessources.clear();
		
		siteConstructions.clear();
		removeSiteConstructions.clear();
		
		waitingToAdd.clear();
		removeList.clear();
		
		this.botManager = null;
		clearSelectedBuildings();
		
		removeSiteConstructions.clear();
		factionManager.clean();
		
		playerWin = false;
		botWin = false;
	}

	public List<Entity> getDrawingList() {
		return drawingList;
	}

	public void setDrawingList(List<Entity> drawingList) {
		this.drawingList = drawingList;
	}

	public List<Unit> getSelectedUnits() {
		return selectedUnits;
	}

	public void setSelectedUnits(List<Unit> selectedUnits) {
		this.selectedUnits = selectedUnits;
	}

	public List<Fighter> getFighters() {
		return fighters;
	}

	public void setFighters(List<Fighter> fighters) {
		this.fighters = fighters;
	}

	public List<Worker> getWorkers() {
		return workers;
	}

	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
	}

	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}

	public List<Ressource> getRessources() {
		return ressources;
	}

	public void setRessources(List<Ressource> ressources) {
		this.ressources = ressources;
	}
	
	/**
	 * cleaning all selected unit
	 */
	public void clearSelectedUnits()
	{
		selectedUnits.clear();
	}

	public FactionManager getFactionManager() {
		return factionManager;
	}

	public List<ProductionBuilding> getProdBuildings() {
		return prodBuildings;
	}

	public ProductionBuilding getSelectedProdBuilding() {
		return selectedProdBuilding;
	}

	/**
	 * select a production building
	 * @param selectedProdBuilding
	 */
	public void setSelectedProdBuilding(ProductionBuilding selectedProdBuilding) {
		this.selectedProdBuilding = selectedProdBuilding;
		this.selectedProdBuilding.setSelected(true);
	}

	public AttackBuilding getSelectedAttackBuilding() {
		return selectedAttackBuilding;
	}
	
	/**
	 * same as other setSelected[building]
	 * @param selectedAttackBuilding
	 */
	public void setSelectedAttackBuilding(AttackBuilding selectedAttackBuilding) {
		this.selectedAttackBuilding = selectedAttackBuilding;
		this.selectedAttackBuilding.setSelected(true);
	}
	
	public void setSelectedPopulationBuilding(PopulationBuilding populationBuilding) {
		this.selectedPopulationBuilding = populationBuilding;
		this.selectedPopulationBuilding.setSelected(true);
	}

	public StorageBuilding getSelectedStorageBuilding() {
		return selectedStorageBuilding;
	}
	
	/**
	 * same as other setSelected[building]
	 * @param selectedStorageBuilding
	 */
	public void setSelectedStorageBuilding(StorageBuilding selectedStorageBuilding) {
		this.selectedStorageBuilding = selectedStorageBuilding;
		this.selectedStorageBuilding.setSelected(true);
	}
	
	public void setSelectedSiteConstruction(SiteConstruction sc) {
		this.selectedSiteConstruction = sc;
	}

	public List<AttackBuilding> getAttackBuildings() {
		return attackBuildings;
	}
	
	public boolean getPlayerWin() {
		return this.playerWin;
	}
	
	public boolean getBotWin() {
		return this.botWin;
	}

	public void setAttackBuildings(List<AttackBuilding> attackBuildings) {
		this.attackBuildings = attackBuildings;
	}

	public List<StorageBuilding> getStorageBuildings() {
		return storageBuildings;
	}

	public void setStorageBuildings(List<StorageBuilding> storageBuildings) {
		this.storageBuildings = storageBuildings;
	}

	public List<StorageBuilding> getPlayerStorageBuildings() {
		return playerStorageBuildings;
	}

	public void setPlayerStoragesBuildings(List<StorageBuilding> playerStorageBuildings) {
		this.playerStorageBuildings = playerStorageBuildings;
	}
	
	/**
	 * clear all selected unit
	 */
	public void clearSelectedUnit() {
		for(Unit unit : selectedUnits) {
			unit.setSelected(false);
		}
		this.selectedUnits.clear();
	}
	
	public void clearSelectedFighter() {
		this.selectedFighters.clear();
	}
	
	public void clearSelectedWorker() {
		this.selectedWorkers.clear();
	}
	
	public void clearSelectedProdBuilding() {
		if(this.selectedProdBuilding != null) {
			this.selectedProdBuilding.setSelected(false);
		}
		this.selectedProdBuilding = null;
	}
	
	public void clearSelectedPopulationBuilding() {
		if(this.selectedPopulationBuilding != null) {
			this.selectedPopulationBuilding.setSelected(false);
		}
		this.selectedPopulationBuilding = null;
	}
	
	public void clearSelectedAttackBuilding() {
		if(this.selectedAttackBuilding != null) {
			this.selectedAttackBuilding.setSelected(false);
		}
		this.selectedAttackBuilding = null;
	}
	
	public void clearSelectedStorageBuilding() {
		if(this.selectedStorageBuilding != null) {
			this.selectedStorageBuilding.setSelected(false);
		}
		this.selectedStorageBuilding = null;
	}
	
	public void clearSelectedSiteConstruction() {
		if(this.selectedSiteConstruction != null) {
			this.selectedSiteConstruction.setSelected(false);
		}
		this.selectedSiteConstruction = null;
	}

	public List<Entity> getPlayerEntities() {
		return playerEntities;
	}

	public Ressource getSelectedRessource() {
		return selectedRessource;
	}

	public void setSelectedRessource(Ressource selectedRessource) {
		this.selectedRessource = selectedRessource;
	}

	public List<Fighter> getPlayerFighters() {
		return playerFighters;
	}

	public List<Worker> getPlayerWorkers() {
		return playerWorkers;
	}
	
	public List<Fighter> getSelectedFighters() {
		return selectedFighters;
	}

	public List<Worker> getSelectedWorkers() {
		return selectedWorkers;
	}
	
	public List<SiteConstruction> getSiteConstructions(){
		return this.siteConstructions;
	}

	public SiteConstruction getSelectedSiteConstruction() {
		return selectedSiteConstruction;
	}
	
	public PopulationBuilding getSelectedPopulationBuilding() {
		return selectedPopulationBuilding;
	}

	public List<ProductionBuilding> getPlayerProdBuildings() {
		return playerProdBuildings;
	}

	public List<AttackBuilding> getPlayerAttackBuildings() {
		return playerAttackBuildings;
	}

	public GraphicsManager getGraphicsManager() {
		return graphicsManager;
	}
	
	public List<Entity> getWaitingList(){
		return waitingToAdd;
	}
	
	public List<Entity> getRemoveList(){
		return removeList;
	}
	
	public List<Entity> getBotEntities(){
		return this.botEntities;
	}
	
	public List<PopulationBuilding> getPopulationBuildings(){
		return this.populationBuildings;
	}
}