package com.tsilva.countdown.modules.editPost.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.R;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventDto;
import com.tsilva.countdown.databinding.EditPostActivityBinding;
import com.tsilva.countdown.modules.ModulesConfiguration;
import com.tsilva.countdown.modules.editPost.viewModel.EditPostViewModelFactory;
import com.tsilva.countdown.services.StorageService;
import com.tsilva.countdown.storage.activity.CurrentActivity;

import javax.inject.Inject;

/**
 * Created by Telmo Silva on 27.01.2020.
 */

public final class EditPostActivity extends CurrentActivity
{
    public static boolean isAlive = false;

    @Inject
    StorageService storageService;

    @Inject
    EditPostViewModelFactory editPostViewModelFactory;

    private EditPostActivityBinding editPostActivityBinding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isAlive = true;

        CountdownApp.applicationComponent.inject(this);
        setCurrentActivity();

        Intent intent = getIntent();
        String postId = intent.getStringExtra(ModulesConfiguration.POST_ID);
        CountdownEventDto countdownEventDto =
                (CountdownEventDto) intent.getSerializableExtra(
                        ModulesConfiguration.COUNTDOWN_EVENT_DTO);

        editPostActivityBinding = DataBindingUtil
                .setContentView(this, R.layout.edit_post_activity);
        editPostActivityBinding.setViewModel(editPostViewModelFactory.create(
                editPostActivityBinding.getRoot(),
                postId,
                countdownEventDto));

        editPostActivityBinding.executePendingBindings();
    }

    @Override
    public void onBackPressed()
    {
        editPostActivityBinding.getViewModel().onBackPressed();
    }

    @Override
    protected void onDestroy()
    {
        isAlive = false;
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