package engine.manager;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioManager {
	
	private int state;
	private int oldState = -1;
	private Clip musique;
	private int sliderVolume;
	private int currentMusique;
	
	public AudioManager() {
		currentMusique = 1;
		try {
			File file = new File("src/sounds/musique1.wav");
			musique = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
			musique.open(inputStream);
			musique.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		manageVolume(25);
	}
	
	public void manageVolume(int volume) {
		this.sliderVolume = volume;
		float volumeInFloat = (float)volume / 100;
		FloatControl gainControl = (FloatControl) musique.getControl(FloatControl.Type.MASTER_GAIN);        
	    gainControl.setValue(20f * (float) Math.log10(volumeInFloat));
	}
	
	public void update() {
		if(oldState != state) {
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
		}
	}
	
	public void startSound(File path) {
		musique.close();
		try {
			System.out.println("tentative nouvelle musique " + currentMusique);
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(path);
			musique.open(inputStream);
			musique.start();
		} catch(Exception e){
			e.printStackTrace();
		}
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
