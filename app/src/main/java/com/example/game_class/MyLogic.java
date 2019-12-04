package com.example.game_class;

import java.util.Random;

public class MyLogic {
    private int rows, cols;
    private boolean[][] matrix;
    private boolean[] arr;
    private int life;

    public MyLogic() {
        this(1, 1, 3);
    }

    public MyLogic(int r, int c) {
        this(r, c, 3);
    }

    public MyLogic(int r, int c, int L){
        rows = r;
        cols = c;
        matrix = new boolean[r][c];
        arr = new boolean[c];
        arr[c/2] = true;
        life = L;
    }

    public int getLife() {
        return life;
    }

    public void checkHit(){
        for(int i = 0; i < cols; i++){
            if(matrix[rows-1][i] && arr[i]){
                life--;
            }
        }
    }

    public boolean[][] getMatrix() {
        return matrix;
    }

    public void nextLine() {
        if (newLine()) {
            Random r = new Random();
            for (int i = 0; i < cols - 1; i++) {
                matrix[0][r.nextInt(cols)] = true;
            }
        } else {
            for (int i = 0; i < cols - 1; i++) {
                matrix[0][i] = false;
            }
        }
    }

    public void step() {
        for (int i = rows - 1; i > 0; i--) {
            for (int j = cols - 1; j >= 0; j--) {
                if (matrix[i - 1][j]) {
                    matrix[i - 1][j] = false;
                    matrix[i][j] = true;
                } else {
                    matrix[i][j] = false;
                }
            }
        }
    }


    private boolean newLine() {
        for (int i = 0; i < cols; i++) {
            if (matrix[0][i] || matrix[1][i]|| matrix[2][i]) {
                return false;
            }
        }
        return true;
    }


    public boolean[] getArr() {
        return arr;
    }

    public void right() {
        for(int i = cols - 2; i >= 0; i-- ){
            if(arr[i]){
                arr[i] = false;
                arr[i+1] = true;
            }
        }
    }

    public void left(){
        for(int i = 1; i < cols; i++){
            if(arr[i]){
                arr[i] = false;
                arr[i-1] = true;
            }
        }
    }

}
