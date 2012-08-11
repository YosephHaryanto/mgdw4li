package pkgLadang;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

public class ScreenMain extends StateManager{
	int menu = 0;
	boolean exit = false;
	StateManager state;
	
	public ScreenMain(StateManager state){
		this.state = state;
	}
	
	public boolean getInput(int keyState){
		if((keyState & GameCanvas.FIRE_PRESSED)!= 0){
			if(!state.fireButtonHold){
				action();
				state.fireButtonHold = true;
			}
		}
		else 
			state.fireButtonHold = false;
		
		if(keyState == GameCanvas.DOWN_PRESSED){
			if(!state.downButtonHold){
				menu++;
				if(menu == 5)
					menu = 0;
				state.downButtonHold = true;
			}
		} else
			state.downButtonHold = false;
		
		if(keyState == GameCanvas.UP_PRESSED){
			if(!state.upButtonHold){
				menu--;
				if(menu == -1)
					menu = 4;
				state.upButtonHold = true;
			}
		} else
			state.upButtonHold = false;
		
		return exit;
	}
	
	private void action(){
		switch(menu){
		case 0:
			state.setState(SCREEN_IN_GAME);
		break;
		case 1:
			state.setState(SCREEN_SPLASH);
		break;
		case 4:
			exit = true;
			break;
		default:
			state.setState(SCREEN_MAIN_MENU);
		}
	}
	
	public void draw(TextureManager texture, Graphics g){
		for(int i = 0; i < 5; i++){
			if(menu == i)
				g.drawImage(texture.getTexture("btnHover"), 240/2, 130 + (i*38), Graphics.HCENTER | Graphics.VCENTER);
			else
				g.drawImage(texture.getTexture("btnNormal"), 240/2, 130 + (i*38), Graphics.HCENTER | Graphics.VCENTER);			
		}
			
		g.drawImage(texture.getTexture("txtStory"), 240/2, 130, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(texture.getTexture("txtFree"), 240/2, 168, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(texture.getTexture("txtOptions"), 240/2, 206, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(texture.getTexture("txtCredits"), 240/2, 244, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(texture.getTexture("txtExit"), 240/2, 281, Graphics.HCENTER | Graphics.VCENTER);
	}
}