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

	final int SCREEN_SPLASH = 0;
	final int SCREEN_MAIN_MENU = 1;
	final int SCREEN_IN_GAME = 2;
	final int SCREEN_CHOOSE_ACTION = 3;
	final int SCREEN_CHOOSE_CROP = 4;
	
	int currCropMenu = 0;
	int sleepTime;
	int posi;
	int farmOpened = 0;
	
	String cropChoice = "";
	
	
	//Character
	MainChar joko = new MainChar("/img/sprite/spriteboy2.png");
	
	Enemy foeUp[];
	Enemy foeLeft[];
	Enemy foeDown[];
	Enemy foeRight[];
	
	MusicManager musicManager;
	Background bg;
	FarmField farm;
	Crop [] crop;
	Day hari;
	TextureManager textureManager;
	StateManager state;
	EnemyManager enemy;
	
	
	//SCREEN
	ScreenSplash splash;
	ScreenMain mainMenu;
	ScreenAction actionMenu;
	ScreenCrop cropMenu;
	
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
		behind = 0;
		
		init();
		while(!gameOver){
			beginTime = System.currentTimeMillis();
			framesSkipped = 0;
			clearScreen();
			update();
			draw();
			
			
			timeDiff = System.currentTimeMillis() - beginTime;
			sleepTime = (int) (SKIP_TICKS - timeDiff);
			
				if(sleepTime > 0){
					try{
						Thread.sleep(sleepTime);
					}
					catch (InterruptedException e){
						e.printStackTrace();
					}
				}
				
				while(sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS){
					update();
					sleepTime += SKIP_TICKS;
					framesSkipped++;
					behind++;
					System.out.println("Frame Skipped " + behind++);
				}
			flushGraphics();
		}
	}
	
	private void init(){
		
		//Character
		joko = new MainChar("/img/sprite/spriteboy2.png");
		
		//Enemy
		foeUp = new Enemy[10];
		foeLeft = new Enemy[10];
		foeDown= new Enemy[10];
		foeRight = new Enemy[10];
		enemy = new EnemyManager();
		
		bg = new Background();
		farm = new FarmField();
		crop = new Crop[16];
		hari = new Day();
		textureManager = new TextureManager();
		state = new StateManager();
		
		//SCREEN
		splash = new ScreenSplash(state);
		mainMenu = new ScreenMain(state);
		actionMenu = new ScreenAction(state);
		cropMenu = new ScreenCrop(state);
		
		gameOver = false;
		bg.initAll();
		farm.init();
		joko.init();
		initCrop();
		
		textureManager.insertTexture("/img/sprite/enWorm.png", "Worm");
		textureManager.insertTexture("/img/sprite/enRat.png", "Rat");
		textureManager.insertTexture("/img/tile/tomato.png", "tomato");
		textureManager.insertTexture("/img/tile/cabbage.png", "cabbage");
		textureManager.insertTexture("/img/tile/corn.png", "corn");
		textureManager.insertTexture("/img/tile/carrot.png", "carrot");
		textureManager.insertTexture("/img/tile/potato.png", "potato");
		textureManager.insertTexture("/img/tile/turnip.png", "turnip");
		textureManager.insertTexture("/img/background/bg_main2.jpg", "bgMain");
		textureManager.insertTexture("/img/background/bg_main2.jpg", "bgSplash");
		textureManager.insertTexture("/img/background/btnHover.png", "btnHover");
		textureManager.insertTexture("/img/background/btnNormal.png", "btnNormal");
		textureManager.insertTexture("/img/background/txtStory.png", "txtStory");
		textureManager.insertTexture("/img/background/txtFree.png", "txtFree");
		textureManager.insertTexture("/img/background/txtOptions.png", "txtOptions");
		textureManager.insertTexture("/img/background/txtCredits.png", "txtCredits");
		textureManager.insertTexture("/img/background/txtExit.png", "txtExit");
		textureManager.insertTexture("/img/background/imgPopup.jpg", "imgPopup");
		textureManager.insertTexture("/img/background/imgPopupLong.jpg", "imgPopupLong");
				
		//Enemy initialization
		initEnemy();
		System.out.println("Init finished");
	}
	
	private void update(){
		getInput();
		
		switch (state.getState()){
		case (SCREEN_CHOOSE_ACTION):
		case (SCREEN_CHOOSE_CROP): //Hapus comment kalo enemy tetep jalan pas masang taneman
		case (SCREEN_IN_GAME):
			if(!hari.stateTanam){
				if(updateEn(foeUp) &
				   updateEn(foeDown) &
				   updateEn(foeLeft) &
				   updateEn(foeRight)){
					
						if(hari.date <= 30)hari.nextDay();
						farmOpened = (hari.date-1) / 10;
						initEnemy();
				}
			}
		if (musicManager == null){
			if(!hari.stateTanam){
				musicManager = new MusicManager();
				musicManager.play();
				musicManager.playing = true;
			}
		}
		else {
			if(hari.stateTanam && musicManager.playing){
				musicManager.stop();
				musicManager.playing = false;
			}
			else if(!hari.stateTanam && !musicManager.playing){
				musicManager.play();
				musicManager.playing = true;
			}
			
		}
			updateTime();
			break;
		}
	}
	
	private boolean updateEn(Enemy [] en){
		boolean allDead = true;
		if(en[0] == null) 
			return true;
		
		for(int i = 0; i < en.length; i++){
			en[i].update(joko.spr, crop);
			if(en[i].alive) allDead = false; 
		}
		return allDead;
	}
	
	private void updateTime(){
		if(hari.firstCycle)
			hari.startTime();
		if(hari.timeRunning && hari.isTick()){
			hari.nextHour();
		}
	}

	private void initEnemy(){
		String [] enemyArr = enemy.getEnemyList(hari.date);
		int pack = (hari.date-1) / 10;
		
		switch(pack){
		case 3:
			for (int i = 0; i < foeLeft.length; i++) {
				foeLeft[i] = new Enemy(textureManager, enemyArr[i], (i + 1),2,"LEFT");
				foeLeft[i].initChar();
			}
		case 2:
			for (int i = 0; i < foeUp.length; i++) {
				foeUp[i] = new Enemy(textureManager, enemyArr[i], (i + 1),2,"UP");
				foeUp[i].initChar();
			}
		case 1:
			for (int i = 0; i < foeDown.length; i++) {
				foeDown[i] = new Enemy(textureManager, enemyArr[i], (i + 1),2,"DOWN");
				foeDown[i].initChar();
			}
		case 0:
			for (int i = 0; i < foeRight.length; i++) {
				foeRight[i] = new Enemy(textureManager, enemyArr[i], (i + 1),2,"RIGHT");
				foeRight[i].initChar();
			}
			break;
		}
	}
	
	private void initCrop(){
		for(int i = 0; i < crop.length; i++){
			crop[i] = new Crop(textureManager);
		}
	}
	
	private void getInput(){
		int keyState = getKeyStates();
		
		switch(state.getState()){
		
		case SCREEN_SPLASH:
			splash.getInput(keyState);
			break;
		case SCREEN_MAIN_MENU:
			if(mainMenu.getInput(keyState)) midlet.notifyDestroyed();
			break;
		case SCREEN_IN_GAME:
			joko.input(keyState, state);
			break;
		case SCREEN_CHOOSE_ACTION:
			posi = joko.plantPos;
			actionMenu.getInput(keyState,crop[posi-1]);
			break;
		case SCREEN_CHOOSE_CROP:
			cropMenu.getInput(keyState, crop[posi-1], posi);
			break;
		}
	}
	
	private void draw(){
		int tempState = state.getState();
		g.drawImage(bg.changeBackground(tempState), 0, 0, 0 );
		
		switch(tempState){
		case SCREEN_SPLASH:
			break;
		case SCREEN_MAIN_MENU:
			mainMenu.draw(textureManager, g);
			break;
		case SCREEN_IN_GAME:
			drawBasic(false);
			break;
		case SCREEN_CHOOSE_ACTION:
			drawBasic(true);
			break;
		case SCREEN_CHOOSE_CROP:
			drawBasic(true);
			cropMenu.draw(textureManager, g);
			break;
		}
	}
	
	private void drawBasic(boolean secondLevel){//fix
		farm.drawMap(g);
		
		//0 = load 1 field
		//1 = load 2 field
		//2 = load 3 field
		//3 = load 4 field
		farm.openField(farmOpened);
		
		for(int i = 0; i < crop.length; i++){
			crop[i].draw(g);
		}
		
		for(int i = 0; i < foeUp.length; i++){
			if(foeUp[0] != null)foeUp[i].draw(g);
			if(foeRight[0] != null)foeRight[i].draw(g);
			if(foeLeft[0] != null)foeLeft[i].draw(g);
			if(foeDown[0] != null)foeDown[i].draw(g);
		}
		
		joko.draw(g);
		g.drawString("Day: " + hari.date + "  |  " + hari.getTime(), 32, 0, 0);
		
		if(secondLevel){
			posi = joko.plantPos;
			actionMenu.draw(textureManager, g, (crop[posi-1].active == 1));
		}
	}
	
	private void clearScreen(){
		g.setColor(0xc3c3c3);
		g.fillRect(0,0,getWidth(),getHeight());
	}

}