package com.tsilva.countdown.modules.detailsScreen.viewModel;

import android.content.Context;

import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventDto;
import com.tsilva.countdown.services.StorageService;

/**
 * Created by Telmo Silva on 04.02.2020.
 */

public final class PostDetailsViewModelFactory
{
    private Context context = null;
    private StorageService storageService = null;

    public PostDetailsViewModelFactory(
            Context context,
            StorageService storageService)
    {
        this.context = context;
        this.storageService = storageService;
    }

    public PostDetailsViewModel create(CountdownEventDto countdownEventDto)
    {
        return new PostDetailsViewModel(
                context,
                storageService,
                countdownEventDto);
    }
}