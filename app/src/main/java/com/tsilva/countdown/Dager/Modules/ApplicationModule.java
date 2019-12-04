package com.tsilva.countdown.Dager.Modules;

import android.app.Application;
import android.content.Context;

import com.tsilva.countdown.Services.PermissionsService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Telmo Silva on 04.12.2019.
 */

@Module
public class ApplicationModule
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
}