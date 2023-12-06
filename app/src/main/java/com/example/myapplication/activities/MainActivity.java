package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.ApiService;
import com.example.myapplication.dto.AuthorizationDate;
import com.example.myapplication.R;
import com.example.myapplication.dto.UsersData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.31.5:8080/api/users/")
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON parsing
            .build();

    ApiService apiService = retrofit.create(ApiService.class);

    EditText loginText, passwordText;
    Button authorizationBtn;
    TextView wrongAuthorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginText =  findViewById(R.id.loginTxt);
        passwordText =  findViewById(R.id.passwordTxt);
        authorizationBtn =  findViewById(R.id.authorizationBtn);
        authorizationBtn.setEnabled(false);

        EditText[] edList = {loginText, passwordText};
        CustomTextWatcher textWatcher = new CustomTextWatcher(edList, authorizationBtn);
        for (EditText editText : edList) editText.addTextChangedListener(textWatcher);
    }

    public void authorizationClick(View view) {

        loginText = findViewById(R.id.loginTxt);
        passwordText = findViewById(R.id.passwordTxt);

        AuthorizationDate authorizationDate = new AuthorizationDate(loginText.getText().toString(),passwordText.getText().toString());

        Call<UsersData> call = apiService.login(authorizationDate);

        call.enqueue(new Callback<UsersData>() {
            @Override
            public void onResponse(Call<UsersData> call, Response<UsersData> response) {
                if (response.isSuccessful()) {
                    UsersData userResponse = response.body();

                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    intent.putExtra("userResponse", userResponse);
                    startActivity(intent);

                } else {
                    if(response.code() == 401){
                        loginText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        passwordText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                        wrongAuthorization = findViewById(R.id.wrongAuthorizationTxt);
                        wrongAuthorization.setText("Неверный логин или пароль");
                        wrongAuthorization.setTextColor(Color.RED);
                    }
                }
            }
            @Override
            public void onFailure(Call<UsersData> call, Throwable t) {
                Log.d("Don't work", "Internet problem " + t);
            }
        });
    }

    public void newAccRegistrationClick(View view){
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    public class CustomTextWatcher implements TextWatcher {

        View v;
        EditText[] edList;

        public CustomTextWatcher(EditText[] edList, Button v) {
            this.v = v;
            this.edList = edList;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            for (EditText editText : edList) {
                if (editText.getText().toString().trim().length() <= 0) {
                    v.setEnabled(false);
                    break;
                }
                else v.setEnabled(true);
            }
        }
    }
}