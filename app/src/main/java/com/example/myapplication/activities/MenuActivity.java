package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ApiService;
import com.example.myapplication.R;
import com.example.myapplication.dto.UsersData;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.31.5:8080/api/users/")
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON parsing
            .build();

    ApiService apiService = retrofit.create(ApiService.class);

    private TextView welcomeText;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView menuIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.slide_menu);
        menuIcon = findViewById(R.id.menuIcon);


        UsersData usersData = (UsersData) getIntent().getSerializableExtra("userResponse");

        welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Здравствуйте, " + usersData.getFirstName());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Установка слушателя кликов на иконку меню
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Открываем боковое меню
                drawerLayout.openDrawer(navigationView);
            }
        });

        // Установка слушателя кликов для пунктов меню
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Обработка клика по пункту меню
                switch (item.getItemId()) {
                    case R.id.myProfile:
                        Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
                        intent.putExtra("userDataForProfile", usersData);
                        startActivity(intent);
                        break;
                    case R.id.myDoctor:
                        intent = new Intent(MenuActivity.this, DoctorOfUserActivity.class);
                        intent.putExtra("userDataForDoctor", usersData);
                        startActivity(intent);
                        break;
                    // Добавьте дополнительные случаи для других пунктов меню, если необходимо
                }

                // Закрываем боковое меню после клика
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
    }
}



