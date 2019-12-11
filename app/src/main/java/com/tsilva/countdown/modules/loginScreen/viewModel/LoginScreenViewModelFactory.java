package com.tsilva.countdown.modules.loginScreen.viewModel;

import com.tsilva.countdown.services.PermissionsService;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;

/**
 * Created by Telmo Silva on 11.12.2019.
 */

public class LoginScreenViewModelFactory
{
    private PermissionsService permissionsService = null;
    private PersistenceService persistenceService = null;
    private StorageService storageService = null;

    public LoginScreenViewModelFactory(
            PermissionsService permissionsService,
            PersistenceService persistenceService,
            StorageService storageService)
    {
        this.permissionsService = permissionsService;
        this.persistenceService = persistenceService;
        this.storageService = storageService;
    }

    public LoginScreenViewModel create()
    {
        return new LoginScreenViewModel(permissionsService, persistenceService, storageService);
    }
}