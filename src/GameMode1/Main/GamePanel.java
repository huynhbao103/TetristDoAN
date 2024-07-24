package GameMode1.Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	final int FPS =120;
	Thread gameThread;
	PlayManager pm;
	public static Sounds music = new Sounds();
	public static Sounds se = new Sounds();
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setBackground(Color.black);
		this.setLayout(null);
		//KeyListerner
			this.addKeyListener(new Keyhandler());
			this.setFocusable(true);
		
		 this.pm = new PlayManager(); 
	}
	
	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
		
		music.play(0, true);
		music.loop();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// game Loop
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime)/drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
			   }
			}
		}
		
		
	public void update() {
		if(Keyhandler.pausePressed == false && pm.gameOver == false) {
		pm.update();
		}
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		pm.draw(g2);
	}
	

}
