package factionConfiguration;

import java.io.File;
import java.util.Scanner;

import configuration.EntityConfiguration;

public class Empire extends Race
{
	public Empire()
	{
		super();
		
		Scanner scan = null;
		
		try
		{
			scan = new Scanner(new File("src/file/empire.txt"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		while(scan.hasNext()) {
			String line = scan.nextLine();
			if(line.equals("infantry")) {
				initInfantry(scan.nextLine());
			}
			else if(line.equals("archer")) {
				initArcher(scan.nextLine());
			}
			else if(line.equals("cavalry")) {
				initCavalry(scan.nextLine());
			}
			else if(line.equals("special")) {
				initSpecial(scan.nextLine());
			}
		}
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
		
		setName("Empire");
	}
	
	public void initWorker()
	{
		this.getPatronWorkers().put(EntityConfiguration.WORKER, new ForWorker(5 , 10, 10, 20, 5, 3, 20, 20, 2, 25, "Travailleur empire", 5, 10, 1, 10));
	}
	
	public void initCavalry(String line)
	{
		String[] s2 = line.split(";");
		String splitFormat = ": ";
		this.getPatronFighters().put(EntityConfiguration.CAVALRY, new ForFighter(Integer.valueOf(s2[0].split(splitFormat)[1]), Integer.valueOf(s2[1].split(splitFormat)[1]), Integer.valueOf(s2[2].split(splitFormat)[1]), Integer.valueOf(s2[3].split(splitFormat)[1]),  Integer.valueOf(s2[4].split(splitFormat)[1]),  Integer.valueOf(s2[5].split(splitFormat)[1]),  Integer.valueOf(s2[6].split(splitFormat)[1]),  Integer.valueOf(s2[7].split(splitFormat)[1]),  Integer.valueOf(s2[8].split(splitFormat)[1]),  Integer.valueOf(s2[9].split(splitFormat)[1]), s2[10].split(splitFormat)[1],  Integer.valueOf(s2[11].split(splitFormat)[1])));
	}
	
	public void initInfantry(String line)
	{
		String[] s2 = line.split(";");
		String splitFormat = ": ";
		
		this.getPatronFighters().put(EntityConfiguration.INFANTRY, new ForFighter(Integer.valueOf(s2[0].split(splitFormat)[1]), Integer.valueOf(s2[1].split(splitFormat)[1]), Integer.valueOf(s2[2].split(splitFormat)[1]), Integer.valueOf(s2[3].split(splitFormat)[1]),  Integer.valueOf(s2[4].split(splitFormat)[1]),  Integer.valueOf(s2[5].split(splitFormat)[1]),  Integer.valueOf(s2[6].split(splitFormat)[1]),  Integer.valueOf(s2[7].split(splitFormat)[1]),  Integer.valueOf(s2[8].split(splitFormat)[1]),  Integer.valueOf(s2[9].split(splitFormat)[1]), s2[10].split(splitFormat)[1],  Integer.valueOf(s2[11].split(splitFormat)[1])));
	}
	
	public void initArcher(String line)
	{
		String[] s2 = line.split(";");
		String splitFormat = ": ";
		this.getPatronFighters().put(EntityConfiguration.ARCHER, new ForFighter(Integer.valueOf(s2[0].split(splitFormat)[1]), Integer.valueOf(s2[1].split(splitFormat)[1]), Integer.valueOf(s2[2].split(splitFormat)[1]), Integer.valueOf(s2[3].split(splitFormat)[1]),  Integer.valueOf(s2[4].split(splitFormat)[1]),  Integer.valueOf(s2[5].split(splitFormat)[1]),  Integer.valueOf(s2[6].split(splitFormat)[1]),  Integer.valueOf(s2[7].split(splitFormat)[1]),  Integer.valueOf(s2[8].split(splitFormat)[1]),  Integer.valueOf(s2[9].split(splitFormat)[1]), s2[10].split(splitFormat)[1],  Integer.valueOf(s2[11].split(splitFormat)[1])));
	}
	
	public void initSpecial(String line)
	{
		String[] s2 = line.split(";");
		String splitFormat = ": ";
		this.getPatronFighters().put(EntityConfiguration.SPECIAL_UNIT, new ForFighter(Integer.valueOf(s2[0].split(splitFormat)[1]), Integer.valueOf(s2[1].split(splitFormat)[1]), Integer.valueOf(s2[2].split(splitFormat)[1]), Integer.valueOf(s2[3].split(splitFormat)[1]),  Integer.valueOf(s2[4].split(splitFormat)[1]),  Integer.valueOf(s2[5].split(splitFormat)[1]),  Integer.valueOf(s2[6].split(splitFormat)[1]),  Integer.valueOf(s2[7].split(splitFormat)[1]),  Integer.valueOf(s2[8].split(splitFormat)[1]),  Integer.valueOf(s2[9].split(splitFormat)[1]), s2[10].split(splitFormat)[1],  Integer.valueOf(s2[11].split(splitFormat)[1])));
	}
	
	public void initForge()
	{
		this.getProductionBuildings().put(EntityConfiguration.FORGE, new ForProductionBuilding(100, 1, null, "Forge Empire", 100));
	}
	
	public void initHq()
	{
		this.getProductionBuildings().put(EntityConfiguration.HQ, new ForProductionBuilding(100, 1, null, "Quartier general Empire", 100));
	}
	
	public void initCastle()
	{
		this.getProductionBuildings().put(EntityConfiguration.CASTLE, new ForProductionBuilding(100, 1, null, "Chateau Empire", 100));
	}
	
	public void initTower()
	{
		this.getAttackBuildings().put(EntityConfiguration.TOWER, new ForAttackBuilding(100, 10, 10, 10 ,10 ,1, "Tour Empire", 100));
	}
	
	public void initRessourceStockage()
	{
		this.getStorageBuildings().put(EntityConfiguration.STORAGE, new ForStorageBuilding(100 , 1, "Stockage Empire", 100));
	}
	
	public void initBarrack()
	{
		this.getProductionBuildings().put(EntityConfiguration.BARRACK, new ForProductionBuilding(100, 1, null, "Caserne Empire", 100));
	}
	
	public void initArchery()
	{
		this.getProductionBuildings().put(EntityConfiguration.ARCHERY, new ForProductionBuilding(100, 1, null, "Archerie Empire", 100));
	}
	
	public void initStable()
	{
		this.getProductionBuildings().put(EntityConfiguration.STABLE, new ForProductionBuilding(100, 1, null, "Ecurie Empire", 100));
	}
	
	public void initUpgrades()
	{
		
	}
}
