package engine.manager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import configuration.EntityConfiguration;
import configuration.MapConfiguration;

/**
 * 
 * this class is require to manage and conserv all bufferedImage of the game
 * @author gautier
 */

public class GraphicsManager {
	private BufferedImage[][] leftInfantryPlayer;
	private BufferedImage[][] rightInfantryPlayer;
	
	private BufferedImage[][] leftInfantryBot;
	private BufferedImage[][] rightInfantryBot;
	
	private BufferedImage[][] leftArcherPlayer;
	private BufferedImage[][] rightArcherPlayer;
	
	private BufferedImage[][] leftArcherBot;
	private BufferedImage[][] rightArcherBot;
	
	private BufferedImage[][] leftCavalryPlayer;
	private BufferedImage[][] rightCavalryPlayer;
	
	private BufferedImage[][] leftCavalryBot;
	private BufferedImage[][] rightCavalryBot;
	
	private BufferedImage[][] leftSpecialPlayer;
	private BufferedImage[][] rightSpecialPlayer;
	
	private BufferedImage[][] leftSpecialBot;
	private BufferedImage[][] rightSpecialBot;
	
	private BufferedImage[][] leftWorkerPlayer;
	private BufferedImage[][] rightWorkerPlayer;
	
	private BufferedImage[][] leftWorkerBot;
	private BufferedImage[][] rightWorkerBot;

	private BufferedImage[][] forge;
	private BufferedImage[][] barrack;
	private BufferedImage[][] archery;
	private BufferedImage[][] stable;
	private BufferedImage[][] castle;
	private BufferedImage[][] storage;
	private BufferedImage[][] tower;
	private BufferedImage[][] hq;
	
	private BufferedImage[][] grass;

	private BufferedImage[][] rock;
	private BufferedImage[][] tree;
	private BufferedImage[][] water;
	private BufferedImage[][] sand;
	private BufferedImage[][] gold;
	
	private BufferedImage[][] siteConstruction;
	
	private BufferedImage panelGaucheBas;
	
	/**
	 * constructor
	 * load all building and tile texture
	 */
	public GraphicsManager() {		
		try {
			
			siteConstruction = new BufferedImage[1][1];
			siteConstruction[0][0] = ImageIO.read(new File("src/graphics/site_construction.png"));
			BufferedImage imageBis = siteConstruction[0][0].getSubimage(10, 10, 45, 45);
			siteConstruction[0][0] = imageBis;
			
			forge = new BufferedImage[1][2];
			forge[0][0] = ImageIO.read(new File("src/graphics/forgePlayer.png"));
			forge[0][1] = ImageIO.read(new File("src/graphics/forgeBot.png"));
			
			barrack = new BufferedImage[1][2];
			barrack[0][0] = ImageIO.read(new File("src/graphics/barrackPlayer.png"));
			barrack[0][1] = ImageIO.read(new File("src/graphics/barrackBot.png"));
			
			archery = new BufferedImage[1][2];
			archery[0][0] = ImageIO.read(new File("src/graphics/archeriePlayer.png"));
			archery[0][1] = ImageIO.read(new File("src/graphics/archerieBot.png"));
			
			stable = new BufferedImage[1][2];
			stable[0][0] = ImageIO.read(new File("src/graphics/stablePlayer.png"));
			stable[0][1] = ImageIO.read(new File("src/graphics/stableBot.png"));
			
			castle = new BufferedImage[1][2];
			castle[0][0] = ImageIO.read(new File("src/graphics/castlePlayer.png"));
			castle[0][1] = ImageIO.read(new File("src/graphics/castleBot.png"));
			
			storage = new BufferedImage[1][2];
			storage[0][0] = ImageIO.read(new File("src/graphics/storagePlayer.png"));
			storage[0][1] = ImageIO.read(new File("src/graphics/storageBot.png"));
			
			tower = new BufferedImage[1][2];
			tower[0][0] = ImageIO.read(new File("src/graphics/towerPlayer.png"));
			tower[0][1] = ImageIO.read(new File("src/graphics/towerBot.png"));
			
			hq = new BufferedImage[1][2];
			hq[0][0] = ImageIO.read(new File("src/graphics/hqPlayer.png"));
			hq[0][1] = ImageIO.read(new File("src/graphics/hqBot.png"));
			
			grass = new BufferedImage[1][1];
			grass[0][0] = ImageIO.read(new File("src/graphics/grass_1.png"));
			
			rock = new BufferedImage[1][1];
			rock[0][0] = ImageIO.read(new File("src/graphics/stone_1.png"));
			imageBis = rock[0][0].getSubimage(10, 10, 40, 40);
			rock[0][0] = imageBis;
			
			tree = new BufferedImage[1][1];
			tree[0][0] = ImageIO.read(new File("src/graphics/tree.png"));
			
			gold = new BufferedImage[1][1];
			gold[0][0] = ImageIO.read(new File("src/graphics/gold_1.png"));
			imageBis = gold[0][0].getSubimage(10, 10, 40, 40);
			gold[0][0] = imageBis;
			
			sand = new BufferedImage[1][1];
			sand[0][0] = ImageIO.read(new File("src/graphics/sand_1.png"));
			
			panelGaucheBas = ImageIO.read(new File("src/graphics/panelGaucheBas.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		initTreeGraphics();
		initWaterGraphics();
	}
	
	/**
	 * load all water graphics
	 */
	private void initWaterGraphics() {
		water = new BufferedImage[1][9];
		try {
			water[0][0] = ImageIO.read(new File("src/graphics/water_1.png"));
			water[0][1] = ImageIO.read(new File("src/graphics/water_bord_up.png"));
			water[0][2] = ImageIO.read(new File("src/graphics/water_bord_down.png"));
			water[0][3] = ImageIO.read(new File("src/graphics/water_bord_left.png"));
			water[0][4] = ImageIO.read(new File("src/graphics/water_bord_right.png"));
			water[0][5] = ImageIO.read(new File("src/graphics/water_turn_up_left.png"));
			water[0][6] = ImageIO.read(new File("src/graphics/water_turn_up_right.png"));
			water[0][7] = ImageIO.read(new File("src/graphics/water_turn_down_left.png"));
			water[0][8] = ImageIO.read(new File("src/graphics/water_turn_down_right.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * load all tree
	 */
	private void initTreeGraphics() {
		tree = new BufferedImage[1][3];

		try {
			BufferedImage image = ImageIO.read(new File("src/graphics/tree.png"));
			for(int i = 0; i < 3; i++) {
				tree[0][i] = image.getSubimage(i * 32, 0, 32, 32);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * load player cavalry texture
	 */
	private void initCavalryPlayerGraphics() {
		BufferedImage cavalryImage = null;
		
		try {
			cavalryImage = ImageIO.read(new File("src/graphics/cavalryPlayer.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		leftCavalryPlayer = new BufferedImage[4][4];
		rightCavalryPlayer = new BufferedImage[4][4];
		//CAVALRY
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = cavalryImage.getSubimage(j * 512, (i * 2) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				int a = 0;
				if(i == EntityConfiguration.IDDLE) {
					a = EntityConfiguration.ATTACK;
				}
				else if(i == EntityConfiguration.WALK) {
					a = EntityConfiguration.DIE;
				}
				else if(i == EntityConfiguration.ATTACK) {
					a = EntityConfiguration.IDDLE;
				}
				else if(i == EntityConfiguration.DIE) {
					a = EntityConfiguration.WALK;
				}
				leftCavalryPlayer[a][j] = imageBis;
			}
		}
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = cavalryImage.getSubimage(j * 512, (i * 2 + 1) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				int a = 0;
				if(i == EntityConfiguration.IDDLE) {
					a = EntityConfiguration.ATTACK;
				}
				else if(i == EntityConfiguration.WALK) {
					a = EntityConfiguration.DIE;
				}
				else if(i == EntityConfiguration.ATTACK) {
					a = EntityConfiguration.IDDLE;
				}
				else if(i == EntityConfiguration.DIE) {
					a = EntityConfiguration.WALK;
				}
				rightCavalryPlayer[a][j] = imageBis;
			}
		}
	}
	
	/**
	 * load player worker texture
	 */
	private void initWorkerPlayerGraphics() {
		BufferedImage workerImage = null;
		
		try {
			workerImage = ImageIO.read(new File("src/graphics/engineerPlayer.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		leftWorkerPlayer = new BufferedImage[4][4];
		rightWorkerPlayer = new BufferedImage[4][4];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = workerImage.getSubimage(j * 512, (i * 2) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				leftWorkerPlayer[i][j] = imageBis;
			}
		}
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = workerImage.getSubimage(j * 512, (i * 2 + 1) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				rightWorkerPlayer[i][j] = imageBis;
			}
		}
	}
	
	/**
	 * load infantry player texture
	 */
	private void initInfantryPlayerGraphics() {
		BufferedImage infantryImage = null;
		
		try {
			infantryImage = ImageIO.read(new File("src/graphics/knightPlayer.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		leftInfantryPlayer = new BufferedImage[4][4];
		rightInfantryPlayer = new BufferedImage[4][4];
		//INFANTRY
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = infantryImage.getSubimage(j * 512, (i * 2) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				leftInfantryPlayer[i][j] = imageBis;
			}
		}
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = infantryImage.getSubimage(j * 512, (i * 2 + 1) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				rightInfantryPlayer[i][j] = imageBis;
			}
		}
	}
	
	/**
	 * load player archer texture
	 */
	private void initArcherPlayerGraphics() {
		BufferedImage archerImage = null;
		
		try {
			archerImage = ImageIO.read(new File("src/graphics/archerPlayer.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		leftArcherPlayer = new BufferedImage[4][4];
		rightArcherPlayer = new BufferedImage[4][4];
		//ARCHER
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = archerImage.getSubimage(j * 512, (i * 2) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				leftArcherPlayer[i][j] = imageBis;
			}
		}
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = archerImage.getSubimage(j * 512, (i * 2 + 1) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				rightArcherPlayer[i][j] = imageBis;
			}
		}
	}
	
	/**
	 * load special unit player texture
	 */
	private void initSpecialPlayerGraphics() {
		BufferedImage specialImage = null;
		
		try {
			specialImage = ImageIO.read(new File("src/graphics/thiefPlayer.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		leftSpecialPlayer = new BufferedImage[4][4];
		rightSpecialPlayer = new BufferedImage[4][4];
		//SPECIAL
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = specialImage.getSubimage(j * 512, (i * 2) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				leftSpecialPlayer[i][j] = imageBis;
			}
		}
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = specialImage.getSubimage(j * 512, (i * 2 + 1) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				rightSpecialPlayer[i][j] = imageBis;
			}
		}
	}
	
	/**
	 * load bot cavalry texture
	 */
	private void initCavalryBotGraphics() {
		BufferedImage cavalryImage = null;
		
		try {
			cavalryImage = ImageIO.read(new File("src/graphics/cavalryBot.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		leftCavalryBot = new BufferedImage[4][4];
		rightCavalryBot = new BufferedImage[4][4];
		//CAVALRY
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = cavalryImage.getSubimage(j * 512, (i * 2) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				int a = 0;
				if(i == EntityConfiguration.IDDLE) {
					a = EntityConfiguration.ATTACK;
				}
				else if(i == EntityConfiguration.WALK) {
					a = EntityConfiguration.DIE;
				}
				else if(i == EntityConfiguration.ATTACK) {
					a = EntityConfiguration.IDDLE;
				}
				else if(i == EntityConfiguration.DIE) {
					a = EntityConfiguration.WALK;
				}
				leftCavalryBot[a][j] = imageBis;
			}
		}
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = cavalryImage.getSubimage(j * 512, (i * 2 + 1) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				int a = 0;
				if(i == EntityConfiguration.IDDLE) {
					a = EntityConfiguration.ATTACK;
				}
				else if(i == EntityConfiguration.WALK) {
					a = EntityConfiguration.DIE;
				}
				else if(i == EntityConfiguration.ATTACK) {
					a = EntityConfiguration.IDDLE;
				}
				else if(i == EntityConfiguration.DIE) {
					a = EntityConfiguration.WALK;
				}
				rightCavalryBot[a][j] = imageBis;
			}
		}
	}
	
	/**
	 * load bot worker texture
	 */
	private void initWorkerBotGraphics() {
		BufferedImage workerImage = null;
		
		try {
			workerImage = ImageIO.read(new File("src/graphics/engineerBot.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		leftWorkerBot = new BufferedImage[4][4];
		rightWorkerBot = new BufferedImage[4][4];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = workerImage.getSubimage(j * 512, (i * 2) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				leftWorkerBot[i][j] = imageBis;
			}
		}
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = workerImage.getSubimage(j * 512, (i * 2 + 1) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				rightWorkerBot[i][j] = imageBis;
			}
		}
	}
	
	/**
	 * load bot infantry texture
	 */
	private void initInfantryBotGraphics() {
		BufferedImage infantryImage = null;
		
		try {
			infantryImage = ImageIO.read(new File("src/graphics/knightBot.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		leftInfantryBot = new BufferedImage[4][4];
		rightInfantryBot = new BufferedImage[4][4];
		//INFANTRY
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = infantryImage.getSubimage(j * 512, (i * 2) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				leftInfantryBot[i][j] = imageBis;
			}
		}
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = infantryImage.getSubimage(j * 512, (i * 2 + 1) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				rightInfantryBot[i][j] = imageBis;
			}
		}
	}
	
	/**
	 * load bot archer texture
	 */
	private void initArcherBotGraphics() {
		BufferedImage archerImage = null;
		
		try {
			archerImage = ImageIO.read(new File("src/graphics/archerBot.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		leftArcherBot = new BufferedImage[4][4];
		rightArcherBot = new BufferedImage[4][4];
		//ARCHER
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = archerImage.getSubimage(j * 512, (i * 2) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				leftArcherBot[i][j] = imageBis;
			}
		}
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = archerImage.getSubimage(j * 512, (i * 2 + 1) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				rightArcherBot[i][j] = imageBis;
			}
		}
	}
	
	/**
	 * load bot special unit texture
	 */
	private void initSpecialBotGraphics() {
		BufferedImage specialImage = null;
		
		try {
			specialImage = ImageIO.read(new File("src/graphics/thiefBot.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		leftSpecialBot = new BufferedImage[4][4];
		rightSpecialBot = new BufferedImage[4][4];
		//SPECIAL
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = specialImage.getSubimage(j * 512, (i * 2) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				leftSpecialBot[i][j] = imageBis;
			}
		}
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				BufferedImage imageBis = specialImage.getSubimage(j * 512, (i * 2 + 1) * 511, 512, 511);
				int decoupeX = 80;
				int decoupeY = 80;
				imageBis = imageBis.getSubimage(decoupeX, decoupeY, 512 - decoupeX * 2, 511 - decoupeY * 2);
				rightSpecialBot[i][j] = imageBis;
			}
		}
	}

	public BufferedImage[][] getLeftInfantry(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			if(this.leftInfantryPlayer == null) {
				initInfantryPlayerGraphics();
			}
			return this.leftInfantryPlayer;
		}
		else {
			if(this.leftInfantryBot == null) {
				initInfantryBotGraphics();
			}
			return this.leftInfantryBot;
		}
	}

	public BufferedImage[][] getRightInfantry(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			if(this.rightInfantryPlayer == null) {
				initInfantryPlayerGraphics();
			}
			return this.rightInfantryPlayer;
		}
		else {
			if(this.rightInfantryBot == null) {
				initInfantryBotGraphics();
			}
			return this.rightInfantryBot;
		}
	}
	
	public BufferedImage[][] getLeftArcher(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			if(this.leftArcherPlayer == null) {
				initArcherPlayerGraphics();
			}
			return this.leftArcherPlayer;
		}
		else {
			if(this.leftArcherBot == null) {
				initArcherBotGraphics();
			}
			return this.leftArcherBot;
		}
	}

	public BufferedImage[][] getRightArcher(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			if(this.rightArcherPlayer == null) {
				initArcherPlayerGraphics();
			}
			return this.rightArcherPlayer;
		}
		else {
			if(this.rightArcherBot == null) {
				initArcherBotGraphics();
			}
			return this.rightArcherBot;
		}
	}
	
	public BufferedImage[][] getLeftCavalry(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			if(this.leftCavalryPlayer == null) {
				initCavalryPlayerGraphics();
			}
			return this.leftCavalryPlayer;
		}
		else {
			if(this.leftCavalryBot == null) {
				initCavalryBotGraphics();
			}
			return this.leftCavalryBot;
		}
	}

	public BufferedImage[][] getRightCavalry(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			if(this.rightCavalryPlayer == null) {
				initCavalryPlayerGraphics();
			}
			return this.rightCavalryPlayer;
		}
		else {
			if(this.rightCavalryBot == null) {
				initCavalryBotGraphics();
			}
			return this.rightCavalryBot;
		}
	}
	
	public BufferedImage[][] getLeftSpecial(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			if(this.leftSpecialPlayer == null) {
				initSpecialPlayerGraphics();
			}
			return this.leftSpecialPlayer;
		}
		else {
			if(this.leftSpecialBot == null) {
				initSpecialBotGraphics();
			}
			return this.leftSpecialBot;
		}
	}

	public BufferedImage[][] getRightSpecial(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			if(this.rightSpecialPlayer == null) {
				initSpecialPlayerGraphics();
			}
			return this.rightSpecialPlayer;
		}
		else {
			if(this.rightSpecialBot == null) {
				initSpecialBotGraphics();
			}
			return this.rightSpecialBot;
		}
	}
	

	public BufferedImage[][] getLeftWorker(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			if(this.leftWorkerPlayer == null) {
				initWorkerPlayerGraphics();
			}
			return this.leftWorkerPlayer;
		}
		else {
			if(this.leftWorkerBot == null) {
				initWorkerBotGraphics();
			}
			return this.leftWorkerBot;
		}
	}

	public BufferedImage[][] getRightWorker(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			if(this.rightWorkerPlayer == null) {
				initWorkerPlayerGraphics();
			}
			return this.rightWorkerPlayer;
		}
		else {
			if(this.rightWorkerBot == null) {
				initWorkerBotGraphics();
			}
			return this.rightWorkerBot;
		}
	}

	public BufferedImage[][] getForge(int faction) {
		BufferedImage[][] tmp = new BufferedImage[1][1];
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			tmp[0][0] = forge[0][0];
		}
		else {
			tmp[0][0] = forge[0][1];
		}
		return tmp;
	}

	public BufferedImage[][] getBarrack(int faction) {
		BufferedImage[][] tmp = new BufferedImage[1][1];
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			tmp[0][0] = barrack[0][0];
		}
		else {
			tmp[0][0] = barrack[0][1];
		}
		return tmp;
	}

	public BufferedImage[][] getArchery(int faction) {
		BufferedImage[][] tmp = new BufferedImage[1][1];
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			tmp[0][0] = archery[0][0];
		}
		else {
			tmp[0][0] = archery[0][1];
		}
		return tmp;
	}

	public BufferedImage[][] getStable(int faction) {
		BufferedImage[][] tmp = new BufferedImage[1][1];
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			tmp[0][0] = stable[0][0];
		}
		else {
			tmp[0][0] = stable[0][1];
		}
		return tmp;
	}

	public BufferedImage[][] getCastle(int faction) {
		BufferedImage[][] tmp = new BufferedImage[1][1];
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			tmp[0][0] = castle[0][0];
		}
		else {
			tmp[0][0] = castle[0][1];
		}
		return tmp;
	}

	public BufferedImage[][] getStorage(int faction) {
		BufferedImage[][] tmp = new BufferedImage[1][1];
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			tmp[0][0] = storage[0][0];
		}
		else {
			tmp[0][0] = storage[0][1];
		}
		return tmp;
	}

	public BufferedImage[][] getTower(int faction) {
		BufferedImage[][] tmp = new BufferedImage[1][1];
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			tmp[0][0] = tower[0][0];
		}
		else {
			tmp[0][0] = tower[0][1];
		}
		return tmp;
	}

	public BufferedImage[][] getHq(int faction) {
		BufferedImage[][] tmp = new BufferedImage[1][1];
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			tmp[0][0] = hq[0][0];
		}
		else {
			tmp[0][0] = hq[0][1];
		}
		return tmp;
	}

	public BufferedImage[][] getGrass() {
		return grass;
	}

	public BufferedImage[][] getRock() {
		return rock;
	}

	public BufferedImage[][] getTree() {
		BufferedImage[][] image = new BufferedImage[1][1];
		Random rand = new Random();
		int random = rand.nextInt(3);
		
		image[0][0] = tree[0][random];
		return image;
	}
	
	public BufferedImage[][] getSiteConstruction(){
		return siteConstruction;
	}

	public BufferedImage[][] getWater(int id) {
		BufferedImage[][] image = new BufferedImage[1][1];
		
		if(id == MapConfiguration.WATER) {
			image[0][0] = water[0][0];
			return image;
		}
		else {
			id = id - (MapConfiguration.WATER_BORD_UP - 1);
			image[0][0] = water[0][id];
			return image;
		}
	}

	public BufferedImage[][] getGold() {
		return gold;
	}
	
	public BufferedImage getGrassTile() {
		return grass[0][0];
	}

	public BufferedImage getPanelGaucheBas() {
		return panelGaucheBas;
	}

	public BufferedImage[][] getSand() {
		return sand;
	}
}