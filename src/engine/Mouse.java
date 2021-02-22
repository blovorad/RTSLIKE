package engine;

public class Mouse {
	
	private Building building;
	private int id = -1;
	private int x;
	private int y;
	
	public Mouse()
	{
		x = 0;
		y = 0;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		if(id > -1)
		{
			building.getPosition().setX(x);
			//System.out.println("on bouge le truc a x :" + building.getPosition().getX());
		}
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		if(id > -1)
		{
			building.getPosition().setX(y);
			/*System.out.println("on bouge le truc a y :" + building.getPosition().getY());
			System.out.println("souris coordonner: " + this.getX() + "," + this.getY());*/
		}
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}
	
	public void reset()
	{
		id = -1;
	}
}
