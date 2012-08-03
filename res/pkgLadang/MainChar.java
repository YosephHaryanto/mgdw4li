package pkgLadang;

import java.io.IOException;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Image;

public class MainChar{

	//private GameCanvas gc;
	public int[] seqDown = new int[] {0,0,0,0,1,1,1,1,2,2,2,2};
	public int[] seqLeft = new int[] {3,3,3,3,4,4,4,4,5,5,5,5};
	public int[] seqRight = new int[] {6,6,6,6,7,7,7,7,8,8,8,8};
	public int[] seqUp = new int[] {9,9,9,9,10,10,10,10,11,11,11,11};
	public String uri;
	public Image img;
	public Sprite spr;
	public int x,y;
	public int height = 320, width = 240;
	boolean lastDown, lastRight, lastLeft, lastUp = false;
	public int speed = 2;

	public MainChar(String newUri){
        this.uri= newUri;
    }

	public void initChar(){
		try{
			//runImg = Image.createImage("/img/sprite/spriteboy.png");
			this.img = Image.createImage(this.uri);
			this.spr = new Sprite(this.img, 32, 32);
			this.spr.setFrameSequence(seqDown);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	//	spr.defineReferencePixel(img.getWidth()/2, img.getHeight()/2);
		this.x = (width/2) - (this.spr.getWidth()/2);
		this.y = (height/2) - (this.spr.getHeight());
		this.spr.setFrame(0); 
	}

	public void move(int direction){

		/* 0 = down
		 * 1 = left
		 * 2 = up
		 * 3 = right
		 */

		switch(direction){
		case 0:
			if((this.y + (this.spr.getHeight())) < height){
				this.y += this.speed;
			}
			if(!lastDown){
				this.spr.setFrameSequence(seqDown);
				setFalseDirection();
				lastDown = true;
			}
			this.spr.nextFrame();
			break;

		case 1:
			if(this.x > 0)
				this.x -= this.speed;
			if(!lastLeft){
				this.spr.setFrameSequence(seqLeft);
				setFalseDirection();
				lastLeft = true;
			}
			this.spr.nextFrame();
			break;

		case 2:
			if(this.y > 0)
				this.y -= this.speed;
			if(!lastUp){
				this.spr.setFrameSequence(seqUp);
				setFalseDirection();
				lastUp = true;
			}
			this.spr.nextFrame();
			break;

		case 3:
			if(this.x + (this.spr.getWidth()) < width)
				this.x += this.speed;
			if(!lastRight){
				this.spr.setFrameSequence(seqRight);
				setFalseDirection();
				lastRight = true;
			}
			this.spr.nextFrame();
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

	public int determinePos(){

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
		posNow += String.valueOf((this.x+offsetX)/32);
		posNow += String.valueOf((this.y+offsetY)/32);
		for(int i = 1; i < 17; i++){
			if(Integer.parseInt(posNow) == coordPos[i-1]){
				pos = finalPos[i-1];
			}
		}	
		return pos;
	}
}