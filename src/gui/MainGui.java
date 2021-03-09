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
import engine.Mouse;
import engine.Position;
import engine.Ressource;
import engine.entity.building.AttackBuilding;
import engine.entity.building.ProductionBuilding;
import engine.entity.building.StorageBuilding;
import engine.entity.unit.Unit;
import engine.manager.AudioManager;
import engine.manager.EntitiesManager;
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
		
		selectionRectangle = new SelectionRect();
		audioManager = new AudioManager();
		
		mouse = new Mouse();
		manager = new EntitiesManager();
		camera = new Camera(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
		dashboard = new GameDisplay(camera, manager, mouse, selectionRectangle, audioManager);
		
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
	
	private class MouseControls implements MouseListener 
	{
		
		public void checkWhatIsSelected(int mouseX, int mouseY)
		{
			manager.clearSelectedBuildings();
			manager.clearSelectedUnits();
			dashboard.setDescriptionPanelStandard();

			int x = mouseX + camera.getX();
			int y = mouseY + camera.getY();
			
			boolean noUnitSelected = true;
			List<Unit> listUnits = manager.getUnits();
			for(Unit unit : listUnits)
			{
				Position unitPosition = new Position(unit.getPosition().getX(),  unit.getPosition().getY());
				
				if(x > unitPosition.getX() && x < unitPosition.getX() + GameConfiguration.TILE_SIZE && y > unitPosition.getY() && y < unitPosition.getY() + GameConfiguration.TILE_SIZE)
				{
					if(unit.getFaction() == EntityConfiguration.PLAYER_FACTION) {
						manager.addSelectedUnit(unit);
						dashboard.setDescriptionPanelForUnit(unit);
						noUnitSelected = false;
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
						manager.setSelectedProdBuilding(building);
						dashboard.setDescriptionPanelForBuilding(building);
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
							break;
						}
					}
				}
			}
		}
		
		public void checkWhatIsSelected(int x, int y, int w, int h)
		{
			manager.clearSelectedBuildings();
			manager.clearSelectedUnits();
			dashboard.setDescriptionPanelStandard();
			
			SelectionRect rect = new SelectionRect(x, y, w, h);
			
			boolean noUnitSelected = true;
			List<Unit> listUnits = manager.getUnits();
			for(Unit unit : listUnits)
			{
				if(Collision.collide(unit.getPosition(), rect, camera) == true)
				{
					if(unit.getFaction() == EntityConfiguration.PLAYER_FACTION) {
						manager.addSelectedUnit(unit);
						dashboard.setDescriptionPanelForUnit(unit);
						noUnitSelected = false;
					}
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
						manager.setSelectedProdBuilding(building);
						dashboard.setDescriptionPanelForBuilding(building);
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
			if(dashboard.getState() == 1)
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
						System.out.println("on a presser");
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
					List<Unit> listSelectedUnit = manager.getSelectedUnits();
					
					if(!listSelectedUnit.isEmpty()) {
						for(Unit unit : listSelectedUnit){
							unit.calculateSpeed(new Position(mouseX, mouseY));
						}
					}
					else {
						ProductionBuilding building = manager.getSelectedProdBuilding();
						if(building != null) {
							building.setDestination(new Position(mouseX, mouseY));
							System.out.println(building.getDestination().getX());
							System.out.println(building.getDestination().getY());
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
				System.out.println("released");
				if(selectionRectangle.isActive() == true)
				{
					if(selectionRectangle.getW() == 0 && selectionRectangle.getH() == 0)
					{
						checkWhatIsSelected(e.getX(), e.getY());
					}
					else
					{
						checkWhatIsSelected(selectionRectangle.getX(), selectionRectangle.getY(), selectionRectangle.getW(), selectionRectangle.getH());
					}
					selectionRectangle.setActive(false);
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
			if(dashboard.getState() == 1)
			{
				int x = e.getX();
				int y = e.getY();
				
				selectionRectangle.moveSelectionRect(x, y);
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(dashboard.getState() == 1)
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
