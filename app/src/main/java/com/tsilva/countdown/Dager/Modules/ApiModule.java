package com.tsilva.countdown.Dager.Modules;

import com.tsilva.countdown.Api.Requests.Post.PostFirebaseAuthApiClientSignUp;
import com.tsilva.countdown.Api.RestClient.FirebaseAuthApiClient;

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
}