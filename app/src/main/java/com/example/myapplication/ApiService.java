package com.example.myapplication;

import com.example.myapplication.dto.AuthorizationDate;
import com.example.myapplication.dto.RegistrationData;
import com.example.myapplication.dto.RegistrationResponse;
import com.example.myapplication.dto.UsersData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("login")
    Call<UsersData> login(@Body AuthorizationDate authorizationDate);

    @POST("registration")
    Call<RegistrationResponse> registration(@Body RegistrationData registration);

    @GET("user-info")
    Call<UsersData> getUsernameByLogin(@Query("login") String login);
}