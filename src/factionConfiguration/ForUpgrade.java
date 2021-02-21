package factionConfiguration;

import java.util.Map;

public class ForUpgrade 
{

	private int age; // age ou l'upgrade est dispo
	private String name;
	private Map<String, Integer> effects; // je pense utiliser une hashmap en mode (defense,10) pour par exemple augmenter de 10% la def
	private int timeToProduce;
	private Boolean done; // variable pour dire si l'upgrade a ete produite
	
	public ForUpgrade(int age, String name, Map<String, Integer> effects, int timeToProduce, Boolean done) {
		this.setAge(age);
		this.setName(name);
		this.setEffects(effects);
		this.setTimeToProduce(timeToProduce);
		this.setDone(done);
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

	public Map<String, Integer> getEffects() {
		return effects;
	}

	public void setEffects(Map<String, Integer> effects) {
		this.effects = effects;
	}

	public int getTimeToProduce() {
		return timeToProduce;
	}

	public void setTimeToProduce(int timeToProduce) {
		this.timeToProduce = timeToProduce;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}
	
}
