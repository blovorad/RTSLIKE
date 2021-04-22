package factionConfiguration;

import java.io.File;
import java.util.Scanner;

import configuration.EntityConfiguration;
/**
 * 
 * @author gautier
 *
 */
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
		initForgeUpgrades();
		initHQUpgrades();
		
		setName("Empire");
	}
	
	public void initWorker(String line)
	{
		String[] s2 = line.split(";");
		String splitFormat = ": ";
		this.getPatronWorkers().put(EntityConfiguration.WORKER, new ForWorker(Integer.valueOf(s2[0].split(splitFormat)[1]), Integer.valueOf(s2[1].split(splitFormat)[1]), Integer.valueOf(s2[2].split(splitFormat)[1]), Integer.valueOf(s2[3].split(splitFormat)[1]), Integer.valueOf(s2[4].split(splitFormat)[1]), Integer.valueOf(s2[5].split(splitFormat)[1]), Integer.valueOf(s2[6].split(splitFormat)[1]), Integer.valueOf(s2[7].split(splitFormat)[1]), Integer.valueOf(s2[8].split(splitFormat)[1]), s2[9].split(splitFormat)[1], Integer.valueOf(s2[10].split(splitFormat)[1]), Integer.valueOf(s2[11].split(splitFormat)[1]), Integer.valueOf(s2[12].split(splitFormat)[1]), Integer.valueOf(s2[13].split(splitFormat)[1]), Integer.valueOf(s2[14].split(splitFormat)[1]), Integer.valueOf(s2[15].split(splitFormat)[1]), Integer.valueOf(s2[16].split(splitFormat)[1])));
	}
	
	public void initCavalry(String line)
	{
		String[] s2 = line.split(";");
		String splitFormat = ": ";
		this.getPatronFighters().put(EntityConfiguration.CAVALRY, new ForFighter(Integer.valueOf(s2[0].split(splitFormat)[1]), Integer.valueOf(s2[1].split(splitFormat)[1]), Integer.valueOf(s2[2].split(splitFormat)[1]), Integer.valueOf(s2[3].split(splitFormat)[1]),  Integer.valueOf(s2[4].split(splitFormat)[1]),  Integer.valueOf(s2[5].split(splitFormat)[1]),  Integer.valueOf(s2[6].split(splitFormat)[1]),  Integer.valueOf(s2[7].split(splitFormat)[1]),  Integer.valueOf(s2[8].split(splitFormat)[1]), s2[9].split(splitFormat)[1],  Integer.valueOf(s2[10].split(splitFormat)[1]), Integer.valueOf(s2[11].split(splitFormat)[1])));
	}
	
	public void initInfantry(String line)
	{
		String[] s2 = line.split(";");
		String splitFormat = ": ";

		this.getPatronFighters().put(EntityConfiguration.INFANTRY, new ForFighter(Integer.valueOf(s2[0].split(splitFormat)[1]), Integer.valueOf(s2[1].split(splitFormat)[1]), Integer.valueOf(s2[2].split(splitFormat)[1]), Integer.valueOf(s2[3].split(splitFormat)[1]),  Integer.valueOf(s2[4].split(splitFormat)[1]),  Integer.valueOf(s2[5].split(splitFormat)[1]),  Integer.valueOf(s2[6].split(splitFormat)[1]),  Integer.valueOf(s2[7].split(splitFormat)[1]),  Integer.valueOf(s2[8].split(splitFormat)[1]), s2[9].split(splitFormat)[1],  Integer.valueOf(s2[10].split(splitFormat)[1]), Integer.valueOf(s2[11].split(splitFormat)[1])));
	}
	
	public void initArcher(String line)
	{
		String[] s2 = line.split(";");
		String splitFormat = ": ";
		this.getPatronFighters().put(EntityConfiguration.ARCHER, new ForFighter(Integer.valueOf(s2[0].split(splitFormat)[1]), Integer.valueOf(s2[1].split(splitFormat)[1]), Integer.valueOf(s2[2].split(splitFormat)[1]), Integer.valueOf(s2[3].split(splitFormat)[1]),  Integer.valueOf(s2[4].split(splitFormat)[1]),  Integer.valueOf(s2[5].split(splitFormat)[1]),  Integer.valueOf(s2[6].split(splitFormat)[1]),  Integer.valueOf(s2[7].split(splitFormat)[1]),  Integer.valueOf(s2[8].split(splitFormat)[1]), s2[9].split(splitFormat)[1],  Integer.valueOf(s2[10].split(splitFormat)[1]), Integer.valueOf(s2[11].split(splitFormat)[1])));
	}
	
	public void initSpecial(String line)
	{
		String[] s2 = line.split(";");
		String splitFormat = ": ";
		this.getPatronFighters().put(EntityConfiguration.SPECIAL_UNIT, new ForFighter(Integer.valueOf(s2[0].split(splitFormat)[1]), Integer.valueOf(s2[1].split(splitFormat)[1]), Integer.valueOf(s2[2].split(splitFormat)[1]), Integer.valueOf(s2[3].split(splitFormat)[1]),  Integer.valueOf(s2[4].split(splitFormat)[1]),  Integer.valueOf(s2[5].split(splitFormat)[1]),  Integer.valueOf(s2[6].split(splitFormat)[1]),  Integer.valueOf(s2[7].split(splitFormat)[1]),  Integer.valueOf(s2[8].split(splitFormat)[1]), s2[9].split(splitFormat)[1],  Integer.valueOf(s2[10].split(splitFormat)[1]), Integer.valueOf(s2[11].split(splitFormat)[1])));
	}
	
	public void initForge()
	{
		this.getProductionBuildings().put(EntityConfiguration.FORGE, new ForProductionBuilding(100, 2, this.getForgeUpgrades(), "Forge Empire", 400, 150));
	}
	
	public void initHq()
	{
		this.getProductionBuildings().put(EntityConfiguration.HQ, new ForProductionBuilding(300, 2, this.getHQUpgrades(), "Quartier general Empire", 400, 250));
	}
	
	public void initCastle()
	{
		this.getProductionBuildings().put(EntityConfiguration.CASTLE, new ForProductionBuilding(400, 3, null, "Chateau Empire", 400, 250));
	}
	
	public void initTower()
	{
		this.getAttackBuildings().put(EntityConfiguration.TOWER, new ForAttackBuilding(150, 400, 1, 80 ,10 ,1, "Tour Empire", 175));
	}
	
	public void initRessourceStockage()
	{
		this.getStorageBuildings().put(EntityConfiguration.STORAGE, new ForStorageBuilding(50 , 1, "Stockage Empire", 300, 50));
	}
	
	public void initBarrack()
	{
		this.getProductionBuildings().put(EntityConfiguration.BARRACK, new ForProductionBuilding(125, 1, null, "Caserne Empire", 400, 100));
	}
	
	public void initArchery()
	{
		this.getProductionBuildings().put(EntityConfiguration.ARCHERY, new ForProductionBuilding(125, 2, null, "Archerie Empire", 400, 100));
	}
	
	public void initStable()
	{
		this.getProductionBuildings().put(EntityConfiguration.STABLE, new ForProductionBuilding(140, 2, null, "Ecurie Empire", 400, 125));
	}
	
	public void initForgeUpgrades()
	{
		this.getForgeUpgrades().put(EntityConfiguration.ARMOR_UPGRADE, new ForUpgrade(2, "armure en fer", 1, EntityConfiguration.ARMOR_UPGRADE, 4, 75));
		this.getForgeUpgrades().put(EntityConfiguration.DAMAGE_UPGRADE, new ForUpgrade(2, "�p�e en acier", 1, EntityConfiguration.DAMAGE_UPGRADE, 4, 75));
		this.getForgeUpgrades().put(EntityConfiguration.ATTACK_SPEED_UPGRADE, new ForUpgrade(3, "apprentissage de l'�quilibre", 5, EntityConfiguration.ATTACK_SPEED_UPGRADE, 4, 75));
		this.getForgeUpgrades().put(EntityConfiguration.ATTACK_RANGE_UPGRADE, new ForUpgrade(3, "lame plus longue", 1, EntityConfiguration.ATTACK_RANGE_UPGRADE, 4, 75));
		this.getForgeUpgrades().put(EntityConfiguration.SIGHT_RANGE_UPGRADE, new ForUpgrade(3, "lunette de vue", 75, EntityConfiguration.SIGHT_RANGE_UPGRADE, 4, 75));
	}
	
	public void initHQUpgrades()
	{
		this.getHQUpgrades().put(EntityConfiguration.AGE_UPGRADE, new ForUpgrade(1, "upgrade age tier 2", 1, EntityConfiguration.AGE_UPGRADE, 7, 300));
		this.getHQUpgrades().put(EntityConfiguration.AGE_UPGRADE_2, new ForUpgrade(1, "upgrade age tier 3", 1, EntityConfiguration.AGE_UPGRADE_2, 7, 500));
	}
	
}
