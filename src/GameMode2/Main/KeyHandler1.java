
package GameMode2.Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler1 implements KeyListener{
    public static boolean upPressed, downPressed,leftPressed ,rightPressed,pausePressed;
    
    @Override
    public void keyTyped(KeyEvent e){
        
    }
    @Override
    public void keyPressed(KeyEvent e){
        int code =e.getKeyCode();
        if(code ==KeyEvent.VK_W){
            upPressed =true;
        }
        if(code ==KeyEvent.VK_A){
            leftPressed =true;
        }
        if(code ==KeyEvent.VK_S){
            downPressed =true;
        }
        if(code ==KeyEvent.VK_D){
            rightPressed =true;
        }
        if (code == KeyEvent.VK_SPACE){
            if(pausePressed){
                pausePressed = false ;
                GamePanel1.music.play(0, true);
                GamePanel1.music.loop();
            }
            else{
                pausePressed = true ;
                GamePanel1.music.stop();
            }
        }
              
        
    }
    @Override
    public void keyReleased(KeyEvent e){
        
    }
}
