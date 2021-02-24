package engine;

import java.util.Random;

public class Unit extends Entity
{
	public Unit()
	{
		this.setHp(100);
		Random rand = new Random();
		
		this.setPosition(new Position(rand.nextInt(800),rand.nextInt(800)));
	}
	
	void update()
	{
		
	}
}
