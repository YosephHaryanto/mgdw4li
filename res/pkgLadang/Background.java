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
	Image img_popup, img_popupLong;
	
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
			img_popup = Image.createImage(uri+"imgPopup.jpg");
			img_popupLong = Image.createImage(uri+"imgPopupLong.jpg");
			
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
	
	public void drawActionMenu(Graphics g, int menu, boolean already){
		String [] menus = null;
		if(already)
			menus = new String[]{"Panen","Siram","Kembali"};
		else
			menus = new String[]{"Tanam baru","blabla","Kembali"};
		
		g.drawImage(img_popup, 240/2, 320/2, Graphics.HCENTER | Graphics.VCENTER);
		for(int i = 0; i < menus.length; i++){
			if(i == menu){
				g.drawString(menus[i], 95, (135+(i*15)),0);
			}
			else
				g.drawString(menus[i], 90, (135+(i*15)),0);
		}
	}
	
	public String drawCropMenu(Graphics g, int menu){
		String [] menus = {"Tomat","Kubis","Jagung","Wortel","Lobak","Kentang"};
		String [] fileName = {"tomato","cabbage","corn","carrot","turnip","potato"};
		String choice = "";
		int increment = 20;
		
		g.drawImage(img_popupLong, 240/2+increment, 320/2+increment*2, Graphics.HCENTER | Graphics.VCENTER);
		for(int i = 0; i < menus.length; i++){
			if(i == menu){
				g.drawString(menus[i], 95 + increment, (135+(i*15)+ increment),0);
				choice = fileName[i];
			}
			else
				g.drawString(menus[i], 90 + increment, (135+(i*15)+ increment),0);
		}
		return choice;
	}
}