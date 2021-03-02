package engine;

public class Entity 
{
	private int hp;
	private int hpMax;
	private String description;
	private Position position;
	private Entity target;
	private Position destination;
	private int faction;
	private int id;
	
	public Entity(int hp, int hpMax, String description, Position position, int id)
	{
		this.hp = hp;
		this.hpMax = hpMax;
		this.description = description;
		this.position = position;
		this.id = id;
		this.target = null;
		this.destination = null;
	}
	
	public Entity(int hp, String description, Position position, Position destination, int id)
	{
		this.hp = hp;
		this.description = description;
		this.position = position;
		this.id = id;
		this.destination = destination;
		this.target = null;
	}
	
	public void damage(int damage)
	{
		this.setHp(this.getHp() - damage);
	}

	public void heal(int hp)
	{
		this.setHp(this.getHp() + hp);
		if(this.getHp() > this.getHpMax())
		{
			this.setHp(this.getHpMax());
		}
	}
	
	//getter & setter
	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	public Position getDestination() {
		return destination;
	}

	public void setDestination(Position destination) {
		this.destination = destination;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHpMax() {
		return hpMax;
	}

	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}
}
