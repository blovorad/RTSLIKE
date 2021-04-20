package engine.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * 
 * @author gautier
 *
 */

public class AudioManager {
	
	private int state;
	private int oldState = -1;
	private Clip musique;
	private int sliderVolume;
	private int currentMusique;
	private Clip fxMinage;
	private Clip fxAttack;
	private Clip fxOrder;
	private Clip otherFx;
	private List<File>fxs;
	
	public AudioManager() {
		currentMusique = 1;
		fxs = new ArrayList<File>();
		try {
			File file = new File("src/sounds/musique1.wav");
			musique = AudioSystem.getClip();
			otherFx = AudioSystem.getClip();
			fxOrder = AudioSystem.getClip();
			fxAttack = AudioSystem.getClip();
			fxMinage = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
			musique.open(inputStream);
			//musique.start();
			
			fxs.add(new File("src/sounds/launchGameFx.wav"));
			fxs.add(new File("src/sounds/menuButtonFx.wav"));
			fxs.add(new File("src/sounds/produceUpgradeFx.wav"));
			fxs.add(new File("src/sounds/yesMyLord.wav"));
			fxs.add(new File("src/sounds/yes.wav"));
			fxs.add(new File("src/sounds/enGarde.wav"));
			fxs.add(new File("src/sounds/combat.wav"));
			fxs.add(new File("src/sounds/unitProduit.wav"));
			fxs.add(new File("src/sounds/minage.wav"));
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//manageVolume(25);
	}
	
	public void manageVolume(int volume) {
		this.sliderVolume = volume;
		float volumeInFloat = (float)volume / 100;
		FloatControl gainControl = (FloatControl) musique.getControl(FloatControl.Type.MASTER_GAIN);        
	    gainControl.setValue(20f * (float) Math.log10(volumeInFloat));
	}
	
	public void update() {
		/*if(oldState != state) {
			if(state == 1 && oldState != 3) {
				File file = new File("src/sounds/musique2.wav");
				startSound(file);
				currentMusique = 2;
			}
			else if(state == 0) {
				File file = new File("src/sounds/musique1.wav");
				startSound(file);
				currentMusique = 1;
			}
			oldState = state;
		}
		else {
			if(!musique.isRunning()) {
				if(state == 0) {
					File file = new File("src/sounds/musique1.wav");
					startSound(file);
					currentMusique = 1;
				}
				else if(state == 1) {
					currentMusique++;
					if(currentMusique > 4) {
						currentMusique = 2;
					}
					File file = new File("src/sounds/musique" + currentMusique + ".wav");
					startSound(file);
				}
			}
		}*/
	}
	
	public void startSound(File path) {
		musique.close();
		try {
			/*System.out.println("tentative nouvelle musique " + currentMusique);
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(path);
			musique.open(inputStream);
			musique.start();*/
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void startFx(int id) {
		/*if(id == 8) {
			if(!fxMinage.isRunning()) {
				if(fxMinage != null) {
					fxMinage.close();
				}
				try {
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(fxs.get(id));
					fxMinage.open(inputStream);
					float volumeInFloat = (float)sliderVolume / 100;
					if(volumeInFloat > 15f) {
						volumeInFloat = 15f;
					}
					FloatControl gainControl = (FloatControl) fxMinage.getControl(FloatControl.Type.MASTER_GAIN);
					float volumeToChange = (float)Math.log10(volumeInFloat);
				    gainControl.setValue(volumeToChange);
				    fxMinage.start();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		else if(id >= 3 && id <= 4) {
			if(!fxOrder.isRunning()) {
				if(fxOrder != null) {
					fxOrder.close();
				}
				try {
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(fxs.get(id));
					fxOrder.open(inputStream);
					float volumeInFloat = (float)sliderVolume / 100;
					FloatControl gainControl = (FloatControl) fxOrder.getControl(FloatControl.Type.MASTER_GAIN);        
				    gainControl.setValue(20f * (float) Math.log10(volumeInFloat));
				    fxOrder.start();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		else if(id == 6) {
			if(!fxAttack.isRunning()) {
				if(fxAttack != null) {
					fxAttack.close();
				}
				try {
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(fxs.get(id));
					fxAttack.open(inputStream);
					float volumeInFloat = (float)sliderVolume / 100;
					FloatControl gainControl = (FloatControl) fxAttack.getControl(FloatControl.Type.MASTER_GAIN);        
				    gainControl.setValue(20f * (float) Math.log10(volumeInFloat));
				    fxAttack.start();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		else {
			if(!otherFx.isRunning()) {
				if(otherFx != null) {
					otherFx.close();
				}
				try {
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(fxs.get(id));
					otherFx.open(inputStream);
					float volumeInFloat = (float)sliderVolume / 100;
					FloatControl gainControl = (FloatControl) otherFx.getControl(FloatControl.Type.MASTER_GAIN);        
				    gainControl.setValue(20f * (float) Math.log10(volumeInFloat));
				    otherFx.start();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}*/
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
		if(oldState == -1) {
			oldState = state;
		}
	}

	public int getOldState() {
		return oldState;
	}

	public void setOldState(int oldState) {
		this.oldState = oldState;
	}

	public Clip getMusique() {
		return musique;
	}

	public void setMusique(Clip musique) {
		this.musique = musique;
	}

	public int getCurrentMusique() {
		return currentMusique;
	}

	public void setCurrentMusique(int currentMusique) {
		this.currentMusique = currentMusique;
	}

	public int getSliderVolume() {
		return sliderVolume;
	}

	public void setSliderVolume(int sliderVolume) {
		this.sliderVolume = sliderVolume;
	}
}
