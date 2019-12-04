package com.tsilva.countdown.Dager.Components;

import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.Dager.Modules.ApiModule;
import com.tsilva.countdown.Dager.Modules.ApplicationModule;
import com.tsilva.countdown.Dager.Modules.ViewModelsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Telmo Silva on 04.12.2019.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class, ViewModelsModule.class})
public interface ApplicationComponent extends ApplicationGraph
{
    final class Initializer
    {
        private Initializer() {} // No instances.

        public static ApplicationComponent init(CountdownApp app)
        {
            return DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(app))
                    .build();
        }
    }
}