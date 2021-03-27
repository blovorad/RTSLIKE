package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.AbstractMap;

import java.util.HashMap;
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
import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;

import configuration.EntityConfiguration;
import configuration.GameConfiguration;
import configuration.PositionConfiguration;
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
import engine.manager.GameBuilder;
import engine.manager.GraphicsManager;
import engine.map.Fog;
import engine.map.Map;
import engine.map.Minimap;
import engine.math.SelectionRect;
import factionConfiguration.ForFighter;
import factionConfiguration.ForUpgrade;
import factionConfiguration.ForWorker;
import factionConfiguration.Race;
import engine.Mouse;
import engine.Position;
import engine.Ressource;

/**
 * 
 * @author gautier
 *
 */

public class GameDisplay extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	private PaintStrategyGame paintStrategyGame = null;
	private PaintStrategyMainMenu paintStrategyMainMenu = null;
	private PaintStrategyOption paintStrategyOption = null;
	private PaintStrategyPauseMenu paintStrategyPauseMenu = null;
	
	private Minimap minimap;
	
	private Map map;
	private Fog fog;
	
	private Camera camera;
	
	private Mouse mouse;
	
	private SelectionRect selectionRectangle;

	
	//use to give the current selected faction when you launch game
	private EntitiesManager manager;
	
	//state of the game
	private int state;
	private int oldState;
	
	private AudioManager audioManager;
	
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
	
	//button
	private JButton constructionButton = new JButton(new PrintConstruction("construction"));

	//panel of game
	private JPanel mainMenuPanel;
	private JPanel gamePanel;
	private JPanel optionPanel;
	private JPanel pauseMenuPanel;
	
	private JPanel descriptionPanel;
	private JPanel ressourceInfoPanel;
	private JPanel minimapPanel;
	private JLabel populationLabel;
	private JLabel moneyLabel;
	private JLabel ageLabel;
	private JTextArea currentProductionLabel = new JTextArea();
	private JLabel timeLabel;

	private JTextArea unitStatistiquesLabel = new JTextArea();
	private JTextArea buildingStatistiquesLabel = new JTextArea();
	private JTextArea ressourceStatistiquesLabel = new JTextArea();
	private GraphicsManager graphicsManager;
	
	private int selectedMap = 1;
	
	private float time;

	public GameDisplay(Camera camera, EntitiesManager manager, Mouse mouse, SelectionRect selectionRectangle, AudioManager audioManager, GraphicsManager graphicsManager)
	{
		this.graphicsManager = graphicsManager;
		this.camera = camera;
		this.manager = manager;
		this.mouse = mouse;
		this.selectionRectangle = selectionRectangle;
		this.state = GameConfiguration.INMENU;
		this.oldState = this.state;
		this.setLayout(new GridLayout(1,1));
		this.setOpaque(false);
		
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
		
		currentProductionLabel.setEditable(false);
		unitStatistiquesLabel.setEditable(false);
		buildingStatistiquesLabel.setEditable(false);
		ressourceStatistiquesLabel.setEditable(false);
		constructionButton.setOpaque(false);
		currentProductionLabel.setOpaque(false);
		unitStatistiquesLabel.setOpaque(false);
		buildingStatistiquesLabel.setOpaque(false);
		ressourceStatistiquesLabel.setOpaque(false);
		
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
		panel.setOpaque(false);
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
		panel.setOpaque(false);
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
        		JLabel p1 = new JLabel("Joueur 1");
        		p1.setOpaque(false);
        		panel2.add(p1);
        		boxPlayer1 = new JComboBox<String>(races);
        		boxPlayer1.setOpaque(false);
        		panel2.setOpaque(false);
        		panel2.add(boxPlayer1);
                panel.add(panel2);
        	}
        	else if(i == 5)
        	{
        		JPanel panel2 = new JPanel(new GridLayout(2,0));
        		JLabel p2 = new JLabel("Joueur 2");
        		p2.setOpaque(false);
        		panel2.add(p2);
        		boxPlayer2 = new JComboBox<String>(races);
        		boxPlayer2.setOpaque(false);
        		boxPlayer2.setSelectedIndex(1);
        		panel2.setOpaque(false);
        		panel2.add(boxPlayer2);
                panel.add(panel2);
        	}
        	else if(i == 9)
        	{
        		JButton button = new JButton(new ExitGameButton("QUITTER"));
        		panel.add(button);
        	}
        	else
        	{
        		JLabel label = new JLabel();
        		label.setVisible(false);
        		panel.add(label);
        	}
        }
        panel.setOpaque(false);
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
	        	    		 radioButton1.setOpaque(false);
	        	    	     radioButton1.setSelected(true);
	        	    	     groupButton.add(radioButton1);
	        	    	     panel2.add(radioButton1);
	        	    }
	        		else if(j == 3)
	        		{
	        	    	radioButton2 = new JRadioButton(new RadioButton2("map2"));
	        	    	radioButton2.setOpaque(false);
	        	    	groupButton.add(radioButton2);
	        	    	panel2.add(radioButton2);
	        		}
	        	    else if(j == 5)
	        	    {
	        	    	radioButton3 = new JRadioButton(new RadioButton3("map3"));
	        	    	radioButton3.setOpaque(false);
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
	        		panel2.setOpaque(false);
	        	     panel.add(panel2);
	        }
	        else
	        {
	        	JLabel label = new JLabel();
	        	label.setVisible(false);
	        	panel.add(label);
	        }
	    }
	    panel.setOpaque(false);
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
			else if(i == 11) {
				panel.add(createMinimapPanel());
			}
			else{
				JLabel label = new JLabel();
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
		descriptionPanel.setOpaque(false);
		setDescriptionPanelStandard();
		
		return descriptionPanel;
	}
	
	public void setDescriptionPanelForWorker(Worker worker)
	{
		descriptionPanel.removeAll();
		
		descriptionPanel.setLayout(new GridLayout(2, 1));
		
		descriptionPanel.add(constructionButton);
		
		unitStatistiquesLabel.setText("\nPoints de vie : " + worker.getHp() +
				"\nDégâts : " + worker.getDamage() + 
				"\nArmure : " + worker.getArmor());
		JTextArea area = new JTextArea();
		area.setEditable(false);
		area.setOpaque(false);
		area.setText("      " + worker.getDescription());
		
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.setOpaque(false);
		panel.add(area);
		panel.add(unitStatistiquesLabel);
		
		descriptionPanel.add(panel);
		
		descriptionPanel.validate();
	}
	
	private void setDescriptionPanelForConstruction()
	{		
		descriptionPanel.removeAll();
		descriptionPanel.setLayout(new GridLayout(5, 2));
		Faction faction = manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION);
		
		JButton buttonForge = new JButton(new ConstructBuilding("Forge", EntityConfiguration.FORGE));
		buttonForge.setFocusable(false);
		if(faction.getAge() <= 1) {
			buttonForge.setEnabled(false);
		}
		buttonForge.setToolTipText("Coût : " + faction.getRace().getProductionBuildings().get(EntityConfiguration.FORGE).getCost() + ",age requis : " + faction.getRace().getProductionBuildings().get(EntityConfiguration.FORGE).getAge());
		
		JButton buttonBarrack = new JButton(new ConstructBuilding("Caserne", EntityConfiguration.BARRACK));
		buttonBarrack.setFocusable(false);
		buttonBarrack.setToolTipText("Coût : " + faction.getRace().getProductionBuildings().get(EntityConfiguration.BARRACK).getCost());
		
		JButton buttonStable = new JButton(new ConstructBuilding("Ecurie", EntityConfiguration.STABLE));
		buttonStable.setFocusable(false);
		if(faction.getAge() <= 1) {
			buttonStable.setEnabled(false);
		}
		buttonStable.setToolTipText("Coût : " + faction.getRace().getProductionBuildings().get(EntityConfiguration.STABLE).getCost() + ",age requis : " + faction.getRace().getProductionBuildings().get(EntityConfiguration.STABLE).getAge());
		
		JButton buttonHq = new JButton(new ConstructBuilding("Qg", EntityConfiguration.HQ));
		buttonHq.setFocusable(false);
		buttonHq.setToolTipText("Coût : " + faction.getRace().getProductionBuildings().get(EntityConfiguration.HQ).getCost());
		
		JButton buttonStockage = new JButton(new ConstructBuilding("Stockage", EntityConfiguration.STORAGE));
		buttonStockage.setFocusable(false);
		buttonStockage.setToolTipText("Coût : " + faction.getRace().getStorageBuildings().get(EntityConfiguration.STORAGE).getCost());
		
		JButton buttonCastle = new JButton(new ConstructBuilding("Chateau", EntityConfiguration.CASTLE));
		buttonCastle.setFocusable(false);
		if(faction.getAge() <= 2) {
			buttonCastle.setEnabled(false);
		}
		buttonCastle.setToolTipText("Coût : " + faction.getRace().getProductionBuildings().get(EntityConfiguration.CASTLE).getCost() + ",age requis : " + faction.getRace().getProductionBuildings().get(EntityConfiguration.CASTLE).getAge());
		
		JButton buttonArchery = new JButton(new ConstructBuilding("Acherie", EntityConfiguration.ARCHERY));
		buttonArchery.setFocusable(false);
		if(faction.getAge() <= 1) {
			buttonArchery.setEnabled(false);
		}
		buttonArchery.setToolTipText("Coût : " + faction.getRace().getProductionBuildings().get(EntityConfiguration.ARCHERY).getCost() + ",age requis : " + faction.getRace().getProductionBuildings().get(EntityConfiguration.ARCHERY).getAge());
		
		JButton buttonTower = new JButton(new ConstructBuilding("Tour", EntityConfiguration.TOWER));
		buttonTower.setFocusable(false);
		buttonTower.setToolTipText("Coût : " + faction.getRace().getAttackBuildings().get(EntityConfiguration.TOWER).getCost());
		
		JTextArea area = new JTextArea();
		area.setEditable(false);
		area.setOpaque(false);
		area.setText("\n          Liste des constructions");
		
		descriptionPanel.add(area);
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
		
		unitStatistiquesLabel.setText("\nPoints de vie : " + unit.getHp() +
										"\nDégâts : " + unit.getDamage() + 
										"\nArmure : " + unit.getArmor());
		
		descriptionPanel.add(new JLabel("etat"));
		descriptionPanel.add(new JLabel(unit.getDescription()));
		descriptionPanel.add(unitStatistiquesLabel);
		
		descriptionPanel.validate();
	}
	
	public void setDescriptionPanelForBuilding(AttackBuilding building)
	{
		descriptionPanel.removeAll();
		
		descriptionPanel.setLayout(new FlowLayout());

		buildingStatistiquesLabel.setText("\n\n" + building.getDescription() + ", attaque les unités adverse aux alentours\nPoints de vie : " + building.getHp());
		descriptionPanel.add(buildingStatistiquesLabel);
		
		descriptionPanel.validate();
	}
	
	public void setDescriptionPanelForBuilding(StorageBuilding building)
	{
		descriptionPanel.removeAll();
		
		descriptionPanel.setLayout(new FlowLayout());
		buildingStatistiquesLabel.setText("\n\n" + building.getDescription() + ", permet de déposer les ressource\nPoints de vie : " + building.getHp());
		descriptionPanel.add(buildingStatistiquesLabel);
		
		descriptionPanel.validate();
	}
	
	public void setDescriptionPanelForBuilding(ProductionBuilding building, List<Integer> searchingUpgrades)
	{
		descriptionPanel.removeAll();
		
		descriptionPanel.setLayout(new GridLayout(3, 1));
		
		for(int i = 0; i < 2; i++) {
			if(i == 0) {
				buildingStatistiquesLabel.setText("\n      " + building.getDescription() + "\n      Points de vie : " + building.getHp());
				descriptionPanel.add(buildingStatistiquesLabel);
			}
			else if(i == 1) {
				if(building.getIsProducing()) {
					int idProduction = building.getElementCount().get(0);
					
					Race race = manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getRace();
					ForFighter fighter = race.getPatronFighters().get(idProduction);
					ForUpgrade forgeUpgrade = race.getForgeUpgrades().get(idProduction);
					ForUpgrade hqUpgrade = race.getHQUpgrades().get(idProduction);
					ForWorker worker = race.getPatronWorkers().get(idProduction);
					if(fighter != null) {
						currentProductionLabel.setText("\n      Prod: " + fighter.getDescription()+ ", temps restant : " + (int)building.getTimer() + ", file d'attente : " + (building.getElementCount().size()- 1));
					}
					else if(forgeUpgrade != null) {
						currentProductionLabel.setText("\n      Prod: " + forgeUpgrade.getDescription()+ ", temps restant : " + (int)building.getTimer() + ", file d'attente : " + (building.getElementCount().size() - 1));
					}
					else if(hqUpgrade != null){
						currentProductionLabel.setText("\n      Prod: " + hqUpgrade.getDescription()+ ", temps restant : " + (int)building.getTimer() + ", file d'attente : " + (building.getElementCount().size() - 1));
					}
					else if(worker != null) {
						currentProductionLabel.setText("\n      Prod: " + worker.getDescription()+ ", temps restant : " + (int)building.getTimer() + ", file d'attente : " + (building.getElementCount().size() - 1));
					}
				}
				else {
					currentProductionLabel.setText("\n      Rien n'est en production");
				}
				descriptionPanel.add(currentProductionLabel);
			}
		}
		
		GridLayout gridLayout = new GridLayout(4,2);
		JPanel panel = new JPanel(gridLayout);
		panel.setOpaque(false);
		int caseLayoutCount = gridLayout.getColumns() * gridLayout.getRows();
		int infoEnd = 1;
		
		JButton button1 = new JButton(new UndoProduction("retirer production", building));
		button1.setFocusable(false);
		panel.add(button1);
		
		
		if(building.getId() == EntityConfiguration.FORGE) {
			AbstractMap<Integer, ForUpgrade> upgradesAvailable = building.getUpgrades();
			AbstractMap<Integer, ForUpgrade> upgradesUse = new HashMap<Integer, ForUpgrade>();
			
			for(int key : upgradesAvailable.keySet()) {
				upgradesUse.put(key, upgradesAvailable.get(key));
			}
			
			for(Integer id : searchingUpgrades) {
				if(upgradesAvailable.containsKey(id)) {
					upgradesUse.remove(id);
				}
			}
			
			for(ForUpgrade upgrade : upgradesAvailable.values()) {
				if(!upgradesUse.containsValue(upgrade)) {
					panel.add(new JLabel());
				}
				else {
					JButton button = new JButton(new BuildingProduction("" + upgrade.getDescription(), upgrade.getId(), building ));
					button.setFocusable(false);
					button.setToolTipText("Coût : " + upgrade.getCost());
					if(upgrade.getAge() > this.manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getAge()) {
						button.setEnabled(false);
					}
					panel.add(button);
				}
				infoEnd++;
			}
		}
		else if(building.getId() == EntityConfiguration.HQ) {
			String name = manager.getFactionManager().getFactions().get(building.getFaction()).getRace().getPatronWorkers().get(building.getProductionId()).getDescription();
			AbstractMap<Integer, ForUpgrade> upgradesAvailable = building.getUpgrades();
			AbstractMap<Integer, ForUpgrade> upgradesUse = new HashMap<Integer, ForUpgrade>();
			
			for(int key : upgradesAvailable.keySet()) {
				upgradesUse.put(key, upgradesAvailable.get(key));
			}
			
			for(Integer id : searchingUpgrades) {
				if(upgradesAvailable.containsKey(id)) {
					upgradesUse.remove(id);
				}
			}
			
			for(ForUpgrade upgrade : upgradesAvailable.values()) {
				if(!upgradesUse.containsValue(upgrade)) {
					panel.add(new JLabel());
				}
				else {
					JButton button = new JButton(new BuildingProduction("" + upgrade.getDescription(), upgrade.getId(), building ));
					button.setFocusable(false);
					button.setToolTipText("Coût : " + upgrade.getCost());
					if(upgrade.getId() == EntityConfiguration.AGE_UPGRADE_2 && this.manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getAge() < 2) {
						button.setEnabled(false);
					}
					panel.add(button);
				}
				infoEnd++;
			}
			JButton button = new JButton(new BuildingProduction("" + name, building.getProductionId(), building ));
			button.setFocusable(false);
			button.setToolTipText("Coût : " + manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getRace().getPatronWorkers().get(building.getProductionId()).getCost());
			infoEnd++;
			
			panel.add(button);
		}
		else {
			String name = manager.getFactionManager().getFactions().get(building.getFaction()).getRace().getPatronFighters().get(building.getProductionId()).getDescription();
			JButton button = new JButton(new BuildingProduction("" + name, building.getProductionId(), building ));
			button.setFocusable(false);
			button.setToolTipText("Coût : " + manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getRace().getPatronFighters().get(building.getProductionId()).getCost());
			infoEnd++;
	
			panel.add(button);
		}
		
		for(int i = infoEnd; i < caseLayoutCount; i++) {
			panel.add(new JLabel());
		}
		
		descriptionPanel.add(panel);
		
		descriptionPanel.validate();
	}
	
	public void setDescriptionPanelForRessource(Ressource ressource)
	{
		descriptionPanel.removeAll();
		
		descriptionPanel.setLayout(new FlowLayout());
		
		ressourceStatistiquesLabel.setText("\nRessource restante : " + ressource.getHp());
		descriptionPanel.add(ressourceStatistiquesLabel);
		
		descriptionPanel.validate();
	}
	
	public void setDescriptionPanelStandard()
	{
		descriptionPanel.removeAll();
		descriptionPanel.setLayout(new FlowLayout());
		JTextArea area = new JTextArea();
		area.setEditable(false);
		area.setOpaque(false);
		area.setText("\n\nVotre faction est : " + manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getRace().getName());
		descriptionPanel.add(area);
		
		descriptionPanel.validate();
	}
	
	private JPanel createRessourceInfo()
	{	
		JPanel panel = new JPanel(new GridLayout(5, 1));
		ressourceInfoPanel = new JPanel(new GridLayout(2,5));

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
		
		ressourceInfoPanel.add(moneyLabel);
		ressourceInfoPanel.add(timeLabel);
		ressourceInfoPanel.add(populationLabel);
		ressourceInfoPanel.add(ageLabel);
		ressourceInfoPanel.add(race);
		
		ressourceInfoPanel.setOpaque(false);
		panel.add(ressourceInfoPanel);
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
		panelBis.setLayout(new GridLayout(PositionConfiguration.OPTIONSMENUMAXINDEX,1));
		
		for(int i = 1; i <= PositionConfiguration.OPTIONSMENUMAXINDEX; i++)
		{
			switch (i)
			{
				case PositionConfiguration.SONLABELINDEX:
					panelBis.add(new JLabel("Son:"));
					break;
				case PositionConfiguration.SONINDEX:
					panelBis.add(sonSlider);
					break;
				case PositionConfiguration.SCROLLINGLABELINDEX:
					panelBis.add(new JLabel("Scrolling:"));
					break;
				case PositionConfiguration.SCROLLINGINDEX:
					panelBis.add(scrollingSlider);
					break;
				case PositionConfiguration.BACKINDEX:
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
		panel.setOpaque(false);
		panelBis.setOpaque(false);
		sonSlider.setOpaque(false);
		scrollingSlider.setOpaque(false);
		
		return panel;
	}
	
	private JPanel createPauseMenuPanel()
	{
		JPanel panel = new JPanel();
		JPanel panelBis = new JPanel();
		
		GridLayout gridLayaout = new GridLayout(1,3);
		panelBis.setLayout(new GridLayout(PositionConfiguration.PAUSEMENUMAXINDEX,1));
		
		for(int i = 1; i <= PositionConfiguration.PAUSEMENUMAXINDEX; i++)
		{
			switch(i)
			{
				case PositionConfiguration.BACKTOGAMEINDEX:
					panelBis.add(new JButton(new UnpauseGameButton("RETOUR AU JEU")));
					break;
				case PositionConfiguration.OPTIONSINDEX:
					panelBis.add(new JButton(new GoToOptionFromPauseMenuButton("OPTION")));
					break;
				case PositionConfiguration.LEAVEINDEX:
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
		panelBis.setOpaque(false);
		panel.setOpaque(false);
		
		return panel;
	}
	
	public JPanel createMinimapPanel() {
		minimapPanel = new JPanel();
		minimapPanel.setOpaque(false);
		
		return minimapPanel;
	}
	
	public void actualiseCurrentProdLabel(ProductionBuilding building) {
		if(building.getIsProducing()) {
			int idProduction = building.getElementCount().get(0);
			
			Race race = manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getRace();
			ForFighter fighter = race.getPatronFighters().get(idProduction);
			ForUpgrade forgeUpgrade = race.getForgeUpgrades().get(idProduction);
			ForUpgrade hqUpgrade = race.getHQUpgrades().get(idProduction);
			ForWorker worker = race.getPatronWorkers().get(idProduction);
			if(fighter != null) {
				currentProductionLabel.setText("\n      Prod: " + fighter.getDescription()+ ", temps restant : " + (int)building.getTimer() + ", file d'attente : " + (building.getElementCount().size()- 1));
			}
			else if(forgeUpgrade != null) {
				currentProductionLabel.setText("\n      Prod: " + forgeUpgrade.getDescription()+ ", temps restant : " + (int)building.getTimer() + ", file d'attente : " + (building.getElementCount().size() - 1));
			}
			else if(hqUpgrade != null){
				currentProductionLabel.setText("\n      Prod: " + hqUpgrade.getDescription()+ ", temps restant : " + (int)building.getTimer() + ", file d'attente : " + (building.getElementCount().size() - 1));
			}
			else if(worker != null) {
				currentProductionLabel.setText("\n      Prod: " + worker.getDescription()+ ", temps restant : " + (int)building.getTimer() + ", file d'attente : " + (building.getElementCount().size() - 1));
			}
		}
		else {
			currentProductionLabel.setText("\n      Rien n'est en production");
		}
	}
	
	public void actualiseStatistiquesBuilding(ProductionBuilding building) {
		buildingStatistiquesLabel.setText("\n      " + building.getDescription() + "\n      Points de vie : " + building.getHp());
		if(building.getHp() <= 0) {
			this.setDescriptionPanelStandard();
		}
	}
	
	public void actualiseStatistiquesBuilding(AttackBuilding building) {
		buildingStatistiquesLabel.setText("\n\n" + building.getDescription() + ", attaque les unités adverse aux alentours\nPoints de vie : " + building.getHp());
		if(building.getHp() <= 0) {
			this.setDescriptionPanelStandard();
		}
	}
	
	public void actualiseStatistiquesBuilding(StorageBuilding building) {
		buildingStatistiquesLabel.setText("\n\n" + building.getDescription() + ", permet de déposer les ressource\nPoints de vie : " + building.getHp());
		if(building.getHp() <= 0) {
			this.setDescriptionPanelStandard();
		}
	}
	
	public void actualiseStatistiquesRessource(int hp) {
		ressourceStatistiquesLabel.setText("\nRessource restante : " + hp);
		if(hp <= 0) {
			this.setDescriptionPanelStandard();
		}
	}

	public void update() {
		if(state == GameConfiguration.INGAME){
			int populationCount = manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getPopulationCount();
			int maxPopulation = manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getMaxPopulation();
			time += 1.0 / GameConfiguration.GAME_SPEED;
			this.moneyLabel.setText("ARGENT:" + manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getMoneyCount());
			this.timeLabel.setText("TEMPS:" + (int)time);
			this.populationLabel.setText("POPULATION:" + populationCount + " / " + maxPopulation);
			this.ageLabel.setText("AGE:" + manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).getAge());
			
			if(this.manager.getSelectedProdBuilding() != null) {
				ProductionBuilding prod = manager.getSelectedProdBuilding();
				actualiseStatistiquesBuilding(prod);
				actualiseCurrentProdLabel(prod);
			}
			else if(this.manager.getSelectedAttackBuilding() != null) {
				AttackBuilding attack = manager.getSelectedAttackBuilding();
				actualiseStatistiquesBuilding(attack);
			}
			else if(this.manager.getSelectedStorageBuilding() != null) {
				StorageBuilding storage = manager.getSelectedStorageBuilding();
				actualiseStatistiquesBuilding(storage);
			}
			else if(this.manager.getSelectedRessource() != null) {
				Ressource ressource = manager.getSelectedRessource();
				actualiseStatistiquesRessource(ressource.getHp());
			}
			
			if(manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).isUpgradeAge()) {
				if(this.manager.getSelectedProdBuilding() !=  null) {
					List<Integer> searchingUpgrades = manager.getFactionManager().getFactions().get(this.manager.getSelectedProdBuilding().getFaction()).getSearchingUpgrades();
					setDescriptionPanelForBuilding(this.manager.getSelectedProdBuilding(), searchingUpgrades);
				}
				else if(this.manager.getSelectedWorkers().isEmpty() == false && this.manager.getSelectedFighters().isEmpty()) {
					setDescriptionPanelForConstruction();
				}
				manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).setUpgradeAge(false);
			}
			if(manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).isStatUpgrade()) {
				if(this.manager.getSelectedUnits().size() > 0) {
					setDescriptionPanelForUnit(this.manager.getSelectedUnits().get(0));
					manager.getFactionManager().getFactions().get(EntityConfiguration.PLAYER_FACTION).setStatUpgrade(false);
				}
			}
			if(manager.getSelectedUnits().size() > 0) {
				if(this.manager.getSelectedUnits().get(0).isHit()) {
					setDescriptionPanelForUnit(this.manager.getSelectedUnits().get(0));
				}
			}
			
			for(Entity entity : manager.getDrawingList()) {
				Position p = entity.getPosition();
				fog.clearFog(p.getX() - entity.getSightRange() / 6, p.getY() - entity.getSightRange() / 6, entity.getSightRange());
			}
		}
		if(audioManager.getSliderVolume() != this.sonSlider.getValue()) {
			audioManager.manageVolume(this.sonSlider.getValue());
		}
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		if(state == GameConfiguration.INGAME)
		{
			if(this.paintStrategyGame == null) {
				minimap = new Minimap(minimapPanel);
				paintStrategyGame = new PaintStrategyGame(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT, minimapPanel, minimap);
			}
			this.paintStrategyGame.paint(this.map, g, this.camera, graphicsManager);
			List<Entity> entities = manager.getDrawingList();
			List<Unit> units = manager.getSelectedUnits();
			
			Entity building = null;
			Entity ressource = null;
			if(manager.getSelectedAttackBuilding() != null) {
				building = manager.getSelectedAttackBuilding();
			}
			else if(manager.getSelectedProdBuilding() != null) {
				building = manager.getSelectedProdBuilding();
			}
			else if(manager.getSelectedStorageBuilding() != null) {
				building = manager.getSelectedStorageBuilding();
			}
			
			if(manager.getSelectedRessource() != null) {
				ressource = manager.getSelectedRessource();
			}
			
			for(Entity entity : entities)
			{
				this.paintStrategyGame.paint(entity, g, camera, graphicsManager);
			}
			
			for(Unit unit : units) {
				this.paintStrategyGame.paintRectSelectionUnit(unit, g, camera);
			}
			
			if(building != null) {
				this.paintStrategyGame.paintSelectionRectBuilding(building, g, camera);
			}
			else if(ressource != null) {
				this.paintStrategyGame.paintSelectionRectRessource(ressource, g, camera);
			}
			
			if(selectionRectangle.isActive())
			{
				this.paintStrategyGame.paint(selectionRectangle, g, camera);
			}
			
			this.paintStrategyGame.paint(fog, g, camera);
			
			this.paintStrategyGame.paintGui(map, fog, entities, g, camera, descriptionPanel, ressourceInfoPanel, minimapPanel, minimap, graphicsManager);
		}
		else if(state == GameConfiguration.INMENU)
		{
			if(this.paintStrategyMainMenu == null) {
				paintStrategyMainMenu = new PaintStrategyMainMenu();
			}
			this.paintStrategyMainMenu.paint(g, graphicsManager);		
		}
		else if(state == GameConfiguration.INOPTION)
		{
			if(this.paintStrategyOption == null) {
				paintStrategyOption = new PaintStrategyOption();
			}
			this.paintStrategyOption.paint(g, graphicsManager);
		}
		else if(state == GameConfiguration.INPAUSEMENU)
		{
			if(this.paintStrategyPauseMenu == null) {
				paintStrategyPauseMenu = new PaintStrategyPauseMenu();
			}
			this.paintStrategyPauseMenu.paint(g, graphicsManager);
		}
	}
	
	private class BuildingProduction extends AbstractAction
	{
		private static final long serialVersionUID = 1L;
		
		private int id;
		private ProductionBuilding prodBuilding;
		
		public BuildingProduction(String name, int id, ProductionBuilding building)
		{
			super(name);
			this.id = id;
			this.prodBuilding = building;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int money = manager.getFactionManager().getFactions().get(prodBuilding.getFaction()).getMoneyCount();
			int priceOfProduction = prodBuilding.startProd(id, money);
			if(priceOfProduction > 0) {
				manager.getFactionManager().getFactions().get(prodBuilding.getFaction()).setMoneyCount(money - priceOfProduction);
				if(id >= EntityConfiguration.ARMOR_UPGRADE && id <= EntityConfiguration.AGE_UPGRADE_2) {
					manager.getFactionManager().getFactions().get(prodBuilding.getFaction()).getSearchingUpgrades().add(id);
					setDescriptionPanelForBuilding(prodBuilding, manager.getFactionManager().getFactions().get(prodBuilding.getFaction()).getSearchingUpgrades());
				}
			}
		}
	}
	
	private class UndoProduction extends AbstractAction{
		private static final long serialVersionUID = 1L;
		
		private ProductionBuilding prodBuilding;
		
		public UndoProduction(String name, ProductionBuilding building) {
			super(name);
			this.prodBuilding = building;
		}
		
		public void actionPerformed(ActionEvent e) {
			int money = manager.getFactionManager().getFactions().get(prodBuilding.getFaction()).getMoneyCount();
			List<Integer>searchingUpgrades = manager.getFactionManager().getFactions().get(prodBuilding.getFaction()).getSearchingUpgrades();
			manager.getFactionManager().getFactions().get(prodBuilding.getFaction()).setMoneyCount(money + prodBuilding.removeProduction(searchingUpgrades));
			setDescriptionPanelForBuilding(prodBuilding, searchingUpgrades);
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
			state = GameConfiguration.INPAUSEMENU;
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
			setSelectedMap(1);
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
			setSelectedMap(2);
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
			setSelectedMap(3);
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
			state = GameConfiguration.INOPTION;
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
			fog = new Fog(GameConfiguration.LINE_COUNT, GameConfiguration.COLUMN_COUNT);
			audioManager.startFx(0);
			time = 0;
			map = GameBuilder.buildMap(selectedMap, graphicsManager, manager);
			GameBuilder.buildFaction(manager, boxPlayer1.getSelectedIndex() + 1, boxPlayer2.getSelectedIndex() + 1, map);
			
			gamePanel = createGamePanel();
			gamePanel.setVisible(false);
			
			oldState = state;
			state = GameConfiguration.INGAME;
			
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
			state = GameConfiguration.INGAME;
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
			state = GameConfiguration.INOPTION;
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
			state = GameConfiguration.INMENU;
			manageState();
			mouse.reset();
		}
	}
	
	private void manageState()
	{
		switch(state)
		{
			case GameConfiguration.INMENU:
				if(oldState == GameConfiguration.INOPTION)
				{
					optionPanel.setVisible(false);
					getMainPanel().remove(optionPanel);
				}
				else if(oldState == GameConfiguration.INPAUSEMENU)
				{
					pauseMenuPanel.setVisible(false);
					getMainPanel().remove(pauseMenuPanel);
					manager.clean();
					camera.reset();
				}
				audioManager.startFx(1);
				mainMenuPanel.setVisible(true);
				getMainPanel().add(mainMenuPanel);
				break;
				
			case GameConfiguration.INGAME:
				if(oldState == GameConfiguration.INPAUSEMENU)
				{
					pauseMenuPanel.setVisible(false);
					getMainPanel().remove(pauseMenuPanel);
				}
				else if(oldState == GameConfiguration.INMENU)
				{
					mainMenuPanel.setVisible(false);
					getMainPanel().remove(mainMenuPanel);
				}
				gamePanel.setVisible(true);
				getMainPanel().add(gamePanel);
				break;
				
			case GameConfiguration.INOPTION:
				if(oldState == GameConfiguration.INPAUSEMENU)
				{
					gamePanel.setVisible(false);
					pauseMenuPanel.setVisible(false);
					getMainPanel().remove(gamePanel);
					getMainPanel().remove(pauseMenuPanel);
				}
				else if(oldState == GameConfiguration.INMENU)
				{
					mainMenuPanel.setVisible(false);
					getMainPanel().remove(mainMenuPanel);
				}
				audioManager.startFx(1);
				optionPanel.setVisible(true);
				getMainPanel().add(optionPanel);
				break;
				
			case GameConfiguration.INPAUSEMENU:
				if(oldState == GameConfiguration.INOPTION)
				{
					audioManager.startFx(1);
					optionPanel.setVisible(false);
					getMainPanel().remove(optionPanel);
				}
				else if(oldState == GameConfiguration.INGAME)
				{
					gamePanel.setVisible(false);
					getMainPanel().remove(gamePanel);
				}
				audioManager.startFx(1);
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

	public int getSelectedMap() {
		return selectedMap;
	}

	public void setSelectedMap(int selectedMap) {
		this.selectedMap = selectedMap;
	}

	public Fog getFog() {
		return fog;
	}

	public void setFog(Fog fog) {
		this.fog = fog;
	}

	public Minimap getMinimap() {
		return minimap;
	}
}
