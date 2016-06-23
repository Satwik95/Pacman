/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac.man;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author satwik
 */
public class Tile extends Rectangle {
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Tile(int x,int y){
        
        setBounds(x,y,32,32); // size if the blue tiles
        
    }
    // maze tiles
    public void render (Graphics g){
        g.setColor(new Color(33,0, 127)); 
        g.fillRect(x,y,width,height);
    }
    
}
