package factionConfiguration;
/**
 * 
 * specificaly create to upgrade, that don't need the same as other For class
 * @author gautier
 */
public class ForUpgrade {
	
	/**
	 * age where you can build this upgrade
	 */
	private int age;
	/**
	 * description of the upgrade
	 */
	private String description;
	/**
	 * id to upgrade
	 */
	private int id;
	/**
	 * effect of the upgrade, it adds with the corresponding competence
	 */
	private int effect;
	/**
	 * how many time before be product
	 */
	private int timeToProduce;
	/**
	 * cost
	 */
	private int cost;
	
	public ForUpgrade(int age, String description, int effect, int id, int timeToProduce, int cost) {
		this.setAge(age);
		this.id = id;
		this.effect = effect;
		this.description = description;
		this.cost = cost;
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
