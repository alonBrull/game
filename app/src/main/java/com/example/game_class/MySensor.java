package com.example.game_class;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MySensor implements SensorEventListener {
    private CallBack_Sensor callBack_sensor;
    private SensorManager sensorManager;
    float ax,ay,az;   // these are the acceleration in x,y and z axis
    private int i;
    public void setCallBackSensor(CallBack_Sensor callBack_sensor){
        this.callBack_sensor = callBack_sensor;
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

            if(i != 0 && ax > -2.5 && ax < 2.5) {
                i = 0;
                callBack_sensor.center();
                callBack_sensor.move();
            }

            else if(i != 1 && ax > 3 && ax < 4.5) {
                i = 1;
                callBack_sensor.centerLeft();
                callBack_sensor.move();
            }

            else if(i != 2 && ax < -3 && ax > -4.5) {
                i = 2;
                callBack_sensor.centerRight();
                callBack_sensor.move();
            }

            else if(i != 3 && ax > 5) {
                i = 3;
                callBack_sensor.left();
                callBack_sensor.move();
            }

            else if(i != 4 && ax < - 5) {
                i = 4;
                callBack_sensor.right();
                callBack_sensor.move();
            }
        }
    }
}
