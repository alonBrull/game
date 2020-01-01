package com.example.game_class;

public class Score {
    private int score;
    private int gold;
    private String name;

    public Score() {
        this(0, 0);
    }

    public Score(int s, int g) {
        score = s;
        gold = g;
        name = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}


