package engine.entity.building;

import java.util.List;
import configuration.EntityConfiguration;
import engine.Position;
import engine.manager.GraphicsManager;
import engine.map.Tile;
import factionConfiguration.ForFighter;

/**
 * Class du batiment Barrack qui etend ProductionBuilding.
 * <p>
 * Elle redefinie les methode de ProductionBuilding afin des produire des archers.
 * @author Maxime Grodet
 * @see ProductionBuilding
 * @see Fighter
 */
public class Barrack extends ProductionBuilding{

	/**
	 * Lien vers le model de l'infantry afin de recuperer ses attribut.
	 * @see ForFighter
	 */
	private ForFighter infantry;
	
	/**
	 * Constructeur de la barrack ou tous ses parametres y sont definis.
	 * @param position Position du batiment.
	 * @param infantry Model de l'infantry.
	 * @param id Id du batiment.
	 * @param description Desciption du batiment.
	 * @param hpMax Quantite d'hp maximum du batiment.
	 * @param faction Faction du batiment.
	 * @param tile Case ou est le batiment.
	 * @param sightRange Taille du chanmp de vision du batiment.
	 * @param graphicsManager Lien vers le GraphicsManager
	 */
	public Barrack(Position position, ForFighter infantry, int id, String description, int hpMax, int faction, Tile tile, int sightRange, GraphicsManager graphicsManager) {
		super(position, id, description, hpMax, faction, tile, null, sightRange, graphicsManager);
		this.infantry = infantry;
		this.setProductionId(EntityConfiguration.INFANTRY);
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
			this.setTimer(infantry.getTimeToBuild());
		}
		//System.out.println("producing infantry final");
		
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
		//System.out.println("Start prod de infantry, cout : " + this.infantry.getCost() + ", gold : " + moneyCount);
		if(this.infantry.getCost() <= moneyCount) {
			this.getElementCount().add(id);
			if(this.getIsProducing() == false) {
				this.setTimer(infantry.getTimeToBuild());
				this.setIsProducing(true);
			}
			return infantry.getCost();
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
			//System.out.println("Suppression prod de infantry, cout : " + this.infantry.getCost());
			this.getElementCount().remove(this.getElementCount().size() - 1);
			if(this.getElementCount().isEmpty()) {
				this.setIsProducing(false);
				this.setTimer(0);
			}
			return this.infantry.getCost();
		}
		return 0;
	}
}
