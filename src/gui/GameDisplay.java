package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.AbstractMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BoundedRangeModel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import engine.Camera;
import engine.Entity;
import engine.Faction;
import engine.entity.building.AttackBuilding;
import engine.entity.building.ProductionBuilding;
import engine.entity.building.StorageBuilding;
import engine.entity.unit.Unit;

import engine.manager.AudioManager;

import engine.entity.unit.Worker;

import engine.manager.EntitiesManager;
import engine.map.Map;
import engine.map.Tile;
import engine.math.SelectionRect;
import factionConfiguration.ForUpgrade;
import engine.Mouse;
import engine.Position;
import engine.Ressource;

public class GameDisplay extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	private PaintStrategy paintStrategy = new PaintStrategy(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
	
	private Map map;
	
	private Camera camera;
	
	private Mouse mouse;
	
	private SelectionRect selectionRectangle;

	
	//use to give the current selected faction when you launch game
	private EntitiesManager manager;
	
	//state of the game
	private int state;
	private int oldState;
	
	private AudioManager audioManager;
	
	//state constant
	private final int MAINMENU = 0;
	private final int INGAME = 1;
	private final int OPTION = 2;
	private final int PAUSE = 3;
	
	//for Main menuGui
	private JComboBox<String> boxPlayer1;
	private JComboBox<String> boxPlayer2;
	private String[] races = {"Le Royaume", "Les Barbares", "L'Empire"};
	
	
	//for select map
	private JRadioButton radioButton1;
	private JRadioButton radioButton2;
	private JRadioButton radioButton3;
	private ButtonGroup groupButton;
	
	//print the selected map
	private JLabel labelMap;
	private ImageIcon map1;
	private ImageIcon map2;
	private ImageIcon map3;
	
	//for option panel
	private JSlider scrollingSlider = new JSlider(new ScrollingModel());
	private JSlider sonSlider = new JSlider(new SonModel());
	
	/**
	 * 
	 * Constant for the management of Layout of optionPanel.
	 * 
	 */
	private final int OPTIONSMENUMAXINDEX = 11;
	private final int SCROLLINGLABELINDEX = 5;
	private final int SCROLLINGINDEX = 6;
	private final int SONLABELINDEX = 2;
	private final int SONINDEX = 3;
	private final int BACKINDEX = 9;
	
	//for pauseMenuPanel
	/**
	 * 
	 * constant for the management of Layout of pauseMenuPanel.
	 */
	private final int PAUSEMENUMAXINDEX = 11;
	private final int BACKTOGAMEINDEX = 3;
	private final int OPTIONSINDEX = 6;
	private final int LEAVEINDEX = 9;
	
	//button
	private JButton constructionButton = new JButton(new PrintConstruction("construction"));

	//panel of game
	private JPanel mainMenuPanel;
	private JPanel gamePanel;
	private JPanel optionPanel;
	private JPanel pauseMenuPanel;
	
	private JPanel descriptionPanel;
	private JLabel populationLabel;
	private JLabel moneyLabel;
	private JLabel ageLabel;
	private JLabel timeLabel;
	
	private float time;

	public GameDisplay(Camera camera, EntitiesManager manager, Mouse mouse, SelectionRect selectionRectangle, AudioManager audioManager)
	{
		this.camera = camera;
		this.manager = manager;
		this.mouse = mouse;
		this.selectionRectangle = selectionRectangle;
		this.state = MAINMENU;
		this.oldState = this.state;
		this.setLayout(new GridLayout(1,1));
		
		audioManager.setState(state);
		this.audioManager = audioManager;
		
		/*gamePanel = createGamePanel();
		gamePanel.setVisible(false);*/
		
		optionPanel = createOptionPanel();
		optionPanel.setVisible(false);
		
		pauseMenuPanel = createPauseMenuPanel();
		pauseMenuPanel.setVisible(false);
		
		mainMenuPanel = createMainMenuPanel();
		mainMenuPanel.setVisible(true);
		this.add(mainMenuPanel);
	}
	
	public JPanel getMainPanel()
	{
		return this;
	}
	
	private JPanel createMainMenuPanel()
	{
		JPanel panel = new JPanel(new GridLayout(0,3));
		
		panel.add(createMainMenuLeftPanel());
		panel.add(createMainMenuCenterPanel());
		panel.add(createMainMenuRightPanel());
		
		return panel;
	}
	
	private JPanel createMainMenuLeftPanel()
	{
		GridLayout gridLayout = new GridLayout(10,1);
		JPanel panel = new JPanel(gridLayout);
		int gridPlacement = gridLayout.getColumns() * gridLayout.getRows();
		
		for(int i = 0; i < gridPlacement; i++)
        {
        	if(i == 0)
        	{
        		panel.add(new JButton(new GoToOptionFromMainMenuButton("OPTION")));
        	}
        	else
        	{
        		panel.add(new JLabel());
        	}
        }
    
		return panel;
	}
	
	private JPanel createMainMenuCenterPanel()
	{
		GridLayout gridLayout = new GridLayout(10,1);
        JPanel panel = new JPanel(gridLayout);
        
        int gridPlacement = gridLayout.getColumns() * gridLayout.getRows();
        for(int i = 0; i < gridPlacement; i++)
        {
        	if(i == 3)
        	{
        		JPanel panel2 = new JPanel(new GridLayout(2,0));
        		panel2.add(new JLabel("Player 1"));
        		boxPlayer1 = new JComboBox<String>(races);
        		panel2.add(boxPlayer1);
                panel.add(panel2);
        	}
        	else if(i == 5)
        	{
        		JPanel panel2 = new JPanel(new GridLayout(2,0));
        		panel2.add(new JLabel("Player 2"));
        		boxPlayer2 = new JComboBox<String>(races);
        		boxPlayer2.setSelectedIndex(1);
        		panel2.add(boxPlayer2);
                panel.add(panel2);
        	}
        	else if(i == 9)
        	{
        		panel.add(new JButton(new ExitGameButton("QUITTER")));
        	}
        	else
        	{
        		JLabel label = new JLabel();
        		label.setVisible(false);
        		panel.add(label);
        	}
        }
        
        return panel;
	}
	
	private JPanel createMainMenuRightPanel()
	{
		map1 = new ImageIcon("src/graphics/map1.png");
	    map2 = new ImageIcon("src/graphics/map2.png");
	    map3 = new ImageIcon("src/graphics/map3.png");
	    groupButton = new ButtonGroup();
	        
	    GridLayout gridLayout = new GridLayout(4,1);
	    JPanel panel = new JPanel(gridLayout);
	    int gridPlacement = gridLayout.getColumns() * gridLayout.getRows();
	    for(int i = 0; i < gridPlacement; i++)
	    {
	    	if(i == 0)
	        {
	    		labelMap = new JLabel(map1);
	            panel.add(labelMap);
	        }
	    	else if(i == 1)
	        {
	    		GridLayout gridLayout2 = new GridLayout(4,2);
	        	JPanel panel2 = new JPanel(gridLayout2);
	        	int gridPlacement2 = gridLayout2.getColumns() * gridLayout2.getRows();
	        	for(int j = 0; j < gridPlacement2; j++)
	        	{
	        		if(j == 1)
	        		{
	        	    		 radioButton1 = new JRadioButton(new RadioButton1("map1"));
	        	    	     radioButton1.setSelected(true);
	        	    	     groupButton.add(radioButton1);
	        	    	     panel2.add(radioButton1);
	        	    }
	        		else if(j == 3)
	        		{
	        	    	radioButton2 = new JRadioButton(new RadioButton2("map2"));
	        	    	groupButton.add(radioButton2);
	        	    	panel2.add(radioButton2);
	        		}
	        	    else if(j == 5)
	        	    {
	        	    	radioButton3 = new JRadioButton(new RadioButton3("map3"));
	        	    	groupButton.add(radioButton3);
	        	    	panel2.add(radioButton3); 
	        	    }
	        	    else if(j == 7)
	        	    {
	        	    	panel2.add(new JButton(new LaunchGame("LANCER PARTIE")));
	        	    }
	        	    else
	        	    {
	        	    	JLabel label = new JLabel();
	        	    	label.setVisible(false);
	        	    	panel2.add(label);
	        	    }
	        	}
	        	     panel.add(panel2);
	        }
	        else
	        {
	        	JLabel label = new JLabel();
	        	label.setVisible(false);
	        	panel.add(label);
	        }
	    }
	    
	    return panel;
	}
	
	private JPanel createGamePanel()
	{
		GridLayout gridLayout = new GridLayout(4,3);
		JPanel panel = new JPanel(gridLayout);
		panel.setOpaque(false);
		
		int gridPlacement = gridLayout.getColumns() * gridLayout.getRows();
		for(int i = 0; i < gridPlacement; i++)
		{
			if(i == 0)
			{
				panel.add(createRessourceInfo());
			}
			else if(i == 2)
			{
				panel.add(createGameMenuPanel());
			}
			else if(i == 9)
			{
				panel.add(createDescriptionPanel());
			}
			else
			{
				JLabel label = new JLabel();
				label.setVisible(false);
				panel.add(label);
			}
		}
		
		return panel;
	}

	private JPanel createGameMenuPanel()
	{
		GridLayout gridLayout = new GridLayout(6,5);
		JPanel panel = new JPanel(gridLayout);
		panel.setOpaque(false);
		
		int gridPlacement = gridLayout.getColumns() * gridLayout.getRows();
		
		for(int i = 0; i < gridPlacement; i++) {
			if(i == 4) {
				panel.add(new JButton(new PauseGameMenu("MENU")));
			}
			else {
				panel.add(new JLabel());
			}
		}

		return panel;
	}
	
	private JPanel createDescriptionPanel()
	{
		descriptionPanel = new JPanel(new GridLayout(2, 2));
		
		setDescriptionPanelStandard();
		
		return descriptionPanel;
	}
	
	public void setDescriptionPanelForWorker(Worker worker)
	{
		descriptionPanel.removeAll();
		
		descriptionPanel.setLayout(new GridLayout(2, 2));
		
		descriptionPanel.add(constructionButton);
		descriptionPanel.add(new JLabel("" + worker.getDescription()));
		
		descriptionPanel.validate();
	}
	
	private void setDescriptionPanelForConstruction()
	{		
		descriptionPanel.removeAll();
		descriptionPanel.setLayout(new GridLayout(5, 2));
		
		JButton buttonForge = new JButton(new ConstructBuilding("Forge", EntityConfiguration.FORGE));
		buttonForge.setFocusable(false);
		
		JButton buttonBarrack = new JButton(new ConstructBuilding("Caserne", EntityConfiguration.BARRACK));
		buttonBarrack.setFocusable(false);
		
		JButton buttonStable = new JButton(new ConstructBuilding("Ecurie", EntityConfiguration.STABLE));
		buttonStable.setFocusable(false);
		
		JButton buttonHq = new JButton(new ConstructBuilding("Qg", EntityConfiguration.HQ));
		buttonHq.setFocusable(false);
		
		JButton buttonStockage = new JButton(new ConstructBuilding("Stockage", EntityConfiguration.STORAGE));
		buttonStockage.setFocusable(false);
		
		JButton buttonCastle = new JButton(new ConstructBuilding("Chateau", EntityConfiguration.CASTLE));
		buttonCastle.setFocusable(false);
		
		JButton buttonArchery = new JButton(new ConstructBuilding("Acherie", EntityConfiguration.ARCHERY));
		buttonArchery.setFocusable(false);
		
		JButton buttonTower = new JButton(new ConstructBuilding("Tour", EntityConfiguration.TOWER));
		buttonTower.setFocusable(false);
		
		descriptionPanel.add(new JLabel("Liste des constructions"));
		descriptionPanel.add(new JLabel());
		descriptionPanel.add(buttonHq);
		descriptionPanel.add(buttonStockage);
		descriptionPanel.add(buttonBarrack);
		descriptionPanel.add(buttonArchery);
		descriptionPanel.add(buttonStable);
		descriptionPanel.add(buttonCastle);
		descriptionPanel.add(buttonForge);
		descriptionPanel.add(buttonTower);
		
		descriptionPanel.validate();
	}
	
	public void setDescriptionPanelForUnit(Unit unit)
	{
		descriptionPanel.removeAll();
		descriptionPanel.setLayout(new GridLayout(1, 3));
		
		descriptionPanel.add(new JLabel("etat"));
		descriptionPanel.add(new JLabel(unit.getDescription()));
		descriptionPanel.add(new JLabel("LES STATS"));
		
		descriptionPanel.validate();
	}
	
	public void setDescriptionPanelForBuilding(AttackBuilding building)
	{
		descriptionPanel.removeAll();
		
		descriptionPanel.setLayout(new GridLayout(2, 1));
	
		descriptionPanel.add(new JLabel(building.getDescription()));
		
		descriptionPanel.validate();
	}
	
	public void setDescriptionPanelForBuilding(StorageBuilding building)
	{
		descriptionPanel.removeAll();
		
		descriptionPanel.setLayout(new GridLayout(2, 1));
	
		descriptionPanel.add(new JLabel(building.getDescription()));
		
		descriptionPanel.validate();
	}
	
	public void setDescriptionPanelForBuilding(ProductionBuilding building)
	{
		descriptionPanel.removeAll();
		
		descriptionPanel.setLayout(new GridLayout(2, 1));
		
		if(building.getProductionId() > -1)
		{
			if(building.getId() == EntityConfiguration.FORGE) {
				AbstractMap<Integer, ForUpgrade> upgrades = building.getUpgrades();
				descriptionPanel.setLayout(new GridLayout(upgrades.size(), 1));
				for(ForUpgrade upgrade : upgrades.values()) {
					JButton button = new JButton(new CreateUnit("" + upgrade.getDescription(), upgrade.getId(), building ));
					button.setFocusable(false);
					descriptionPanel.add(button);
				}
				descriptionPanel.add(new JLabel(building.getDescription()));
			}
			else {
				JButton button = new JButton(new CreateUnit("" + building.getProductionId(), building.getProductionId(), building ));
				button.setFocusable(false);
				descriptionPanel.add(button);
				descriptionPanel.add(new JLabel(building.getDescription()));
			}
		}
		else
		{
			descriptionPanel.add(new JLabel(building.getDescription()));
		}
		
		descriptionPanel.validate();
	}
	
	public void setDescriptionPanelForRessource(Ressource ressource)
	{
		descriptionPanel.removeAll();
		
		descriptionPanel.setLayout(new FlowLayout());
		
		descriptionPanel.add(new JLabel("Ressource restante : " + ressource.getHp()));
		
		descriptionPanel.validate();
	}
	
	public void setDescriptionPanelStandard()
	{
		descriptionPanel.removeAll();
		
		descriptionPanel.setLayout(new GridLayout(2, 2));
		
		descriptionPanel.add(constructionButton);
		
		descriptionPanel.validate();
	}
	
	private JPanel createRessourceInfo()
	{	
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 0));

		this.moneyLabel =  new JLabel("ARGENT:" + manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getMoneyCount());
		this.moneyLabel.setForeground(Color.WHITE);
		this.timeLabel =  new JLabel("TEMPS:");
		this.timeLabel.setForeground(Color.WHITE);
		this.populationLabel =  new JLabel("POPULATION:" + manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getPopulationCount());
		this.populationLabel.setForeground(Color.WHITE);
		this.ageLabel =  new JLabel("AGE:" + manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getAge());
		this.ageLabel.setForeground(Color.WHITE);
		JLabel race = new JLabel("FACTION :  " + manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getRace().getName());
		race.setForeground(Color.WHITE);
		
		panel.add(moneyLabel);
		panel.add(timeLabel);
		panel.add(populationLabel);
		panel.add(ageLabel);
		panel.add(race);
		
		panel.setOpaque(false);
		return panel;
	}
	
	private JPanel createOptionPanel()
	{
		JPanel panel = new JPanel();
		JPanel panelBis = new JPanel();
		
		sonSlider.setMinimum(0);
		sonSlider.setPaintTicks(true);
		sonSlider.setPaintTrack(true);
		sonSlider.setPaintLabels(true);
		sonSlider.setMaximum(100);
		sonSlider.setMajorTickSpacing(25);
		
		scrollingSlider.setMinimum(0);
		scrollingSlider.setMaximum(100);

		GridLayout gridLayout = new GridLayout(1,3);
		panelBis.setLayout(new GridLayout(OPTIONSMENUMAXINDEX,1));
		
		for(int i = 1; i <= OPTIONSMENUMAXINDEX; i++)
		{
			switch (i)
			{
				case SONLABELINDEX:
					panelBis.add(new JLabel("Son:"));
					break;
				case SONINDEX:
					panelBis.add(sonSlider);
					break;
				case SCROLLINGLABELINDEX:
					panelBis.add(new JLabel("Scrolling:"));
					break;
				case SCROLLINGINDEX:
					panelBis.add(scrollingSlider);
					break;
				case BACKINDEX:
					panelBis.add(new JButton(new BackToOldStateFromOptionButton("RETOUR")));
					break;
				default:
						JLabel label = new JLabel();
						label.setVisible(false);
						panelBis.add(label);
					break;
			}
		}
		
		panel.setLayout(gridLayout);
		panel.add(new JLabel());
		panel.add(panelBis);
		panel.add(new JLabel());
		
		return panel;
	}
	
	private JPanel createPauseMenuPanel()
	{
		JPanel panel = new JPanel();
		JPanel panelBis = new JPanel();
		
		GridLayout gridLayaout = new GridLayout(1,3);
		panelBis.setLayout(new GridLayout(PAUSEMENUMAXINDEX,1));
		
		for(int i = 1; i <= PAUSEMENUMAXINDEX; i++)
		{
			switch(i)
			{
				case BACKTOGAMEINDEX:
					panelBis.add(new JButton(new UnpauseGameButton("RETOUR AU JEU")));
					break;
				case OPTIONSINDEX:
					panelBis.add(new JButton(new GoToOptionFromPauseMenuButton("OPTION")));
					break;
				case LEAVEINDEX:
					panelBis.add(new JButton(new GoToMainMenuFromPauseMenu("RETOUR AU MENU PRINCIPAL")));
					break;
				default:
					panelBis.add(new JLabel());
					break;
				
			}
		}
		
		panel.setLayout(gridLayaout);
		panel.add(new JLabel());
		panel.add(panelBis);
		panel.add(new JLabel());
		panel.setOpaque(false);
		
		return panel;
	}
	
	public void update() {
		if(state == INGAME){
			int populationCount = manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getPopulationCount();
			int maxPopulation = manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getMaxPopulation();
			time += 1.0 / GameConfiguration.GAME_SPEED;
			this.moneyLabel.setText("ARGENT:" + manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getMoneyCount());
			this.timeLabel.setText("TEMPS:" + (int)time);
			this.populationLabel.setText("POPULATION:" + populationCount + " / " + maxPopulation);
			this.ageLabel.setText("AGE:" + manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getAge());
		}
		if(audioManager.getSliderVolume() != this.sonSlider.getValue()) {
			audioManager.manageVolume(this.sonSlider.getValue());
		}
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		if(state == INGAME)
		{
			this.paintStrategy.paint(this.map, g, this.camera);
			List<Entity> entities = manager.getDrawingList();
			List<Unit> units = manager.getSelectedUnits();
			
			Entity building = null;
			if(manager.getSelectedAttackBuilding() != null) {
				building = manager.getSelectedAttackBuilding();
			}
			else if(manager.getSelectedProdBuilding() != null) {
				building = manager.getSelectedProdBuilding();
			}
			else if(manager.getSelectedStorageBuilding() != null) {
				building = manager.getSelectedStorageBuilding();
			}
			
			for(Entity entity : entities)
			{
				this.paintStrategy.paint(entity, g, camera);
			}
			
			for(Unit unit : units) {
				this.paintStrategy.paintRectSelectionUnit(unit, g, camera);
			}
			
			if(building != null) {
				this.paintStrategy.paintSelectionRectBuilding(building, g, camera);
			}
			
			if(selectionRectangle.isActive())
			{
				this.paintStrategy.paint(selectionRectangle, g, camera);
			}
			
			this.paintStrategy.paintGui(map, entities, g, camera);
		}
		else if(state == MAINMENU)
		{
			//this.paintStrategy.paint(g);
		}
		else if(state == OPTION)
		{
			
		}
		else if(state == PAUSE)
		{
			
		}
	}
	
	private class CreateUnit extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		
		private int id;
		private ProductionBuilding prodBuilding;
		
		public CreateUnit(String name, int id, ProductionBuilding building)
		{
			super(name);
			this.id = id;
			this.prodBuilding = building;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			prodBuilding.startProd(id);
		}
	}
	
	private class PauseGameMenu extends AbstractAction
	{
		private static final long serialVersionUID = 1L;

		public PauseGameMenu(String name)
		{
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			oldState = state;
			state = PAUSE;
			manageState();
		}
	}
	
	private class ConstructBuilding extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		
		private int id;
		
		public ConstructBuilding(String name, int id)
		{
			super(name);
			this.id = id;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			mouse.setId(this.getId());
		}
		
		public int getId() {
			return id;
		}
	}
	
	private class PrintConstruction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;

		public PrintConstruction(String name)
		{
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			setDescriptionPanelForConstruction();
		}
	}
	
	//for mainMenuGui
	private class RadioButton1 extends AbstractAction
	{

		private static final long serialVersionUID = 1L;
		
		public RadioButton1(String name) {
			super(name);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			labelMap.setIcon(map1);
		}
		
	}
	
	private class RadioButton2 extends AbstractAction
	{

		private static final long serialVersionUID = 1L;
		
		public RadioButton2(String name) 
		{
			super(name);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			labelMap.setIcon(map2);
		}
		
	}
	
	private class RadioButton3 extends AbstractAction
	{

		private static final long serialVersionUID = 1L;
		
		public RadioButton3(String name) 
		{
			super(name);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			labelMap.setIcon(map3);
		}
		
	}
	
	private class ExitGameButton extends AbstractAction
	{

		private static final long serialVersionUID = 1L;
		
		public ExitGameButton(String name) 
		{
			super(name);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			System.exit(0);
		}
		
	}
	
	private class GoToOptionFromMainMenuButton extends AbstractAction
	{

		private static final long serialVersionUID = 1L;
		
		public GoToOptionFromMainMenuButton(String name) 
		{
			super(name);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			oldState = state;
			state = OPTION;
			manageState();
		}
		
	}
	
	private class LaunchGame extends AbstractAction
	{

		private static final long serialVersionUID = 1L;
		
		public LaunchGame(String name) 
		{
			super(name);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{	
			time = 0;
			Faction faction1 = new Faction(boxPlayer1.getSelectedIndex() + 1);
			Faction faction2 = new Faction(boxPlayer2.getSelectedIndex() + 1);
			Faction gaia = new Faction(4);
			
			manager.getFactionManager().addFaction(EntityConfiguration.PLAYER_FACTION, faction1);
			manager.getFactionManager().addFaction(EntityConfiguration.BOT_FACTION, faction2);
			manager.getFactionManager().addFaction(EntityConfiguration.GAIA_FACTION, gaia);
			
			if(radioButton1.isSelected())
			{
				map = new Map(GameConfiguration.LINE_COUNT, GameConfiguration.COLUMN_COUNT, 1, "src/file/map1.txt");
			}
			else if(radioButton2.isSelected())
			{
				map = new Map(GameConfiguration.LINE_COUNT, GameConfiguration.COLUMN_COUNT, 2, "src/file/map2.txt");
			}
			else
			{
				map = new Map(GameConfiguration.LINE_COUNT, GameConfiguration.COLUMN_COUNT, 3, "src/file/map3.txt");
			}
			manager.addRessource(map.getGoldTiles());
			
			gamePanel = createGamePanel();
			gamePanel.setVisible(false);
			
			oldState = state;
			state = INGAME;
			
			//création d'un ennemie pour test
			Tile tile = map.getTile(15, 15);
			Tile tile2 = map.getTile(20, 15);
			manager.createBuilding(EntityConfiguration.ARCHERY, EntityConfiguration.BOT_FACTION, new Position(tile.getColumn() * 32, tile.getLine() * 32), tile);
			manager.createBuilding(EntityConfiguration.TOWER, EntityConfiguration.BOT_FACTION, new Position(tile2.getColumn() * 32, tile2.getLine() * 32), tile2);
			
			manageState();
		}
	}
	/**
	 * Allows you to want the button Back and had to assign it an action.
	 * @author girard
	 *
	 */
	private class BackToOldStateFromOptionButton extends AbstractAction
	{
		private static final long serialVersionUID = 1L;


		public BackToOldStateFromOptionButton(String name)
		{
			super(name);
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			int stateBis = state;
			state = oldState;
			oldState = stateBis;
			
			manageState();
		}
	}
	
	/**
	 * 
	 * @author girard
	 * Internal class allowing the management of the slider of the son.
	 *
	 */
	private class ScrollingModel implements BoundedRangeModel
	{
		public ScrollingModel()
		{
			super();
		}

		@Override
		public void addChangeListener(ChangeListener arg0) 
		{
			
		}

		@Override
		public int getExtent() 
		{
			return 0;
		}

		@Override
		public int getMaximum() 
		{
			return 0;
		}

		@Override
		public int getMinimum() 
		{
			return 0;
		}

		@Override
		public int getValue() 
		{
			return 0;
		}

		@Override
		public boolean getValueIsAdjusting() 
		{
			return false;
		}

		@Override
		public void removeChangeListener(ChangeListener arg0) 
		{
			
		}

		@Override
		public void setExtent(int arg0) 
		{
			
		}

		@Override
		public void setMaximum(int arg0) 
		{
			
		}

		@Override
		public void setMinimum(int arg0) 
		{
			
		}

		@Override
		public void setRangeProperties(int arg0, int arg1, int arg2, int arg3, boolean arg4) 
		{
			
		}

		@Override
		public void setValue(int arg0) 
		{
			
		}

		@Override
		public void setValueIsAdjusting(boolean arg0) 
		{
			
		}
		
	}
	
	/**
	 * 
	 * @author girard
	 * Internal class allowing the management of the slider of the son.
	 * 
	 */
	private class SonModel implements BoundedRangeModel
	{
		private int value;
		
		public SonModel()
		{
			super();
			this.value = 25;
			this.setValue(value);
		}

		@Override
		public void addChangeListener(ChangeListener arg0) 
		{
			
		}

		@Override
		public int getExtent() 
		{
			return 0;
		}

		@Override
		public int getMaximum() 
		{
			return 100;
		}

		@Override
		public int getMinimum() 
		{
			return 0;
		}

		@Override
		public int getValue() 
		{
			return value;
		}

		@Override
		public boolean getValueIsAdjusting() 
		{
			return false;
		}

		@Override
		public void removeChangeListener(ChangeListener arg0) 
		{
			
		}

		@Override
		public void setExtent(int arg0) 
		{
			
		}

		@Override
		public void setMaximum(int arg0) 
		{
			
		}

		@Override
		public void setMinimum(int arg0) 
		{
			
		}

		@Override
		public void setRangeProperties(int arg0, int arg1, int arg2, int arg3, boolean arg4) 
		{
			
		}

		@Override
		public void setValue(int arg0) 
		{
			value = arg0;
		}

		@Override
		public void setValueIsAdjusting(boolean arg0) 
		{
			
		}
		
	}
	
	/**
	 * 
	 * Allows you to want the button BackToGame and had to assign it an action.
	 * @author girard
	 * 
	 */
	private class UnpauseGameButton extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		
		public UnpauseGameButton(String name)
		{
			super(name);
		}
		public void actionPerformed(ActionEvent e)
		{
			oldState = state;
			state = INGAME;
			manageState();
		}
	}
	
	/**
	 * 
	 * Allows you to want the button Options and had to assign it an action.
	 * @author girard
	 *
	 */
	
	private class GoToOptionFromPauseMenuButton extends AbstractAction
	{
		private static final long serialVersionUID = 1L;

		public GoToOptionFromPauseMenuButton(String name)
		{
			super(name);
		}
		
		public void actionPerformed(ActionEvent e)
		{
			oldState = state;
			state = OPTION;
			manageState();
		}
	}
	
	/**
	 * Allows you to want the button Leave and had to assign it an action.
	 * @author girard
	 *
	 */
	
	private class GoToMainMenuFromPauseMenu extends AbstractAction
	{
		private static final long serialVersionUID = 1L;

		public GoToMainMenuFromPauseMenu(String name)
		{
			super(name);
		}
		
		public void actionPerformed(ActionEvent e)
		{
			oldState = state;
			state = MAINMENU;
			manageState();
			mouse.reset();
		}
	}
	
	private void manageState()
	{
		switch(state)
		{
			case MAINMENU:
				if(oldState == OPTION)
				{
					optionPanel.setVisible(false);
					getMainPanel().remove(optionPanel);
				}
				else if(oldState == PAUSE)
				{
					pauseMenuPanel.setVisible(false);
					getMainPanel().remove(pauseMenuPanel);
					manager.clean();
					camera.reset();
				}
				mainMenuPanel.setVisible(true);
				getMainPanel().add(mainMenuPanel);
				break;
				
			case INGAME:
				if(oldState == PAUSE)
				{
					pauseMenuPanel.setVisible(false);
					getMainPanel().remove(pauseMenuPanel);
				}
				else if(oldState == MAINMENU)
				{
					mainMenuPanel.setVisible(false);
					getMainPanel().remove(mainMenuPanel);
				}
				gamePanel.setVisible(true);
				getMainPanel().add(gamePanel);
				break;
				
			case OPTION:
				if(oldState == PAUSE)
				{
					gamePanel.setVisible(false);
					pauseMenuPanel.setVisible(false);
					getMainPanel().remove(gamePanel);
					getMainPanel().remove(pauseMenuPanel);
				}
				else if(oldState == MAINMENU)
				{
					mainMenuPanel.setVisible(false);
					getMainPanel().remove(mainMenuPanel);
				}
				optionPanel.setVisible(true);
				getMainPanel().add(optionPanel);
				break;
				
			case PAUSE:
				if(oldState == OPTION)
				{
					optionPanel.setVisible(false);
					getMainPanel().remove(optionPanel);
				}
				else if(oldState == INGAME)
				{
					gamePanel.setVisible(false);
					getMainPanel().remove(gamePanel);
				}
				
				pauseMenuPanel.setVisible(true);
				getMainPanel().add(pauseMenuPanel);
				break;
			
			default:
				break;
		}
		audioManager.setState(state);
	}
	
	public Map getMap()
	{
		return this.map;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public JLabel getPopulationLabel() {
		return populationLabel;
	}

	public void setPopulationLabel(JLabel populationLabel) {
		this.populationLabel = populationLabel;
	}

	public JLabel getMoneyLabel() {
		return moneyLabel;
	}

	public void setMoneyLabel(JLabel moneyLabel) {
		this.moneyLabel = moneyLabel;
	}

	public JLabel getTimeLabel() {
		return timeLabel;
	}

	public JLabel getAgeLabel() {
		return ageLabel;
	}

	public void setAgeLabel(JLabel ageLabel) {
		this.ageLabel = ageLabel;
	}

	public void setTimeLabel(JLabel timeLabel) {
		this.timeLabel = timeLabel;
	}
}
