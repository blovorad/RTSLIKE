package engine.entity.unit;

import java.util.List;

import configuration.EntityConfiguration;

import engine.Position;
import engine.Ressource;
import engine.entity.building.StorageBuilding;
import engine.manager.GraphicsManager;
import engine.map.Fog;
import engine.map.Map;
import engine.math.Collision;


/**
 * 
 * create: 16/02/2021
 * @author girard
 * @version: 21/04/2021
 *
 */

public class Worker extends Unit
{	
	/**
	 * storageBuilding, les bâtiments de stockage le plus proche que le worker conserve pour apporter les ressources
	 * ressource, la ressource que le worker conserve pour pouvoir toujours y retourner quand il récolte 
	 * quantityRessource, la quantité de ressources que possède le worker sur lui
	 * ressourcesMax,  le max de ressource que le worker peut porter sur lui
	 * harvestSpeed, la vitesse de récolte
	 * repairSpeed, la vitesse de réparation
	 */
	private StorageBuilding storageBuilding;
	
	private Ressource ressource;
	
	private int quantityRessource;
	private int ressourcesMax;
	private int harvestSpeed;
	private int repairSpeed;
	
	
	public Worker (int hp, int currentAction, int attackRange, int attackSpeed, int maxSpeed, int damage, int armor, int repairSpeed, Position position, int id, String description, int hpMax, int faction, Position destination, int harvestSpeed, int ressourceMax, int sightRange, int maxFrame, GraphicsManager graphicsManager)
	{	
		super( hp, currentAction, attackRange, attackSpeed, maxSpeed, damage, armor, position, id, description, destination, hpMax, faction, sightRange, maxFrame, graphicsManager, EntityConfiguration.PASSIF_STATE);
		this.repairSpeed = repairSpeed;
		this.harvestSpeed = harvestSpeed;
		
		this.ressourcesMax = ressourceMax;
		this.quantityRessource = 0;
		
		this.storageBuilding = null;
	}
	
	/**
	 * Fonction qui execute la récolte de ressources. Enlève des hp a la ressource,
	 * et donne des ressources aux workers.
	 */
	
	public void toHarvest()
	{
		if(this.ressource != null)
		{
			if( this.getTimer() <= 0)
			{
				this.setTimer(harvestSpeed);
				if(this.ressource.getHp() > 0)
				{
					this.ressource.setHp(this.ressource.getHp() -1);
					this.quantityRessource ++;
				}	

				if(this.ressource.getHp() <= 0)
				{
					this.ressource = null;
				}
			}
			this.setTimer(this.getTimer() - 1);
		}
	}
	/**
	 *Fonction qui permet d'exécuter la réparation des bâtiments, 
	 *redonne hp aux bâtiments.
	 */
	public void toRepair()
	{
		if(this.getTarget() != null)
		{
			if(this.getTimer() <= 0)
			{
				if(this.getTarget().getHp() < this.getTarget().getHpMax()) {
					this.getTarget().setHp(((this.getTarget().getHp()) + 1));
				}
				
				if(this.getTarget().getHp() >= this.getTarget().getHpMax() || this.getTarget().getHp() <= 0)
				{
					this.setTarget(null);
					this.setCurrentAction(EntityConfiguration.IDDLE);
				}
				this.setTimer(this.repairSpeed);
			}
			this.setTimer(this.getTimer() - 1);
		}
	}
	
	/**
	 * Fonction qui met à jour les workers, qui permet d'executer toutes leurs actions
	 * @param ressources, tableau des ressources se trouvant sur la mappe
	 * @param storageBuildings, tableau des batiments de récolte se trouvant sur la map
	 * @param units, tableau des unites se trouvant sur la map
	 * @param playerFog
	 * @param map
	 */
	public void update(List<Ressource> ressources, List<StorageBuilding> storageBuildings, List<Unit> units, Fog playerFog, Map map)
	{
		super.update(units, playerFog, map);
		if(this.getCurrentAction() == EntityConfiguration.HARVEST && this.getTarget() != null) {
			if(this.getTarget().getId() == EntityConfiguration.SITE_CONSTRUCTION) {
				this.setCurrentAction(EntityConfiguration.WALK);
			}
		}
		
		if(this.getCurrentAction() == EntityConfiguration.HARVEST)
		{
			// Va au batiments quand il a les ressources max
			if(this.quantityRessource == this.ressourcesMax)	
			{
				if(this.storageBuilding == null)
				{
					this.nearbyStorage(storageBuildings);
					this.setTarget(storageBuilding);
				}
				
				if(this.storageBuilding != null && Collision.collideUnit(this.getTarget().getPosition(), this))
				{
					this.storageBuilding.addRessource(this.quantityRessource);
					this.storageBuilding = null;
					this.quantityRessource = 0;
				}
			}
			
			// met la ressource a null quand fini
			else if(this.getRessource() != null && this.getRessource().getHp() <= 0)
			{
				this.ressource = null;
			}
			
			
			//cherche une nouvelle ressources si il a finis la sienne 
			else if(this.ressource == null && !ressources.isEmpty())
			{
				this.ressource = null;
				nearbyResource(ressources);
				if(this.ressource == null) {
					if(this.quantityRessource > 0) {
						nearbyStorage(storageBuildings);
						this.setTarget(this.storageBuilding);
					}
					this.setCurrentAction(EntityConfiguration.IDDLE);
				}
				else {
					this.setTarget(this.ressource);
				}
			}
			// récupère ressources
			 else if(this.ressource != null && Collision.collideUnit(this.ressource.getPosition(), this) && this.ressource.getHp() > 0)
			{
				this.getSpeed().reset();
				this.toHarvest();
			} 
			
			
			// revien a la ressource quand posse ces ressources
			else if(this.ressource != null && this.quantityRessource != this.ressourcesMax){
				this.setTarget(ressource);
			}		
		}
		
		// Pose ces ressources si il en a et si on click sur un batiment de stockage
		else if(this.getTarget() != null && this.getTarget().getId() == EntityConfiguration.STORAGE && this.quantityRessource != 0 && Collision.collideUnit(this.getTarget().getPosition(), this))
		{
			nearbyStorage(storageBuildings);
			this.storageBuilding.addRessource(this.quantityRessource);
			storageBuildings = null;
			this.quantityRessource = 0;
			this.storageBuilding = null;
			this.setTarget(null);
			this.setCurrentAction(EntityConfiguration.IDDLE);
		}	
		
		//réparee les batiments
		else if(this.getTarget() != null && this.getTarget().getFaction() == this.getFaction())
		{
			if(Collision.collideUnit(this.getTarget().getPosition(), this))
			{
				this.setCurrentAction(EntityConfiguration.REPAIR);
				this.toRepair();
				this.getSpeed().reset();
			}
			
			this.checkTarget();
			
			if(this.getTarget() == null)
			{
				this.setCurrentAction(EntityConfiguration.IDDLE);
			}
		}
		
		
	}
	
	/**
	 * Fonction qui permet de trouver la ressource la plus proche du worker sur la map 
	 * @param ressources, tableau de toutes les ressources se trouvant sur la map
	 */
	public void nearbyResource(List<Ressource> ressources)
	{
		if(!ressources.isEmpty())
		{
			this.ressource = ressources.get(0);
			int distanceRessource;
			
			for(Ressource value: ressources)
			{
				distanceRessource = calculate(this.ressource.getPosition());
				if(distanceRessource > calculate(value.getPosition()) && value.getHp() > 0 )
				{
					this.ressource = value;
				}
			}
			
			if(!Collision.collideRessource(this, this.ressource))
			{
				this.ressource = null;
			}
		}
	}
	
	/**
	 * Fonction qui permet de trouver le batiment de ressources le plus proche du worker
	 * @param storageBuildings, tableau de toutes les storageBuildings de la map
	 */
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
		else {
			this.setCurrentAction(EntityConfiguration.IDDLE);
		}
	}
	
	/**
	 *  Fonction qui attribue au worker selectionne par le joueur la ressource selectionnee par le joueur
	 * @param ressource
	 */
	public void initRessource(Ressource ressource)
	{
		this.ressource = ressource;
		this.calculateSpeed(this.ressource.getPosition());
		this.setCurrentAction(EntityConfiguration.HARVEST);
		this.setTarget(ressource);
	}
	
	/**
	 * Tous les geteur et seteur pour les attributs de la classe
	 * @return
	 */
	
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
	
	public int getQuantityRessource() {
		return this.quantityRessource;
	}
}