package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.ApiService;
import com.example.myapplication.R;
import com.example.myapplication.dto.RegistrationData;
import com.example.myapplication.dto.RegistrationResponse;
import com.example.myapplication.dto.UsersData;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://185.250.46.135:8081/api/users/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService apiService = retrofit.create(ApiService.class);

    EditText firstNameText, secondNameText, lastNameText, birthdateText, numberText, emailText;
    Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firstNameText = findViewById(R.id.editFirstName);
        secondNameText = findViewById(R.id.editSecondName);
        lastNameText = findViewById(R.id.editLastName);
        birthdateText = findViewById(R.id.editBirthdate);
        numberText = findViewById(R.id.editPhoneNumber);
        emailText = findViewById(R.id.editEmail);

        UsersData usersData = (UsersData) getIntent().getSerializableExtra("userDataForProfile");

        userId = usersData.getUserId();

        firstNameText.setText(usersData.getFirstName());
        secondNameText.setText(usersData.getSecondName());
        lastNameText.setText(usersData.getLastName());
        birthdateText.setText(usersData.getBirthdate());
        numberText.setText(usersData.getNumber());
        emailText.setText(usersData.getNumber());
        emailText.setText(usersData.getEmail());

    }

    public void updateUserDataClick(View view) {
        UsersData updateUser = new UsersData();

        updateUser.setFirstName(firstNameText.getText().toString());
        updateUser.setSecondName(secondNameText.getText().toString());
        updateUser.setLastName(lastNameText.getText().toString());
        updateUser.setBirthdate(birthdateText.getText().toString());
        updateUser.setNumber(numberText.getText().toString());
        updateUser.setEmail(emailText.getText().toString());

        updateUserProfile(userId, updateUser);
    }
    public void updateUserProfile(Long userId, UsersData usersData) {
        Call<RegistrationResponse> call = apiService.updateUser(userId, usersData);

        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    RegistrationResponse registrationResponse = response.body();
                    if (registrationResponse != null) {
                        if (response.code() == 200) {
                            // Обработка успешного обновления
                            Toast.makeText(ProfileActivity.this, "Профиль успешно обновлен", Toast.LENGTH_SHORT).show();
                        } else {
                            // Обработка неудачи
                            Toast.makeText(ProfileActivity.this, "Не удалось обновить профиль", Toast.LENGTH_SHORT).show();
                            Log.i("Error","of" + response.body());
                        }
                    } else {
                        // Обработка пустого тела ответа
                        Toast.makeText(ProfileActivity.this, "Пустой ответ от сервера", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 404) {
                    // Обработка статуса 404
                    Toast.makeText(ProfileActivity.this, "Пользователь не найден", Toast.LENGTH_SHORT).show();
                } else {
                    // Обработка других ошибок
                    Toast.makeText(ProfileActivity.this, "Ошибка: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                // Обработка неудачи
                Toast.makeText(ProfileActivity.this, "Не удалось обновить профиль: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Program Error","Of" + t.getMessage());
            }
        });
    }

}