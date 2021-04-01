package engine.manager;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import configuration.BotConfiguration;
import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Entity;
import engine.Faction;
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
import engine.math.Collision;
import factionConfiguration.ForStorageBuilding;
import factionConfiguration.Race;

public class BotManager {
	
	private boolean barrackBuild;
	private boolean archeryBuild;
	private boolean stableBuild;
	private boolean castleBuild;
	private boolean forgeBuild;
	private boolean hqBuild;
	private Fog fog;
	private FactionManager factionManager;
	private AbstractMap<Integer, Integer> priceOfEntity;
	private GraphicsManager graphicsManager;
	private Map map;
	
	public BotManager(FactionManager factionManager, GraphicsManager graphicsManager, Map map) {
		barrackBuild = false;
		archeryBuild = false;
		stableBuild = false;
		castleBuild = false;
		forgeBuild = false;
		hqBuild = false;
		setFactionManager(factionManager);
		fog = new Fog(GameConfiguration.LINE_COUNT, GameConfiguration.COLUMN_COUNT);
		Race race = factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).getRace();
		
		priceOfEntity = new HashMap<Integer, Integer>();
		setGraphicsManager(graphicsManager);
		setMap(map);
		
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
	}
	
	public SiteConstruction update(List<Entity> botEntities, List<StorageBuilding>botStorageBuildings, List<AttackBuilding> botAttackBuildings, List<ProductionBuilding> botProdBuildings, List<Worker> botWorkers, List<Fighter> botFighters, List<Ressource> ressources, List<SiteConstruction> siteConstructions) {
		updateFog(botEntities);
		int money = factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).getMoneyCount();
		//System.out.println("money : " + money);
		List<Ressource> visibleRessources = getVisibleRessources(ressources, fog);
		//System.out.println("nb ressource : " + visibleRessources.size());
		List<Worker> IdleWorker = getIdleWorker(botWorkers);
		//System.out.println("nb idle : " + IdleWorker.size());
		SiteConstruction sc = recolte(IdleWorker, visibleRessources, botStorageBuildings, money, siteConstructions);
		prodWorker(botWorkers, money, botProdBuildings, factionManager);
		return sc;
	}
	
	public void updateFog(List<Entity> botEntities) {
		for(Entity entity : botEntities) {
			Position p = entity.getPosition();
			fog.clearFog(p.getX() - entity.getSightRange() / 3, p.getY() - entity.getSightRange() / 3, entity.getSightRange(), entity, null, null, null, null);
		}
	}
	//tools
	public List<Worker> getIdleWorker(List<Worker> botWorkers){
		List<Worker> idleWorker = new ArrayList<Worker>();
		for(Worker worker : botWorkers) {
			if(worker.getCurrentAction() == EntityConfiguration.IDDLE) {
				idleWorker.add(worker);
			}
		}
		return idleWorker;
	}
	
	public List<Ressource> getVisibleRessources(List<Ressource> ressources, Fog fog){
		List<Ressource> visibleRessources = new ArrayList<Ressource>();
		boolean[][] tabFog = fog.getFog();
		for(int i=0; i<GameConfiguration.LINE_COUNT; i++) {
			for(int j=0; j<GameConfiguration.COLUMN_COUNT; j++) {
			}
		}
		for(Ressource ressource : ressources) {
			int x = ressource.getPosition().getX() / GameConfiguration.TILE_SIZE;
            int y = ressource.getPosition().getY() / GameConfiguration.TILE_SIZE;
			if(tabFog[y][x] == false) {
				visibleRessources.add(ressource);
			}
		}
		return visibleRessources;
	}
	
	public int calculate(Position position, Position position2)
	{
		return (int) Math.sqrt(Math.pow(position.getX() - position2.getX(), 2) + Math.pow(position.getY() - position2.getY(), 2));
	}
	
	
	//states
	public void explore() {
		
	}
	
	public SiteConstruction recolte(List<Worker> IdleWorker, List<Ressource> visibleRessources, List<StorageBuilding>botStorageBuildings, int money, List<SiteConstruction> siteConstructions) {
		SiteConstruction sc = null;
		for(Worker worker : IdleWorker) {
			if(visibleRessources.isEmpty() == false) {
				Ressource ressource = visibleRessources.get(0);
				for(Ressource visibleRessource : visibleRessources) { //choisi la ressource la plus proche du worker
					int distanceRessource;
					distanceRessource = calculate(ressource.getPosition(), worker.getPosition());
					if(distanceRessource > calculate(visibleRessource.getPosition(), worker.getPosition()) && visibleRessource.getHp() > 0 )
					{
						ressource = visibleRessource;
					}
				}
				boolean storageInRange = false;
				for(StorageBuilding storage : botStorageBuildings) { // check si storage in range
					System.out.println("range ressource : " + calculate(storage.getPosition(), ressource.getPosition()) /  GameConfiguration.TILE_SIZE);
					if(calculate(storage.getPosition(), ressource.getPosition()) / GameConfiguration.TILE_SIZE < BotConfiguration.RANGE_RESSOURCE_STORAGE) {
						storageInRange = true;
						System.out.println("bat storage in range ! storagerang = " + storageInRange);
					}
				}
				for(SiteConstruction sitec : siteConstructions) { // check si a site de constru de storage in range
					if(sitec.getFaction() == EntityConfiguration.BOT_FACTION) {
						if(sitec.getId() == EntityConfiguration.STORAGE) {
							if(calculate(ressource.getPosition(), sitec.getPosition()) / GameConfiguration.TILE_SIZE < BotConfiguration.RANGE_RESSOURCE_STORAGE) {
								worker.setTarget(sitec);
								worker.calculateSpeed(sitec.getPosition());
								worker.setCurrentAction(EntityConfiguration.WALK);
								System.out.println("va constru fdp !");
								storageInRange = true;
							}
						}
					}
				}
				System.out.println(storageInRange);
				if(storageInRange == false) { // si pas storage ni site constru in range, construit site constru
					if(priceOfEntity.get(EntityConfiguration.STORAGE)< money) {
						factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).setMoneyCount(money - priceOfEntity.get(EntityConfiguration.STORAGE));
						int tileRessourceX = ressource.getPosition().getX() /64;
						int tileRessourceY = ressource.getPosition().getY() /64;
						System.out.println("pos ressource : " + tileRessourceX + " " + tileRessourceY);
						int tileStorageX = 0;
						int tileStorageY = 0;
						int x = 1;
						int y = 1;
						while(tileStorageX == 0 && tileStorageY == 0) {
							for(int i = tileRessourceY-y; i<=tileRessourceY+y; i++) {
								for(int j = tileRessourceX-x; j<=tileRessourceX+x; j++) {
									if(map.getTile(j, i).isSolid() == false) {
										tileStorageX = j;
										tileStorageY = i;
										System.out.println("pos libre : " + j + " " + i);
									}
								}
							}
							x++;
							y++;
						}
						ForStorageBuilding patronBuilding = this.factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).getRace().getStorageBuildings().get(EntityConfiguration.STORAGE);
						sc = new SiteConstruction(EntityConfiguration.STORAGE, 1, patronBuilding.getHpMax(), patronBuilding.getDescription(), new Position(tileStorageX * 64, tileStorageY * 64) , EntityConfiguration.SITE_CONSTRUCTION, EntityConfiguration.BOT_FACTION, GameConfiguration.TILE_SIZE, 0, graphicsManager, map.getTile(tileStorageX, tileStorageY));
					}
				}
				else {
					worker.initRessource(ressource);
					System.out.println("au travail fdp !");
				}
			}
		}
		return sc;
	}
	
	public void prodWorker(List<Worker> botWorkers, int money, List<ProductionBuilding> botProdBuildings, FactionManager factionManager) {
		if(botWorkers.size() < BotConfiguration.MAX_WORKER) {
			if(money > priceOfEntity.get(EntityConfiguration.WORKER)) {
				for(ProductionBuilding building : botProdBuildings) {
					if(building.getId() == EntityConfiguration.HQ) {
						if(!building.getIsProducing()) {
							int price = building.startProd(EntityConfiguration.WORKER, money);
							factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).setMoneyCount(money - price);
							System.out.println("start prod");
						}
					}
				}
			}
		}
	}
	//getter & setter
	public void setBarrackBuild(boolean barrackBuild) {
		this.barrackBuild = barrackBuild;
	}

	public void setArcheryBuild(boolean archeryBuild) {
		this.archeryBuild = archeryBuild;
	}

	public void setStableBuild(boolean stableBuild) {
		this.stableBuild = stableBuild;
	}

	public void setCastleBuild(boolean castleBuild) {
		this.castleBuild = castleBuild;
	}

	public void setForgeBuild(boolean forgeBuild) {
		this.forgeBuild = forgeBuild;
	}

	public void setHqBuild(boolean hqBuild) {
		this.hqBuild = hqBuild;
	}

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
}
