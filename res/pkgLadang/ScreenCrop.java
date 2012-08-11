package pkgLadang;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

public class ScreenCrop extends StateManager{
	int menu = 0;
	String choice = "";
	StateManager state;
	
	public ScreenCrop(StateManager state){
		this.state = state;
	}
	
	public void getInput(int keyState, Crop crop, int posi){
		if((keyState & GameCanvas.FIRE_PRESSED)!= 0){
			if(!state.fireButtonHold){
				action(crop, posi);
				state.fireButtonHold = true;
			}
		}
		else 
			state.fireButtonHold = false;
		
		if(keyState == GameCanvas.DOWN_PRESSED){
			if(!state.downButtonHold){
				menu++;
				if(menu == 6)
					menu = 0;
				state.downButtonHold = true;
			}
		} else
			state.downButtonHold = false;
		
		if(keyState == GameCanvas.UP_PRESSED){
			if(!state.upButtonHold){
				menu--;
				if(menu == -1)
					menu = 5;
				state.upButtonHold = true;
			}
		} else
			state.upButtonHold = false;
	}
	
	private void action(Crop crop, int posi){
		crop.setCrop(choice);
		crop.plant(2,posi);
		crop.active = 1;
		
		state.setState(SCREEN_IN_GAME);
	}
	
	public void draw(TextureManager texture, Graphics g){
		String [] menus = {"Tomat","Kubis","Jagung","Wortel","Lobak","Kentang"};
		String [] fileName = {"tomato","cabbage","corn","carrot","turnip","potato"};
		String choices = "";
		int increment = 20;
		
		g.drawImage(texture.getTexture("imgPopupLong"), 240/2+increment, 320/2+increment*2, Graphics.HCENTER | Graphics.VCENTER);
		for(int i = 0; i < menus.length; i++){
			if(i == menu){
				g.drawString(menus[i], 95 + increment, (135+(i*15)+ increment),0);
				choices = fileName[i];
			}
			else
				g.drawString(menus[i], 90 + increment, (135+(i*15)+ increment),0);
		}
		this.choice = choices;
	}
}