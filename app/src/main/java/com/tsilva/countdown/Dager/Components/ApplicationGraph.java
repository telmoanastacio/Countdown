package com.tsilva.countdown.Dager.Components;

import com.tsilva.countdown.Activities.MainActivity;
import com.tsilva.countdown.CountdownApp;

/**
 * Created by Telmo Silva on 04.12.2019.
 */

public interface ApplicationGraph
{
    void inject(CountdownApp countdownApp);
    void inject(MainActivity mainActivity);
}