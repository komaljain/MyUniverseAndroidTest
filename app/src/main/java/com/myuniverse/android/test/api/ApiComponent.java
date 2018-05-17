package com.myuniverse.android.test.api;

import com.myuniverse.android.test.HomeActivity;
import com.myuniverse.android.test.application.MyUniverseModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MyUniverseModule.class, ApiModule.class})
public interface ApiComponent {

    void inject(HomeActivity activity);
}
