package com.example.game_class;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public interface CallBack_ActivityList {
    void setName(String name);

    void hideFragmentAskName();

    void setLocation(LatLng latLng);

    ArrayList<Score> getScores();
 }
