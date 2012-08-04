package pkgLadang;

import java.io.IOException;
import javax.microedition.lcdui.Image;

public class Crop{
	
	public int health;
	public int seedPrice;
	public int cropPrice;
	public int position;
	public int x, y;
	public int active = 0;
	public boolean first = true;
	Image current, tomato, cabbage, corn, carrot, potato, turnip;	
	TextureManager textureManager;
	Crop(TextureManager textureManager){
		this.textureManager = textureManager;
	}
	
	
	public void setCrop(String cropType){
		this.current = textureManager.getTexture(cropType);
	}
	
	public Crop(){
		if(this.first){
			this.health = 3;
			this.first = false;
		}
	}
	
	public void destroy(){
		this.health = 3;
		this.first = false;
		this.x = -30;
		this.y = -30;
		this.active = 0;
	}
	
	public void plant(int state, int pos){
		int posX = 0, posY = 0;
		switch(pos){
		case 1:
			posX = 32;
			posY = 64;
			break;
		case 2:
			posX = 64;
			posY = 64;
			break;
		case 3:
			posX = 32;
			posY = 96;
			break;
		case 4:
			posX = 64;
			posY = 96;
			break;
		case 5:
			posX = 160;
			posY = 64;
			break;
		case 6:
			posX = 192;
			posY = 64;
			break;
		case 7:
			posX = 160;
			posY = 96;
			break;
		case 8:
			posX = 192;
			posY = 96;
			break;
		case 9:
			posX = 32;
			posY = 192;
			break;
		case 10:
			posX = 64;
			posY = 192;
			break;
		case 11:
			posX = 32;
			posY = 224;
			break;
		case 12:
			posX = 64;
			posY = 224;
			break;
		case 13:
			posX = 160;
			posY = 192;
			break;
		case 14:
			posX = 192;
			posY = 192;
			break;
		case 15:
			posX = 160;
			posY = 224;
			break;
		case 16:
			posX = 192;
			posY = 224;
			break;
		}
		
		this.x = posX;
		this.y = posY;
	}
}


/* index dari farm field tempat nanem
 * 1  2    5  6
 * 3  4    7  8
 * 
 * 9  10   13 14
 * 11 12   15 16
 */