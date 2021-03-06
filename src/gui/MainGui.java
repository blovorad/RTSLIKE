package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Camera;
import engine.Mouse;
import engine.Position;
import engine.Ressource;
import engine.entity.building.AttackBuilding;
import engine.entity.building.Building;
import engine.entity.building.PopulationBuilding;
import engine.entity.building.ProductionBuilding;
import engine.entity.building.SiteConstruction;
import engine.entity.building.StorageBuilding;
import engine.entity.unit.Fighter;
import engine.entity.unit.Unit;
import engine.entity.unit.Worker;
import engine.manager.AudioManager;
import engine.manager.EntitiesManager;
import engine.manager.GraphicsManager;
import engine.map.Minimap;
import engine.map.Tile;
import engine.math.Collision;
import engine.math.SelectionRect;

/**
 * 
 * @author gautier
 *
 */

public class MainGui extends JFrame implements Runnable
{
	private final static long serialVersionUID = 1L;
	
	/**
	 * dim of the game screen
	 */
	private final static Dimension preferredSize = new Dimension(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
	
	/**
	 * use to print all game
	 */
	private GameDisplay dashboard;
	/**
	 * camera of the game
	 */
	private Camera camera;
	
	private EntitiesManager manager;
	
	private Mouse mouse;
	
	private SelectionRect selectionRectangle;
	
	private AudioManager audioManager;
	/**
	 * use to see if we clic on the minimap to get the camera at the right place
	 */
	private boolean moveMinimap;

	public MainGui()
	{
		super("Game");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		if(GameConfiguration.launchInFullScreen) {
			this.setUndecorated(true);
		}
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		GraphicsManager graphicsManager = new GraphicsManager();
		
		selectionRectangle = new SelectionRect();
		audioManager = new AudioManager();
		
		mouse = new Mouse();
		camera = new Camera(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
		manager = new EntitiesManager(audioManager, camera);
		dashboard = new GameDisplay(camera, manager, mouse, selectionRectangle, audioManager, graphicsManager);
		manager.setDashboard(dashboard);
		
		MouseControls mouseControls = new MouseControls();
		MouseMotion mouseMotion = new MouseMotion();
		dashboard.addMouseListener(mouseControls);
		dashboard.addMouseMotionListener(mouseMotion);
		
		KeyControls keyboardListener = new KeyControls();
		JTextField textField = new JTextField();
		textField.addKeyListener(keyboardListener);
		this.getContentPane().add(textField);
		
		dashboard.setPreferredSize(preferredSize);
		contentPane.add(dashboard, BorderLayout.CENTER);
		
		if(!GameConfiguration.launchInFullScreen) {
			pack();
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setPreferredSize(preferredSize);
		//System.out.println("resolution: " + GameConfiguration.WINDOW_WIDTH + "x" + GameConfiguration.WINDOW_HEIGHT);
		moveMinimap = false;
	}

	@Override
	/**
	 * core method of the game it will call update and draw, exit if we quit
	 */
	public void run() 
	{
		while(true)
		{
			try 
			{
				Thread.sleep(GameConfiguration.GAME_SPEED);
			}catch (InterruptedException e) 
			{
				System.out.println(e.getMessage());
			}
			if(dashboard.getState() == GameConfiguration.INGAME) {
				manager.update();
				camera.update();
			}
			audioManager.update();
			dashboard.update();
			dashboard.repaint();
		}
	}
	
	/**
	 * 
	 * @author gautier
	 * key control nothing to say
	 */
	private class KeyControls implements KeyListener {

		@Override
		public void keyPressed(KeyEvent event) 
		{
			char keyChar = event.getKeyChar();
			switch (keyChar) 
			{
				case 'q':
					if(selectionRectangle.isActive() == false)
					{
						camera.move(-15, camera.getSpeed().getVy());
					}
					break;
					
				case 'd':
					if(selectionRectangle.isActive() == false)
					{
						camera.move(15, camera.getSpeed().getVy());
					}
					break;
					
				case 'z':
					if(selectionRectangle.isActive() == false)
					{
						camera.move(camera.getSpeed().getVx(), -15);
					}
					break;
					
				case 's':
					if(selectionRectangle.isActive() == false)
					{
						camera.move(camera.getSpeed().getVx(), 15);
					}
					break;
				case '':
					if(manager.getSelectedAttackBuilding() != null) {
						if(manager.getSelectedAttackBuilding().getFaction() == EntityConfiguration.PLAYER_FACTION) {
							manager.getSelectedAttackBuilding().setHp(0);
						}
					}
					else if(manager.getSelectedProdBuilding() != null) {
						if(manager.getSelectedProdBuilding().getFaction() == EntityConfiguration.PLAYER_FACTION) {
							manager.getSelectedProdBuilding().setHp(0);
						}
					}
					else if(manager.getSelectedStorageBuilding() != null) {
						if(manager.getSelectedStorageBuilding().getFaction() == EntityConfiguration.PLAYER_FACTION) {
							manager.getSelectedStorageBuilding().setHp(0);
						}
					}
					else if(manager.getSelectedPopulationBuilding() != null) {
						if(manager.getSelectedPopulationBuilding().getFaction() == EntityConfiguration.PLAYER_FACTION) {
							manager.getSelectedPopulationBuilding().setHp(0);
						}
					}
					else if(manager.getSelectedSiteConstruction() != null) {
						if(manager.getSelectedSiteConstruction().getFaction() == EntityConfiguration.PLAYER_FACTION) {
							manager.getSelectedSiteConstruction().setHp(0);
						}
					}
					else if(manager.getSelectedFighters().isEmpty() == false) {
						if(manager.getSelectedFighters().get(0).getFaction() == EntityConfiguration.PLAYER_FACTION) {
							manager.getSelectedFighters().get(0).setHp(0);
						}
					}
					else if(manager.getSelectedWorkers().isEmpty() == false) {
						if(manager.getSelectedWorkers().get(0).getFaction() == EntityConfiguration.PLAYER_FACTION) {
							manager.getSelectedWorkers().get(0).setHp(0);
						}
					}
					break;
					
				default:
					break;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) 
		{

		}

		@Override
		public void keyReleased(KeyEvent event) 
		{
			char keyChar = event.getKeyChar();
			switch (keyChar) 
			{
				case 'q':
					camera.move(0, camera.getSpeed().getVy());
					break;
					
				case 'd':
					camera.move(0, camera.getSpeed().getVy());
					break;
					
				case 'z':
					camera.move(camera.getSpeed().getVx(), 0);
					break;
					
				case 's':
					camera.move(camera.getSpeed().getVx(), 0);
					break;
					
				default:
					break;
			}
		}
	}
	
	/**
	 * 
	 * @author gautier
	 * core class use to check what is selected or make move entity
	 */
	private class MouseControls implements MouseListener 
	{	
		public Building checkBuilding(int mouseX, int mouseY) {
			Building building = null;
			Position destination = new Position(mouseX, mouseY);
			
			List<StorageBuilding> storageBuildings = manager.getStorageBuildings();
			List<AttackBuilding> attackBuildings = manager.getAttackBuildings();
			List<ProductionBuilding> prodBuildings = manager.getProdBuildings();
			
			if(building == null){
				for(StorageBuilding storage : storageBuildings){
					Position storagePosition = new Position(storage.getPosition().getX(),  storage.getPosition().getY());
					
					if(Collision.collideEntity(destination, storagePosition)){
						building = storage;
						break;
					}
				}
			}
			
			if(building == null){
				for(AttackBuilding attack : attackBuildings ){
					Position  attackPosition = new Position(attack.getPosition().getX(), attack.getPosition().getY());
					
					if(Collision.collideEntity(destination, attackPosition)){
						building = attack;
						break;
					}
				}
			}
			
			if(building == null){
				for(ProductionBuilding prod : prodBuildings ){
					Position prodPosition = new Position(prod.getPosition().getX(), prod.getPosition().getY());
					
					if(Collision.collideEntity(destination, prodPosition)){
						building = prod;
						break;
					}
				}
			}
			
			return building;
		}
		
		public SiteConstruction checkSiteConstruction(int mouseX, int mouseY) {
			SiteConstruction sc = null;
			Position destination = new Position(mouseX, mouseY);
			
			List<SiteConstruction> siteConstructions = manager.getSiteConstructions();
			
			for( SiteConstruction siteConstruction : siteConstructions) {
				Position siteConstructionPosition = new Position(siteConstruction.getPosition().getX(), siteConstruction.getPosition().getY());
				
				if(Collision.collideEntity(destination, siteConstructionPosition)){
					sc = siteConstruction;
					break;
				}
			}
			
			return sc;
		}
		
		public StorageBuilding checkStorageBuilding(int mouseX, int mouseY) {
			StorageBuilding storageBuilding = null;
			Position destination = new Position(mouseX, mouseY);
			
			List<StorageBuilding> storageBuildings = manager.getPlayerStorageBuildings();
			for(StorageBuilding storage : storageBuildings){
				Position storagePosition = new Position(storage.getPosition().getX(),  storage.getPosition().getY());
				if(Collision.collideEntity(destination, storagePosition)){
					storageBuilding = storage;
					break;
				}
			}
			
			return storageBuilding;
		}
		
		/**
		 * to see if we clic on unit
		 * @param mouseX pos of the clic
		 * @param mouseY pos of the clic
		 * @return
		 */
		public Unit checkUnit(int mouseX, int mouseY)
		{	
			List<Fighter> fighters = manager.getFighters();
			List<Worker> workers = manager.getWorkers();
			
			Unit selectUnit = null;
			
			int x = mouseX;
			int y = mouseY;
			

			if(selectUnit == null)
			{
				for( Fighter fighter: fighters )
				{
					Position  fighterPosition = new Position(fighter.getPosition().getX(), fighter.getPosition().getY());
					
					if(x > fighterPosition.getX() && x < fighterPosition.getX() + EntityConfiguration.UNIT_SIZE && y > fighterPosition.getY() && y < fighterPosition.getY() + EntityConfiguration.UNIT_SIZE)
					{
						selectUnit = fighter;
						break;
					}
				}
			}
			
			if(selectUnit == null)
			{
				for( Worker worker: workers )
				{
					Position workerPosition  = new Position(worker.getPosition().getX(), worker.getPosition().getY());
					
					if(x > workerPosition.getX() && x < workerPosition.getX() + EntityConfiguration.UNIT_SIZE && y > workerPosition.getY() && y < workerPosition.getY() + EntityConfiguration.UNIT_SIZE)
					{
						selectUnit = worker;
						break;
					}
				}
			}
			
			return selectUnit;
		}
		
		/**
		 * to see if we clic on ressource
		 * @param mouseX
		 * @param mouseY
		 * @return
		 */
		public Ressource checkRessource(int mouseX, int mouseY)
		{
			int x = mouseX;
			int y = mouseY;
			
			Position destination = new Position(x,y);
			
			List<Ressource> ressources = manager.getRessources();
			
			Ressource selectRessource = null;
			
			if(selectRessource == null)
			{
				for(Ressource ressource : ressources)
				{
					Position ressourcePosition = ressource.getPosition();
					
					if(Collision.collideEntity(destination, ressourcePosition))
					{
						selectRessource = ressource;
						break;
					}
				}
			}
			return selectRessource;
		}
		
		/**
		 * if we do a double clic or a simple clic without drag the mouse, checking what we selected
		 * @param mouseX pos clic
		 * @param mouseY pos clic
		 * @param clickCount if we do a double clic
		 */
		public void checkWhatIsSelected(int mouseX, int mouseY, int clickCount)
		{
			manager.clearSelectedBuildings();
			manager.clearSelectedUnit();
			manager.clearSelectedRessource();
			manager.clearSelectedWorker();
			manager.clearSelectedFighter();
			manager.clearSelectedSiteConstruction();
			manager.clearSelectedPopulationBuilding();
			dashboard.setDescriptionPanelStandard();

			int x = mouseX + camera.getX();
			int y = mouseY + camera.getY();
			
			boolean noUnitSelected = true;
			boolean workerSelected = false;
			List<Worker> listWorkers = manager.getPlayerWorkers();
			List<Fighter> listFighters = manager.getPlayerFighters();
			
			for(Worker worker : listWorkers) {
				Position pos = worker.getPosition();
				if(x > pos.getX() && x < pos.getX() + EntityConfiguration.UNIT_SIZE && y > pos.getY() && y < pos.getY() + EntityConfiguration.UNIT_SIZE) {
					dashboard.setDescriptionPanelForWorker(worker);
					noUnitSelected = false;
					workerSelected = true;
					manager.addSelectedWorker(worker);
					manager.addSelectedUnit(worker);
					if(clickCount > 1) {
						for(Worker worker2 : listWorkers) {
							if(worker != worker2 && Collision.collideForFx(worker2, camera)) {
								manager.addSelectedWorker(worker2);
								manager.addSelectedUnit(worker2);
							}
						}
					}
					break;
				}
			}
			
			if(workerSelected == false) {
				int unitIdSelected = -1;
				for(Fighter fighter : listFighters) {
					Position pos = fighter.getPosition();
					if(x > pos.getX() && x < pos.getX() + EntityConfiguration.UNIT_SIZE && y > pos.getY() && y < pos.getY() + EntityConfiguration.UNIT_SIZE) {
						noUnitSelected = false;
						manager.addSelectedFighter(fighter);
						manager.addSelectedUnit(fighter);
						unitIdSelected = fighter.getId();
						if(clickCount > 1) {
							for(Fighter fighter2 : listFighters) {
								if(fighter != fighter2 && unitIdSelected == fighter2.getId() && Collision.collideForFx(fighter2, camera)) {
									manager.addSelectedFighter(fighter2);
									manager.addSelectedUnit(fighter2);
								}
							}
						}
						break;
					}
				}
				if(manager.getSelectedFighters().isEmpty() == false) {
					dashboard.setDescriptionPanelForUnit(manager.getSelectedFighters().get(0), manager.getSelectedFighters());
				}
			}
	
			if(noUnitSelected == true){
				boolean noBuildingSelected = true;
				List<ProductionBuilding> listProdBuildings = manager.getProdBuildings();
				
				for(ProductionBuilding building : listProdBuildings){

					if((x > building.getPosition().getX() && x < building.getPosition().getX() + GameConfiguration.TILE_SIZE)
											&& (y > building.getPosition().getY() && y < building.getPosition().getY() + GameConfiguration.TILE_SIZE)){
						List<Integer> searchingUpgrades = manager.getFactionManager().getFactions().get(building.getFaction()).getSearchingUpgrades();
						manager.setSelectedProdBuilding(building);
						dashboard.setDescriptionPanelForBuilding(building, searchingUpgrades);
						noBuildingSelected = false;
						break;
					}
				}
				if(noBuildingSelected == true){
					List<AttackBuilding> listAttackBuildings = manager.getAttackBuildings();
					for(AttackBuilding building : listAttackBuildings){

						if((x > building.getPosition().getX() && x < building.getPosition().getX() + GameConfiguration.TILE_SIZE)
												&& (y > building.getPosition().getY() && y < building.getPosition().getY() + GameConfiguration.TILE_SIZE)){
							manager.setSelectedAttackBuilding(building);
							dashboard.setDescriptionPanelForBuilding(building);
							noBuildingSelected = false;
							break;
						}
					}
				}
				if(noBuildingSelected == true){
					List<StorageBuilding> listStorageBuildings = manager.getStorageBuildings();
					for(StorageBuilding building : listStorageBuildings){

						if((x > building.getPosition().getX() && x < building.getPosition().getX() + GameConfiguration.TILE_SIZE)
												&& (y > building.getPosition().getY() && y < building.getPosition().getY() + GameConfiguration.TILE_SIZE)){
							manager.setSelectedStorageBuilding(building);
							dashboard.setDescriptionPanelForBuilding(building);
							noBuildingSelected = false;
							break;
						}
					}
				}
				
				if(noBuildingSelected == true){
					List<PopulationBuilding> listPopulationBuilding = manager.getPopulationBuildings();
					for(PopulationBuilding building : listPopulationBuilding){

						if((x > building.getPosition().getX() && x < building.getPosition().getX() + GameConfiguration.TILE_SIZE)
												&& (y > building.getPosition().getY() && y < building.getPosition().getY() + GameConfiguration.TILE_SIZE)){
							manager.setSelectedPopulationBuilding(building);
							dashboard.setDescriptionPanelForBuilding(building);
							noBuildingSelected = false;
							break;
						}
					}
				}
				
				if(noBuildingSelected == true){
					List<Ressource> listRessources = manager.getRessources();
					for(Ressource ressource : listRessources){
						if((x > ressource.getPosition().getX() && x < ressource.getPosition().getX() + GameConfiguration.TILE_SIZE)
								&& (y > ressource.getPosition().getY() && y < ressource.getPosition().getY() + GameConfiguration.TILE_SIZE)){
							dashboard.setDescriptionPanelForRessource(ressource);
							ressource.setSelected(true);
							manager.setSelectedRessource(ressource);
							noBuildingSelected = false;
							break;
						}
					}
				}
				
				if(noBuildingSelected == true) {
					List<SiteConstruction> listSiteConstructions = manager.getSiteConstructions();
					for(SiteConstruction sc : listSiteConstructions) {
						if((x > sc.getPosition().getX() && x < sc.getPosition().getX() + GameConfiguration.TILE_SIZE)
								&& (y > sc.getPosition().getY() && y < sc.getPosition().getY() + GameConfiguration.TILE_SIZE)) {
							dashboard.setDescriptionPanelForSiteConstruction(sc);
							sc.setSelected(true);
							manager.setSelectedSiteConstruction(sc);
							noBuildingSelected = false;
							break;
						}
					}
				}
			}
			else {
				audioManager.startFx(4);
			}
		}
		
		/**
		 * if we do a simple clic, and drag the mouse
		 * @param x pos clic
		 * @param y pos clic
		 * @param w width of the rect
		 * @param h height of the rect
		 */
		public void checkWhatIsSelected(int x, int y, int w, int h)
		{
			manager.clearSelectedBuildings();
			manager.clearSelectedUnit();
			manager.clearSelectedRessource();
			manager.clearSelectedWorker();
			manager.clearSelectedFighter();
			manager.clearSelectedPopulationBuilding();
			manager.clearSelectedSiteConstruction();
			dashboard.setDescriptionPanelStandard();
			
			SelectionRect rect = new SelectionRect(x, y, w, h);
			
			boolean noUnitSelected = true;
			List<Worker> listWorkers = manager.getPlayerWorkers();
			List<Fighter> listFighters = manager.getPlayerFighters();
			
			for(Worker worker : listWorkers) {
				if(Collision.collideUnit(worker.getPosition(), rect, camera) == true){
					manager.addSelectedUnit(worker);
					manager.addSelectedWorker(worker);
					dashboard.setDescriptionPanelForWorker(worker);
					noUnitSelected = false;
				}
			}
			
			for(Fighter fighter : listFighters) {
				if(Collision.collideUnit(fighter.getPosition(), rect, camera) == true){
					manager.addSelectedUnit(fighter);
					manager.addSelectedFighter(fighter);
					noUnitSelected = false;
				}
			}
			if(manager.getSelectedFighters().isEmpty() == false) {
				dashboard.setDescriptionPanelForUnit(manager.getSelectedFighters().get(0), manager.getSelectedFighters());
			}
			
			if(noUnitSelected == true){
				boolean noBuildingSelected = true;
				List<ProductionBuilding> listProdBuildings = manager.getProdBuildings();
				
				for(ProductionBuilding building : listProdBuildings){

					if(Collision.collide(building.getPosition(), rect, camera)){
						List<Integer> searchingUpgrades = manager.getFactionManager().getFactions().get(building.getFaction()).getSearchingUpgrades();
						building.setSelected(true);
						manager.setSelectedProdBuilding(building);
						dashboard.setDescriptionPanelForBuilding(building, searchingUpgrades);
						noBuildingSelected = false;
						break;
					}
				}
				if(noBuildingSelected == true){
					List<AttackBuilding> listAttackBuildings = manager.getAttackBuildings();
					for(AttackBuilding building : listAttackBuildings){

						if(Collision.collide(building.getPosition(), rect, camera)){
							building.setSelected(true);
							manager.setSelectedAttackBuilding(building);
							dashboard.setDescriptionPanelForBuilding(building);
							noBuildingSelected = false;
							break;
						}
					}
				}
				if(noBuildingSelected == true){
					List<StorageBuilding> listStorageBuildings = manager.getStorageBuildings();
					for(StorageBuilding building : listStorageBuildings)
					{

						if(Collision.collide(building.getPosition(), rect, camera)){
							building.setSelected(true);
							manager.setSelectedStorageBuilding(building);
							dashboard.setDescriptionPanelForBuilding(building);
							noBuildingSelected = false;
							break;
						}
					}
				}
				
				if(noBuildingSelected == true){
					List<PopulationBuilding> listPopulationBuilding = manager.getPopulationBuildings();
					for(PopulationBuilding building : listPopulationBuilding){

						if(Collision.collide(building.getPosition(), rect, camera)){
							building.setSelected(true);
							manager.setSelectedPopulationBuilding(building);
							dashboard.setDescriptionPanelForBuilding(building);
							noBuildingSelected = false;
							break;
						}
					}
				}
				
				if(noBuildingSelected == true){
					List<Ressource> listRessources = manager.getRessources();
					for(Ressource ressource : listRessources){
						if(Collision.collide(ressource.getPosition(), rect, camera)){
							dashboard.setDescriptionPanelForRessource(ressource);
							ressource.setSelected(true);
							manager.setSelectedRessource(ressource);
							noBuildingSelected = false;
							break;
						}
					}
				}
				
				if(noBuildingSelected == true) {
					List<SiteConstruction> listSiteConstructions = manager.getSiteConstructions();
					for(SiteConstruction sc : listSiteConstructions) {
						if(Collision.collide(sc.getPosition(), rect, camera)) {
							dashboard.setDescriptionPanelForSiteConstruction(sc);
							sc.setSelected(true);
							manager.setSelectedSiteConstruction(sc);
							noBuildingSelected = false;
							break;
						}
					}
				}
			}
			else {
				audioManager.startFx(4);
			}
		}
		
		
		@Override
		public void mouseClicked(MouseEvent e) 
		{
		
		}

		@Override
		public void mousePressed(MouseEvent e) 
		{
			if(dashboard.getState() == GameConfiguration.INGAME)
			{
				int mouseX = e.getX() + camera.getX();
				int mouseY = e.getY() + camera.getY();
				JPanel leftDownPanel = dashboard.getDescriptionPanel();
				JPanel rightDownPanel = dashboard.getMinimapPanel();
				/**
				 * left clic
				 */
				if(e.getButton() == 1)
				{
					Minimap minimap = dashboard.getMinimap();
					
					/**
					 * if we clic on the minimap we doing something like, moving to the point
					 */
					if((e.getX() > minimap.getFirstGridXOfMap() && e.getX() < minimap.getFirstGridXOfMap() + (minimap.getGridMapWidth() * GameConfiguration.COLUMN_COUNT)) && (e.getY() > minimap.getFirstGridYOfMap() && e.getY() < minimap.getFirstGridYOfMap() + (minimap.getGridMapHeight() * GameConfiguration.LINE_COUNT))) {
						int x = e.getX() - minimap.getFirstGridXOfMap();
						int y = e.getY() - minimap.getFirstGridYOfMap();
						x /= minimap.getGridMapWidth();
						y /= minimap.getGridMapHeight();

						x *= GameConfiguration.TILE_SIZE;
						y *= GameConfiguration.TILE_SIZE;
						
						x -= camera.getScreenWidth() / 2;
						y -= camera.getScreenHeight() / 2;
						
						if(x < 0){
							x = 0;
						}
						else if(x > GameConfiguration.TILE_SIZE * GameConfiguration.COLUMN_COUNT - camera.getScreenWidth()){
							x = GameConfiguration.TILE_SIZE * GameConfiguration.COLUMN_COUNT - camera.getScreenWidth();
						}
						
						if(y < 0){
							y = 0;
						}
						else if(y > GameConfiguration.TILE_SIZE * GameConfiguration.LINE_COUNT - camera.getScreenHeight()){
							y = GameConfiguration.TILE_SIZE * GameConfiguration.LINE_COUNT - camera.getScreenHeight();
						}
						
						Position p = new Position(x, y);
						camera.setX(p.getX());
						camera.setY(p.getY());
						moveMinimap = true;
					}
					/**
					 * if we don't clic on panel we can do something, like building a building !
					 */
					if(!Collision.collidePanel(leftDownPanel, e.getX(), e.getY()) && !Collision.collidePanelMinimap(rightDownPanel, e.getX(), e.getY())) {
						if(mouse.getId() > -1){
							Tile tile = dashboard.getMap().getTile((e.getY() + camera.getY()) / GameConfiguration.TILE_SIZE, (e.getX() + camera.getX()) / GameConfiguration.TILE_SIZE);
							if(tile.isSolid() == false){
								int x = ((e.getX() + camera.getX()) / GameConfiguration.TILE_SIZE) * GameConfiguration.TILE_SIZE;
								int y = ((e.getY() + camera.getY()) / GameConfiguration.TILE_SIZE) * GameConfiguration.TILE_SIZE;
								
								List<Worker> listWorkers = manager.getSelectedWorkers();
								
								Position p = new Position(x, y);
								
								SiteConstruction constructionSite = manager.createConstructionSite(mouse.getId(), EntityConfiguration.PLAYER_FACTION, p, tile);
								
								for(Worker worker : listWorkers) {
									worker.setFinalDestination(constructionSite.getPosition());
									worker.setCurrentAction(EntityConfiguration.CONSTRUCT);
									worker.setTarget(constructionSite);
									worker.setSiteConstruction(constructionSite);
								}
								manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).setMoneyCount(manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getMoneyCount() - mouse.getCost());
								mouse.setId(-1);
							}
							audioManager.startFx(3);
							selectionRectangle.setActive(false);
						}
						else if(selectionRectangle.isActive() == false){
							selectionRectangle.setActive(true);
							selectionRectangle.setX(e.getX());
							selectionRectangle.setY(e.getY());
							selectionRectangle.setW(0);
							selectionRectangle.setH(0);
						}
					}
				}
				/**
				 * right clic
				 */
				else if(e.getButton() == 3){
					Minimap minimap = dashboard.getMinimap();
					List<Unit> listSelectedUnit = manager.getSelectedUnits();	
					
					/**
					 * if we don't clic on panel, depending of what we selected it makes unit move or attack, worker go to ressource, undo the current building selected to build
					 */
					if(!Collision.collidePanel(leftDownPanel, e.getX(), e.getY()) && !Collision.collidePanelMinimap(rightDownPanel, e.getX(), e.getY())) {
						if(mouse.getId() > -1) {
							mouse.setId(-1);
						}
						else {
							List<Worker> listWorkers = manager.getSelectedWorkers();
							List<Fighter> listFighters = manager.getSelectedFighters();
							SiteConstruction sc = checkSiteConstruction(mouseX, mouseY);
							Building building = null;
							Unit target = null;
							Ressource ressource = null;
							if(sc == null) {
								building = checkBuilding(mouseX, mouseY);
							}
							if(sc == null && building == null) {
								target = checkUnit(mouseX, mouseY);
							}
							if(sc == null && building == null && target == null) {
								ressource  = checkRessource(mouseX, mouseY);
							}
							boolean workerAction = false;
							boolean fighterAction = false;
							boolean unitMoving = false;
							
							if(listWorkers.isEmpty() == false) {
								if(ressource != null) {
									workerAction = true;
									for(Worker worker : listWorkers) {
										worker.initRessource(ressource);
									}
								}
								else if(building != null) {
									if(building.getFaction() == EntityConfiguration.PLAYER_FACTION) {
										workerAction = true;
										if(building.getId() == EntityConfiguration.STORAGE  && building.getHp() >= building.getHpMax()) {
											StorageBuilding storageBuilding = checkStorageBuilding(mouseX, mouseY);
											for(Worker worker : listWorkers) {
												if(worker.getQuantityRessource() > 0) {
													worker.setStorageBuilding(storageBuilding);
													worker.setRessource(null);
													worker.setSiteConstruction(null);
													worker.setTarget(storageBuilding);
													worker.setFinalDestination(storageBuilding.getPosition());
													worker.setCurrentAction(EntityConfiguration.HARVEST);
												}
											}
										}
										else {
											for(Worker worker : listWorkers) {
												worker.setBuilding(building);
												worker.setRessource(null);
												worker.setSiteConstruction(null);
												worker.setTarget(building);
												worker.setFinalDestination(building.getPosition());
												worker.setCurrentAction(EntityConfiguration.REPAIR);
											}
										}
									}
								}
								else if(sc != null) {
									if(sc.getFaction() == EntityConfiguration.PLAYER_FACTION && sc.getHp() < sc.getHpMax()) {
										workerAction = true;
										for(Worker worker : listWorkers) {
											worker.setSiteConstruction(sc);
											worker.setRessource(null);
											worker.setBuilding(null);
											worker.setTarget(sc);
											worker.setFinalDestination(sc.getPosition());
											worker.setCurrentAction(EntityConfiguration.CONSTRUCT);
										}
									}
								}
							}
	
							
							if(workerAction == false) {
								ProductionBuilding prodBuilding = manager.getSelectedProdBuilding();
								
								if(prodBuilding != null && prodBuilding.getFaction() == EntityConfiguration.PLAYER_FACTION && prodBuilding.getId() != EntityConfiguration.FORGE) {
									prodBuilding.setRallyPoint(new Position(mouseX, mouseY));
								}
								else if(target == null && sc == null && building == null) {
									
									if((listWorkers.isEmpty() == false || listFighters.isEmpty() == false && manager.getSelectedAttackBuilding() == null && manager.getSelectedSiteConstruction() == null && manager.getSelectedStorageBuilding() == null)){
										for(Worker worker : listWorkers) {
											worker.setFinalDestination(new Position(mouseX, mouseY));
											worker.setCurrentAction(EntityConfiguration.WALK);
											worker.setRessource(null);
											worker.setSiteConstruction(null);
											worker.setBuilding(null);
										}
										
										for(Fighter fighter : listFighters) {
											fighter.setFinalDestination(new Position(mouseX, mouseY));
											fighter.setTarget(null);
											fighter.setTargetUnit(null);
											fighter.setCurrentAction(EntityConfiguration.WALK);
										}
										unitMoving = true;
									}
								}
								else if(target != null){
									for(Unit unit : listSelectedUnit){
										unit.setFinalDestination(target.getPosition());
										unit.setCurrentAction(EntityConfiguration.WALK);
										unit.setTarget(target);
										fighterAction = true;
									}
								}
								else if(building != null && building.getFaction() != EntityConfiguration.PLAYER_FACTION){
									for(Unit unit : listSelectedUnit){
										unit.setFinalDestination(building.getPosition());
										unit.setCurrentAction(EntityConfiguration.WALK);
										unit.setTarget(building);
										fighterAction = true;
									}
								}
								else if(sc != null && sc.getFaction() != EntityConfiguration.PLAYER_FACTION) {
									for(Unit unit : listSelectedUnit){
										unit.setFinalDestination(sc.getPosition());
										unit.setCurrentAction(EntityConfiguration.WALK);
										unit.setTarget(sc);
										fighterAction = true;
									}
								}
							}
							
							if(workerAction == true || unitMoving == true) {
								audioManager.startFx(3);
							}
							else if(fighterAction == true) {
								audioManager.startFx(5);
							}
								
								
								
								/*if(listSelectedUnit.isEmpty() == false && target != null)
								{
									boolean isAttack = false;
									for(Unit unit : listSelectedUnit)
									{
										//System.out.println("ici");
										//unit.calculateSpeed(target.getPosition());
										unit.setFinalDestination(target.getPosition());
										unit.setCurrentAction(EntityConfiguration.WALK);
										unit.setTarget(target);
										if(unit.getFaction() != target.getFaction())
										{
											isAttack = true;
										}
									}
									
									if(isAttack == false) {
										audioManager.startFx(3);
									}
									else {
										audioManager.startFx(5);
									}
								}
								else if(listSelectedUnit.isEmpty() == false && targetUnit != null)
								{
									boolean isAttack = false;
									for(Unit unit : listSelectedUnit)
									{
										//unit.calculateSpeed(targetUnit.getPosition());
										unit.setFinalDestination(targetUnit.getPosition());
										unit.setCurrentAction(EntityConfiguration.WALK);
										unit.setTarget(targetUnit);
										unit.setTargetUnit(targetUnit);
										if(unit.getFaction() != targetUnit.getFaction()) {
											isAttack = true;
										}
									}
									if(isAttack == false) {
										audioManager.startFx(3);
									}
									else {
										audioManager.startFx(5);
									}
								}
								else if(listSelectedUnit.isEmpty() == false && target == null) {
									audioManager.startFx(3);
									for(Unit unit : listSelectedUnit){
										//unit.calculateSpeed(new Position(mouseX, mouseY));
										//System.out.println("LALALLA");
										unit.setFinalDestination(new Position(mouseX, mouseY));
										unit.setTarget(null);
										unit.setTargetUnit(null);
										unit.setCurrentAction(EntityConfiguration.WALK);
									}
								}
								else {
									//ici on fait en sorte que la tour attaque bien la cible qu'on lui montre	
									if(manager.getSelectedAttackBuilding() != null) {
										List<Unit> units = manager.getUnits();
										int x = mouseX + camera.getX();
										int y = mouseY + camera.getY();
										for(Unit unit : units) {
											Position unitPosition = unit.getPosition();
											if(unit.getFaction() == EntityConfiguration.BOT_FACTION) {
												if (x > unitPosition.getX() && x < unitPosition.getX() + EntityConfiguration.UNIT_SIZE && y > unitPosition.getY() && y < unitPosition.getY() + EntityConfiguration.UNIT_SIZE) {
													manager.getSelectedAttackBuilding().setTarget(unit);
													System.out.println("new target : " + manager.getSelectedAttackBuilding().getTarget().getDescription());
													break;
												}
											}
										}
									}
									else {
										//ici on met le point de ralliment pour les batiment de production
										ProductionBuilding building = manager.getSelectedProdBuilding();
										if(building != null && building.getFaction() == EntityConfiguration.PLAYER_FACTION && building.getId() != EntityConfiguration.FORGE) {
											building.setDestination(new Position(mouseX, mouseY));
										}
									}
								}
							}*/
						}
					}
					else {
						/**
						 * if we clic on the minimap make selected unit move to the point
						 */
						if((e.getX() > minimap.getFirstGridXOfMap() && e.getX() < minimap.getFirstGridXOfMap() + (minimap.getGridMapWidth() * GameConfiguration.COLUMN_COUNT)) && (e.getY() > minimap.getFirstGridYOfMap() && e.getY() < minimap.getFirstGridYOfMap() + (minimap.getGridMapHeight() * GameConfiguration.LINE_COUNT))) {
							int x = e.getX() - minimap.getFirstGridXOfMap();
							int y = e.getY() - minimap.getFirstGridYOfMap();
							x /= minimap.getGridMapWidth();
							y /= minimap.getGridMapHeight();

							x *= GameConfiguration.TILE_SIZE;
							y *= GameConfiguration.TILE_SIZE;
							
							Position p = new Position(x, y);
							
							if(listSelectedUnit.isEmpty() == false) {
								audioManager.startFx(3);
								for(Unit unit : listSelectedUnit){
									unit.setFinalDestination(p);
									unit.setTarget(null);
									unit.setTargetUnit(null);
									unit.setCurrentAction(EntityConfiguration.WALK);
								}
							}
							ProductionBuilding building = manager.getSelectedProdBuilding();
							if(building != null && building.getFaction() == EntityConfiguration.PLAYER_FACTION && building.getId() != EntityConfiguration.FORGE) {
								building.setRallyPoint(new Position(mouseX, mouseY));
							}
						}
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(e.getButton() == 1){
				if(dashboard.getState() == GameConfiguration.INGAME) {
					if(selectionRectangle.isActive() == true){
						if(selectionRectangle.getW() == 0 && selectionRectangle.getH() == 0){
							checkWhatIsSelected(e.getX(), e.getY(), e.getClickCount());
						}
						else{
							checkWhatIsSelected(selectionRectangle.getX(), selectionRectangle.getY(), selectionRectangle.getW(), selectionRectangle.getH());
						}
						selectionRectangle.setActive(false);
					}
					moveMinimap = false;
				}
			}
			else if(e.getButton() == 3){
				
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}
	}
	
	private class MouseMotion implements MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			if(dashboard.getState() == GameConfiguration.INGAME) {
				if(moveMinimap) {
					Minimap minimap = dashboard.getMinimap();

					if((e.getX() > minimap.getFirstGridXOfMap() && e.getX() < minimap.getFirstGridXOfMap() + (minimap.getGridMapWidth() * GameConfiguration.COLUMN_COUNT)) && (e.getY() > minimap.getFirstGridYOfMap() && e.getY() < minimap.getFirstGridYOfMap() + (minimap.getGridMapHeight() * GameConfiguration.LINE_COUNT))) {
						int x = e.getX() - minimap.getFirstGridXOfMap();
						int y = e.getY() - minimap.getFirstGridYOfMap();
						x /= minimap.getGridMapWidth();
						y /= minimap.getGridMapHeight();
	
						x *= GameConfiguration.TILE_SIZE;
						y *= GameConfiguration.TILE_SIZE;
								
						x -= camera.getScreenWidth() / 2;
						y -= camera.getScreenHeight() / 2;
								
						if(x < 0){
							x = 0;
						}
						else if(x > GameConfiguration.TILE_SIZE * GameConfiguration.COLUMN_COUNT - camera.getScreenWidth()){
							x = GameConfiguration.TILE_SIZE * GameConfiguration.COLUMN_COUNT - camera.getScreenWidth();
						}
								
						if(y < 0){
							y = 0;
						}
						else if(y > GameConfiguration.TILE_SIZE * GameConfiguration.LINE_COUNT - camera.getScreenHeight()){
							y = GameConfiguration.TILE_SIZE * GameConfiguration.LINE_COUNT - camera.getScreenHeight();
						}
								
						Position p = new Position(x, y);
						camera.setX(p.getX());
						camera.setY(p.getY());
					}
				}
				else {
					int x = e.getX();
					int y = e.getY();
					selectionRectangle.moveSelectionRect(x, y);
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(dashboard.getState() == GameConfiguration.INGAME) {
				int x = e.getX();
				int y = e.getY();
				mouse.setX(x);
				mouse.setY(y);
				
				if(x < camera.getRectX() || x > camera.getRectX() + camera.getRectW() || y < camera.getRectY() || y > camera.getRectY() + camera.getRectH()) {
					double angle = Math.atan2(y - GameConfiguration.WINDOW_HEIGHT / 2, x - GameConfiguration.WINDOW_WIDTH / 2);
					camera.move((int)(20 * Math.cos(angle)), (int)(20 * Math.sin(angle)));
				}
				else {
					camera.move(0, 0);
				}
			}
			
		}
	}
	
	public static void main(String[] args)
	{
		MainGui n = new MainGui();
		
		Thread gameThread = new Thread(n);
		gameThread.start();
	}
}