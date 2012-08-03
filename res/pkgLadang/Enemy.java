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
	
	public Enemy(String newUri, String type, int pos, int initSpeed){
        this.uri= newUri;
        this.position = pos;
        this.speed = initSpeed;
    }
	
	public void initChar(){
		try{
			//runImg = Image.createImage("/img/sprite/spriteboy.png");
			this.img = Image.createImage(this.uri);
			this.spr = new Sprite(this.img, 30, 30);
			this.spr.setFrameSequence(seqDown);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		this.x = width/2;
		this.y = -1 * this.position * (this.spr.getHeight());
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
	
	public void move(int direction){
		if(this.moving){
						
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
				if(this.x < (0 - this.spr.getWidth())) this.moving = false;
				break;
			}
			
			this.spr.nextFrame();
		}
	}
	
	public boolean isOut(int direction, Crop []crop){
		if(this.moving){
			for(int i = 11; i > 7; i--){
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