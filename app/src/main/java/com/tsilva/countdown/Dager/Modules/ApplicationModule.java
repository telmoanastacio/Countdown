package com.tsilva.countdown.Dager.Modules;

import android.app.Application;
import android.content.Context;

import com.tsilva.countdown.Services.ImageProcessingService;
import com.tsilva.countdown.Services.PermissionsService;
import com.tsilva.countdown.Services.PersistenceService;
import com.tsilva.countdown.Services.StorageService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    public ImageProcessingService provideImageProcessingService(Context context)
    {
        return ImageProcessingService.imageProcessingServiceInstance(context);
    }

    @Provides
    public PersistenceService providePersistenceService(Context context)
    {
        return PersistenceService.persistenceServiceInstance(context);
    }

    @Provides
    public StorageService provideStorageService()
    {
        return StorageService.storageServiceInstance();
    }
}