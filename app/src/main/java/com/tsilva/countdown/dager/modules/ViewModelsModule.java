package com.tsilva.countdown.dager.modules;

import android.content.Context;

import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientEmailVerification;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientPasswordReset;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientSignIn;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientSignUp;
import com.tsilva.countdown.modules.loginScreen.viewModel.LoginScreenViewModelFactory;
import com.tsilva.countdown.persistence.UserLoginCredentials;
import com.tsilva.countdown.services.ImageProcessingService;
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
            ImageProcessingService imageProcessingService,
            StorageService storageService,
            UserLoginCredentials userLoginCredentials,
            PostFirebaseAuthApiClientSignUp postFirebaseAuthApiClientSignUp,
            PostFirebaseAuthApiClientSignIn postFirebaseAuthApiClientSignIn,
            PostFirebaseAuthApiClientEmailVerification postFirebaseAuthApiClientEmailVerification,
            PostFirebaseAuthApiClientPasswordReset postFirebaseAuthApiClientPasswordReset,
            Context context)
    {
        return new LoginScreenViewModelFactory(
                permissionsService,
                persistenceService,
                imageProcessingService,
                storageService,
                userLoginCredentials,
                postFirebaseAuthApiClientSignUp,
                postFirebaseAuthApiClientSignIn,
                postFirebaseAuthApiClientEmailVerification,
                postFirebaseAuthApiClientPasswordReset,
                context);
    }
}