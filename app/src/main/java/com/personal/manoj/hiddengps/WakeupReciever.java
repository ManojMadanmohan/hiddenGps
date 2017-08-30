package com.personal.manoj.hiddengps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class WakeupReciever extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        //do nothin
        Toast.makeText(context, "on Recieve called",Toast.LENGTH_LONG).show();
    }
}
