package engine;

public class Entity 
{
	private int hp;
	private String description;
	private Position position;
	private Entity target;
	private Position destination;
	private int id;
	
	public Entity(int hp, String description, Position position, int id)
	{
		this.hp = hp;
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
		this.setHp(this.getHp() -damage);
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
}
