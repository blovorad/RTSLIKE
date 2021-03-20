package factionConfiguration;

import java.io.File;
import java.util.Scanner;

import configuration.EntityConfiguration;
/**
 * 
 * @author gautier
 *
 */
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
			else if(line.equals("worker")) {
				initWorker(scan.nextLine());
			}
		}
		initForge();
		initHq();
		initCastle();
		initTower();
		initRessourceStockage();
		initBarrack();
		initArchery();
		initStable();
		initHQUpgrades();
		initForgeUpgrades();
		
		scan.close();
		
		setName("Royaume");
	}
	
	public void initWorker(String line)
	{
		String[] s2 = line.split(";");
		String splitFormat = ": ";
		this.getPatronWorkers().put(EntityConfiguration.WORKER, new ForWorker(Integer.valueOf(s2[0].split(splitFormat)[1]), Integer.valueOf(s2[1].split(splitFormat)[1]), Integer.valueOf(s2[2].split(splitFormat)[1]), Integer.valueOf(s2[3].split(splitFormat)[1]), Integer.valueOf(s2[4].split(splitFormat)[1]), Integer.valueOf(s2[5].split(splitFormat)[1]), Integer.valueOf(s2[6].split(splitFormat)[1]), Integer.valueOf(s2[7].split(splitFormat)[1]), Integer.valueOf(s2[8].split(splitFormat)[1]), Integer.valueOf(s2[9].split(splitFormat)[1]), s2[10].split(splitFormat)[1], Integer.valueOf(s2[11].split(splitFormat)[1]), Integer.valueOf(s2[12].split(splitFormat)[1]), Integer.valueOf(s2[13].split(splitFormat)[1]), Integer.valueOf(s2[14].split(splitFormat)[1]), Integer.valueOf(s2[15].split(splitFormat)[1]), Integer.valueOf(s2[16].split(splitFormat)[1]), Integer.valueOf(s2[17].split(splitFormat)[1])));
	}
	
	public void initCavalry(String line)
	{
		String[] s2 = line.split(";");
		String splitFormat = ": ";
		this.getPatronFighters().put(EntityConfiguration.CAVALRY, new ForFighter(Integer.valueOf(s2[0].split(splitFormat)[1]), Integer.valueOf(s2[1].split(splitFormat)[1]), Integer.valueOf(s2[2].split(splitFormat)[1]), Integer.valueOf(s2[3].split(splitFormat)[1]),  Integer.valueOf(s2[4].split(splitFormat)[1]),  Integer.valueOf(s2[5].split(splitFormat)[1]),  Integer.valueOf(s2[6].split(splitFormat)[1]),  Integer.valueOf(s2[7].split(splitFormat)[1]),  Integer.valueOf(s2[8].split(splitFormat)[1]),  Integer.valueOf(s2[9].split(splitFormat)[1]), s2[10].split(splitFormat)[1],  Integer.valueOf(s2[11].split(splitFormat)[1]), Integer.valueOf(s2[12].split(splitFormat)[1])));
	}
	
	public void initInfantry(String line)
	{
		String[] s2 = line.split(";");
		String splitFormat = ": ";
		
		this.getPatronFighters().put(EntityConfiguration.INFANTRY, new ForFighter(Integer.valueOf(s2[0].split(splitFormat)[1]), Integer.valueOf(s2[1].split(splitFormat)[1]), Integer.valueOf(s2[2].split(splitFormat)[1]), Integer.valueOf(s2[3].split(splitFormat)[1]),  Integer.valueOf(s2[4].split(splitFormat)[1]),  Integer.valueOf(s2[5].split(splitFormat)[1]),  Integer.valueOf(s2[6].split(splitFormat)[1]),  Integer.valueOf(s2[7].split(splitFormat)[1]),  Integer.valueOf(s2[8].split(splitFormat)[1]),  Integer.valueOf(s2[9].split(splitFormat)[1]), s2[10].split(splitFormat)[1],  Integer.valueOf(s2[11].split(splitFormat)[1]), Integer.valueOf(s2[12].split(splitFormat)[1])));
	}
	
	public void initArcher(String line)
	{
		String[] s2 = line.split(";");
		String splitFormat = ": ";
		this.getPatronFighters().put(EntityConfiguration.ARCHER, new ForFighter(Integer.valueOf(s2[0].split(splitFormat)[1]), Integer.valueOf(s2[1].split(splitFormat)[1]), Integer.valueOf(s2[2].split(splitFormat)[1]), Integer.valueOf(s2[3].split(splitFormat)[1]),  Integer.valueOf(s2[4].split(splitFormat)[1]),  Integer.valueOf(s2[5].split(splitFormat)[1]),  Integer.valueOf(s2[6].split(splitFormat)[1]),  Integer.valueOf(s2[7].split(splitFormat)[1]),  Integer.valueOf(s2[8].split(splitFormat)[1]),  Integer.valueOf(s2[9].split(splitFormat)[1]), s2[10].split(splitFormat)[1],  Integer.valueOf(s2[11].split(splitFormat)[1]), Integer.valueOf(s2[12].split(splitFormat)[1])));
	}
	
	public void initSpecial(String line)
	{
		String[] s2 = line.split(";");
		String splitFormat = ": ";
		this.getPatronFighters().put(EntityConfiguration.SPECIAL_UNIT, new ForFighter(Integer.valueOf(s2[0].split(splitFormat)[1]), Integer.valueOf(s2[1].split(splitFormat)[1]), Integer.valueOf(s2[2].split(splitFormat)[1]), Integer.valueOf(s2[3].split(splitFormat)[1]),  Integer.valueOf(s2[4].split(splitFormat)[1]),  Integer.valueOf(s2[5].split(splitFormat)[1]),  Integer.valueOf(s2[6].split(splitFormat)[1]),  Integer.valueOf(s2[7].split(splitFormat)[1]),  Integer.valueOf(s2[8].split(splitFormat)[1]),  Integer.valueOf(s2[9].split(splitFormat)[1]), s2[10].split(splitFormat)[1],  Integer.valueOf(s2[11].split(splitFormat)[1]), Integer.valueOf(s2[12].split(splitFormat)[1])));
	}
	
	public void initForge()
	{
		this.getProductionBuildings().put(EntityConfiguration.FORGE, new ForProductionBuilding(100, 2, this.getForgeUpgrades(), "Forge Royaume", 100, 400, 25));
	}
	
	public void initHq()
	{
		this.getProductionBuildings().put(EntityConfiguration.HQ, new ForProductionBuilding(100, 1, this.getHQUpgrades(), "Quartier general Royaume", 100, 400, 25));
	}
	
	public void initCastle()
	{
		this.getProductionBuildings().put(EntityConfiguration.CASTLE, new ForProductionBuilding(100, 3, null, "Chateau Royaume", 100, 400, 25));
	}
	
	public void initTower()
	{
		this.getAttackBuildings().put(EntityConfiguration.TOWER, new ForAttackBuilding(100, 400, 1, 80 ,10 ,1, "Tour Royaume", 100, 25));
	}
	
	public void initRessourceStockage()
	{
		this.getStorageBuildings().put(EntityConfiguration.STORAGE, new ForStorageBuilding(100 , 1, "Stockage Royaume", 100, 400, 25));
	}
	
	public void initBarrack()
	{
		this.getProductionBuildings().put(EntityConfiguration.BARRACK, new ForProductionBuilding(100, 1, null, "Caserne Royaume", 100, 400, 25));
	}
	
	public void initArchery()
	{
		this.getProductionBuildings().put(EntityConfiguration.ARCHERY, new ForProductionBuilding(100, 2, null, "Archerie Royaume", 100, 400, 25));
	}
	
	public void initStable()
	{
		this.getProductionBuildings().put(EntityConfiguration.STABLE, new ForProductionBuilding(100, 2, null, "Ecurie Royaume", 100, 400, 25));
	}
	
	public void initForgeUpgrades()
	{
		this.getForgeUpgrades().put(EntityConfiguration.ARMOR_UPGRADE, new ForUpgrade(2, "armure en fer", 5, EntityConfiguration.ARMOR_UPGRADE, 40, 25));
		this.getForgeUpgrades().put(EntityConfiguration.DAMAGE_UPGRADE, new ForUpgrade(2, "épée en acier", 5, EntityConfiguration.DAMAGE_UPGRADE, 40, 25));
		this.getForgeUpgrades().put(EntityConfiguration.ATTACK_SPEED_UPGRADE, new ForUpgrade(3, "apprentissage de l'équilibre", 5, EntityConfiguration.ATTACK_SPEED_UPGRADE, 40, 25));
		this.getForgeUpgrades().put(EntityConfiguration.ATTACK_RANGE_UPGRADE, new ForUpgrade(3, "lame plus longue", 5, EntityConfiguration.ATTACK_RANGE_UPGRADE, 40, 25));
		this.getForgeUpgrades().put(EntityConfiguration.SIGHT_RANGE_UPGRADE, new ForUpgrade(3, "lunette de vue", 5, EntityConfiguration.SIGHT_RANGE_UPGRADE, 40, 25));
	}
	
	public void initHQUpgrades()
	{
		this.getHQUpgrades().put(EntityConfiguration.AGE_UPGRADE, new ForUpgrade(1, "upgrade age tier 2", 1, EntityConfiguration.AGE_UPGRADE, 7, 300));
		this.getHQUpgrades().put(EntityConfiguration.AGE_UPGRADE_2, new ForUpgrade(1, "upgrade age tier 3", 1, EntityConfiguration.AGE_UPGRADE_2, 7, 300));
	}
	
}
