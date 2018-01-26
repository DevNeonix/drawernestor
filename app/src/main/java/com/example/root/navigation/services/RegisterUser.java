package com.example.root.navigation.services;

import com.example.root.navigation.Models.RUser;
import com.example.root.navigation.Models.RespuestaGenerica;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by root on 25/01/18.
 */

public interface RegisterUser {
    @POST("users")
    Call<RespuestaGenerica> register(@Body RUser user);
}
