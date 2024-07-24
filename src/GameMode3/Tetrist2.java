package GameMode3;

import javax.swing.JFrame;
import java.awt.GridLayout;

public class Tetrist2 {
    public static final int WIDTH = 900, HEIGHT = 629;

    private Board board;
    private Board2 board2;
    private Title title;
    private JFrame window;

    public Tetrist2() {

        window = new JFrame("Tetris 2 player");
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        board = new Board();
        board2 = new Board2();
        window.setLayout(new GridLayout(1, 2));
        title = new Title(this);

        window.addKeyListener(board);
        window.addKeyListener(board2);
        window.addKeyListener(title);

        window.add(title);

        window.setVisible(true);
         
    }

    public void startTetris() {
        window.remove(title);
        window.addMouseMotionListener(board);
        window.addMouseListener(board);
        window.add(board);
         window.addMouseMotionListener(board2);
        window.addMouseListener(board2);
        window.add(board2);
        board.startGame();
        board2.startGame();
        window.revalidate(); 
        
    }

    public static void main(String[] args) {
        new Tetrist2();
    }

}