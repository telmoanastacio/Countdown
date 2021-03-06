package com.tsilva.countdown.dager.modules;

import android.app.Application;
import android.content.Context;

import com.tsilva.countdown.api.restClient.RestClientConfiguration;
import com.tsilva.countdown.api.restClient.clients.FirebaseAuthApiClient;
import com.tsilva.countdown.api.restClient.clients.FirebaseRealtimeDBApiClient;
import com.tsilva.countdown.persistence.UserLoginCredentials;
import com.tsilva.countdown.services.ImageProcessingService;
import com.tsilva.countdown.services.PermissionsService;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;
import com.tsilva.countdown.storage.activity.ActivityManager;
import com.tsilva.countdown.storage.adapter.AdapterManager;
import com.tsilva.countdown.storage.dialog.DialogManager;
import com.tsilva.countdown.storage.sharedViewModel.SharedViewModelManager;
import com.tsilva.countdown.storage.status.StatusManager;
import com.tsilva.countdown.storage.utils.UtilsManager;

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
    public PermissionsService providePermissionsService(
            Context context,
            StorageService storageService)
    {
        return PermissionsService.permissionsServiceInstance(context, storageService);
    }

    @Provides
    public PersistenceService providePersistenceService(Context context)
    {
        return PersistenceService.persistenceServiceInstance(context);
    }

    @Provides
    public UserLoginCredentials provideUserLoginCredentials(PersistenceService persistenceService)
    {
        return new UserLoginCredentials(persistenceService.getSharedPreferencesOperations());
    }

    @Provides
    public ImageProcessingService provideImageProcessingService
            (Context context,
             PersistenceService persistenceService)
    {
        return ImageProcessingService.imageProcessingServiceInstance(context, persistenceService);
    }

    @Provides
    public ActivityManager provideActivityManager()
    {
        return ActivityManager.activityManagerInstance();
    }

    @Provides
    public AdapterManager provideAdapterManager()
    {
        return AdapterManager.adapterManagerInstance();
    }

    @Provides
    public SharedViewModelManager provideSharedViewModelManager()
    {
        return SharedViewModelManager.sharedViewModelManagerInstance();
    }

    @Provides
    public StatusManager providesStatusManager()
    {
        return StatusManager.statusManagerInstance();
    }

    @Provides
    public UtilsManager providesUtilsManager()
    {
        return UtilsManager.utilsManagerInstance();
    }

    @Provides
    public DialogManager providesDialogManager()
    {
        return DialogManager.dialogManagerInstance();
    }

    @Provides
    public StorageService provideStorageService(
            PersistenceService persistenceService,
            ActivityManager activityManager,
            AdapterManager adapterManager,
            SharedViewModelManager sharedViewModelManager,
            StatusManager statusManager,
            UtilsManager utilsManager,
            DialogManager dialogManager
    )
    {
        StorageService storageService = StorageService.storageServiceInstance();
        storageService.init(
                persistenceService,
                activityManager,
                adapterManager,
                sharedViewModelManager,
                statusManager,
                utilsManager,
                dialogManager);
        return storageService;
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

    @Provides
    @Singleton
    public FirebaseRealtimeDBApiClient provideFirebaseRealtimeDBApiClient()
    {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestClientConfiguration.FIREBASE_REALTIME_DB_API_CLIENT_ENDPOINT)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(FirebaseRealtimeDBApiClient.class);
    }
}