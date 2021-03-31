package engine.manager;

import java.util.ArrayList;
import java.util.List;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Entity;
import engine.Faction;
import engine.Position;
import engine.Ressource;
import engine.entity.building.AttackBuilding;
import engine.entity.building.ProductionBuilding;
import engine.entity.building.StorageBuilding;
import engine.entity.unit.Fighter;
import engine.entity.unit.Worker;
import engine.map.Fog;
import engine.math.Collision;

public class BotManager {
	
	private boolean barrackBuild;
	private boolean archeryBuild;
	private boolean stableBuild;
	private boolean castleBuild;
	private boolean forgeBuild;
	private boolean hqBuild;
	private Fog fog;
	private FactionManager factionManager;
	
	public BotManager(FactionManager factionManager) {
		barrackBuild = false;
		archeryBuild = false;
		stableBuild = false;
		castleBuild = false;
		forgeBuild = false;
		hqBuild = false;
		setFactionManager(factionManager);
		fog = new Fog(GameConfiguration.LINE_COUNT, GameConfiguration.COLUMN_COUNT);
	}
	
	public void update(List<Entity> botEntities, List<StorageBuilding>botStorageBuildings, List<AttackBuilding> botAttackBuildings, List<ProductionBuilding> botProdBuildings, List<Worker> botWorkers, List<Fighter> botFighters, List<Ressource> ressources) {
		updateFog(botEntities);
		int money = factionManager.getFactions().get(EntityConfiguration.BOT_FACTION).getMoneyCount();
		System.out.println("money : " + money);
		List<Ressource> visibleRessources = getVisibleRessources(ressources, fog);
		//System.out.println("nb ressource : " + visibleRessources.size());
		List<Worker> IdleWorker = getIdleWorker(botWorkers);
		//System.out.println("nb idle : " + IdleWorker.size());
		recolte(IdleWorker, visibleRessources);
		prodWorker(botWorkers, money, botProdBuildings, factionManager);
	}
	
	public void updateFog(List<Entity> botEntities) {
		for(Entity entity : botEntities) {
			Position p = entity.getPosition();
			fog.clearFog(p.getX() - entity.getSightRange() / 6, p.getY() - entity.getSightRange() / 6, entity.getSightRange());
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
	
	public int calculate(Position position, Worker worker)
	{
		return (int) Math.sqrt(Math.pow(position.getX() - worker.getPosition().getX(), 2) + Math.pow(position.getY() - worker.getPosition().getY(), 2));
	}
	
	
	//states
	public void explore() {
		
	}
	
	public void recolte(List<Worker> IdleWorker, List<Ressource> visibleRessources) {
		for(Worker worker : IdleWorker) {
			if(visibleRessources.isEmpty() == false) {
				Ressource ressource = visibleRessources.get(0);
				for(Ressource visibleRessource : visibleRessources) {
					int distanceRessource;
					distanceRessource = calculate(ressource.getPosition(), worker);
					if(distanceRessource > calculate(visibleRessource.getPosition(), worker) && visibleRessource.getHp() > 0 )
					{
						ressource = visibleRessource;
					}
				}
				worker.initRessource(visibleRessources.get(0));
				System.out.println("au travail fdp !");
			}
		}
	}
	
	public void prodWorker(List<Worker> botWorkers, int money, List<ProductionBuilding> botProdBuildings, FactionManager factionManager) {
		if(botWorkers.size()<10) {
			if(money>25) {
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
}
