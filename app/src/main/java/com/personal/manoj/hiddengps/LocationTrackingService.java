package com.personal.manoj.hiddengps;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.location.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class LocationTrackingService extends Service
{

    public static final int LOCATION_UPDATE_INTERVAL_MS = 300000;
    public static final int FASTEST_LOCATION_UPDATE_INTERVAL_MS = 60000;
    public static final int ACCURACY_THRESHOLD = 200;

    private DatabaseReference _userReference;

    public LocationTrackingService()
    {
        Log.d("LocationTrackingService", "service constructor");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        initUserReference();
        Log.d("LocationTrackingService", "service on create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("LocationTrackingService", "service on start");
        listenForLocation();
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    private void listenForLocation()
    {
        FusedLocationProviderClient client = new FusedLocationProviderClient(this);
        LocationRequest request = new LocationRequest()
                .setInterval(LOCATION_UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_LOCATION_UPDATE_INTERVAL_MS)
                .setMaxWaitTime(LOCATION_UPDATE_INTERVAL_MS)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationCallback callback = new LocationCallback()
        {

            @Override
            public void onLocationResult(LocationResult locationResult)
            {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                updateUserLocation(location);
            }

        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("LocationTrackingService", "loc perm error");
            Toast.makeText(this, "System permission error", Toast.LENGTH_LONG).show();
        } else
        {
            Log.d("LocationTrackingService", "requesting loc... ");
            client.requestLocationUpdates(request, callback, null);
        }
    }

    private void initUserReference()
    {
        String userId = Utils.getUserId(this);
        _userReference = FirebaseDatabase.getInstance().getReference()
                .child("personal")
                .child("hiddenGps")
                .child("users")
                .child(userId);
    }

    private void updateUserLocation(final Location location)
    {
        if(location.getAccuracy() < ACCURACY_THRESHOLD)
        {
            Map map = new HashMap()
            {
                {
                    put("lat", location.getLatitude());
                    put("lon", location.getLongitude());
                    put("timeStamp", location.getTime());
                    put("accuracy", location.getAccuracy());
                }
            };
            _userReference.child("location")
                    .updateChildren(map);
        }
    }


}
