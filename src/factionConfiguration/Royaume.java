package factionConfiguration;

public class Royaume extends Race 
{
	public Royaume()
	{
		initCavalry();
		initInfantry();
		initArcher();
		initSpecial();
		initForge();
		initHq();
		initCastle();
		initTower();
		initRessourceStockage();
		initUpgrades();
		
		setName("Royaume");
	}
	
	public void initCavalry()
	{
		setCavalry(new ForUnit(5, 10 ,10, 20, 5, 3, 50, 20, 2, 25));
	}
	
	public void initInfantry()
	{
		setInfantry(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25));
	}
	
	public void initArcher()
	{
		setArcher(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25));
	}
	
	public void initSpecial()
	{
		setSpecial(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25));
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
	
	public void initUpgrades()
	{
		
	}
	
}
