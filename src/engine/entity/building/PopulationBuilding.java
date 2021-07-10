package engine.entity.building;

import engine.Position;
import engine.manager.GraphicsManager;

public class PopulationBuilding extends Building{
	
	private int population;
	
	public PopulationBuilding(int hp, int hpMax, String description, Position position, int id, int faction, int sightRange, GraphicsManager graphicsManager, int population) {
		super(hp, hpMax, description, position, id, faction, sightRange, 0, graphicsManager);
		this.population = population;
	}
	
	public void update() {
		super.update();
	}
	
	public int getPopulation() {
		return this.population;
	}
}
