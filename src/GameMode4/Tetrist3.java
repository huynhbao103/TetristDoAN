package GameMode4;

import javax.swing.JFrame;
import java.awt.GridLayout;

public class Tetrist3 {
    public static final int WIDTH = 900, HEIGHT = 629;

    private final Board3 board3;
    private final Title3 title;
    private JFrame window;

    public Tetrist3() {

        window = new JFrame("Tetris Ramdom ");
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        board3 = new Board3();
        window.setLayout(new GridLayout(1, 2));
        title = new Title3(this);

        window.addKeyListener(board3);
        window.addKeyListener(title);

        window.add(title);

        window.setVisible(true);
         
    }

    public void startTetris() {
        window.remove(title);
        window.addMouseMotionListener(board3);
        window.addMouseListener(board3);
        window.add(board3);
        board3.startGame();
        window.revalidate(); 
        
    }

    public static void main(String[] args) {
        new Tetrist3();
    }

}