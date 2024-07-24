package GameMode1.Main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;

public class Sounds {
	Clip musicClip;
	URL url[] = new URL[10];
    boolean isMusicPlaying = false;

	public Sounds() {
		url[0] = getClass().getResource("/GameMode1/res/Tetris.mid");
		url[1] = getClass().getResource("/GameMode1/res/delete line.wav");
		url[2] = getClass().getResource("/GameMode1/res/gameover.wav");
		url[3] = getClass().getResource("/GameMode1/res/rotation.wav");
		url[4] = getClass().getResource("/GameMode1/res/touch_floor.wav");

	}

	public void play(int i, boolean music) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(url[i]);
			Clip clip = AudioSystem.getClip();

			if (music) {
				musicClip = clip;
				isMusicPlaying = true;
			}
			clip.open(ais);
			clip.addLineListener(new LineListener() {

				@Override
				public void update(LineEvent event) {
					// TODO Auto-generated method stub
					if (event.getType() == Type.STOP) {
						clip.close();
					}
				}

			});
			ais.close();
			clip.start();
		} catch (Exception e) {

		}
	}
	public void loop() {
		 if (musicClip != null) {
	            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
	            isMusicPlaying = true; // Đánh dấu rằng nhạc đang được phát
	        }
	    }

	public void stop() {
		 if (musicClip != null) {
	            musicClip.stop();
	            musicClip.close();
	            isMusicPlaying = false; // Đánh dấu rằng nhạc đã dừng
	        }
	}
	
	  public boolean isMusicPlaying() {
	        return isMusicPlaying;
	    }
}
