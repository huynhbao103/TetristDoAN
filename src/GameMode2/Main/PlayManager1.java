package GameMode2.Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import GameMode2.Mino.Block1;
import GameMode2.Mino.Mino1;
import GameMode2.Mino.Mino_Bar1;
import GameMode2.Mino.Mino_Square1;
import GameMode2.Mino.Mino_T1;
import GameMode2.Mino.Mino_1L1;
import GameMode2.Mino.Mino_1L2;
import GameMode2.Mino.Mino_1Z1;
import GameMode2.Mino.Mino_1Z2;

public class PlayManager1 {

    // Main Play Area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    // Mino
    Mino1 currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino1 nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block1> statBlocks = new ArrayList<>();

    // Others
    public static int dropInterval = 60; // mino drops in every 60 frames
    boolean gameOver;
    boolean scoreSaved;

    // Effect
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    // Score
    int level = 1;
    int lines;
    int score;

    public PlayManager1() {
        // Main Play manager Frame
        left_x = (GamePanel1.WIDTH / 2) - (WIDTH / 2); // 1280/2 - 360/2=460
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH / 2) - Block1.SIZE;
        MINO_START_Y = top_y + Block1.SIZE;

        NEXTMINO_X = right_x + 120;
        NEXTMINO_Y = top_y + 510;

        // Set the starting Mino
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

        // Initialize score saved flag
        scoreSaved = false;
    }

    private Mino1 pickMino() {
        // Pick a random mino
        Mino1 mino = null;
        int i = new Random().nextInt(7);

        switch (i) {
            case 0:
                mino = new Mino_1L1();
                break;
            case 1:
                mino = new Mino_1L2();
                break;
            case 2:
                mino = new Mino_Square1();
                break;
            case 3:
                mino = new Mino_Bar1();
                break;
            case 4:
                mino = new Mino_T1();
                break;
            case 5:
                mino = new Mino_1Z1();
                break;
            case 6:
                mino = new Mino_1Z2();
                break;
        }
        return mino;
    }

    public void update() {
        // Check the state of currentMino
        if (!currentMino.active) {
            // If mino is not active, add it to staticBlocks
            statBlocks.add(currentMino.b[0]);
            statBlocks.add(currentMino.b[1]);
            statBlocks.add(currentMino.b[2]);
            statBlocks.add(currentMino.b[3]);

            // Check game over
            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
                if (!scoreSaved) { // Check if score has not been saved
                    gameOver = true;
                    GamePanel1.music.stop();
                    GamePanel1.se.play(2, false);
                    ScoreManager1.saveScore(score); // Save score only once
                    scoreSaved = true; // Mark score as saved
                }
            } else {
                currentMino.deactivating = false;
                currentMino = nextMino;
                currentMino.setXY(MINO_START_X, MINO_START_Y);
                nextMino = pickMino();
                nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

                // Clear full lines
                checkDelete();
            }
        } else {
            currentMino.update();
        }
    }

    private void checkDelete() {
        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        while (x < right_x && y < bottom_y) {
            for (int i = 0; i < statBlocks.size(); i++) {
                if (statBlocks.get(i).x == x && statBlocks.get(i).y == y) {
                    // Increase the count if there is a static block
                    blockCount++;
                }
            }
            x += Block1.SIZE;

            if (x == right_x) {
                // If blockCount hits 12, that means the current y line is all filled with blocks
                // So we can delete them
                if (blockCount == 12) {
                    effectCounterOn = true;
                    effectY.add(y);
                    for (int i = statBlocks.size() - 1; i > -1; i--) {
                        // Remove all the blocks in the current y line
                        if (statBlocks.get(i).y == y) {
                            statBlocks.remove(i);
                        }
                    }

                    lineCount++;
                    lines++;

                    // Increase speed
                    if (lines % 10 == 0 && dropInterval > 1) {
                        level++;
                        dropInterval = Math.max(10, dropInterval - 10);
                    }

                    // Slide down blocks above the deleted line
                    for (int i = 0; i < statBlocks.size(); i++) {
                        if (statBlocks.get(i).y < y) {
                            statBlocks.get(i).y += Block1.SIZE;
                        }
                    }
                }
                blockCount = 0;
                x = left_x;
                y += Block1.SIZE;
            }
        }

        // Add Score
        if (lineCount > 0) {
            GamePanel1.se.play(1, false);
            int singleLineScore = 10 * level;
            score += singleLineScore * lineCount;
        }
    }

    public void draw(Graphics2D g2) {
        // Draw game area frame
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

        // Draw next mino frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x -= 60, y, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 60, y + 60);

        // Draw score frame
        g2.drawRect(x -= 10, top_y, 250, 300);
        x += 40;
        y = top_y + 90;
        g2.drawString("LEVEL: " + level, x, y);
        y += 70;
        g2.drawString("LINES: " + lines, x, y);
        y += 70;
        g2.drawString("Score: " + score, x, y);

        // Draw current mino
        if (currentMino != null) {
            currentMino.draw(g2);
        }

        // Draw next mino
        nextMino.draw(g2);

        // Draw static blocks
        for (int i = 0; i < statBlocks.size(); i++) {
            statBlocks.get(i).draw(g2);
        }

        // Draw effect
        if (effectCounterOn) {
            effectCounter++;

            g2.setColor(Color.red);
            for (int i = 0; i < effectY.size(); i++) {
                g2.fillRect(left_x, effectY.get(i), WIDTH, Block1.SIZE);
            }
            if (effectCounter == 10) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

        // Draw pause and game over
        g2.setColor(Color.YELLOW);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (gameOver) {
            x = left_x - 100;
            y = top_y + 100;
            g2.drawString("GAME OVER", x, y);
            // luu diem
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            List<Integer> scores = ScoreManager1.getScores();
            y += 50;
            g2.drawString("Highscores:", x, y);
            y += 30;
            int count = 0;
            for (int i = 0; i < scores.size() && count < 10; i++) {
                g2.drawString((i + 1) + ". " + scores.get(i), x, y);
                y += 25;
                count++;
            }
        } else if (KeyHandler1.pausePressed) {
            x = left_x + 90;
            y = top_y + 320;
            g2.drawString("PAUSE", x, y);
        }
    }
}
