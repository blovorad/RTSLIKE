package engine.manager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import configuration.EntityConfiguration;

/**
 * 
 * @author gautier
 *
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
	private BufferedImage[][] gold;
	
	private BufferedImage panelGaucheBas;
	
	public GraphicsManager() {		
		try {
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
			tree = new BufferedImage[1][1];
			tree[0][0] = ImageIO.read(new File("src/graphics/plant_3.png"));
			water = new BufferedImage[1][1];
			water[0][0] = ImageIO.read(new File("src/graphics/water_1.png"));
			gold = new BufferedImage[1][1];
			gold[0][0] = ImageIO.read(new File("src/graphics/gold_1.png"));
			
			panelGaucheBas = ImageIO.read(new File("src/graphics/panelGaucheBas.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		initPlayerGraphics();
		initBotGraphics();
	}
	
	private void initBotGraphics() {
		BufferedImage workerImage = null;
		BufferedImage archerImage = null;
		BufferedImage infantryImage = null;
		BufferedImage specialImage = null;
		BufferedImage cavalryImage = null;
		
		try {
			workerImage = ImageIO.read(new File("src/graphics/engineerBot.png"));
			archerImage = ImageIO.read(new File("src/graphics/archerBot.png"));
			infantryImage = ImageIO.read(new File("src/graphics/knightBot.png"));
			specialImage = ImageIO.read(new File("src/graphics/thiefBot.png"));
			cavalryImage = ImageIO.read(new File("src/graphics/cavalryBot.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		//left gauche, right droite
		
		//WORKER FIRST
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

	private void initPlayerGraphics() {
		BufferedImage workerImage = null;
		BufferedImage archerImage = null;
		BufferedImage infantryImage = null;
		BufferedImage specialImage = null;
		BufferedImage cavalryImage = null;
		
		try {
			workerImage = ImageIO.read(new File("src/graphics/engineerPlayer.png"));
			archerImage = ImageIO.read(new File("src/graphics/archerPlayer.png"));
			infantryImage = ImageIO.read(new File("src/graphics/knightPlayer.png"));
			specialImage = ImageIO.read(new File("src/graphics/thiefPlayer.png"));
			cavalryImage = ImageIO.read(new File("src/graphics/cavalryPlayer.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		//left gauche, right droite
		
		//WORKER FIRST
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

	public BufferedImage[][] getLeftInfantry(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			return this.leftInfantryPlayer;
		}
		else {
			return this.leftInfantryBot;
		}
	}

	public BufferedImage[][] getRightInfantry(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			return this.rightInfantryPlayer;
		}
		else {
			return this.rightInfantryBot;
		}
	}
	
	public BufferedImage[][] getLeftArcher(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			return this.leftArcherPlayer;
		}
		else {
			return this.leftArcherBot;
		}
	}

	public BufferedImage[][] getRightArcher(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			return this.rightArcherPlayer;
		}
		else {
			return this.rightArcherBot;
		}
	}
	
	public BufferedImage[][] getLeftCavalry(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			return this.leftCavalryPlayer;
		}
		else {
			return this.leftCavalryBot;
		}
	}

	public BufferedImage[][] getRightCavalry(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			return this.rightCavalryPlayer;
		}
		else {
			return this.rightCavalryBot;
		}
	}
	
	public BufferedImage[][] getLeftSpecial(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			return this.leftSpecialPlayer;
		}
		else {
			return this.leftSpecialBot;
		}
	}

	public BufferedImage[][] getRightSpecial(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			return this.rightSpecialPlayer;
		}
		else {
			return this.rightSpecialBot;
		}
	}
	

	public BufferedImage[][] getLeftWorker(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			return this.leftWorkerPlayer;
		}
		else {
			return this.leftWorkerBot;
		}
	}

	public BufferedImage[][] getRightWorker(int faction) {
		if(faction == EntityConfiguration.PLAYER_FACTION) {
			return this.rightWorkerPlayer;
		}
		else {
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
		return tree;
	}

	public BufferedImage[][] getWater() {
		return water;
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
}
