package com.tsilva.countdown.modules.detailsScreen.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.R;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventDto;
import com.tsilva.countdown.databinding.PostDetailsActivityBinding;
import com.tsilva.countdown.modules.ModulesConfiguration;
import com.tsilva.countdown.modules.detailsScreen.viewModel.PostDetailsViewModelFactory;
import com.tsilva.countdown.services.StorageService;
import com.tsilva.countdown.storage.activity.CurrentActivity;

import javax.inject.Inject;

/**
 * Created by Telmo Silva on 04.02.2020.
 */

public final class PostDetailsActivity extends CurrentActivity
{
    public static boolean isAlive = false;

    @Inject
    StorageService storageService;

    @Inject
    PostDetailsViewModelFactory postDetailsViewModelFactory;

    private PostDetailsActivityBinding postDetailsActivityBinding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isAlive = true;

        CountdownApp.applicationComponent.inject(this);
        setCurrentActivity();

        Intent intent = getIntent();
        CountdownEventDto countdownEventDto =
                (CountdownEventDto) intent.getSerializableExtra(
                        ModulesConfiguration.COUNTDOWN_EVENT_DTO);

        postDetailsActivityBinding = DataBindingUtil
                .setContentView(this, R.layout.post_details_activity);
        postDetailsActivityBinding.setViewModel(postDetailsViewModelFactory.create(
                countdownEventDto));

        postDetailsActivityBinding.executePendingBindings();
    }

    @Override
    public void onBackPressed()
    {
        postDetailsActivityBinding.getViewModel().onBackPressed();
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