package engine.manager;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import configuration.BotConfiguration;
import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Entity;
import engine.Position;
import engine.Ressource;
import engine.entity.building.AttackBuilding;
import engine.entity.building.ProductionBuilding;
import engine.entity.building.SiteConstruction;
import engine.entity.building.StorageBuilding;
import engine.entity.unit.Fighter;
import engine.entity.unit.Unit;
import engine.entity.unit.Worker;
import engine.map.Fog;
import engine.map.Map;
import engine.map.Tile;
import factionConfiguration.Race;

public class BotManager {
	
	private boolean barrackBuilt;
	private boolean archeryBuilt;
	private boolean stableBuilt;
	private boolean castleBuilt;
	private boolean forgeBuilt;
	private boolean hqBuilt;
	private Fog fog;
	private FactionManager factionManager;
	private AbstractMap<Integer, Integer> priceOfEntity;
	private GraphicsManager graphicsManager;
	private Map map;
	private int max;
	private List<Entity> botEntities;
	private List<StorageBuilding>botStorageBuildings;
	private List<AttackBuilding> botAttackBuildings;
	private List<ProductionBuilding> botProdBuildings;
	private List<Worker> botWorkers;
	private List<Fighter> botFighters;
	private List<Ressource> ressources;
	private List<SiteConstruction> siteConstructions;
	private List<Ressource> visibleRessources;
	private List<Worker> idleWorker;
	
	private List<Entity> buildingEnnemieVisible;
	private List<Entity> buildingWaitingEnnemieVisible = new ArrayList<Entity>();
	private List<Entity> buildingRemoveEnnemieVisible = new ArrayList<Entity>();
	
	private List<Unit> unitEnnemieVisible;
	private List<Unit> unitWaitingEnnemieVisible = new ArrayList<Unit>();
	private List<Unit> unitRemoveEnnemieVisible = new ArrayList<Unit>();
	
	private int money;
	private Fighter explorer;
	private Fighter explorerRandom;
	private ProductionBuilding hq;
	
	private boolean buildingInAttempt;
	private Tile tileToBuild;
	private int idToBuild;
	private List<Worker> builders;
	private List<Fighter> army;
	private AbstractMap<Integer, Boolean> upgrades;
	
	public BotManager(FactionManager factionManager, GraphicsManager graphicsManager, Map map) {
		setBarrackBuilt(false);
		setArcheryBuilt(false);
		setStableBuilt(false);
		setCastleBuilt(false);
		setForgeBuilt(false);
		setHqBuilt(true);
		setFactionManager(factionManager);
		buildingEnnemieVisible = new ArrayList<Entity>();
		unitEnnemieVisible = new ArrayList<Unit>();
		fog = new Fog(GameConfiguration.LINE_COUNT, GameConfiguration.COLUMN_COUNT);
		Race race = factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).getRace();
		
		priceOfEntity = new HashMap<Integer, Integer>();
		setGraphicsManager(graphicsManager);
		setMap(map);
		setMax(1);
		
		for(int i = EntityConfiguration.INFANTRY; i < EntityConfiguration.ARCHERY + 1; i++) {
			if(i >= EntityConfiguration.INFANTRY && i <= EntityConfiguration.SPECIAL_UNIT) {
				priceOfEntity.put(i, race.getPatronFighters().get(i).getCost());
			}
			else if(i == EntityConfiguration.WORKER) {
				priceOfEntity.put(i, race.getPatronWorkers().get(i).getCost());
			}
			else if(i == EntityConfiguration.STORAGE) {
				priceOfEntity.put(i, race.getStorageBuildings().get(i).getCost());
			}
			else if(i == EntityConfiguration.TOWER) {
				priceOfEntity.put(i, race.getAttackBuildings().get(i).getCost());
			}
			else {
				priceOfEntity.put(i, race.getProductionBuildings().get(i).getCost());
			}
			//System.out.println("COST ENTITY : " + priceOfEntity.get(i));
		}
		
		for(int i = EntityConfiguration.ARMOR_UPGRADE; i <= EntityConfiguration.SIGHT_RANGE_UPGRADE; i++) {
			priceOfEntity.put(i, race.getForgeUpgrades().get(i).getCost());
			//System.out.println("COST FORGE UPGRADE : " + priceOfEntity.get(i));
		}
		
		for(int i = EntityConfiguration.AGE_UPGRADE; i <= EntityConfiguration.AGE_UPGRADE_2; i++) {
			priceOfEntity.put(i, race.getHQUpgrades().get(i).getCost());
			//System.out.println("COST HQ UPGRADE : " + priceOfEntity.get(i));
		}
		builders = new ArrayList<Worker>();
		army = new ArrayList<Fighter>();
		upgrades = new HashMap<Integer, Boolean>();
		for(int i = EntityConfiguration.ARMOR_UPGRADE; i <= EntityConfiguration.SIGHT_RANGE_UPGRADE; i++) {
			upgrades.put(i, false);
		}
		
		buildingInAttempt = false;
		tileToBuild = null;
		idToBuild = -1;
	}
	
	public void update(List<Entity> botEntities, List<StorageBuilding>botStorageBuildings, List<AttackBuilding> botAttackBuildings, List<ProductionBuilding> botProdBuildings, List<Worker> botWorkers, List<Fighter> botFighters, List<Ressource> ressources, List<SiteConstruction> siteConstructions, List<Entity> playerBuildings, List<Unit> playerUnits) {
		/*updateList(botEntities, botStorageBuildings, botAttackBuildings, botProdBuildings, botWorkers, botFighters, ressources, siteConstructions);
		updateMoney();
		updateFog(playerBuildings, playerUnits);
		explore(); 
		nextAge();
		updateVisibleRessources();
		//System.out.println("nb ressource : " + visibleRessources.size());
		recolte();
		prodWorker();
		placeBuildings();
		constructBuilding();
		prodArmy();
		attack();
		prodUpgrade();
		checkTargetUnit();*/
	}

	//tools ----------------------------------------------------------------------------------------------------------------------------------
	public void checkTargetUnit() {
		for(Fighter fighter : botFighters) {
			if(fighter.getTargetUnit() != null) {
				boolean[][] fog = this.fog.getFog();
				Position targetPos = fighter.getTargetUnit().getPosition();
				if(fog[targetPos.getY() / GameConfiguration.TILE_SIZE][targetPos.getX() / GameConfiguration.TILE_SIZE] == true) {
					fighter.setTarget(null);
					fighter.setTargetUnit(null);
				}
			}
		}
		
		for(Worker worker : botWorkers) {
			if(worker.getTargetUnit() != null) {
				boolean[][] fog = this.fog.getFog();
				Position targetPos = worker.getTargetUnit().getPosition();
				if(fog[targetPos.getY() / GameConfiguration.TILE_SIZE][targetPos.getX() / GameConfiguration.TILE_SIZE] == true) {
					worker.setTarget(null);
					worker.setTargetUnit(null);
				}
			}
		}
	}
	
	public void updateFog(List<Entity> playerBuildings, List<Unit> playerUnits) {
		for(Entity entity : getBotEntities()) {
			Position p = entity.getPosition();
			fog.clearFog(p.getX() - entity.getSightRange() / 3, p.getY() - entity.getSightRange() / 3, entity.getSightRange(), entity, null, null, null, null);
		}
		fog.checkUnit(playerUnits, unitWaitingEnnemieVisible, unitRemoveEnnemieVisible);
		fog.checkingPlayerTargetUnitInFog(unitEnnemieVisible, unitWaitingEnnemieVisible, unitRemoveEnnemieVisible);

		if(unitWaitingEnnemieVisible.isEmpty() == false) {
			unitEnnemieVisible.addAll(unitWaitingEnnemieVisible);
			unitWaitingEnnemieVisible.clear();
		}
		for(Unit unit : unitEnnemieVisible) {
			if(unit.getHp() <= 0) {
				unitRemoveEnnemieVisible.add(unit);
			}
		}
		if(unitRemoveEnnemieVisible.isEmpty() == false) {
			unitEnnemieVisible.removeAll(unitRemoveEnnemieVisible);
			unitRemoveEnnemieVisible.clear();
		}
		unitEnnemieVisible.removeAll(unitRemoveEnnemieVisible);
		unitRemoveEnnemieVisible.clear();
		
		
		fog.checkBuilding(playerBuildings, buildingWaitingEnnemieVisible, buildingRemoveEnnemieVisible);
		fog.checkingPlayerTargetBuildingInFog(buildingEnnemieVisible, buildingWaitingEnnemieVisible, buildingRemoveEnnemieVisible);

		if(buildingWaitingEnnemieVisible.isEmpty() == false) {
			buildingEnnemieVisible.addAll(buildingWaitingEnnemieVisible);
			buildingWaitingEnnemieVisible.clear();
		}
		for(Entity entity : buildingEnnemieVisible) {
			if(entity.getHp() <= 0) {
				buildingRemoveEnnemieVisible.add(entity);
			}
		}
		if(buildingRemoveEnnemieVisible.isEmpty() == false) {
			buildingEnnemieVisible.removeAll(buildingRemoveEnnemieVisible);
			buildingRemoveEnnemieVisible.clear();
		}
		buildingEnnemieVisible.removeAll(buildingRemoveEnnemieVisible);
		buildingRemoveEnnemieVisible.clear();
	}

	public void updateList(List<Entity> botEntities, List<StorageBuilding>botStorageBuildings, List<AttackBuilding> botAttackBuildings, List<ProductionBuilding> botProdBuildings, List<Worker> botWorkers, List<Fighter> botFighters, List<Ressource> ressources, List<SiteConstruction> siteConstructions) {
		setBotEntities(botEntities);
		setBotStorageBuildings(botStorageBuildings);
		setBotAttackBuildings(botAttackBuildings);
		setBotProdBuildings(botProdBuildings);
		setBotWorkers(botWorkers);
		setBotFighters(botFighters);
		setRessources(ressources);
		setSiteConstructions(siteConstructions);
	}
	
	public void updateMoney() {
		setMoney(factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).getMoneyCount());
	}
	
	public void removeMoney(int sub) {
		factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).setMoneyCount(getMoney() - sub);
		updateMoney();
	}
	
	public void addMoney(int add) {
		factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).setMoneyCount(getMoney() + add);
		updateMoney();
	}
	
	public void updateIdleWorker(){
		List<Worker> idleWorker = new ArrayList<Worker>();
		for(Worker worker : getBotWorkers()) {
			if(worker.getCurrentAction() == EntityConfiguration.IDDLE) {
				idleWorker.add(worker);
			}
		}
		setIdleWorker(idleWorker);
	}
	
	public void updateVisibleRessources(){
		List<Ressource> visibleRessources = new ArrayList<Ressource>();
		boolean[][] tabFog = fog.getFog();
		for(int i=0; i<GameConfiguration.LINE_COUNT; i++) {
			for(int j=0; j<GameConfiguration.COLUMN_COUNT; j++) {
			}
		}
		for(Ressource ressource : getRessources()) {
			int x = ressource.getPosition().getX() / GameConfiguration.TILE_SIZE;
            int y = ressource.getPosition().getY() / GameConfiguration.TILE_SIZE;
			if(tabFog[y][x] == false) {
				visibleRessources.add(ressource);
			}
		}
		this.setVisibleRessources(visibleRessources);
	}
	
	public int calculate(Position position, Position position2)
	{
		return (int) Math.sqrt(Math.pow(position.getX() - position2.getX(), 2) + Math.pow(position.getY() - position2.getY(), 2));
	}
	
	public void updateBuiltBuildings() {
		setArcheryBuilt(false);
		setBarrackBuilt(false);
		setCastleBuilt(false);
		setForgeBuilt(false);
		setHqBuilt(false);
		setStableBuilt(false);
		setForgeBuilt(false);
		for(ProductionBuilding building : getBotProdBuildings()) {
			if(building.getId() == EntityConfiguration.ARCHERY) {
				setArcheryBuilt(true);
			}
			if(building.getId() == EntityConfiguration.BARRACK) {
				setBarrackBuilt(true);
			}
			if(building.getId() == EntityConfiguration.CASTLE) {
				setCastleBuilt(true);
			}
			if(building.getId() == EntityConfiguration.FORGE) {
				setForgeBuilt(true);
			}
			if(building.getId() == EntityConfiguration.HQ) {
				setHqBuilt(true);
			}
			if(building.getId() == EntityConfiguration.STABLE) {
				setStableBuilt(true);
			}
			if(building.getId() == EntityConfiguration.FORGE) {
				setForgeBuilt(true);
			}
		}
		for(SiteConstruction site : getSiteConstructions()) {
			if(site.getBuildingId() == EntityConfiguration.ARCHERY) {
				setArcheryBuilt(true);
			}
			if(site.getBuildingId() == EntityConfiguration.BARRACK) {
				setBarrackBuilt(true);
			}
			if(site.getBuildingId() == EntityConfiguration.CASTLE) {
				setCastleBuilt(true);
			}
			if(site.getBuildingId() == EntityConfiguration.FORGE) {
				setForgeBuilt(true);
			}
			if(site.getBuildingId() == EntityConfiguration.HQ) {
				setHqBuilt(true);
			}
			if(site.getBuildingId() == EntityConfiguration.STABLE) {
				setStableBuilt(true);
			}
			if(site.getBuildingId() == EntityConfiguration.FORGE) {
				setForgeBuilt(true);
			}
		}
	}
	
    public int getRandomNumberInRange(int min, int max) {
        if (min <= max) {
        	Random r = new Random();
            return r.nextInt((max - min) + 1) + min;
        }
        else {
        	Random r = new Random();
            return r.nextInt((min - max) + 1) + max;
        }
    }
	
	//states--------------------------------------------------------------------------------------------
	public void explore() {
		if(getHq() == null) {
			for(ProductionBuilding building : getBotProdBuildings()) {
				if(building.getId() == EntityConfiguration.HQ) {
					setHq(building);
				}
			}
		}
		else {
			if(getHq().getHp() <= 0) {
				setHq(null);
			}
		}
		if(getExplorer() == null) {
			for(Fighter fighter : getBotFighters()) {
				if(fighter.getId() == EntityConfiguration.CAVALRY && getArmy().contains(fighter) == false && getExplorer() == null) {
					setExplorer(fighter);
					fighter.setState(EntityConfiguration.PASSIF_STATE);
				}
			}
		}
		else {
			if(getExplorer().getHp() <= 0) {
				setExplorer(null);
			}
		}
		if(getExplorerRandom() == null) {
			for(Fighter fighter : getBotFighters()) {
				if(fighter.getId() == EntityConfiguration.CAVALRY && getArmy().contains(fighter) == false && fighter.equals(getExplorer()) == false && getExplorerRandom() == null) {
					setExplorerRandom(fighter);
					fighter.setState(EntityConfiguration.PASSIF_STATE);
					System.out.println("explo rdm set");
				}
			}
		}
		else {
			if(getExplorerRandom().getHp() <= 0) {
				setExplorerRandom(null);
			}
		}
		if(getHq() != null && getExplorer() != null) {
			if(explorer.getDestination() == null) {
				int x = hq.getPosition().getX() / GameConfiguration.TILE_SIZE;
				int y = hq.getPosition().getY() / GameConfiguration.TILE_SIZE;
				//System.out.println("pos hq = " + x + "," + y);
				boolean[][] tabFog = fog.getFog();
				int targetX = 0;
				int targetY = 0;
				int maxX = getMax();
				int maxY = getMax();
				while(targetX == 0 && targetY == 0) {
					for(int i = x-maxX; i < x+maxX; i++) {
						//System.out.println("tabfog[" + (y-maxY) + "][" + i + "] = " + tabFog[y-maxY][i]);
						if(y-maxY < GameConfiguration.LINE_COUNT && i < GameConfiguration.COLUMN_COUNT) {
							if(tabFog[y-maxY][i] == true) {
								if(targetX == 0 && targetY == 0) {
									targetX = i * GameConfiguration.TILE_SIZE;
									targetY = (y-maxY) * GameConfiguration.TILE_SIZE;
								}
								else {
									if(calculate(hq.getPosition(), new Position(i * GameConfiguration.TILE_SIZE, (y-maxY) * GameConfiguration.TILE_SIZE)) <  calculate(hq.getPosition(), new Position(targetX, targetY))) {
										targetX = i * GameConfiguration.TILE_SIZE;
										targetY = (y-maxY) * GameConfiguration.TILE_SIZE;
									}
								}
							}
						}
					}
					for(int j = y-maxY; j < y+maxY; j++) {
						//System.out.println("tabfog[" + j + "][" + (x+maxX) + "] = " + tabFog[j][x+maxX]);
						if(j < GameConfiguration.LINE_COUNT && x+maxX < GameConfiguration.COLUMN_COUNT) {
							if(tabFog[j][x+maxX] == true) {
								if(targetX == 0 && targetY == 0) {
									targetX = (x+maxX) * GameConfiguration.TILE_SIZE;
									targetY = j * GameConfiguration.TILE_SIZE;
								}
								else {
									if(calculate(hq.getPosition(), new Position((x+maxX) * GameConfiguration.TILE_SIZE, j * GameConfiguration.TILE_SIZE)) <  calculate(hq.getPosition(), new Position(targetX, targetY))) {
										targetX = (x+maxX) * GameConfiguration.TILE_SIZE;
										targetY = j * GameConfiguration.TILE_SIZE;
									}
								}
							}
						}
					}
					for(int j = y-maxY; j < y+maxY; j++) {
						//System.out.println("tabfog[" + j + "][" + (x-maxX) + "] = " + tabFog[j][x-maxX]);
						if(j < GameConfiguration.LINE_COUNT && x-maxX < GameConfiguration.COLUMN_COUNT) {
							if(tabFog[j][x-maxX] == true) {
								if(targetX == 0 && targetY == 0) {
									targetX = (x-maxX) * GameConfiguration.TILE_SIZE;
									targetY = j * GameConfiguration.TILE_SIZE;
								}
								else {
									if(calculate(hq.getPosition(), new Position((x-maxX) * GameConfiguration.TILE_SIZE, j * GameConfiguration.TILE_SIZE)) <  calculate(hq.getPosition(), new Position(targetX, targetY))) {
										targetX = (x-maxX) * GameConfiguration.TILE_SIZE;
										targetY = j * GameConfiguration.TILE_SIZE;
									}
								}
							}
						}
					}
					for(int i = x-maxX; i < x+maxX; i++) {
						//System.out.println("tabfog[" + (y+maxY) + "][" + i + "] = " + tabFog[y+maxY][i]);
						if(y+maxY < GameConfiguration.LINE_COUNT && i < GameConfiguration.COLUMN_COUNT) {
							if(tabFog[y+maxY][i] == true) {
								if(targetX == 0 && targetY == 0) {
									targetX = i * GameConfiguration.TILE_SIZE;
									targetY = (y+maxY) * GameConfiguration.TILE_SIZE;
								}
								else {
									if(calculate(hq.getPosition(), new Position(i * GameConfiguration.TILE_SIZE, (y+maxY) * GameConfiguration.TILE_SIZE)) <  calculate(hq.getPosition(), new Position(targetX, targetY))) {
										targetX = i * GameConfiguration.TILE_SIZE;
										targetY = (y+maxY) * GameConfiguration.TILE_SIZE;
									}
								}
							}
						}
					}
					//System.out.println("maxX = " + maxX);
					//System.out.println("maxY = " + maxY);
					if(targetX == 0 && targetY == 0) {
						maxX++;
						maxY++;
					}
				}
				getExplorer().setDestination(new Position(targetX, targetY));
				getExplorer().calculateSpeed(new Position(targetX, targetY));
				//System.out.println("cavalier exploring");
				//System.out.println("cavalier dest" + explorer.getDestination().getX() + "," + explorer.getDestination().getY());
				this.setMax(maxX);
			}
		}
		if(getExplorerRandom() != null && getExplorerRandom().getDestination() == null) {
			boolean[][] tabFog = fog.getFog();
			boolean destinationFound = false;
			int placeX = getRandomNumberInRange(0, GameConfiguration.COLUMN_COUNT -1);
			int placeY = getRandomNumberInRange(0, GameConfiguration.LINE_COUNT - 1);
			while(destinationFound == false) {
				if(tabFog[placeY][placeX] == true) {
					destinationFound = true;
				}
				else {
					placeX = getRandomNumberInRange(0, GameConfiguration.COLUMN_COUNT -1); // line column ?
					placeY = getRandomNumberInRange(0, GameConfiguration.LINE_COUNT - 1);
				}
			}
			getExplorerRandom().setDestination(new Position(placeX * GameConfiguration.TILE_SIZE, placeY * GameConfiguration.TILE_SIZE));
			getExplorerRandom().calculateSpeed(new Position(placeX * GameConfiguration.TILE_SIZE, placeY * GameConfiguration.TILE_SIZE));
			System.out.println("actual dest : " + getExplorerRandom().getDestination().getX() + ";" + getExplorerRandom().getDestination().getY());
		}
	}
	
	public void recolte() {
		updateVisibleRessources();
		updateIdleWorker();
		//System.out.println("nb idle : " + IdleWorker.size());
		for(Worker worker : getIdleWorker()) {
			if(getVisibleRessources().isEmpty() == false) {
				Ressource ressource = visibleRessources.get(0);
				for(Ressource visibleRessource : getVisibleRessources()) { //choisi la ressource la plus proche du worker
					int distanceRessource;
					distanceRessource = calculate(ressource.getPosition(), worker.getPosition());
					if(distanceRessource > calculate(visibleRessource.getPosition(), worker.getPosition()) && visibleRessource.getHp() > 0 )
					{
						ressource = visibleRessource;
					}
				}
				boolean storageInRange = false;
				for(StorageBuilding storage : getBotStorageBuildings()) { // check si storage in range
					//System.out.println("range ressource : " + calculate(storage.getPosition(), ressource.getPosition()) /  GameConfiguration.TILE_SIZE);
					if(calculate(storage.getPosition(), ressource.getPosition()) / GameConfiguration.TILE_SIZE < BotConfiguration.RANGE_RESSOURCE_STORAGE) {
						storageInRange = true;
						//System.out.println("bat storage in range ! storagerang = " + storageInRange);
					}
				}
				for(SiteConstruction sitec : getSiteConstructions()) { // check si a site de constru de storage in range
					if(sitec.getFaction() == EntityConfiguration.BOT_FACTION) {
						if(sitec.getBuildingId() == EntityConfiguration.STORAGE) {
							ressource = getClosestRessource(visibleRessources, sitec);
							
							if(calculate(ressource.getPosition(), sitec.getPosition()) / GameConfiguration.TILE_SIZE < BotConfiguration.RANGE_RESSOURCE_STORAGE) {
								worker.setTarget(sitec);
								worker.calculateSpeed(sitec.getPosition());
								worker.setCurrentAction(EntityConfiguration.WALK);
								//System.out.println("va constru fdp !");
								storageInRange = true;
							}
						}
					}
				}
				//System.out.println(storageInRange);
				if(storageInRange == false) { // si pas storage ni site constru in range, construit site constru
					if(priceOfEntity.get(EntityConfiguration.STORAGE)< money) {
						factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).setMoneyCount(money - priceOfEntity.get(EntityConfiguration.STORAGE));
						int tileRessourceX = ressource.getPosition().getX() / GameConfiguration.TILE_SIZE;
						int tileRessourceY = ressource.getPosition().getY() / GameConfiguration.TILE_SIZE;
						//System.out.println("pos ressource : " + tileRessourceX + " " + tileRessourceY);

						int storagePosX = 0;
						int storagePosY = 0;
						boolean foundPlace = false;
						int x = tileRessourceX - 1;
						int y = tileRessourceY - 1;
						int width = tileRessourceX + 1;
						int height = tileRessourceY + 1;
						
						for(int i = y; i<= height; i++) {
							for(int j = x; j <= width; j++) {
								if(map.getTile(j, i).isSolid() == false && foundPlace == false) {
									storagePosX = j;
									storagePosY = i;
									foundPlace = true;
									//System.out.println("pos libre : " + j + " " + i);
								}
							}
						}
						if(foundPlace) {
							buildingInAttempt = true;
							tileToBuild = map.getTile(storagePosX, storagePosY);
							idToBuild = EntityConfiguration.STORAGE;
						}
					}
				}
				else {
					if(worker.getCurrentAction() != EntityConfiguration.REPAIR && worker.getCurrentAction() != EntityConfiguration.WALK) {
						worker.initRessource(ressource);
						//System.out.println("au travail fdp !");
					}
				}
			}
		}
	}
	
	public void prodWorker() {
		if(botWorkers.size() < BotConfiguration.MAX_WORKER) {
			if(getMoney() > priceOfEntity.get(EntityConfiguration.WORKER)) {
				for(ProductionBuilding building : botProdBuildings) {
					if(building.getId() == EntityConfiguration.HQ) {
						if(!building.getIsProducing()) {
							int price = building.startProd(EntityConfiguration.WORKER, getMoney());
							removeMoney(price);
							//System.out.println("start prod");
						}
					}
				}
			}
		}
	}
	
	public Ressource getClosestRessource(List<Ressource> visibleRessources, Worker worker) {
		Ressource ressource = visibleRessources.get(0);
		for(Ressource visibleRessource : visibleRessources) { //choisi la ressource la plus proche du worker
			int distanceRessource;
			distanceRessource = calculate(ressource.getPosition(), worker.getPosition());
			if(distanceRessource > calculate(visibleRessource.getPosition(), worker.getPosition()) && visibleRessource.getHp() > 0 ){
				ressource = visibleRessource;
			}
		}
		
		return ressource;
	}
	
	public Ressource getClosestRessource(List<Ressource> visibleRessources, SiteConstruction sc) {
		Ressource ressource = visibleRessources.get(0);
		for(Ressource visibleRessource : visibleRessources) { //choisi la ressource la plus proche du worker
			int distanceRessource;
			distanceRessource = calculate(ressource.getPosition(), sc.getPosition());
			if(distanceRessource > calculate(visibleRessource.getPosition(), sc.getPosition()) && visibleRessource.getHp() > 0 ){
				ressource = visibleRessource;
			}
		}
		
		return ressource;
	}
	
	public void buildBuilding() {
		buildingInAttempt = false;
		tileToBuild = null;
		updateBuiltBuildings();
		idToBuild = -1;
	}
	
	public void nextAge() {
		if(factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).getAge() != 3) {
			for(ProductionBuilding building : getBotProdBuildings()) {
				if(building.getId() == EntityConfiguration.HQ) {
					boolean upgrading = false;
					if(factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).getAge() == 1) {
						if(building.getIsProducing()) {
							for(int prod : building.getElementCount()) {
								if(prod == EntityConfiguration.AGE_UPGRADE) {
									upgrading = true;
								}
							}
						}
						if(upgrading == false) {
							removeMoney(building.startProd(EntityConfiguration.AGE_UPGRADE, money));
							//System.out.println("upgrading to age 2");

						}
					}
					if(factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).getAge() == 2) {
						if(building.getIsProducing()) {
							for(int prod : building.getElementCount()) {
								if(prod == EntityConfiguration.AGE_UPGRADE_2) {
									upgrading = true;
								}
							}
						}
						if(upgrading == false) {
							removeMoney(building.startProd(EntityConfiguration.AGE_UPGRADE_2, money));
							//System.out.println("upgrading to age 3");
						}
					}
				}
			}
		}
	}
	
	public void placeBuildings() {
		updateBuiltBuildings();
		updateMoney();
		if(getBuildingInAttempt() == false && getBotWorkers().isEmpty() == false) {
			//System.out.println("placing building");
			//System.out.println("hq ? " + isHqBuilt());
			//System.out.println("barrack ? " + isBarrackBuilt());
			if(isHqBuilt() == false && getMoney() >= priceOfEntity.get(EntityConfiguration.HQ)) {
				//System.out.println("hq pas contruct");
				int targetX = getBotWorkers().get(0).getPosition().getX() / 64;
				//System.out.println("targetX : " + targetX);
				int targetY = getBotWorkers().get(0).getPosition().getY() / 64;
				//System.out.println("targetY : " + targetY);
				Tile targetTile = map.getTile(targetY, targetX);
				Tile place = null;
				int max = 1;
				while(place == null) {
					for(int i = targetTile.getColumn() - max; i <= targetTile.getColumn() + max; i++) {
						for(int j = targetTile.getLine() - max; j <= targetTile.getLine() + max; j++) {
							if(i < GameConfiguration.COLUMN_COUNT && j < GameConfiguration.LINE_COUNT) {
								//System.out.println("case check : " + j + ";" + i);
								if(map.getTile(j, i).isSolid() == false) {
									place = map.getTile(j, i);
								}
							}
						}
					}
					max++;
				}
				removeMoney(priceOfEntity.get(EntityConfiguration.HQ));
				buildingInAttempt = true;
				tileToBuild = place;
				idToBuild = EntityConfiguration.HQ;
			}
			else if(isBarrackBuilt() == false && getMoney() >= priceOfEntity.get(EntityConfiguration.BARRACK)) {
				//System.out.println("barrack pas construct");
				if(getHq() != null) {
					//System.out.println("on a un hq");
					int targetX = getHq().getPosition().getX() / 64;
					int targetY = getHq().getPosition().getY() / 64;
					//Tile HqTile = map.getTile(targetX, targetY);
					Tile place = null;
					while(place == null) {
						//System.out.println("tagetX = " + targetX);
						//System.out.println("tagetY = " + targetY);
						int placeX = getRandomNumberInRange(targetX - 5, targetX + 5);
						//System.out.println("placeX = " + placeX);
						int placeY = getRandomNumberInRange(targetY - 5, targetY + 5);
						//System.out.println("placeY = " + placeY);
						if(placeX < GameConfiguration.COLUMN_COUNT && placeY < GameConfiguration.LINE_COUNT) { //verifier si je me suis pas encore tromper sur ligne collone
							if(map.getTile(placeX, placeY).isSolid() == false) {
								place = map.getTile(placeX, placeY);
							}
						}
					}
					removeMoney(priceOfEntity.get(EntityConfiguration.BARRACK));
					buildingInAttempt = true;
					tileToBuild = place;
					idToBuild = EntityConfiguration.BARRACK;
				}
			}
			else if(factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).getAge() >= 2) {
				if(isStableBuilt() == false && getMoney() >= priceOfEntity.get(EntityConfiguration.STABLE)) {
					//System.out.println("stable pas construct");
					if(getHq() != null) {
						//System.out.println("on a un hq");
						int targetX = getHq().getPosition().getX() / 64;
						int targetY = getHq().getPosition().getY() / 64;
						//Tile HqTile = map.getTile(targetX, targetY);
						Tile place = null;
						while(place == null) {
							//System.out.println("tagetX = " + targetX);
							//System.out.println("tagetY = " + targetY);
							int placeX = getRandomNumberInRange(targetX - 5, targetX + 5);
							//System.out.println("placeX = " + placeX);
							int placeY = getRandomNumberInRange(targetY - 5, targetY + 5);
							//System.out.println("placeY = " + placeY);
							if(placeX < 100 && placeY < 100) {
								if(map.getTile(placeX, placeY).isSolid() == false) {
									place = map.getTile(placeX, placeY);
								}
							}
						}
						removeMoney(priceOfEntity.get(EntityConfiguration.STABLE));
						buildingInAttempt = true;
						tileToBuild = place;
						idToBuild = EntityConfiguration.STABLE;
					}
				}
				else if(isArcheryBuilt() == false && getMoney() >= priceOfEntity.get(EntityConfiguration.ARCHERY)) {
					//System.out.println("archery pas construct");
					if(getHq() != null) {
						//System.out.println("on a un hq");
						int targetX = getHq().getPosition().getX() / 64;
						int targetY = getHq().getPosition().getY() / 64;
						//Tile HqTile = map.getTile(targetX, targetY);
						Tile place = null;
						while(place == null) {
							//System.out.println("tagetX = " + targetX);
							//System.out.println("tagetY = " + targetY);
							int placeX = getRandomNumberInRange(targetX - 5, targetX + 5);
							//System.out.println("placeX = " + placeX);
							int placeY = getRandomNumberInRange(targetY - 5, targetY + 5);
							//System.out.println("placeY = " + placeY);
							if(placeX < 100 && placeY < 100) {
								if(map.getTile(placeX, placeY).isSolid() == false) {
									place = map.getTile(placeX, placeY);
								}
							}
						}
						removeMoney(priceOfEntity.get(EntityConfiguration.ARCHERY));
						buildingInAttempt = true;
						tileToBuild = place;
						idToBuild = EntityConfiguration.ARCHERY;
					}
				}
				else if(isForgeBuilt() == false && getMoney() >= priceOfEntity.get(EntityConfiguration.FORGE)) {
					//System.out.println("stable pas construct");
					if(getHq() != null) {
						//System.out.println("on a un hq");
						int targetX = getHq().getPosition().getX() / 64;
						int targetY = getHq().getPosition().getY() / 64;
						//Tile HqTile = map.getTile(targetX, targetY);
						Tile place = null;
						while(place == null) {
							//System.out.println("tagetX = " + targetX);
							//System.out.println("tagetY = " + targetY);
							int placeX = getRandomNumberInRange(targetX - 5, targetX + 5);
							//System.out.println("placeX = " + placeX);
							int placeY = getRandomNumberInRange(targetY - 5, targetY + 5);
							//System.out.println("placeY = " + placeY);
							if(placeX < 100 && placeY < 100) {
								if(map.getTile(placeX, placeY).isSolid() == false) {
									place = map.getTile(placeX, placeY);
								}
							}
						}
						removeMoney(priceOfEntity.get(EntityConfiguration.FORGE));
						buildingInAttempt = true;
						tileToBuild = place;
						idToBuild = EntityConfiguration.FORGE;
					}
				}
				else if(factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).getAge() >= 3) {
					if(isCastleBuilt() == false && getMoney() >= priceOfEntity.get(EntityConfiguration.CASTLE)) {
						//System.out.println("castle pas construct");
						if(getHq() != null) {
							//System.out.println("on a un hq");
							int targetX = getHq().getPosition().getX() / 64;
							int targetY = getHq().getPosition().getY() / 64;
							//Tile HqTile = map.getTile(targetX, targetY);
							Tile place = null;
							while(place == null) {
								//System.out.println("tagetX = " + targetX);
								//System.out.println("tagetY = " + targetY);
								int placeX = getRandomNumberInRange(targetX - 5, targetX + 5);
								//System.out.println("placeX = " + placeX);
								int placeY = getRandomNumberInRange(targetY - 5, targetY + 5);
								//System.out.println("placeY = " + placeY);
								if(placeX < 100 && placeY < 100) {
									if(map.getTile(placeX, placeY).isSolid() == false) {
										place = map.getTile(placeX, placeY);
									}
								}
							}
							removeMoney(priceOfEntity.get(EntityConfiguration.CASTLE));
							buildingInAttempt = true;
							tileToBuild = place;
							idToBuild = EntityConfiguration.CASTLE;
						}
					}
				}
			}
		}
		
	}
	
	public void constructBuilding() {
		updateIdleWorker();
		if(getSiteConstructions() != null) {
			for(SiteConstruction sitec : getSiteConstructions()) { // check si a site de constru de storage in range
				if(sitec.getFaction() == EntityConfiguration.BOT_FACTION) {
					if(sitec.getBuildingId() != EntityConfiguration.STORAGE) {
						//System.out.println("construct target found");
						boolean hasWorker = false;
						for(Worker worker : getBotWorkers()) {
							if(worker.getTarget() != null) {
								if(worker.getTarget().equals(sitec)) {
									hasWorker = true;
								}
							}
						}
						if(hasWorker == false) {
							if(getIdleWorker().isEmpty() == false) {
								//System.out.println("y a des idle");
								for(Worker worker : getIdleWorker()) {
									builders.add(worker);
								}
							}
							else {
								for(Worker worker : getBotWorkers()) {
									if(getBuilders().isEmpty()) {
										if(worker.getTarget() != null && worker.getTarget().getId() != EntityConfiguration.SITE_CONSTRUCTION) {
											builders.add(worker);
										}
									}
									if(builders.isEmpty() == false) {
										if(calculate(worker.getPosition(), sitec.getPosition()) < calculate(getBuilders().get(0).getPosition(), sitec.getPosition())){
											if(worker.getTarget() != null && worker.getTarget().getId() != EntityConfiguration.SITE_CONSTRUCTION) {
												builders.clear();
												builders.add(worker);
											}
										}
									}
								}
							}
							for(Worker worker : getBuilders()) {
								//System.out.println("builders : " + getBuilders().size());
								worker.setTarget(sitec);
								worker.calculateSpeed(sitec.getPosition());
								worker.setCurrentAction(EntityConfiguration.WALK);
								//System.out.println("va constru fdp !");
							}
						}
					}
				}
				getBuilders().clear();
			}
		}
	}
	
	public void prodArmy() {
		int nbIfantry = 0;
		int nbCavalry = 0;
		int nbArcher = 0;
		int nbSpecial = 0;
		
		List<Fighter> cptInfantry = new ArrayList<Fighter>();
		List<Fighter> cptCavalry = new ArrayList<Fighter>();
		List<Fighter> cptArcher = new ArrayList<Fighter>();
		List<Fighter> cptSpecial = new ArrayList<Fighter>();
		
		if(isBarrackBuilt()) {
			nbIfantry = BotConfiguration.MAX_INFANTRY;
		}
		if(isStableBuilt()) {
			nbCavalry = BotConfiguration.MAX_CAVALRY;;
		}
		if(isArcheryBuilt()) {
			nbArcher = BotConfiguration.MAX_ARCHER;;
		}
		if(isCastleBuilt()) {
			nbSpecial = BotConfiguration.MAX_SPECIAL;;
		}
		for(Fighter fighter : getBotFighters()) {
			if(getArmy().contains(fighter) == false) {
				if(fighter.getId() == EntityConfiguration.INFANTRY) {
					cptInfantry.add(fighter);
				}
				if(fighter.getId() == EntityConfiguration.CAVALRY && fighter.equals(getExplorer()) == false && fighter.equals(getExplorerRandom()) == false) {
					cptCavalry.add(fighter);
				}
				if(fighter.getId() == EntityConfiguration.ARCHER) {
					cptArcher.add(fighter);
				}
				if(fighter.getId() == EntityConfiguration.SPECIAL_UNIT) {
					cptSpecial.add(fighter);
				}
			}
		}
		/*System.out.println("army size : " + army.size());
		System.out.println("infantry : " + cptInfantry.size() + " / " + nbIfantry);
		System.out.println("cavalry : " + cptCavalry.size() + " / " + nbCavalry);
		System.out.println("archer : " + cptArcher.size() + " / " + nbArcher);
		System.out.println("special : " + cptSpecial.size() + " / " + nbSpecial);*/
		for(ProductionBuilding building : getBotProdBuildings()) {
			if(building.getIsProducing() == false) {
				if(building.getId() == EntityConfiguration.BARRACK && cptInfantry.size() < nbIfantry) {
					if(getMoney() > priceOfEntity.get(EntityConfiguration.INFANTRY)) {
						removeMoney(building.startProd(EntityConfiguration.INFANTRY, getMoney()));
					}
				}
				if(building.getId() == EntityConfiguration.STABLE && cptCavalry.size() < nbCavalry) {
					if(getMoney() > priceOfEntity.get(EntityConfiguration.CAVALRY)) {
						removeMoney(building.startProd(EntityConfiguration.CAVALRY, getMoney()));
					}
				}
				if(building.getId() == EntityConfiguration.ARCHERY && cptArcher.size() < nbArcher) {
					if(getMoney() > priceOfEntity.get(EntityConfiguration.ARCHER)) {
						removeMoney(building.startProd(EntityConfiguration.ARCHER, getMoney()));
					}
				}
				if(building.getId() == EntityConfiguration.CASTLE && cptSpecial.size() < nbSpecial) {
					if(getMoney() > priceOfEntity.get(EntityConfiguration.SPECIAL_UNIT)) {
						removeMoney(building.startProd(EntityConfiguration.SPECIAL_UNIT, getMoney()));
					}
				}
			}
		}
		if(cptInfantry.size() >= nbIfantry && nbIfantry != 0 && cptCavalry.size() >= nbCavalry && nbCavalry != 0 && cptArcher.size() >= nbArcher && nbArcher != 0 && cptSpecial.size() >= nbSpecial && nbSpecial != 0) {
			//System.out.println("constitution de l'armee ! -------------------------------------------------");
			for(Fighter fighter : cptInfantry) {
				getArmy().add(fighter);
			}
			for(Fighter fighter : cptCavalry) {
				getArmy().add(fighter);
			}
			for(Fighter fighter : cptArcher) {
				getArmy().add(fighter);
			}
			for(Fighter fighter : cptSpecial) {
				getArmy().add(fighter);
			}
		}
	}
	
	public void attack() {
        if(getArmy().isEmpty() == false) {
            if(buildingEnnemieVisible.isEmpty() == false) {
                Entity target = null;
                for(Entity ennemie : buildingEnnemieVisible) {
                	if(target == null) {
                        target = ennemie;
                    }
                    if(calculate(ennemie.getPosition(), hq.getPosition()) < calculate(target.getPosition(), hq.getPosition())) {
                        target = ennemie;
                    }
                }
                //System.out.println("army target : " + target);
                for(Fighter fighter : getArmy()) {
                    if(fighter.getTarget() == null) {
                        fighter.setTarget(target);
                        fighter.calculateSpeed(target.getPosition());
                        //System.out.println("attack !");
                    }
                }
            }
            if(unitEnnemieVisible.isEmpty() == false) {
            	 Entity target = null;
            	 Unit targetUnit = null;
                 for(Unit ennemie : unitEnnemieVisible) {
                 	if(target == null) {
                         target = ennemie;
                     }
                 	if(targetUnit == null) {
                 		targetUnit = ennemie;
                 	}
                    if(calculate(ennemie.getPosition(), hq.getPosition()) < calculate(target.getPosition(), hq.getPosition())) {
                        target = ennemie;
                    }
                 }
                 //System.out.println("army target : " + target);
                 for(Fighter fighter : getArmy()) {
                	 if(fighter.getHp() <= 0) {
                		 getArmy().remove(fighter);
                	 }
                	 else if(fighter.getTarget() == null) {
                         fighter.setTarget(target);
                         fighter.setTargetUnit(targetUnit);
                         fighter.calculateSpeed(target.getPosition());
                         //System.out.println("attack !");
                     }
                 }
            }
        }
    }
	
	public void prodUpgrade() {
		if(isForgeBuilt() == true) {
			for(ProductionBuilding building : getBotProdBuildings()) {
				if(building.getId() == EntityConfiguration.FORGE) {
					//System.out.println("Forge presente !");
					if(building.getIsProducing() == false) {
						boolean done = false;
						int id = EntityConfiguration.ARMOR_UPGRADE;
						while(done == false) {
							if(getUpgrades().get(id) == false) {
								if(priceOfEntity.get(id) < getMoney()) {
									System.out.println("producing upgrade id :" + id);
									removeMoney(building.startProd(id, getMoney()));
									getUpgrades().replace(id, true);
									done = true;
								}
							}
							id++;
							if(id > EntityConfiguration.SIGHT_RANGE_UPGRADE) {
								done = true;
							}
						}
					}
				}
			}
		}
	}
	
	//getter & setter
	public FactionManager getFactionManager() {
		return factionManager;
	}

	public void setFactionManager(FactionManager factionManager) {
		this.factionManager = factionManager;
	}

	public GraphicsManager getGraphicsManager() {
		return graphicsManager;
	}

	public void setGraphicsManager(GraphicsManager graphicsManager) {
		this.graphicsManager = graphicsManager;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
	
	public boolean getBuildingInAttempt() {
		return buildingInAttempt;
	}
	
	public Tile getTileToBuild() {
		return tileToBuild;
	}
	
	public int getIdToBuild() {
		return idToBuild;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int initMax) {
		this.max = initMax;
	}

	public List<Entity> getBotEntities() {
		return botEntities;
	}

	public void setBotEntities(List<Entity> botEntities) {
		this.botEntities = botEntities;
	}

	public List<StorageBuilding> getBotStorageBuildings() {
		return botStorageBuildings;
	}

	public void setBotStorageBuildings(List<StorageBuilding> botStorageBuildings) {
		this.botStorageBuildings = botStorageBuildings;
	}

	public List<AttackBuilding> getBotAttackBuildings() {
		return botAttackBuildings;
	}

	public void setBotAttackBuildings(List<AttackBuilding> botAttackBuildings) {
		this.botAttackBuildings = botAttackBuildings;
	}

	public List<ProductionBuilding> getBotProdBuildings() {
		return botProdBuildings;
	}

	public void setBotProdBuildings(List<ProductionBuilding> botProdBuildings) {
		this.botProdBuildings = botProdBuildings;
	}

	public List<Worker> getBotWorkers() {
		return botWorkers;
	}

	public void setBotWorkers(List<Worker> botWorkers) {
		this.botWorkers = botWorkers;
	}

	public List<Fighter> getBotFighters() {
		return botFighters;
	}

	public void setBotFighters(List<Fighter> botFighters) {
		this.botFighters = botFighters;
	}

	public List<Ressource> getRessources() {
		return ressources;
	}

	public void setRessources(List<Ressource> ressources) {
		this.ressources = ressources;
	}

	public List<SiteConstruction> getSiteConstructions() {
		return siteConstructions;
	}

	public void setSiteConstructions(List<SiteConstruction> siteConstructions) {
		this.siteConstructions = siteConstructions;
	}

	public List<Ressource> getVisibleRessources() {
		return visibleRessources;
	}

	public void setVisibleRessources(List<Ressource> visibleRessources) {
		this.visibleRessources = visibleRessources;
	}

	public int getMoney() {
		updateMoney();
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public List<Worker> getIdleWorker() {
		return idleWorker;
	}

	public void setIdleWorker(List<Worker> idleWorker) {
		this.idleWorker = idleWorker;
	}

	public boolean isBarrackBuilt() {
		return barrackBuilt;
	}

	public void setBarrackBuilt(boolean barrackBuilt) {
		this.barrackBuilt = barrackBuilt;
	}

	public boolean isArcheryBuilt() {
		return archeryBuilt;
	}

	public void setArcheryBuilt(boolean archeryBuilt) {
		this.archeryBuilt = archeryBuilt;
	}

	public boolean isStableBuilt() {
		return stableBuilt;
	}

	public void setStableBuilt(boolean stableBuilt) {
		this.stableBuilt = stableBuilt;
	}

	public boolean isCastleBuilt() {
		return castleBuilt;
	}

	public void setCastleBuilt(boolean castleBuilt) {
		this.castleBuilt = castleBuilt;
	}

	public boolean isForgeBuilt() {
		return forgeBuilt;
	}

	public void setForgeBuilt(boolean forgeBuilt) {
		this.forgeBuilt = forgeBuilt;
	}

	public boolean isHqBuilt() {
		return hqBuilt;
	}

	public void setHqBuilt(boolean hqBuilt) {
		this.hqBuilt = hqBuilt;
	}

	public Fighter getExplorer() {
		return explorer;
	}

	public void setExplorer(Fighter explorer) {
		this.explorer = explorer;
	}

	public ProductionBuilding getHq() {
		return hq;
	}

	public void setHq(ProductionBuilding hq) {
		this.hq = hq;
	}

	public List<Worker> getBuilders() {
		return builders;
	}

	public void setBuilders(List<Worker> builders) {
		this.builders = builders;
	}

	public List<Fighter> getArmy() {
		return army;
	}

	public void setArmy(List<Fighter> army) {
		this.army = army;
	}

	public Fighter getExplorerRandom() {
		return explorerRandom;
	}

	public void setExplorerRandom(Fighter explorerRandom) {
		this.explorerRandom = explorerRandom;
	}

	public AbstractMap<Integer, Boolean> getUpgrades() {
		return upgrades;
	}

	public void setUpgrades(AbstractMap<Integer, Boolean> upgrades) {
		this.upgrades = upgrades;
	}
}
