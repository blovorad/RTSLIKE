package engine.entity.building;

import configuration.EntityConfiguration;
import engine.Position;
import engine.entity.unit.Unit;
import engine.map.Tile;
import factionConfiguration.ForFighter;

public class Archery extends ProductionBuilding{

	private ForFighter archer;
	
	public Archery(Position position, ForFighter archer, int id, String description, int hpMax, int faction, Tile tile) {
		super(position, id, description, hpMax, faction, tile);
		this.archer = archer;
		this.setProductionId(EntityConfiguration.ARCHER);
	}

	@Override
	public Unit produce() {
		Unit u;
		
		u = this.getElementCount().get(0);
		this.getElementCount().remove(0);
		if(this.getElementCount().isEmpty()) {
			this.setIsProducing(false);
		}
		else
		{
			this.setTimer(archer.getTimeToBuild());
		}
		System.out.println("producing archer final");
		
		return u;
	}

	@Override
	public void startProd(int id) {
		Unit u = null;

		if(id == EntityConfiguration.ARCHER) {
			System.out.println("starting archer production");
			if(this.getDestination() == null) {
				u = new Unit(archer.getHp(), 0, archer.getAttackRange(), archer.getMaxSpeed(), archer.getDamage(), archer.getRange(), archer.getArmor(), new Position(this.getPosition().getX()- 50, this.getPosition().getY() - 50), id, archer.getDescription(), archer.getHpMax(), this.getFaction());
			}
			else {
				u = new Unit(archer.getHp(), 0, archer.getAttackRange(), archer.getMaxSpeed(), archer.getDamage(), archer.getRange(), archer.getArmor(), new Position(this.getPosition().getX()- 50, this.getPosition().getY() - 50), id, archer.getDescription(), this.getDestination(), archer.getHpMax(), this.getFaction());
				u.calculateSpeed(getDestination());
			}
		}
		else {
			System.out.println("Invalid id");
		}
		
		this.getElementCount().add(u);
		if(this.getIsProducing() == false) {
			this.setTimer(archer.getTimeToBuild());
			this.setIsProducing(true);
		}
	}
}
