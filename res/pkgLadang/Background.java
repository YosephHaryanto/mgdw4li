package pkgLadang;

import java.io.IOException;
import javax.microedition.lcdui.Image;

public class Background{
	
	final int SCREEN_SPLASH = 0;
	final int SCREEN_MAIN_MENU = 1;
	final int SCREEN_IN_GAME = 2;
	
	Image bg_splash;
	Image bg_main;
	
	public void initAll(){
		try {
			String uri = "/img/background/";
			bg_splash = Image.createImage(uri+"bg_main2.jpg");
			bg_main = Image.createImage(uri+"bg_main2.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Image changeBackground(int screenState){
		switch(screenState){
		case SCREEN_SPLASH:
			return bg_splash;
		case SCREEN_MAIN_MENU:
			return bg_main;
		default:
			return bg_splash;
		}
	}
	

}