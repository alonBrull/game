package com.example.game_class;

import com.google.android.gms.maps.model.LatLng;

public interface CallBack_Location {
    LatLng getLatlng();

    LatLng getLatLngFromAddress(String address);

    void changeCameraPosition(Score score);

    void setMarkers();

    void setCurrentLocation();
}
