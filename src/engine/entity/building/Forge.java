package engine.entity.building;

import java.util.AbstractMap;

import engine.Position;
import engine.map.Tile;
import factionConfiguration.ForUpgrade;

public class Forge extends ProductionBuilding{
	
	public Forge(Position position, int id, String description, int hpMax, int faction, Tile tile, AbstractMap<Integer, ForUpgrade> upgrades) {
		super(position, id, description, hpMax, faction, tile, upgrades);
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
		this.getElementCount().add(id);
		if(this.getIsProducing() == false) {
			this.setTimer(getUpgrades().get(id).getTimeToProduce());
			this.setIsProducing(true);
		}
		return 0;
	}

	@Override
	public int removeProduction() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
