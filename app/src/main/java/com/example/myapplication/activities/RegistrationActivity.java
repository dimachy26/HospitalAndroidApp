package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.ApiService;
import com.example.myapplication.R;
import com.example.myapplication.dto.AuthorizationDate;
import com.example.myapplication.dto.RegistrationData;
import com.example.myapplication.dto.RegistrationResponse;
import com.example.myapplication.dto.UsersData;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.31.5:8080/api/users/")
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON parsing
            .build();

    ApiService apiService = retrofit.create(ApiService.class);

    EditText firstNameText, secondNameText, lastNameText, birthdateText, numberText, emailText, loginText, passwordText;
    Button registrationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firstNameText = findViewById(R.id.enterFirstName);
        secondNameText = findViewById(R.id.enterSecondName);
        lastNameText = findViewById(R.id.enterLastName);
        birthdateText = findViewById(R.id.enterBirthdate);
        numberText = findViewById(R.id.enterPhoneNumber);
        emailText = findViewById(R.id.enterEmail);
        loginText = findViewById(R.id.enterLogin);
        passwordText = findViewById(R.id.enterPassword);

        registrationBtn = findViewById(R.id.registrationBtn);

        registrationBtn.setEnabled(false);

        EditText[] edList = {firstNameText, secondNameText, lastNameText, birthdateText, numberText, emailText, loginText, passwordText};
        CustomTextWatcher textWatcher = new CustomTextWatcher(edList, registrationBtn);
        for (EditText editText : edList) editText.addTextChangedListener(textWatcher);
    }

    public void registrationClick(View view) {

        RegistrationData registration = new RegistrationData();
        registration.setFirstName(firstNameText.getText().toString());
        registration.setSecondName(secondNameText.getText().toString());
        registration.setLastName(lastNameText.getText().toString());
        registration.setBirthdate(birthdateText.getText().toString());
        registration.setNumber(numberText.getText().toString());
        registration.setEmail(emailText.getText().toString());
        registration.setLogin(loginText.getText().toString());
        registration.setPassword(passwordText.getText().toString());

        Call<RegistrationResponse> callRegistration = apiService.registration(registration);

        System.out.println(registration);

        callRegistration.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> callRegistration, Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    RegistrationResponse registrationResponse = response.body();
                    Log.i("Registration status", "Success");

                    loginAutomatically(registration.getLogin(), registration.getPassword());

                } else {
                    if (response.code() == 409) {
                        try {
                            // Получаем тело ответа при ошибке
                            String errorBody = response.errorBody().string();

                            Gson gson = new Gson();
                            ErrorResponse errorResponse = gson.fromJson(errorBody, ErrorResponse.class);


                            // Duplicate fields
                            List<String> duplicateFields = errorResponse.getDuplicateFields();
                            Log.w("Conflict", "Конфликтуют поля: " + duplicateFields);


                            //Изменение цвета edittext при конфликтах
                            if (duplicateFields.contains("login")) {
                                loginText.setBackgroundResource(R.drawable.edit_text_background_error);
                                loginText.setText("");
                                loginText.setHint("Данный логин уже используется");
                                loginText.setHintTextColor(Color.RED);
                            } else {
                                loginText.setBackgroundResource(R.drawable.edit_text_background);
                            }
                            if (duplicateFields.contains("email")) {
                                emailText.setBackgroundResource(R.drawable.edit_text_background_error);
                                emailText.setText("");
                                emailText.setHint("Данная почта уже используется");
                                emailText.setHintTextColor(Color.RED);

                            } else {
                                emailText.setBackgroundResource(R.drawable.edit_text_background);
                            }
                            if (duplicateFields.contains("number")) {
                                numberText.setBackgroundResource(R.drawable.edit_text_background_error);
                                numberText.setText("");
                                numberText.setHint("Данный номер телефона уже используется");
                                numberText.setHintTextColor(Color.RED);
                            } else {
                                numberText.setBackgroundResource(R.drawable.edit_text_background);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //Others Errors
                        Log.d("Ошибка", "Код ответа: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Log.w("Ошибка сервера", "А че делать-то" + t);
            }
        });
    }

    private void loginAutomatically(String login, String password) {

        AuthorizationDate authorizationDate = new AuthorizationDate(login, password);

        Call<UsersData> callAuthorization = apiService.login(authorizationDate);
        callAuthorization.enqueue(new Callback<UsersData>() {
            @Override
            public void onResponse(Call<UsersData> callAuthorization, Response<UsersData> response) {
                if (response.isSuccessful()) {
                    // Авторизация успешна, обрабатываем ответ
                    UsersData userResponse = response.body();

                    Intent intent = new Intent(RegistrationActivity.this, MenuActivity.class);
                    intent.putExtra("userResponse", userResponse);
                    startActivity(intent);

                    Log.i("Login status", "Success");
                } else {
                    // Ошибка авторизации
                    Log.d("Ошибка", "Код ответа при авторизации: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UsersData> callAuthorization, Throwable t) {
                // Обработка ошибки при выполнении запроса на авторизацию
            }
        });
    }

    public class CustomTextWatcher implements TextWatcher {

        View v;
        EditText[] edList;

        public CustomTextWatcher(EditText[] edList, Button v) {
            this.v = v;
            this.edList = edList;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            for (EditText editText : edList) {
                if (editText.getText().toString().trim().length() <= 0) {
                    v.setEnabled(false);
                    break;
                } else v.setEnabled(true);
            }
        }
    }
}