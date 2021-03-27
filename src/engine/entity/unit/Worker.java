package engine.entity.unit;

import java.util.List;

import configuration.EntityConfiguration;
import engine.Entity;
import engine.Position;
import engine.Ressource;
import engine.entity.building.StorageBuilding;
import engine.manager.GraphicsManager;
import engine.math.Collision;


/**
 * 
 * create: 16/02/2021
 * @author girard
 * @version: 9/03/2021
 *
 */

public class Worker extends Unit
{	
	private StorageBuilding storageBuilding;
	
	private Ressource ressource;
	
	private int quantityRessource;
	private int ressourcesMax;
	private int harvestSpeed;
	private int repairSpeed;
	private int timer;
	
	
	public Worker (int hp, int currentAction, int attackRange, int attackSpeed, int maxSpeed, int damage, int range, int armor, int repairSpeed, Position position, int id, String description, int hpMax, int faction, Position destination, int harvestSpeed, int ressourceMax, int sightRange, int maxFrame, GraphicsManager graphicsManager)
	{	
		super( hp, currentAction, attackRange, attackSpeed, maxSpeed, damage, range, armor, position, id, description, hpMax, faction, sightRange, maxFrame, graphicsManager);
		this.repairSpeed = repairSpeed;
		this.harvestSpeed = harvestSpeed;
		
		this.timer = harvestSpeed;
		this.ressourcesMax = ressourceMax;
		this.quantityRessource = 0;
		
		this.storageBuilding = null;
	}
	
	public void toHarvest()
	{
		if( timer == 0)
		{
			if(this.ressource.getHp() > 0)
			{
				this.ressource.setHp(this.ressource.getHp() -1);
				this.quantityRessource ++;
				timer = this.harvestSpeed;
			}	

			if(this.ressource.getHp() <= 0)
			{
				this.ressource = null;
			}
		}
		this.timer--;
	}
	
	public void toRepair()
	{
		if(this.timer == 0)
		{
			this.getTarget().setHp(((this.getTarget().getHp()) + 1));
				
			if(this.getTarget().getHp() == this.getTarget().getHpMax())
			{
				this.setTarget(null);
			}
			this.timer = this.harvestSpeed;
		}
		this.timer--;	
	}

	public void update(List<Ressource> ressources, List<StorageBuilding> storageBuildings)
	{
		super.update();
		
		if(this.getCurrentAction() == EntityConfiguration.HARVEST) {
			// Va au batiments quand il a les ressources max
			if(this.quantityRessource == this.ressourcesMax)	
			{
				System.out.println("pourquoi 1");
				if(this.storageBuilding == null) {
					System.out.println("pourquoi 2");
					this.nearbyStorage(storageBuildings);
					this.setTarget(storageBuilding);
				}
				if(Collision.collideUnit(this.getTarget().getPosition(), this))
				{
					System.out.println("pourquoi 3");
					this.storageBuilding.addRessource(this.quantityRessource);
					this.storageBuilding = null;
					this.quantityRessource = 0;
				}
			}
			
			//récupère ressources, en gros on mine
			else if(this.ressource != null && Collision.collideUnit(this.ressource.getPosition(), this))
			{
				System.out.println("L");
				this.getSpeed().reset();
				this.toHarvest();
			} 
			
			//pose les ressources au batiment et repart a sa ressources
			else if(storageBuilding != null)
			{
				this.setTarget(storageBuilding);
				if(Collision.collideUnit(this.getTarget().getPosition(), this))
				{
					this.storageBuilding.addRessource(this.quantityRessource);
					this.quantityRessource = 0;
					this.setTarget(ressource);
				}
			}
			//si sa ressource est morte
			if(this.getRessource() == null) {
				nearbyResource(ressources);
				initRessource(this.ressource);
			}
			else if(ressource.getHp() <= 0){
				this.ressource = null;
				nearbyResource(ressources);
				initRessource(this.ressource);
			}
		}
		else if(this.getCurrentAction() == EntityConfiguration.REPAIR) {
			if(this.getTarget().getHp() <= 0) {
				this.setTarget(null);
				this.getSpeed().reset();
			}
			else if(this.getTarget().getHp() < this.getTarget().getHpMax() && Collision.collideUnit(this.getTarget().getPosition(), this))
			{
				this.getSpeed().reset();
				this.toRepair();	
			}
		}
	}
	
	public void nearbyResource(List<Ressource> ressources)
	{
		if(!ressources.isEmpty())
		{
			this.ressource = ressources.get(0);
			int distanceRessource;
			
			for(Ressource value: ressources)
			{
				distanceRessource = calculate(this.ressource.getPosition());
				if(distanceRessource > calculate(value.getPosition()))
				{
					this.ressource = value;
				}
			}
		}
	}
	
	public void nearbyStorage(List<StorageBuilding> storageBuildings)
	{
		if(!storageBuildings.isEmpty())
		{
			this.storageBuilding = storageBuildings.get(0);
			int distanceStorageBuilding;
			
			for(StorageBuilding value: storageBuildings)
			{
				distanceStorageBuilding = calculate(this.storageBuilding.getPosition());
				if(distanceStorageBuilding > calculate(value.getPosition()))
				{
					this.storageBuilding = value;
				}
			}
		}
	}
	
	public void initRessource(Ressource ressource)
	{
		this.ressource = ressource;
		this.calculateSpeed(ressource.getPosition());
		this.setTarget(ressource);
		this.setCurrentAction(EntityConfiguration.HARVEST);
	}
	
	public void goToRepair(Entity target) {
		this.setCurrentAction(EntityConfiguration.REPAIR);
		this.calculateSpeed(target.getPosition());
		this.setTarget(target);
	}
	
	public int calculateTimer()
	{
		return this.harvestSpeed;
	}
	
	public int calculate(Position position)
	{
		return (int) Math.sqrt(Math.pow(position.getX() - this.getPosition().getX(), 2) + Math.pow(position.getY() - this.getPosition().getY(), 2));
	}

	public int getHarvestSpeed()
	{
		return this.harvestSpeed;
	}
	
	public void setHarvestSpeed(int harvestSpeed)
	{
		this.harvestSpeed = harvestSpeed;
	}
	
	public int gatQuantityRessource()
	{
		return this.quantityRessource;
	}
	public int getRepair() 
	{
		return this.repairSpeed;
	}

	public void setRepair(int repairSpeed) 
	{
		this.repairSpeed = repairSpeed;
	}
	
	public Ressource getRessource()
	{
		return this.ressource;
	}
	
	public void setRessource(Ressource ressource)
	{
		this.ressource = ressource;
	}
	
	public StorageBuilding getStorageBuilding()
	{
		return this.storageBuilding;
	}
	
	public void setStorageBuilding(StorageBuilding storageBuilding)
	{
		this.storageBuilding = storageBuilding;
	}
}
