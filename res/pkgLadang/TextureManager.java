package pkgLadang;

import java.io.IOException;
import java.util.Hashtable;
import javax.microedition.lcdui.Image;

public class TextureManager {
	Hashtable Textures;
	TextureManager(){
		Textures = new Hashtable();
	}
	public boolean insertTexture(String image,String key){
		try {
			Textures.put(key, Image.createImage(image));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Image getTexture(String key){
		return (Image) Textures.get(key);
	}
	
	
}
