package engine.entity.building;

import java.awt.image.BufferedImage;

import configuration.EntityConfiguration;
import engine.Position;
import engine.map.Tile;
import factionConfiguration.ForFighter;

public class Castle extends ProductionBuilding{

	private ForFighter special;
	
	public Castle(Position position, ForFighter special, int id, String description, int hpMax, int faction, Tile tile, BufferedImage texture) {
		super(position, id, description, hpMax, faction, tile, null, texture);
		this.special = special;
		this.setProductionId(EntityConfiguration.SPECIAL_UNIT);
		/*this.setCanAttak(false);
		this.setCanHeal(true);
		this.setDamage(10);
		this.setAttakRange(80);
		this.setAttackSpeed(10);
		this.setAttackCooldown(this.getAttackSpeed());*/
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
			this.setTimer(special.getTimeToBuild());
		}
		System.out.println("producing worker final");
		
		return id;
	}

	@Override
	public int startProd(int id, int moneyCount) {
		this.getElementCount().add(id);
		if(this.getIsProducing() == false) {
			this.setTimer(special.getTimeToBuild());
			this.setIsProducing(true);
		}
		return 0;
	}

	@Override
	public int removeProduction() {
		if(this.getIsProducing() == true) {
			System.out.println("Suppression prod de special, cout : " + this.special.getCost());
			this.getElementCount().remove(this.getElementCount().size() - 1);
			if(this.getElementCount().isEmpty()) {
				this.setIsProducing(false);
				this.setTimer(0);
			}
			return this.special.getCost();
		}
		return 0;
	}
	
}
