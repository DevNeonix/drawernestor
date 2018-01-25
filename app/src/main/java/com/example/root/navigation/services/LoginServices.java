package com.example.root.navigation.services;



import com.example.root.navigation.Models.Login;
import com.example.root.navigation.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by root on 21/01/18.
 */

public interface LoginServices {

    @POST("login")
    Call<User> login(@Body Login login);
}
