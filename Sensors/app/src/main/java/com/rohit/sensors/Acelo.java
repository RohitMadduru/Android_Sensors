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

public class Acelo extends AppCompatActivity implements SensorEventListener {


    Sensor accelerometer;

    TextView Xaxis, Yaxis, Zaxis;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acelo);

        Xaxis = findViewById(R.id.Xaxis);
        Yaxis = findViewById(R.id.Yaxis);
        Zaxis = findViewById(R.id.Zaxis);
        android.hardware.SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(Acelo.this, accelerometer, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Yaxis.setText("Phone Does not Support Accelerometer");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        // Log.d(TAG,"X:" + event.values[0] + "Y: " + event.values[1] + "Z: " + event.values[2]);
        Xaxis.setText("X-Value:  " + event.values[0] + " m/s2");
        Yaxis.setText("Y-Value:  " + event.values[1]+ " m/s2");
        Zaxis.setText("Z-Value:  " + event.values[2]+ " m/s2");
    }
}