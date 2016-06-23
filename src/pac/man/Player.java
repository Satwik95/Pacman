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
public class Player extends Rectangle {
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean right,left,down,up;
    private int speed = 4;
    private int time = 0,targetTime = 10,lastDir = 1;
    public int imageIndex = 0;
  public  int score = 0,life=2; 
  public int finalScore;
    
    public Player(int x,int y)
    {
        setBounds(x,y,30,30);  //fits  the entire pixel
    }
    public void tick()
    {  
        if(right && canMove(x+speed,y)){x+=speed; lastDir = 1;}
        if(down && canMove(x,y+speed)){y+=speed;}
        if(left && canMove(x-speed,y)){x-=speed;lastDir = -1;}
        if(up && canMove(x,y-speed)){y-=speed;}
        
        Level level = Game.level;
        
        for(int i = 0;i<level.apples.size();i++){
        	if(this.intersects(level.apples.get(i))){  //eating apples
        		level.apples.remove(i);
        		score+=100;
        		break;
        	}
        }
        
        // game restarts when we collect all the coins
      //  if(level.apples.size()==0) 
        	
        //	{Game.player = new Player(0,0);
        	//Game.level = new Level("/map/map.png");
        //return;}
        
     
        for(int i = 0;i< Game.level.enemies.size();i++)
        {
        	Enemy e = Game.level.enemies.get(i);
        	if(e.intersects(this))
        	{     		
        		
        		for(int j = 0;j<3;j++)
            	{   i++;
            	
            			finalScore=score;
            		Game.STATE = Game.LOSE_GAME;
            			
            	}
            	Game.player = new Player(0,0);
            	Game.level = new Level("/map/map.png");
            	return;
        
        	
        	
        	
        	}
        }
        /*		for(int u= 0;u<10;u++)
            	{   i++;
            		finalScore=score;
            		Game.STATE = Game.LOSE_GAME;	
            	}
            	Game.player = new Player(0,0);
            	Game.level = new Level("/map/map.png");
            	return;*/
        	
        	
        if(Game.level.apples.size() == 0){
        	for(int y = 0;y<10;y++)
        	{  
        		finalScore=score;
        		Game.STATE = Game.VIC_SCREEN;	
        	}
        	Game.player = new Player(0,0);
        	Game.level = new Level("/map/map.png");
        	return;
        }
        
        time++;
        if(time==targetTime)
        {
        	time = 0;
        	imageIndex++;
        }
        
    }
    
    
    //collision and free movement in the maze
    
    private boolean canMove(int nextx, int nexty){
    	Rectangle bounds = new Rectangle(nextx,nexty,width,height);
    	Level level  = Game.level;  //sets the maze movement
    	
    	for(int xx=0;xx<level.tiles.length;xx++){
    		for(int yy=0;yy<level.tiles[0].length;yy++){
    			if(level.tiles[xx][yy]!=null){
    				if(bounds.intersects(level.tiles[xx][yy])){
    					return false;
    				}
    			}
    			
    		}
    	}
    	
    	
    	
    return true;}
    
    
    
    public void render(Graphics g){
        
        SpriteSheet sheet = Game.spritesheet;
      if(lastDir == 1)  g.drawImage(Texture.player[imageIndex%2],x,y,width,height,null);
      else   g.drawImage(Texture.player[imageIndex%2],x+32,y,-width,height,null);  //when we do -width it flips but it become x-32
      										                                       //hence we add 32 to x to adjust it
    }
    
    
    
    
}
