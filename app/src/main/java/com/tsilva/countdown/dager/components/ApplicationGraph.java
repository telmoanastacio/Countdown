package com.tsilva.countdown.dager.components;

import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.modules.loginScreen.activity.LoginScreenActivity;
import com.tsilva.countdown.modules.optionsMenu.activity.OptionsMenuActivity;
import com.tsilva.countdown.modules.postList.activity.PostListActivity;

/**
 * Created by Telmo Silva on 04.12.2019.
 */

public interface ApplicationGraph
{
    void inject(CountdownApp countdownApp);
    void inject(LoginScreenActivity loginScreenActivity);
    void inject(OptionsMenuActivity optionsMenuActivity);
    void inject(PostListActivity postListActivity);
}