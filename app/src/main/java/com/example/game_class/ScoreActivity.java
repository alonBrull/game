package com.example.game_class;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {
    private Button score_BTN_playAgain, score_BTN_menu, score_BTN_scores;
    private Button score_BTN_here;
    private FragmentChart fram_chart;
    private FragmentAskName fram_askname;
    private FragmentMap fram_map;

    private FrameLayout score_FRM_chart, score_FRM_askname, score_FRM_map;

    private TextView score_TXT_score;
    private Score score;
    private Score[] highScores;
    private String[] highScoresStrings;

    private final int NUM_OF_SCORES = 10;
    private boolean isSensor;

    MySharedPreferences msp;
    private Gson gson;

//    private MediaPlayer mediaPlayerBackGround;
//    private MediaPlayer mediaPlayerEffect;

    // map parameters
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    private Button btn1, btn2, btn3;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_score);

//        mediaPlayerBackGround = MediaPlayer.create(this, R.raw.score_track);
//        mediaPlayerBackGround.setLooping(true);
//        mediaPlayerBackGround.start();

        Bundle extras = getIntent().getExtras();
        isSensor = extras.getBoolean("sensor");

        msp = new MySharedPreferences(this);
        gson = new Gson();

        findViews();
        setOnClickListeners();

        String sc = msp.getString("score", "NA");
        score = gson.fromJson(sc, Score.class);

        score_TXT_score.setText("SCORE\n" + "" + score.getScore());

        highScores = new Score[NUM_OF_SCORES];
        highScoresStrings = new String[NUM_OF_SCORES];

        fram_askname = new FragmentAskName();
        fram_askname.setCallBackList(myCallBack);
        fram_askname.setCallBackLocation(callBack_location);

        initScores();
        manageScores();

        fram_chart = new FragmentChart();
        fram_chart.setCallBackList(myCallBack);
        fram_chart.setCallBackLocation(callBack_location);


        // map
        fram_map = new FragmentMap();
        fram_map.setCallBack(callBack_location);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();
    }

    private void setOnClickListeners() {
        score_BTN_scores.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fram_chart.isVisible() && !fram_askname.isVisible()) {
                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    transaction1.add(R.id.score_FRM_chart, fram_chart);
                    transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction1.commit();

                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    transaction2.add(R.id.score_FRM_map, fram_map);
                    transaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction2.commit();

                } else {
                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    transaction1.remove(fram_chart);
                    transaction1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    transaction1.commit();

                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    transaction2.remove(fram_map);
                    transaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    transaction2.commit();
                }
            }
        }));

        score_BTN_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoGameActivity();
            }
        });

        score_BTN_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMainActivity();
            }
        });
    }

    private void findViews() {
        score_TXT_score = findViewById(R.id.score_TXT_score);

        score_FRM_chart = findViewById(R.id.score_FRM_chart);
        score_FRM_askname = findViewById(R.id.score_FRM_askname);
        score_FRM_map = findViewById(R.id.score_FRM_map);

        score_BTN_scores = findViewById(R.id.score_BTN_scores);
        score_BTN_playAgain = findViewById(R.id.score_BTN_playAgain);
        score_BTN_menu = findViewById(R.id.score_BTN_menu);
    }

    private void gotoGameActivity() {
//        mediaPlayerBackGround.release();
//        mediaPlayerBackGround = null;

        Intent intent = new Intent(ScoreActivity.this, GameActivity.class);
        intent.putExtra("sensor", isSensor);

        startActivity(intent);

        this.finish();
    }

    private void gotoMainActivity() {
//        mediaPlayerBackGround.release();
//        mediaPlayerBackGround = null;
        this.finish();
    }

    private void initScores() {
        setScoresStrings();
        for (int i = 0; i < 10; i++) {
            if (highScoresStrings[i].equals("NOTFOUND"))
                highScores[i] = null;
            else
                highScores[i] = gson.fromJson(highScoresStrings[i], Score.class);
        }
    }

    private void setScoresStrings() {
        for (int i = 0; i < NUM_OF_SCORES; i++)
            highScoresStrings[i] = msp.getString("p" + i, "NOTFOUND");
    }

    private void manageScores() {
        for (int i = 0; i < NUM_OF_SCORES; i++) {
            if (highScores[i] == null) {
                requestName();
                break;
            } else if (score.getScore() > highScores[i].getScore()) {
                requestName();
                break;
            }
        }
    }

    private void sortScores() {
        for (int i = 0; i < NUM_OF_SCORES; i++) {
            if (highScores[i] == null) {
                highScores[i] = score;
                highScoresStrings[i] = gson.toJson(score);
                String key = "p" + i; // key for shared preferences
                msp.putString(key, highScoresStrings[i]);

                Log.d("pttt", "first:" + highScores[0].getName());

                return;
            }
            if (score.getScore() > highScores[i].getScore()) {
                demoteScores(i);
                highScores[i] = score;
                highScoresStrings[i] = gson.toJson(score);
                String key = "p" + i; // key for shared preferences
                msp.putString(key, highScoresStrings[i]);

                Log.d("pttt", "first:" + highScores[0].getName() + " second:" + highScores[1].getName() + " third:" + highScores[2].getName());

                return;
            }
        }
    }

    private void requestName() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.score_FRM_askname, fram_askname);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void demoteScores(int i) {
        for (int j = NUM_OF_SCORES - 1; j > i; j--) {
            if (highScores[j - 1] != null) {
                highScores[j] = highScores[j - 1];
                highScoresStrings[j] = highScoresStrings[j - 1];
                String key = "p" + j; // key for shared preferences
                msp.putString(key, highScoresStrings[j]);
            }
        }
    }

    final CallBack_ActivityList myCallBack = new CallBack_ActivityList() {
        @Override
        public void setName(String name) {
            score.setName(name);
            sortScores();// continue sort
        }

        public void setLocation(LatLng latLng) {
            score.setLocation(latLng);
        }

        @Override
        public void hideFragmentAskName() {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
            ft.remove(fram_askname);
            ft.commit();
        }


        @Override
        public ArrayList<Score> getScores() {
            ArrayList<Score> scores = new ArrayList<>();
            for (int i = 0; i < NUM_OF_SCORES; i++) {
                if (highScores[i] == null)
                    return scores;

                scores.add(highScores[i]);
            }
            return scores;
        }
    };

    // handle maps
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    lat = location.getLatitude();
                                    lng = location.getLongitude();

                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }

    final CallBack_Location callBack_location = new CallBack_Location() {
        @Override
        public LatLng getLatlng() {
            return new LatLng(lat, lng);
        }

        @Override
        public LatLng getLatLngFromAddress(String address) {
            return getLocationFromAddress(address);
        }

        @Override
        public void changeCameraPosition(Score score) {
            LatLng latLng = score.getLocation();
            fram_map.moveCamera(latLng);
        }

        @Override
        public void setMarkers() {
            for (int i = 0; i < NUM_OF_SCORES; i++) {
                Score s = highScores[i];
                if (s == null)
                    return;
                fram_map.addMarker(s.getLocation(), "#" + (i + 1), s.getName());
            }
        }

        @Override
        public void setCurrentLocation() {
            score.setLocation(getLatlng());
        }
    };

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            try {
                if (address == null) {
                    return new LatLng(0, 0);
                }
                Address location = address.get(0);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                p1 = new LatLng(latitude, longitude);

                return p1;
            } catch (IndexOutOfBoundsException e) {
                Toast.makeText(this, "please enter a valid address", Toast.LENGTH_LONG).show();
                return new LatLng(0, 0);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new LatLng(0, 0);
        }
    }
}