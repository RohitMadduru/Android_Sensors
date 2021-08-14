package com.rohit.sensors;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


public class Location extends AppCompatActivity {


    Button btnlocation;
    TextView latitude1, longitude1;
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        btnlocation = findViewById(R.id.getLocation);
        latitude1 = findViewById(R.id.latitude1);
        longitude1 = findViewById(R.id.longitude1);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Location.this);

        btnlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Location.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(Location.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {
                    ActivityCompat.requestPermissions(Location.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 55);
                }

            }

            public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
                if (requestCode == 55 && grantResults.length > 0 && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    getCurrentLocation();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }


            @SuppressLint("MissingPermission")
            private void getCurrentLocation() {

                LocationManager locationManager = (LocationManager) getSystemService(
                        Context.LOCATION_SERVICE
                );
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {

                        android.location.Location location = task.getResult();
                        if (location != null) {
                            latitude1.setText(String.valueOf(location.getLatitude()));
                            longitude1.setText(String.valueOf(location.getLongitude()));
                        } else {
                            LocationRequest locationRequest = new LocationRequest()
                                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                    .setInterval(10000)
                                    .setNumUpdates(1);
                            LocationCallback locationCallback = new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    android.location.Location location1 = locationResult.getLastLocation();
                                    latitude1.setText(String.valueOf(location1.getLatitude()));
                                    longitude1.setText(String.valueOf(location1.getLongitude()));
                                }
                            };
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        }
                    });
                } else {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            }
        });
    }
}