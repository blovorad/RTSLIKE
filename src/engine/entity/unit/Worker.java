package engine.entity.unit;

import java.util.List;

import configuration.EntityConfiguration;

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
		System.out.println("maxspeed worker : " + maxSpeed);
	}
	
	public void toHarvest()
	{
		if(this.ressource != null)
		{
			if( timer == 0)
			{
				
				if(this.ressource.getHp() > 0)
				{
					this.ressource.setHp(this.ressource.getHp() -1);
					this.quantityRessource ++;
					System.out.println("Mes resources sont a: " + this.quantityRessource);
					
					timer = this.harvestSpeed;
				}	

					if(this.ressource.getHp() <= 0)
					{
						this.ressource = null;
					}
				
				
				
				
			}
			this.timer--;
		}
	}
	
	public void toRepair()
	{
		if(this.getTarget() != null)
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
		
	}
	
	public void vision()
	{
		
	}
	
	/*public int distance(Position a, Position b)
	{
		return Math.pow(a.getX(), 2);
	}
	*/
	public void update(List<Ressource> ressources, List<StorageBuilding> storageBuildings)
	{
		super.update();
		/*if( this.getDestination() != this.getDestination() != this.ressource.getPosition())
		{
			
		}*/
		
		if(this.getTarget() != null && this.ressource == null && this.getTarget().getId() == EntityConfiguration.RESSOURCE && Collision.collideUnit(this.getTarget().getPosition(), this))
		{
			System.out.println("1");
			nearbyResource(ressources);
			this.setTarget(ressource);
		}
		
		else if(this.getRessource() != null && this.getRessource().getHp() <= 0)
		{
			System.out.println("2");
			//nearbyResource(ressources);
			this.ressource = null;
		}
		
		// revien a la ressource quand posse ces ressources
		else if(this.ressource != null && this.getTarget() == this.storageBuilding && this.quantityRessource != this.ressourcesMax)
		{
			System.out.println("3");
			this.setTarget(ressource);
		}
		
		// cherche un batiment de stockage si jamais il en a pas
		else if(this.storageBuilding == null && this.getRessource() != null && Collision.collideUnit(this.ressource.getPosition(), this))
		{
			System.out.println("4");
			nearbyStorage(storageBuildings);
		}
		
		// Va au batiments quand il a les ressources max
		else if(this.storageBuilding != null && this.quantityRessource == this.ressourcesMax)	
		{
			System.out.println("5");
			this.setTarget(storageBuilding);
			if(Collision.collideUnit(this.getTarget().getPosition(), this))
			{
				System.out.println("1");
				this.storageBuilding.addRessource(this.quantityRessource);
				this.quantityRessource = 0;
			}
		}
		
		//cherche une nouvelle ressources si il a finis la sienne 
		else if(this.ressource == null && this.getCurrentAction() == 1 && !ressources.isEmpty())
		{
			System.out.println("6");
			System.out.println("Salut");
			this.ressource = null;
			nearbyResource(ressources);
		}
		
		// récupère ressources
		else if(this.ressource != null && this.storageBuilding != null && Collision.collideUnit(this.ressource.getPosition(), this))
		{
			System.out.println("7");
			this.toHarvest();
			this.setCurrentAction(1);
			System.out.println(this.ressource);
		} 
		
		//réparee les batiments
		else if(this.getTarget() != null && this.getTarget().getFaction() == EntityConfiguration.PLAYER_FACTION && this.getTarget().getHp() < this.getTarget().getHpMax() && Collision.collideUnit(this.getTarget().getPosition(), this))
		{
			System.out.println("8");
			this.toRepair();	
		}
		else if(this.getCurrentAction() == 1 && storageBuilding != null && this.ressource == null && this.quantityRessource > 0)
		{
			this.setTarget(storageBuilding);
			if(Collision.collideUnit(this.getTarget().getPosition(), this))
			{
				System.out.println("1");
				this.storageBuilding.addRessource(this.quantityRessource);
				this.quantityRessource = 0;
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
			
			System.out.println("Bonjour");
			
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
