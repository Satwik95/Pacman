/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac.man;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JFrame;

/**
 *
 * @author // 
 * 
 * */
public class Game extends Canvas implements Runnable,KeyListener
{
/**
	 * 
	 */
private static final long serialVersionUID = 1L;
boolean isRunning = false;
public static final int WIDTH = 640 , HEIGHT  = 480;
public static final String TITLE = "Pac-Man";

public static int score=0;

private Thread thread;

public static Player player;
public static Level level;
public static SpriteSheet spritesheet;

public static final int PAUSE_SCREEN = 0,GAME =1, VIC_SCREEN = 2,LOSE_GAME = 3;
public static int STATE = -1;
public boolean isEnter = false,isC = false,isL = false;
private int time= 0;
private int targetFrame = 30;
private boolean showText = true;
public Game() throws IOException
{
    Dimension dimension = new Dimension(Game.WIDTH,Game.HEIGHT);
    setPreferredSize(dimension);
    setMinimumSize(dimension);     
    setMaximumSize(dimension);
    
    addKeyListener(this);  //user input  to this thread
    
    STATE = PAUSE_SCREEN;   //start with pause screen
    player = new Player(Game.WIDTH/2,Game.HEIGHT/2);  // this is the object for the user

   level = new Level("/map/map.png");  // level maze set
   
   spritesheet = new SpriteSheet("/sprites/spritesheet.png");  
   
    new Texture();
}

    
public synchronized void start()   // using synchronization we we use objects
{
    if(isRunning) return;
    
    isRunning = true;
    thread = new Thread(this);
    thread.start();//goes to run method
}
    

public synchronized void stop()
{
    if(!isRunning) return;
    isRunning = false;
    try{ thread.join();
    }catch(InterruptedException e){
        e.printStackTrace();
    }
}

    private void tick()  //game logic and states
    {if(STATE ==GAME){
     player.tick(); 
     level.tick();
    }else if(STATE == PAUSE_SCREEN)
    {   time++;
    	if(time==targetFrame){
    		time= 0;
    		if(showText){
    			showText = false;
    		}else{showText = true;
    	}}
    	if(isEnter){
    		isEnter = false;
    		  player = new Player(Game.WIDTH/2,Game.HEIGHT/2);  

    		   level = new Level("/map/map.png");  
    		   STATE = GAME;
    		
    	}else if(STATE == VIC_SCREEN)
    	{
    		if(isC)
    			{isC = false;}
    			 player = new Player(Game.WIDTH/2,Game.HEIGHT/2);  

 		   level = new Level("/map/map.png");  
 		   STATE = GAME;
    			
    	}else if(STATE == LOSE_GAME)
    	{
    		if(isL)
    			isL = false;
    			 player = new Player(Game.WIDTH/2,Game.HEIGHT/2);  

 		   level = new Level("/map/map.png");  
 		   STATE = GAME;
    			
    	}
    }
   }
    
    private void render()// rendering
    {
      BufferStrategy bs = getBufferStrategy();
      if(bs == null)
      {
          createBufferStrategy(3);
          return;
      }
      
      Graphics g = bs.getDrawGraphics();
      g.setColor(Color.black);
      g.fillRect(0,0,Game.WIDTH,Game.HEIGHT);
      if(STATE == GAME){
      player.render(g);
      score=player.score;
      level.render(g);
      }else if(STATE == PAUSE_SCREEN){
    	  int boxWidth = 500;
    	  int boxHeight = 200;
    	  int xx = Game.WIDTH/2 - boxWidth/2;
    	  int yy = Game.HEIGHT/2 - boxHeight/2;
    	  
    	  g.setColor(Color.blue);
    	  g.fillRect(xx,yy,boxWidth,boxHeight);
    	  
    	  g.setColor(Color.white);
    	  g.setFont(new Font(Font.DIALOG,Font.BOLD,30));
    	 if(showText) g.drawString("Press Enter/arrow key To START!", xx+10, yy+90);
    	  
      }else if(STATE == VIC_SCREEN){
    	  int boxWidth = 500;
    	  int boxHeight = 200;
    	  int xx = Game.WIDTH/2 - boxWidth/2;
    	  int yy = Game.HEIGHT/2 - boxHeight/2;
    	  
    	  g.setColor(Color.green);
    	  g.fillRect(xx,yy,boxWidth,boxHeight);
    	  
    	  g.setColor(Color.white);
    	  g.setFont(new Font(Font.DIALOG,Font.BOLD,30));
    	  g.drawString("VICTORY!! Press '1' to Restart", xx+40, yy+90);
    	  g.drawString("SCORE IS:"+ score, xx+40, yy+140);
    	  
    	  
      }else if(STATE == LOSE_GAME){
    	  int boxWidth = 500;
    	  int boxHeight = 200;
    	  int xx = Game.WIDTH/2 - boxWidth/2;
    	  int yy = Game.HEIGHT/2 - boxHeight/2;
    	  
    	  g.setColor(Color.green);
    	  g.fillRect(xx,yy,boxWidth,boxHeight);
    	  
    	  g.setColor(Color.white);
    	  g.setFont(new Font(Font.DIALOG,Font.BOLD,30));
    	  g.drawString("You Lost :( Press '1' to Restart", xx+40, yy+90);
    	  g.drawString("SCORE IS:"+ score, xx+40, yy+140);
    	  g.drawString("Highest Score is:"+ 14200, xx+40, yy+180);
    	  
    	  
      }
      g.dispose(); 
      bs.show();
      
    }
    @Override/* basic idea for game engine:
    public void gameLoop()
{
    timer = new Timer();
    timer.schedule(new LoopyStuff(), 0, 1000 / 60); //new timer at 60 fps, the timing mechanism
}

private class LoopyStuff extends java.util.TimerTask
{
    public void run() //this becomes the loop
    {
        doGameUpdates();
        render();

        if (!isRunning)
        {
            timer.cancel();
        }
    }
} */
    public void run(){// game engine
        requestFocus();
       int fps = 0;
       double timer = System.currentTimeMillis();
        long lasttime = System.nanoTime();
        double targetTick = 60.0;
        double delta = 0;
        double ns = 1000000000/targetTick;
        while(isRunning){     
            long now = System.nanoTime();
           delta+=(now - lasttime)/ns;
           lasttime = now;
           
           while(delta>=1)
           {      
           tick();
            render();
           fps++;
           delta--;
           
           }
           
           if(System.currentTimeMillis()-timer >= 1000){
               
               System.out.println(fps);// to set the fps to 60fps
               fps = 0;
               timer+=1000;
           }
           
        }
        
        stop();
    }
    
    //---------------------------VOID MAIN --------------------------------------------------
    public static void main(String args[]) throws IOException
    {
        Game game = new Game();
        JFrame frame = new JFrame();
        frame.setTitle(game.TITLE);
        frame.add(game);
        frame.setResizable(false);
        frame.pack();//keep window size as we specified
        
       
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e){
     //To change body of generated methods, choose Tools | Templates.
    	if(STATE == GAME){
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = true;
        if(e.getKeyCode() == KeyEvent.VK_LEFT) player.left = true;
        if(e.getKeyCode() == KeyEvent.VK_UP) player.up = true;   
        if(e.getKeyCode() == KeyEvent.VK_DOWN) player.down = true; 
    	}if(STATE==PAUSE_SCREEN)
    	{
    		if(e.getKeyCode() == KeyEvent.VK_ENTER) STATE  = GAME;
    		isEnter = true;
    	}
    	else if(STATE==VIC_SCREEN)
    	{
    		if(e.getKeyCode() == KeyEvent.VK_1) STATE  = GAME;
    		isC = true;
    	}
    	else if(STATE==VIC_SCREEN)
    	{
    		if(e.getKeyCode() == KeyEvent.VK_1) STATE  = GAME;
    		isL = true;
    	}
        
      //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = false;
        if(e.getKeyCode() == KeyEvent.VK_LEFT) player.left = false;
        if(e.getKeyCode() == KeyEvent.VK_UP) player.up = false;
        if(e.getKeyCode() == KeyEvent.VK_DOWN) player.down = false;
        if(e.getKeyCode() == KeyEvent.VK_ENTER){ STATE  = GAME;
		isEnter = false;}
        if(e.getKeyCode() == KeyEvent.VK_1){ STATE  = GAME;
		isC = false;isL = false;
		}
       
     
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        
           
    }
}
