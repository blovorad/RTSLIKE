package engine.entity.building;

import java.util.List;
import configuration.EntityConfiguration;
import engine.Position;
import engine.manager.GraphicsManager;
import engine.map.Tile;
import factionConfiguration.ForFighter;

/**
 * Class du batiment Archery qui etend ProductionBuilding.
 * <p>
 * Elle redefinie les methode de ProductionBuilding afin des produire des archers.
 * @author Maxime Grodet
 * @see ProductionBuilding
 * @see Fighter
 */
public class Archery extends ProductionBuilding{

	/**
	 * Lien vers le model de l'archer afin de recuperer ses attribut.
	 * @see ForFighter
	 */
	private ForFighter archer;
	
	/**
	 * Constructeur de l'archery ou tous ses parametres y sont definis.
	 * @param position Position du batiment.
	 * @param archer Model de l'archer.
	 * @param id Id du batiment.
	 * @param description Desciption du batiment.
	 * @param hpMax Quantite d'hp maximum du batiment.
	 * @param faction Faction du batiment.
	 * @param tile Case ou est le batiment.
	 * @param sightRange Taille du chanmp de vision du batiment.
	 * @param graphicsManager Lien vers le GraphicsManager
	 */
	public Archery(Position position, ForFighter archer, int id, String description, int hpMax, int faction, Tile tile, int sightRange, GraphicsManager graphicsManager) {
		super(position, id, description, hpMax, faction, tile, null, sightRange, graphicsManager);
		this.archer = archer;
		this.setProductionId(EntityConfiguration.ARCHER);
	}

	/**
	 * Methode qui gere la production d'une unite.
	 * @return L'id de l'elemennt en production.
	 */
	@Override
	public int produce() {
		int id = this.getElementCount().get(0);
		this.getElementCount().remove(0);
		if(this.getElementCount().isEmpty()) {
			this.setIsProducing(false);
		}
		else
		{
			this.setTimer(archer.getTimeToBuild());
		}
		//System.out.println("producing archer final");
		
		return id;
	}

	/**
	 * Methode qui gere l'initialisation de la production d'une unite.
	 * @param id Id de l'unite a produire.
	 * @param moneyCount Quantite d'argent disponible.
	 * @return Le prix de l'unite si les conditions sont bonne ou 0.
	 */
	@Override
	public int startProd(int id, int moneyCount) {
		//System.out.println("Start prod de archer, cout : " + this.archer.getCost() + ", gold : " + moneyCount);
		if(this.archer.getCost() <= moneyCount) {
			this.getElementCount().add(id);
			if(this.getIsProducing() == false) {
				this.setTimer(archer.getTimeToBuild());
				this.setIsProducing(true);
			}
			return archer.getCost();
		}
		else {
			//System.out.println("Pas assez de gold !");
			return 0;
		}
	}

	/**
	 * Methode qui enleve des elements dans la file d'attente de production du batiment.
	 * @param searchingUpgrade Liste des upgrades. Unique utilisee dans le HQ et la forge.
	 * @return Le prix de l'unite si les conditions sont bonne ou 0.
	 */
	@Override
	public int removeProduction(List<Integer> searchingUpgrade) {
		if(this.getIsProducing() == true) {
			//System.out.println("Suppression prod de archer, cout : " + this.archer.getCost());
			this.getElementCount().remove(this.getElementCount().size() - 1);
			if(this.getElementCount().isEmpty()) {
				this.setIsProducing(false);
				this.setTimer(0);
			}
			return this.archer.getCost();
		}
		return 0;
	}
}