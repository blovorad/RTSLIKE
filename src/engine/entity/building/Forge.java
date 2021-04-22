package engine.entity.building;

import java.util.AbstractMap;
import java.util.List;
import engine.Position;
import engine.manager.GraphicsManager;
import engine.map.Tile;
import factionConfiguration.ForUpgrade;

/**
 * Class du batiment Forge qui etend ProductionBuilding.
 * <p>
 * Elle redefinie les methode de ProductionBuilding afin des produire des archers.
 * @author Maxime Grodet
 * @see ProductionBuilding
 * @see ForUpgrade
 */
public class Forge extends ProductionBuilding{
	
	/**
	 * Constructeur du castle ou tous ses parametres y sont definis.
	 * @param position Position du batiment.
	 * @param id Id du batiment.
	 * @param description Desciption du batiment.
	 * @param hpMax Quantite d'hp maximum du batiment.
	 * @param faction Faction du batiment.
	 * @param tile Case ou est le batiment.
	 * @param sightRange Taille du chanmp de vision du batiment.
	 * @param graphicsManager Lien vers le GraphicsManager
	 * @param upgrades HashMap des upgrades disponible.
	 */
	public Forge(Position position, int id, String description, int hpMax, int faction, Tile tile, AbstractMap<Integer, ForUpgrade> upgrades, int sightRange, GraphicsManager graphicsManager) {
		super(position, id, description, hpMax, faction, tile, upgrades, sightRange, graphicsManager);
		this.setProductionId(1);
	}

	/**
	 * Methode qui gere la production d'une upgrade.
	 * @return L'id de l'element en production.
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
			int nextIdToProduce = this.getElementCount().get(0);
			this.setTimer(getUpgrades().get(nextIdToProduce).getTimeToProduce());
		}
		//System.out.println("producing upgrade final");
		
		return id;
	}

	/**
	 * Methode qui gere l'initialisation de la production d'une upgrade.
	 * @param id Id de l'element a produire.
	 * @param moneyCount Quantite d'argent disponible.
	 * @return Le prix de l'upgrade si les conditions sont bonne ou 0.
	 */
	@Override
	public int startProd(int id, int moneyCount) {
		int upgradeCost = this.getUpgrades().get(id).getCost();
		int timeBuilding = this.getUpgrades().get(id).getTimeToProduce();
		//System.out.println("Start prod de upgrade, cout : " + upgradeCost + ", gold : " + moneyCount);
		if(upgradeCost <= moneyCount) {
			this.getElementCount().add(id);
			if(this.getIsProducing() == false) {
				this.setTimer(timeBuilding);
				this.setIsProducing(true);
			}
			return upgradeCost;
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
			int idRemove = this.getElementCount().get(this.getElementCount().size() - 1);
			int upgradeCost = this.getUpgrades().get(this.getElementCount().get(this.getElementCount().size() - 1)).getCost();
			
			boolean remove = false;
			int i = 0;
			while(remove == false && searchingUpgrade.get(i) != null) {
				if(searchingUpgrade.get(i) == idRemove) {
					searchingUpgrade.remove(i);
					remove = true;
				}
				i++;
			}
			
			//System.out.println("Suppression prod de upgrade, cout : " + upgradeCost);
			this.getElementCount().remove(this.getElementCount().size() - 1);
			if(this.getElementCount().isEmpty()) {
				this.setIsProducing(false);
				this.setTimer(0);
			}
			return upgradeCost;
		}
		return 0;
	}
	
}