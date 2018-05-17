package com.myuniverse.android.test.api;

import com.myuniverse.android.test.model.City;
import com.myuniverse.android.test.model.State;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyUniverseApi {

    @GET("city")
    Call<State> getCities();
}
