package gui;

import engine.Entity;
import engine.Ressource;
import engine.entity.building.AttackBuilding;
import engine.entity.building.ProductionBuilding;
import engine.entity.building.SiteConstruction;
import engine.entity.building.StorageBuilding;
import engine.entity.unit.Fighter;
import engine.entity.unit.Unit;
import engine.entity.unit.Worker;
/**
 * 
 * @author gautier
 * this class is use to assure that a description panel won't be actualise to many time, that cause bug
 */
public class StatEntityContainer {
	/**
	 * hp of the entity we will print
	 */
	private int hp;
	/**
	 * quantity ressource of the entity we will print, use if we select a worker
	 */
	private int quantityRessource;
	/**
	 * armor of the entity we will print, use if we select a unit
	 */
	private int armor;
	/**
	 * damage of the entity we will print, use if we select a unit
	 */
	private int damage;
	/**
	 * time remaining before build a unit or upgrade  of the entity we will print, use for productionBuilding
	 */
	private int timerForProduction;
	/**
	 * current entity selected
	 */
	private Entity entity;
	
	public StatEntityContainer() {
		hp = 0;
		quantityRessource = 0;
		armor = 0;
		damage = 0;
		entity = null;
	}
	
	public void setForSiteConstruction(SiteConstruction sc) {
		hp = sc.getHp();
		entity = sc;
	}
	
	public void setForWorker(Worker worker) {
		hp = worker.getHp();
		quantityRessource = worker.getQuantityRessource();
		armor = worker.getArmor();
		damage = worker.getDamage();
		entity = worker;
	}
	
	public void setForUnit(Unit unit) {
		hp = unit.getHp();
		armor = unit.getArmor();
		damage = unit.getDamage();
		entity = unit;
	}
	
	public void setForRessource(Ressource ressource) {
		hp = ressource.getHp();
		entity = ressource;
	}
	
	public void setForAttackBuilding(AttackBuilding building) {
		hp = building.getHp();
		entity = building;
	}
	
	public void setForStorageBuilding(StorageBuilding building) {
		hp = building.getHp();
		entity = building;
	}
	
	public void setForProductionBuilding(ProductionBuilding building) {
		hp = building.getHp();
		entity = building;
		timerForProduction = (int)building.getTimer();
	}
	
	/**
	 * actualize the panel if change are spot
	 * @param sc current siteConstruction
	 * @return true if change are effective
	 */
	public boolean checkChangeForSiteConstruction(SiteConstruction sc) {
		if(hp != sc.getHp()) {
			hp = sc.getHp();
			return true;
		}
		
		return false;
	}
	
	/**
	 * actualize  panel if change are spot
	 * @param fighter current fighter
	 * @return true if change are effective
	 */
	public boolean checkChangeForFighter(Fighter fighter) {
		if(hp != fighter.getHp()) {
			hp = fighter.getHp();
			return true;
		}
		else if(armor != fighter.getArmor()) {
			armor = fighter.getArmor();
			return true;
		}
		else if(damage != fighter.getDamage()) {
			damage = fighter.getDamage();
			return true;
		}
		
		return false;
	}
	
	/**
	 * actualize  panel if change are spot
	 * @param ressource current ressource
	 * @return true if change are effective
	 */
	public boolean checkChangeForRessource(Ressource ressource) {
		if(hp != ressource.getHp()) {
			hp = ressource.getHp();
			return true;
		}
		
		return false;
	}
	
	/**
	 * actualize  panel if change are spot
	 * @param building current building
	 * @return true if change are effective
	 */
	public boolean checkChangeForAttackBuilding(AttackBuilding building) {
		if(hp != building.getHp()) {
			hp = building.getHp();
			return true;
		}
		
		return false;
	}
	
	/**
	 * actualize  panel if change are spot
	 * @param building current building
	 * @return true if change are effective
	 */
	public boolean checkChangeForStorageBuilding(StorageBuilding building) {
		if(hp != building.getHp()) {
			hp = building.getHp();
			return true;
		}
		
		return false;
	}
	
	/**
	 * actualize  panel if change are spot
	 * @param building current building
	 * @return true if change are effective
	 */
	public boolean checkChangeForProductionBuilding(ProductionBuilding building) {
		if(hp != building.getHp()) {
			hp = building.getHp();
			return true;
		}
		else if(timerForProduction != (int)building.getTimer()) {
			timerForProduction = (int)building.getTimer();
			return true;
		}
		
		return false;
	}
	
	/**
	 * actualize  panel if change are spot
	 * @param worker current worker
	 * @return true if change are effective
	 */
	public boolean checkChangeForWorker(Worker worker) {
		if(hp != worker.getHp()) {
			hp = worker.getHp();
			return true;
		}
		else if(quantityRessource != worker.getQuantityRessource()) {
			quantityRessource = worker.getQuantityRessource();
			return true;
		}
		else if(armor != worker.getArmor()) {
			armor = worker.getArmor();
			return true;
		}
		else if(damage != worker.getDamage()) {
			damage = worker.getDamage();
			return true;
		}
		
		return false;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
}