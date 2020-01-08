package com.example.game_class;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MySensor implements SensorEventListener {
    private GameActivity gameActivity;
    private SensorManager sensorManager;
    float ax,ay,az;   // these are the acceleration in x,y and z axis
    long time, lastUpdate;

    public void setGameActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    public MySensor(Context context)
    {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            ax=event.values[0];
            ay=event.values[1];
            az=event.values[2];


            long actualTime = event.timestamp; //get the event's timestamp
            if(actualTime - lastUpdate > 110000000) {
                right();
                left();
                lastUpdate = actualTime;
            }
        }
    }

    public void right(){
        if(ax < -2.5 && ay < 9.5)
            gameActivity.moveRight();
    }
    public void left(){
        if(ax > 2.5 && ay < 9.5)
            gameActivity.moveLeft();
    }
}
