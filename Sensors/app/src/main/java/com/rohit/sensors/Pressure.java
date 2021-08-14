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

public class Pressure extends AppCompatActivity implements SensorEventListener {
    TextView pressure;
    private SensorManager sensorManager;
    private Sensor pressureSensor;
    private boolean isAvailable;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pressure);
        pressure = findViewById(R.id.press1);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
            pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            isAvailable = true;
        } else {
            pressure.setText("Pressure Sensor is Not Available");
            isAvailable = false;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        pressure.setText(event.values[0] + " hpa");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAvailable) {
            sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isAvailable) {
            sensorManager.unregisterListener(this);
        }
    }
}