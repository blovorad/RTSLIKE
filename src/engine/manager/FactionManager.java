package engine.manager;

import java.util.AbstractMap;


import java.util.HashMap;

import engine.Faction;

/**
 * 
 * this class contain all faction
 * @author gautier
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