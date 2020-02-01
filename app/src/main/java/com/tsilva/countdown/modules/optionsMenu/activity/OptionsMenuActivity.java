package com.tsilva.countdown.modules.optionsMenu.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.R;
import com.tsilva.countdown.databinding.OptionsMenuActivityBinding;
import com.tsilva.countdown.modules.optionsMenu.viewModel.OptionsMenuViewModelFactory;
import com.tsilva.countdown.services.StorageService;
import com.tsilva.countdown.storage.activity.CurrentActivity;

import javax.inject.Inject;

/**
 * Created by Telmo Silva on 09.01.2020.
 */

public final class OptionsMenuActivity extends CurrentActivity
{
    public static boolean isAlive = false;

    @Inject
    Context context;

    @Inject
    StorageService storageService;

    @Inject
    OptionsMenuViewModelFactory optionsMenuViewModelFactory;

    private OptionsMenuActivityBinding optionsMenuActivityBinding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isAlive = true;

        CountdownApp.applicationComponent.inject(this);
        setCurrentActivity();

        optionsMenuActivityBinding = DataBindingUtil
                .setContentView(this, R.layout.options_menu_activity);
        optionsMenuActivityBinding.setViewModel(optionsMenuViewModelFactory.create());

        optionsMenuActivityBinding.executePendingBindings();
    }

    @Override
    protected void onDestroy()
    {
        isAlive = false;
        storageService.getActivityManager().clearActivityByObject(this);
        super.onDestroy();
    }

    @Override
    public CurrentActivity getCurrentActivity()
    {
        return storageService.getActivityManager().getCurrentActivity();
    }

    @Override
    public void setCurrentActivity()
    {
        try
        {
            storageService.getActivityManager().setCurrentActivity(this);
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }
}