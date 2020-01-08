package com.example.game_class;

import com.google.android.gms.maps.model.LatLng;

public class Score {
    private int score;
    private int gold;
    private String name;
    private LatLng location;

    public Score() {
        this(0, 0);
    }

    public Score(int s, int g) {
        this(s, g, "");
    }

    public Score(int s, int g, String n){
        score = s;
        gold = g;
        name = n;
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

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}


