package GameMode1.Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyhandler implements KeyListener {
	
	public static boolean upPressed, downPressed,leftPressed,rightPressed , pausePressed;

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		 // Xử lý sự kiện phím cho Player 2
	    if (code == KeyEvent.VK_UP) {
	        upPressed = true;
	    }
	    if (code == KeyEvent.VK_DOWN) {
	        downPressed = true;
	    }
	    if (code == KeyEvent.VK_LEFT) {
	        leftPressed = true;
	    }
	    if (code == KeyEvent.VK_RIGHT) {
	        rightPressed = true;
	    }
	    //player 3
	    // Xử lý sự kiện phím cho Player 2
	    if (code == KeyEvent.VK_8) {
	        upPressed = true;
	    }
	    if (code == KeyEvent.VK_2) {
	        downPressed = true;
	    }
	    if (code == KeyEvent.VK_4) {
	        leftPressed = true;
	    }
	    if (code == KeyEvent.VK_6) {
	        rightPressed = true;
	    }
		if (code == KeyEvent.VK_SPACE) {
			if (pausePressed) {
				
				pausePressed = false;
				
				GamePanel.se.play(0, true);
				GamePanel.music.loop();
			} else {
				pausePressed = true;	
				GamePanel.music.stop();
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	

}
