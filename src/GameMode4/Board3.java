package GameMode4;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board3 extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;

    private BufferedImage pause, refresh;

	//board dimensions (the playing area)
    private final int boardHeight = 20, boardWidth = 10;

	// block size
    public static final int blockSize = 30;

	// field
    private Color[][] board = new Color[boardHeight][boardWidth];

	// array with all the possible shapes
    private Shape3[] shapes = new Shape3[7];

	// currentShape
    private static Shape3 currentShape, nextShape;

	// game loop
    private Timer looper;

    private int FPS = 60;

    private int delay = 1000 / FPS;

	// mouse events variables
    private int mouseX, mouseY;

    private boolean leftClick = false;

    private Rectangle stopBounds, refreshBounds;

    private boolean gamePaused = false;

    private boolean gameOver = false;
    
    private boolean scoreSaved = false;

    
    private Color[] colors = {Color.decode("#ed1c24"), Color.decode("#ff7f27"), Color.decode("#fff200"), 
        Color.decode("#22b14c"), Color.decode("#00a2e8"), Color.decode("#a349a4"), Color.decode("#3f48cc")};
    private Random random = new Random();
	// buttons press lapse
    private Timer buttonLapse = new Timer(300, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            buttonLapse.stop();
        }
    });

	// score
    private int score = 0;

    public Board3() {

        pause = ImageLoader3.loadImage("/pause.png");
        refresh = ImageLoader3.loadImage("/refresh.png");
        
        mouseX = 0;
        mouseY = 0;

        stopBounds = new Rectangle(550, 500, pause.getWidth(), pause.getHeight() + pause.getHeight() / 2);
        refreshBounds = new Rectangle(350, 500 - refresh.getHeight() - 20, refresh.getWidth(),
                refresh.getHeight() + refresh.getHeight() / 2);

		// create game looper
        looper = new Timer(delay, new GameLooper());

		// create shapes
        shapes[0] = new Shape3(new int[][]{
            {1, 1, 1, 1} // I shape;
        }, this, colors[0]);

        shapes[1] = new Shape3(new int[][]{
            {1, 1, 1},
            {0, 1, 0}, // T shape;
        }, this, colors[1]);

        shapes[2] = new Shape3(new int[][]{
            {1, 1, 1},
            {1, 0, 0}, // L shape;
        }, this, colors[2]);

        shapes[3] = new Shape3(new int[][]{
            {1, 1, 1},
            {0, 0, 1}, // J shape;
        }, this, colors[3]);

        shapes[4] = new Shape3(new int[][]{
            {0, 1, 1},
            {1, 1, 0}, // S shape;
        }, this, colors[4]);

        shapes[5] = new Shape3(new int[][]{
            {1, 1, 0},
            {0, 1, 1}, // Z shape;
        }, this, colors[5]);

        shapes[6] = new Shape3(new int[][]{
            {1, 1},
            {1, 1}, // O shape;
        }, this, colors[6]);

    }

    
    
public int checkLine() {
    int linesCleared = 0;
    for (int row = boardHeight - 1; row >= 0; row--) {
        boolean lineFilled = true;
        for (int col = 0; col < boardWidth; col++) {
            if (board[row][col] == null) {
                lineFilled = false;
                break;
            }
        }
        if (lineFilled) {
            linesCleared++;
            removeLine(row);
            row++; // Check the same row again after lines above have been shifted down
        }
    }
    return linesCleared;
}

    
public void addScore(int linesCleared) {
    score += linesCleared * 10;
}


private void removeLine(int row) {
    for (int r = row; r > 0; r--) {
        for (int col = 0; col < boardWidth; col++) {
            board[r][col] = board[r - 1][col];
        }
    }

    // Xóa hàng đầu tiên
    for (int col = 0; col < boardWidth; col++) {
        board[0][col] = null;
    }
}

  private void update() {
    if (stopBounds.contains(mouseX, mouseY) && leftClick && !buttonLapse.isRunning() && !gameOver) {
        buttonLapse.start();
        gamePaused = !gamePaused;
    }

    if (refreshBounds.contains(mouseX, mouseY) && leftClick) {
        startGame();
    }

    if (gamePaused || gameOver) {
        if (gameOver && !scoreSaved) {
            ScoreManager3.saveScore(score); // Lưu điểm khi trò chơi kết thúc
            scoreSaved = true; // Đánh dấu rằng điểm số đã được lưu
        }
        return;
    }
    
    currentShape.update();
    
    // Kiểm tra và xóa hàng đầy đủ
    int linesCleared = checkLine();
    if (linesCleared > 0) {
        addScore(linesCleared); // Thêm điểm khi có hàng bị xóa
    }
}



   

   @Override
public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.BLACK);
    g2.fillRect(0, 0, getWidth(), getHeight());

    // Vẽ bảng điểm
    for (int row = 0; row < board.length; row++) {
        for (int col = 0; col < board[row].length; col++) {
            if (board[row][col] != null) {
                g2.setColor(board[row][col]);
                g2.fillRect(col * blockSize, row * blockSize, blockSize, blockSize);
            }
        }
    }

    // Vẽ hình dạng tiếp theo
    g2.setColor(nextShape.getColor());
    for (int row = 0; row < nextShape.getCoords().length; row++) {
        for (int col = 0; col < nextShape.getCoords()[0].length; col++) {
            if (nextShape.getCoords()[row][col] != 0) {
                g2.fillRect(col * 30 + 320, row * 30 + 50, Board3.blockSize, Board3.blockSize);
            }
        }
    }

    currentShape.render(g2);

    // Vẽ nút tạm dừng và làm mới
    if (stopBounds.contains(mouseX, mouseY)) {
        g2.drawImage(pause.getScaledInstance(pause.getWidth() + 3, pause.getHeight() + 3, BufferedImage.SCALE_DEFAULT), stopBounds.x + 3, stopBounds.y + 3, null);
    } else {
        g2.drawImage(pause, stopBounds.x, stopBounds.y, null);
    }

    if (refreshBounds.contains(mouseX, mouseY)) {
        g2.drawImage(refresh.getScaledInstance(refresh.getWidth() + 3, refresh.getHeight() + 3, BufferedImage.SCALE_DEFAULT), refreshBounds.x + 3, refreshBounds.y + 3, null);
    } else {
        g2.drawImage(refresh, refreshBounds.x, refreshBounds.y, null);
    }

    // Hiển thị trạng thái tạm dừng và kết thúc trò chơi
    if (gamePaused) {
        String gamePausedString = "GAME PAUSED";
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Georgia", Font.BOLD, 30));
        g2.drawString(gamePausedString, 35, getHeight() / 2);
    }
    if (gameOver) {
        drawGameOver(g2); // Gọi drawGameOver để vẽ thông báo GAME OVER và điểm cao
    }

    // Vẽ điểm số hiện tại
    g2.setColor(Color.WHITE);
    g2.setFont(new Font("Georgia", Font.BOLD, 20));
    g2.drawString("SCORE", getWidth() - 555, getHeight() / 2);
    g2.drawString(String.valueOf(score), getWidth() - 555, getHeight() / 2 + 30);

    // Vẽ lưới
    g2.setColor(Color.WHITE);
    for (int i = 0; i <= boardHeight; i++) {
        g2.drawLine(0, i * blockSize, boardWidth * blockSize, i * blockSize);
    }
    for (int j = 0; j <= boardWidth; j++) {
        g2.drawLine(j * blockSize, 0, j * blockSize, boardHeight * blockSize);
    }
}

    public void setNextShape() {
        int index = random.nextInt(shapes.length);
        int colorIndex = random.nextInt(colors.length);
        nextShape = new Shape3(shapes[index].getCoords(), this, colors[colorIndex]);
    }

    public void setCurrentShape() {
    currentShape = nextShape;
    setNextShape();

    for (int row = 0; row < currentShape.getCoords().length; row++) {
        for (int col = 0; col < currentShape.getCoords()[0].length; col++) {
            if (currentShape.getCoords()[row][col] != 0) {
                if (board[currentShape.getY() + row][currentShape.getX() + col] != null) {
                    gameOver = true;
                    break;
                }
            }
        }
        if (gameOver) {
            break;
        }
    }
}

    
    private void drawGameOver(Graphics2D g2) {
    // Vẽ thông báo "GAME OVER"
    g2.setColor(Color.YELLOW);
    g2.setFont(g2.getFont().deriveFont(50f));
    int x = getWidth() / 2 - 150;  // Canh giữa theo chiều ngang
    int y = getHeight() / 2 - 150;  // Canh giữa theo chiều dọc
    g2.drawString("GAME OVER", x, y);
    // Hiển thị danh sách điểm cao
    g2.setFont(new Font("Arial", Font.PLAIN, 20));
    List<Integer> scores = ScoreManager3.getScores();
    y += 60;  // Dịch xuống để không chồng lên thông báo GAME OVER
    g2.drawString("Highscores:", x - 100, y);
    y += 30;
    int count = 0;
    for (int i = 0; i < scores.size() && count < 10; i++) {
        g2.drawString((i + 1) + ". " + scores.get(i), x - 100, y);
        y += 25;
        count++;
    }
}


    public Color[][] getBoard() {
        return board;
    }

   
 @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            currentShape.rotateShape();
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            currentShape.setDeltaX(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            currentShape.setDeltaX(-1);
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            currentShape.speedUp();
        }
    }



@Override
public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_S) {
        currentShape.speedDown();
    }
}


    @Override
    public void keyTyped(KeyEvent e) {

    }

   public void startGame() {
    stopGame();
    setRandomBlocksAtBottom(4);
    setNextShape();
    setCurrentShape();
    gameOver = false;
    scoreSaved = false; // Đặt lại cờ lưu điểm
    looper.start();
}



     private void setRandomBlocksAtBottom(int numberOfRows) {
    // Khởi tạo nhiều hàng dưới cùng với các khối ngẫu nhiên
    for (int row = boardHeight - numberOfRows; row < boardHeight; row++) {
        for (int col = 0; col < boardWidth; col++) {
            if (random.nextBoolean()) { // Ngẫu nhiên quyết định có đặt khối ở cột này hay không
                board[row][col] = colors[random.nextInt(colors.length)];
            }
        }
    }
}
   public void stopGame() {
    score = 0;

    for (var board1 : board) {
        for (int col = 0; col < board1.length; col++) {
            board1[col] = null;
        }
    }
    looper.stop();
}


    class GameLooper implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            update();
            repaint();
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClick = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}