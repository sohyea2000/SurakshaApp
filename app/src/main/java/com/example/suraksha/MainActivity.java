package com.example.suraksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;

public class MainActivity extends AppCompatActivity  {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    FirebaseDatabase database;
    DatabaseReference databaseReference,dataRef;

    Register rg;
    public String name;
    String lat,lot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rg = new Register();
        name = rg.name;

        database = FirebaseDatabase.getInstance();

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();

       Button helpBtn = findViewById(R.id.helpBtn);
       Button provideBtn = findViewById(R.id.provideBtn);
       helpBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(MainActivity.this, MapActivity.class);
               startActivity(intent);
           }
       });
       provideBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent in = new Intent(MainActivity.this,UserActivity.class);
               startActivity(in);
           }
       });
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    lat = String.valueOf(currentLocation.getLatitude());
                    lot = String.valueOf(currentLocation.getLongitude());
                    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Toast.makeText(getApplicationContext(), currentuser, Toast.LENGTH_SHORT).show();
                    databaseReference= database.getReference("Users");
                    databaseReference.child(currentuser).setValue(name);
                    dataRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser);
                    dataRef.child(currentuser).setValue(name);
                    HashMap<String,String> userMap = new HashMap<>();

                    userMap.put("LATITUDE",lat);
                    userMap.put("LONGITUDE",lot);
                    dataRef.setValue(userMap);


                }
            }
        });
    }
   /* private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("HELP!!!")
                        .setContentText("SOMEONE NEEDS YOU");

        Intent notificationIntent = new Intent(this, MapActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }*/


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }

}


