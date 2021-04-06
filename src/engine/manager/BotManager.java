package engine.manager;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	private int money;
	
	private boolean buildingInAttempt;
	private Tile tileToBuild;
	private int idToBuild;
	
	public BotManager(FactionManager factionManager, GraphicsManager graphicsManager, Map map) {
		setBarrackBuilt(false);
		setArcheryBuilt(false);
		setStableBuilt(false);
		setCastleBuilt(false);
		setForgeBuilt(false);
		setHqBuilt(hqBuilt);
		setFactionManager(factionManager);
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
		}
		
		buildingInAttempt = false;
		tileToBuild = null;
		idToBuild = -1;
	}
	
	public void update(List<Entity> botEntities, List<StorageBuilding>botStorageBuildings, List<AttackBuilding> botAttackBuildings, List<ProductionBuilding> botProdBuildings, List<Worker> botWorkers, List<Fighter> botFighters, List<Ressource> ressources, List<SiteConstruction> siteConstructions) {
		updateList(botEntities, botStorageBuildings, botAttackBuildings, botProdBuildings, botWorkers, botFighters, ressources, siteConstructions);
		updateMoney();
		updateFog();
		explore(); 
		nextAge();
		updateVisibleRessources();
		//System.out.println("nb ressource : " + visibleRessources.size());
		recolte();
		prodWorker();
	}

	//tools ----------------------------------------------------------------------------------------------------------------------------------
	public void updateFog() {
		for(Entity entity : getBotEntities()) {
			Position p = entity.getPosition();
			fog.clearFog(p.getX() - entity.getSightRange() / 3, p.getY() - entity.getSightRange() / 3, entity.getSightRange(), entity, null, null, null, null);
		}
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
		}
	}
	
	
	//states
	public void explore() {
		for(ProductionBuilding building : getBotProdBuildings()) {
			if(building.getId() == EntityConfiguration.HQ) {
				for(Fighter fighter : getBotFighters()) {
					if(fighter.getId() == EntityConfiguration.CAVALRY) {
						if(fighter.getDestination() == null) {
							int x = building.getPosition().getX() / GameConfiguration.TILE_SIZE;
							int y = building.getPosition().getY() / GameConfiguration.TILE_SIZE;
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
												if(calculate(building.getPosition(), new Position(i * GameConfiguration.TILE_SIZE, (y-maxY) * GameConfiguration.TILE_SIZE)) <  calculate(building.getPosition(), new Position(targetX, targetY))) {
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
												if(calculate(building.getPosition(), new Position((x+maxX) * GameConfiguration.TILE_SIZE, j * GameConfiguration.TILE_SIZE)) <  calculate(building.getPosition(), new Position(targetX, targetY))) {
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
												if(calculate(building.getPosition(), new Position((x-maxX) * GameConfiguration.TILE_SIZE, j * GameConfiguration.TILE_SIZE)) <  calculate(building.getPosition(), new Position(targetX, targetY))) {
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
												if(calculate(building.getPosition(), new Position(i * GameConfiguration.TILE_SIZE, (y+maxY) * GameConfiguration.TILE_SIZE)) <  calculate(building.getPosition(), new Position(targetX, targetY))) {
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
							fighter.setDestination(new Position(targetX, targetY));
							fighter.calculateSpeed(new Position(targetX, targetY));
							fighter.setState(EntityConfiguration.WALK);
							System.out.println("cavalier exploring");
							System.out.println("cavalier dest" + fighter.getDestination().getX() + "," + fighter.getDestination().getY());
							this.setMax(maxX);
						}
					}
				}
			}
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
					System.out.println("range ressource : " + calculate(storage.getPosition(), ressource.getPosition()) /  GameConfiguration.TILE_SIZE);
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
								if(map.getTile(j, i).isSolid() == false) {
									storagePosX = j;
									storagePosY = i;
									map.getTile(j, i).setSolid(true);
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
			if(money > priceOfEntity.get(EntityConfiguration.WORKER)) {
				for(ProductionBuilding building : botProdBuildings) {
					if(building.getId() == EntityConfiguration.HQ) {
						if(!building.getIsProducing()) {
							int price = building.startProd(EntityConfiguration.WORKER, money);
							removeMoney(price);
							System.out.println("start prod");
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
		if(idToBuild == EntityConfiguration.BARRACK) {
			barrackBuilt = true;
		}
		else if(idToBuild == EntityConfiguration.ARCHERY) {
			archeryBuilt = true;
		}
		else if(idToBuild == EntityConfiguration.STABLE) {
			stableBuilt = true;
		}
		else if(idToBuild == EntityConfiguration.CASTLE) {
			castleBuilt = true;
		}
		else if(idToBuild == EntityConfiguration.FORGE) {
			forgeBuilt = true;
		}
		else if(idToBuild == EntityConfiguration.HQ) {
			hqBuilt = true;
		}
		
		idToBuild = -1;
	}
	
	public void removeBuilding(int id) {
		if(idToBuild == EntityConfiguration.BARRACK) {
			barrackBuilt = false;
		}
		else if(idToBuild == EntityConfiguration.ARCHERY) {
			archeryBuilt = false;
		}
		else if(idToBuild == EntityConfiguration.STABLE) {
			stableBuilt = false;
		}
		else if(idToBuild == EntityConfiguration.CASTLE) {
			castleBuilt = false;
		}
		else if(idToBuild == EntityConfiguration.FORGE) {
			forgeBuilt = false;
		}
		else if(idToBuild == EntityConfiguration.HQ) {
			hqBuilt = false;
		}
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
							System.out.println("upgrading to age 2");

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
							System.out.println("upgrading to age 3");
						}
					}
				}
			}
		}
	}
	
	public void constructBuildings() {
		updateBuiltBuildings();
		updateIdleWorker();
		if(getIdleWorker() != null) {

		}
		else {
	
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
}
