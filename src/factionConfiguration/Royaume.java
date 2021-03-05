package factionConfiguration;

import java.io.File;
import java.util.Scanner;

import configuration.EntityConfiguration;

public class Royaume extends Race 
{
	public Royaume()
	{
		super();
		Scanner scan = null;
		
		try
		{
			scan = new Scanner(new File("src/file/royaume.txt"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		initCavalry();
		initInfantry(scan);
		initArcher();
		initSpecial();
		initWorker();
		initForge();
		initHq();
		initCastle();
		initTower();
		initRessourceStockage();
		initBarrack();
		initArchery();
		initStable();
		initUpgrades();
		
		scan.close();
		
		setName("Royaume");
	}
	
	public void initWorker()
	{
		this.getPatronWorkers().put(EntityConfiguration.WORKER, new ForWorker(5 , 10, 10, 20, 5, 3, 20, 20, 2, 25, "Travailleur royaume", 5, 10, 1, 10));
	}
	
	public void initCavalry()
	{
		this.getPatronFighters().put(EntityConfiguration.CAVALRY, new ForFighter(5 , 10, 10, 20, 5, 3, 50, 20, 2, 25, "Cavalier royaume", 5));
	}
	
	public void initInfantry(Scanner scan)
	{
		//int attackRange, int attackSpeed, int sightRange, int range, int damage, int armor, int maxSpeed, int hp, int age, int timeToBuild, String description, int hpMax
		System.out.println("" + scan.nextLine());
		String s = scan.nextLine();
		System.out.println("" + s);
		String[] s2 = s.split(";");
		String splitFormat = ": ";
		
		this.getPatronFighters().put(EntityConfiguration.INFANTRY, new ForFighter(Integer.valueOf(s2[0].split(splitFormat)[1]), Integer.valueOf(s2[1].split(splitFormat)[1]), Integer.valueOf(s2[2].split(splitFormat)[1]), Integer.valueOf(s2[3].split(splitFormat)[1]),  Integer.valueOf(s2[4].split(splitFormat)[1]),  Integer.valueOf(s2[5].split(splitFormat)[1]),  Integer.valueOf(s2[6].split(splitFormat)[1]),  Integer.valueOf(s2[7].split(splitFormat)[1]),  Integer.valueOf(s2[8].split(splitFormat)[1]),  Integer.valueOf(s2[9].split(splitFormat)[1]), s2[10].split(splitFormat)[1],  Integer.valueOf(s2[11].split(splitFormat)[1])));
	}
	
	public void initArcher()
	{
		this.getPatronFighters().put(EntityConfiguration.ARCHER, new ForFighter(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Archer royaume", 5));
	}
	
	public void initSpecial()
	{
		this.getPatronFighters().put(EntityConfiguration.SPECIAL_UNIT, new ForFighter(5, 10, 10, 15, 3, 1, 25, 15, 1, 25, "Special royaume", 5));
	}
	
	public void initForge()
	{
		this.getProductionBuildings().put(EntityConfiguration.FORGE, new ForProductionBuilding(100, 1, null, "Forge Royaume", 100));
	}
	
	public void initHq()
	{
		this.getProductionBuildings().put(EntityConfiguration.HQ, new ForProductionBuilding(100, 1, null, "Quartier general Royaume", 100));
	}
	
	public void initCastle()
	{
		this.getProductionBuildings().put(EntityConfiguration.CASTLE, new ForProductionBuilding(100, 1, null, "Chateau Royaume", 100));
	}
	
	public void initTower()
	{
		this.getAttackBuildings().put(EntityConfiguration.TOWER, new ForAttackBuilding(100, 10, 10, 10 ,10 ,1, "Tour Royaume", 100));
	}
	
	public void initRessourceStockage()
	{
		this.getStorageBuildings().put(EntityConfiguration.STORAGE, new ForStorageBuilding(100 , 1, "Stockage Royaume", 100));
	}
	
	public void initBarrack()
	{
		this.getProductionBuildings().put(EntityConfiguration.BARRACK, new ForProductionBuilding(100, 1, null, "Caserne Royaume", 100));
	}
	
	public void initArchery()
	{
		this.getProductionBuildings().put(EntityConfiguration.ARCHERY, new ForProductionBuilding(100, 1, null, "Archerie Royaume", 100));
	}
	
	public void initStable()
	{
		this.getProductionBuildings().put(EntityConfiguration.STABLE, new ForProductionBuilding(100, 1, null, "Ecurie Royaume", 100));
	}
	
	public void initUpgrades()
	{
		
	}
	
}
