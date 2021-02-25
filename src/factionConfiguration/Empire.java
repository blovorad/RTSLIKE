package factionConfiguration;

public class Empire extends Race
{
	public Empire()
	{
		initCavalry();
		initInfantry();
		initArcher();
		initSpecial();
		initWorker();
		initForge();
		initHq();
		initCastle();
		initTower();
		initRessourceStockage();
		initUpgrades();
		
		setName("Empire");
	}
	
	public void initWorker()
	{
		setWorker(new ForUnit(5, 10 ,10, 20, 5, 3, 50, 20, 2, 25, "worker empire"));
	}
	
	public void initCavalry()
	{
		setCavalry(new ForUnit(5, 10 ,10, 20, 5, 3, 50, 20, 2, 25, "cavalry empire"));
	}
	
	public void initInfantry()
	{
		setInfantry(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "infantry empire"));
	}
	
	public void initArcher()
	{
		setArcher(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "archer empire"));
	}
	
	public void initSpecial()
	{
		setSpecial(new ForUnit(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "special empire"));
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
