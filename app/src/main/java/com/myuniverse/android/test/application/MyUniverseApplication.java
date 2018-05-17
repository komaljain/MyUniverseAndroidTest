package com.myuniverse.android.test.application;

import android.app.Application;

import com.myuniverse.android.test.R;
import com.myuniverse.android.test.api.ApiComponent;
import com.myuniverse.android.test.api.ApiModule;
import com.myuniverse.android.test.api.DaggerApiComponent;

public class MyUniverseApplication extends Application {
    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApiComponent = DaggerApiComponent.builder()
                .myUniverseModule(new MyUniverseModule(this))
                .apiModule(new ApiModule(getString(R.string.API_URL)))
                .build();
    }

    public ApiComponent getApiComponent() {
        return mApiComponent;
    }
}
