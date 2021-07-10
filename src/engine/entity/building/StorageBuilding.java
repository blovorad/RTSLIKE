package engine.entity.building;

import engine.Entity;
import engine.Position;
import engine.manager.GraphicsManager;
import engine.map.Tile;

/**
 * Class qui define les batiment de stockage de ressource.
 * @author maxime
 * @see Entity
 */
public class StorageBuilding extends Building{
	
	/**
	 * Case ou se situe le batiment.
	 */
	private Tile tile;
	
	/**
	 * Quantite de ressource en stock.
	 */
	private int ressourceStock;
	
	/**
	 * Constructeur du batiment ou tous ses parametres sont definis
	 * @param position Position du batiment
	 * @param id Id du batiement
	 * @param description Description du batitment 
	 * @param hpMax Point de vie maximum du batiment
	 * @param faction Faction du batiment
	 * @param tile Case ou se situe le batiment
	 * @param sightRange Champ de vision du batiment
	 * @param graphicsManager Lien vers le GraphicsManager
	 */
	public StorageBuilding(Position position, int id, String description, int hpMax, int faction, Tile tile, int sightRange, GraphicsManager graphicsManager) {
		super(hpMax, hpMax, description, position, id, faction, sightRange, 0, graphicsManager);
		this.setTile(tile);
	}
	
	/**
	 * Methode qui ajoute les ressource des worker dans le stock.
	 * @param ressource Quantite de ressource a ajouter
	 */
	public void addRessource(int ressource) {
		setRessourceStock(getRessourceStock() + ressource);
	}
	
	/**
	 * Methode qui met a zero le stock et envoi les ressource.
	 * @return La quantite de ressource en stock
	 */
	public int takeRessourceStock() {
		int ressource = ressourceStock;
		ressourceStock = 0;
		
		return ressource;
	}
	 /**
	  * Methode apelle dans EntitiesManager et executee a chaque iteration du jeu.
	 * <p>
	 * Elle gere le comportement du batiment et met a jour ses variables.
	  */
	public void update()
	{
		super.update();
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public int getRessourceStock() {
		return ressourceStock;
	}

	public void setRessourceStock(int ressourceStock) {
		this.ressourceStock = ressourceStock;
	}
}