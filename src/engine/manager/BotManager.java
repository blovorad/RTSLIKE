package engine.manager;

import java.util.List;

import configuration.GameConfiguration;
import engine.Entity;
import engine.Position;
import engine.Ressource;
import engine.entity.building.AttackBuilding;
import engine.entity.building.ProductionBuilding;
import engine.entity.building.StorageBuilding;
import engine.entity.unit.Fighter;
import engine.entity.unit.Worker;
import engine.map.Fog;

public class BotManager {
	
	private boolean barrackBuild;
	private boolean archeryBuild;
	private boolean stableBuild;
	private boolean castleBuild;
	private boolean forgeBuild;
	private boolean hqBuild;
	private Fog fog;
	
	public BotManager() {
		barrackBuild = false;
		archeryBuild = false;
		stableBuild = false;
		castleBuild = false;
		forgeBuild = false;
		hqBuild = false;
		
		fog = new Fog(GameConfiguration.LINE_COUNT, GameConfiguration.COLUMN_COUNT);
	}
	
	public void update(List<Entity> botEntities, List<StorageBuilding>botStorageBuildings, List<AttackBuilding> botAttackBuildings, List<ProductionBuilding> botProdBuildings, List<Worker> botWorkers, List<Fighter> botFighters, List<Ressource> ressources) {
		updateFog(botEntities);
		
	}
	
	public void updateFog(List<Entity> botEntities) {
		for(Entity entity : botEntities) {
			Position p = entity.getPosition();
			fog.clearFog(p.getX() - entity.getSightRange() / 6, p.getY() - entity.getSightRange() / 6, entity.getSightRange());
		}
	}

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
}
