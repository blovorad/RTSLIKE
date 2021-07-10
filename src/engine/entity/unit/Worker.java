package engine.entity.unit;

import java.util.ArrayList;
import java.util.List;

import configuration.EntityConfiguration;

import engine.Position;
import engine.Ressource;
import engine.entity.building.Building;
import engine.entity.building.SiteConstruction;
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
	private SiteConstruction siteConstruction;
	
	private Ressource ressource;
	
	private Building building;
	private int quantityRessource;
	private int ressourcesMax;
	private int harvestSpeed;
	private int repairSpeed;
	private int harvestDamage;
	private int repairDamage;
	
	
	public Worker (int hp, int currentAction, int attackRange, int attackSpeed, int maxSpeed, int damage, int harvestDamage, int repairDamage, int armor, int repairSpeed, Position position, int id, String description, int hpMax, int faction, Position destination, int harvestSpeed, int ressourceMax, int sightRange, int maxFrame, GraphicsManager graphicsManager)
	{	
		super( hp, currentAction, attackRange, attackSpeed, maxSpeed, damage, armor, position, id, description, destination, hpMax, faction, sightRange, maxFrame, graphicsManager);
		this.repairSpeed = repairSpeed;
		this.harvestSpeed = harvestSpeed;
		this.harvestDamage = harvestDamage;
		this.repairDamage = repairDamage;
		
		this.ressourcesMax = ressourceMax;
		this.quantityRessource = 0;
		
		this.storageBuilding = null;
		this.siteConstruction = null;
		this.building = null;
		this.ressource = null;
	}
	
	/**
	 * Fonction qui execute la récolte de ressources. Enlève des hp a la ressource,
	 * et donne des ressources aux workers.
	 */
	
	public void harvest()
	{
		if(this.getTimer() <= 0){
			this.setTimer(harvestSpeed);
			if(this.ressource.getHp() >= this.harvestDamage){
				this.quantityRessource += this.harvestDamage;
				this.ressource.setHp(this.ressource.getHp() - this.harvestDamage);
			}
			else {
				this.quantityRessource += this.ressource.getHp();
				this.ressource.setHp(0);
			}
		}
		this.setTimer(this.getTimer() - 1);
	}
	/**
	 *Fonction qui permet d'exécuter la réparation des bâtiments, 
	 *redonne hp aux bâtiments.
	 */
	public void repair()
	{
		if(this.getTimer() <= 0)
		{
			if(this.building.getHpMax() - this.building.getHp() >= this.repairDamage) {
				this.building.setHp(this.building.getHp() + this.repairDamage);
			}
			else {
				this.building.setHp(this.building.getHpMax());
			}
			this.setTimer(this.repairSpeed);
		}
		this.setTimer(this.getTimer() - 1);
	}
	
	public void construct() {
		if(this.getTimer() <= 0)
		{
			if(this.siteConstruction.getHpMax() - this.siteConstruction.getHp() >= this.repairDamage) {
				this.siteConstruction.setHp(this.siteConstruction.getHp() + this.repairDamage);
			}
			else {
				this.siteConstruction.setHp(this.siteConstruction.getHpMax());
			}
			this.setTimer(this.repairSpeed);
		}
		this.setTimer(this.getTimer() - 1);
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
		
		if(this.getCurrentAction() == EntityConfiguration.HARVEST){
			if(this.quantityRessource >= this.ressourcesMax || this.storageBuilding != null)	{
				if(this.storageBuilding == null){
					this.nearbyStorage(storageBuildings);
					this.setTarget(storageBuilding);
					this.setFinalDestination(storageBuilding.getPosition());
				}
				
				if(this.storageBuilding != null && Collision.collideUnit(this.storageBuilding.getPosition(), this)){
					this.storageBuilding.addRessource(this.quantityRessource);
					this.storageBuilding = null;
					this.quantityRessource = 0;
					if(ressource != null) {
						this.setTarget(ressource);
						this.setFinalDestination(ressource.getPosition());
					}
				}
			}
			else {
				if(ressource == null) {
					boolean foundRessource = nearbyRessource(ressources, map);
					if(foundRessource == false) {
						this.ressource = null;
						setCurrentAction(EntityConfiguration.IDDLE);
					}
					else {
						this.setTarget(ressource);
						this.setFinalDestination(ressource.getPosition());
					}
				}
				else {
					if(ressource.getHp() <= 0) {
						ressource = null;
					}
					else if(Collision.collideUnit(ressource.getPosition(), this)) {
						if(getSpeed().hasSpeed()) {
							getSpeed().reset();
						}
						harvest();
					}
				}
			}
		}
		else if(this.getCurrentAction() == EntityConfiguration.REPAIR) {
			if(Collision.collideUnit(this.building.getPosition(), this)) {
				if(getSpeed().hasSpeed()) {
					getSpeed().reset();
				}
				repair();
				if(building.getHp() >= building.getHpMax()) {
					this.setTarget(null);
					this.building = null;
					this.setCurrentAction(EntityConfiguration.IDDLE);
				}
				else if(building.getHp() <= 0) {
					this.setFinalDestination(null);
					this.setTarget(null);
					this.building = null;
					this.setCurrentAction(EntityConfiguration.IDDLE);
				}
			}
			else {
				if(building.getHp() <= 0) {
					this.setFinalDestination(null);
					this.setTarget(null);
					this.building = null;
					this.setCurrentAction(EntityConfiguration.WALK);
				}
				else if(building.getHp() >= building.getHpMax()) {
					this.building = null;
					this.setTarget(null);
					this.setCurrentAction(EntityConfiguration.WALK);
				}
			}
		}
		else if(this.getCurrentAction() == EntityConfiguration.CONSTRUCT) {
			if(Collision.collideUnit(this.siteConstruction.getPosition(), this)) {
				if(getSpeed().hasSpeed()) {
					getSpeed().reset();
				}
				construct();
				if(siteConstruction.getHp() >= siteConstruction.getHpMax()) {
					if(siteConstruction.getBuildingId() == EntityConfiguration.STORAGE) {
						this.setCurrentAction(EntityConfiguration.HARVEST);
						boolean foundRessource = nearbyRessource(ressources, map);
						if(foundRessource == false) {
							this.ressource = null;
							setCurrentAction(EntityConfiguration.IDDLE);
						}
						else {
							this.setTarget(ressource);
							this.setFinalDestination(ressource.getPosition());
						}
					}
					else {
						this.setCurrentAction(EntityConfiguration.IDDLE);
					}
					this.setTarget(null);
					this.siteConstruction = null;
				}
				else if(siteConstruction.getHp() <= 0) {
					this.setTarget(null);
					this.siteConstruction = null;
					this.setCurrentAction(EntityConfiguration.IDDLE);
				}
			}
			else {
				if(siteConstruction.getHp() <= 0) {
					this.setTarget(null);
					this.siteConstruction = null;
					this.setCurrentAction(EntityConfiguration.WALK);
				}
				else if(siteConstruction.getHp() >= siteConstruction.getHpMax()) {
					this.setTarget(null);
					this.siteConstruction = null;
					this.setCurrentAction(EntityConfiguration.WALK);
				}
			}
		}
	}
	
	/**
	 * Fonction qui permet de trouver la ressource la plus proche du worker sur la map 
	 * @param ressources, tableau de toutes les ressources se trouvant sur la map
	 */
	public boolean nearbyRessource(List<Ressource> ressources, Map map){
		if(ressources.isEmpty() == false){
			List<Ressource> accessible = new ArrayList<Ressource>();
			for(Ressource ressource : ressources) {
				accessible.add(ressource);
			}

			int distanceRessource;
			boolean notFound = false;
			while(notFound == false && accessible.isEmpty() == false) {
				this.ressource = accessible.get(0);
				for(Ressource value: accessible){
					distanceRessource = calculate(this.ressource.getPosition());
					if(distanceRessource > calculate(value.getPosition()) && value.getHp() > 0 ){
						this.ressource = value;
					}
				}
				this.setFinalDestination(ressource.getPosition());
				if(this.getFinalNode() == null) {
					notFound = true;
				}
				else {
					notFound = this.generatePath(map);
				}
				this.setFinalDestination(null);
				accessible.remove(this.ressource);
			}
			if(ressource != null && Collision.collideRessource(this, this.ressource) == false){
				return false;
			}
			return true;
		}
		return false;
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
		this.setFinalDestination(ressource.getPosition());
		this.setCurrentAction(EntityConfiguration.HARVEST);
		this.setTarget(ressource);
		this.setTargetUnit(null);
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
	
	public void setSiteConstruction(SiteConstruction siteConstruction) {
		this.siteConstruction = siteConstruction;
	}
	
	public SiteConstruction getSiteConstruction() {
		return this.siteConstruction;
	}
	
	public void setBuilding(Building building) {
		this.building = building;
	}
	
	public Building getBuilding() {
		return this.building;
	}

}