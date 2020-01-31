package com.tsilva.countdown.dager.modules;

import android.content.Context;

import com.tsilva.countdown.api.requests.delete.DeleteFirebaseRealtimeDBApiClientUpdateCountdownEvent;
import com.tsilva.countdown.api.requests.get.GetFirebaseRealtimeDBApiClientGetCountdownEvents;
import com.tsilva.countdown.api.requests.patch.PatchFirebaseRealtimeDBApiClientUpdateCountdownEvent;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientDeleteAccount;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientEmailVerification;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientPasswordReset;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientSignIn;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientSignUp;
import com.tsilva.countdown.api.requests.post.PostFirebaseRealtimeDBApiClientPostCountdownEvent;
import com.tsilva.countdown.modules.confirmScreen.viewModel.ConfirmDialogViewModelFactory;
import com.tsilva.countdown.modules.editPost.viewModel.EditPostViewModelFactory;
import com.tsilva.countdown.modules.loginScreen.viewModel.LoginScreenViewModelFactory;
import com.tsilva.countdown.modules.optionsMenu.viewModel.OptionsMenuViewModelFactory;
import com.tsilva.countdown.modules.postList.viewModel.PostListViewModelFactory;
import com.tsilva.countdown.modules.postList.viewModel.item.PostItemViewModelFactory;
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

    @Provides
    public OptionsMenuViewModelFactory provideOptionsMenuViewModelFactory(
            Context context,
            PersistenceService persistenceService,
            StorageService storageService,
            UserLoginCredentials userLoginCredentials,
            PostFirebaseAuthApiClientDeleteAccount
                    postFirebaseAuthApiClientDeleteAccount)
    {
        return new OptionsMenuViewModelFactory(
                context,
                persistenceService,
                storageService,
                userLoginCredentials,
                postFirebaseAuthApiClientDeleteAccount);
    }

    @Provides
    public PostListViewModelFactory providePostListViewModelFactory(
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
        return new PostListViewModelFactory(
                context,
                persistenceService,
                storageService,
                userLoginCredentials,
                getFirebaseRealtimeDBApiClientGetCountdownEvents,
                postFirebaseRealtimeDBApiClientPostCountdownEvent,
                patchFirebaseRealtimeDBApiClientUpdateCountdownEvent,
                deleteFirebaseRealtimeDBApiClientUpdateCountdownEvent);
    }

    @Provides
    public PostItemViewModelFactory providePostItemViewModelFactory(
            Context context,
            PersistenceService persistenceService,
            StorageService storageService,
            UserLoginCredentials userLoginCredentials)
    {
        return new PostItemViewModelFactory(
                context,
                persistenceService,
                storageService,
                userLoginCredentials);
    }

    @Provides
    public EditPostViewModelFactory provideEditPostViewModelFactory(
            Context context,
            StorageService storageService,
            UserLoginCredentials userLoginCredentials)
    {
        return new EditPostViewModelFactory(
                context,
                storageService,
                userLoginCredentials);
    }

    @Provides
    public ConfirmDialogViewModelFactory provideConfirmDialogViewModelFactory()
    {
        return new ConfirmDialogViewModelFactory();
    }
}