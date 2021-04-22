package engine.manager;

import java.util.AbstractMap;


import java.util.HashMap;

import engine.Faction;

/**
 * 
 * @author gautier
 * this class contain all faction
 */

public class FactionManager {
	/**
	 * hashMap of faction
	 */
	private AbstractMap<Integer, Faction> factions;
	
	/**
	 * contructor
	 */
	public FactionManager() {
		factions = new HashMap<Integer, Faction>();
	}

	public AbstractMap<Integer, Faction> getFactions() {
		return factions;
	}

	public void setFactions(AbstractMap<Integer, Faction> factions) {
		this.factions = factions;
	}
	
	/**
	 * adding a faction in the hashMap
	 * @param id of the faction
	 * @param faction the current faction to add
	 */
	public void addFaction(int id, Faction faction){
		factions.put(id, faction);
	}
	
	/**
	 * clear the hashMap, used when you quit game
	 */
	public void clean() {
		factions.clear();
	}
}