package GameMode3;

import java.awt.Color;
import java.awt.Graphics;

public class Shape2 {

    private Color color;

    private int x, y;

    private long time, lastTime;

    private int normal = 600, fast = 50;

    private int delay;

    private int[][] coords;

    private int[][] reference;

    private int deltaX;



    private boolean collision = false, moveX = false;

    private int timePassedFromCollision = -1;
    private final Board2 board2;

    public Shape2(int[][] coords, Board2 board2, Color color) {
        this.coords = coords;
        this.board2 = board2;
        this.color = color;
        deltaX = 0;
        x = 4;
        y = 0;
        delay = normal;
        time = 0;
        lastTime = System.currentTimeMillis();
        reference = new int[coords.length][coords[0].length];

        System.arraycopy(coords, 0, reference, 0, coords.length);

    }

    long deltaTime;

   public void update() {
    moveX = true;
    deltaTime = System.currentTimeMillis() - lastTime;
    time += deltaTime;
    lastTime = System.currentTimeMillis();

    if (collision && timePassedFromCollision > 500) {
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[0].length; col++) {
                if (coords[row][col] != 0) {
                    board2.getBoard2()[y + row][x + col] = color;
                }
            }
        }
        int linesCleared = board2.checkLine();
        if (linesCleared > 0) {
            board2.addScore(linesCleared);
        }
        board2.setCurrentShape();
        timePassedFromCollision = -1;
    }

    // check moving horizontal
    if (!(x + deltaX + coords[0].length > 10) && !(x + deltaX < 0)) {
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[row].length; col++) {
                if (coords[row][col] != 0) {
                    if (board2.getBoard2()[y + row][x + deltaX + col] != null) {
                        moveX = false;
                    }
                }
            }
        }

        if (moveX) {
            x += deltaX;
        }
    }

    // Check position + height(number of row) of shape
    if (timePassedFromCollision == -1) {
        if (!(y + 1 + coords.length > 20)) {
            for (int row = 0; row < coords.length; row++) {
                for (int col = 0; col < coords[row].length; col++) {
                    if (coords[row][col] != 0) {
                        if (board2.getBoard2()[y + 1 + row][x + col] != null) {
                            collision();
                        }
                    }
                }
            }
            if (time > delay) {
                y++;
                time = 0;
            }
        } else {
            collision();
        }
    } else {
        timePassedFromCollision += deltaTime;
    }

    deltaX = 0;
}

    private void collision() {
        collision = true;
        timePassedFromCollision = 0;
    }

    public void render(Graphics g) {

        g.setColor(color);
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[0].length; col++) {
                if (coords[row][col] != 0) {
                    g.fillRect(col * 30 + x * 30, row * 30 + y * 30, Board2.blockSize, Board2.blockSize);
                }
            }
        }
    }

    private void checkLine() {
        int size = board2.getBoard2().length - 1;

        for (int i = board2.getBoard2().length - 1; i > 0; i--) {
            int count = 0;
            for (int j = 0; j < board2.getBoard2()[0].length; j++) {
                if (board2.getBoard2()[i][j] != null) {
                    count++;
                }

                board2.getBoard2()[size][j] = board2.getBoard2()[i][j];
            }
            if (count < board2.getBoard2()[0].length) {
                size--;
            }
        }
    }

    public void rotateShape() {

        int[][] rotatedShape = null;

        rotatedShape = transposeMatrix(coords);

        rotatedShape = reverseRows(rotatedShape);

        if ((x + rotatedShape[0].length > 10) || (y + rotatedShape.length > 20)) {
            return;
        }

        for (int row = 0; row < rotatedShape.length; row++) {
            for (int col = 0; col < rotatedShape[row].length; col++) {
                if (rotatedShape[row][col] != 0) {
                    if (board2.getBoard2()[y + row][x + col] != null) {
                        return;
                    }
                }
            }
        }
        coords = rotatedShape;
    }

    private int[][] transposeMatrix(int[][] matrix) {
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                temp[j][i] = matrix[i][j];
            }
        }
        return temp;
    }

    private int[][] reverseRows(int[][] matrix) {

        int middle = matrix.length / 2;

        for (int i = 0; i < middle; i++) {
            int[] temp = matrix[i];

            matrix[i] = matrix[matrix.length - i - 1];
            matrix[matrix.length - i - 1] = temp;
        }

        return matrix;

    }

    public Color getColor() {
        return color;
    }

    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
    }

    public void speedUp() {
        delay = fast;
    }

    public void speedDown() {
        delay = normal;
    }

    public int[][] getCoords() {
        return coords;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}