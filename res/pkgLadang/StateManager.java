package pkgLadang;

public class StateManager{
	
	private int screenState;
	
	boolean leftButtonHold, rightButtonHold, upButtonHold, downButtonHold, fireButtonHold = false;
	final int SCREEN_SPLASH = 0;
	final int SCREEN_MAIN_MENU = 1;
	final int SCREEN_IN_GAME = 2;
	final int SCREEN_CHOOSE_ACTION = 3;
	final int SCREEN_CHOOSE_CROP = 4;
	
	public StateManager(){
		screenState = 0;
	}
	
	public void setState(int state){
		this.screenState = state;
	}
	
	public int getState(){
		return this.screenState;
	}
	
}