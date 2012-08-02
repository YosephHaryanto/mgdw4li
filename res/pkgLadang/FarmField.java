package pkgLadang;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

public class FarmField{
	
	Image land,landCorner,landSide,landGreen,landWet;
	Image tomato, cabbage;
	LayerManager manager = new LayerManager();
	
	/*code for map
	First Digit         Second Digit (rotation clockwise)
	0 : land			0 : no rotation
	1 : land corner		1 : 90
	2 : land side		2 : 180
	3 : green			3 : 270
	4 : land wet
	*/
	
//	int [][] mapCode =  {
//			{30,30,30,30},
//			{10,21,21,11},
//			{20,0,0,22},
//			{20,0,0,22},
//			{13,23,23,12},
//			{10,21,21,11},
//			{20,40,40,22},
//			{20,40,40,22},
//			{13,23,23,12},
//			{30,30,30,30}};
	
	int [][] mapCode =  {
						{30,30,30,30},
						{10,21,21,11},
						{20,00,00,22},
						{20,00,00,22},
						{13,23,23,12},
						{10,21,21,11},
						{20,00,00,22},
						{20,00,00,22},
						{13,23,23,12},
						{30,30,30,30}
						};
	
	public void init(){
		try {
			land = Image.createImage("/img/tile/land.png");
			landSide = Image.createImage("/img/tile/land side.png");
			landGreen = Image.createImage("/img/tile/green.png");
			landCorner = Image.createImage("/img/tile/land corner.png");
			landWet = Image.createImage("/img/tile/land wet.png");
			
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void drawMap(Graphics g){
		Image tempImg = null;
		int rot = 0;
		for ( int i = 0; i < 10;i++){
			for ( int j = 0; j < 4;j++){
				switch (mapCode[i][j] /10){
				case 0:
					tempImg = land;
					break;
				case 1:
					tempImg = landCorner;
					break;
				case 2:
					tempImg = landSide;
					break;
				case 3:
					tempImg = landGreen;
					break;
				case 4:
					tempImg = landWet;
					break;
				}
				switch (mapCode[i][j] % 10){
				case 0:
					rot = Sprite.TRANS_NONE;
					break;
				case 1:
					rot = Sprite.TRANS_ROT90;
					break;
				case 2:
					rot = Sprite.TRANS_ROT180;
					break;
				case 3:
					rot = Sprite.TRANS_ROT270;
					break;
				}
				g.drawRegion(tempImg, 0, 0, tempImg.getWidth() , tempImg.getHeight(), rot, j*32, i*32, 0);
				g.drawRegion(tempImg, 0, 0, tempImg.getWidth() , tempImg.getHeight(), rot, j*32 + 4*32, i*32, 0);
			}
		}
		//g.drawImage(tomato, 64, 64, 0);
		//g.drawImage(cabbage, 32, 64, 0);
	}
}