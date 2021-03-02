package factionConfiguration;

import java.util.Map;

public class ForUpgrade 
{

	private int age; // age ou l'upgrade est dispo
	private String name;
	private Map<Integer, Integer> effects; // hashmap en mode (id def,10) pour par exemple augmenter de 10% la def
	private int timeToProduce;
	private int idBuilding;
	private boolean done;
	
	public ForUpgrade(int age, String name, Map<Integer, Integer> effects, int timeToProduce) {
		this.setAge(age);
		this.setName(name);
		this.setEffects(effects);
		this.setTimeToProduce(timeToProduce);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Integer, Integer> getEffects() {
		return effects;
	}

	public void setEffects(Map<Integer, Integer> effects) {
		this.effects = effects;
	}

	public int getTimeToProduce() {
		return timeToProduce;
	}

	public void setTimeToProduce(int timeToProduce) {
		this.timeToProduce = timeToProduce;
	}

	public int getIdBuilding() {
		return idBuilding;
	}

	public void setIdBuilding(int idBuilding) {
		this.idBuilding = idBuilding;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
}
