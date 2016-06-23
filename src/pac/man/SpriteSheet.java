package pac.man;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet

{   private BufferedImage sheet;
	public SpriteSheet(String path) throws IOException{
		
		 sheet = ImageIO.read(getClass().getResource(path)); 
	}
	
	public BufferedImage getSprite(int xx,int yy){
		return sheet.getSubimage(xx,yy,16,16);
		
		
		
		
	}
	
}
