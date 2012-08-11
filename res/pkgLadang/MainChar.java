package pkgLadang;

import java.io.IOException;

import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class MainChar extends StateManager{

	public int[] seqDown = new int[] {0,0,0,0,1,1,1,1,2,2,2,2};
	public int[] seqLeft = new int[] {3,3,3,3,4,4,4,4,5,5,5,5};
	public int[] seqRight = new int[] {6,6,6,6,7,7,7,7,8,8,8,8};
	public int[] seqUp = new int[] {9,9,9,9,10,10,10,10,11,11,11,11};
	public String uri;
	public Image img;
	public Sprite spr;
	public int x,y;
	public int speed = 2;
	public int height = 320, width = 240;
	public int plantPos = 99;
	boolean lastDown, lastRight, lastLeft, lastUp = false;
	
	final int SCREEN_SPLASH = 0;
	final int SCREEN_MAIN_MENU = 1;
	final int SCREEN_IN_GAME = 2;
	final int SCREEN_CHOOSE_ACTION = 3;
	final int SCREEN_CHOOSE_CROP = 4;
	

	public MainChar(String newUri){
        uri= newUri;
    }

	public void init(){
		try{
			img = Image.createImage(uri);
			spr = new Sprite(img, 32, 32);
			spr.setFrameSequence(seqDown);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	//	spr.defineReferencePixel(img.getWidth()/2, img.getHeight()/2);
		x = (width/2) - (spr.getWidth()/2);
		y = (height/2) - (spr.getHeight());
		spr.setFrame(0); 
	}
	
	public void input(int keyStates, StateManager state){
		boolean idle = true;
			if(keyStates == GameCanvas.FIRE_PRESSED){
				if(!fireButtonHold){
					plantPos = determinePos();
					if(plantPos != 99)
						state.setState(SCREEN_CHOOSE_ACTION);
					fireButtonHold = true;
				}
			}
			else {
				fireButtonHold = false;
			}
			
			if(keyStates == GameCanvas.DOWN_PRESSED){
				if(!downButtonHold){
					move(0);
					idle = false;
				}
			} else {
				downButtonHold = false;
			}
			
			if(keyStates == GameCanvas.LEFT_PRESSED){
				if(!leftButtonHold){
					move(1);
					idle = false;
				}
			} else {
				leftButtonHold = false;
			}
			
			if(keyStates == GameCanvas.UP_PRESSED){
				if(!upButtonHold){
					move(2);
					idle = false;
				}
			} else {
				upButtonHold = false;
			}
			
			if(keyStates == GameCanvas.RIGHT_PRESSED){
				if(!rightButtonHold){
					move(3);
					idle = false;
				}
			} else {
				rightButtonHold = false;
			}
			if (idle)
				move(4);
	}

	public void draw(Graphics g){
		spr.setPosition(x, y);
		spr.paint(g);
	}
	
	private void move(int direction){

		/* 0 = down
		 * 1 = left
		 * 2 = up
		 * 3 = right
		 */

		switch(direction){
		case 0:
			if((y + (spr.getHeight())) < height){
				y += speed;
			}
			if(!lastDown){
				spr.setFrameSequence(seqDown);
				setFalseDirection();
				lastDown = true;
			}
			spr.nextFrame();
			break;

		case 1:
			if(x > 0)
				x -= speed;
			if(!lastLeft){
				spr.setFrameSequence(seqLeft);
				setFalseDirection();
				lastLeft = true;
			}
			spr.nextFrame();
			break;

		case 2:
			if(y > 0)
				y -= speed;
			if(!lastUp){
				spr.setFrameSequence(seqUp);
				setFalseDirection();
				lastUp = true;
			}
			spr.nextFrame();
			break;

		case 3:
			if(x + (spr.getWidth()) < width)
				x += speed;
			if(!lastRight){
				spr.setFrameSequence(seqRight);
				setFalseDirection();
				lastRight = true;
			}
			spr.nextFrame();
			break;
		case 4:
			spr.setFrame(4);
			break;
		}
	}

	private void setFalseDirection(){
		lastDown = false;
		lastRight = false;
		lastLeft = false;
		lastUp = false;
	}

	private int determinePos(){

		int pos = 99;
		//offset variable untuk menentukan berapa perbedaan yang ditimbulkan karena arah karakter
		int offsetX=0,offsetY=0;
		String posNow = "";
		int [] finalPos = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
		int [] coordPos = {12,22,13,23,52,62,53,63,16,26,17,27,56,66,57,67};

		//tentuin arah karakter pake last variable
		if (lastDown){
			offsetX=16;
			offsetY = 32;
		}
		else
		if (lastUp){
			offsetX = 16;
			offsetY = 0;
		}
		else
		if (lastLeft){
			offsetX = 0;
			offsetY = 16;
		}
		else
		if (lastRight){
			offsetX = 32;
			offsetY =16;
		}
		//offset ditambahin ke posNow
		posNow += String.valueOf((x+offsetX)/32);
		posNow += String.valueOf((y+offsetY)/32);
		for(int i = 1; i < 17; i++){
			if(Integer.parseInt(posNow) == coordPos[i-1]){
				pos = finalPos[i-1];
			}
		}	
		return pos;
	}
}