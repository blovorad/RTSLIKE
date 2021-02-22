package factionConfiguration;

public class Gaia extends Race
{
	public Gaia()
	{
		initCavalry();
		initInfantry();
		initArcher();
		initForge();
		initHq();
		initCastle();
		initTower();
		initRessourceStockage();
		
		setName("Gaia");
	}
	
	public void initCavalry()
	{
		setCavalry(new ForUnit(5, 10, 20, 5, 3, 50, 20, 2, 25));
	}
	
	public void initInfantry()
	{
		setInfantry(new ForUnit(5, 10, 15, 3, 1, 25, 15, 1, 25));
	}
	
	public void initArcher()
	{
		setArcher(new ForUnit(5, 10, 15, 3, 1, 25, 15, 1, 25));
	}
	
	public void initForge()
	{
		
	}
	
	public void initHq()
	{
		
	}
	
	public void initCastle()
	{
		
	}
	
	public void initTower()
	{
		
	}
	
	public void initRessourceStockage()
	{
		
	}
}
