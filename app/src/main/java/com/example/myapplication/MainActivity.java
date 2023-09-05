package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.31.5:8080/api/users/") // Replace with the API base URL
                .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON parsing
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Family>> call = apiService.getAllUsers();

        call.enqueue(new Callback<List<Family>>() {
            @Override
            public void onResponse(Call<List<Family>> call, Response<List<Family>> response) {
                if (response.isSuccessful()) {
                    List<Family> families = response.body();

                    for (Family family : families) {
                        Log.d("FamilyData", "ID: " + family.getIdFamily());
                        Log.d("FamilyData", "First Name: " + family.getFirstName());
                        Log.d("FamilyData", "Last Name: " + family.getLastName());
                    // Process the list of families here
                }
                }
                else {
                    // Handle API error (e.g., response code is not in the 2xx range)
                    Log.e("ApiError", "API request failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Family>> call, Throwable t) {
                // Handle network failure (e.g., no internet connection, request timeout)
                Log.e("NetworkError", "Network request failed: " + t.getMessage());
            }
        });
    }
}