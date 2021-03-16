package engine.entity.building;

import java.awt.image.BufferedImage;
import java.util.AbstractMap;
import java.util.List;

import engine.Position;
import engine.map.Tile;
import factionConfiguration.ForUpgrade;

public class Forge extends ProductionBuilding{
	
	public Forge(Position position, int id, String description, int hpMax, int faction, Tile tile, AbstractMap<Integer, ForUpgrade> upgrades, BufferedImage texture, int sightRange) {
		super(position, id, description, hpMax, faction, tile, upgrades, texture, sightRange);
		this.setProductionId(1);
	}

	@Override
	public int produce() {
		// TODO empecher meme upgrade pour les bolosse a deux forge
		int id = this.getElementCount().get(0);
		this.getElementCount().remove(0);
		if(this.getElementCount().isEmpty()) {
			this.setIsProducing(false);
		}
		else
		{
			this.setTimer(getUpgrades().get(id).getTimeToProduce());
		}
		System.out.println("producing upgrade final");
		
		return id;
	}

	@Override
	public int startProd(int id, int moneyCount) {
		int upgradeCost = this.getUpgrades().get(id).getCost();
		int timeBuilding = this.getUpgrades().get(id).getTimeToProduce();
		System.out.println("Start prod de upgrade, cout : " + upgradeCost + ", gold : " + moneyCount);
		if(upgradeCost <= moneyCount) {
			this.getElementCount().add(id);
			if(this.getIsProducing() == false) {
				this.setTimer(timeBuilding);
				this.setIsProducing(true);
			}
			return upgradeCost;
		}
		else {
			System.out.println("Pas assez de gold !");
			return 0;
		}
	}

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
			
			System.out.println("Suppression prod de upgrade, cout : " + upgradeCost);
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
