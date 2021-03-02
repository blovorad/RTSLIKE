package engine;

import java.util.ArrayList;
import java.util.List;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.entity.building.Archery;
import engine.entity.building.AttackBuilding;
import engine.entity.building.Barrack;
import engine.entity.building.Building;
import engine.entity.building.Castle;
import engine.entity.building.Forge;
import engine.entity.building.Hq;
import engine.entity.building.ProductionBuilding;
import engine.entity.building.Stable;
import engine.entity.building.StorageBuilding;
import engine.entity.building.Tower;
import factionConfiguration.ForBuilding;

public class EntitiesManager 
{
	private FactionManager factionManager;
	
	private List<Entity> collisionList = new ArrayList<Entity>();
	private List<Entity> drawingList = new ArrayList<Entity>();
	
	private List<Unit> selectedUnits = new ArrayList<Unit>();
	private List<Building> selectedBuildings = new ArrayList<Building>();
	
	private List<ProductionBuilding> selectedProdBuildings = new ArrayList<ProductionBuilding>();
	
	private List<Fighter> fighters;
	private List<Fighter> removeFighters = new ArrayList<Fighter>();
	
	private List<Worker> workers;
	private List<Worker> removeWorkers = new ArrayList<Worker>();
	
	private List<Unit> units;
	private List<Unit> removeUnits = new ArrayList<Unit>();
	
	private List<Building> buildings;
	private List<Building> removeBuildings = new ArrayList<Building>();
	
	private List<AttackBuilding> attackBuildings;
	private List<AttackBuilding> removeAttackBuildings = new ArrayList<AttackBuilding>();

	private List<ProductionBuilding> prodBuildings;
	private List<ProductionBuilding> removeProdBuildings = new ArrayList<ProductionBuilding>();
	
	
	private List<Ressource> ressources;
	private List<Ressource> removeRessources = new ArrayList<Ressource>();

	
	public EntitiesManager() 
	{
		this.factionManager = new FactionManager();
		this.fighters = new ArrayList<Fighter>();
		this.workers = new ArrayList<Worker>();
		this.units = new ArrayList<Unit>();
		this.buildings = new ArrayList<Building>();
		this.ressources = new ArrayList<Ressource>();
		this.setSelectedUnits(new ArrayList<Unit>());
		this.selectedBuildings = new ArrayList<Building>();
		this.prodBuildings = new ArrayList<ProductionBuilding>();
		this.attackBuildings = new ArrayList<AttackBuilding>();
	}
	
	public void update() 
	{
		for(Fighter fighter : fighters) 
		{
			fighter.update();
			if(fighter.getHp() < 1)
			{
				removeFighters.add(fighter);
			}
		}
		
		//removing fighter
		fighters.removeAll(removeFighters);
		collisionList.removeAll(removeFighters);
		drawingList.removeAll(removeFighters);
		removeFighters.clear();
		
		for(Worker worker : workers) 
		{
			worker.update();
			if(worker.getHp() < 1)
			{
				removeWorkers.add(worker);
			}
		}
		
		//removing worker
		workers.removeAll(removeWorkers);
		collisionList.removeAll(removeWorkers);
		drawingList.removeAll(removeWorkers);
		removeWorkers.clear();
		
		for(Unit unit : units)
		{
			unit.update();
			
			if(unit.getHp() < 1)
			{
				removeUnits.add(unit);
			}
		}
		
		//removing units
		units.removeAll(removeUnits);
		collisionList.removeAll(removeUnits);
		drawingList.removeAll(removeUnits);
		removeUnits.clear();
		
		for(Building building : buildings) 
		{
			building.update(units);
			if(building.getIsProducing())
			{
				if(building.getTimer() <= 0)
				{
					Unit unit = building.produce();
					units.add(unit);
					collisionList.add(unit);
					drawingList.add(unit);
					System.out.println("producing unit");
				}
			}
			if(building.getHp() < 1)
			{
				//System.out.println("We removing a building cause : " + building.getHp());
				removeBuildings.add(building);
			}
		}
		
		//removing building
		buildings.removeAll(removeBuildings);
		collisionList.removeAll(removeBuildings);
		drawingList.removeAll(removeBuildings);
		removeBuildings.clear();
		
		for(ProductionBuilding building : prodBuildings) 
		{
			building.update();
			if(building.getIsProducing())
			{
				if(building.getTimer() <= 0)
				{
					Unit unit = building.produce();
					units.add(unit);
					collisionList.add(unit);
					drawingList.add(unit);
					System.out.println("producing unit");
				}
			}
			if(building.getHp() < 1)
			{
				//System.out.println("We removing a building cause : " + building.getHp());
				removeProdBuildings.add(building);
			}
		}
		
		//removing building
		prodBuildings.removeAll(removeProdBuildings);
		collisionList.removeAll(removeProdBuildings);
		drawingList.removeAll(removeProdBuildings);
		removeProdBuildings.clear();
		
		for(AttackBuilding building : attackBuildings) 
		{
			building.update(units);
			if(building.getHp() < 1)
			{
				//System.out.println("We removing a building cause : " + building.getHp());
				removeAttackBuildings.add(building);
			}
		}
		
		//removing building
		attackBuildings.removeAll(removeAttackBuildings);
		collisionList.removeAll(removeAttackBuildings);
		drawingList.removeAll(removeAttackBuildings);
		removeAttackBuildings.clear();
		
		for(Ressource ressource : ressources) 
		{
			//ressource.update();
			if(ressource.getHp() < 1)
			{
				ressource.getTileAttach().setSolid(false);
				removeRessources.add(ressource);
			}
		}
		
		//removing ressource
		ressources.removeAll(removeRessources);
		collisionList.removeAll(removeRessources);
		drawingList.removeAll(removeRessources);
		removeRessources.clear();
		
	}
	
	public void createBuilding(int id, int faction, Position position) 
	{
		ProductionBuilding bprod = null;
		AttackBuilding battack = null;
		StorageBuilding bstorage = null;
		
		faction = faction - EntityConfiguration.PLAYER_FACTION;
		ForBuilding patronBuilding = this.factionManager.getFactions().get(faction).getBuildings().get(id);
		
		if(id == EntityConfiguration.FORGE)
		{
			//List<Upgrades> list = faction.getListUpgrade();
			//tu dois créer les upgrades a la main ici
			//exemple Upgrades epe = new Upgrades();
			for(int i =0; i < 1; i++)
			{
				//ici tu regarde si les upgrades sont deja faite et les remove  a la list ou celle des autres batiments
			}
			bprod = new Forge(position, id, patronBuilding.getDescription(), patronBuilding.getHpMax());
		}
		//dans les autres tu balances le ForUnit de la race.
		else if(id == EntityConfiguration.STABLE)
		{
			bprod = new Stable(position, factionManager.getFactions().get(faction).getRace().getCavalry(), id, patronBuilding.getDescription(), patronBuilding.getHpMax());
		}
		else if(id == EntityConfiguration.BARRACK)
		{
			bprod = new Barrack(position, factionManager.getFactions().get(faction).getRace().getInfantry(), id, patronBuilding.getDescription(), patronBuilding.getHpMax());
		}
		else if(id == EntityConfiguration.ARCHERY)
		{
			bprod = new Archery(position, factionManager.getFactions().get(faction).getRace().getArcher(), id, patronBuilding.getDescription(), patronBuilding.getHpMax());
		}
		else if(id == EntityConfiguration.HQ)
		{
			bprod = new Hq(position, factionManager.getFactions().get(faction).getRace().getWorker(), id, patronBuilding.getDescription(), patronBuilding.getHpMax());
		}
		else if(id == EntityConfiguration.CASTLE)
		{
			bprod = new Castle(position, factionManager.getFactions().get(faction).getRace().getSpecial(), id, patronBuilding.getDescription(), patronBuilding.getHpMax());
		}
		//coder pas prio storage et tower
		else if(id == EntityConfiguration.STORAGE)
		{
			bstorage = new StorageBuilding(position, id, patronBuilding.getDescription(), patronBuilding.getHpMax());
		}
		else if(id == EntityConfiguration.TOWER)
		{
			battack = new Tower(position, id, patronBuilding.getDescription(), patronBuilding.getHpMax());
		}
		else
		{
			System.out.println("invalide ID");
		}
		if(bprod != null) {
			this.drawingList.add(bprod);
			this.prodBuildings.add(bprod);
			this.factionManager.getFactions().get(faction).setBuildingCount(this.factionManager.getFactions().get(faction).getBuildingCount() + 1);
			System.out.println("ajoutation ProdBuilding");
		}
		if(battack != null) {
			this.drawingList.add(battack);
			this.attackBuildings.add(battack);
			this.factionManager.getFactions().get(faction).setBuildingCount(this.factionManager.getFactions().get(faction).getBuildingCount() + 1);
			System.out.println("ajoutation building");
		}
		if(bstorage != null){
			this.drawingList.add(battack);
			this.attackBuildings.add(battack);
			this.factionManager.getFactions().get(faction).setBuildingCount(this.factionManager.getFactions().get(faction).getBuildingCount() + 1);
			System.out.println("ajoutation building");
		}
		
	}

	public void selectBuilding(Building building)
	{
		selectedBuildings.add(building);
	}
	
	public void selectBuilding(ProductionBuilding building)
	{
		selectedProdBuildings.add(building);
	}
	
	public void addBuilding(Building building)
	{
		this.buildings.add(building); 
	}
	
	public void addSelectedUnit(Unit unit)
	{
		this.selectedUnits.add(unit);
	}
	
	public void addRessource(List<Tile> listPositionRessources)
	{
		for(Tile t : listPositionRessources)
		{
			this.ressources.add(new Ressource(200, "ressource en or", new Position(t.getColumn() * GameConfiguration.TILE_SIZE, t.getLine() * GameConfiguration.TILE_SIZE), t));
			this.collisionList.add(new Ressource(200, "ressource en or", new Position(t.getColumn() * GameConfiguration.TILE_SIZE, t.getLine() * GameConfiguration.TILE_SIZE), t));
			this.drawingList.add(new Ressource(200, "ressource en or", new Position(t.getColumn() * GameConfiguration.TILE_SIZE, t.getLine() * GameConfiguration.TILE_SIZE), t));
		}
	}
	
	public void clean()
	{
		factionManager.clean();
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

	public List<Building> getSelectedBuildings() {
		return selectedBuildings;
	}

	public void setSelectedBuildings(List<Building> selectedBuildings) {
		this.selectedBuildings = selectedBuildings;
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

	public List<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
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
	
	public void clearSelectedBuildings()
	{
		selectedBuildings.clear();
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
}
