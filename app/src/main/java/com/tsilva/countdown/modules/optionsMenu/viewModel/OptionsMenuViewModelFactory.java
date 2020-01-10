package com.tsilva.countdown.modules.optionsMenu.viewModel;

import android.content.Context;

import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientDeleteAccount;
import com.tsilva.countdown.persistence.UserLoginCredentials;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;

/**
 * Created by Telmo Silva on 10.01.2020.
 */

public final class OptionsMenuViewModelFactory
{
    private Context context = null;
    private PersistenceService persistenceService = null;
    private StorageService storageService = null;
    private UserLoginCredentials userLoginCredentials = null;
    private PostFirebaseAuthApiClientDeleteAccount postFirebaseAuthApiClientDeleteAccount = null;

    public OptionsMenuViewModelFactory(Context context,
                                       PersistenceService persistenceService,
                                       StorageService storageService,
                                       UserLoginCredentials userLoginCredentials,
                                       PostFirebaseAuthApiClientDeleteAccount
                                               postFirebaseAuthApiClientDeleteAccount)
    {
        this.context = context;
        this.persistenceService = persistenceService;
        this.storageService = storageService;
        this.userLoginCredentials = userLoginCredentials;
        this.postFirebaseAuthApiClientDeleteAccount = postFirebaseAuthApiClientDeleteAccount;
    }

    public OptionsMenuViewModel create()
    {
        return new OptionsMenuViewModel(context,
                                        persistenceService,
                                        storageService,
                                        userLoginCredentials,
                                        postFirebaseAuthApiClientDeleteAccount);
    }
}