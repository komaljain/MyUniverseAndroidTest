package com.myuniverse.android.test.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.myuniverse.android.test.model.State;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CityInterceptor implements Interceptor {

    private static String TAG = CityInterceptor.class.getCanonicalName();

    private Context context;

    public CityInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        okhttp3.Response response = chain.proceed(request);
        if (response.code() == 404) {
            StringBuilder returnString = new StringBuilder();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(
                        new InputStreamReader(context.getAssets().open("test_out.txt"), "UTF-8"));

                String mLine;
                while ((mLine = reader.readLine()) != null) {
                    returnString.append(mLine);
                }

            } catch (IOException e) {
                //TODO: log the exception
                Log.e(TAG, e.toString());
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        //TODO: log the exception
                        Log.e(TAG, e.toString());
                    }
                }
            }

            MediaType contentType = response.body().contentType();

            Type type = new TypeToken<State>() {}.getType();
            State states = new Gson().fromJson(returnString.toString(), State.class);

            ResponseBody body = ResponseBody.create(contentType, new Gson().toJson(states));
            return response.newBuilder().code(200).body(body).build();
        }

        return response;
    }
}
