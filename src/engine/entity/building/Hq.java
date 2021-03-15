package engine.entity.building;

import java.awt.image.BufferedImage;
import java.util.AbstractMap;
import java.util.List;

import configuration.EntityConfiguration;
import engine.Position;
import engine.map.Tile;
import factionConfiguration.ForUpgrade;
import factionConfiguration.ForWorker;

public class Hq extends ProductionBuilding{

	private ForWorker worker;
	
	public Hq(Position position, ForWorker worker, int id, String description, int hpMax, int faction, Tile tile, AbstractMap<Integer, ForUpgrade> upgrades, BufferedImage texture, int sightRange) {
		super(position, id, description, hpMax, faction, tile, upgrades, texture, sightRange);
		this.worker = worker;
		this.setProductionId(EntityConfiguration.WORKER);
	}
	
	public void upgradeTier() {
		
	}

	@Override
	public int produce() {		
		int id = this.getElementCount().get(0);
		this.getElementCount().remove(0);
		if(this.getElementCount().isEmpty()) {
			this.setIsProducing(false);
		}
		else
		{
			this.setTimer(worker.getTimeToBuild());
		}
		System.out.println("producing worker final");
		
		return id;
	}

	@Override
	public int startProd(int id, int moneyCount) {	
		if(id == EntityConfiguration.WORKER) {
			System.out.println("Start prod de worker, cout : " + this.worker.getCost() + ", gold : " + moneyCount);
			if(this.worker.getCost() <= moneyCount) {
				this.getElementCount().add(id);
				if(this.getIsProducing() == false) {
					this.setTimer(worker.getTimeToBuild());
					this.setIsProducing(true);
				}
				return worker.getCost();
			}
			else {
				System.out.println("Pas assez de gold !");
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
				System.out.println("Pas assez de gold !");
				return 0;
			}
		}
	}

	@Override
	public int removeProduction(List<Integer> searchingUpgrade) {
		if(this.getIsProducing() == true) {
			int idRemove = this.getElementCount().get(this.getElementCount().size() - 1);
			if(idRemove == EntityConfiguration.WORKER) {
				System.out.println("Suppression prod de worker, cout : " + this.worker.getCost());
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
				
				System.out.println("Suppression prod de upgrade, cout : " + upgradeCost);
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
	
}
