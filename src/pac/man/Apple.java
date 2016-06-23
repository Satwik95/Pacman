package pac.man;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Apple extends Rectangle {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Apple(int x,int y)
	{
		setBounds(x+10,y+8,8,8);//x+8 and y+8 aligns the  32*32 at the center and 8,8 gives the coin the size i.e width and height
		
		  
		  
		
	}
	public void render(Graphics g)
	{
		g.setColor(Color.cyan);   //coin color
		g.fillRect(x,y,width,height);
		
	}
}
