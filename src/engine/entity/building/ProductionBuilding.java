package engine.entity.building;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Entity;
import engine.Position;
import engine.manager.GraphicsManager;
import engine.map.Tile;
import factionConfiguration.ForUpgrade;

/**
 * Class qui definie les batiment de production et etend Entity.
 * @author Maxime Grodet
 * @see Entity
 *
 */
public abstract class ProductionBuilding extends Entity{

	/**
	 * Liste d'entier qui represente la file de production.
	 */
	private List<Integer> elementCount;
	
	/**
	 * Entier qui represente l'id du l'element en cours de production.
	 */
	private int productionId;
	
	/**
	 * Timer
	 */
	private float timer;
	
	/**
	 * Boolean true lorsqu'un batiment est en production.
	 */
	private boolean isProducing;
	
	/**
	 * Case ou se situe le batiment.
	 */
	private Tile tile;
	
	/**
	 * HashMap des upgrade a produire dans un batiment.
	 */
	private AbstractMap<Integer, ForUpgrade> upgrades;
	
	/**
	 * Constructeur d'un batiment de production ou toutes ses variables son definies.
	 * @param position Position du batiment.
	 * @param id Id du batiment.
	 * @param description Desciption du batiment. 
	 * @param hpMax Quantite d'hp maximum du batiment.
	 * @param faction Faction du batiment.
	 * @param tile Case ou est le batiment.
	 * @param sightRange Taille du chanmp de vision du batiment.
	 * @param graphicsManager Lien vers le GraphicsManager
	 */
	public ProductionBuilding(Position position, int id, String description, int hpMax, int faction, Tile tile, AbstractMap<Integer, ForUpgrade> upgrades, int sightRange, GraphicsManager graphicsManager) {
		super(hpMax, hpMax, description , position, id, faction, sightRange, 0, graphicsManager);
		elementCount = new ArrayList<Integer>();
		this.setTile(tile);
		this.setUpgrades(upgrades);
		if(upgrades == null) {
			this.upgrades = new HashMap<Integer, ForUpgrade>();
		}
	}
	
	/**
	 * Methode apelle dans EntitiesManager et executee a chaque iteration du jeu.
	 * <p>
	 * Elle gere le comportement du batiment et met a jour ses variables.
	 * @param popCount Population actuelle.
	 * @param maxPop Population maximale.
	 */
	public void update(int popCount, int maxPop) {
		super.update();
		if(this.getId() == EntityConfiguration.FORGE) {
			timer -= 1.0 / GameConfiguration.GAME_SPEED;
			if(this.getProductionId() == EntityConfiguration.ARMOR_UPGRADE) {
				System.out.println("updating armor upgrade production time remaning : " + timer);
			}
		}
		if(timer > 0) {
			if(popCount < maxPop) {
				timer -= 1.0 / GameConfiguration.GAME_SPEED;
				/*if(this.getProductionId() == EntityConfiguration.INFANTRY) {
					System.out.println("updating infantry production time remaning : " + timer);
				}
				else if(this.getProductionId() == EntityConfiguration.ARCHER) {
					System.out.println("updating archer production time remaning : " + timer);
				}
				else if(this.getProductionId() == EntityConfiguration.CAVALRY) {
					System.out.println("updating cavalry production time remaning : " + timer);
				}
				else if(this.getProductionId() == EntityConfiguration.SPECIAL_UNIT) {
					System.out.println("updating special production time remaning : " + timer);
				}
				else if(this.getProductionId() == EntityConfiguration.WORKER) {
					System.out.println("updating worker production time remaning : " + timer);
				}
				else {
					System.out.println("Invalid id");
				}*/
			}
		}
	}
	
	/**
	 * Methode qui gere la production d'une unite.
	 * @return L'id de l'elemennt en production.
	 */
	public abstract int produce();
	
	/**
	 * Methode qui gere l'initialisation de la production d'une unite.
	 * @param id Id de l'unite a produire.
	 * @param moneyCount Quantite d'argent disponible.
	 * @return Le prix de l'unite si les conditions sont bonne ou 0.
	 */
	public abstract int startProd(int id, int moneyCount);
	
	/**
	 * Methode qui enleve des elements dans la file d'attente de production du batiment.
	 * @param searchingUpgrade Liste des upgrades. Unique utilisee dans le HQ et la forge.
	 * @return Le prix de l'unite si les conditions sont bonne ou 0.
	 */
	public abstract int removeProduction(List<Integer> searchingUpgrade);
	
	/**
	 * Methode qui prepare un batiment a etre supprimer
	 */
	public void remove() {
		timer = 0;
		isProducing = false;
		elementCount.clear();
	}

	public List<Integer> getElementCount() {
		return elementCount;
	}

	public void setElementCount(List<Integer> elementCount) {
		this.elementCount = elementCount;
	}

	public int getProductionId() {
		return productionId;
	}

	public void setProductionId(int productionId) {
		this.productionId = productionId;
	}

	public float getTimer() {
		return timer;
	}

	public void setTimer(float timer) {
		this.timer = timer;
	}

	public boolean getIsProducing() {
		return isProducing;
	}

	public void setIsProducing(boolean isProducing) {
		this.isProducing = isProducing;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public AbstractMap<Integer, ForUpgrade> getUpgrades() {
		return upgrades;
	}

	public void setUpgrades(AbstractMap<Integer, ForUpgrade> upgrades) {
		this.upgrades = upgrades;
	}
	
}