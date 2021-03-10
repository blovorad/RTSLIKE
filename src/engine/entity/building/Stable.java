package engine.entity.building;

import java.awt.image.BufferedImage;

import configuration.EntityConfiguration;
import engine.Position;
import engine.map.Tile;
import factionConfiguration.ForFighter;

public class Stable extends ProductionBuilding{
	
	private ForFighter cavalry;
	
	public Stable(Position position, ForFighter cavalry, int id, String description, int hpMax, int faction, Tile tile, BufferedImage texture) {
		super(position, id, description, hpMax, faction, tile, null, texture);
		this.cavalry = cavalry;
		this.setProductionId(EntityConfiguration.CAVALRY);
	}
	
	public void addUnit() {
		
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
			this.setTimer(cavalry.getTimeToBuild());
		}
		System.out.println("producing cavalry final");
		
		return id;
	}

	@Override
	public int startProd(int id, int moneyCount) {
		this.getElementCount().add(id);
		if(this.getIsProducing() == false) {
			this.setTimer(cavalry.getTimeToBuild());
			this.setIsProducing(true);
		}
		return 0;
	}

	@Override
	public int removeProduction() {
		if(this.getIsProducing() == true) {
			System.out.println("Suppression prod de cavalry, cout : " + this.cavalry.getCost());
			this.getElementCount().remove(this.getElementCount().size() - 1);
			if(this.getElementCount().isEmpty()) {
				this.setIsProducing(false);
				this.setTimer(0);
			}
			return this.cavalry.getCost();
		}
		return 0;
	}

}
