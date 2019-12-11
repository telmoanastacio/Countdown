package com.tsilva.countdown.dager.components;

import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.dager.modules.ApiModule;
import com.tsilva.countdown.dager.modules.ApplicationModule;
import com.tsilva.countdown.dager.modules.ViewModelsModule;

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