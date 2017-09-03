package com.personal.manoj.hiddengps;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class HiddenGps extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Crashlytics.setUserEmail(Utils.getUserEmail(this));
        Crashlytics.setString("PHONE",Utils.getUserPhone(this));
        //start service to listen to location
        startService(new Intent(this, LocationTrackingService.class));
    }
}
