package engine.entity.building;

import java.util.AbstractMap;
import java.util.List;
import configuration.EntityConfiguration;
import engine.Position;
import engine.manager.GraphicsManager;
import engine.map.Tile;
import factionConfiguration.ForUpgrade;
import factionConfiguration.ForWorker;

/**
 * Class du batiment HQ qui etend ProductionBuilding.
 * <p>
 * Elle redefinie les methode de ProductionBuilding afin des produire des archers.
 * @author Maxime Grodet
 * @see ProductionBuilding
 * @see ForWorker
 */
public class Hq extends ProductionBuilding{

	/**
	 * Lien vers le model du worker afin de recuperer ses attribut.
	 * @see ForWorker
	 */
	private ForWorker worker;
	
	/**
	 * Hashmap des upgrade de l'hq.
	 * @see ForUpgrade
	 */
	private AbstractMap<Integer, ForUpgrade> upgradesOfHq;
	
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
	 * @param worker Model du worker.
	 */
	public Hq(Position position, ForWorker worker, int id, String description, int hpMax, int faction, Tile tile, AbstractMap<Integer, ForUpgrade> upgrades, int sightRange, GraphicsManager graphicsManager) {
		super(position, id, description, hpMax, faction, tile, upgrades, sightRange, graphicsManager);
		this.worker = worker;
		this.setUpgradesOfHq(upgrades);
		this.setProductionId(EntityConfiguration.WORKER);
	}

	/**
	 * Methode qui gere la production d'une upgrade ou d'un worker.
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
			if(nextIdToProduce == EntityConfiguration.AGE_UPGRADE) {
				this.setTimer(upgradesOfHq.get(nextIdToProduce).getTimeToProduce());
			}
			else if(nextIdToProduce == EntityConfiguration.AGE_UPGRADE_2) {
				this.setTimer(upgradesOfHq.get(nextIdToProduce).getTimeToProduce());
			}
			else {
				this.setTimer(worker.getTimeToBuild());
			}
		}
		//System.out.println("producing worker final");
		
		return id;
	}

	/**
	 * Methode qui gere l'initialisation de la production d'une upgrade ou d'un worker.
	 * @param id Id de l'element a produire.
	 * @param moneyCount Quantite d'argent disponible.
	 * @return Le prix de l'upgrade si les conditions sont bonne ou 0.
	 */
	@Override
	public int startProd(int id, int moneyCount) {	
		if(id == EntityConfiguration.WORKER) {
			//System.out.println("Start prod de worker, cout : " + this.worker.getCost() + ", gold : " + moneyCount);
			if(this.worker.getCost() <= moneyCount) {
				this.getElementCount().add(id);
				if(this.getIsProducing() == false) {
					this.setTimer(worker.getTimeToBuild());
					this.setIsProducing(true);
				}
				return worker.getCost();
			}
			else {
				//System.out.println("Pas assez de gold !");
				return 0;
			}
		}
		else {
			ForUpgrade upgrade = this.getUpgrades().get(id);
			if(upgrade.getCost() <= moneyCount) {
				this.getElementCount().add(id);
				if(this.getIsProducing() == false) {
					this.setTimer(upgrade.getTimeToProduce());
					this.setIsProducing(true);
				}
				return upgrade.getCost();
			}
			else {
				//System.out.println("Pas assez de gold !");
				return 0;
			}
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
			if(idRemove == EntityConfiguration.WORKER) {
				//System.out.println("Suppression prod de worker, cout : " + this.worker.getCost());
				this.getElementCount().remove(this.getElementCount().size() - 1);
				if(this.getElementCount().isEmpty()) {
					this.setIsProducing(false);
					this.setTimer(0);
				}
				return this.worker.getCost();
			}
			else {
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
		}
		return 0;
	}

	public AbstractMap<Integer, ForUpgrade> getUpgradesOfHq() {
		return upgradesOfHq;
	}

	public void setUpgradesOfHq(AbstractMap<Integer, ForUpgrade> upgradesOfHq) {
		this.upgradesOfHq = upgradesOfHq;
	}
	
}