package com.example.root.navigation.services;



import com.example.root.navigation.Models.Login;
import com.example.root.navigation.Models.Users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by root on 21/01/18.
 */

public interface LoginServices {

    @POST("login")
    Call<Users> login(@Body Login login);
}
