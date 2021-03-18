package engine.manager;

import java.util.ArrayList;
import java.util.List;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Entity;
import engine.Position;
import engine.Ressource;
import engine.entity.building.Archery;
import engine.entity.building.AttackBuilding;
import engine.entity.building.Barrack;
import engine.entity.building.Castle;
import engine.entity.building.Forge;
import engine.entity.building.Hq;
import engine.entity.building.ProductionBuilding;
import engine.entity.building.Stable;
import engine.entity.building.StorageBuilding;
import engine.entity.building.Tower;
import engine.entity.unit.Fighter;
import engine.entity.unit.Unit;
import engine.entity.unit.Worker;
import engine.map.Tile;
import factionConfiguration.ForAttackBuilding;
import factionConfiguration.ForFighter;
import factionConfiguration.ForProductionBuilding;
import factionConfiguration.ForStorageBuilding;
import factionConfiguration.ForUpgrade;
import factionConfiguration.ForWorker;


/**
 * 
 * @author gautier
 *
 */

public class EntitiesManager 
{
	private FactionManager factionManager;
	private GraphicsManager graphicsManager;
	
	private List<Entity> collisionList = new ArrayList<Entity>();
	private List<Entity> drawingList = new ArrayList<Entity>();
	
	private List<Unit> selectedUnits = new ArrayList<Unit>();
	
	private ProductionBuilding selectedProdBuilding;
	private AttackBuilding selectedAttackBuilding;
	private StorageBuilding selectedStorageBuilding;
	
	private List<Fighter> fighters;
	private List<Fighter> removeFighters = new ArrayList<Fighter>();
	
	private List<Worker> workers;
	private List<Worker> removeWorkers = new ArrayList<Worker>();
	
	private List<Unit> units;
	
	private List<AttackBuilding> attackBuildings;
	private List<AttackBuilding> removeAttackBuildings = new ArrayList<AttackBuilding>();

	private List<ProductionBuilding> prodBuildings;
	private List<ProductionBuilding> removeProdBuildings = new ArrayList<ProductionBuilding>();
	
	private List<StorageBuilding> storageBuildings;
	private List<StorageBuilding> playerStorageBuildings;
	private List<StorageBuilding> botStorageBuildings;
	private List<StorageBuilding> removeStorageBuildings = new ArrayList<StorageBuilding>();
	
	private List<Ressource> ressources;
	private List<Ressource> removeRessources = new ArrayList<Ressource>();

	
	public EntitiesManager() 
	{
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
		this.playerStorageBuildings = new ArrayList<StorageBuilding>();
		this.botStorageBuildings = new ArrayList<StorageBuilding>();
		
		this.selectedAttackBuilding = null;
		this.selectedProdBuilding = null;
		this.selectedStorageBuilding = null;
	}
	
	public void update() 
	{
		for(Fighter fighter : fighters) 
		{
			fighter.update();
			if(fighter.getHp() < 1)
			{
				removeFighters.add(fighter);
				factionManager.getFactions().get(fighter.getFaction()).setPopulationCount(factionManager.getFactions().get(fighter.getFaction()).getPopulationCount() - 1);
			}
		}
		
		for(Worker worker : workers) {
			worker.update();
			if(worker.getHp() < 1) {
				removeWorkers.add(worker);
				factionManager.getFactions().get(worker.getFaction()).setPopulationCount(factionManager.getFactions().get(worker.getFaction()).getPopulationCount() - 1);
			}
		}
		
		for(ProductionBuilding building : prodBuildings) {
			building.update(factionManager.getFactions().get(building.getFaction()).getPopulationCount(), factionManager.getFactions().get(building.getFaction()).getMaxPopulation());
			if(building.getIsProducing()) {
				if(building.getTimer() <= 0) {
					int id = building.produce();
					if(id >= EntityConfiguration.INFANTRY && id <= EntityConfiguration.SPECIAL_UNIT) {
						ForFighter patron = factionManager.getFactions().get(building.getFaction()).getRace().getPatronFighters().get(id);
						createFighter(id, building.getFaction(), patron, new Position(building.getPosition().getX() - 50, building.getPosition().getY() - 50), building.getDestination());
					}
					else if(id == EntityConfiguration.WORKER) {
						ForWorker patron = factionManager.getFactions().get(building.getFaction()).getRace().getPatronWorkers().get(id);
						createWorker(id, building.getFaction(), patron, new Position(building.getPosition().getX() - 50, building.getPosition().getY()- 50), building.getDestination());
					}
					else if(id >= EntityConfiguration.ARMOR_UPGRADE && id <= EntityConfiguration.AGE_UPGRADE_2) {
						createUpgrade(id, building.getFaction());
					}
					System.out.println("producing unit");
				}
			}
			if(building.getHp() < 1) {
				//System.out.println("We removing a building cause : " + building.getHp());
				this.factionManager.getFactions().get(building.getFaction()).checkUpgrade(building.getElementCount());
				building.remove();
				removeProdBuildings.add(building);
			}
		}
		
		for(AttackBuilding building : attackBuildings) {
			building.update(units);
			if(building.getHp() < 1) {
				//System.out.println("We removing a building cause : " + building.getHp());
				removeAttackBuildings.add(building);
			}
		}
		
		for(StorageBuilding building : storageBuildings) {
			building.update();
			if(building.getHp() < 1) {
				removeStorageBuildings.add(building);
			}
		}
		
		for(Ressource ressource : ressources) {
			//ressource.update();
			if(ressource.getHp() < 1) {
				ressource.getTileAttach().setSolid(false);
				removeRessources.add(ressource);
			}
		}
		
		cleanList();	
	}
	
	public void cleanList() {
		
		//removing ressource
		ressources.removeAll(removeRessources);
		collisionList.removeAll(removeRessources);
		drawingList.removeAll(removeRessources);
		removeRessources.clear();
		
		//removing storage building
		storageBuildings.removeAll(removeStorageBuildings);
		collisionList.removeAll(removeStorageBuildings);
		drawingList.removeAll(removeStorageBuildings);
		playerStorageBuildings.removeAll(removeStorageBuildings);
		botStorageBuildings.removeAll(removeStorageBuildings);
		removeStorageBuildings.clear();
		
		//removing attack building
		attackBuildings.removeAll(removeAttackBuildings);
		collisionList.removeAll(removeAttackBuildings);
		drawingList.removeAll(removeAttackBuildings);
		removeAttackBuildings.clear();
		
		//removing production building
		prodBuildings.removeAll(removeProdBuildings);
		collisionList.removeAll(removeProdBuildings);
		drawingList.removeAll(removeProdBuildings);
		removeProdBuildings.clear();
		
		//removing worker
		workers.removeAll(removeWorkers);
		collisionList.removeAll(removeWorkers);
		drawingList.removeAll(removeWorkers);
		units.removeAll(removeWorkers);
		selectedUnits.removeAll(removeWorkers);
		removeWorkers.clear();
		
		//removing fighter
		fighters.removeAll(removeFighters);
		collisionList.removeAll(removeFighters);
		drawingList.removeAll(removeFighters);
		units.removeAll(removeFighters);
		selectedUnits.removeAll(removeFighters);
		removeFighters.clear();
	}
	
	public void createFighter(int id, int faction, ForFighter patron, Position position, Position destination)
	{
		Fighter fighter = null;
		
		if(id == EntityConfiguration.INFANTRY) {
			fighter = new Fighter(patron.getHp(), 0, patron.getAttackRange(), patron.getAttackSpeed(), patron.getMaxSpeed(), patron.getDamage(), patron.getRange(), patron.getArmor(), 0, position, id, patron.getDescription(), patron.getHpMax(), faction, destination, patron.getSightRange());
		}
		else if(id == EntityConfiguration.ARCHER) {
			fighter = new Fighter(patron.getHp(), 0, patron.getAttackRange(), patron.getAttackSpeed(), patron.getMaxSpeed(), patron.getDamage(), patron.getRange(), patron.getArmor(), 0, position, id, patron.getDescription(), patron.getHpMax(), faction, destination, patron.getSightRange());
		}
		else if(id == EntityConfiguration.CAVALRY) {
			fighter = new Fighter(patron.getHp(), 0, patron.getAttackRange(), patron.getAttackSpeed(), patron.getMaxSpeed(), patron.getDamage(), patron.getRange(), patron.getArmor(), 0, position, id, patron.getDescription(), patron.getHpMax(), faction, destination, patron.getSightRange());
		}
		else if(id == EntityConfiguration.SPECIAL_UNIT) {
			fighter = new Fighter(patron.getHp(), 0, patron.getAttackRange(), patron.getAttackSpeed(), patron.getMaxSpeed(), patron.getDamage(), patron.getRange(), patron.getArmor(), 0, position, id, patron.getDescription(), patron.getHpMax(), faction, destination, patron.getSightRange());
		}
		else {
			System.out.println("invalid id production fighter : " + id);
		}
		
		if(fighter == null) {
			System.out.println("error adding worker in tab cause fighter is null");
		}
		else {
			this.collisionList.add(fighter);
			this.drawingList.add(fighter);
			this.units.add(fighter);
			this.fighters.add(fighter);
			this.factionManager.getFactions().get(faction).setPopulationCount(this.factionManager.getFactions().get(faction).getPopulationCount() + 1);
		}
	}
	
	public void createWorker(int id, int faction, ForWorker patron, Position position, Position destination)
	{
		Worker worker = null;
		
		if(id == EntityConfiguration.WORKER) {
			worker = new Worker(patron.getHp(), 0, patron.getAttackRange(), patron.getAttackSpeed(), patron.getMaxSpeed(), patron.getDamage(), patron.getRange(), patron.getArmor(), patron.getRepair(), position, id, patron.getDescription(), patron.getHpMax(), faction, destination, patron.getSightRange());
		}
		else {
			System.out.print("invalid id production worker : " + id);
		}
		
		if(worker == null) {
			System.out.println("error adding worker in tab cause worker is null");
		}
		else {
			this.collisionList.add(worker);
			this.drawingList.add(worker);
			this.units.add(worker);
			this.workers.add(worker);
			this.factionManager.getFactions().get(faction).setPopulationCount(this.factionManager.getFactions().get(faction).getPopulationCount() + 1);
		}
	}
	
	public void createUpgrade(int id, int faction) {
		if(id == EntityConfiguration.AGE_UPGRADE || id == EntityConfiguration.AGE_UPGRADE_2) {
			System.out.println("upgrade age !");
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
	
	public void createBuilding(int id, int faction, Position position, Tile tile) {
		ProductionBuilding bprod = null;
		AttackBuilding battack = null;
		StorageBuilding bstorage = null;
		tile.setSolid(true);
		
		if(id == EntityConfiguration.FORGE){
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			bprod = new Forge(position, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, factionManager.getFactions().get(faction).getRace().getForgeUpgrades(), graphicsManager.getGraphicsEntity(EntityConfiguration.FORGE), patronBuilding.getSightRange());
		}
		else if(id == EntityConfiguration.STABLE){
			ForFighter patronFighter = factionManager.getFactions().get(faction).getRace().getPatronFighters().get(EntityConfiguration.CAVALRY);
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			bprod = new Stable(position, patronFighter, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, graphicsManager.getGraphicsEntity(EntityConfiguration.STABLE), patronBuilding.getSightRange());
		}
		else if(id == EntityConfiguration.BARRACK){
			ForFighter patronFighter = factionManager.getFactions().get(faction).getRace().getPatronFighters().get(EntityConfiguration.INFANTRY);
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			bprod = new Barrack(position, patronFighter, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, graphicsManager.getGraphicsEntity(EntityConfiguration.BARRACK), patronBuilding.getSightRange());
		}
		else if(id == EntityConfiguration.ARCHERY){
			ForFighter patronFighter = factionManager.getFactions().get(faction).getRace().getPatronFighters().get(EntityConfiguration.ARCHER);
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			bprod = new Archery(position, patronFighter, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, graphicsManager.getGraphicsEntity(EntityConfiguration.ARCHER), patronBuilding.getSightRange());
		}
		else if(id == EntityConfiguration.HQ){
			ForWorker patronWorker = factionManager.getFactions().get(faction).getRace().getPatronWorkers().get(EntityConfiguration.WORKER);
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			bprod = new Hq(position, patronWorker, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, factionManager.getFactions().get(faction).getRace().getHQUpgrades(), graphicsManager.getGraphicsEntity(EntityConfiguration.HQ), patronBuilding.getSightRange());
		}
		else if(id == EntityConfiguration.CASTLE){
			ForFighter patronFighter = factionManager.getFactions().get(faction).getRace().getPatronFighters().get(EntityConfiguration.SPECIAL_UNIT);
			ForProductionBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getProductionBuildings().get(id);
			bprod = new Castle(position, patronFighter, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, graphicsManager.getGraphicsEntity(EntityConfiguration.CASTLE), patronBuilding.getSightRange());
		}
		else if(id == EntityConfiguration.STORAGE){
			ForStorageBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getStorageBuildings().get(id);
			bstorage = new StorageBuilding(position, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, graphicsManager.getGraphicsEntity(EntityConfiguration.STORAGE), patronBuilding.getSightRange());
		}
		else if(id == EntityConfiguration.TOWER){
			ForAttackBuilding patronBuilding = this.factionManager.getFactions().get(faction).getRace().getAttackBuildings().get(id);
			battack = new Tower(position, id, patronBuilding.getDescription(), patronBuilding.getHpMax(), faction, tile, graphicsManager.getGraphicsEntity(EntityConfiguration.TOWER), patronBuilding.getSightRange());
		}
		else{
			System.out.println("invalide ID");
		}
		
		if(bprod != null) {
			this.drawingList.add(bprod);
			this.prodBuildings.add(bprod);
			this.factionManager.getFactions().get(faction).setBuildingCount(this.factionManager.getFactions().get(faction).getBuildingCount() + 1);
			System.out.println("ajout Building production");
		}
		else if(bstorage != null){
			this.drawingList.add(bstorage);
			this.storageBuildings.add(bstorage);
			if(faction == EntityConfiguration.PLAYER_FACTION) {
				this.playerStorageBuildings.add(bstorage);
			}
			else if(faction == EntityConfiguration.BOT_FACTION) {
				this.botStorageBuildings.add(bstorage);
			}
			this.factionManager.getFactions().get(faction).setBuildingCount(this.factionManager.getFactions().get(faction).getBuildingCount() + 1);
			System.out.println("ajout building storage");
		}
		else if(battack != null) {
			this.drawingList.add(battack);
			this.attackBuildings.add(battack);
			this.factionManager.getFactions().get(faction).setBuildingCount(this.factionManager.getFactions().get(faction).getBuildingCount() + 1);
			System.out.println("ajout building attack");
		}
		
	}
	
	public void addSelectedUnit(Unit unit){
		this.selectedUnits.add(unit);
	}
	
	public void addRessource(List<Tile> listPositionRessources){
		for(Tile t : listPositionRessources){
			Ressource r = new Ressource(200, "ressource en or", new Position(t.getColumn() * GameConfiguration.TILE_SIZE, t.getLine() * GameConfiguration.TILE_SIZE), t, EntityConfiguration.GAIA_FACTION, graphicsManager.getGraphicsEntity(EntityConfiguration.RESSOURCE));
			this.ressources.add(r);
			this.drawingList.add(r);
		}
	}
	
	public void clearSelectedBuildings(){
		this.selectedAttackBuilding = null;
		this.selectedProdBuilding = null;
		this.selectedStorageBuilding = null;
	}
	
	public void clean(){
		this.collisionList.clear();
		this.drawingList.clear();
		this.fighters.clear();
		this.attackBuildings.clear();
		this.storageBuildings.clear();
		this.prodBuildings.clear();
		this.workers.clear();
		this.ressources.clear();
		this.selectedUnits.clear();
		this.units.clear();
		clearSelectedBuildings();
		factionManager.clean();
		this.units.clear();
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

	public List<Entity> getCollisionList() {
		return collisionList;
	}

	public void setCollisionList(List<Entity> collisionList) {
		this.collisionList = collisionList;
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
	
	public void clearSelectedUnits()
	{
		selectedUnits.clear();
	}

	public FactionManager getFactionManager() {
		return factionManager;
	}

	public void setFactionManager(FactionManager factionManager) {
		this.factionManager = factionManager;
	}

	public List<ProductionBuilding> getRemoveProdBuildings() {
		return removeProdBuildings;
	}

	public void setRemoveProdBuildings(List<ProductionBuilding> removeProdBuildings) {
		this.removeProdBuildings = removeProdBuildings;
	}

	public List<ProductionBuilding> getProdBuildings() {
		return prodBuildings;
	}

	public void setProdBuildings(List<ProductionBuilding> prodBuildings) {
		this.prodBuildings = prodBuildings;
	}

	public ProductionBuilding getSelectedProdBuilding() {
		return selectedProdBuilding;
	}

	public void setSelectedProdBuilding(ProductionBuilding selectedProdBuilding) {
		this.selectedProdBuilding = selectedProdBuilding;
	}

	public AttackBuilding getSelectedAttackBuilding() {
		return selectedAttackBuilding;
	}

	public void setSelectedAttackBuilding(AttackBuilding selectedAttackBuilding) {
		this.selectedAttackBuilding = selectedAttackBuilding;
	}

	public StorageBuilding getSelectedStorageBuilding() {
		return selectedStorageBuilding;
	}

	public void setSelectedStorageBuilding(StorageBuilding selectedStorageBuilding) {
		this.selectedStorageBuilding = selectedStorageBuilding;
	}

	public List<AttackBuilding> getAttackBuildings() {
		return attackBuildings;
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

	public List<StorageBuilding> getBotStorageBuildings() {
		return botStorageBuildings;
	}

	public void setBotStorageBuildings(List<StorageBuilding> botStorageBuildings) {
		this.botStorageBuildings = botStorageBuildings;
	}
}
