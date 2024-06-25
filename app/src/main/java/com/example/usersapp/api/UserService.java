package com.example.usersapp.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
    @GET("/api/")
    Call<UserResponse> getRandomUser();
}
