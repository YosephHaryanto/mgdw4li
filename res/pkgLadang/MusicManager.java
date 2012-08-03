package pkgLadang;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

public class MusicManager {
	Player sound;
	MusicManager(){
		try {
			sound=Manager.createPlayer(getClass().getResourceAsStream("/loop.wav"), "audio/x-wav");
			sound.realize();
			sound.prefetch();
			sound.setLoopCount(-1); 
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play(){
		try {
			sound.start();
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void stop(){
		try {
			sound.stop();
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
