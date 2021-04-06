package gui;

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
 *
 */
public class StatEntityContainer {
	private int hp;
	private int quantityRessource;
	private int armor;
	private int damage;
	private int timerForProduction;
	
	public StatEntityContainer() {
		hp = 0;
		quantityRessource = 0;
		armor = 0;
		damage = 0;
	}
	
	public void setForSiteConstruction(SiteConstruction sc) {
		hp = sc.getHp();
	}
	
	public void setForWorker(Worker worker) {
		hp = worker.getHp();
		quantityRessource = worker.getQuantityRessource();
		armor = worker.getArmor();
		damage = worker.getDamage();
	}
	
	public void setForUnit(Unit unit) {
		hp = unit.getHp();
		armor = unit.getArmor();
		damage = unit.getDamage();
	}
	
	public void setForRessource(Ressource ressource) {
		hp = ressource.getHp();
	}
	
	public void setForAttackBuilding(AttackBuilding building) {
		hp = building.getHp();
	}
	
	public void setForStorageBuilding(StorageBuilding building) {
		hp = building.getHp();
	}
	
	public void setForProductionBuilding(ProductionBuilding building) {
		hp = building.getHp();
		timerForProduction = (int)building.getTimer();
	}
	
	public boolean checkChangeForSiteConstruction(SiteConstruction sc) {
		if(hp != sc.getHp()) {
			hp = sc.getHp();
			return true;
		}
		
		return false;
	}
	
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
	
	public boolean checkChangeForRessource(Ressource ressource) {
		if(hp != ressource.getHp()) {
			hp = ressource.getHp();
			return true;
		}
		
		return false;
	}
	
	public boolean checkChangeForAttackBuilding(AttackBuilding building) {
		if(hp != building.getHp()) {
			hp = building.getHp();
			return true;
		}
		
		return false;
	}
	
	public boolean checkChangeForStorageBuilding(StorageBuilding building) {
		if(hp != building.getHp()) {
			hp = building.getHp();
			return true;
		}
		
		return false;
	}
	
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
}
