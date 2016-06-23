/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac.man;
import java.util.ArrayList;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


import javax.imageio.ImageIO;

/**
 *
 * @author satwik
 */
public class Level {
    public int width;
    public int height;
    
    public Tile[][] tiles;
    public ArrayList<Enemy> enemies;
    public ArrayList<Apple> apples;
   
    public Player player ;
    
    public Level(String path)
    {   apples = new ArrayList<>();
        enemies = new ArrayList<>();
        try{
           BufferedImage map = ImageIO.read(getClass().getResource(path)); 
      // loading map 
           this.height = map.getHeight();
           this.width = map.getWidth();
           int pixels[] = new int[width*height];
           tiles = new Tile[width][height];
           map.getRGB(0,0,width,height, pixels,0,width);
           
           for(int xx = 0;xx<width;xx++)  // moving through all the boxes in the array checking colour of every box
           {   for(int yy = 0;yy<height;yy++)
               {
                  int val = pixels[xx + (yy*width)] ; //calculating hex code of colour
                  
                  if(val == 0xFF000000)
                      //tiles
                  { tiles[xx][yy] = new Tile(xx*32,yy*32);}
                  else if (val == 0xFF0026FF){
                	  //player
                	   Game.player.x = xx*32;
                	   Game.player.y = yy*32;							//allocating colour as we used on the spritesheet 
                	  
                	  
                  }
                  else if (val == 0xFFFF0000){
                	 // enemy  
                	  enemies.add(new Enemy(xx*32,yy*32));
                	  
                  }
                  
             
                  
                  else{  // remaining parts coin
                	  apples.add(new Apple(xx*32,yy*32)); // create new coins
                	  
                  }
                  
                  
               } 
           }
           
           
        }catch(IOException e)
            
        { e.printStackTrace();
        }
    }
    
    
    public void tick()
    {
    	for(int i = 0; i<enemies.size();i++)
    	{
    		enemies.get(i).tick();
    	}
    }
    
      public void render (Graphics g){
        for(int x =0;x<width;x++){
           for(int y = 0;y<height;y++)
           {
               if(tiles[x][y] != null)
                   tiles[x][y].render(g);
        }}
        
        for(int i = 0; i<apples.size();i++){
        	apples.get(i).render(g);
        }
        
        for(int i = 0; i<enemies.size();i++)
        {
        	enemies.get(i).render(g);
        }
    }
}
