package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.ApiService;
import com.example.myapplication.R;
import com.example.myapplication.dto.UsersData;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.31.5:8080/api/users/")
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON parsing
            .build();

    ApiService apiService = retrofit.create(ApiService.class);

    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        UsersData usersData = (UsersData) getIntent().getSerializableExtra("userResponse");

        welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Здравствуйте, " + usersData.getFirstName());
    }
}



