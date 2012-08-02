package pkgLadang;

import java.io.IOException;

import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

//TEST FADLI

public class CvsMain extends GameCanvas implements Runnable {
	
	private MIDlet midlet;
	
	public Graphics g;
	Thread runner;
	public static int SLEEP_TIME =1; //change it to 1 when testing in real device
	
	boolean gameOver = false;
	boolean leftButtonHold, rightButtonHold, upButtonHold, downButtonHold, fireButtonHold = false;
	boolean enLaunched = false;
	
	int screenState;
	final int SCREEN_SPLASH = 0;
	final int SCREEN_MAIN_MENU = 1;
	final int SCREEN_IN_GAME = 2;
	
	int currMainMenu = 0;
	
	//Character
	MainChar joko = new MainChar("/img/sprite/spriteboy2.png");
	
	//Enemy (1 wave)
	//Enemy w1s1 = new Enemy("/img/sprite/enWorm.png", "Worm", 1, 2);
	
	Enemy foeUp[] = new Enemy[10];
	Enemy foeLeft[] = new Enemy[10];
	Enemy foeDown[] = new Enemy[10];
	Enemy foeRight[] = new Enemy[10];
	
	
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
	
	
	protected CvsMain(MIDlet m){
		super(false);
		this.setFullScreenMode(true);
		g = getGraphics();
		runner = new Thread(this);
		midlet = m;
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
	
	private void init(){
		gameOver = false;
		screenState = SCREEN_SPLASH;
		
		bg.initAll();
		joko.initChar();
		
		//Enemy initialization
		initEnemy("UP");
		System.out.println("Loaded");
	}
	
	private void updateEn(){
		switch (screenState){
		case (SCREEN_IN_GAME):
			
			for (int i = 0; i < foeUp.length; i++) {
				if (!joko.spr.collidesWith(foeUp[i].spr, true)) {
					
					foeUp[i].move(enLaunched, 1);

					if(foeUp[foeUp.length - 1].isOut(enLaunched)){
						resetEnemy();
						enLaunched = true;
					}
					
				}
			}
		}
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
	
	private void initEnemy(String which){
		for(int i = 0; i < foeUp.length; i++){
			foeUp[i] = new Enemy("/img/sprite/enWorm.png","Worm",(i+1),3);
			foeUp[i].initChar();
		}
	}
	
	private void resetEnemy(){
		for(int i = 0; i < foeUp.length; i++){
			foeUp[i].x = getWidth()/2;
			foeUp[i].y = -1 * foeUp[i].position * (foeUp[i].spr.getHeight());
			foeUp[i].spr.setFrame(0); 
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
					goToMenu(currMainMenu);
					fireButtonHold = true;
				}
			}
			else 
				fireButtonHold = false;
			
			if(keyState == DOWN_PRESSED){
				if(!downButtonHold){
					currMainMenu++;
					if(currMainMenu == 5)
						currMainMenu = 0;
					downButtonHold = true;
				}
			} else
				downButtonHold = false;
			
			if(keyState == UP_PRESSED){
				if(!upButtonHold){
					currMainMenu--;
					if(currMainMenu == -1)
						currMainMenu = 4;
					upButtonHold = true;
				}
			} else
				upButtonHold = false;
			break;
			
		case SCREEN_IN_GAME:
			
			if(keyState == FIRE_PRESSED){
				if(!fireButtonHold){
										
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
				}
			} else {
				downButtonHold = false;
			}
			
			if(keyState == LEFT_PRESSED){
				if(!leftButtonHold){
					joko.move(1);
				}
			} else {
				leftButtonHold = false;
			}
			
			if(keyState == UP_PRESSED){
				if(!upButtonHold){
					joko.move(2);
				}
			} else {
				upButtonHold = false;
			}
			
			if(keyState == RIGHT_PRESSED){
				if(!rightButtonHold){
					joko.move(3);
				}
			} else {
				rightButtonHold = false;
			}
			break;
		}
	}
	
	private void goToMenu(int menu){
		switch(menu){
		case 0:
			screenState = SCREEN_IN_GAME;
		break;
		case 1:
			screenState = SCREEN_SPLASH;
		break;
		case 4:
			midlet.notifyDestroyed();
			break;
		default:
			screenState = SCREEN_MAIN_MENU;
		}
	}
	
	private void draw(){
		g.drawImage(bg.changeBackground(screenState), 0, 0, 0 );
		
		switch(screenState){
		case SCREEN_SPLASH:
			break;
		case SCREEN_MAIN_MENU:
			bg.drawMainMenu(g, currMainMenu);
			break;
		case SCREEN_IN_GAME:
			drawMap();
			draw(joko);
			for(int i = 0; i < foeUp.length; i++){
				draw(foeUp[i]);
			}
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