package engine.entity.building;

import java.util.List;

import configuration.EntityConfiguration;
import engine.Position;
import engine.manager.GraphicsManager;
import engine.map.Tile;
import factionConfiguration.ForFighter;
/**
 * 
 * @author maxime
 *
 */
public class Archery extends ProductionBuilding{

	private ForFighter archer;
	
	public Archery(Position position, ForFighter archer, int id, String description, int hpMax, int faction, Tile tile, int sightRange, GraphicsManager graphicsManager) {
		super(position, id, description, hpMax, faction, tile, null, sightRange, graphicsManager);
		this.archer = archer;
		this.setProductionId(EntityConfiguration.ARCHER);
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
			this.setTimer(archer.getTimeToBuild());
		}
		//System.out.println("producing worker final");
		
		return id;
	}

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
