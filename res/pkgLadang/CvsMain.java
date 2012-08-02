package pkgLadang;

import java.io.IOException;

import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class CvsMain extends GameCanvas implements Runnable {
	
	public Graphics g;
	Thread runner;
	public static int SLEEP_TIME = 30;
	
	boolean gameOver = false;
	boolean leftButtonHold, rightButtonHold, upButtonHold, downButtonHold, fireButtonHold = false;
	boolean enReady = true, enLaunched = false;
	
	int screenState;
	final int SCREEN_SPLASH = 0;
	final int SCREEN_MAIN_MENU = 1;
	final int SCREEN_IN_GAME = 2;
	
	
	//Character
	MainChar joko = new MainChar("/img/sprite/spriteboy2.png");
	
	//Enemy (1 wave)
	Enemy w1s1 = new Enemy("/img/sprite/enWorm.png", "Worm", 1, 2);
	
	//Background
	Background bg = new Background();

	//for map generation
	Image land,landCorner,landSide,landGreen,landWet;
	
	Image tomato;

	/*code for map
	First Digit         Second Digit (rotation clockwise)
	0 : land			0 : no rotation
	1 : land corner		1 : 90
	2 : land side		2 : 180
	3 : green			3 : 270
	4 : land wet
	*/
	int [][] mapCode =  {
					{30,30,30,30},
					{10,21,21,11},
					{20,0,0,22},
					{20,0,0,22},
					{13,23,23,12},
					{10,21,21,11},
					{20,40,40,22},
					{20,40,40,22},
					{13,23,23,12},
					{30,30,30,30}};
	
	
	protected CvsMain(){
		super(false);
		this.setFullScreenMode(true);
		g = getGraphics();
		runner = new Thread(this);
	}
	
	public void mulai(){
		runner.start();
	}
	public void run() {
		init();
		initMap();
		while(!gameOver){
			clearScreen();
			getInput();
			updateEn();
			draw();
			flushGraphics();
			try{
				
				Thread.sleep(SLEEP_TIME);
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	

	private void updateEn(){
		switch (screenState){
		case (SCREEN_IN_GAME):
			
			if (joko.spr.collidesWith(w1s1.spr, true) == false) {
				if (w1s1.move(enLaunched, enReady)) {
					enReady = true;
					enLaunched = false;
				} else
					enReady = false;
			}
		}
	}
	
	private void init(){
		gameOver = false;
		screenState = SCREEN_SPLASH;
		
		bg.initAll();
		joko.initChar();
		
		//Enemy
		w1s1.initChar();
		System.out.println("Loaded");
	}
		
	private void initMap(){
		try {
			land = Image.createImage("/img/tile/land.png");
			landSide = Image.createImage("/img/tile/land side.png");
			landGreen = Image.createImage("/img/tile/green.png");
			landCorner = Image.createImage("/img/tile/land corner.png");
			landWet = Image.createImage("/img/tile/land wet.png");
			tomato = Image.createImage("/img/tile/tomato.png");
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void getInput(){
		int keyState = getKeyStates();
		switch(screenState){
		
		case SCREEN_SPLASH:
			if((keyState & FIRE_PRESSED)!= 0){
				if(!fireButtonHold){
					screenState = SCREEN_MAIN_MENU;
					fireButtonHold = true;
				}
			}
			else 
				fireButtonHold = false;
			break;
			
		case SCREEN_MAIN_MENU:
			if((keyState & FIRE_PRESSED)!= 0){
				if(!fireButtonHold){
					screenState = SCREEN_IN_GAME;
					fireButtonHold = true;
				}
			}
			else 
				fireButtonHold = false;
			break;
			
		case SCREEN_IN_GAME:
			boolean idle = true;
			if(keyState == FIRE_PRESSED){
				if(!fireButtonHold){
					if(enReady){
						w1s1.y = -1 * w1s1.spr.getHeight();
						enLaunched = true;
					}
					
					fireButtonHold = true;
				}
			}
			else {
				fireButtonHold = false;
				//enLaunched = false;
			}
			
			if(keyState == DOWN_PRESSED){
				if(!downButtonHold){
					joko.move(0);
					idle = false;
				}
			} else {
				downButtonHold = false;
			}
			
			if(keyState == LEFT_PRESSED){
				if(!leftButtonHold){
					joko.move(1);
					idle = false;
				}
			} else {
				leftButtonHold = false;
			}
			
			if(keyState == UP_PRESSED){
				if(!upButtonHold){
					joko.move(2);
					idle =false;
				}
			} else {
				upButtonHold = false;
			}
			
			if(keyState == RIGHT_PRESSED){
				if(!rightButtonHold){
					joko.move(3);
					idle = false;
				}
			} else {
				rightButtonHold = false;
			}
			if (idle)
				joko.move(4);
			break;
		}
	}
	
	private void draw(){
		g.drawImage(bg.changeBackground(screenState), 0, 0, 0 );
		
		switch(screenState){
		case SCREEN_SPLASH:
			break;
		case SCREEN_MAIN_MENU:
			break;
		case SCREEN_IN_GAME:
			drawMap();
			draw(joko);
			draw(w1s1);
			break;
		}
	}
	
	private void clearScreen(){
		g.setColor(0xc3c3c3);
		g.fillRect(0,0,getWidth(),getHeight());
	}
		
	private void drawMap(){
		Image tempImg = null;
		int rot = 0;
		for ( int i = 0; i < 10;i++){
			for ( int j = 0; j < 4;j++){
				switch (mapCode[i][j] /10){
				case 0:
					tempImg = land;
					break;
				case 1:
					tempImg = landCorner;
					break;
				case 2:
					tempImg = landSide;
					break;
				case 3:
					tempImg = landGreen;
					break;
				case 4:
					tempImg = landWet;
					break;
				}
				switch (mapCode[i][j] % 10){
				case 0:
					rot = Sprite.TRANS_NONE;
					break;
				case 1:
					rot = Sprite.TRANS_ROT90;
					break;
				case 2:
					rot = Sprite.TRANS_ROT180;
					break;
				case 3:
					rot = Sprite.TRANS_ROT270;
					break;
				}
				g.drawRegion(tempImg, 0, 0, tempImg.getWidth() , tempImg.getHeight(), rot, j*32, i*32, 0);
				g.drawRegion(tempImg, 0, 0, tempImg.getWidth() , tempImg.getHeight(), rot, j*32 + 4*32, i*32, 0);
			}
		}
		g.drawImage(tomato, 64, 64, 0);
	}
	
	private void draw(MainChar chars){
		chars.spr.setPosition(chars.x, chars.y);
		chars.spr.paint(g);
	}
	
	private void draw(Enemy ens){
		ens.spr.setPosition(ens.x, ens.y);
		ens.spr.paint(g);
	}
}