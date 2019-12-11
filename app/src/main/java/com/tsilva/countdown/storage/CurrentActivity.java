package com.tsilva.countdown.storage;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Telmo Silva on 11.12.2019.
 */

public abstract class CurrentActivity extends AppCompatActivity
{
    public abstract CurrentActivity getCurrentActivity();
    public abstract void setCurrentActivity();
}