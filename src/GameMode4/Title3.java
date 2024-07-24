package GameMode4;

import GameMode3.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Title3 extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private BufferedImage instructions;
	private Tetrist3 window;
	private BufferedImage[] playButton = new BufferedImage[2];
	private Timer timer;
	
	
	public Title3(Tetrist3 window){
                instructions = ImageLoader3.loadImage("/arrow.png");
		timer = new Timer(1000/60, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
			
		});
		timer.start();
		this.window = window;
		
		
		
	}
	
        @Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		
		g.fillRect(0, 0, Tetrist3.WIDTH, Tetrist3.HEIGHT);
		
		
		g.drawImage(instructions, Tetrist3.WIDTH/2 - instructions.getWidth()/2,
				30 - instructions.getHeight()/2 + 150, null);
		
                g.setColor(Color.WHITE);
		g.drawString("Press space to play!", 150, Tetrist3.HEIGHT / 2 + 100);
		
		
	}	

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_SPACE) {
            window.startTetris();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}