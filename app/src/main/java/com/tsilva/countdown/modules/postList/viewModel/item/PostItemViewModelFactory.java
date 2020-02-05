package com.tsilva.countdown.modules.postList.viewModel.item;

import android.content.Context;

import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventDto;
import com.tsilva.countdown.persistence.UserLoginCredentials;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;

/**
 * Created by Telmo Silva on 22.01.2020.
 */

public final class PostItemViewModelFactory
{
    private Context context = null;
    private PersistenceService persistenceService = null;
    private StorageService storageService = null;
    private UserLoginCredentials userLoginCredentials = null;

    public PostItemViewModelFactory(
            Context context,
            PersistenceService persistenceService,
            StorageService storageService,
            UserLoginCredentials userLoginCredentials)
    {
        this.context = context;
        this.persistenceService = persistenceService;
        this.storageService = storageService;
        this.userLoginCredentials = userLoginCredentials;
    }

    public PostItemViewModel create(String postId, CountdownEventDto countdownEventDto)
    {
        return new PostItemViewModel(
                context,
                persistenceService,
                storageService,
                userLoginCredentials,
                postId,
                countdownEventDto);
    }
}