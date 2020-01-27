package com.tsilva.countdown.modules.editPost.viewModel;

import android.content.Context;
import android.view.View;

import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventDto;
import com.tsilva.countdown.persistence.UserLoginCredentials;
import com.tsilva.countdown.services.StorageService;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Telmo Silva on 27.01.2020.
 */

public final class EditPostViewModelFactory
{
    private Context context = null;
    private StorageService storageService = null;
    private UserLoginCredentials userLoginCredentials = null;

    public EditPostViewModelFactory(
            Context context,
            StorageService storageService,
            UserLoginCredentials userLoginCredentials)
    {
        this.context = context;
        this.storageService = storageService;
        this.userLoginCredentials = userLoginCredentials;
    }

    public EditPostViewModel create(
            @NotNull View root,
            String postId,
            CountdownEventDto countdownEventDto)
    {
        return new EditPostViewModel(
                context,
                storageService,
                userLoginCredentials,
                root,
                postId,
                countdownEventDto);
    }
}