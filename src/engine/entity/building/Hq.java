package engine.entity.building;

import java.awt.image.BufferedImage;
import java.util.AbstractMap;

import configuration.EntityConfiguration;
import engine.Position;
import engine.map.Tile;
import factionConfiguration.ForUpgrade;
import factionConfiguration.ForWorker;

public class Hq extends ProductionBuilding{

	private ForWorker worker;
	
	public Hq(Position position, ForWorker worker, int id, String description, int hpMax, int faction, Tile tile, AbstractMap<Integer, ForUpgrade> upgrades, BufferedImage texture) {
		super(position, id, description, hpMax, faction, tile, upgrades, texture);
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

	@Override
	public int removeProduction() {
		
		if(this.getIsProducing() == true) {
			System.out.println("Suppression prod de worker, cout : " + this.worker.getCost());
			this.getElementCount().remove(this.getElementCount().size() - 1);
			if(this.getElementCount().isEmpty()) {
				this.setIsProducing(false);
				this.setTimer(0);
			}
			return this.worker.getCost();
		}
		return 0;
	}
	
}
