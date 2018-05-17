package com.myuniverse.android.test.api;

import android.content.Context;

import com.myuniverse.android.test.R;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public Retrofit createRetroFitClient(Context context, OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.API_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
