package com.tsilva.countdown.Dager.Modules;

import com.tsilva.countdown.Api.Requests.Patch.PatchFirebaseRealtimeDBApiClientUpdateCountdownEvent;
import com.tsilva.countdown.Api.Requests.Post.PostFirebaseAuthApiClientDeleteAccount;
import com.tsilva.countdown.Api.Requests.Post.PostFirebaseAuthApiClientEmailVerification;
import com.tsilva.countdown.Api.Requests.Post.PostFirebaseAuthApiClientPasswordReset;
import com.tsilva.countdown.Api.Requests.Post.PostFirebaseAuthApiClientSignIn;
import com.tsilva.countdown.Api.Requests.Post.PostFirebaseAuthApiClientSignUp;
import com.tsilva.countdown.Api.Requests.Post.PostFirebaseRealtimeDBApiClientPostCountdownEvent;
import com.tsilva.countdown.Api.RestClient.Clients.FirebaseAuthApiClient;
import com.tsilva.countdown.Api.RestClient.Clients.FirebaseRealtimeDBApiClient;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Telmo Silva on 04.12.2019.
 */

@Module
public final class ApiModule
{
    @Provides
    public PostFirebaseAuthApiClientSignUp providePostFirebaseAuthApiClientSignUp(
            FirebaseAuthApiClient firebaseAuthApiClient)
    {
        return new PostFirebaseAuthApiClientSignUp(firebaseAuthApiClient);
    }

    @Provides
    public PostFirebaseAuthApiClientEmailVerification
    providePostFirebaseAuthApiClientEmailVerification(FirebaseAuthApiClient firebaseAuthApiClient)
    {
        return new PostFirebaseAuthApiClientEmailVerification(firebaseAuthApiClient);
    }

    @Provides
    public PostFirebaseAuthApiClientSignIn providePostFirebaseAuthApiClientSignIn(
            FirebaseAuthApiClient firebaseAuthApiClient)
    {
        return new PostFirebaseAuthApiClientSignIn(firebaseAuthApiClient);
    }

    @Provides
    public PostFirebaseAuthApiClientPasswordReset providePostFirebaseAuthApiClientPasswordReset(
            FirebaseAuthApiClient firebaseAuthApiClient)
    {
        return new PostFirebaseAuthApiClientPasswordReset(firebaseAuthApiClient);
    }

    @Provides
    public PostFirebaseAuthApiClientDeleteAccount providePostFirebaseAuthApiClientDeleteAccount(
            FirebaseAuthApiClient firebaseAuthApiClient)
    {
        return new PostFirebaseAuthApiClientDeleteAccount(firebaseAuthApiClient);
    }

    @Provides
    public PostFirebaseRealtimeDBApiClientPostCountdownEvent
    providePostFirebaseRealtimeDBApiClientPostCountdownEvent(
            FirebaseRealtimeDBApiClient firebaseRealtimeDBApiClient)
    {
        return new PostFirebaseRealtimeDBApiClientPostCountdownEvent(firebaseRealtimeDBApiClient);
    }

    @Provides
    public PatchFirebaseRealtimeDBApiClientUpdateCountdownEvent
    providePatchFirebaseRealtimeDBApiClientUpdateCountdownEvent(
            FirebaseRealtimeDBApiClient firebaseRealtimeDBApiClient)
    {
        return new PatchFirebaseRealtimeDBApiClientUpdateCountdownEvent(
                firebaseRealtimeDBApiClient);
    }
}