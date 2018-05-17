package com.myuniverse.android.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myuniverse.android.test.api.MyUniverseApi;
import com.myuniverse.android.test.api.CityInterceptor;
import com.myuniverse.android.test.api.RetrofitClient;
import com.myuniverse.android.test.application.MyUniverseApplication;
import com.myuniverse.android.test.model.State;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {

    @Inject
    Retrofit retrofit;

    private State states;

    private AutoCompleteTextView actvStates;
    private ListView lvCities;

    private ArrayAdapter<String> actvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ((MyUniverseApplication) getApplication()).getApiComponent().inject(this);

        initUI();

        //Calling the api
        MyUniverseApi api = retrofit.create(MyUniverseApi.class);
        Call<State> call = api.getCities();

        call.enqueue(new Callback<State>() {
            @Override
            public void onResponse(Call<State> call, Response<State> response) {
                states = response.body();
                if(states != null) {
                    processResponse();
                }
            }

            @Override
            public void onFailure(Call<State> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUI() {
        actvStates = (AutoCompleteTextView) findViewById(R.id.actvStates);
        lvCities = (ListView) findViewById(R.id.lvCities);
    }


    private void processResponse() {
        //Populating the autocomplete text view with states
        actvAdapter = new ArrayAdapter<String>
                (HomeActivity.this, android.R.layout.select_dialog_item, states.getStates());
        //this can be changed if we want to start getting the suggestions after 2,3 or any other number.
        actvStates.setThreshold(1);
        actvStates.setAdapter(actvAdapter);

        //On selecting any state the corresponding cities are displayed.
        actvStates.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {

                String selectedState = actvAdapter.getItem(pos);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        HomeActivity.this,
                        android.R.layout.simple_list_item_1,
                        //TODO: automatically replace " " to "_" while gson conversion.
                        states.getCities().get(selectedState.replaceAll(" ", "_")));

                lvCities.setAdapter(arrayAdapter);
            }
        });
    }

}
