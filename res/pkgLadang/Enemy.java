package pkgLadang;

import java.io.IOException;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Image;

public class Enemy{
	
	//private GameCanvas gc;
	public int[] seqDown = new int[] {0,0,0,0,1,1,1,1,2,2,2,2,3,3,3,3};
	public int[] seqLeft = new int[] {4,4,4,4,5,5,5,5,6,6,6,6,7,7,7,7};
	public int[] seqRight = new int[] {8,8,8,8,9,9,9,9,10,10,10,10,11,11,11,11};
	public int[] seqUp = new int[] {12,12,12,12,13,13,13,13,14,14,14,14,15,15,15,15};
	public String uri, type;
	public Image img;
	public Sprite spr;
	public int x,y,speed,position;
	public int attack, health;
	public int height = 300, width = 240;
	public boolean lastUp, lastLeft, lastRight, lastDown = false;
	public boolean moving = true;
	public int currentDir = 0;
	public String origin;
	
	public Enemy(String newUri, String type, int pos, int initSpeed, String origins){
        this.uri= newUri;
        this.position = pos;
        this.speed = initSpeed;
        this.origin = origins;
    }
	
	public void initChar(){
		try{
			//runImg = Image.createImage("/img/sprite/spriteboy.png");
			this.img = Image.createImage(this.uri);
			this.spr = new Sprite(this.img, 30, 30);
		}
		catch(IOException e){
			e.printStackTrace();
		}

		this.setinitPos();
		this.spr.setFrame(0);
	}
	
	private void setAllFalse(){
		lastDown = false;
		lastLeft = false;
		lastRight = false;
		lastUp = false;
	}
	
	public void moveDir(String dir){
		if(dir.equals("LEFT")){
			if(!this.lastLeft){
				this.spr.setFrameSequence(seqLeft);
				this.setAllFalse();
				this.lastLeft = true;
			}
			this.x -= this.speed;
		}
		else if(dir.equals("RIGHT")){
			if(!this.lastRight){
				this.spr.setFrameSequence(seqRight);
				this.setAllFalse();
				this.lastRight = true;
			}
			this.x += this.speed;
		}
		else if(dir.equals("UP")){
			if(!this.lastUp){
				this.spr.setFrameSequence(seqUp);
				this.setAllFalse();
				this.lastUp = true;
			}
			this.y -= this.speed;
		}
		else if(dir.equals("DOWN")){
			if(!this.lastDown){
				this.spr.setFrameSequence(seqDown);
				this.setAllFalse();
				this.lastDown = true;
			}
			this.y += this.speed;
		}
	}
	
	public void setinitPos(){
		
		if (this.origin.equals("UP")) {
			this.x = width / 2;
			this.y = -1 * this.position * (this.spr.getHeight());
			this.spr.setFrameSequence(seqDown);
		}
		else if (this.origin.equals("DOWN")) {
			this.x = width / 2 - this.spr.getWidth();
			this.y = height + (this.position * (this.spr.getHeight()));
			this.spr.setFrameSequence(seqUp);
		}
		else if (this.origin.equals("LEFT")) {
			this.x = -1 * this.position * (this.spr.getWidth());
			this.y = height / 2 - this.spr.getWidth();
			this.spr.setFrameSequence(seqLeft);
		}
		else if (this.origin.equals("RIGHT")) {
			this.x = width + (this.position * (this.spr.getWidth()));
			this.y = height / 2;
			this.spr.setFrameSequence(seqUp);
		}
		
		this.currentDir = 0;
		
	}
	
	private void moveSequence(){
		if(this.origin.equals("UP")){
			switch (this.currentDir){
			case 0:
				this.moveDir("DOWN");
				if(this.y > 224) this.currentDir = 1;
				break;
			case 1:
				this.moveDir("LEFT");
				if(this.x < 64) this.currentDir = 2;
				break;
			case 2:
				this.moveDir("UP");
				if(this.y < 192) this.currentDir = 3;
				break;
			case 3:
				this.moveDir("LEFT");
				if(this.x < 32) this.currentDir = 4;
				break;
			case 4:
				this.moveDir("DOWN");
				if(this.y > 224) this.currentDir = 5;
				break;
			case 5:
				this.moveDir("LEFT");
				if(this.x < (0 - this.spr.getWidth())) this.setinitPos(); //this.moving = false (to just 1 wave)
				break;
			}
			
		} else if(this.origin.equals("DOWN")){
			switch (this.currentDir){
			case 0:
				this.moveDir("UP");
				if(this.y < 64) this.currentDir = 1;
				break;
			case 1:
				this.moveDir("RIGHT");
				if(this.x > 160) this.currentDir = 2;
				break;
			case 2:
				this.moveDir("DOWN");
				if(this.y > 96) this.currentDir = 3;
				break;
			case 3:
				this.moveDir("RIGHT");
				if(this.x > 192) this.currentDir = 4;
				break;
			case 4:
				this.moveDir("UP");
				if(this.y < 64) this.currentDir = 5;
				break;
			case 5:
				this.moveDir("RIGHT");
				if(this.x > (width + this.spr.getWidth())) this.setinitPos();;
				break;
			}
		}
		else if(this.origin.equals("LEFT")){
			switch (this.currentDir){
			case 0:
				this.moveDir("RIGHT");
				if(this.x > 192) this.currentDir = 1;
				break;
			case 1:
				this.moveDir("DOWN");
				if(this.y > 192) this.currentDir = 2;
				break;
			case 2:
				this.moveDir("LEFT");
				if(this.x < 160) this.currentDir = 3;
				break;
			case 3:
				this.moveDir("DOWN");
				if(this.y > 224) this.currentDir = 4;
				break;
			case 4:
				this.moveDir("RIGHT");
				if(this.x > 192) this.currentDir = 5;
				break;
			case 5:
				this.moveDir("DOWN");
				if(this.y > (height + this.spr.getHeight())) this.setinitPos();;
				break;
			}
		}
		else if(this.origin.equals("RIGHT")){
			switch (this.currentDir){
			case 0:
				this.moveDir("LEFT");
				if(this.x < 32) this.currentDir = 1;
				break;
			case 1:
				this.moveDir("UP");
				if(this.y < 96) this.currentDir = 2;
				break;
			case 2:
				this.moveDir("RIGHT");
				if(this.x > 64) this.currentDir = 3;
				break;
			case 3:
				this.moveDir("UP");
				if(this.y < 64) this.currentDir = 4;
				break;
			case 4:
				this.moveDir("LEFT");
				if(this.x < 32) this.currentDir = 5;
				break;
			case 5:
				this.moveDir("UP");
				if(this.y < ( 0 - this.spr.getHeight())) this.setinitPos();;
				break;
			}
		}
	}
	
	public void move(){
		if(this.moving){
			this.moveSequence();
			this.spr.nextFrame();
		}
	}
	
	public boolean isOut(Crop []crop){
		
		int cropFrom = 0;
		if(this.origin.equals("UP"))
			cropFrom = 7;
		else if(this.origin.equals("DOWN"))
			cropFrom = 3;
		else if(this.origin.equals("LEFT"))
			cropFrom = 11;
		else if(this.origin.equals("RIGHT"))
			cropFrom = -1;
		
		if(this.moving){
			for(int i = cropFrom+4; i > cropFrom; i--){
				try{
					if(this.spr.collidesWith(crop[i].current, crop[i].x, crop[i].y,true)){
						System.out.println("DANG!");
						crop[i].health--;
						return true; //object is arrived in the crop
					}
				}
				catch (Exception e){};
			}
			return false;
		}
		return true;
	}
	
	

}