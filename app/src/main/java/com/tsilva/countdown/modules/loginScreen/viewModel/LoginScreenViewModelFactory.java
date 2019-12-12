package com.tsilva.countdown.modules.loginScreen.viewModel;

import android.content.Context;

import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientEmailVerification;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientPasswordReset;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientSignIn;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientSignUp;
import com.tsilva.countdown.persistence.UserLoginCredentials;
import com.tsilva.countdown.services.ImageProcessingService;
import com.tsilva.countdown.services.PermissionsService;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;

/**
 * Created by Telmo Silva on 11.12.2019.
 */

public final class LoginScreenViewModelFactory
{
    private PermissionsService permissionsService = null;
    private PersistenceService persistenceService = null;
    private ImageProcessingService imageProcessingService = null;
    private StorageService storageService = null;
    private UserLoginCredentials userLoginCredentials = null;
    private PostFirebaseAuthApiClientSignUp postFirebaseAuthApiClientSignUp = null;
    private PostFirebaseAuthApiClientSignIn postFirebaseAuthApiClientSignIn = null;
    private PostFirebaseAuthApiClientEmailVerification postFirebaseAuthApiClientEmailVerification =
            null;
    private PostFirebaseAuthApiClientPasswordReset postFirebaseAuthApiClientPasswordReset = null;
    private Context context = null;

    public LoginScreenViewModelFactory(
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
        this.permissionsService = permissionsService;
        this.persistenceService = persistenceService;
        this.imageProcessingService = imageProcessingService;
        this.userLoginCredentials = userLoginCredentials;
        this.storageService = storageService;
        this.postFirebaseAuthApiClientSignUp = postFirebaseAuthApiClientSignUp;
        this.postFirebaseAuthApiClientSignIn = postFirebaseAuthApiClientSignIn;
        this.postFirebaseAuthApiClientEmailVerification =
                postFirebaseAuthApiClientEmailVerification;
        this.postFirebaseAuthApiClientPasswordReset = postFirebaseAuthApiClientPasswordReset;
        this.context = context;
    }

    public LoginScreenViewModel create()
    {
        return new LoginScreenViewModel(
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