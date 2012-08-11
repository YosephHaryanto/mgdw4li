package pkgLadang;

import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Enemy{
	
	public int[] seqDown ;
	public int[] seqLeft ;
	public int[] seqRight ;
	public int[] seqUp;
	public String uri, type;
	public Image img;
	public Sprite spr;
	public int x,y,speed,position;
	public int attack, health;
	public int height = 300, width = 240;
	public boolean lastUp, lastLeft, lastRight, lastDown = false;
	public boolean moving = true;
	public boolean alive = true;
	public int currentDir = 0;
	public String origin;
	TextureManager textureManager;
	
	
	public Enemy(TextureManager textureManager, String type, int pos, int initSpeed, String origins){
        this.textureManager = textureManager;
        this.position = pos;
        this.speed = initSpeed;
        this.origin = origins;
        this.type = type;
    }
	
	public void initChar(){
		if (type.equals("Worm")){
			img = textureManager.getTexture(type);
			spr = new Sprite(img, 30, 30);
			attack = 1;
			health = 3;
			seqDown = new int[] {0,0,0,0,1,1,1,1,2,2,2,2,3,3,3,3};
			seqLeft = new int[] {4,4,4,4,5,5,5,5,6,6,6,6,7,7,7,7};
			seqRight = new int[] {8,8,8,8,9,9,9,9,10,10,10,10,11,11,11,11};
			seqUp = new int[] {12,12,12,12,13,13,13,13,14,14,14,14,15,15,15,15};
		}
		
		else if(type.equals("Rat")){
			img = textureManager.getTexture(type);
			spr = new Sprite(img, 32, 32);
			attack = 2;
			health = 5;
			seqDown = new int[] {0,0,0,1,1,2,2,2};
			seqLeft = new int[] {3,3,3,4,4,4,5,5,5};
			seqRight = new int[] {6,6,6,7,7,7,8,8,8};
			seqUp = new int[] {9,9,9,10,10,10,11,11,11};
		}
		
		
		this.setinitPos();
		this.spr.setFrame(0);
		this.moving = true;
		this.alive = true;
	}
	
	private void setAllFalse(){
		lastDown = false;
		lastLeft = false;
		lastRight = false;
		lastUp = false;
	}
	
	private void moveDir(String dir){
		if(dir.equals("LEFT")){
			if(!lastLeft){
				spr.setFrameSequence(seqLeft);
				setAllFalse();
				lastLeft = true;
			}
			x -= speed;
		}
		else if(dir.equals("RIGHT")){
			if(!lastRight){
				spr.setFrameSequence(seqRight);
				setAllFalse();
				lastRight = true;
			}
			x += this.speed;
		}
		else if(dir.equals("UP")){
			if(!lastUp){
				spr.setFrameSequence(seqUp);
				setAllFalse();
				lastUp = true;
			}
			y -= this.speed;
		}
		else if(dir.equals("DOWN")){
			if(!lastDown){
				spr.setFrameSequence(seqDown);
				setAllFalse();
				lastDown = true;
			}
			y += speed;
		}
	}

	
	public void setinitPos(){
		
		if (origin.equals("UP")) {
			x = width / 2;
			y = -1 * position * (spr.getHeight());
			spr.setFrameSequence(seqDown);
		}
		else if (origin.equals("DOWN")) {
			x = width / 2 - spr.getWidth();
			y = height + (position * (spr.getHeight()));
			spr.setFrameSequence(seqUp);
		}
		else if (origin.equals("LEFT")) {
			x = -1 * position * (spr.getWidth());
			y = height / 2 - spr.getWidth();
			spr.setFrameSequence(seqLeft);
		}
		else if (origin.equals("RIGHT")) {
			x = width + (position * (spr.getWidth()));
			y = height / 2;
			spr.setFrameSequence(seqUp);
		}
		
		currentDir = 0;
		
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
				if(this.x < (0 - this.spr.getWidth())) resetEnemy();//setinitPos(); //this.moving = false (to just 1 wave)
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
				if(this.x > (width + this.spr.getWidth())) resetEnemy();
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
				if(this.y > (height + this.spr.getHeight())) resetEnemy();
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
				if(this.y < ( 0 - this.spr.getHeight())) resetEnemy();
				break;
			}
		}
	}
	
	public void move(){
		if(moving){
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
						crop[i].health -= attack;
						return true; //object is arrived in the crop
					}
				}
				catch (Exception e){};
			}
			return false;
		}
		return true;
	}
	
	private void resetEnemy(){
		setinitPos();
		spr.setFrame(0);
		moving = false;
		alive = false;
     }
	
	public void update (Sprite spr, Crop [] crop){			
			if (!spr.collidesWith(this.spr, true)) {
				move();
				if(isOut(crop))
					resetEnemy();
			}
	}
	
	public void draw(Graphics g){
		spr.setPosition(x, y);
		spr.paint(g);
		
	}

}