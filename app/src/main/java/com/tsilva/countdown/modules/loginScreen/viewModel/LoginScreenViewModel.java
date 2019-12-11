package com.tsilva.countdown.modules.loginScreen.viewModel;

import com.tsilva.countdown.services.PermissionsService;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;

/**
 * Created by Telmo Silva on 11.12.2019.
 */

enum ViewType
{
    LOADING, CONTENT, EMPTY
}

public class LoginScreenViewModel
{
    private PermissionsService permissionsService = null;
    private PersistenceService persistenceService = null;
    private StorageService storageService = null;

    LoginScreenViewModel(
            PermissionsService permissionsService,
            PersistenceService persistenceService,
            StorageService storageService)
    {}
}