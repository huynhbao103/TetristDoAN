
package GameMode2.Main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
public class GamePanel1 extends JPanel implements Runnable {
    public static final int WIDTH = 1280;
    public static final int HEIGHT =720 ;
    final int FPS =500 ;
    Thread gameThread;
    PlayManager1 pm;
    public static sound1 music = new sound1();
    public static sound1 se = new sound1();
    
    public GamePanel1(){
        //Panel Setting
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        //Implement KeyListener
        this.addKeyListener(new KeyHandler1());
        this.setFocusable(true);
        
        
        pm =new PlayManager1();
    }
    public void lauchGame(){
        gameThread = new Thread(this);
        gameThread.start();
        
        music.play(0, true);
        music.loop();
    }
    
    @Override
    public void run (){
  
    // vòng lặp của game
    double drawInterval = 1000000000/FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    
    while(gameThread !=null){
      currentTime =System.nanoTime();
      
      delta += (currentTime-lastTime)/ drawInterval;
      lastTime = currentTime;
      
      if(delta >= 1){
          update();
          repaint();
          delta--;
      }
    }
  
    }
    private void update(){
        if(KeyHandler1.pausePressed == false && pm.gameOver == false){
            pm.update();
        } 
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        
        Graphics2D g2 =(Graphics2D)g;
        pm.draw(g2);
    }
   
}
