package com.tsilva.countdown.modules.postList.viewModel;

import android.content.Context;
import android.view.View;

import com.tsilva.countdown.api.requests.delete.DeleteFirebaseRealtimeDBApiClientUpdateCountdownEvent;
import com.tsilva.countdown.api.requests.get.GetFirebaseRealtimeDBApiClientGetCountdownEvents;
import com.tsilva.countdown.api.requests.patch.PatchFirebaseRealtimeDBApiClientUpdateCountdownEvent;
import com.tsilva.countdown.api.requests.post.PostFirebaseRealtimeDBApiClientPostCountdownEvent;
import com.tsilva.countdown.modules.postList.viewModel.item.PostItemViewModelFactory;
import com.tsilva.countdown.persistence.UserLoginCredentials;
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
    private UserLoginCredentials userLoginCredentials = null;
    private GetFirebaseRealtimeDBApiClientGetCountdownEvents
            getFirebaseRealtimeDBApiClientGetCountdownEvents = null;
    private PostFirebaseRealtimeDBApiClientPostCountdownEvent
            postFirebaseRealtimeDBApiClientPostCountdownEvent = null;
    private PatchFirebaseRealtimeDBApiClientUpdateCountdownEvent
            patchFirebaseRealtimeDBApiClientUpdateCountdownEvent = null;
    private DeleteFirebaseRealtimeDBApiClientUpdateCountdownEvent
            deleteFirebaseRealtimeDBApiClientUpdateCountdownEvent = null;

    public PostListViewModelFactory(
            Context context,
            PersistenceService persistenceService,
            StorageService storageService,
            UserLoginCredentials userLoginCredentials,
            GetFirebaseRealtimeDBApiClientGetCountdownEvents
                    getFirebaseRealtimeDBApiClientGetCountdownEvents,
            PostFirebaseRealtimeDBApiClientPostCountdownEvent
                    postFirebaseRealtimeDBApiClientPostCountdownEvent,
            PatchFirebaseRealtimeDBApiClientUpdateCountdownEvent
                    patchFirebaseRealtimeDBApiClientUpdateCountdownEvent,
            DeleteFirebaseRealtimeDBApiClientUpdateCountdownEvent
                    deleteFirebaseRealtimeDBApiClientUpdateCountdownEvent)
    {
        this.context = context;
        this.persistenceService = persistenceService;
        this.storageService = storageService;
        this.userLoginCredentials = userLoginCredentials;
        this.getFirebaseRealtimeDBApiClientGetCountdownEvents =
                getFirebaseRealtimeDBApiClientGetCountdownEvents;
        this.postFirebaseRealtimeDBApiClientPostCountdownEvent =
                postFirebaseRealtimeDBApiClientPostCountdownEvent;
        this.patchFirebaseRealtimeDBApiClientUpdateCountdownEvent =
                patchFirebaseRealtimeDBApiClientUpdateCountdownEvent;
        this.deleteFirebaseRealtimeDBApiClientUpdateCountdownEvent =
                deleteFirebaseRealtimeDBApiClientUpdateCountdownEvent;
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
                userLoginCredentials,
                getFirebaseRealtimeDBApiClientGetCountdownEvents,
                postFirebaseRealtimeDBApiClientPostCountdownEvent,
                patchFirebaseRealtimeDBApiClientUpdateCountdownEvent,
                deleteFirebaseRealtimeDBApiClientUpdateCountdownEvent);
    }
}