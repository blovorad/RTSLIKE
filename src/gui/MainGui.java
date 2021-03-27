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
import javax.swing.JTextField;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Camera;
import engine.Entity;
import engine.Mouse;
import engine.Position;
import engine.Ressource;
import engine.entity.building.AttackBuilding;
import engine.entity.building.ProductionBuilding;
import engine.entity.building.StorageBuilding;
import engine.entity.unit.Fighter;
import engine.entity.unit.Unit;
import engine.entity.unit.Worker;
import engine.manager.AudioManager;
import engine.manager.EntitiesManager;
import engine.manager.GraphicsManager;
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
	
	private final static Dimension preferredSize = new Dimension(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
	
	private GameDisplay dashboard;
	
	private Camera camera;
	
	private EntitiesManager manager;
	
	private Mouse mouse;
	
	private SelectionRect selectionRectangle;
	
	private AudioManager audioManager;

	public MainGui()
	{
		super("Game");
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		GraphicsManager graphicsManager = new GraphicsManager();
		
		selectionRectangle = new SelectionRect();
		audioManager = new AudioManager();
		
		mouse = new Mouse();
		manager = new EntitiesManager(audioManager);
		camera = new Camera(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
		dashboard = new GameDisplay(camera, manager, mouse, selectionRectangle, audioManager, graphicsManager);
		
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
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setPreferredSize(preferredSize);
		System.out.println("resolution: " + GameConfiguration.WINDOW_WIDTH + "x" + GameConfiguration.WINDOW_HEIGHT);
	}

	@Override
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
			manager.update();
			camera.update();
			audioManager.update();
			dashboard.update();
			dashboard.repaint();
		}
	}
	
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
	
	public Container getContent() {
		// TODO Auto-generated method stub
		return this.getContentPane();
	}
	
	//méthode pour vérifier se trouve a l'endroit ou l'on fait click droit
	private class MouseControls implements MouseListener 
	{
		public Entity checkEntity(int mouseX, int mouseY)
		{
			int x = mouseX + camera.getX();
			int y = mouseY + camera.getY();
			
			Position destination = new Position(x,y);
			
			Entity selectEntity = null;
			
			List<StorageBuilding> storagesBuilding = manager.getStorageBuildings();
			List<AttackBuilding> attackBuildings = manager.getAttackBuildings();
			List<ProductionBuilding> prodBuildings = manager.getProdBuildings();
			List<Fighter> fighters = manager.getFighters();
			List<Worker> workers = manager.getWorkers();
			
			/*List<Ressource> ressources = manager.getRessources();
			
			Ressource selectRessource = null;
			
			if(selectRessource == null)
			{
				for(Ressource ressource : ressources)
				{
					Position ressourcePosition = new Position(ressource.getPosition().getX(),  ressource.getPosition().getY());
					
					if(destination.inTile(ressourcePosition))
					{
						selectEntity = ressource;
						break;
					}
				}
			}*/
			
			
			if(selectEntity == null)
			{
				for(StorageBuilding storage : storagesBuilding)
				{
					Position storagePosition = new Position(storage.getPosition().getX(),  storage.getPosition().getY());
					
					if(destination.inTile(storagePosition))
					{
						selectEntity = storage;
						break;
					}
				}
			}
			
			if(selectEntity == null)
			{
				for(AttackBuilding attack : attackBuildings )
				{
					Position  attackPosition = new Position(attack.getPosition().getX(), attack.getPosition().getY());
					
					if(destination.inTile(attackPosition))
					{
						selectEntity = attack;
						break;
					}
				}
			}
			
			if(selectEntity == null)
			{
				for(ProductionBuilding prod : prodBuildings )
				{
					Position prodPosition = new Position(prod.getPosition().getX(), prod.getPosition().getY());
					
					if(destination.inTile(prodPosition))
					{
						selectEntity = prod;
						break;
					}
				}
			}
			
			if(selectEntity == null)
			{
				for( Fighter fighter: fighters )
				{
					Position  fighterPosition = new Position(fighter.getPosition().getX(), fighter.getPosition().getY());
					
					if(x > fighterPosition.getX() && x < fighterPosition.getX() + EntityConfiguration.UNIT_SIZE && y > fighterPosition.getY() && y < fighterPosition.getY() + EntityConfiguration.UNIT_SIZE)
					{
						selectEntity = fighter;
						break;
					}
				}
			}
			
			if(selectEntity == null)
			{
				for( Worker worker: workers )
				{
					Position workerPosition  = new Position(worker.getPosition().getX(), worker.getPosition().getY());
					
					if(x > workerPosition.getX() && x < workerPosition.getX() + EntityConfiguration.UNIT_SIZE && y > workerPosition.getY() && y < workerPosition.getY() + EntityConfiguration.UNIT_SIZE)
					{
						selectEntity = worker;
						break;
					}
				}
			}
			
		return selectEntity;	
		}
		
		public Ressource checkRessource(int mouseX, int mouseY)
		{
			int x = mouseX + camera.getX();
			int y = mouseY + camera.getY();
			
			Position destination = new Position(x,y);
			
			List<Ressource> ressources = manager.getRessources();
			
			Ressource selectRessource = null;
			
			if(selectRessource == null)
			{
				for(Ressource ressource : ressources)
				{
					Position ressourcePosition = ressource.getPosition();
					
					if(Collision.collideRessource(destination, ressourcePosition))
					{
						selectRessource = ressource;
						break;
					}
				}
			}
			return selectRessource;
		}
		
		public void checkWhatIsSelected(int mouseX, int mouseY, int clickCount)
		{
			manager.clearSelectedBuildings();
			manager.clearSelectedUnit();
			manager.clearSelectedRessource();
			manager.clearSelectedWorker();
			manager.clearSelectedFighter();
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
					if(clickCount > 1) {
						for(Worker worker2 : listWorkers) {
							manager.addSelectedWorker(worker2);
							manager.addSelectedUnit(worker2);
						}
						break;
					}
					else {
						manager.addSelectedWorker(worker);
						manager.addSelectedUnit(worker);
						break;
					}
				}
			}
			
			if(workerSelected == false) {
				for(Fighter fighter : listFighters) {
					Position pos = fighter.getPosition();
					if(x > pos.getX() && x < pos.getX() + EntityConfiguration.UNIT_SIZE && y > pos.getY() && y < pos.getY() + EntityConfiguration.UNIT_SIZE) {
						dashboard.setDescriptionPanelForUnit(fighter);
						noUnitSelected = false;
						if(clickCount > 1) {
							manager.addSelectedFighter(fighter);
							manager.addSelectedUnit(fighter);
						}
						else {
							manager.addSelectedFighter(fighter);
							manager.addSelectedUnit(fighter);
							break;
						}
					}
				}
			}
	
			if(noUnitSelected == true)
			{
				boolean noBuildingSelected = true;
				List<ProductionBuilding> listProdBuildings = manager.getProdBuildings();
				
				for(ProductionBuilding building : listProdBuildings)
				{

					if((x > building.getPosition().getX() && x < building.getPosition().getX() + GameConfiguration.TILE_SIZE)
											&& (y > building.getPosition().getY() && y < building.getPosition().getY() + GameConfiguration.TILE_SIZE))
					{
						List<Integer> searchingUpgrades = manager.getFactionManager().getFactions().get(building.getFaction()).getSearchingUpgrades();
						manager.setSelectedProdBuilding(building);
						dashboard.setDescriptionPanelForBuilding(building, searchingUpgrades);
						noBuildingSelected = false;
						break;
					}
				}
				if(noBuildingSelected == true){
					List<AttackBuilding> listAttackBuildings = manager.getAttackBuildings();
					for(AttackBuilding building : listAttackBuildings)
					{

						if((x > building.getPosition().getX() && x < building.getPosition().getX() + GameConfiguration.TILE_SIZE)
												&& (y > building.getPosition().getY() && y < building.getPosition().getY() + GameConfiguration.TILE_SIZE))
						{
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

						if((x > building.getPosition().getX() && x < building.getPosition().getX() + GameConfiguration.TILE_SIZE)
												&& (y > building.getPosition().getY() && y < building.getPosition().getY() + GameConfiguration.TILE_SIZE))
						{
							manager.setSelectedStorageBuilding(building);
							dashboard.setDescriptionPanelForBuilding(building);
							noBuildingSelected = false;
							break;
						}
					}
				}
				
				if(noBuildingSelected == true)
				{
					List<Ressource> listRessources = manager.getRessources();
					for(Ressource ressource : listRessources)
					{
						if((x > ressource.getPosition().getX() && x < ressource.getPosition().getX() + GameConfiguration.TILE_SIZE)
								&& (y > ressource.getPosition().getY() && y < ressource.getPosition().getY() + GameConfiguration.TILE_SIZE))
						{
							dashboard.setDescriptionPanelForRessource(ressource);
							ressource.setSelected(true);
							manager.setSelectedRessource(ressource);
							break;
						}
					}
				}
			}
		}
		
		public void checkWhatIsSelected(int x, int y, int w, int h)
		{
			manager.clearSelectedBuildings();
			manager.clearSelectedUnit();
			manager.clearSelectedRessource();
			manager.clearSelectedFighter();
			manager.clearSelectedWorker();
			dashboard.setDescriptionPanelStandard();
			
			SelectionRect rect = new SelectionRect(x, y, w, h);
			
			boolean noUnitSelected = true;
			List<Worker> listWorkers = manager.getPlayerWorkers();
			List<Fighter> listFighters = manager.getPlayerFighters();
			
			for(Worker worker : listWorkers) {
				if(Collision.collideUnit(worker.getPosition(), rect, camera) == true)
				{
					manager.addSelectedUnit(worker);
					dashboard.setDescriptionPanelForWorker(worker);
					noUnitSelected = false;
				}
			}
			
			for(Fighter fighter : listFighters) {
				if(Collision.collideUnit(fighter.getPosition(), rect, camera) == true)
				{
					manager.addSelectedUnit(fighter);
					dashboard.setDescriptionPanelForUnit(fighter);
					noUnitSelected = false;
				}
			}
			
			if(noUnitSelected == true)
			{
				boolean noBuildingSelected = true;
				List<ProductionBuilding> listProdBuildings = manager.getProdBuildings();
				
				for(ProductionBuilding building : listProdBuildings)
				{

					if(Collision.collide(building.getPosition(), rect, camera))
					{
						List<Integer> searchingUpgrades = manager.getFactionManager().getFactions().get(building.getFaction()).getSearchingUpgrades();
						manager.setSelectedProdBuilding(building);
						dashboard.setDescriptionPanelForBuilding(building, searchingUpgrades);
						noBuildingSelected = false;
						break;
					}
				}
				if(noBuildingSelected == true){
					List<AttackBuilding> listAttackBuildings = manager.getAttackBuildings();
					for(AttackBuilding building : listAttackBuildings)
					{

						if(Collision.collide(building.getPosition(), rect, camera)){
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
							manager.setSelectedStorageBuilding(building);
							dashboard.setDescriptionPanelForBuilding(building);
							noBuildingSelected = false;
							break;
						}
					}
				}
				
				if(noBuildingSelected == true)
				{
					List<Ressource> listRessources = manager.getRessources();
					for(Ressource ressource : listRessources)
					{
						if(Collision.collide(ressource.getPosition(), rect, camera))
						{
							dashboard.setDescriptionPanelForRessource(ressource);
							ressource.setSelected(true);
							manager.setSelectedRessource(ressource);
							break;
						}
					}
				}
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
				
				if(e.getButton() == 1)
				{

					if(selectionRectangle.isActive() == false)
					{
						selectionRectangle.setActive(true);
						selectionRectangle.setX(e.getX());
						selectionRectangle.setY(e.getY());
						selectionRectangle.setW(0);
						selectionRectangle.setH(0);
						//System.out.println("on a presser");
					}
					
					if(mouse.getId() > -1)
					{
						Tile tile = dashboard.getMap().getTile((e.getX() + camera.getX()) / GameConfiguration.TILE_SIZE, (e.getY() + camera.getY()) / GameConfiguration.TILE_SIZE);
						System.out.println("tuile solide : " + tile.isSolid());
						if(tile.isSolid() == false)
						{
							int x = ((e.getX() + camera.getX()) / GameConfiguration.TILE_SIZE) * GameConfiguration.TILE_SIZE;
							int y = ((e.getY() + camera.getY()) / GameConfiguration.TILE_SIZE) * GameConfiguration.TILE_SIZE;
							
							Position p = new Position(x, y);
							
							manager.createBuilding(mouse.getId(), EntityConfiguration.PLAYER_FACTION, p, tile);
							mouse.setId(-1);
						}
						selectionRectangle.setActive(false);
					}
				}
				else if(e.getButton() == 3)
				{
					if(mouse.getId() > -1) {
						mouse.setId(-1);
					}
					else {
						System.out.print("Coucou");
						
						List<Unit> listSelectedUnit = manager.getSelectedUnits();	
						List<Worker> listWorkers = manager.getSelectedWorkers();
						boolean goingToHarvest = false;// utilise pour dire aller on recolte ducoup le reste est inutile car le clic est productif
						
						if(listWorkers.isEmpty() == false) {
							Ressource ressource  = checkRessource(mouseX, mouseY);
							System.out.println("NOOOOOOOn");
							if(ressource != null) {
								goingToHarvest = true;
								for(Worker worker : listWorkers) 
								{
									//TU METS ICI LE TRUC QUI TE PERMET DE LEUR DIRE QUON A CLIQUER SUR LA RESSOURCE
									worker.initRessource(ressource);
									System.out.println("test");
								}
							}
						}
						
						if(goingToHarvest == false) {
							Entity target = checkEntity(mouseX, mouseY);
							
							if(listSelectedUnit.isEmpty() == false && target != null)
							{
								for(Unit unit : listSelectedUnit)
								{
									unit.setTarget(target);
									unit.calculateSpeed(target.getPosition());
								}
							}
							else if(listSelectedUnit.isEmpty() == false && target == null) {
								for(Unit unit : listSelectedUnit){
									unit.calculateSpeed(new Position(mouseX, mouseY));
									unit.setTarget(null);
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
									if(building != null) {
										building.setDestination(new Position(mouseX, mouseY));
									}
								}
							}
						}
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) 
		{
			if(e.getButton() == 1)
			{
				if(dashboard.getState() == GameConfiguration.INGAME) {
					//System.out.println("released");
					if(selectionRectangle.isActive() == true)
					{
						if(selectionRectangle.getW() == 0 && selectionRectangle.getH() == 0)
						{
							checkWhatIsSelected(e.getX(), e.getY(), e.getClickCount());
						}
						else
						{
							checkWhatIsSelected(selectionRectangle.getX(), selectionRectangle.getY(), selectionRectangle.getW(), selectionRectangle.getH());
						}
						selectionRectangle.setActive(false);
					}
				}
			}
			else if(e.getButton() == 3)
			{
				
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) 
		{
			
		}

		@Override
		public void mouseExited(MouseEvent e) 
		{
			
		}
	}
	
	private class MouseMotion implements MouseMotionListener
	{
		@Override
		public void mouseDragged(MouseEvent e) {
			if(dashboard.getState() == GameConfiguration.INGAME)
			{
				int x = e.getX();
				int y = e.getY();
				
				selectionRectangle.moveSelectionRect(x, y);
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(dashboard.getState() == GameConfiguration.INGAME)
			{
				int x = e.getX();
				int y = e.getY();
				
				if(x < camera.getRectX() || x > camera.getRectX() + camera.getRectW() || y < camera.getRectY() || y > camera.getRectY() + camera.getRectH())
				{
					double angle = Math.atan2(y - GameConfiguration.WINDOW_HEIGHT / 2, x - GameConfiguration.WINDOW_WIDTH / 2);
					camera.move((int)(20 * Math.cos(angle)), (int)(20 * Math.sin(angle)));
				}
				else
				{
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

	public EntitiesManager getManager() 
	{
		return manager;
	}

	public void setManager(EntitiesManager manager) 
	{
		this.manager = manager;
	}

	public AudioManager getAudioManager() {
		return audioManager;
	}

	public void setAudioManager(AudioManager audioManager) {
		this.audioManager = audioManager;
	}
}
