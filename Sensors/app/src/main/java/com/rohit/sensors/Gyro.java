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

public class Gyro extends AppCompatActivity implements SensorEventListener {

    TextView XGaxis, YGaxis, ZGaxis;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro);
        XGaxis = findViewById(R.id.XGaxis);
        YGaxis = findViewById(R.id.YGaxis);
        ZGaxis = findViewById(R.id.ZGaxis);
        android.hardware.SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyro != null) {
            sensorManager.registerListener(Gyro.this, gyro, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            YGaxis.setText("Phone Does not Support Gyroscope");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        // Log.d(TAG,"X:" + event.values[0] + "Y: " + event.values[1] + "Z: " + event.values[2]);
        XGaxis.setText("X-Value:  " + event.values[0]+ " rad/s");
        YGaxis.setText("Y-Value:  " + event.values[1]+ " rad/s");
        ZGaxis.setText("Z-Value:  " + event.values[2]+ " rad/s");
    }
}