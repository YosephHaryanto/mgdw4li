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
			this.spr.setFrameSequence(this.seqDown);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		this.x = width/2;
		this.y = -1 * this.position * (this.spr.getHeight());
		this.spr.setFrame(0); 
	}
	
	public boolean move(boolean moving, boolean ready){
		if(moving){
			this.y += this.speed;
			this.spr.nextFrame();
		}
		
		if(moving){
			if(this.y > (height + this.spr.getHeight())){
				return true;
			}
			else
				return false;
		}
		
		return true;
	}
	
	

}