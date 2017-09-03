package com.personal.manoj.hiddengps;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

public class HiddenGps extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Toast.makeText(this, "application onreate called", Toast.LENGTH_SHORT).show();
        //start service to listen to location
        startService(new Intent(this, LocationTrackingService.class));
    }
}
