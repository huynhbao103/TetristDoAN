package GameMode1.Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import GameMode1.Mino.Block;
import GameMode1.Mino.Mino;
import GameMode1.Mino.Mino_Bar;
import GameMode1.Mino.Mino_Square;
import GameMode1.Mino.Mino_T;
import GameMode1.Mino.Mino_l1;
import GameMode1.Mino.Mino_l2;
import GameMode1.Mino.Mino_z1;
import GameMode1.Mino.Mino_z2;

public class PlayManager {

    // play area game
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;
    // Mino block
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    //next mino
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    // Other
    // auto drop
    public static int dropInterval = 60; // 60/FPS

    //effect
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();
    // game over
    boolean gameOver;
     boolean scoreSaved;

    // điểm và level
    int level = 1;
    int lines;
    int score;

    public PlayManager() {
        left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2); // 1280/2-360/2 = 460
        right_x = left_x + WIDTH;
        top_y = 100;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        // Next MINO
        NEXTMINO_X = right_x + 180;
        NEXTMINO_Y = top_y + 500;

        // set the starting MINO
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
        
        
         scoreSaved = false;
    }

    private Mino pickMino() {
        Mino mino = null;
        int i = new Random().nextInt(7);
        switch (i) {
            case 0:
                mino = new Mino_l1();
                break;
            case 1:
                mino = new Mino_l2();
                break;
            case 2:
                mino = new Mino_Square();
                break;
            case 3:
                mino = new Mino_Bar();
                break;
            case 4:
                mino = new Mino_T();
                break;
            case 5:
                mino = new Mino_z1();
                break;
            case 6:
                mino = new Mino_z2();
                break;

        }
        return mino;

    }

    public void update() {
        // Kiểm tra trạng thái của currentMino
        if (!currentMino.active) {
            // Nếu mino không hoạt động, thêm nó vào staticBlocks
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            // Kiểm tra game over
            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
                if (!scoreSaved) { // Kiểm tra nếu điểm chưa được lưu
                    gameOver = true;
                    GamePanel.music.stop();
                    GamePanel.se.play(2, false);
                    ScoreManager.saveScore(score); // Lưu điểm chỉ một lần
                    scoreSaved = true; // Đánh dấu rằng điểm đã được lưu
                }
            } else {
                currentMino.deactivating = false;
                currentMino = nextMino;
                currentMino.setXY(MINO_START_X, MINO_START_Y);
                nextMino = pickMino();
                nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

                // Xoá các hàng đầy
                Checkdelete();
            }
        } else {
            currentMino.update();
        }
    }

    public void Checkdelete() {
        int x = left_x;
        int y = top_y;
        int blockCount = 0;

        int lineCount = 0;

        while (x < right_x && y < bottom_y) {
            for (int i = 0; i < staticBlocks.size(); i++) {
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
                    //Increase the count  if  there  is a  static  block 
                    blockCount++;
                }

            }
            x += Block.SIZE;
            if (x == right_x) {
                // if  blockCOiunt = 12 thi no full hang nen xoa di 1 hang 
                if (blockCount == 12) {

                    effectCounterOn = true;
                    effectY.add(y);

                    for (int i = staticBlocks.size() - 1; i > -1; i--) {
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }

                    lineCount++;
                    lines++;

                    //drop speed 
                    if (lines % 10 == 0 && dropInterval > 1) {
                        level++;
                        if (dropInterval > 10) {
                            dropInterval -= 10;
                        } else {
                            dropInterval -= 1;
                        }
                    }

                    // a line have been  deleted so need to side  down blocks that are  above it 
                    for (int i = 0; i < staticBlocks.size(); i++) {
                        //if a block is above the current y, move it down  by the block size
                        if (staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }

                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }

        if (lineCount > 0) {
            GamePanel.se.play(1, false);
            int singleLineScore = 10 * level;
            score += singleLineScore * lineCount;
        }

    }

    public void draw(Graphics2D g2) {
        //draw play area frame
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

        // draw play seen frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("Next Block", x + 30, y + 30);

        //draw the score
        g2.drawRect(x, top_y, 250, 300);
        x += 40;
        y = top_y + 90;
        g2.drawString("Level: " + level, x, y);
        y += 70;
        g2.drawString("lines: " + lines, x, y);
        y += 70;
        g2.drawString("Score: " + score, x, y);

        //draw the currentMino
        if (currentMino != null) {
            currentMino.draw(g2);

            // draw  static blocks
            for (int i = 0; i < staticBlocks.size(); i++) {
                staticBlocks.get(i).draw(g2);
            }
        }

        // draw effect
        if (effectCounterOn) {
            effectCounter++;
            g2.setColor(Color.green);
            for (int i = 0; i < effectY.size(); i++) {
                g2.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE);
            }
            if (effectCounter == 5) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

        nextMino.draw(g2);

        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (gameOver) {
            x = left_x - 250;
            y = top_y ;
            g2.drawString("Game Over", x, y);
            // luu diem
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            List<Integer> scores = ScoreManager.getScores();
            y += 50;
            g2.drawString("Highscores:", x, y);
            y += 30;
            int count = 0;
            for (int i = 0; i < scores.size() && count < 10; i++) {
                g2.drawString((i + 1) + ". " + scores.get(i), x, y);
                y += 25;
                count++;
            }
        } else if (Keyhandler.pausePressed) {
            x = left_x + 90;
            y = top_y + 320;
            g2.drawString("PAUSE", x, y);
        }
    }
}
