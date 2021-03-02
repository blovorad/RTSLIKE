package engine.entity.building;

import java.util.ArrayList;
import java.util.List;

import configuration.EntityConfiguration;
import engine.Entity;
import engine.Position;
import engine.Unit;

public abstract class ProductionBuilding extends Entity{

	private List<Unit> elementCount;
	private int productionId;
	private int timer;
	private boolean isProducing;
	
	public ProductionBuilding(Position position, int id, String description, int hpMax) {
		super(100, hpMax, description , position, id);
		elementCount = new ArrayList<Unit>();
	}
	
	public void update() {
		if(timer > 0)
		{
			timer--;
			if(this.getProductionId() == EntityConfiguration.INFANTRY) {
				System.out.println("updating infantry production time remaning : " + timer);
			}
			else if(this.getProductionId() == EntityConfiguration.ARCHER) {
				System.out.println("updating archer production time remaning : " + timer);
			}
			else if(this.getProductionId() == EntityConfiguration.CAVALRY) {
				System.out.println("updating cavalry production time remaning : " + timer);
			}
			else if(this.getProductionId() == EntityConfiguration.SPECIAL_UNIT) {
				System.out.println("updating special production time remaning : " + timer);
			}
			else if(this.getProductionId() == EntityConfiguration.WORKER) {
				System.out.println("updating worker production time remaning : " + timer);
			}
			else {
				System.out.println("Invalid id");
			}
		}
	}
	
	public abstract Unit produce();
	public abstract void startProd(int id);

	public List<Unit> getElementCount() {
		return elementCount;
	}

	public void setElementCount(List<Unit> elementCount) {
		this.elementCount = elementCount;
	}

	public int getProductionId() {
		return productionId;
	}

	public void setProductionId(int productionId) {
		this.productionId = productionId;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public boolean getIsProducing() {
		return isProducing;
	}

	public void setIsProducing(boolean isProducing) {
		this.isProducing = isProducing;
	}
	
}
