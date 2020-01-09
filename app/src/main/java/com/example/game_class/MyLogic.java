package com.example.game_class;

import java.util.Random;

public class MyLogic {
    private int rows, cols;
    private int[][] matrix;
    private boolean[] arr;
    private int life;

    public MyLogic() {
        this(1, 1, 3);
    }

    public MyLogic(int r, int c) {
        this(r, c, 3);
    }

    public MyLogic(int r, int c, int L) {
        rows = r;
        cols = c;
        matrix = new int[r][c];
        arr = new boolean[c];
        arr[c / 2] = true;
        life = L;
    }

    public int getLife() {
        return life;
    }

    public int checkBonus() {
        for (int i = 0; i < cols; i++) {
            if (matrix[rows - 2][i] == 2 && arr[i]) {
                matrix[rows - 2][i] = 0;
                return i;
            }
        }
        return -1;
    }

    public int checkHit() {
        for (int i = 0; i < cols; i++) {
            if (matrix[rows - 2][i] == 1 && arr[i]) {
                life--;
                matrix[rows - 2][i] = 0;
                return i;
            }
        }
        return -1;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void nextLine() {
        if (newLine()) {
            Random r = new Random();
            for (int i = 0; i < cols - 1; i++) {
                matrix[0][r.nextInt(cols)] = 1;
            }

            if(r.nextInt(3) > 1) // bunus line
                matrix[0][r.nextInt(cols)] = 2;

        } else {
            for (int i = 0; i < cols - 1; i++) {
                matrix[0][i] = 0;
            }
        }
    }

    public void step() {
        for (int i = rows - 1; i > 0; i--) {
            for (int j = cols - 1; j >= 0; j--) {
                if (matrix[i - 1][j] != 0) {
                    matrix[i][j] = matrix[i-1][j];
                    matrix[i - 1][j] = 0;
                } else {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    private boolean newLine() {
        for (int i = 0; i < cols; i++) {
            if (matrix[0][i] != 0 || matrix[1][i] != 0 || matrix[2][i] != 0) {
                return false;
            }
        }
        return true;
    }

    public void right() {
        for (int i = cols - 2; i >= 0; i--) {
            if (arr[i]) {
                arr[i] = false;
                arr[i + 1] = true;
            }
        }
    }

    public void left() {
        for (int i = 1; i < cols; i++) {
            if (arr[i]) {
                arr[i] = false;
                arr[i - 1] = true;
            }
        }
    }

    public void setArr(boolean[] arr) {
        for(int i = 0; i < cols; i ++)
            this.arr[i] = arr[i];
    }

    public boolean[] getArr() {
        return arr;
    }

}
