package pkgLadang;

import java.io.IOException;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Image;

public class MainChar{
	
	//private GameCanvas gc;
	public int[] seqDown = new int[] { 0,0,0,0,1,1,1,1,2,2,2,2};
	public int[] seqLeft = new int[] {3,3,3,3,4,4,4,4,5,5,5,5};
	public int[] seqRight = new int[] {6,6,6,6,7,7,7,7,8,8,8,8};
	public int[] seqUp = new int[] {9,9,9,9,10,10,10,10,11,11,11,11};
	public String uri;
	public Image img;
	public Sprite spr;
	public int x,y;
	public int height = 300, width = 240;
	boolean lastDown, lastRight, lastLeft, lastUp = false;
	
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
		
		this.x = width/2;
		this.y = height/2;
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
				this.y += 2;
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
				this.x -= 2;
			if(!lastLeft){
				this.spr.setFrameSequence(seqLeft);
				setFalseDirection();
				lastLeft = true;
			}
			this.spr.nextFrame();
			break;
			
		case 2:
			if(this.y > 0)
				this.y -= 2;
			if(!lastUp){
				this.spr.setFrameSequence(seqUp);
				setFalseDirection();
				lastUp = true;
			}
			this.spr.nextFrame();
			break;
			
		case 3:
			if(this.x + (this.spr.getWidth()) < width)
				this.x += 2;
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
}