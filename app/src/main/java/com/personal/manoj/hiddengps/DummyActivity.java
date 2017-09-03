package com.personal.manoj.hiddengps;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class DummyActivity extends Activity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "trying to start location updates...", Toast.LENGTH_SHORT).show();
        finish();
    }
}
