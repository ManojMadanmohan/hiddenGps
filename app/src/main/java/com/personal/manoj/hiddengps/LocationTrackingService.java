package com.personal.manoj.hiddengps;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import com.google.android.gms.location.*;

public class LocationTrackingService extends Service
{

    public static final int LOCATION_UPDATE_INTERVAL_MS = 300000;
    public static final int FASTEST_LOCATION_UPDATE_INTERVAL_MS = 60000;

    public LocationTrackingService()
    {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

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
                Location location = locationResult.getLastLocation();
                //use lat lng
                Toast.makeText(LocationTrackingService.this, location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                super.onLocationResult(locationResult);
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
            Toast.makeText(this, "error loc", Toast.LENGTH_SHORT).show();
        } else
        {
            Toast.makeText(this, "loc request making", Toast.LENGTH_SHORT).show();
            client.requestLocationUpdates(request, callback, null);
        }
    }


}
