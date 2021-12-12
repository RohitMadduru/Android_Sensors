package com.rohit.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Magnet extends AppCompatActivity implements SensorEventListener {

    TextView XMaxis, YMaxis, ZMaxis;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnet);
        XMaxis = findViewById(R.id.XMaxis);
        YMaxis = findViewById(R.id.YMaxis);
        ZMaxis = findViewById(R.id.ZMaxis);
        android.hardware.SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor magno = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magno != null) {
            sensorManager.registerListener(Magnet.this, magno, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            YMaxis.setText("Phone Does not Support Magnetometer");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        XMaxis.setText("X-Value:  " + event.values[0]+ "  μT");
        YMaxis.setText("Y-Value:  " + event.values[1]+ "  μT");
        ZMaxis.setText("Z-Value:  " + event.values[2]+ "  μT");
    }
}