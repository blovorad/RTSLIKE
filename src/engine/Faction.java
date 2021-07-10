package engine;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import configuration.EntityConfiguration;
import factionConfiguration.Barbare;
import factionConfiguration.Empire;
import factionConfiguration.ForUpgrade;
import factionConfiguration.Gaia;
import factionConfiguration.Race;
import factionConfiguration.Royaume;
/**
 * 
 *	this class is use to each faction of the game, stocking a lot of variable
 *	@author gautier
 */
public class Faction {
	/**
	 * current race of the faction
	 */
	private Race race;
	/**
	 * current age of the faction
	 */
	private int age;
	/**
	 * how many building is build
	 */
	private int buildingCount;
	/**
	 * how many unit is alive
	 */
	private int populationCount;
	/**
	 * max of unit can be alive
	 */
	private int maxPopulation;
	
	private int realMaxPopulation;
	/**
	 * how many money faction has
	 */
	private int moneyCount;
	/**
	 * Which upgrade is done
	 */
	private AbstractMap<Integer, ForUpgrade> upgradesDone;
	/**
	 * Which upgrade is currently in research
	 */
	private List<Integer> searchingUpgrades;
	/**
	 * if faction upgrading age need to actualize panel
	 */
	private boolean upgradeAge;
	/**
	 * if faction upgrade stat of unit with forge, need to actualize panel
	 */
	private boolean statUpgrade;
	
	/**
	 * constructor of faction
	 * @param id to know which race you are
	 */
	public Faction(int id, int population, int money) {
		age = 1;
		buildingCount = 0;
		maxPopulation = 10;
		realMaxPopulation = population;
		moneyCount = 100000;
		populationCount = 0;
		upgradesDone = new HashMap<Integer, ForUpgrade>();
		searchingUpgrades = new ArrayList<Integer>();
		upgradeAge = false;
		setStatUpgrade(false);
		
		if(id == 1){
			race = new Royaume();
		}
		else if(id == 2){
			race = new Barbare();
		}
		else if(id == 3){
			race = new Empire();
		}
		else if(id == 4){
			race = new Gaia();
		}
	}
	
	/**
	 * checking if an upgrade who is currently in search need to be abandon, it calls when a building is destroy
	 * @param upgrades
	 */
	public void checkUpgrade(List<Integer> upgrades) {
		for(Integer id : upgrades) {
			if(id >= EntityConfiguration.ARMOR_UPGRADE && id <= EntityConfiguration.AGE_UPGRADE_2) {
				searchingUpgrades.remove(id);
			}
		}
	}
	
	public void reduceMaxPopulation(int reduce) {
		maxPopulation -= reduce;
		if(maxPopulation < 10) {
			maxPopulation = 10;
		}
	}
	
	public void addMaxPopulation(int adding) {
		maxPopulation += adding; 
		if(adding > realMaxPopulation) {
			maxPopulation = realMaxPopulation;
		}
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getBuildingCount() {
		return buildingCount;
	}

	public void setBuildingCount(int buildingCount) {
		this.buildingCount = buildingCount;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}
	

	public int getMaxPopulation() {
		return maxPopulation;
	}
	
	public int getRealMaxPop() {
		return this.realMaxPopulation;
	}

	public void setMaxPopulation(int maxPopulation) {
		this.maxPopulation = maxPopulation;
	}

	public int getMoneyCount() {
		return moneyCount;
	}

	public void setMoneyCount(int moneyCount) {
		this.moneyCount = moneyCount;
	}

	public AbstractMap<Integer, ForUpgrade> getUpgradesDone() {
		return upgradesDone;
	}

	public void setUpgradesDone(AbstractMap<Integer, ForUpgrade> upgradesDone) {
		this.upgradesDone = upgradesDone;
	}

	public int getPopulationCount() {
		return populationCount;
	}

	public void setPopulationCount(int populationCount) {
		this.populationCount = populationCount;
	}

	public List<Integer> getSearchingUpgrades() {
		return searchingUpgrades;
	}

	public void setSearchingUpgrades(List<Integer> searchingUpgrades) {
		this.searchingUpgrades = searchingUpgrades;
	}

	public boolean isUpgradeAge() {
		return upgradeAge;
	}

	public void setUpgradeAge(boolean upgradeAge) {
		this.upgradeAge = upgradeAge;
	}

	public boolean isStatUpgrade() {
		return statUpgrade;
	}

	public void setStatUpgrade(boolean statUpgrade) {
		this.statUpgrade = statUpgrade;
	}
}