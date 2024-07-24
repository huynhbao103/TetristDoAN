package GameMode1.Main;

import javax.swing.JFrame;

public class Main extends JFrame {


	public Main()  {
     
    }

    public static void main(String[] args) {
        JFrame windown = new JFrame("simple Testrist");
        windown.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windown.setResizable(false);// điều chỉnh độ rộng của game
        // add game to the window 
        GamePanel gp = new GamePanel();			
        windown.add(gp);
        windown.pack();
        
        windown.setLocationRelativeTo(null);
        windown.setVisible(true);
        gp.launchGame();
    }
}
