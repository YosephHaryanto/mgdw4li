package pkgLadang;

import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

public class CvsMain extends GameCanvas implements Runnable {
	
	private MIDlet midlet;
	
	public Graphics g;
	Thread runner;
	public final int FRAMES_PER_SECOND = 25; //ganti ini untuk ubah FPSnya
    public final int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;
    public final int MAX_FRAME_SKIPS = 5;
    int behind;

	boolean gameOver = false;
	boolean leftButtonHold, rightButtonHold, upButtonHold, downButtonHold, fireButtonHold = false;
	boolean enLaunched = false;
	
	int screenState;
	final int SCREEN_SPLASH = 0;
	final int SCREEN_MAIN_MENU = 1;
	final int SCREEN_IN_GAME = 2;
	
	int currMainMenu = 0;
	int sleepTime;
	
	
	//Character
	MainChar joko = new MainChar("/img/sprite/spriteboy2.png");
	
	//Enemy (1 wave)
	//Enemy w1s1 = new Enemy("/img/sprite/enWorm.png", "Worm", 1, 2);
	
	Enemy foeUp[] = new Enemy[100];
	Enemy foeLeft[] = new Enemy[10];
	Enemy foeDown[] = new Enemy[10];
	Enemy foeRight[] = new Enemy[10];
	
	//Background
	Background bg = new Background();
	
	//Farm Field
	FarmField farm = new FarmField();
	
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
		
		long beginTime;     // the time when the cycle begun
	    long timeDiff;      // the time it took for the cycle to execute
	    // ms to sleep (<0 if we're behind)
		int framesSkipped;  // number of frames being skipped
				
		init();
		behind = 0;
		while(!gameOver){
			beginTime = System.currentTimeMillis();
			framesSkipped = 0;
			clearScreen();
			update();
			draw();
			
			
			timeDiff = System.currentTimeMillis() - beginTime;
			sleepTime = (int) (SKIP_TICKS - timeDiff);
			
				//Kalo total waktu update+draw dibawah 40ms, OK. 
				if(sleepTime > 0){
					try{
						Thread.sleep(sleepTime);
					}
					catch (InterruptedException e){
						e.printStackTrace();
					}
				}
				
				//Kalo total waktu update+draw diatas 40ms (telat), update game tanpa
				//draw
				
				while(sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS){
					update();
					sleepTime += SKIP_TICKS;
					framesSkipped++;
					behind++;
					System.out.println("Shit, we are behind! " + behind++);
				}
			flushGraphics();
		}
	}
	
	private void init(){
		gameOver = false;
		screenState = SCREEN_SPLASH;
		
		bg.initAll();
		joko.initChar();
		farm.init();
		
		//Enemy initialization
		initEnemy("UP");
		System.out.println("Loaded");
	}
	
	private void update(){
		getInput();
		updateEn();
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
	
	private void initEnemy(String which){
		for(int i = 0; i < foeUp.length; i++){
			foeUp[i] = new Enemy("/img/sprite/enWorm.png","Worm",(i+1),1);
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
			boolean idle = true;
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
					idle = false;
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
			farm.drawMap(g);
			draw(joko);
			for(int i = 0; i < foeUp.length; i++){
				draw(foeUp[i]);
			}
			try{
				g.drawString("FPS :" + (int) (1000/sleepTime), 100,0, Graphics.TOP | Graphics.LEFT);
			}
			catch(Exception exception){};
			break;
		}
	}
	
	private void clearScreen(){
		g.setColor(0xc3c3c3);
		g.fillRect(0,0,getWidth(),getHeight());
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