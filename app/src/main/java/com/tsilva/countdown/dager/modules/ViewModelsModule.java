package com.tsilva.countdown.dager.modules;

import com.tsilva.countdown.modules.loginScreen.viewModel.LoginScreenViewModelFactory;
import com.tsilva.countdown.services.PermissionsService;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Telmo Silva on 04.12.2019.
 */

@Module
public final class ViewModelsModule
{
    @Provides
    public LoginScreenViewModelFactory provideLoginScreenViewModelFactory(
            PermissionsService permissionsService,
            PersistenceService persistenceService,
            StorageService storageService)
    {
        return new LoginScreenViewModelFactory(
                permissionsService, persistenceService, storageService);
    }
}