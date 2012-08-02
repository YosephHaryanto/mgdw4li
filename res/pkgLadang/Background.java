package pkgLadang;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Background{
	
	final int SCREEN_SPLASH = 0;
	final int SCREEN_MAIN_MENU = 1;
	final int SCREEN_IN_GAME = 2;
	
	Image bg_splash;
	Image bg_main;
	
	Image btn_hover, btn_normal, txt_story, txt_free, txt_option, txt_credits, txt_exit;
	
	public void initAll(){
		try {
			String uri = "/img/background/";
			bg_splash = Image.createImage(uri+"bg_main2.jpg");
			bg_main = Image.createImage(uri+"bg_main2.jpg");
			
			btn_hover = Image.createImage(uri+"btnHover.png");
			btn_normal = Image.createImage(uri+"btnNormal.png");
			txt_story = Image.createImage(uri+"txtStory.png");
			txt_free = Image.createImage(uri+"txtFree.png");
			txt_option = Image.createImage(uri+"txtOptions.png");
			txt_credits = Image.createImage(uri+"txtCredits.png");
			txt_exit = Image.createImage(uri+"txtExit.png");
			
		} catch (IOException e) {
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
	
	public void drawMainMenu(Graphics g, int menu){
		
		for(int i = 0; i < 5; i++){
			if(menu == i)
				g.drawImage(btn_hover, 240/2, 130 + (i*38), Graphics.HCENTER | Graphics.VCENTER);
			else
				g.drawImage(btn_normal, 240/2, 130 + (i*38), Graphics.HCENTER | Graphics.VCENTER);			
		}
			
		g.drawImage(txt_story, 240/2, 130, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(txt_free, 240/2, 168, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(txt_option, 240/2, 206, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(txt_credits, 240/2, 244, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(txt_exit, 240/2, 281, Graphics.HCENTER | Graphics.VCENTER);
	}
}