package engine.entity.building;

import engine.Entity;
import engine.Position;
import engine.manager.GraphicsManager;
import engine.map.Tile;

/**
 * Class qui definie un site de construction. Un site de construction se devient le batiment conrrespondant a son id une fois ses hp a 100%.
 * @author gautier
 * @see Entity
 */
public class SiteConstruction extends Building{
	
	/**
	 * Case ou se situe le site de construction.
	 */
	private Tile tile;
	
	/**
	 * Id correspondant au batiment qu'il va devenir.
	 */
	private int buildingId;
	
	/**
	 * Constructeur du site de construction ou tous ses parametres sont definis.
	 * @param buildingId Id du building qu'il va devenir
	 * @param hp Point de vie
	 * @param hpMax Point de vie maximum
	 * @param description Description
	 * @param position Position du site.
	 * @param id Id du site.
	 * @param faction Faction du site.
	 * @param sightRange Champ de vue du site.
	 * @param maxFrame 
	 * @param graphicsManager Lien vers le GraphicsManager
	 * @param tile Tile ou se situe le site.
	 */
	public SiteConstruction(int buildingId, int hp, int hpMax, String description, Position position, int id, int faction, int sightRange, int maxFrame, GraphicsManager graphicsManager, Tile tile) {
		super(hp, hpMax, description, position, id, faction, sightRange, maxFrame, graphicsManager);
		
		this.buildingId = buildingId;
		this.tile = tile;
	}
	
	/**
	 * Methode apelle dans EntitiesManager et executee a chaque iteration du jeu.
	 * <p>
	 * Elle gere le comportement du batiment et met a jour ses variables.
	 */
	public void update() {
		super.update();
		if(this.getHp() >= this.getHpMax()) {
			this.setSelected(false);
			this.setRemove(true);
		}
	}
	
	public int getBuildingId() {
		return buildingId;
	}
	
	public Tile getTile() {
		return this.tile;
	}
}