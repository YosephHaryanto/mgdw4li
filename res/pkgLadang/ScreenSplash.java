package pkgLadang;

import javax.microedition.lcdui.game.GameCanvas;

public class ScreenSplash extends StateManager{
		int currMainMenu = 0;
		boolean exit = false;
		StateManager state;
		
		public ScreenSplash(StateManager state){
			this.state = state;
		}
		
		public void getInput(int keyState){
			if((keyState & GameCanvas.FIRE_PRESSED)!= 0){
				if(!state.fireButtonHold){
					state.setState(SCREEN_MAIN_MENU);
					state.fireButtonHold = true;
				}
			}
			else 
				state.fireButtonHold = false;
		}
}