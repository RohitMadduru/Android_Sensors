package com.rohit.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void accelerometer(View view) {

        Intent intent = new Intent(HomeScreen.this, Acelo.class);
        startActivity(intent);
    }

    public void location(View view) {
        Intent intent = new Intent(HomeScreen.this, Location.class);
        startActivity(intent);
    }

    public void microphone(View view) {
        Intent intent = new Intent(HomeScreen.this, Micro.class);
        startActivity(intent);
    }

    public void magnetometer(View view) {
        Intent intent = new Intent(HomeScreen.this, Magnet.class);
        startActivity(intent);
    }

    public void gyroscope(View view) {
        Intent intent = new Intent(HomeScreen.this, Gyro.class);
        startActivity(intent);
    }

    public void UploadImage(View view) {
        Intent intent = new Intent(HomeScreen.this, Upload.class);
        startActivity(intent);
    }

    public void proximity1(View view) {
        Intent intent = new Intent(HomeScreen.this, Proximity.class);
        startActivity(intent);
    }

    public void temp1(View view) {
        Intent intent = new Intent(HomeScreen.this, Temperature.class);
        startActivity(intent);
    }

    public void humidity1(View view) {
        Intent intent = new Intent(HomeScreen.this, Humidity.class);
        startActivity(intent);
    }

    public void pressure1(View view) {
        Intent intent = new Intent(HomeScreen.this, Pressure.class);
        startActivity(intent);
    }


}