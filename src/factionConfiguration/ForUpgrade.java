package factionConfiguration;

public class ForUpgrade {

	private int age; // age ou l'upgrade est dispo
	private String description;
	private int id;
	private int effect;
	private int timeToProduce;
	private int cost;
	
	public ForUpgrade(int age, String description, int effect, int id, int timeToProduce, int cost) {
		this.setAge(age);
		this.id = id;
		this.effect = effect;
		this.description = description;
		this.setTimeToProduce(timeToProduce);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getTimeToProduce() {
		return timeToProduce;
	}

	public void setTimeToProduce(int timeToProduce) {
		this.timeToProduce = timeToProduce;
	}

	public int getEffect() {
		return effect;
	}

	public void setEffect(int effect) {
		this.effect = effect;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}
