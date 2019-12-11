package com.tsilva.countdown.dager.modules;

import com.tsilva.countdown.api.requests.delete.DeleteFirebaseRealtimeDBApiClientUpdateCountdownEvent;
import com.tsilva.countdown.api.requests.get.GetFirebaseRealtimeDBApiClientGetCountdownEvents;
import com.tsilva.countdown.api.requests.patch.PatchFirebaseRealtimeDBApiClientUpdateCountdownEvent;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientDeleteAccount;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientEmailVerification;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientPasswordReset;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientSignIn;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientSignUp;
import com.tsilva.countdown.api.requests.post.PostFirebaseRealtimeDBApiClientPostCountdownEvent;
import com.tsilva.countdown.api.restClient.clients.FirebaseAuthApiClient;
import com.tsilva.countdown.api.restClient.clients.FirebaseRealtimeDBApiClient;

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
    public GetFirebaseRealtimeDBApiClientGetCountdownEvents provideGetFirebaseRealtimeDBApiClientGetAllData(
            FirebaseRealtimeDBApiClient firebaseRealtimeDBApiClient)
    {
        return new GetFirebaseRealtimeDBApiClientGetCountdownEvents(firebaseRealtimeDBApiClient);
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

    @Provides
    public DeleteFirebaseRealtimeDBApiClientUpdateCountdownEvent
    provideDeleteFirebaseRealtimeDBApiClientUpdateCountdownEvent(
            FirebaseRealtimeDBApiClient firebaseRealtimeDBApiClient)
    {
        return new DeleteFirebaseRealtimeDBApiClientUpdateCountdownEvent(
                firebaseRealtimeDBApiClient);
    }
}