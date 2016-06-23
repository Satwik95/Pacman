package pac.man;

import java.awt.image.BufferedImage;

public class Texture {
	public static BufferedImage[] player;
	public static BufferedImage ghost;
	
	public Texture(){
		 player = new BufferedImage[2];// set size 
		player[0] = Game.spritesheet.getSprite(0,0);
		player[1] = Game.spritesheet.getSprite(16,0);
		
		ghost = Game.spritesheet.getSprite(0,16);
	}
	
	
}
