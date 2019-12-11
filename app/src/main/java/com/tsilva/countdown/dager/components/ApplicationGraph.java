package com.tsilva.countdown.dager.components;

import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.modules.loginScreen.activity.LoginScreenActivity;

/**
 * Created by Telmo Silva on 04.12.2019.
 */

public interface ApplicationGraph
{
    void inject(CountdownApp countdownApp);
    void inject(LoginScreenActivity loginScreenActivity);
}