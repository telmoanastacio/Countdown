package com.tsilva.countdown.Dager.Modules;

import android.app.Application;
import android.content.Context;

import com.tsilva.countdown.Api.RestClient.Clients.FirebaseAuthApiClient;
import com.tsilva.countdown.Api.RestClient.RestClientConfiguration;
import com.tsilva.countdown.Services.ImageProcessingService;
import com.tsilva.countdown.Services.PermissionsService;
import com.tsilva.countdown.Services.PersistenceService;
import com.tsilva.countdown.Services.StorageService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Telmo Silva on 04.12.2019.
 */

@Module
public final class ApplicationModule
{
    private final Application application;

    public ApplicationModule(Application application)
    {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext()
    {
        return application;
    }

    @Provides
    public PermissionsService providePermissionsService(Context context)
    {
        return PermissionsService.permissionsServiceInstance(context);
    }

    @Provides
    public PersistenceService providePersistenceService(Context context)
    {
        return PersistenceService.persistenceServiceInstance(context);
    }

    @Provides
    public ImageProcessingService provideImageProcessingService
            (Context context,
             PersistenceService persistenceService)
    {
        return ImageProcessingService.imageProcessingServiceInstance(context, persistenceService);
    }

    @Provides
    public StorageService provideStorageService()
    {
        return StorageService.storageServiceInstance();
    }

    @Provides
    @Singleton
    public FirebaseAuthApiClient provideFirebaseAuthApiClient()
    {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestClientConfiguration.FIREBASE_AUTH_API_CLIENT_ENDPOINT)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(FirebaseAuthApiClient.class);
    }
}