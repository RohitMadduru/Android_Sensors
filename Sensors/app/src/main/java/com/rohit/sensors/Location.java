package com.rohit.sensors;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Location extends AppCompatActivity {

    NetworkChange networkChange = new NetworkChange();
    Button btnlocation, setLocation;
    TextView latitude1, longitude1;

    public static final String FILE_NAME = "location.txt";

    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        setLocation = findViewById(R.id.setLocation);
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


                                       }

        );

        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lat = latitude1.getText().toString();
                String lon = longitude1.getText().toString();
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    fos.write(lat.getBytes());
                    fos.write(lon.getBytes());
                    Toast.makeText(Location.this, "Saved to " + getFilesDir() + "/" + FILE_NAME,Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }


    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChange, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChange);
        super.onStop();
    }


}