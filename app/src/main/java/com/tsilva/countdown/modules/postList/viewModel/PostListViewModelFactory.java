package com.tsilva.countdown.modules.postList.viewModel;

import android.content.Context;
import android.view.View;

import com.tsilva.countdown.api.requests.get.GetFirebaseRealtimeDBApiClientGetCountdownEvents;
import com.tsilva.countdown.api.requests.post.PostFirebaseRealtimeDBApiClientPostCountdownEvent;
import com.tsilva.countdown.modules.postList.viewModel.item.PostItemViewModelFactory;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;

/**
 * Created by Telmo Silva on 20.01.2020.
 */

public final class PostListViewModelFactory
{
    private Context context = null;
    private PersistenceService persistenceService = null;
    private StorageService storageService = null;
    private GetFirebaseRealtimeDBApiClientGetCountdownEvents
            getFirebaseRealtimeDBApiClientGetCountdownEvents = null;
    private PostFirebaseRealtimeDBApiClientPostCountdownEvent
            postFirebaseRealtimeDBApiClientPostCountdownEvent = null;

    public PostListViewModelFactory(
            Context context,
            PersistenceService persistenceService,
            StorageService storageService,
            GetFirebaseRealtimeDBApiClientGetCountdownEvents
                    getFirebaseRealtimeDBApiClientGetCountdownEvents,
            PostFirebaseRealtimeDBApiClientPostCountdownEvent
                    postFirebaseRealtimeDBApiClientPostCountdownEvent)
    {
        this.context = context;
        this.persistenceService = persistenceService;
        this.storageService = storageService;
        this.getFirebaseRealtimeDBApiClientGetCountdownEvents =
                getFirebaseRealtimeDBApiClientGetCountdownEvents;
        this.postFirebaseRealtimeDBApiClientPostCountdownEvent =
                postFirebaseRealtimeDBApiClientPostCountdownEvent;
    }

    public PostListViewModel create(
            View rootView,
            PostItemViewModelFactory postItemViewModelFactory)
    {
        return new PostListViewModel(
                rootView,
                postItemViewModelFactory,
                context,
                persistenceService,
                storageService,
                getFirebaseRealtimeDBApiClientGetCountdownEvents,
                postFirebaseRealtimeDBApiClientPostCountdownEvent);
    }
}