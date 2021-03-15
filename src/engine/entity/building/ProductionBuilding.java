package engine.entity.building;

import java.awt.image.BufferedImage;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import configuration.EntityConfiguration;
import engine.Entity;
import engine.Position;
import engine.map.Tile;
import factionConfiguration.ForUpgrade;

public abstract class ProductionBuilding extends Entity{

	private List<Integer> elementCount;
	private int productionId;
	private int timer;
	private boolean isProducing;
	private Tile tile;
	private AbstractMap<Integer, ForUpgrade> upgrades;
	
	public ProductionBuilding(Position position, int id, String description, int hpMax, int faction, Tile tile, AbstractMap<Integer, ForUpgrade> upgrades, BufferedImage texture, int sightRange) {
		super(100, hpMax, description , position, id, faction, texture, sightRange);
		elementCount = new ArrayList<Integer>();
		this.setTile(tile);
		this.setUpgrades(upgrades);
		if(upgrades == null) {
			this.upgrades = new HashMap<Integer, ForUpgrade>();
		}
	}
	
	public void update(int popCount, int maxPop) {
		super.update();
		if(this.getId() == EntityConfiguration.FORGE) {
			timer--;
			if(this.getProductionId() == EntityConfiguration.ARMOR_UPGRADE) {
				System.out.println("updating armor upgrade production time remaning : " + timer);
			}
		}
		else if(popCount < maxPop) {
			if(timer > 0) {
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
	}
	
	public abstract int produce();
	public abstract int startProd(int id, int moneyCount);
	public abstract int removeProduction();
	
	public void remove() {
		timer = 0;
		isProducing = false;
		elementCount.clear();
	}

	public List<Integer> getElementCount() {
		return elementCount;
	}

	public void setElementCount(List<Integer> elementCount) {
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

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public AbstractMap<Integer, ForUpgrade> getUpgrades() {
		return upgrades;
	}

	public void setUpgrades(AbstractMap<Integer, ForUpgrade> upgrades) {
		this.upgrades = upgrades;
	}
	
}
