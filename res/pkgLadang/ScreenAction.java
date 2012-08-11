package pkgLadang;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

public class ScreenAction extends StateManager{
	int menu = 0;
	StateManager state;
	
	public ScreenAction(StateManager state){
		this.state = state;
	}
	
	public void getInput(int keyState, Crop crop){
		if((keyState & GameCanvas.FIRE_PRESSED)!= 0){
			if(!state.fireButtonHold){
				action(crop);
				menu = 0;
				state.fireButtonHold = true;
			}
		}
		else 
			state.fireButtonHold = false;
		
		if(keyState == GameCanvas.RIGHT_PRESSED){
			if(!state.rightButtonHold){
				menu++;
				if(menu == 3)
					menu = 0;
				state.rightButtonHold = true;
			}
		} else
			state.rightButtonHold = false;
		
		if(keyState == GameCanvas.LEFT_PRESSED){
			if(!state.leftButtonHold){
				menu--;
				if(menu == -1)
					menu = 2;
				state.leftButtonHold = true;
			}
		} else
			state.leftButtonHold = false;
	}
	
	private void action(Crop crop){
		switch(menu){
		case 0:
			if(crop.active == 0)
				state.setState(SCREEN_CHOOSE_CROP);
			else{
				state.setState(SCREEN_IN_GAME);
				crop.destroy();
			}
			break;
		case 1:
		break;
		case 2:
			state.setState(SCREEN_IN_GAME);
			break;
		default:
			state.setState(SCREEN_MAIN_MENU);
		}
	}
	
	public void draw(TextureManager texture, Graphics g, boolean already){
		String [] menus = null;
		
		if(already)
			menus = new String[]{"Panen","Siram","Kembali"};
		else
			menus = new String[]{"Tanam","Siram","Kembali"};
		
		g.drawImage(texture.getTexture("imgPopup"), 240/2, 320/2, Graphics.HCENTER | Graphics.VCENTER);
		for(int i = 0; i < menus.length; i++){
			if(i == menu)
				g.drawString(menus[i], 100, 145, 0);
			g.drawString("<<", 80, 145, 0);
			g.drawString(">>", 160, 145, 0);
		}
	}
}