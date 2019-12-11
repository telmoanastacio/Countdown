package com.tsilva.countdown;

import android.app.Application;

import com.tsilva.countdown.dager.components.ApplicationComponent;
import com.tsilva.countdown.dager.components.ApplicationGraph;

/**
 * Created by Telmo Silva on 04.12.2019.
 */

public final class CountdownApp extends Application
{
    public static CountdownApp instance;
    public static ApplicationGraph applicationComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();

        instance = this;
        applicationComponent = createApplicationComponent(instance);
        applicationComponent.inject(this);
    }

    private static ApplicationGraph createApplicationComponent(CountdownApp applicationInstance)
    {
        return ApplicationComponent.Initializer.init(applicationInstance);
    }
}