package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.ApiService;
import com.example.myapplication.DoctorAdapter;
import com.example.myapplication.R;
import com.example.myapplication.dto.DoctorData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoctorOfUserActivity extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://185.250.46.135:8081/api/users/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService apiService = retrofit.create(ApiService.class);
    private DoctorAdapter doctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_of_user);

        Long userId = getIntent().getLongExtra("userDataForDoctor", 1);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        doctorAdapter = new DoctorAdapter(new ArrayList<>());
        recyclerView.setAdapter(doctorAdapter);

        // Вызов метода для получения списка врачей
        getDoctorsByUserId(userId, recyclerView);
    }

    private void getDoctorsByUserId(Long userId, RecyclerView recyclerView) {
        Call<List<DoctorData>> call = apiService.getDoctorsByUserId(userId);

        call.enqueue(new Callback<List<DoctorData>>() {
            @Override
            public void onResponse(Call<List<DoctorData>> call, Response<List<DoctorData>> response) {
                if (response.isSuccessful()) {
                    List<DoctorData> doctors = response.body();

                    doctorAdapter.updateData(doctors);

                    Log.d("DoctorOfUserActivity", "Number of doctors: " + doctors.size());


                } else {
                    // Обработка ошибки
                    Toast.makeText(DoctorOfUserActivity.this, "Error fetching doctors", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DoctorData>> call, Throwable t) {
                // Обработка ошибки
                Toast.makeText(DoctorOfUserActivity.this, "Error fetching doctors", Toast.LENGTH_SHORT).show();
                Log.e("Error", "Of " + t);
            }
        });
    }
}